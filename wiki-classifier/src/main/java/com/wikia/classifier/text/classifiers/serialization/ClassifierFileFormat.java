package com.wikia.classifier.text.classifiers.serialization;

import com.wikia.classifier.text.classifiers.Classifier;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public interface ClassifierFileFormat {
    public void write( Classifier classifier, String filename ) throws IOException;
    public void write( Classifier classifier, OutputStream outputStream ) throws IOException;
    public Classifier read( String filename ) throws IOException;
    public Classifier read( InputStream inputStream ) throws IOException;
}
