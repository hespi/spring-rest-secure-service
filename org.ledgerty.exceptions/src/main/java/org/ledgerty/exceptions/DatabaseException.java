package org.ledgerty.exceptions;

import org.apache.http.HttpStatus;

/**
 * Created by Héctor on 05/06/2017.
 */
public class DatabaseException extends BaseException {

    public DatabaseException(String message) {
        super(HttpStatus.SC_INTERNAL_SERVER_ERROR, message);
    }

}
