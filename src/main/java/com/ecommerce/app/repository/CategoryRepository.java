package com.ecommerce.app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.ecommerce.app.domain.Category;

public interface CategoryRepository extends JpaRepository<Category, Long>{

//	Category findByCategoryName(String categoryName);
	

//	@Query("Select c from Category c where c.categoryName.name=:categoryName")
//	Category findByNameAndParant(@Param("categoryName") String categoryName);

}
