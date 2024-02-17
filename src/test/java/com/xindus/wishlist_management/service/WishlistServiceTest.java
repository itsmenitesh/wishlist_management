package com.xindus.wishlist_management.service;

import com.xindus.wishlist_management.model.User;
import com.xindus.wishlist_management.model.WishlistItem;
import com.xindus.wishlist_management.repository.WishlistRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
@SpringBootTest
public class WishlistServiceTest {

    // We're going to pretend to be this repository
    @Mock
    private WishlistRepository wishlistRepository;

    // We're testing this service
    @InjectMocks
    private WishlistService wishlistService;

    // This test checks if getting a wishlist works
    @Test
    public void testGetWishlist() {
        // We create a test user and a test wishlist
        User user = new User();
        user.setUsername("test");

        List<WishlistItem> wishlistItems = new ArrayList<>();
        WishlistItem item = new WishlistItem();
        item.setItemName("test item");
        wishlistItems.add(item);

        when(wishlistRepository.findByUser(user)).thenReturn(wishlistItems);

        List<WishlistItem> result = wishlistService.getWishlist(user);

        assertEquals(wishlistItems, result);
    }

    // This test checks if adding a wishlist item works
    @Test
    public void testAddWishlistItem() {
        User user = new User();
        user.setUsername("test");

        WishlistItem item = new WishlistItem();
        item.setItemName("test item");

        when(wishlistRepository.save(any(WishlistItem.class))).thenReturn(item);

        WishlistItem result = wishlistService.addWishlistItem(user, item);

        assertEquals(item, result);
    }

    // This test checks if deleting a wishlist item works
    @Test
    public void testDeleteWishlistItem() {
        Long id = 1L;

        doNothing().when(wishlistRepository).deleteById(id);

        String result = wishlistService.deleteWishlistItem(id);

        assertEquals("Item with "+ id +" is Deleted successfully!!!..", result);

        verify(wishlistRepository, times(1)).deleteById(id);
    }
}
