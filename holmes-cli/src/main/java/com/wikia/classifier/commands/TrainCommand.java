package com.wikia.classifier.commands;

import com.beust.jcommander.Parameter;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.wikia.classifier.classifiers.Classifier;
import com.wikia.classifier.classifiers.model.PageWithType;
import com.wikia.classifier.classifiers.serialization.GZippedClassifierFileFormat;
import com.wikia.classifier.classifiers.training.ClassifierTrainer;
import com.wikia.classifier.classifiers.training.ClassifierTrainerFactory;
import com.wikia.classifier.classifiers.training.ClassifierTrainingResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;

public class TrainCommand implements Command {
    private static Logger logger = LoggerFactory.getLogger(TrainCommand.class);

    @Parameter( required = true, description = "List of json input files")
    private List<String> files = new ArrayList<>();

    @Parameter( names = {"-o", "--out"}, required = true, description = "Path to output file")
    private String outFilePath = null;

    @Override
    public String getName() {
        return "Train";
    }

    @Override
    public void execute(AppParams params) {
        ArrayList<PageWithType> list = new ArrayList<>();
        for ( String file: files) {
            try {
                list.addAll( readFile(file) );
            } catch (FileNotFoundException e) {
                logger.error(String.format("File ('%s') not found.", file), e);
            } catch (IOException e) {
                logger.error(String.format("File ('%s') caused error.", file), e);
            }
        }
        if( list.size() > 0 ) {
            ClassifierTrainer trainer = new ClassifierTrainerFactory().create();
            ClassifierTrainingResult trainingResult = trainer.train(list);

            logger.info(String.format("SuccessRate: %.2f", trainingResult.getSuccessRate() * 100.0));

            try {
                serialize(trainingResult.getClassifier());
            } catch (IOException e) {
                logger.error( "Error while writing to file.", e );
            }
        }
    }

    public void serialize( Classifier classifier ) throws IOException {
        new GZippedClassifierFileFormat().write( classifier, outFilePath );
    }

    public List<PageWithType> readFile( String inputFile ) throws IOException {
        Gson gson = new Gson();
        try (Reader reader = new FileReader(inputFile)) {
            return gson.fromJson(reader, new TypeToken<List<PageWithType>>() {
            }.getType());
        }
    }
}

