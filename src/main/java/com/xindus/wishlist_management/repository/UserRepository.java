package com.xindus.wishlist_management.repository;

import com.xindus.wishlist_management.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

// This is an interface for doing stuff with Users in the database
public interface UserRepository extends JpaRepository<User, Integer> {

    // This method finds a user by their username
    Optional<User> findByUsername(String username);
}
