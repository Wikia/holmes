package com.wikia.api.client.response;

import org.testng.Assert;
import org.testng.annotations.*;

public class AllPagesQueryContinueTest {

    @Test
    public void testGetAllPages() throws Exception {
        Assert.assertNull(new AllPagesQueryContinue().getAllPages());
    }

    @Test
    public void testSetAllPages() throws Exception {
        AllPagesQueryContinue allPagesQueryContinue = new AllPagesQueryContinue();
        AllPagesAPContinue allPagesAPContinue = new AllPagesAPContinue();

        allPagesQueryContinue.setAllPages(allPagesAPContinue);

        Assert.assertEquals(allPagesAPContinue, allPagesQueryContinue.getAllPages());

    }
}
