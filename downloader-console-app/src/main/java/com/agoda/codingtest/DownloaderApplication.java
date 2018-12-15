package com.agoda.codingtest;

import com.agoda.codingtest.client.FileDownloadClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


/**
 * Application startpoint
 */
@SpringBootApplication
public class DownloaderApplication implements CommandLineRunner {

	private static Logger LOG = LoggerFactory.getLogger(DownloaderApplication.class);

	/**
	 * Client that handles anything related to file download
	 */
	@Autowired
	FileDownloadClient fileDownloadClient;

	public static void main(String[] args) {
		SpringApplication.run(DownloaderApplication.class, args);
	}

	@Override
	public void run(String... args) {
		for (int i = 0; i < args.length; ++i) {
			try {
				String localFilePath = fileDownloadClient.download(args[i]);

				LOG.info("File with URL " + args[i] + " downloaded to " + localFilePath);

			} catch (Exception e) {
				LOG.error("Error downloading the file " + args[i]);
				e.printStackTrace();
			}
		}
	}
}
