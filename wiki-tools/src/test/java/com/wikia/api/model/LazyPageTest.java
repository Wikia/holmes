package com.wikia.api.model;


import com.google.common.util.concurrent.MoreExecutors;
import com.wikia.api.util.LazyFuture;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

@SuppressWarnings("unchecked")
public class LazyPageTest {
    private LazyPage lazyPage;

    @BeforeMethod
    public void setUp() throws Exception {
        lazyPage = new LazyPage();
    }

    @Test
    public void testGetWikiText() throws Exception {
        Assert.assertNull(lazyPage.getWikiText(), "Default value is null.");
    }

    @Test
    public void testSetWikiText() throws Exception {
        Runnable runnableMock = mock(Runnable.class);

        lazyPage.setWikiText("foo");
        Assert.assertEquals(lazyPage.getWikiText(),"foo", "Value is present after set.");
        lazyPage.getFutureWikiText().addListener(runnableMock, MoreExecutors.sameThreadExecutor());

        verify(runnableMock).run();
    }

    @Test
    public void testGetFutureWikiText() throws Exception {
        Assert.assertNotNull(lazyPage.getFutureWikiText(), "Default value is not null.");
    }

    @Test
    public void testSetFutureWikiText() throws Exception {
        LazyFuture<String> lazyFutureMock = mock(LazyFuture.class);
        lazyPage.setFutureWikiText(lazyFutureMock);
        Assert.assertEquals(lazyPage.getFutureWikiText(), lazyFutureMock);
    }

    @Test
    public void testGetId() throws Exception {
        Assert.assertNull(lazyPage.getPageId(), "Default value is null.");
    }

    @Test
    public void testGetFutureId() throws Exception {
        Runnable runnableMock = mock(Runnable.class);
        Assert.assertNotNull(lazyPage.getFutureId(), "Future not null");
        lazyPage.getFutureId().addListener(runnableMock, MoreExecutors.sameThreadExecutor());
        verify(runnableMock).run();
    }

    @Test
    public void testSetFutureId() throws Exception {
        LazyFuture<Long> lazyFuture = mock(LazyFuture.class);
        lazyPage.setFutureId(lazyFuture);
        Assert.assertEquals(lazyPage.getFutureId(), lazyFuture);
    }

    @Test
    public void testSetId() throws Exception {
        lazyPage.setId(13L);
        Assert.assertEquals(lazyPage.getPageId(), (Long) 13L);
    }

    @Test
    public void testGetFutureNamespace() throws Exception {
        LazyFuture<Long> lazyFutureMock = mock(LazyFuture.class);
        lazyPage.setFutureNamespace(lazyFutureMock);
        Assert.assertEquals(lazyPage.getFutureNamespace(), lazyFutureMock);
    }

    @Test
    public void testGetNamespace() throws Exception {
        Assert.assertNull(lazyPage.getNamespace());
    }

    @Test
    public void testSetFutureNamespace() throws Exception {
        LazyFuture<Long> lazyFutureMock = mock(LazyFuture.class);
        lazyPage.setFutureNamespace(lazyFutureMock);
        Assert.assertEquals(lazyPage.getFutureNamespace(), lazyFutureMock);
    }

    @Test
    public void testSetNamespace() throws Exception {
        Runnable runnableMock = mock(Runnable.class);
        lazyPage.setNamespace(14L);
        Assert.assertEquals(lazyPage.getNamespace(), (Long) 14L);
        lazyPage.getFutureNamespace().addListener(runnableMock, MoreExecutors.sameThreadExecutor());
        verify(runnableMock).run();
    }

    @Test
    public void testGetFutureTitle() throws Exception {
        Assert.assertNotNull(lazyPage.getFutureTitle());
    }

    @Test
    public void testGetTitle() throws Exception {
        Assert.assertNull(lazyPage.getTitle());
    }

    @Test
    public void testSetFutureTitle() throws Exception {
        LazyFuture lazyFutureMock = mock(LazyFuture.class);
        lazyPage.setFutureTitle(lazyFutureMock);
        Assert.assertEquals(lazyPage.getFutureTitle(), lazyFutureMock);
    }

    @Test
    public void testSetTitle() throws Exception {
        Runnable runnableMock = mock(Runnable.class);
        lazyPage.setTitle("foo");
        Assert.assertEquals(lazyPage.getTitle(), "foo");
        lazyPage.getFutureTitle().addListener(runnableMock, MoreExecutors.sameThreadExecutor());
        verify(runnableMock).run();
    }

    @Test
    public void testGetFutureLinks() throws Exception {
        Assert.assertNotNull(lazyPage.getFutureLinks());
    }

    @Test
    public void testGetLinks() throws Exception {
        Assert.assertNotNull(lazyPage.getLinks());
        Assert.assertEquals(lazyPage.getLinks(), new ArrayList<PageInfo>());
    }

    @Test
    public void testSetFutureLinks() throws Exception {
        LazyFuture<List<PageInfo>> futureMock = mock(LazyFuture.class);
        lazyPage.setFutureLinks(futureMock);
        Assert.assertEquals(lazyPage.getFutureLinks(), futureMock);
    }

    @Test
    public void testSetLinks() throws Exception {
        List<PageInfo> pageListMock = mock(List.class);
        lazyPage.setLinks(pageListMock);
        Assert.assertEquals(lazyPage.getLinks(), pageListMock);
    }
}
