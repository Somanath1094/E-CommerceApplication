package com.ecommerce.app.services;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.ecommerce.app.domain.Product;
import com.ecommerce.app.dto.ProductDTO;
import com.ecommerce.app.dto.ProductResponse;
import com.ecommerce.app.exception.ProductException;

public interface ProductService {

	Product createProduct(Long categoryId, ProductDTO productDto);

	List<Product> getAllProducts();

	Product updateProduct(Product req, Long id);

	String deleteProduct(Long productId) throws ProductException;

	Page<Product> findAll(Optional<String> searchText, Pageable pageable);
	
}
