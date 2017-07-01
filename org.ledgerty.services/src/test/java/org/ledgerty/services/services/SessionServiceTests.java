package org.ledgerty.services.services;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.*;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.junit.Before;
import org.junit.Test;
import org.ledgerty.common.GuidUtils;
import org.ledgerty.common.exceptions.InvalidParametersException;
import org.ledgerty.dao.User;
import org.ledgerty.services.security.LedgertySessionToken;
import org.ledgerty.services.exceptions.InvalidTokenException;
import org.ledgerty.services.interfaces.ISessionService;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;

/**
 * Created by Héctor on 20/06/2017.
 */
public class SessionServiceTests {

    ISessionService sessionService;
    HttpServletRequest request;
    HttpServletResponse response;

    @Before
    public void setUp() throws Exception {
        request = mock(HttpServletRequest.class);
        response = mock(HttpServletResponse.class);
        sessionService = new SessionService(request, response);
    }

    /** generateToken TESTS **/

    @Test(expected = InvalidParametersException.class)
    public void SessionServiceTests_generateToken_When_UserDataIsNull_Throws_InvalidParametersException() {
        sessionService.generateToken(null);
    }

    @Test()
    public void SessionServiceTests_generateToken_When_UserDataIsNotNull_Returns_TokenData() {
        User user = new User(GuidUtils.generateShortGuid());
        LedgertySessionToken expected = new LedgertySessionToken(user.getGuid(), new Date());

        LedgertySessionToken actual = sessionService.generateToken(user);
        assertTrue(EqualsBuilder.reflectionEquals(expected, actual, "generationTime"));
        assertEquals(DateFormatUtils.format(expected.getGenerationTime(), DateFormatUtils.ISO_8601_EXTENDED_DATE_FORMAT.getPattern()), DateFormatUtils.format(actual.getGenerationTime(), DateFormatUtils.ISO_8601_EXTENDED_DATE_FORMAT.getPattern()));
    }

    /** encryptToken TESTS **/

    @Test(expected = InvalidParametersException.class)
    public void SessionServiceTests_encryptToken_When_TokenIsNull_Throws_InvalidParametersException() {
        sessionService.encryptToken(null);
    }

    @Test
    public void SessionServiceTests_encryptToken_When_TokenIsNotNull_Returns_EncryptedToken() {
        User user = new User(GuidUtils.generateShortGuid());
        LedgertySessionToken ledgertySessionToken = new LedgertySessionToken(user.getGuid(), new Date());

        String encryptedToken = sessionService.encryptToken(ledgertySessionToken);
        assertNotNull(encryptedToken);
        assertTrue(encryptedToken.length() > 0);
    }

    /** decryptToken TESTS **/

    @Test(expected = InvalidParametersException.class)
    public void SessionServiceTests_decryptToken_When_EncryptedTokenIsNull_Throws_InvalidParametersException() {
        sessionService.decryptToken(null);
    }

    @Test(expected = InvalidParametersException.class)
    public void SessionServiceTests_decryptToken_When_EncryptedTokenIsEmpty_Throws_InvalidParametersException() {
        sessionService.decryptToken("");
    }

    @Test(expected = InvalidTokenException.class)
    public void SessionServiceTests_decryptToken_When_EncryptedTokenIsInvalid_Throws_InvalidTokenException() {
        sessionService.decryptToken("INVALID_TOKEN");
    }

    @Test
    public void SessionServiceTests_decryptToken_When_EncryptedTokenIsValid_Returns_DecryptedToken() {
        User user = new User(GuidUtils.generateShortGuid());
        LedgertySessionToken ledgertySessionToken = new LedgertySessionToken(user.getGuid(), new Date());

        String encryptedToken = sessionService.encryptToken(ledgertySessionToken);
        when(request.getCookies()).thenReturn(new Cookie[] {((SessionService)sessionService).getSessionTokenCookie()});

        LedgertySessionToken actual = sessionService.decryptToken(encryptedToken);
        assertTrue(EqualsBuilder.reflectionEquals(ledgertySessionToken, actual, "generationTime"));
        assertEquals(DateFormatUtils.format(ledgertySessionToken.getGenerationTime(), DateFormatUtils.ISO_8601_EXTENDED_DATETIME_FORMAT.getPattern()), DateFormatUtils.format(actual.getGenerationTime(), DateFormatUtils.ISO_8601_EXTENDED_DATETIME_FORMAT.getPattern()));
    }
}
