package com.agoda.codingtest.client;

import com.agoda.filedownloader.connection.ConnectionParams;
import com.agoda.filedownloader.manager.DownloadManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Client class for handling calls to FileDownloader
 */
@Component
public class FileDownloadClient {

    @Autowired
    DownloadManager downloadManager;

    /**
     * Downloads the file from fileUrl
     * @param url URL string for the file to be downloaded
     * @return Absolute path of file downloaded to local file system
     * @throws Exception
     */
    public String download(String url) throws Exception {
        ConnectionParams connectionParams = new ConnectionParams();
        connectionParams.setConnectTimeOut(60000);
        connectionParams.setReadTimeOut(60000);

        return downloadManager.download(url, connectionParams);
    }
}
