package com.ecommerce.app.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ecommerce.app.domain.Rating;

public interface RatingRepository extends JpaRepository<Rating, Long>{

}
