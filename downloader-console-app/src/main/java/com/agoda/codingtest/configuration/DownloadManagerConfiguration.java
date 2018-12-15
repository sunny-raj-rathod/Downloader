package com.agoda.codingtest.configuration;

import com.agoda.filedownloader.configuration.DownloadProperties;
import com.agoda.filedownloader.factory.DownloaderFactory;
import com.agoda.filedownloader.manager.DownloadManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DownloadManagerConfiguration {
    @Bean
    public DownloadProperties getDownloadProperties(){
        return new DownloadProperties();
    }

    @Bean
    public DownloaderFactory getDownloaderFactory(){
        return new DownloaderFactory();
    }

    @Bean
    public DownloadManager getDownloadManager(){
        return new DownloadManager(getDownloadProperties(), getDownloaderFactory());
    }
}
