package org.ledgerty.services.controllers;

import org.ledgerty.dao.User;
import org.ledgerty.services.security.LedgertySessionToken;
import org.ledgerty.services.services.GamificationService;
import org.ledgerty.services.utils.DTOFactory;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 * Created by Héctor on 17/06/2017.
 */
public abstract class BaseController {

    protected DTOFactory getDtoFactory() {
        return new DTOFactory(new GamificationService());
    }

    protected User getCurrentUser() {
        LedgertySessionToken token = (LedgertySessionToken)SecurityContextHolder.getContext().getAuthentication();
        return token.getUser();
    }
}
