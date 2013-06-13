package com.wikia.reader.providers;

import org.apache.http.concurrent.FutureCallback;

import java.io.IOException;

/**
 * Author: Artur Dwornik
 * Date: 24.03.13
 * Time: 14:58
 */
public interface WikiTextReader {
    void getWikiText(String title, FutureCallback<String> callback) throws IOException;
}
