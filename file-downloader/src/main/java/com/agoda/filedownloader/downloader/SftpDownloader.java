package com.agoda.filedownloader.downloader;

import com.agoda.filedownloader.connection.ConnectionParams;
import com.agoda.filedownloader.connection.SftpConnection;

import java.net.URI;

/**
 *  Downloader for Sftp protocol, creates a sftp connection for download
 */
public class SftpDownloader extends Downloader {

    public SftpDownloader(URI url){
        connection = new SftpConnection(url);
    }

    public SftpDownloader(URI url, ConnectionParams connectionParams){
        connection = new SftpConnection(url, connectionParams);
    }
}
