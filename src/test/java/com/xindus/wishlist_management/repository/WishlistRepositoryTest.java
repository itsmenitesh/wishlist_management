package com.xindus.wishlist_management.repository;

import com.xindus.wishlist_management.model.User;
import com.xindus.wishlist_management.model.WishlistItem;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest
public class WishlistRepositoryTest {

    @Mock
    private WishlistRepository wishlistRepository;

    // This test checks if finding wishlist items by user works
    @Test
    public void testFindByUser() {
        User user = new User();
        user.setUsername("test");

        // We create a test wishlist
        List<WishlistItem> wishlistItems = new ArrayList<>();
        WishlistItem item = new WishlistItem();
        item.setItemName("test item");
        wishlistItems.add(item);

        when(wishlistRepository.findByUser(user)).thenReturn(wishlistItems);
        List<WishlistItem> result = wishlistRepository.findByUser(user);
        assertEquals(wishlistItems, result);
    }

    // This test checks if saving a wishlist item works
    @Test
    public void testSaveWishlistItem() {
        User user = new User();
        user.setUsername("test");

        WishlistItem item = new WishlistItem();
        item.setItemName("test item");
        when(wishlistRepository.save(any(WishlistItem.class))).thenReturn(item);

        WishlistItem result = wishlistRepository.save(item);

        assertEquals(item, result);
    }
}
