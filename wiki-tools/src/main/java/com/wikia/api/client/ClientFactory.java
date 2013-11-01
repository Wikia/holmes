package com.wikia.api.client;

import java.io.IOException;
import java.net.URL;


public interface ClientFactory {
    Client get( URL apiRoot ) throws IOException;
}
