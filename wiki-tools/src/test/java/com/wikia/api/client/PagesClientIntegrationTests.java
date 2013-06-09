package com.wikia.api.client;/**
 * Author: Artur Dwornik
 * Date: 06.06.13
 * Time: 10:00
 */

import com.wikia.api.json.JsonClientImpl;
import com.wikia.api.service.Page;
import com.wikia.api.service.PageService;
import com.wikia.api.util.ThreadPoolUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.testng.Assert;
import java.net.URL;
import java.util.concurrent.Callable;

public class PagesClientIntegrationTests {
    private static Logger logger = LoggerFactory.getLogger(PagesClientIntegrationTests.class);

    @org.testng.annotations.Test
    public void testGetPageContent() throws Exception {
        PageService pageService = new PageService( new ClientImpl(new JsonClientImpl(), new URL("http", "en.wikipedia.org", "/w/api.php")) );
        Page pageContent = pageService.getPage(736);
        Assert.assertNotNull(pageContent);
        System.out.println( pageContent.getWikiText().substring(0, 50) + " ...");
    }

    @org.testng.annotations.Test
    public void testGetPageContentAsync() throws Exception {
        final PageService pageService = new PageService( new ClientImpl(new JsonClientImpl(), new URL("http", "en.wikipedia.org", "/w/api.php")) );
        Page pageContent = ThreadPoolUtil.getExecutorService().submit(new Callable<Page>() {
            @Override
            public Page call() throws Exception {
                System.out.println("Start execution");
                return pageService.getPage(736);
            }
        }).get();
        Assert.assertNotNull(pageContent);
        System.out.println( pageContent.getWikiText().substring(0, 50) + " ...");
    }

    @org.testng.annotations.Test
    public void testGetPagesContent() throws Exception {
        PageService pageService = new PageService( new ClientImpl(new JsonClientImpl(), new URL("http", "en.wikipedia.org", "/w/api.php")) );
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
}
