package com.agoda.filedownloader.manager;

import com.agoda.filedownloader.configuration.DownloadProperties;
import com.agoda.filedownloader.downloader.Downloader;
import com.agoda.filedownloader.exception.DownloadException;
import com.agoda.filedownloader.factory.DownloaderFactory;
import com.agoda.filedownloader.connection.ConnectionParams;
import com.agoda.filedownloader.util.Utility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Main class which handles the file download irrespective of type of file. Provides a complete abstract over underlying protocols and connection type
 */
@Component
public class DownloadManager {

    @Autowired
    DownloadProperties downloadProperties;

    @Autowired
    DownloaderFactory downloaderFactory;

    @Autowired
    public DownloadManager(DownloadProperties downloadProperties, DownloaderFactory downloaderFactory){
        this.downloadProperties = downloadProperties;
        this.downloaderFactory = downloaderFactory;
    }

    /**
     * Downloads the file from fileUrl
     * @param fileUrl URL string for the file to be downloaded
     * @return Absolute path of file downloaded to local file system
     * @throws Exception
     */
    public String download(String fileUrl) throws Exception {
        return download(new URI(fileUrl), downloadProperties.getDefaultPath(), null);
    }

    /**
     *  Downloads the file from fileUrl, allows to set connection parameters
     * @param fileUrl URL string for the file to be downloaded
     * @param connectionParams Connection params such as timeout, useragent etc.
     * @return Absolute path of file downloaded to local file system
     * @throws Exception
     */
    public String download(String fileUrl, ConnectionParams connectionParams) throws Exception {
        return download(new URI(fileUrl), downloadProperties.getDefaultPath(), connectionParams);
    }

    /**
     * Delegates the download operation to respective type of Downloader, based on the protocol in the URL
     * @param url URL for the file to be downloaded
     * @param downloadedFileLocation path in local file system where downloaded file is to be saved
     * @return Absolute path of file downloaded to local file system
     * @throws Exception
     */
    public String download(URI url, String downloadedFileLocation, ConnectionParams connectionParams) throws Exception {

        String outputFileName = prepareFileName(downloadedFileLocation, url);
        String tempFileName = prepareFileName(downloadProperties.getTempPath(), url);

        Downloader downloader = downloaderFactory.getDownloader(url);

        if(connectionParams != null){
            downloader.setConnectionParams(connectionParams);
        }

        boolean success;
        try{
            success = downloader.download(tempFileName);
        }catch (Exception ex){
            ex.printStackTrace();
            throw new DownloadException("Failed to download " + url.getPath(), ex);
        }

        if(!success){
            throw new DownloadException("Failed to download " + url.getPath());
        }

        boolean renameOp = Utility.renameFile(tempFileName, outputFileName);

        if(!renameOp){
            throw new DownloadException("Failed to download " + url.getPath());
        }

        return outputFileName;
    }

    /**
     * Utility function to handle file naming
     * @param filePath
     * @param url
     * @return
     */
    private String prepareFileName(String filePath, URI url) throws IOException {
        if(!Utility.validateFilePath(filePath)){
            Path pathToFile = Paths.get(filePath);
            Files.createDirectories(pathToFile);
        }

        if(!filePath.endsWith("/"))
            filePath += "/";

        return filePath + (url.getHost() + url.getPath()).replaceAll("/", "_");
    }
}
