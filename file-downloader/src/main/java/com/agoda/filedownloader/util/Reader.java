package com.agoda.filedownloader.util;

import java.io.*;


/**
 * Utility class for handling buffered reading from and to streams
 */
public class Reader {

    private static int readBufferSize = 4096;

    public static boolean bufferedReader(InputStream inputStream, String output){
        try {
            OutputStream outputStream = new BufferedOutputStream(new FileOutputStream(output));
            byte[] bytesArray = new byte[readBufferSize];
            int bytesRead = -1;
            System.out.print("Downloading...");
            while ((bytesRead = inputStream.read(bytesArray)) != -1) {
                outputStream.write(bytesArray, 0, bytesRead);
            }

            outputStream.close();

            return true;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }
}
