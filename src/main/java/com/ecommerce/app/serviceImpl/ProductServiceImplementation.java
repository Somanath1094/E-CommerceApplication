package com.ecommerce.app.serviceImpl;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.ecommerce.app.domain.Category;
import com.ecommerce.app.domain.Product;
import com.ecommerce.app.dto.ProductDTO;
import com.ecommerce.app.exception.APIException;
import com.ecommerce.app.exception.ProductException;
import com.ecommerce.app.exception.ResourceNotFoundException;
import com.ecommerce.app.repository.CategoryRepository;
import com.ecommerce.app.repository.ProductRepository;
import com.ecommerce.app.services.ProductService;

import jakarta.transaction.Transactional;

@Service
public class ProductServiceImplementation implements ProductService{

	@Autowired
	private ProductRepository productRepository;

	@Autowired
	private CategoryRepository categoryRepository;

	@Override
	public Product createProduct(Long categoryId, ProductDTO productDTO) throws APIException{

		Category category = categoryRepository.findById(categoryId)
				.orElseThrow(() ->
				new ResourceNotFoundException("Category", "categoryId", categoryId));

		boolean isProductNotPresent = true;

		List<Product> products = category.getProducts();
		for (Product value : products) {
			System.out.println("$$$$$$$$$$$$ Value:: "+ value.getProductName());
			if (value.getProductName().equals(productDTO.getProductName())) {
				isProductNotPresent = false;
				break;
			}

		}

		if(isProductNotPresent) {
			Product product = new Product();
			product.setId(productDTO.getId());
			product.setProductName(productDTO.getProductName());
			product.setQuantity(productDTO.getQuantity());
			product.setColor(productDTO.getColor());
			product.setBrand(productDTO.getBrand());
			product.setDescription(productDTO.getDescription());
			product.setPrice(productDTO.getPrice());
			product.setDiscount(productDTO.getDiscount());
			product.setSpecialPrice(productDTO.getSpecialPrice());
			product.setCategory(category);
			product.setImageUrl(productDTO.getImageUrl());
			product.setCreatedAt(LocalDateTime.now());
			Product savedProduct = productRepository.save(product);
			System.out.println("&&&&&&&&&&&&&&&&&&&&&&S Products:: "+ savedProduct.getProductName());
			return savedProduct;
		} else {
			throw new APIException("Product already exist!!");
		}

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

	@Override
	@Transactional
	public Page<Product> findAll(Optional<String> searchText, Pageable pageable) {
		Page<Product> page = null;
		if(searchText.isPresent()) {
			page = productRepository.getAllOrBySearchText(searchText.get(),pageable);		
			return page;
		}
		return productRepository.findAll(pageable);
	}




}
