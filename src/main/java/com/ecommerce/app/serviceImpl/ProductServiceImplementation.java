package com.ecommerce.app.serviceImpl;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ecommerce.app.domain.Category;
import com.ecommerce.app.domain.Product;
import com.ecommerce.app.dto.ProductDto;
import com.ecommerce.app.exception.ProductException;
import com.ecommerce.app.repository.CategoryRepository;
import com.ecommerce.app.repository.ProductRepository;
import com.ecommerce.app.services.ProductService;

@Service
public class ProductServiceImplementation implements ProductService{

	@Autowired
	private ProductRepository productRepository;

	@Autowired
	private CategoryRepository categoryRepository;

	@Override
	public Product createProduct(ProductDto productDto) {

		Category firstLevel = categoryRepository.findByName(productDto.getFirstLavelCategory());

		if(firstLevel == null) {
			Category firstLevelCategory = new Category();
			firstLevelCategory.setName(productDto.getFirstLavelCategory());
			firstLevelCategory.setLevel(1);

			firstLevel = categoryRepository.save(firstLevelCategory);
		}

		Category secondLevel=categoryRepository.
				findByNameAndParant(productDto.getSecondLavelCategory(),firstLevel.getName());
		if(secondLevel==null) {
			Category secondLavelCategory=new Category();
			secondLavelCategory.setName(productDto.getSecondLavelCategory());
			secondLavelCategory.setParentCategory(secondLavelCategory);
			secondLavelCategory.setLevel(2);

			secondLevel= categoryRepository.save(secondLavelCategory);
		}

		Category thirdLevel=categoryRepository.
				findByNameAndParant(productDto.getThirdLavelCategory(),secondLevel.getName());
		if(thirdLevel==null) {

			Category thirdLavelCategory=new Category();
			thirdLavelCategory.setName(productDto.getThirdLavelCategory());
			thirdLavelCategory.setParentCategory(thirdLavelCategory);
			thirdLavelCategory.setLevel(3);

			thirdLevel= categoryRepository.save(thirdLavelCategory);
		}

		Product product = new Product();
		product.setTitle(productDto.getTitle());
		product.setQuantity(productDto.getQuantity());
		product.setColor(productDto.getColor());
		product.setBrand(productDto.getBrand());
		product.setDescription(productDto.getDescription());
		product.setDiscountedPrice(productDto.getDiscountedPrice());
		product.setDiscountPersent(productDto.getDiscountPersent());
		product.setCategory(thirdLevel);
		product.setImageUrl(productDto.getImageUrl());
		product.setCreatedAt(LocalDateTime.now());

		Product savedProduct = productRepository.save(product);
		System.out.println("$$$$$$$$$$$$$$$$$ Products:: "+ savedProduct);

		return savedProduct;

	}
	
	@Override
	public List<Product> getAllProducts() {
		return productRepository.findAll();
	}

	@Override
	public Product updateProduct(Product product, Long id) {
		Product updateProduct = productRepository.findById(id).get();
		if(product.getQuantity()!=0) {
			updateProduct.setQuantity(product.getQuantity());
		}
		if(product.getDescription()!=null) {
			updateProduct.setDescription(product.getDescription());
		}	
		
		return productRepository.save(product);
	}

	@Override
	public String deleteProduct(Long id) throws ProductException {
		
		Product product=productRepository.findById(id).get();
		
		System.out.println("delete product "+product.getId()+" - "+id);
		product.getSizes().clear();
		productRepository.delete(product);
		
		return "Product deleted Successfully";
	}
	


}
