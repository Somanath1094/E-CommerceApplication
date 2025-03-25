package com.ecommerce.app.services;

import java.util.List;

import com.ecommerce.app.domain.Product;
import com.ecommerce.app.dto.ProductDto;
import com.ecommerce.app.exception.ProductException;

public interface ProductService {

	Product createProduct(ProductDto productDto);

	List<Product> getAllProducts();

	Product updateProduct(Product req, Long id);

	String deleteProduct(Long productId) throws ProductException;

}
