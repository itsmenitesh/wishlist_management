package com.xindus.wishlist_management.config;

import com.xindus.wishlist_management.model.Token;
import com.xindus.wishlist_management.repository.TokenRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutHandler;

@Configuration
public class CustomLogoutHandler implements LogoutHandler {

    // We need this to do stuff with Tokens in the database
    private final TokenRepository tokenRepository;

    // Spring gives us the TokenRepository here
    public CustomLogoutHandler(TokenRepository tokenRepository) {
        this.tokenRepository = tokenRepository;
    }

    // This method does the actual logout
    @Override
    public void logout(HttpServletRequest request,
                       HttpServletResponse response,
                       Authentication authentication) {
        // We get the Authorization header from the request
        String authHeader = request.getHeader("Authorization");

        // If there's no Authorization header or it doesn't start with "Bearer ", we just continue with the next filter
        if(authHeader == null || !authHeader.startsWith("Bearer ")) {
            return;
        }

        // We get the token from the Authorization header
        String token = authHeader.substring(7);
        // We try to find the token in the repository
        Token storedToken = tokenRepository.findByToken(token).orElse(null);

        // If we found the token, we set it to logged out and save it
        if(storedToken != null) {
            storedToken.setLoggedOut(true);
            tokenRepository.save(storedToken);
        }
    }
}
