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
        allPagesAPContinue.setContinueTitle("asd");
        Assert.assertEquals(allPagesAPContinue.getContinueTitle(), "asd");
    }
}
