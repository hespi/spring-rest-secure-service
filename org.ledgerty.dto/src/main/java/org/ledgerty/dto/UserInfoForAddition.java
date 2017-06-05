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
}
