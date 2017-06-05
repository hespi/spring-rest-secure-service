package org.ledgerty.business;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.hibernate.id.GUIDGenerator;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.ledgerty.dao.User;
import org.ledgerty.exceptions.InvalidParametersException;
import org.ledgerty.exceptions.NotFoundException;

import java.util.Date;
import java.util.UUID;

import static org.junit.Assert.*;

/**
 * Created by Héctor on 05/06/2017.
 */
public class UserManagerTest extends BaseTest {

    private UserManager userManager;

    @Before
    public void setUp() throws Exception {
        userManager = new UserManager(entityManager);
    }

    @Test(expected = InvalidParametersException.class)
    public void UserManagerTest_Login_When_EmailIsNull_Throws_InvalidParametersException() throws Exception {
        userManager.Login(null, "123456");
    }

    @Test(expected = InvalidParametersException.class)
    public void UserManagerTest_Login_When_EmailIsEmpty_Throws_InvalidParametersException() throws Exception {
        userManager.Login("", "123456");
    }

    @Test(expected = InvalidParametersException.class)
    public void UserManagerTest_Login_When_PasswordIsNull_Throws_InvalidParametersException() throws Exception {
        userManager.Login("email@mail.com", null);
    }

    @Test(expected = InvalidParametersException.class)
    public void UserManagerTest_Login_When_PasswordIsEmpty_Throws_InvalidParametersException() throws Exception {
        userManager.Login("email@mail.com", "");
    }

    @Test(expected = NotFoundException.class)
    public void UserManagerTest_Login_When_UserDoesNotExists_Throws_NotFoundException() throws Exception {
        userManager.Login("email@mail.com", "123456");
    }

    @Test(expected = NotFoundException.class)
    public void UserManagerTest_Login_When_UserExistsButIsNotActive_Throws_NotFoundException() throws Exception {
        User expected = new User(
                UUID.randomUUID().toString(),
                new Date(),
                "127.0.0.1",
                false,
                "Héctor",
                "Espí Hernández",
                "hectorspi@gmail.com",
                "123456",
                "669803007",
                "C:\\Borrame\\hespi",
                0F,
                0,
                0
        );

        addUser(expected);

        userManager.Login(expected.getEmail(), expected.getPassword());
    }

    @Test
    public void UserManagerTest_Login_When_UserExists_Returns_UserData() throws Exception {
        User expected = new User(
                UUID.randomUUID().toString(),
                new Date(),
                "127.0.0.1",
                true,
                "Héctor",
                "Espí Hernández",
                "hectorspi@gmail.com",
                "123456",
                "669803007",
                "C:\\Borrame\\hespi",
                0F,
                0,
                0
        );

        addUser(expected);

        User user = userManager.Login(expected.getEmail(), expected.getPassword());
        assertNotNull(user);
        assertTrue(EqualsBuilder.reflectionEquals(expected, user));
    }

    private void addUser(User userData) {
        entityManager.persist(userData);
    }
}