package com.xindus.wishlist_management.repository;

import com.xindus.wishlist_management.model.User;
import com.xindus.wishlist_management.model.WishlistItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

// This is an interface for doing stuff with WishlistItems in the database
public interface WishlistRepository extends JpaRepository<WishlistItem, Long> {

    // This method finds all wishlist items for a user
    List<WishlistItem> findByUser(User user);
}
