package org.ledgerty.services.services;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import org.ledgerty.dao.User;
import org.ledgerty.services.interfaces.IGamificationService;

/**
 * Created by Héctor on 24/06/2017.
 */
public class GamificationServiceTests {
    IGamificationService gamificationService;

    @Before
    public void setUp() {
        gamificationService = new GamificationService();
    }

    /** getLevelFactor TESTS **/

    @Test
    public void gamificationServiceTests_GetLevelFactor_Returns_ExpectedValue() {
        assertEquals(1000, gamificationService.getLevelFactor());
    }

    /** calculateLevel TESTS **/

    @Test
    public void gamificationServiceTests_CalculateLevel_Returns_OneBasedLevel() {
        User user = new User();

        user.setExperience(0);
        assertEquals(1, gamificationService.calculateLevel(user));

        user.setExperience(gamificationService.getLevelFactor());
        assertEquals(2, gamificationService.calculateLevel(user));

        user.setExperience(gamificationService.getLevelFactor() + 1);
        assertEquals(2, gamificationService.calculateLevel(user));

        user.setExperience(gamificationService.getLevelFactor() * 2 - 1);
        assertEquals(2, gamificationService.calculateLevel(user));

        user.setExperience(gamificationService.getLevelFactor() * 2);
        assertEquals(3, gamificationService.calculateLevel(user));
    }
}
