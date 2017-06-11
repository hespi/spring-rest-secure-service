package org.ledgerty.dto;

import org.codehaus.jackson.annotate.JsonProperty;

import java.io.Serializable;

/**
 * Created by Héctor on 05/06/2017.
 */
public class UserInfoForAddition implements Serializable {
    @JsonProperty("CreationIP")
    private String creationIP;
    @JsonProperty("FirstName")
    private String firstName;
    @JsonProperty("LastName")
    private String lastName;
    @JsonProperty("Email")
    private String email;
    @JsonProperty("Password")
    private String password;
    @JsonProperty("PhoneNumber")
    private String phoneNumber;

    public UserInfoForAddition(String creationIP, String firstName, String lastName, String email, String password, String phoneNumber) {
        this.creationIP = creationIP;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.phoneNumber = phoneNumber;
    }

    public String getCreationIP() {
        return creationIP;
    }

    public void setCreationIP(String creationIP) {
        this.creationIP = creationIP;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}
