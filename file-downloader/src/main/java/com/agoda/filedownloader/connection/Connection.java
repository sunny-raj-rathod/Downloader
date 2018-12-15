package com.agoda.filedownloader.connection;

import lombok.Data;

import java.io.InputStream;
import java.net.URI;

/**
 * Abstract connection class
 * Contains generic methods required for implementation of any type of connection - ftp, sftp, http
 * Also contains ConnectionParams for handling the additional parameters of the connection
 */
@Data
public abstract class Connection {
    URI url;

    int port;

    ConnectionParams connectionParams;

    Connection(URI url, int port){
        this.url = url;
        this.port = port;
        connectionParams = new ConnectionParams();
        connectionParams.setConnectTimeOut(60000);
        connectionParams.setReadTimeOut(60000);
    }

    Connection(URI url, int port, ConnectionParams connectionParams){
        this(url, port);

        this.connectionParams = connectionParams;
    }

    abstract public void connect();

    abstract public InputStream getInputStream();

    abstract public void disconnect();
}
