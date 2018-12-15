package com.agoda.filedownloader.connection;

import lombok.Data;

/**
 * Parameters required for connections like timeout and useragent
 * Additional parameters can be proxy etc.
 */
@Data
public class ConnectionParams {
    private int connectTimeOut;

    private int readTimeOut;

    private String userAgent;
}
