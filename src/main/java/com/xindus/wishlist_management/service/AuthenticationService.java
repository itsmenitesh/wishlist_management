package com.xindus.wishlist_management.service;


import com.xindus.wishlist_management.model.AuthenticationResponse;
import com.xindus.wishlist_management.model.Token;
import com.xindus.wishlist_management.model.User;
import com.xindus.wishlist_management.repository.TokenRepository;
import com.xindus.wishlist_management.repository.UserRepository;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AuthenticationService {

    // We need these to do authentication stuff
    private final UserRepository repository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final TokenRepository tokenRepository;
    private final AuthenticationManager authenticationManager;

    // Spring gives us the stuff we need here
    public AuthenticationService(UserRepository repository,
                                 PasswordEncoder passwordEncoder,
                                 JwtService jwtService,
                                 TokenRepository tokenRepository,
                                 AuthenticationManager authenticationManager) {
        this.repository = repository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
        this.tokenRepository = tokenRepository;
        this.authenticationManager = authenticationManager;
    }

    // This method registers a user
    public String register(User request) {
        // If the user already exists, we just say so
        if(repository.findByUsername(request.getUsername()).isPresent()) {
            return "User already exist";
        }

        // We create a new user and save it
        User user = new User();
        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        user.setUsername(request.getUsername());
        user.setPassword(passwordEncoder.encode(request.getPassword()));

        user = repository.save(user);
        return  "User registration was successful";
    }

    // This method authenticates a user
    public AuthenticationResponse authenticate(User request) {
        // We try to authenticate the user
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getUsername(),
                        request.getPassword()
                )
        );

        // We get the user and generate a token for them
        User user = repository.findByUsername(request.getUsername()).orElseThrow();
        String jwt = jwtService.generateToken(user);

        // We revoke all other tokens for the user and save the new one
        revokeAllTokenByUser(user);
        saveUserToken(jwt, user);

        // We return the token
        return new AuthenticationResponse(jwt, "User login was successful");

    }
    // This method revokes all tokens for a user
    private void revokeAllTokenByUser(User user) {
        List<Token> validTokens = tokenRepository.findAllTokensByUser(user.getId());
        if(validTokens.isEmpty()) {
            return;
        }

        validTokens.forEach(t-> {
            t.setLoggedOut(true);
        });

        tokenRepository.saveAll(validTokens);
    }
    // This method saves a token for a user
    private void saveUserToken(String jwt, User user) {
        Token token = new Token();
        token.setToken(jwt);
        token.setLoggedOut(false);
        token.setUser(user);
        tokenRepository.save(token);
    }
}
