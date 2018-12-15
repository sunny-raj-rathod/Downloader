package com.agoda.filedownloader.configuration;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Data
@Component
public class DownloadProperties {
    /**
     * default path for completed downloads
     */
    @Value("${file.downloadPath}")
    private String defaultPath;

    /**
     * temp location for ongoing downloads
     */
    @Value("${file.tempDownloadPath}")
    private String tempPath;
}
