package com.wikia.classifier.commands;

import com.beust.jcommander.Parameter;
import com.google.gson.Gson;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;
import com.wikia.classifier.text.classifiers.Classifier;
import com.wikia.classifier.text.classifiers.DefaultClassifierFactory;
import com.wikia.classifier.text.classifiers.PageWithType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
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

            try {
                serialize(classifier);
            } catch (IOException e) {
                logger.error( "Error while serialization.", e );
            }
        }
    }

    public void serialize( Object objectToSerialize ) throws IOException {
        FileOutputStream fileOut = null;
        ObjectOutputStream out = null;

        try {
            fileOut = new FileOutputStream( outFilePath );
            out = new ObjectOutputStream( fileOut );

            out.writeObject(objectToSerialize);
        } finally {
            if ( out != null ) {
                out.flush();
                out.close();
            }
            if ( fileOut != null ) {
                fileOut.flush();
                fileOut.close();
            }
        }
    }

    public List<PageWithType> readFile( String inputFile ) throws IOException {
        Gson gson = new Gson();
        Reader reader = new FileReader( inputFile );
        try {
            return gson.fromJson(reader, new TypeToken<List<PageWithType>>() {}.getType());
        } finally {
            reader.close();
        }
    }
}
