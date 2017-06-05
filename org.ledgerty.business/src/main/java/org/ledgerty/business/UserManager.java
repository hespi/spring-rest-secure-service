package org.ledgerty.business;

import org.ledgerty.business.common.ValidationUtils;
import org.ledgerty.business.interfaces.IUserManager;
import org.ledgerty.dao.User;
import org.ledgerty.exceptions.InvalidParametersException;
import org.ledgerty.exceptions.NotFoundException;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;

/**
 * Created by Héctor on 05/06/2017.
 */
public class UserManager extends BaseManager implements IUserManager {

    public UserManager(EntityManager entityManager) {
        super(entityManager);
    }


    public User Login(String email, String password) throws InvalidParametersException {
        ValidationUtils.validateNotNullOrEmpty(InvalidParametersException.class, email, "The email cannot be empty");
        ValidationUtils.validateNotNullOrEmpty(InvalidParametersException.class, password, "The password cannot be empty");
        return getActiveUserByEmailAndPassword(email, password);
    }

    private User getActiveUserByEmailAndPassword(String email, String password) {
        TypedQuery<User> query = getEntityManager().createQuery("SELECT u FROM User u WHERE u.email = :email AND u.password = :password AND u.active = true", User.class);
        query.setParameter("email", email);
        query.setParameter("password", password);

        try {
            return query.getSingleResult();
        } catch (NoResultException ex) {
            throw new NotFoundException("The user with specified email and password doesn't exists");
        }
    }
}
