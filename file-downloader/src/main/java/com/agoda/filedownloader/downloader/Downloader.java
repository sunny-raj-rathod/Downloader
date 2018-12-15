package com.agoda.filedownloader.downloader;

import com.agoda.filedownloader.connection.Connection;
import com.agoda.filedownloader.connection.ConnectionParams;
import com.agoda.filedownloader.util.Reader;

import java.io.IOException;
import java.io.InputStream;

/**
 * Base Downloader Class, which contains the download implementation for reuse among specific Downloader child classes
 */
public abstract class Downloader {
    Connection connection;

    public boolean download(String destinationFile) {
        try {
            connection.connect();

            InputStream inputStream = connection.getInputStream();
            boolean readResult = Reader.bufferedReader(inputStream, destinationFile);

            if(!readResult)
                return false;

            inputStream.close();
            return true;
        } catch (IOException e) {
            e.printStackTrace();
        }
        finally {
            connection.disconnect();
        }
        return false;
    }

    public void setConnectionParams(ConnectionParams connectionParams){
        connection.setConnectionParams(connectionParams);
    }
}

