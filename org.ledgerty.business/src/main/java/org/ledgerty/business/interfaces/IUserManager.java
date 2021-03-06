package org.ledgerty.business.interfaces;

import org.ledgerty.dao.User;
import org.ledgerty.dto.in.UserInfoForAddition;
import org.ledgerty.common.exceptions.AlreadyExistsException;
import org.ledgerty.common.exceptions.DatabaseException;
import org.ledgerty.common.exceptions.InvalidParametersException;
import org.ledgerty.common.exceptions.NotFoundException;

/**
 * Created by Héctor on 05/06/2017.
 */
public interface IUserManager extends IBaseManager<User> {

    /**
     * Logins a user into the application
     * @param email User unique email
     * @param password Password related to the user with the specified email
     * @return Reference to the user with the specified email and password
     * @throws InvalidParametersException When email or password are null or empty
     * @throws NotFoundException When there's no user with the specified email and password
     * @throws DatabaseException When any database operation fails
     */
    User Login(String email, String password) throws InvalidParametersException, NotFoundException, DatabaseException;

    /**
     * Registers a user in the application
     * @param user User information
     * @return Reference to the added user
     * @throws InvalidParametersException When user information is null or any of its mandatory data
     * @throws AlreadyExistsException When a user with the same email already exists in the system
     * @throws DatabaseException When any database operation fails
     */
    User Register(UserInfoForAddition user) throws InvalidParametersException, AlreadyExistsException, DatabaseException;

    /**
     * Returns a user by its public identifier
     * @param userId User Guid
     * @throws InvalidParametersException When user identifier is null or empty
     * @throws NotFoundException When a user with the same identifier doesn't exists in the system
     * @throws DatabaseException When any database operation fails
     * @return User reference
     */
    User getUserById(String userId) throws InvalidParametersException, NotFoundException, DatabaseException;
}
