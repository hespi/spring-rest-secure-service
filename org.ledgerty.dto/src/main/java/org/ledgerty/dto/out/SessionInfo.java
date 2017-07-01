package org.ledgerty.dto.out;

import org.codehaus.jackson.annotate.JsonProperty;

import java.io.Serializable;

/**
 * Created by Héctor on 16/06/2017.
 */
public class SessionInfo implements Serializable {

    @JsonProperty("UserId")
    private String userId;

    @JsonProperty("UserFirstName")
    private String userFirstName;

    @JsonProperty("UserLastName")
    private String userLastName;

    @JsonProperty("UserEmail")
    private String userEmail;

    @JsonProperty("UserExperience")
    private Integer userExperience;

    @JsonProperty("UserLevel")
    private Integer userLevel;

    @JsonProperty("UserCoins")
    private Integer userCoins;

    @JsonProperty("Token")
    private String token;

    public SessionInfo(String userId, String userFirstName, String userLastName, String userEmail, Integer userExperience, Integer userLevel, Integer userCoins, String token) {
        this.userId = userId;
        this.userFirstName = userFirstName;
        this.userLastName = userLastName;
        this.userEmail = userEmail;
        this.userExperience = userExperience;
        this.userLevel = userLevel;
        this.userCoins = userCoins;
        this.token = token;
    }

    public SessionInfo() {
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserFirstName() {
        return userFirstName;
    }

    public void setUserFirstName(String userFirstName) {
        this.userFirstName = userFirstName;
    }

    public String getUserLastName() {
        return userLastName;
    }

    public void setUserLastName(String userLastName) {
        this.userLastName = userLastName;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public Integer getUserExperience() {
        return userExperience;
    }

    public void setUserExperience(Integer userExperience) {
        this.userExperience = userExperience;
    }

    public Integer getUserLevel() {
        return userLevel;
    }

    public void setUserLevel(Integer userLevel) {
        this.userLevel = userLevel;
    }

    public Integer getUserCoins() {
        return userCoins;
    }

    public void setUserCoins(Integer userCoins) {
        this.userCoins = userCoins;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
