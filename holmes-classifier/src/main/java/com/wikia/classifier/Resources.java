package com.wikia.classifier;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.wikia.classifier.classifiers.model.PageWithType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.List;

/**
 * Helper class for accessing resources.
 */
public final class Resources {
    private static Logger logger = LoggerFactory.getLogger(Resources.class);
    private static final String exampleSetFileName = "example-set.json";

    /**
     * Prevent creating subclass
     */
    private Resources() {}

    public static String getExampleSetFileName() {
        return exampleSetFileName;
    }

    public static List<PageWithType> getExampleSet() throws IOException {
        try ( Reader reader = getClasspathFileReader( exampleSetFileName ) ) {
            return deserializePagesWithTypes( reader );
        }
    }

    /**
     * Get reader for resource file.
     * @param filename - Full path of resource file
     * @return Resource file reader.
     */
    public static Reader getClasspathFileReader( String filename ) throws IOException {
        logger.debug( String.format("Reading file: %s", filename) );
        InputStream resourceAsStream = Resources.class.getClassLoader().getResourceAsStream(filename);
        if ( resourceAsStream == null ) {
            throw new FileNotFoundException(String.format("File %s could not be found in classpath.", filename));
        }
        return new InputStreamReader( (resourceAsStream) );
    }

    private static List<PageWithType> deserializePagesWithTypes( Reader inputFile ) throws IOException {
        Gson gson = new Gson();
        return gson.fromJson(inputFile, new TypeToken<List<PageWithType>>() {}.getType());
    }
}
