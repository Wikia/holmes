package com.wikia.api.json;


import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.google.gson.reflect.TypeToken;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.impl.conn.PoolingClientConnectionManager;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.net.URI;
import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.*;

@SuppressWarnings("unchecked")
public class JsonClientImplTest {
    private ClientConnectionManager clientConnectionManager = new PoolingClientConnectionManager();

    @Test
    public void testGetJsonElement() throws Exception {
        final URI uri = new URI("http://foobaz.bar/");
        HttpClient httpClientMock = mock(HttpClient.class);
        when(httpClientMock.execute(any(HttpGet.class), any(ResponseHandler.class))).thenAnswer(new Answer<Object>() {
            @Override
            public Object answer(InvocationOnMock invocationOnMock) throws Throwable {
                HttpGet get = (HttpGet) invocationOnMock.getArguments()[0];
                Assert.assertEquals(get.getURI(), uri);
                Assert.assertEquals(get.getHeaders("Accept")[0].getValue(), "application/json");
                return "[]";
            }
        });
        JsonClientImpl jsonClient = spy(new JsonClientImpl(clientConnectionManager));
        doReturn(httpClientMock).when(jsonClient).getHttpClient();

        JsonElement jsonElement = jsonClient.getJsonElement(uri);

        Assert.assertTrue(jsonElement.isJsonArray());
        verify(httpClientMock, times(1)).execute(any(HttpGet.class), any(ResponseHandler.class));
    }

    @Test
    public void testGetHttpClient() throws Exception {
        Assert.assertNotNull(new JsonClientImpl(clientConnectionManager).getHttpClient());
    }

    @Test
    public void testGet() throws Exception {
        URI uri = new URI("http://foobaz.bar/");
        JsonArray jsonElements = new JsonArray();
        jsonElements.add(new JsonPrimitive("foo"));
        jsonElements.add(new JsonPrimitive("bar"));

        JsonClientImpl jsonClient = spy(new JsonClientImpl(clientConnectionManager));
        doReturn(jsonElements).when(jsonClient).getJsonElement(uri);

        List<String> result = (List<String>) jsonClient.get(uri, new TypeToken<List<String>>() { }.getRawType());
        Assert.assertEquals(result, Arrays.asList("foo", "bar"));
    }

    @Test
    public void testParseJson() throws Exception {
        JsonElement jsonElement = new JsonClientImpl(clientConnectionManager).parseJson("[]");
        Assert.assertTrue(jsonElement.isJsonArray());
    }

    @Test()
    public void testParseJsonEmptyString() throws Exception {
        JsonElement jsonElement = new JsonClientImpl(clientConnectionManager).parseJson("");
        Assert.assertTrue(jsonElement.isJsonNull());
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testParseJsonThrows2() throws Exception {
        new JsonClientImpl(clientConnectionManager).parseJson(null);
    }

    @Test(expectedExceptions = JsonParseException.class)
    public void testParseJsonThrows3() throws Exception {
        new JsonClientImpl(clientConnectionManager).parseJson("{");
    }
}
