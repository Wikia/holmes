package com.wikia.api.client;


import com.wikia.api.client.response.AllPagesQueryResponseWrapper;
import com.wikia.api.client.response.LinksResponseWrapper;
import com.wikia.api.json.JsonClientImpl;
import junit.framework.Assert;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.impl.conn.PoolingClientConnectionManager;
import org.testng.annotations.Test;

import java.net.URL;

import static org.fest.assertions.Assertions.*;

public class ClientImplIT {
    private ClientConnectionManager clientConnectionManager = new PoolingClientConnectionManager();

    @Test
    public void testGetRevisionsById() throws Exception {
        // TODO
    }

    @Test
    public void testGetRevisionsByTitle() throws Exception {
        // TODO
    }

    @Test
    public void testGetAllPages() throws Exception {
        Client client = new ClientImpl(new JsonClientImpl(clientConnectionManager), new URL("http://wikipedia.org/w/api.php"));
        AllPagesQueryResponseWrapper allPages = client.getAllPages(3, null);

        assertThat(allPages.getQueryContinue()).isNotNull();
        assertThat(allPages.getQueryContinue().getAllPages()).isNotNull();
        assertThat(allPages.getQueryContinue().getAllPages().getContinueTitle()).isNotNull();

        assertThat(allPages.getQueryResponse()).isNotNull();
        assertThat(allPages.getQueryResponse().getPages()).isNotNull();
        assertThat(allPages.getQueryResponse().getPages()).hasSize(3);
    }

    @Test
    public void testGetLinks() throws Exception {
        Client client = new ClientImpl(new JsonClientImpl(clientConnectionManager), new URL("http://wikipedia.org/w/api.php"));
        LinksResponseWrapper links = client.getLinks(736L, "736|0|Adolf_Hitler");
        Assert.assertNotNull(links);
        Assert.assertNotNull(links.getQueryResponse());
        Assert.assertNotNull(links.getQueryResponse().getPages());
        Assert.assertNotNull(links.getQueryResponse().getPages().get("736"));
        Assert.assertNotNull(links.getQueryResponse().getPages().get("736").getLinks());
        Assert.assertEquals(links.getQueryResponse().getPages().get("736").getLinks().size(), 500);
        Assert.assertNotNull(links.getQueryResponse().getPages().get("736").getLinks().get(0).getTitle());
        Assert.assertNotNull(links.getQueryResponse().getPages().get("736").getLinks().get(0).getNamespace());
    }
}
