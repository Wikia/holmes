package com.wikia.api.json;

import java.io.IOException;
import java.net.URI;


public interface JsonClient {
    <T> T get(URI url, Class<T> tClass) throws IOException;
}
