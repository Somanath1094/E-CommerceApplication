package com.ecommerce.app.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ecommerce.app.domain.Product;
import com.ecommerce.app.dto.ProductDto;
import com.ecommerce.app.services.ProductService;

import ch.qos.logback.core.model.processor.ProcessorException;

@RestController
@RequestMapping("/api")
public class ProductController {

	@Autowired
	private ProductService productService;
	
	
	@PostMapping("/createProduct")
	public ResponseEntity<Product> createAdminProduct(@RequestBody ProductDto productDto) throws ProcessorException{
		
		Product product = productService.createProduct(productDto);
		return new ResponseEntity<Product>(product, HttpStatus.ACCEPTED);
	}
}
