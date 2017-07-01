package org.ledgerty.common.exceptions;

import org.apache.http.HttpStatus;

/**
 * Created by Héctor on 05/06/2017.
 */
public class AlreadyExistsException extends BaseException {

    public AlreadyExistsException(String message) {
        super(HttpStatus.SC_CONFLICT, message);
    }

}
