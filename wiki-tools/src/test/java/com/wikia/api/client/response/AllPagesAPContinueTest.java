package com.wikia.api.client.response;

import org.testng.Assert;
import org.testng.annotations.Test;

public class AllPagesAPContinueTest {

    @Test
    public void testGetContinueTitle() throws Exception {
        Assert.assertNull(new AllPagesAPContinue().getContinueTitle());
    }

    @Test
    public void testSetContinueTitle() throws Exception {
        AllPagesAPContinue allPagesAPContinue = new AllPagesAPContinue();
        allPagesAPContinue.setApContinue("asd");
        Assert.assertEquals(allPagesAPContinue.getContinueTitle(), "asd");
    }

    @Test
    public void testSetContinueTitle2() throws Exception {
        AllPagesAPContinue allPagesAPContinue = new AllPagesAPContinue();
        allPagesAPContinue.setApFrom("asd");
        Assert.assertEquals(allPagesAPContinue.getContinueTitle(), "asd");
    }
}
