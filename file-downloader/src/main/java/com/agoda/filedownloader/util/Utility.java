package com.agoda.filedownloader.util;

import java.io.File;
import java.net.URI;


/**
 * Utility class for any file handling and string manipulation functions
 */
public class Utility {

    public static String getUserNameFromUrl(URI url){
        if(url.getUserInfo() == null){
            return null;
        }
        return url.getUserInfo().substring(0,  url.getUserInfo().indexOf(":"));
    }

    public static String getPasswordFromUrl(URI url){
        if(url.getUserInfo() == null){
            return null;
        }
        return url.getUserInfo().substring(url.getUserInfo().indexOf(":") + 1);
    }

    public static boolean renameFile(String src, String dest){
        File tempFile = new File(src);

        return tempFile.renameTo(new File(dest));
    }

    public static boolean validateFilePath(String path){
        if(path == null){
            return false;
        }

        File tmpDir = new File(path);
        boolean pathExists = tmpDir.exists();

        if(!pathExists){
            return false;
        }

        return true;
    }
}
