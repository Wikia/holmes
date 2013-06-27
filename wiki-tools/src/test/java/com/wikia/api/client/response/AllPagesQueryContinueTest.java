package com.wikia.api.client.response;/**
 * Author: Artur Dwornik
 * Date: 16.06.13
 * Time: 14:22
 */

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
