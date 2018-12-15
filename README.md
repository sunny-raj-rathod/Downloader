# File Downloader
Sample project created as a part of Agoda interview process.

# Project Structure
The project comprises of 2 modules:
- `file-downloader` : Library project - provides  a single method for downloading a file, irrespective of download protocol to be used.
- `downloader-console-app` : Command Line application - uses the above library to create a command line application that accepts list of urls for download.

# Setup
  - `downloader-console-app` requires `application.properties` to be setup correctly.
  - `application.properties` require two directories to be specified : one for temp dir, and second one for final destination of files.
  - Update properties in `downloader-console-app/src/main/resources/application.properties` with absolute paths of the directories. 
  - Non-existing directories will be created automatically, but make sure that paths are accessible.
  - In case of ftp and sftp servers requiring user credentials, username and password should be part of URL itself. For example: `ftp://anonymous:anonymous@speedtest.tele2.net/1KB.zip`, `sftp://sunnyrajrathod:mypassword@localhost/Users/sunnyrajrathod/leetcode-solutions/README.md`

# Run
```sh
$ mvn clean install &&  java -jar -Dspring.config.location=downloader-console-app/src/main/resources/application.properties downloader-console-app/target/downloader-console-app-0.0.1-SNAPSHOT.jar "ftp://anonymous:anonymous@speedtest.tele2.net/1KB.zip"  "ftp://ftp.cs.brown.edu/pub/README" "https://www.baeldung.com/jackson-jsonmappingexception" "http://www.ovh.net/files/1Mb.dat" "ftp://anonymous:anonymous@speedtest.tele2.net/200MB.zip" "http://www.ovh.net/files/1Gb.dat"
```

# Sample Run
- Sample run with filesize upto 200MB is attached. Please check - `samplerun.log` file.

# Supported Protocols
    - ftp
    - sftp
    - http
    - https

# Using file-downloader library
Create an instance of DownloadManager
```
DownloadManager downloadManager;
```
Call download method along with url as the parameter:
```
String localFilePath = downloadManager.download(url)
```

Alternatively, you can also use another overloaded version of download method to setup connection parameters like `connectTimeout`, `readTimeout`, `userAgent` etc.
```
ConnectionParams connectionParams = new ConnectionParams();
connectionParams.setConnectTimeOut(60000);
connectionParams.setReadTimeOut(60000);

String localFilePath = downloadManager.download(url, connectionParams);
```

# Design Approach and Class walk-through

### Connection Classes
`FtpConnection.java`, `SftpConnection.java`, `HttpConnection.java`(with base class - `Connection.java`)
- Each of these connection classes handle connection of a specific protocol.
- Each of these take care of protocol specific implementations to connect and disconnect from source.
- Implements the `connect`, `disconnect` and `getInputStream` methods, which are required by `Downloader` classes

`ConnectionParams.java` 
- Additional class to contain all connection related parameters that can be configured by the user.

### Downloader Classes
`FtpDownloader.java`, `SftpDownloader.java`, `HttpDownloader.java` (with base class - `Downloader.java`)
- Each of these `Downloader` classes contain `Connection` objects of specific type of protocol.
- Each of these Downloader classes, right now only have one method `download` - which connects, performs download and disconnects the connection.

`DownloaderFactory.java` 
- Factory for getting specific `Downloader` objects.
- Throws `UnsupportedFormatException` in case of unsupported protocols.


### DownloadManager
`DownloadManager.java`
- Based on the input URL, it gets the specific type of file downloader from `DownloaderFactory`(`FtpDownloader`, `SftpDownloader`, `HttpDownloader`) and performs the download operation.
Throws `DownloadException` in case of download failure.

### Client 
`FileDownloadClient.java`
- Client class to serve as abstraction in application for all the `DownloadManager` related operations.
- Client just redirects the call to underlying `DownloadManager` component.

### Application 
`DownloaderApplication.java`
- Command line application that takes urls as the command line parameters and calls `FileDownloadClient` to download the files one by one.

# Tests
- Tested with `ftp`, `sftp` and `http`/`https`.
- Also tested with varying connection parameters like `connectTimeout` and `readTimeout`.

# Unit Tests
- Written mock tests for `DownloadManager` and `FileDownloadClient`.
- Check files 
        - `DownloadManagerTest.java`
        - `FileDownloadClientTest.java`

# Essential Design parameters
- Should be extensible to more protocols
    - Handled using creation of proper base classes for `Connection` and `Downloader`
    - Also implemented `DownloaderFactory` for ease of Downloader initialization.
- Should be able to handle files larger than memory
    - Handled using buffered reading while downloading the files.
- Should be able to handle slow and fast sources
    - Taken care of the configurable connection parameters like `connectTimeout`, `readTimeout` using  `ConnectionParams` and its usage in `Connection` classes
- Handle failures
    - Custom Exception `DownloadException` thrown in case of download failure. Client can implement retries for the file downloads.
- No partial data in final location
    - Use of temporary location during file download handles that in case of download failure, data is not written to final location.

