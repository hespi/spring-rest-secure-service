package org.ledgerty.services.controllers;

import org.ledgerty.business.UserManager;
import org.ledgerty.common.ValidationUtils;
import org.ledgerty.common.exceptions.AlreadyExistsException;
import org.ledgerty.common.exceptions.DatabaseException;
import org.ledgerty.common.exceptions.InvalidParametersException;
import org.ledgerty.common.exceptions.NotFoundException;
import org.ledgerty.dao.User;
import org.ledgerty.dto.in.UserCredentials;
import org.ledgerty.dto.in.UserInfoForAddition;
import org.ledgerty.dto.out.SessionInfo;
import org.ledgerty.dto.out.UserData;
import org.ledgerty.services.interfaces.ISessionService;
import org.ledgerty.services.interfaces.IUserController;
import org.ledgerty.services.services.SessionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityManager;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by Héctor on 14/06/2017.
 */
@RestController
@RequestMapping(path = "/users")
public class UserController extends BaseController implements IUserController {

    private EntityManager entityManager;

    private UserManager userManager;
    public UserManager getUserManager() {
        if (userManager == null) {
            userManager = new UserManager(this.entityManager);
        }
        return userManager;
    }

    private ISessionService sessionService;

    @Autowired
    public UserController(EntityManager manager) {
        this.entityManager = manager;
    }

    /** Endpoints **/

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public SessionInfo login(@RequestBody UserCredentials credentials, HttpServletRequest request, HttpServletResponse response) {
        sessionService = new SessionService(request, response);
        return login(credentials);
    }

    @RequestMapping(method = RequestMethod.POST)
    @Transactional
    @Override
    public UserData register(UserInfoForAddition userData) throws InvalidParametersException, AlreadyExistsException, DatabaseException {
        return getDtoFactory().createUserData(getUserManager().Register(userData));
    }

    @RequestMapping(value = "/{userGuid}")
    @PreAuthorize("hasRole('ROLE_USER')")
    public UserData getUser(@PathVariable String userGuid) {
        User user = getUserManager().getUserById(userGuid);
        return getDtoFactory().createUserData(user);
    }

    @Override
    public SessionInfo login(UserCredentials credentials) throws InvalidParametersException, NotFoundException, DatabaseException {
        ValidationUtils.validateNotNullOrEmpty(InvalidParametersException.class, credentials, "User credentials are required");
        User user = getUserManager().Login(credentials.getLogin(), credentials.getPassword());
        return getDtoFactory().createSessionInfo(user, buildUserSessionToken(user));
    }

    private String buildUserSessionToken(User user) {
        return sessionService.encryptToken(sessionService.generateToken(user));
    }
}
