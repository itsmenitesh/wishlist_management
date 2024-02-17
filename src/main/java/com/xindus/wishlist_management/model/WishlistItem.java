package com.xindus.wishlist_management.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "wishlist")
public class WishlistItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "item_name")
    private String itemName;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;


}
