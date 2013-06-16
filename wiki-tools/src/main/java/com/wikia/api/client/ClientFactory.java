package com.wikia.api.client;

import java.io.IOException;
import java.net.URL;

/**
 * Author: Artur Dwornik
 * Date: 16.06.13
 * Time: 00:08
 */
public interface ClientFactory {
    Client get( URL apiRoot ) throws IOException;
}
