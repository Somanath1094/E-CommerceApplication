package com.ecommerce.app.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ecommerce.app.domain.Review;

public interface ReviewRepository extends JpaRepository<Review, Long>{

}
