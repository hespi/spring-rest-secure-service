package org.ledgerty.business.interfaces;

import org.ledgerty.dao.User;
import org.ledgerty.exceptions.InvalidParametersException;

/**
 * Created by Héctor on 05/06/2017.
 */
public interface IUserManager {

    /**
     *
     * @param email
     * @param password
     * @return
     */
    User Login(String email, String password) throws InvalidParametersException;
}
