package com.xindus.wishlist_management.controller;

import com.xindus.wishlist_management.model.User;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import com.xindus.wishlist_management.model.WishlistItem;
import com.xindus.wishlist_management.service.WishlistService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@SpringBootTest
public class WishlistControllerTest {

    @Mock
    private WishlistService wishlistService;

    @InjectMocks
    private WishlistController wishlistController;

    // This test checks if getting a wishlist works
    @Test
    public void testGetWishlist() {
        User user = new User();
        user.setUsername("test");

        List<WishlistItem> wishlistItems = new ArrayList<>();
        WishlistItem item = new WishlistItem();
        item.setItemName("test item");
        wishlistItems.add(item);

        when(wishlistService.getWishlist(user)).thenReturn(wishlistItems);

        Authentication auth = new UsernamePasswordAuthenticationToken(user, null);
        SecurityContextHolder.getContext().setAuthentication(auth);

        ResponseEntity<List<WishlistItem>> result = wishlistController.getWishlist(auth);
        assertEquals(wishlistItems, result.getBody());
    }

    // This test checks if adding a wishlist item works
    @Test
    public void testAddWishlistItem() {
        User user = new User();
        user.setUsername("test");

        WishlistItem item = new WishlistItem();
        item.setItemName("test item");

        when(wishlistService.addWishlistItem(user, item)).thenReturn(item);

        // We set the authentication in the SecurityContext
        Authentication auth = new UsernamePasswordAuthenticationToken(user, null);
        SecurityContextHolder.getContext().setAuthentication(auth);

        ResponseEntity<WishlistItem> result = wishlistController.addWishlistItem(item, auth);

        assertEquals(item, result.getBody());
    }

    // This test checks if deleting a wishlist item works
    @Test
    public void testDeleteWishlistItem() {
        Long id = 1L;

        when(wishlistService.deleteWishlistItem(id)).thenReturn("Item with "+ id +" is Deleted successfully!!!..");

        ResponseEntity<String> result = wishlistController.deleteWishlistItem(id);
        assertEquals("Item with "+ id +" is Deleted successfully!!!..", result.getBody());
    }
}

