package com.agoda.filedownloader.manager;

import com.agoda.filedownloader.configuration.DownloadProperties;
import com.agoda.filedownloader.connection.ConnectionParams;
import com.agoda.filedownloader.downloader.Downloader;
import com.agoda.filedownloader.exception.DownloadException;
import com.agoda.filedownloader.factory.DownloaderFactory;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.internal.util.reflection.Whitebox;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.net.URI;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyObject;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(PowerMockRunner.class)
@PrepareForTest(DownloadManager.class)
public class DownloadManagerTest {

    DownloadManager downloadManager;

    @Mock
    DownloadProperties downloadProperties;

    @Mock
    DownloaderFactory downloaderFactory;

    Downloader downloader;

    @Before
    public void setUp() throws Exception {
        downloadProperties = mock(DownloadProperties.class);
        downloaderFactory = mock(DownloaderFactory.class);
        downloadManager = PowerMockito.spy(new DownloadManager(downloadProperties, downloaderFactory));
        MockitoAnnotations.initMocks(this);
        Whitebox.setInternalState(downloadManager, "downloaderFactory", downloaderFactory);
        downloader = mock(Downloader.class);
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void testDownload() throws Exception {
        URI url = new URI("ftp://anonymous:anonymous@speedtest.tele2.net/1KB.zip");

        String downloadedFileLocation = "/Users/sunnyrajrathod/test";

        ConnectionParams connectionParams = new ConnectionParams();

        PowerMockito.doReturn("local-file-path")
                .when(downloadManager, "prepareFileName", anyObject(), anyObject());

        when(downloaderFactory.getDownloader(any())).thenReturn(downloader);

        Mockito.doNothing().when(downloader).setConnectionParams(any());

        when(downloader.download(any())).thenReturn(true);

        String localFileUrl = downloadManager.download(url, downloadedFileLocation, connectionParams);
        Assert.assertEquals( "local-file-path", localFileUrl);
    }

    @Test(expected = DownloadException.class)
    public void testDownloadWithFailedDownload() throws Exception {
        URI url = new URI("ftp://anonymous:anonymous@speedtest.tele2.net/1KB.zip");

        String downloadedFileLocation = "/Users/sunnyrajrathod/test";

        PowerMockito.doReturn("local-file-path")
                .when(downloadManager, "prepareFileName", anyObject(), anyObject());

        when(downloader.download(any())).thenReturn(false);

        when(downloaderFactory.getDownloader(any())).thenReturn(downloader);

        ConnectionParams connectionParams = new ConnectionParams();

        downloadManager.download(url, downloadedFileLocation, connectionParams);
    }

    @Test(expected = DownloadException.class)
    public void testDownloadWithDownloadException() throws Exception {
        URI url = new URI("ftp://anonymous:anonymous@speedtest.tele2.net/1KB.zip");

        String downloadedFileLocation = "/Users/sunnyrajrathod/test";

        PowerMockito.doReturn("local-file-path")
                .when(downloadManager, "prepareFileName", anyObject(), anyObject());

        when(downloader.download(any())).thenThrow(Exception.class);

        when(downloaderFactory.getDownloader(any())).thenReturn(downloader);

        ConnectionParams connectionParams = new ConnectionParams();

        downloadManager.download(url, downloadedFileLocation, connectionParams);
    }

    @Test
    public void testDownloadWithConnectionParam() throws Exception {
        String fileUrl = "ftp://anonymous:anonymous@speedtest.tele2.net/1KB.zip";

        ConnectionParams connectionParams = new ConnectionParams();

        PowerMockito.doReturn("local-file-path")
                .when(downloadManager, "prepareFileName", anyObject(), anyObject());

        when(downloaderFactory.getDownloader(any())).thenReturn(downloader);

        Mockito.doNothing().when(downloader).setConnectionParams(any());

        when(downloader.download(any())).thenReturn(true);

        String localFileUrl = downloadManager.download(fileUrl, connectionParams);
        Assert.assertEquals( "local-file-path", localFileUrl);
    }

    @Test
    public void testDownloadWithWithDefaultLocation() throws Exception {
        String fileUrl = "ftp://anonymous:anonymous@speedtest.tele2.net/1KB.zip";

        PowerMockito.doReturn("local-file-path")
                .when(downloadManager, "prepareFileName", anyObject(), anyObject());

        when(downloaderFactory.getDownloader(any())).thenReturn(downloader);

        Mockito.doNothing().when(downloader).setConnectionParams(any());

        when(downloader.download(any())).thenReturn(true);

        String localFileUrl = downloadManager.download(fileUrl);
        Assert.assertEquals( "local-file-path", localFileUrl);
    }
}