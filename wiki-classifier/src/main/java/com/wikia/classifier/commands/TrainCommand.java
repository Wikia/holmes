package com.wikia.classifier.commands;

import com.beust.jcommander.Parameter;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.wikia.classifier.text.classifiers.Classifier;
import com.wikia.classifier.text.classifiers.DefaultClassifierFactory;
import com.wikia.classifier.text.classifiers.exceptions.ClassifyException;
import com.wikia.classifier.text.classifiers.model.ClassificationResult;
import com.wikia.classifier.text.classifiers.model.PageWithType;
import com.wikia.classifier.text.classifiers.serialization.GZippedClassifierFileFormat;
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
    private String outFilePath;

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
            DefaultClassifierFactory classifierFactory = new DefaultClassifierFactory();
            Classifier classifier = classifierFactory.build(list);

            for ( PageWithType example: list ) {
                try {
                    ClassificationResult classificationResult = classifier.classify(example);
                    if (!classificationResult.getSingleClass().equals(example.getType())) {
                        logger.warn(String.format("Incorrect classification of %s (%d) was:%s expected %s."
                                , example.getTitle()
                                , example.getWikiId()
                                , classificationResult.getSingleClass()
                                , example.getType()));
                    }
                } catch (ClassifyException e) {
                    logger.error(String.format("Classifying of %s (%d) caused error.", example.getTitle(), example.getWikiId()), e );
                }
            }

            try {
                serialize(classifier);
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
