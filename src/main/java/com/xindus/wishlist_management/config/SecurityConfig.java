package com.xindus.wishlist_management.config;

import com.xindus.wishlist_management.filter.JwtAuthenticationFilter;
import com.xindus.wishlist_management.service.UserDetailsServiceImp;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    // We need these to do security stuff
    private final UserDetailsServiceImp userDetailsServiceImp;
    private final JwtAuthenticationFilter jwtAuthenticationFilter;
    private final CustomLogoutHandler logoutHandler;

    // Spring gives us the stuff we need here
    public SecurityConfig(UserDetailsServiceImp userDetailsServiceImp,
                          JwtAuthenticationFilter jwtAuthenticationFilter,
                          CustomLogoutHandler logoutHandler) {
        this.userDetailsServiceImp = userDetailsServiceImp;
        this.jwtAuthenticationFilter = jwtAuthenticationFilter;
        this.logoutHandler = logoutHandler;
    }

    // This method sets up the security filter chain
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        // We configure HTTP security here
        return http
                // We disable CSRF
                .csrf(AbstractHttpConfigurer::disable)
                // We set up request authorization
                .authorizeHttpRequests(
                        req->req.requestMatchers("/login/**","/register/**")
                                // We allow all requests to /login/** and /register/**
                                .permitAll()
                                // We require authentication for any other request
                                .anyRequest()
                                .authenticated()
                )
                // We set the user details service
                .userDetailsService(userDetailsServiceImp)
                // We set the session management policy to stateless
                .sessionManagement(session->session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                // We add the JWT authentication filter
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
                // We handle exceptions
                .exceptionHandling(
                        e->e.accessDeniedHandler(
                                        (request, response, accessDeniedException)->response.setStatus(403)
                                )
                                // We set the authentication entry point
                                .authenticationEntryPoint(new HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED)))
                // We configure logout
                .logout(l->l
                        // We set the logout URL to /logout
                        .logoutUrl("/logout")
                        // We add the custom logout handler
                        .addLogoutHandler(logoutHandler)
                        // We set the logout success handler to clear the security context
                        .logoutSuccessHandler((request, response, authentication) -> SecurityContextHolder.clearContext()
                        ))
                // We build the security filter chain
                .build();

    }

    // This method sets up the password encoder
    @Bean
    public PasswordEncoder passwordEncoder() {
        // We use BCryptPasswordEncoder
        return new BCryptPasswordEncoder();
    }

    // This method sets up the authentication manager
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        // We get the authentication manager from the configuration
        return configuration.getAuthenticationManager();
    }
}
