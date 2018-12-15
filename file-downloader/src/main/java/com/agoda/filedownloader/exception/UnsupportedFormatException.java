package com.agoda.filedownloader.exception;

public class UnsupportedFormatException extends Exception {
    public UnsupportedFormatException(String message){
        super("Unsupported Format Exception : " + message);
    }
}
