package org.ledgerty.services.interfaces;

import org.ledgerty.business.interfaces.IUserManager;
import org.ledgerty.common.exceptions.AlreadyExistsException;
import org.ledgerty.common.exceptions.DatabaseException;
import org.ledgerty.common.exceptions.InvalidParametersException;
import org.ledgerty.common.exceptions.NotFoundException;
import org.ledgerty.dto.in.UserCredentials;
import org.ledgerty.dto.in.UserInfoForAddition;
import org.ledgerty.dto.out.SessionInfo;
import org.ledgerty.dto.out.UserData;

/**
 * Created by Héctor on 16/06/2017.
 */
public interface IUserController {

    IUserManager getUserManager();

    /**
     * Performs login
     * @param credentials User credentials
     * @return Session information
     * @exception InvalidParametersException When required data is empty
     * @exception NotFoundException When the user with the specified credentials doesn't exists
     * @exception DatabaseException When database fails
     */
    SessionInfo login(UserCredentials credentials) throws InvalidParametersException, NotFoundException, DatabaseException;

    /**
     * Registers a user in the system
     * @param userData
     * @return
     * @throws InvalidParametersException When required information is empty or user data is invalid
     * @throws AlreadyExistsException When a user with the same email already exists
     * @throws DatabaseException When database fails
     */
    UserData register(UserInfoForAddition userData) throws InvalidParametersException, AlreadyExistsException, DatabaseException;
}
