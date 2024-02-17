package com.xindus.wishlist_management.service;

import com.xindus.wishlist_management.model.User;
import com.xindus.wishlist_management.model.WishlistItem;
import com.xindus.wishlist_management.repository.WishlistRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WishlistService {

    // We need this to do stuff with WishlistItems in the database
    private final WishlistRepository wishlistRepository;

    // Spring gives us the WishlistRepository here
    public WishlistService(WishlistRepository wishlistRepository) {
        this.wishlistRepository = wishlistRepository;
    }

    // This method gets all wishlist items for a user
    public List<WishlistItem> getWishlist(User user) {
        return wishlistRepository.findByUser(user);
    }

    // This method adds an item to a user's wishlist
    public WishlistItem addWishlistItem(User user, WishlistItem item) {
        item.setUser(user);
        return wishlistRepository.save(item);
    }

    // This method deletes an item from a wishlist
    public String deleteWishlistItem(Long id) {
        wishlistRepository.deleteById(id);
        return "Item with "+ id +" is Deleted successfully!!!..";
    }
}
