package com.xindus.wishlist_management.controller;

import com.xindus.wishlist_management.model.User;
import com.xindus.wishlist_management.model.WishlistItem;
import com.xindus.wishlist_management.service.WishlistService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api/wishlists")
public class WishlistController {

    // We need this service to do wishlist stuff
    private final WishlistService wishlistService;

    // Spring gives us the service here
    public WishlistController(WishlistService wishlistService) {
        this.wishlistService = wishlistService;
    }

    // This method gives us the wishlist for a user
    @GetMapping
    public ResponseEntity<List<WishlistItem>> getWishlist(Principal principal) {
        User user = (User) ((UsernamePasswordAuthenticationToken) principal).getPrincipal();
        return ResponseEntity.ok(wishlistService.getWishlist(user));
    }

    // This method adds an item to a user's wishlist
    @PostMapping
    public ResponseEntity<WishlistItem> addWishlistItem(@RequestBody WishlistItem item, Principal principal) {
        User user = (User) ((UsernamePasswordAuthenticationToken) principal).getPrincipal();
        return ResponseEntity.ok(wishlistService.addWishlistItem(user, item));
    }

    // This method deletes an item from a wishlist
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteWishlistItem(@PathVariable Long id) {
        String response = wishlistService.deleteWishlistItem(id);
        return ResponseEntity.ok(response);
    }
}

