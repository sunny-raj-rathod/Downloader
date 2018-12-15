package com.agoda.codingtest.client;

import com.agoda.filedownloader.configuration.DownloadProperties;
import com.agoda.filedownloader.manager.DownloadManager;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.mockito.internal.util.reflection.Whitebox;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class FileDownloadClientTest {

    @InjectMocks
    FileDownloadClient fileDownloadClient;

    DownloadManager downloadManager;

    DownloadProperties downloadProperties;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        downloadManager = mock(DownloadManager.class);
        downloadProperties = mock(DownloadProperties.class);
        Whitebox.setInternalState(downloadManager, "downloadProperties", downloadProperties);
        Whitebox.setInternalState(fileDownloadClient, "downloadManager", downloadManager);
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void download() throws Exception {
        String expectedReturnUrl = "return-url";
        when(downloadManager.download(any(), any())).thenReturn(expectedReturnUrl);
        String returnUrl = fileDownloadClient.download("https://www.baeldung.com/java-download-file");
        Assert.assertEquals("return-url", returnUrl);
    }
}