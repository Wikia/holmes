package com.wikia.reader.providers;

import com.wikia.reader.util.AsyncQueue;

import java.io.IOException;
import java.util.List;

/**
 * Author: Artur Dwornik
 * Date: 24.03.13
 * Time: 14:22
 */
public interface WikiIndexReader {
    AsyncQueue<String> getIndex() throws IOException;
}
