package com.wikia.api.service;/**
 * Author: Artur Dwornik
 * Date: 06.06.13
 * Time: 10:00
 */

import com.google.common.collect.Lists;
import com.wikia.api.model.PageInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;

import java.io.IOException;
import java.util.List;
import java.util.Random;

abstract public class PagesServiceIntegrationTestsBase {
    private static Logger logger = LoggerFactory.getLogger(PagesServiceIntegrationTestsBase.class);

    @org.testng.annotations.Test
    public void testGetPageContent() throws Exception {
        PageService pageService = createPageService("http://en.wikipedia.org/w/api.php");
        PageInfo pageContent = pageService.getPage(736);
        Assert.assertNotNull(pageContent);
        System.out.println( pageContent.getWikiText().substring(0, 50) + " ...");
    }

    @org.testng.annotations.Test(timeOut = 30 * 1000 /* 30 seconds */)
    public void testGetPagesContent() throws Exception {
        PageService pageService = createPageService("http://en.wikipedia.org/w/api.php");
        int countDown = 60;
        for(PageInfo pageContent: pageService.getPages()) {
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

    @org.testng.annotations.Test(timeOut = 15 * 1000 /* 5 seconds */)
    public void testFullFetch() throws Exception {
        String wikiApi = "http://4-pages-3-redirects.wikia.com/api.php";
        PageService pageService = createPageService(wikiApi);

        List<PageInfo> pageList =  Lists.newArrayList(pageService.getPages());

        Assert.assertEquals( pageList.size(), 4, "Expected number of fetched pages is 4");
    }


    @org.testng.annotations.Test(timeOut = 5 * 1000 /* 5 seconds */)
    public void testNullFetch() throws Exception {
        String wikiApi = "http://4-pages-3-redirects.wikia.com/api.php";
        PageService pageService = createPageService(wikiApi);

        Assert.assertNull(pageService.getPage("NOT_EXISTING_" + new Random().nextLong()));
    }

    @org.testng.annotations.Test(successPercentage = 90, timeOut = 5 * 1000 /* 5 seconds */)
    public void testNullFetch2() throws Exception {
        String wikiApi = "http://4-pages-3-redirects.wikia.com/api.php";
        PageService pageService = createPageService(wikiApi);

        Assert.assertNull(pageService.getPage( Math.abs(new Random().nextLong()) ));
    }

    @org.testng.annotations.Test(timeOut = 50 * 1000 /* 50 seconds */)
    public void testLinks() throws Exception {
        String wikiApi = "http://4-pages-3-redirects.wikia.com/api.php";
        PageService pageService = createPageService(wikiApi);
        PageInfo page = pageService.getPage("4 Pages 3 redirects Wiki");
        Assert.assertNotNull(page);
        Assert.assertNotNull(page.getLinks());
        for( PageInfo linkedPage: page.getLinks() ) {
            System.out.print(linkedPage.getTitle());
            System.out.print(" ");
            System.out.print(linkedPage.getNamespace());
            System.out.print(" ");
            System.out.print(linkedPage.getId());
            System.out.println();
            Thread.sleep(10);
        }
        Assert.assertEquals(page.getLinks().get(0).getTitle(), "Page 2");
        Assert.assertEquals(page.getLinks().size(), 7);
    }

    protected abstract PageService createPageService(String wikiApi) throws IOException;
}