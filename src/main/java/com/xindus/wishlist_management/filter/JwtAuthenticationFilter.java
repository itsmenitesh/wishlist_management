package com.xindus.wishlist_management.filter;

import com.xindus.wishlist_management.service.JwtService;
import com.xindus.wishlist_management.service.UserDetailsServiceImp;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import java.io.IOException;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    // We need these services to do authentication stuff
    private final JwtService jwtService;
    private final UserDetailsServiceImp userDetailsService;

    // Spring gives us the services here
    public JwtAuthenticationFilter(JwtService jwtService, UserDetailsServiceImp userDetailsService) {
        this.jwtService = jwtService;
        this.userDetailsService = userDetailsService;
    }

    // This method does the actual filtering
    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain)
            throws ServletException, IOException {

        // We get the Authorization header from the request
        String authHeader = request.getHeader("Authorization");

        // If there's no Authorization header or it doesn't start with "Bearer ", we just continue with the next filter
        if(authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request,response);
            return;
        }

        // We get the token from the Authorization header
        String token = authHeader.substring(7);
        // We get the username from the token
        String username = jwtService.extractUsername(token);

        // If we got a username and there's no authentication yet, we try to authenticate
        if(username != null && SecurityContextHolder.getContext().getAuthentication() == null) {

            // We get the user details
            UserDetails userDetails = userDetailsService.loadUserByUsername(username);

            // If the token is valid, we create an authentication token and set it in the SecurityContext
            if(jwtService.isValid(token, userDetails)) {
                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                        userDetails, null, userDetails.getAuthorities()
                );

                authToken.setDetails(
                        new WebAuthenticationDetailsSource().buildDetails(request)
                );

                SecurityContextHolder.getContext().setAuthentication(authToken);
            }
        }
        // We continue with the next filter
        filterChain.doFilter(request, response);
    }
}
