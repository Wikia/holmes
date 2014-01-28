package com.wikia.classifier.io;

import com.google.common.base.Charsets;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.wikia.api.model.Page;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.Iterator;

public class JsonPerLinePageProvider implements PageProvider {
    private static Logger logger = LoggerFactory.getLogger(JsonPerLinePageProvider.class.toString());
    private final String inputFile;

    public JsonPerLinePageProvider(String inputFile) {
        this.inputFile = inputFile;
    }

    @Override
    public Iterable<Page> getPages() throws IOException {
        final Gson gson = new Gson();
        return new Iterable<Page>() {
            @Override
            public Iterator<Page> iterator() {
                final BufferedReader reader;
                try {
                    reader = new BufferedReader(
                            new InputStreamReader(
                                    new FileInputStream(inputFile), Charsets.UTF_8));
                } catch (FileNotFoundException e) {
                    throw new RuntimeException(e);
                }

                return new Iterator<Page>() {
                    private Page currentPage = null;
                    private int lineCounter = 0;

                    @Override
                    public boolean hasNext() {
                        while ( currentPage == null ) {
                            try {
                                String line = reader.readLine();
                                if ( line == null ) break;
                                lineCounter ++;
                                currentPage = gson.fromJson(line, Page.class);
                            } catch (JsonSyntaxException e) {
                                logger.warn( "Error at line " + lineCounter );
                            } catch (IOException e) {
                                throw new RuntimeException(e);
                            }
                        }
                        return currentPage != null;
                    }

                    @Override
                    public Page next() {
                        Page page = currentPage;
                        currentPage = null;
                        return page;
                    }

                    @Override
                    public void remove() {
                        throw new UnsupportedOperationException();
                    }
                };
            }
        };
    }
}
