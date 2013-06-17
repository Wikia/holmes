package com.wikia.api.client.response;/**
 * Author: Artur Dwornik
 * Date: 16.06.13
 * Time: 14:26
 */

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class RedirectTest {
    Redirect freshRedirect;

    @BeforeMethod
    public void createFreshRedirect() throws Exception {
        freshRedirect = new Redirect();
    }

    @Test
    public void testGetFrom() throws Exception {
        Assert.assertNull( freshRedirect.getFrom() );
    }

    @Test
    public void testSetFrom() throws Exception {
        freshRedirect.setFrom( "foo_from" );
        Assert.assertEquals( "foo_from", freshRedirect.getFrom() );
    }

    @Test
    public void testGetTo() throws Exception {
        Assert.assertNull( freshRedirect.getTo() );
    }

    @Test
    public void testSetTo() throws Exception {
        freshRedirect.setTo( "foo_to" );
        Assert.assertEquals( "foo_to", freshRedirect.getTo() );

    }
}
