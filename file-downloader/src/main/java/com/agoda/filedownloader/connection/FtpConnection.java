package com.agoda.filedownloader.connection;

import com.agoda.filedownloader.util.Utility;
import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;


/**
 * Ftp specific connection built over Connection class.
 */
public class FtpConnection extends Connection {

    FTPClient ftpClient;

    public FtpConnection(URI url){
        super(url, 21);
        ftpClient = new FTPClient();
    }

    public FtpConnection(URI url, ConnectionParams connectionParams){
        super(url, 21, connectionParams);
        ftpClient = new FTPClient();
    }

    @Override
    public void connect() {
        try {
            ftpClient.connect(url.getHost(), port);
            ftpClient.login(Utility.getUserNameFromUrl(url), Utility.getPasswordFromUrl(url));
            ftpClient.enterLocalPassiveMode();
            ftpClient.setFileType(FTP.BINARY_FILE_TYPE);
            ftpClient.setDataTimeout(connectionParams.getReadTimeOut());
            ftpClient.setConnectTimeout(connectionParams.getConnectTimeOut());

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public InputStream getInputStream() {
        if(ftpClient.isConnected()){
            try {
                return ftpClient.retrieveFileStream(url.getPath());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    @Override
    public void disconnect() {
        try {
            if (ftpClient.isConnected()) {
                ftpClient.logout();
                ftpClient.disconnect();
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}
