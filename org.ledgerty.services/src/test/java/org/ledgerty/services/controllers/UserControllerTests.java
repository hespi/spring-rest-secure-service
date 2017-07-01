package org.ledgerty.services.controllers;

import org.junit.Test;
import org.ledgerty.dao.User;
import org.ledgerty.dto.in.UserCredentials;
import org.ledgerty.dto.in.UserInfoForAddition;
import org.ledgerty.dto.out.SessionInfo;
import org.ledgerty.dto.out.UserData;
import org.springframework.mock.web.MockHttpServletResponse;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Created by Héctor on 26/06/2017.
 */
public class UserControllerTests extends BaseControllerTests {

    public UserControllerTests() {
        this.controllerEndpointBase = "/users/";
    }

    @Test
    public void userControllerTests_When_UserIsNotLoggedIn_And_TriesToAccessSecuredService_Throws_UnauthorizedException() throws Exception {
        mockMvc.perform(get(this.controllerEndpointBase + "USER_ID/")
                .contentType(jsonContentType)
                .accept(jsonContentType))
                .andExpect(status().isForbidden());
    }

    @Test
    public void userControllerTests_When_UserIsNotLoggedIn_And_TriesToAccessNonSecuredService_Returns_Ok() throws Exception {
        mockMvc.perform(post(this.controllerEndpointBase)
                .content(serializeJson(new UserInfoForAddition("localhost", "First Name", "Last Name", "Email", "Password", "Phone Number")))
                .contentType(jsonContentType)
                .accept(jsonContentType))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName", is("First Name")))
                .andExpect(jsonPath("$.lastName", is("Last Name")))
                .andExpect(jsonPath("$.email", is("Email")))
                .andExpect(jsonPath("$.password", is("Password")))
                .andExpect(jsonPath("$.phoneNumber", is("First Name")))
                .andExpect(jsonPath("$.guid", not(isEmptyOrNullString())));
    }

    @Test
    public void userControllerTests_When_UserIsRegistered_And_Logged_And_TriesToAccessSecuredService_Returns_Ok() throws Exception {
        //Register
        MockHttpServletResponse response = mockMvc.perform(post(this.controllerEndpointBase)
                .content(serializeJson(new UserInfoForAddition("localhost", "First Name", "Last Name", "Email", "Password", "Phone Number")))
                .contentType(jsonContentType)
                .accept(jsonContentType))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName", is("First Name")))
                .andExpect(jsonPath("$.lastName", is("Last Name")))
                .andExpect(jsonPath("$.email", is("Email")))
                .andExpect(jsonPath("$.password", is("Password")))
                .andExpect(jsonPath("$.phoneNumber", is("Phone Number")))
                .andExpect(jsonPath("$.guid", not(isEmptyOrNullString()))).andReturn().getResponse();

        UserData addedUser = parseJson(UserData.class, response.getContentAsString());

        //Login
        response = mockMvc.perform(post(this.controllerEndpointBase + "login")
                .content(serializeJson(new UserCredentials(addedUser.getGuid(), "Password")))
                .contentType(jsonContentType)
                .accept(jsonContentType))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.userFirstName", is("First Name")))
                .andExpect(jsonPath("$.userLastName", is("Last Name")))
                .andExpect(jsonPath("$.userEmail", is("Email")))
                .andExpect(jsonPath("$.userExperience", is(0)))
                .andExpect(jsonPath("$.userLevel", is(1)))
                .andExpect(jsonPath("$.userCoins", is(0)))
                .andExpect(jsonPath("$.token", not(isEmptyOrNullString()))).andReturn().getResponse();

        SessionInfo userSession = parseJson(SessionInfo.class, response.getContentAsString());

        //Access secured end point
        mockMvc.perform(get(this.controllerEndpointBase + "USER_ID")
                .contentType(jsonContentType)
                .accept(jsonContentType)
                .header("Authorization", "Bearer " + userSession.getToken()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName", is("First Name")))
                .andExpect(jsonPath("$.lastName", is("Last Name")))
                .andExpect(jsonPath("$.email", is("Email")))
                .andExpect(jsonPath("$.password", is("Password")))
                .andExpect(jsonPath("$.phoneNumber", is("Phone Number")))
                .andExpect(jsonPath("$.guid", not(isEmptyOrNullString())));
    }
}
