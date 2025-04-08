package com.ecommerce.app.serviceImpl;

import java.util.List;
import java.util.stream.*;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;

import com.ecommerce.app.domain.Cart;
import com.ecommerce.app.domain.CartItem;
import com.ecommerce.app.domain.Product;
import com.ecommerce.app.dto.CartDTO;
import com.ecommerce.app.dto.CartItemDTO;
import com.ecommerce.app.dto.ProductDTO;
import com.ecommerce.app.exception.APIException;
import com.ecommerce.app.exception.ResourceNotFoundException;
import com.ecommerce.app.repository.CartItemRepository;
import com.ecommerce.app.repository.CartRepository;
import com.ecommerce.app.repository.ProductRepository;
import com.ecommerce.app.security.config.AuthUtil;
import com.ecommerce.app.services.CartService;

public class CartServiceImpl implements CartService{

	@Autowired
	private CartRepository cartRepository;

	@Autowired
	private AuthUtil authUtil;

	@Autowired
	ProductRepository productRepository;

	@Autowired
	CartItemRepository cartItemRepository;

	@Autowired
	ModelMapper modelMapper;

	@Override
	public String createOrUpdateCartWithItems(List<CartItemDTO> cartItems) {

		String emailId = authUtil.loggedInEmail();

		Cart existingCart = cartRepository.findCartByEmail(emailId);
		if(existingCart == null) {
			existingCart = new Cart();
			existingCart.setTotalPrice(0.00);
			existingCart.setUser(authUtil.loggedInUser());
			existingCart = cartRepository.save(existingCart);
		}else {
			cartRepository.deleteAllByCartId(existingCart.getCartId());
		}

		double totalPrice = 0.00;

		for(CartItemDTO cartItemDTO : cartItems) {
			Long productId = cartItemDTO.getProductId();
			Integer quantity = cartItemDTO.getQuantity();

			Product product = productRepository.findById(productId)
					.orElseThrow(() -> new ResourceNotFoundException("Product", "productId", productId));

			totalPrice +=product.getSpecialPrice() * quantity;

			CartItem cartItem = new CartItem();
			cartItem.setProduct(product);
			cartItem.setCart(existingCart);
			cartItem.setQuantity(quantity);
			cartItem.setProductPrice(product.getSpecialPrice());
			cartItem.setDiscount(product.getDiscount());
			cartItemRepository.save(cartItem);	
		}
		existingCart.setTotalPrice(totalPrice);
		cartRepository.save(existingCart);
		return "Cart updated with the new item successfully";

	}

	@Override
	public CartDTO addProductToCart(Long productId, Integer quantity) {
		Cart cart = createCart();
		
		Product product = productRepository.findById(productId)
				.orElseThrow(() -> new ResourceNotFoundException("Product", "productId", productId));
		
		CartItem cartItem = cartItemRepository.findCartItemByProductIdAndCartId(cart.getCartId(), productId);
		
		if(cartItem!= null) {
			 throw new APIException("Product " + product.getProductName() + " already exists in the cart");
		}
		
        if (product.getQuantity() == 0) {
            throw new APIException(product.getProductName() + " is not available");
        }
        
        if (product.getQuantity() < quantity) {
            throw new APIException("Please, make an order of the " + product.getProductName()
                    + " less than or equal to the quantity " + product.getQuantity() + ".");
        }
        
        CartItem newCartItem = new CartItem();

        newCartItem.setProduct(product);
        newCartItem.setCart(cart);
        newCartItem.setQuantity(quantity);
        newCartItem.setDiscount(product.getDiscount());
        newCartItem.setProductPrice(product.getSpecialPrice());

        cartItemRepository.save(newCartItem);

        product.setQuantity(product.getQuantity());

        cart.setTotalPrice(cart.getTotalPrice() + (product.getSpecialPrice() * quantity));

        cartRepository.save(cart);

        CartDTO cartDTO = modelMapper.map(cart, CartDTO.class);

        List<CartItem> cartItems = cart.getCartItems();

        Stream<ProductDTO> productStream = cartItems.stream().map(item -> {
            ProductDTO map = modelMapper.map(item.getProduct(), ProductDTO.class);
            map.setQuantity(item.getQuantity());
            return map;
        });

        cartDTO.setProducts(productStream.toList());

        return cartDTO;
	}

	private Cart createCart() {
		Cart userCart  = cartRepository.findCartByEmail(authUtil.loggedInEmail());
		if(userCart != null){
			return userCart;
		}

		Cart cart = new Cart();
		cart.setTotalPrice(0.00);
		cart.setUser(authUtil.loggedInUser());
		Cart newCart =  cartRepository.save(cart);

		return newCart;
	}

}
