package com.agoda.filedownloader.downloader;

import com.agoda.filedownloader.connection.ConnectionParams;
import com.agoda.filedownloader.connection.HttpConnection;
import java.net.URI;

/**
 *  Downloader for HTTP and HTTPS protocol, creates a http connection for download
 */
public class HttpDownloader extends Downloader {

    public HttpDownloader(URI url){
        connection = new HttpConnection(url);
    }

    public HttpDownloader(URI url, ConnectionParams connectionParams){
        connection = new HttpConnection(url, connectionParams);
    }
}
