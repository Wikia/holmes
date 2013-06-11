package com.wikia.api.client;/**
 * Author: Artur Dwornik
 * Date: 06.06.13
 * Time: 10:00
 */

import com.google.common.collect.Lists;
import com.wikia.api.json.JsonClientImpl;
import com.wikia.api.service.Page;
import com.wikia.api.service.PageServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.testng.Assert;

import java.net.URI;
import java.net.URL;
import java.util.List;

public class PagesClientIntegrationTests {
    private static Logger logger = LoggerFactory.getLogger(PagesClientIntegrationTests.class);

    @org.testng.annotations.Test
    public void testGetPageContent() throws Exception {
        PageServiceImpl pageService = new PageServiceImpl( new ClientImpl(new JsonClientImpl(), new URL("http", "en.wikipedia.org", "/w/api.php")) );
        Page pageContent = pageService.getPage(736);
        Assert.assertNotNull(pageContent);
        System.out.println( pageContent.getWikiText().substring(0, 50) + " ...");
    }

    @org.testng.annotations.Test(timeOut = 30 * 1000 /* 30 seconds */)
    public void testGetPagesContent() throws Exception {
        PageServiceImpl pageService = new PageServiceImpl( new ClientImpl(new JsonClientImpl(), new URL("http", "en.wikipedia.org", "/w/api.php")) );
        int countDown = 60;
        for(Page pageContent: pageService.getPages()) {
            if( countDown -- <= 0 ) break;
            Assert.assertNotNull(pageContent);
            Assert.assertNotNull(pageContent.getId());
            Assert.assertNotNull(pageContent.getNamespace());
            Assert.assertNotNull(pageContent.getTitle());
            Assert.assertNotNull(pageContent.getWikiText());
            logger.info(pageContent.getTitle());
            //logger.info( pageContent.getWikiText().substring(0, 50) + " ...");
        }
        Assert.assertEquals( countDown, -1 );
    }

    @org.testng.annotations.Test(timeOut = 5 * 1000 /* 5 seconds */)
    public void testFullFetch() throws Exception {
        String wikiApi = "http://4-pages-3-redirects.wikia.com/api.php";
        PageServiceImpl pageService = new PageServiceImpl( new ClientImpl(new JsonClientImpl(), URI.create(wikiApi).toURL()) );

        List<Page> pageList =  Lists.newArrayList(pageService.getPages());


        Assert.assertEquals( pageList.size(), 4, "Expected number of fetched pages is 4");
    }
}
