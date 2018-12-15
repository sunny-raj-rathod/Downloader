package com.agoda.filedownloader.exception;

public class DownloadException extends Exception {
    public DownloadException(String message){
        super("Download Exception : " + message);
    }

    public DownloadException(String message, Throwable ex){
        super("Download Exception : " + message, ex);
    }
}
