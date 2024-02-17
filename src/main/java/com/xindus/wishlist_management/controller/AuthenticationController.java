package com.xindus.wishlist_management.controller;

import com.xindus.wishlist_management.model.AuthenticationResponse;
import com.xindus.wishlist_management.model.User;
import com.xindus.wishlist_management.service.AuthenticationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

// This is a RestController, which means it's a special type of Controller that includes @ResponseBody
@RestController
public class AuthenticationController {

    // We have an AuthenticationService that we'll use in this controller
    private final AuthenticationService authService;

    // This is our constructor. It gets an AuthenticationService when an AuthenticationController is created
    public AuthenticationController(AuthenticationService authService) {
        this.authService = authService;
    }

    // This is a method that handles POST requests to "/register"
    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody User request) {
        return ResponseEntity.ok(authService.register(request));
    }

    // This is a method that handles POST requests to "/login"
    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponse> login(
            @RequestBody User request
    ) {
        return ResponseEntity.ok(authService.authenticate(request));
    }
}