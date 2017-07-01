package org.ledgerty.services.security;

import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.ledgerty.dao.User;
import org.springframework.security.authentication.AbstractAuthenticationToken;

import java.io.Serializable;
import java.text.ParseException;
import java.util.Arrays;
import java.util.Date;

/**
 * Created by Héctor on 17/06/2017.
 */
public class LedgertySessionToken extends AbstractAuthenticationToken implements Serializable {

    private User user;

    private String userId;

    private Date generationTime;

    public LedgertySessionToken(String userId, Date generationTime) {
        super(Arrays.asList(new Role("USER")));
        this.userId = userId;
        this.generationTime = generationTime;
    }

    public LedgertySessionToken() {
        super(Arrays.asList());
    }

    public String getUserId() {
        return userId;
    }

    public Date getGenerationTime() {
        return generationTime;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public User getUser() {
        return user;
    }

    @Override
    public Object getCredentials() {
        return (user != null) ? user.getPassword() : null;
    }

    @Override
    public Object getPrincipal() {
        return this.userId;
    }

    @Override
    public String toString() {
        return String.format("%s;%s", userId, DateFormatUtils.format(generationTime, DateFormatUtils.ISO_8601_EXTENDED_DATETIME_FORMAT.getPattern()));
    }

    public static LedgertySessionToken fromString(String tokenAsString) throws ParseException {
        String[] tokenParts = (tokenAsString != null) ? tokenAsString.split(";") : null;
        return (tokenParts != null && tokenParts.length == 2) ? new LedgertySessionToken(
                tokenParts[0],
                DateUtils.parseDate(tokenParts[1], DateFormatUtils.ISO_8601_EXTENDED_DATETIME_FORMAT.getPattern())
        ) : null;
    }
}
