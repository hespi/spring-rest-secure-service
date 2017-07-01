package org.ledgerty.common.exceptions;

import org.apache.http.HttpStatus;

/**
 * Created by Héctor on 05/06/2017.
 */
public class InvalidParametersException extends BaseException {

    public InvalidParametersException(String parameterName, String message) {
        super(HttpStatus.SC_BAD_REQUEST, String.format("(%s): %s", parameterName, message));
    }

    public InvalidParametersException(String message) {
        super(HttpStatus.SC_BAD_REQUEST, message);
    }
}
