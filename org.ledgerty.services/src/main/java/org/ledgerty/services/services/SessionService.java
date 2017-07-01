package org.ledgerty.services.services;

import org.jasypt.util.text.BasicTextEncryptor;
import org.ledgerty.common.GuidUtils;
import org.ledgerty.common.ValidationUtils;
import org.ledgerty.common.exceptions.InvalidParametersException;
import org.ledgerty.dao.User;
import org.ledgerty.services.exceptions.InvalidTokenException;
import org.ledgerty.services.interfaces.ISessionService;
import org.ledgerty.services.security.LedgertySessionToken;
import org.springframework.beans.factory.annotation.Value;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.Optional;
import java.util.stream.Stream;

/**
 * Created by Héctor on 25/06/2017.
 */
public class SessionService implements ISessionService {
    private final String TOKEN_COOKIE_NAME = "LedgertySessionId";

    private final String TOKEN_PRIVATE_PASSWORD = "#e9d&HmmmZtKa@AgzD";

    @Value("ledgerty.session.timeout")
    private Integer sessionTimeout = 30 * 60; //30 minutes

    private HttpServletRequest request;

    private HttpServletResponse response;

    private Cookie sessionTokenCookie;

    public Cookie getSessionTokenCookie() {
        return sessionTokenCookie;
    }

    public SessionService(HttpServletRequest request, HttpServletResponse response) {
        this.request = request;
        this.response = response;
    }

    /** ISessionService METHODS **/

    @Override
    public LedgertySessionToken generateToken(User userData) throws InvalidParametersException {
        ValidationUtils.validateNotNullOrEmpty(InvalidParametersException.class, userData, "User data is required");
        return new LedgertySessionToken(userData.getGuid(), new Date());
    }

    @Override
    public String encryptToken(LedgertySessionToken ledgertySessionToken) throws InvalidParametersException {
        ValidationUtils.validateNotNullOrEmpty(InvalidParametersException.class, ledgertySessionToken, "LedgertySessionToken is required");
        return doEncryptToken(ledgertySessionToken, buildSessionTokenCookie());
    }

    @Override
    public LedgertySessionToken decryptToken(String encryptedToken) throws InvalidParametersException, InvalidTokenException {
        ValidationUtils.validateNotNullOrEmpty(InvalidParametersException.class, encryptedToken, "The session ledgertySessionToken is required");
        try {
            return LedgertySessionToken.fromString(doDecryptToken(encryptedToken, getRequestTokenCookie(request)));
        } catch (Exception e) {
            throw new InvalidTokenException("You must provide a valid session ledgertySessionToken");
        }
    }

    /** FUNCTIONS **/

    private Cookie buildSessionTokenCookie() {
        sessionTokenCookie = buildSessionCookie();
        response.addCookie(sessionTokenCookie);
        return sessionTokenCookie;
    }

    private String doDecryptToken(String encryptedToken, Cookie tokenCookie) {
        BasicTextEncryptor encryptor = new BasicTextEncryptor();
        encryptor.setPassword(buildSessionTokenPassword(tokenCookie));
        return encryptor.decrypt(encryptedToken);
    }

    private Cookie getRequestTokenCookie(HttpServletRequest request) {
        Optional<Cookie> tokenCookie = Stream.of(request.getCookies()).filter((c -> c.getName().equals(TOKEN_COOKIE_NAME))).findFirst();
        if (tokenCookie.isPresent()) {
            return tokenCookie.get();
        } else {
            throw new InvalidTokenException("LedgertySessionToken cookie is required");
        }
    }

    private String doEncryptToken(LedgertySessionToken ledgertySessionToken, Cookie sessionCookie) {
        BasicTextEncryptor encryptor = new BasicTextEncryptor();
        encryptor.setPassword(buildSessionTokenPassword(sessionCookie));
        return encryptor.encrypt(ledgertySessionToken.toString());
    }

    private String buildSessionTokenPassword(Cookie sessionCookie) {
        return TOKEN_PRIVATE_PASSWORD + sessionCookie.getValue();
    }

    private Cookie buildSessionCookie() {
        Cookie sessionCookie = new Cookie(TOKEN_COOKIE_NAME, GuidUtils.generateShortGuid());
        sessionCookie.setMaxAge(sessionTimeout);
        sessionCookie.setSecure(false);
        return sessionCookie;
    }
}
