package org.ledgerty.services.services;

import org.ledgerty.dao.User;
import org.ledgerty.services.interfaces.IGamificationService;

/**
 * Created by Héctor on 17/06/2017.
 */
public class GamificationService implements IGamificationService {
    @Override
    public int getLevelFactor() {
        return 1000;
    }

    @Override
    public int calculateLevel(User user) {
        return user != null ? Math.max(1, (int)Math.floor((double)user.getExperience() / (double)getLevelFactor()) + 1) : 0;
    }
}
