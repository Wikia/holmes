package com.wikia.reader.providers;

import com.wikia.reader.input.TextChunk;
import com.wikia.reader.util.AsyncQueue;

import java.io.IOException;

/**
 * Author: Artur Dwornik
 * Date: 24.03.13
 * Time: 00:13
 */
public interface Provider {
    AsyncQueue<TextChunk> provide() throws IOException;
}
