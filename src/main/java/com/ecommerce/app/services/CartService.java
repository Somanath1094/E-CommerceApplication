package com.ecommerce.app.services;

import java.util.List;

import com.ecommerce.app.dto.CartDTO;
import com.ecommerce.app.dto.CartItemDTO;

public interface CartService {

	String createOrUpdateCartWithItems(List<CartItemDTO> cartItems);

	CartDTO addProductToCart(Long productId, Integer quantity);

}
