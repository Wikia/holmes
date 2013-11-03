package com.wikia.classifier;

import com.wikia.classifier.classifiers.model.PageWithType;
import org.testng.annotations.Test;

import java.io.FileNotFoundException;
import java.io.Reader;
import java.util.List;

import static org.fest.assertions.Assertions.*;
public class ResourcesIT {

    @Test
    public void testGetExampleSetFileName() throws Exception {
        assertThat( Resources.getExampleSetFileName() ).isEqualTo("example-set.json");
    }

    @Test
    public void testGetExampleSet() throws Exception {
        assertThat( Resources.getExampleSet() ).hasSize(50);

        List<PageWithType> exampleSet = Resources.getExampleSet();
        for( int i = 0; i < 50 ; i++ ) {
            assertThat( exampleSet.get(i).getType() ).isNotNull();
            assertThat( exampleSet.get(i).getWikiText() ).isNotNull();
            assertThat( exampleSet.get(i).getTitle() ).isNotNull();
        }
    }

    @Test
    public void testGetClasspathFileReader() throws Exception {
        Reader classpathFileReader = Resources.getClasspathFileReader("example-set.json");
        assertThat( classpathFileReader.read() ).isNotEqualTo(-1); // test we can read anything
    }

    @Test
    public void testGetClasspathFileReaderThrowsFileNotFound() throws Exception {
        boolean exceptionCaught = false;
        try {
            Resources.getClasspathFileReader("foo-bar-123");
        } catch ( FileNotFoundException ex ) {
            exceptionCaught = true;
        }
        assertThat( exceptionCaught ).isTrue();
    }
}
