package com.agoda.filedownloader.connection;

import java.io.IOException;
import java.io.InputStream;
import java.net.*;


/**
 * Http and Https specific connection built over Connection class.
 */
public class HttpConnection extends Connection {

    public HttpConnection(URI url){
        super(url, 80);
    }

    public HttpConnection(URI url, ConnectionParams connectionParams){
        super(url, 80, connectionParams);
    }

    HttpURLConnection urlConnection;

    @Override
    public void connect() {
        if(connectionParams.getUserAgent() != null){
            System.setProperty("http.agent", connectionParams.getUserAgent());
        }
        else{
            System.setProperty("http.agent", "chrome");
        }

        try {
            urlConnection = (HttpURLConnection) url.toURL().openConnection();
            urlConnection.setConnectTimeout(connectionParams.getConnectTimeOut());
            urlConnection.setReadTimeout(connectionParams.getReadTimeOut());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public InputStream getInputStream() {
        try {
            if(urlConnection != null)
                return urlConnection.getInputStream();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public void disconnect() {

    }
}
