package com.wikia.classifier.text.classifiers.serialization;

import com.wikia.classifier.text.classifiers.Classifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

public class GZippedClassifierFileFormat implements ClassifierFileFormat {
    private static Logger logger = LoggerFactory.getLogger(GZippedClassifierFileFormat.class);

    @Override
    public void write(Classifier classifier, String filename) throws IOException {
        try ( OutputStream fileOut = new FileOutputStream( filename, false ) ) {
            logger.info( "Write classifier to file: " + filename );
            this.write( classifier, fileOut );
            fileOut.flush();
        }
    }

    @Override
    public void write(Classifier classifier, OutputStream outputStream) throws IOException {
        try (
                OutputStream compressingOutput = new GZIPOutputStream( outputStream, true );
                ObjectOutputStream out = new ObjectOutputStream( compressingOutput )
        ) {
            out.writeObject(classifier);
        }
    }

    @Override
    public Classifier read(String filename) throws IOException {
        try ( InputStream fileInputStream = new FileInputStream( filename ) ) {
            logger.info( "Read classifier from file: " + filename );
            return this.read( fileInputStream );
        }
    }

    @Override
    public Classifier read(InputStream inputStream) throws IOException {
        try (
                InputStream uncompressingInput = new GZIPInputStream( inputStream );
                ObjectInputStream in = new ObjectInputStream( uncompressingInput )
        ) {
            return (Classifier) in.readObject();
        } catch (ClassNotFoundException e) {
            throw new IOException( "Class not found during file deserialization.", e);
        }
    }
}
