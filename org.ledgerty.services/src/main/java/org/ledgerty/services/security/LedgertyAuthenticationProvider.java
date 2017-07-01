package org.ledgerty.services.security;

import org.ledgerty.business.UserManager;
import org.ledgerty.business.interfaces.IUserManager;
import org.ledgerty.common.exceptions.NotFoundException;
import org.ledgerty.dao.User;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;

import javax.persistence.EntityManager;

/**
 * Created by Héctor on 25/06/2017.
 */
public class LedgertyAuthenticationProvider implements AuthenticationProvider {


    private IUserManager userManager;

    private EntityManager entityManager;

    public IUserManager getUserManager() {
        if (userManager == null) {
            userManager = new UserManager(entityManager);
        }
        return userManager;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        LedgertySessionToken sessionToken = (LedgertySessionToken)authentication;
        sessionToken.setUser(getSessionTokenUser(sessionToken));
        return authentication;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return LedgertySessionToken.class.isAssignableFrom(aClass);
    }

    private User getSessionTokenUser(LedgertySessionToken sessionToken) {
        try {
            return getUserManager().getUserById(sessionToken.getUserId());
        } catch (NotFoundException ex) {
            throw new SecurityException("The session user doesn't exists", ex);
        }
    }
}
