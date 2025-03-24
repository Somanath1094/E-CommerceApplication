package com.ecommerce.app.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ecommerce.app.domain.Category;

public interface CategoryRepository extends JpaRepository<Category, Long>{

}
