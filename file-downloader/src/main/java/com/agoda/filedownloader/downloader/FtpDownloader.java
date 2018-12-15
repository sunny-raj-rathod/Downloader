package com.agoda.filedownloader.downloader;

import com.agoda.filedownloader.connection.FtpConnection;
import com.agoda.filedownloader.connection.ConnectionParams;

import java.net.URI;


/**
 * Downloader for Ftp protocol, creates a ftp connection for download
 */
public class FtpDownloader extends Downloader {

    public FtpDownloader(URI url){
        connection = new FtpConnection(url);
    }

    public FtpDownloader(URI url, ConnectionParams connectionParams){
        connection = new FtpConnection(url, connectionParams);
    }
}
