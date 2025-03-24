package com.ecommerce.app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.ecommerce.app.domain.Category;

public interface CategoryRepository extends JpaRepository<Category, Long>{

	Category findByName(String name);
	

	@Query("Select c from Category c where c.name=:name AND c.parentCategory.name=:parentCategoryName")
	Category findByNameAndParant(@Param("name") String name, @Param("parentCategoryName")String parentCategoryName);

}
