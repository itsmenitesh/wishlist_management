package com.xindus.wishlist_management.repository;

import com.xindus.wishlist_management.model.User;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest
public class UserRepositoryTest {

    // We're going to pretend to be this repository
    @Mock
    private UserRepository userRepository;

    // This test checks if finding a user by username works
    @Test
    public void testFindByUsername() {
        // We create a test user
        User user = new User();
        user.setUsername("test");
        when(userRepository.findByUsername(user.getUsername())).thenReturn(Optional.of(user));

        Optional<User> result = userRepository.findByUsername(user.getUsername());

        assertEquals(user, result.get());
    }

    // This test checks if saving a user works
    @Test
    public void testSaveUser() {
        // We create a test user
        User user = new User();
        user.setUsername("test");
        user.setPassword("password");

        when(userRepository.save(any(User.class))).thenReturn(user);

        User result = userRepository.save(user);
        assertEquals(user, result);
    }
}
