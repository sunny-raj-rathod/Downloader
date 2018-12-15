package com.agoda.filedownloader.connection;

import com.agoda.filedownloader.util.Utility;
import com.jcraft.jsch.*;

import java.io.*;
import java.net.URI;


/**
 * Sftp specific connection built over Connection class.
 */
public class SftpConnection extends Connection {
    Session session;
    ChannelSftp sftpChannel;

    public SftpConnection(URI url) {
        super(url, 22);
    }

    public SftpConnection(URI url, ConnectionParams connectionParams) {
        super(url, 22, connectionParams);
    }

    @Override
    public void connect() {
        try {
            JSch jsch = new JSch();
            session = jsch.getSession(Utility.getUserNameFromUrl(url), url.getHost(), port);

            session.setConfig("StrictHostKeyChecking", "no");
            session.setPassword(Utility.getPasswordFromUrl(url));
            session.connect();

            Channel channel = session.openChannel("sftp");
            channel.connect();
            sftpChannel = (ChannelSftp) channel;
        } catch (JSchException e) {
            e.printStackTrace();
        }
    }

    @Override
    public InputStream getInputStream() {
        if(sftpChannel != null){
            try {
                return sftpChannel.get(url.getPath());
            } catch (SftpException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    @Override
    public void disconnect() {
        sftpChannel.exit();
        session.disconnect();
    }
}
