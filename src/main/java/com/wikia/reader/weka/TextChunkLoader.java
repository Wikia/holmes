package com.wikia.reader.weka;

import com.wikia.reader.input.TextChunk;
import weka.core.*;
import weka.core.converters.AbstractLoader;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.logging.Logger;

/**
 * Author: Artur Dwornik
 * Date: 02.04.13
 * Time: 00:34
 */
@Deprecated
public class TextChunkLoader extends AbstractLoader {
    private static Logger logger = Logger.getLogger(TextChunkLoader.class.toString());
    private final List<TextChunk> textChunks;


    public TextChunkLoader(Collection<TextChunk> textChunks) {
        this.textChunks = new ArrayList<>(textChunks);
    }

    @Override
    public Instances getStructure() throws IOException {
        Instances instances = new Instances("Wikia", getTextChunkInstanceConverter(null).getAttributes(), textChunks.size());
        return instances;
    }

    @Override
    public Instances getDataSet() throws IOException {
        Instances instances = getStructure();
        for(TextChunk textChunk: textChunks) {
            Instance instance = getTextChunkInstanceConverter(instances).getInstance(textChunk);
            instance.setDataset(instances);
            instances.add(instance);
        }
        return instances;
    }

    @Override
    public Instance getNextInstance(Instances instances) throws IOException {
        throw new IOException("TextDirectoryLoader can't read data sets incrementally.");
    }

    @Override
    public String getRevision() {
        return RevisionUtils.extract("$Revision$");
    }

    protected TextChunkInstanceConverter getTextChunkInstanceConverter(Instances dataset) {
        return new TextChunkInstanceConverterImpl(dataset);
    }
}
