package com.ecommerce.app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.ecommerce.app.domain.Cart;

public interface CartRepository extends JpaRepository<Cart, Long>{

    @Query("SELECT c FROM Cart c WHERE c.user.email = ?1")
    Cart findCartByEmail(String email);

    @Modifying
    @Query("DELETE FROM CartItem ci WHERE ci.cart.id = ?1")
	void deleteAllByCartId(Long cartId);
	
	
	

}
