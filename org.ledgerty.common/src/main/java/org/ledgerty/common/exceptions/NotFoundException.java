package org.ledgerty.common.exceptions;

import org.apache.http.HttpStatus;

/**
 * Created by Héctor on 05/06/2017.
 */
public class NotFoundException extends BaseException {

    public NotFoundException(String message) {
        super(HttpStatus.SC_NOT_FOUND, message);
    }

}
