package org.ledgerty.services.exceptions;

import org.apache.http.HttpStatus;
import org.ledgerty.common.exceptions.BaseException;

/**
 * Created by Héctor on 17/06/2017.
 */
public class InvalidTokenException extends BaseException {
    public InvalidTokenException(String message) {
        super(HttpStatus.SC_FORBIDDEN, message);
    }
}
