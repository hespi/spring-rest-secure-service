package org.ledgerty.business;

import org.ledgerty.business.interfaces.IUserManager;
import org.ledgerty.common.ValidationUtils;
import org.ledgerty.common.database.JpaEntityManager;
import org.ledgerty.common.database.Query;
import org.ledgerty.dao.User;
import org.ledgerty.dto.UserInfoForAddition;
import org.ledgerty.exceptions.AlreadyExistsException;
import org.ledgerty.exceptions.InvalidParametersException;
import org.ledgerty.exceptions.NotFoundException;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import java.util.Date;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Stream;

/**
 * Created by Héctor on 05/06/2017.
 */
public class UserManager extends BaseManager<User> implements IUserManager {

    public UserManager(EntityManager entityManager) throws ClassNotFoundException {
        super(new JpaEntityManager<User>(User.class, entityManager));
    }


    public User Login(String email, String password) throws InvalidParametersException, NotFoundException {
        ValidationUtils.validateNotNullOrEmpty(InvalidParametersException.class, email, "The email cannot be empty");
        ValidationUtils.validateNotNullOrEmpty(InvalidParametersException.class, password, "The password cannot be empty");
        return getActiveUserByEmailAndPassword(email, password);
    }

    public User Register(UserInfoForAddition userData) throws AlreadyExistsException {
        validateUserInfoForAddition(userData);
        return doAddUser(userData);
    }

    private User doAddUser(UserInfoForAddition userData) {
        String userGuid = UUID.randomUUID().toString();
        return getEntityManager().insert(new User(userGuid,
                new Date(),
                userData.getCreationIP(),
                true,
                userData.getFirstName(),
                userData.getLastName(),
                userData.getEmail(),
                userData.getPassword(),
                userData.getPhoneNumber(),
                getUserStoragePath(userGuid),
                0F,
                0,
                0));
    }

    private String getUserStoragePath(String userGuid) {
        //TODO: Implementar con dependencia
        return userGuid;
    }

    private void validateUserInfoForAddition(UserInfoForAddition user) {
        ValidationUtils.validateNotNullOrEmpty(InvalidParametersException.class, user, "The user information is required");
        ValidationUtils.validateNotNullOrEmpty(InvalidParametersException.class, user.getEmail(), "The user email is required");
        ValidationUtils.validateNotNullOrEmpty(InvalidParametersException.class, user.getFirstName(), "The user first name is required");
        ValidationUtils.validateNotNullOrEmpty(InvalidParametersException.class, user.getLastName(), "The user last name is required");
        ValidationUtils.validateNotNullOrEmpty(InvalidParametersException.class, user.getPassword(), "The user password is required");
        ValidationUtils.validateNullOrEmpty(AlreadyExistsException.class, getUserByEmail(user.getEmail()), "There's an already existing user with the same email");
    }

    private User getUserByEmail(String email) {
        Optional<User> user = getEntityManager().select(new Query("SELECT u " +
                "FROM User u " +
                "WHERE " +
                "u.email = :email")
                .addParameter("email", email)
                ).findFirst();
        return user.isPresent() ? user.get() : null;
    }

    private User getActiveUserByEmailAndPassword(String email, String password) {
        Optional<User> user = getEntityManager().select(new Query("SELECT u " +
                "FROM User u " +
                "WHERE " +
                "u.email = :email AND u.password = :password AND u.active = true")
                .addParameter("email", email)
                .addParameter("password", password)).findFirst();

        if (!user.isPresent()) {
            throw new NotFoundException("The user with specified email and password doesn't exists");
        }
        return user.get();
    }
}
