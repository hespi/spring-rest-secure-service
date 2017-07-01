package org.ledgerty.business;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.junit.Before;
import org.junit.Test;
import org.ledgerty.business.interfaces.IUserManager;
import org.ledgerty.dao.User;
import org.ledgerty.dto.in.UserInfoForAddition;
import org.ledgerty.common.exceptions.AlreadyExistsException;
import org.ledgerty.common.exceptions.InvalidParametersException;
import org.ledgerty.common.exceptions.NotFoundException;

import java.util.Date;
import java.util.UUID;

import static org.junit.Assert.*;

/**
 * Created by Test on 05/06/2017.
 */
public class UserManagerTest extends BaseTest {

    private IUserManager userManager;

    @Before
    public void setUp() throws Exception {
        userManager = new UserManager(entityManager);
    }

    /** LOGIN TESTS **/

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
                "Test",
                "User",
                "email@email.com",
                "123456",
                "666666666",
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
                "Test",
                "Surname",
                "email@email.com",
                "123456",
                "666666666",
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

    /** Register TESTS **/

    @Test(expected = InvalidParametersException.class)
    public void UserManagerTest_Register_When_UserInfoIsNull_Throws_InvalidParametersException() throws Exception {
        userManager.Register(null);
    }

    @Test(expected = InvalidParametersException.class)
    public void UserManagerTest_Register_When_UserEmailIsNull_Throws_InvalidParametersException() throws Exception {
        userManager.Register(new UserInfoForAddition("127.0.0.1", "Test", "User", null, "123456", "666666666"));
    }

    @Test(expected = InvalidParametersException.class)
    public void UserManagerTest_Register_When_UserEmailIsEmpty_Throws_InvalidParametersException() throws Exception {
        userManager.Register(new UserInfoForAddition("127.0.0.1", "Test", "User", "", "123456", "666666666"));
    }

    @Test(expected = InvalidParametersException.class)
    public void UserManagerTest_Register_When_UserFirstNameIsNull_Throws_InvalidParametersException() throws Exception {
        userManager.Register(new UserInfoForAddition("127.0.0.1", null, "User", "email@email.com", "123456", "666666666"));
    }

    @Test(expected = InvalidParametersException.class)
    public void UserManagerTest_Register_When_UserFirstNameIsEmpty_Throws_InvalidParametersException() throws Exception {
        userManager.Register(new UserInfoForAddition("127.0.0.1", "", "User", "email@email.com", "123456", "666666666"));
    }

    @Test(expected = InvalidParametersException.class)
    public void UserManagerTest_Register_When_UserPasswordIsNull_Throws_InvalidParametersException() throws Exception {
        userManager.Register(new UserInfoForAddition("127.0.0.1", "Test", "User", "email@email.com", null, "666666666"));
    }

    @Test(expected = InvalidParametersException.class)
    public void UserManagerTest_Register_When_UserPasswordIsEmpty_Throws_InvalidParametersException() throws Exception {
        userManager.Register(new UserInfoForAddition("127.0.0.1", "Test", "User", "email@email.com", "", "666666666"));
    }

    @Test(expected = AlreadyExistsException.class)
    public void UserManagerTest_Register_When_UserEmailAlreadyExists_Throws_AlreadyExistsException() throws Exception {
        userManager.Register(new UserInfoForAddition("127.0.0.1", "Test", "User", "email@email.com", "123456", "666666666"));
        userManager.Register(new UserInfoForAddition("127.0.0.1", "Test", "User", "email@email.com", "123456", "666666666")); //Second time must fail
    }

    @Test
    public void UserManagerTest_Register_When_UserInfoIsComplete_Returns_CreatedUser() throws Exception {
        User expected = new User("", new Date(), "localhost", true, "Test", "User", "email@email.com", "123456", "666666666", "", 0F, 0, 0);
        User actual = userManager.Register(new UserInfoForAddition("localhost", "Test", "User", "email@email.com", "123456", "666666666"));

        assertTrue(EqualsBuilder.reflectionEquals(expected, actual, "id", "guid", "dateAdded", "storagePath"));
        assertNotNull(actual.getId());
        assertNotNull(actual.getGuid());
        assertNotNull(actual.getStoragePath());
        assertEquals(DateFormatUtils.format(actual.getDateAdded(),"yyyy-mm-dd"), DateFormatUtils.format(new Date(),"yyyy-mm-dd"));
    }

    /** getUserById TESTS **/

    @Test(expected = InvalidParametersException.class)
    public void UserManagerTest_GetUserById_When_UserIdIsNull_Throws_InvalidParametersException() throws Exception {
        userManager.getUserById(null);
    }

    @Test(expected = InvalidParametersException.class)
    public void UserManagerTest_GetUserById_When_UserIdIsEmpty_Throws_InvalidParametersException() throws Exception {
        userManager.getUserById("");
    }

    @Test(expected = NotFoundException.class)
    public void UserManagerTest_GetUserById_When_UserIdDoesNotExists_Throws_NotFoundException() throws Exception {
        userManager.getUserById("NOT_EXISTING");
    }

    @Test(expected = NotFoundException.class)
    public void UserManagerTest_GetUserById_When_UserExistsButIsNotActive_Throws_NotFoundException() throws Exception {
        User expected = new User(
                UUID.randomUUID().toString(),
                new Date(),
                "127.0.0.1",
                false,
                "Test",
                "User",
                "email@email.com",
                "123456",
                "666666666",
                "C:\\Borrame\\hespi",
                0F,
                0,
                0
        );

        addUser(expected);

        userManager.getUserById(expected.getGuid());
    }

    @Test
    public void UserManagerTest_GetUserById_When_UserExists_And_IsActive_Returns_UserReference() {
        User expected = new User(
                UUID.randomUUID().toString(),
                new Date(),
                "127.0.0.1",
                true,
                "Test",
                "User",
                "email@email.com",
                "123456",
                "666666666",
                "C:\\Borrame\\hespi",
                0F,
                0,
                0
        );

        addUser(expected);
        User actual = userManager.getUserById(expected.getGuid());
        assertTrue(EqualsBuilder.reflectionEquals(expected, actual, "id", "guid", "dateAdded", "storagePath"));
        assertNotNull(actual.getId());
        assertNotNull(actual.getGuid());
        assertNotNull(actual.getStoragePath());
        assertEquals(DateFormatUtils.format(actual.getDateAdded(),"yyyy-mm-dd"), DateFormatUtils.format(new Date(),"yyyy-mm-dd"));
    }

    /** FUNCTIONS **/

    private void addUser(User userData) {
        entityManager.persist(userData);
    }
}