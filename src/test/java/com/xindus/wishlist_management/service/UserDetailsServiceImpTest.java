package com.xindus.wishlist_management.service;

import com.xindus.wishlist_management.model.User;
import com.xindus.wishlist_management.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UserDetailsServiceImpTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserDetailsServiceImp userDetailsService;

    // This test checks if loading a user by username works
    @Test
    public void testLoadUserByUsername() {
        User user = new User();
        user.setUsername("test");

        when(userRepository.findByUsername(user.getUsername())).thenReturn(Optional.of(user));
        UserDetails result = userDetailsService.loadUserByUsername(user.getUsername());
        assertEquals(user.getUsername(), result.getUsername());
    }

    // This test checks if loading a user by username throws an exception when the user is not found
    @Test
    public void testLoadUserByUsernameNotFound() {
        String username = "test";
        when(userRepository.findByUsername(username)).thenReturn(Optional.empty());
        assertThrows(UsernameNotFoundException.class, () -> userDetailsService.loadUserByUsername(username));
    }
}
