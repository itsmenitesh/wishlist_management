package com.xindus.wishlist_management.controller;

import com.xindus.wishlist_management.model.AuthenticationResponse;
import com.xindus.wishlist_management.model.User;
import com.xindus.wishlist_management.service.AuthenticationService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;
@SpringBootTest
public class AuthenticationControllerTest {

    @Mock
    private AuthenticationService authenticationService;

    @InjectMocks
    private AuthenticationController authenticationController;

    // This test checks if registration works
    @Test
    public void testRegister() {
        // We create a test user
        User user = new User();
        user.setUsername("test");

        when(authenticationService.register(user)).thenReturn("User registration was successful");

        ResponseEntity<String> result = authenticationController.register(user);
        assertEquals("User registration was successful", result.getBody());
    }

    // This test checks if login works
    @Test
    public void testLogin() {
        // We create a test user
        User user = new User();
        user.setUsername("test");
        user.setPassword("password");

        AuthenticationResponse response = new AuthenticationResponse("token", "User login was successful");

        when(authenticationService.authenticate(user)).thenReturn(response);
        ResponseEntity<AuthenticationResponse> result = authenticationController.login(user);
        assertEquals(response, result.getBody());
    }
}
