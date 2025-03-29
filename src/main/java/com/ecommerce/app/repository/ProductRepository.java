package com.ecommerce.app.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.ecommerce.app.domain.Product;
import com.ecommerce.app.dto.ProductResponse;

public interface ProductRepository extends JpaRepository<Product, Long>{

	@Query(value = "SELECT p FROM Product p WHERE p.productName=?1 OR p.category.categoryName=?1")
	Page<Product> getAllOrBySearchText(String string, Pageable pg);


}
