package com.xindus.wishlist_management.repository;


import com.xindus.wishlist_management.model.Token;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

// This is an interface for doing stuff with Tokens in the database
public interface TokenRepository extends JpaRepository<Token, Integer> {

    // This method gets all the tokens for a user that are not logged out
    @Query("""
    select t from Token t inner join User u on t.user.id = u.id
    where t.user.id = :userId and t.loggedOut = false
    """)
    List<Token> findAllTokensByUser(Integer userId);

    // This method finds a token by its value
    Optional<Token> findByToken(String token);
}
