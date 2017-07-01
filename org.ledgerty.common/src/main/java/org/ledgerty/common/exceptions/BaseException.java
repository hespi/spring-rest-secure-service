package org.ledgerty.common.exceptions;

import org.apache.http.HttpStatus;

/**
 * Created by Héctor on 05/06/2017.
 */
public class BaseException extends RuntimeException {

    private int statusCode = HttpStatus.SC_INTERNAL_SERVER_ERROR;

    public BaseException(String message) {
        this(HttpStatus.SC_INTERNAL_SERVER_ERROR, message);
    }

    public BaseException(int httpStatusCode, String message) {
        super(message);
        statusCode = httpStatusCode;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }
}
