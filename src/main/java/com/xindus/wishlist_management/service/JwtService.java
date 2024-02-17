package com.xindus.wishlist_management.service;


import com.xindus.wishlist_management.model.User;
import com.xindus.wishlist_management.repository.TokenRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.function.Function;

@Service
public class JwtService {

    // This is our secret key for signing tokens
    private final String SECRET_KEY = "4bb6d1dfbafb64a681139d1586b6f1160d18159afd57c8c79136d7490630407c";
    private final TokenRepository tokenRepository;

    // Spring gives us the TokenRepository here
    public JwtService(TokenRepository tokenRepository) {
        this.tokenRepository = tokenRepository;
    }

    // This method gets the username from a token
    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    // This method checks if a token is valid
    public boolean isValid(String token, UserDetails user) {
        String username = extractUsername(token);

        // We check if the token is valid
        boolean validToken = tokenRepository
                .findByToken(token)
                .map(t -> !t.isLoggedOut())
                .orElse(false);

        // We return true if the username matches, the token is not expired, and the token is valid
        return (username.equals(user.getUsername())) && !isTokenExpired(token) && validToken;
    }

    // This method checks if a token is expired
    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    // This method gets the expiration date from a token
    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    // This method gets a claim from a token
    public <T> T extractClaim(String token, Function<Claims, T> resolver) {
        Claims claims = extractAllClaims(token);
        return resolver.apply(claims);
    }

    // This method gets all claims from a token
    private Claims extractAllClaims(String token) {
        return Jwts
                .parser()
                .verifyWith(getSigninKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    // This method generates a token for a user
    public String generateToken(User user) {
        String token = Jwts
                .builder()
                .subject(user.getUsername())
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + 24*60*60*1000 ))
                .signWith(getSigninKey())
                .compact();

        return token;
    }

    // This method gets the signing key
    private SecretKey getSigninKey() {
        byte[] keyBytes = Decoders.BASE64URL.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}
