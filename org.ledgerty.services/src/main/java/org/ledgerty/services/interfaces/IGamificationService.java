package org.ledgerty.services.interfaces;

import org.ledgerty.dao.User;

/**
 * Created by Héctor on 17/06/2017.
 * Manages the calculation of levels, badges...
 */
public interface IGamificationService {

    /**
     * Returns the level factor in experience
     * @return Number of experience to gain a level
     */
    int getLevelFactor();

    /**
     * Calculates the level of a user
     * @param user User data
     * @return 0 when null. Minimum level is 1
     */
    int calculateLevel(User user);

}
