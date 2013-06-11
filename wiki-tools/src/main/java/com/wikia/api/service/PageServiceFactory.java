package com.wikia.api.service;/**
 * Author: Artur Dwornik
 * Date: 11.06.13
 * Time: 15:21
 */

import com.wikia.api.client.ClientImpl;
import com.wikia.api.json.JsonClientImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URL;

public class PageServiceFactory {
    private static Logger logger = LoggerFactory.getLogger(PageServiceFactory.class);

    PageService get( URL apiRoot ) {
        return new PageServiceImpl( new ClientImpl(new JsonClientImpl(), apiRoot) );
    }
}
