package com.ecommerce.app.services;

import com.ecommerce.app.domain.Product;
import com.ecommerce.app.dto.ProductDto;

public interface ProductService {

	Product createProduct(ProductDto productDto);

}
