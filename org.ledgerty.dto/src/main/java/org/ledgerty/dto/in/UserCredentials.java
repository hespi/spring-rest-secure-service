package org.ledgerty.dto.in;

import org.codehaus.jackson.annotate.JsonProperty;

import java.io.Serializable;

/**
 * Created by Héctor on 16/06/2017.
 */
public class UserCredentials implements Serializable {

    @JsonProperty("login")
    private String login;

    @JsonProperty("password")
    private String password;

    public UserCredentials() {

    }

    public UserCredentials(String login, String password) {
        this.login = login;
        this.password = password;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
