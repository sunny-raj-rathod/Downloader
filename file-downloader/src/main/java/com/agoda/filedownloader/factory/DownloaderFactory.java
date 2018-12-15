package com.agoda.filedownloader.factory;

import com.agoda.filedownloader.downloader.Downloader;
import com.agoda.filedownloader.downloader.HttpDownloader;
import com.agoda.filedownloader.downloader.SftpDownloader;
import com.agoda.filedownloader.downloader.FtpDownloader;
import com.agoda.filedownloader.exception.UnsupportedFormatException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.net.URI;


/**
 * Factory for creating Downloader objects specific to the requested URL.
 */
@Component
public class DownloaderFactory {

    public Downloader getDownloader(URI url) throws UnsupportedFormatException {
        switch (url.getScheme().toLowerCase()){
            case "ftp":
                return new FtpDownloader(url);
            case "sftp":
                return new SftpDownloader(url);
            case "http":
            case "https":
                return new HttpDownloader(url);
            default:
                throw new UnsupportedFormatException(url.getScheme() + " is not a supported protocol");
        }
    }
}

