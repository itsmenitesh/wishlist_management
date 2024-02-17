package com.xindus.wishlist_management.service;

import com.xindus.wishlist_management.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImp implements UserDetailsService {

    // We need this to do stuff with Users in the database
    private final UserRepository repository;

    // Spring gives us the UserRepository here
    public UserDetailsServiceImp(UserRepository repository) {
        this.repository = repository;
    }

    // This method loads a user by their username
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // We try to find the user in the repository
        // If we can't find them, we throw an exception
        return repository.findByUsername(username)
                .orElseThrow(()-> new UsernameNotFoundException("User not found"));
    }
}
