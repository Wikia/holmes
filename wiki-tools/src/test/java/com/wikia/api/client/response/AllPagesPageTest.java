package com.wikia.api.client.response;/**
 * Author: Artur Dwornik
 * Date: 16.06.13
 * Time: 14:12
 */

import junit.framework.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.util.Random;

public class AllPagesPageTest {
    private AllPagesPage freshPage;
    private long randomNumber;

    @BeforeTest
    public void createNewPage() {
        freshPage = new AllPagesPage();
    }

    @BeforeTest
    public void setupRandomNumber() {
        randomNumber = new Random().nextLong();
    }

    @Test
    public void testGetId() throws Exception {
        Assert.assertNull(freshPage.getId());
    }

    @Test
    public void testSetId() throws Exception {
        freshPage.setId(randomNumber);
        Assert.assertEquals((Long) randomNumber, freshPage.getId());
    }

    @Test
    public void testGetTitle() throws Exception {
        Assert.assertNull(freshPage.getTitle());
    }

    @Test
    public void testSetTitle() throws Exception {
        freshPage.setTitle("foo");
        Assert.assertEquals("foo", freshPage.getTitle());
    }

    @Test
    public void testGetNamespace() throws Exception {
        Assert.assertNull(freshPage.getNamespace());
    }

    @Test
    public void testSetNamespace() throws Exception {
        freshPage.setNamespace(randomNumber);
        Assert.assertEquals((Long) randomNumber, freshPage.getNamespace());
    }
}
