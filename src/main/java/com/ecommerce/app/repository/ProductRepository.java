package com.ecommerce.app.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ecommerce.app.domain.Product;

public interface ProductRepository extends JpaRepository<Product, Long>{

}
