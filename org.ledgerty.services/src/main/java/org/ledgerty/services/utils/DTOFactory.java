package org.ledgerty.services.utils;

import org.ledgerty.dao.User;
import org.ledgerty.dto.out.SessionInfo;
import org.ledgerty.dto.out.UserData;
import org.ledgerty.services.interfaces.IGamificationService;
import org.ledgerty.services.security.LedgertySessionToken;

/**
 * Created by Héctor on 16/06/2017.
 */
public final class DTOFactory {

    private IGamificationService gamificationManager;
    public IGamificationService getGamificationManager() {
        return gamificationManager;
    }

    public DTOFactory(IGamificationService gamificationManager) {
        this.gamificationManager = gamificationManager;
    }

    public UserData createUserData(User userData) {
        return new UserData(
                userData.getGuid(),
                userData.getFirstName(),
                userData.getLastName(),
                userData.getEmail(),
                userData.getPhoneNumber(),
                userData.getExperience(),
                getGamificationManager().calculateLevel(userData),
                userData.getCoins()
        );
    }

    public SessionInfo createSessionInfo(User userData, String encryptedToken) {
        return new SessionInfo(
                userData.getGuid(),
                userData.getFirstName(),
                userData.getLastName(),
                userData.getEmail(),
                userData.getExperience(),
                getGamificationManager().calculateLevel(userData),
                userData.getCoins(),
                encryptedToken
        );
    }
}
