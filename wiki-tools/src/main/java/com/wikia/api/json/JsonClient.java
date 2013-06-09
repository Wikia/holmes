package com.wikia.api.json;

import java.io.IOException;
import java.net.URI;

/**
 * Author: Artur Dwornik
 * Date: 04.06.13
 * Time: 00:47
 */
public interface JsonClient {
    <T> T get(URI url, Class<T> tClass) throws IOException;
}
