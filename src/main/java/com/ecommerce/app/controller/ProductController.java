package com.ecommerce.app.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ecommerce.app.domain.Product;
import com.ecommerce.app.dto.ApiResponse;
import com.ecommerce.app.dto.ProductDTO;
import com.ecommerce.app.exception.ProductException;
import com.ecommerce.app.services.ProductService;

import ch.qos.logback.core.model.processor.ProcessorException;

@RestController
@RequestMapping("/api")
public class ProductController {

	@Autowired
	private ProductService productService;
	
	
	@PostMapping("/createProduct/{categoryId}")
	public ResponseEntity<Product> createAdminProduct(@RequestBody ProductDTO productDto, @PathVariable Long categoryId) throws ProcessorException{
		
		Product product = productService.createProduct(categoryId,productDto);
		return new ResponseEntity<>(product, HttpStatus.ACCEPTED);
	}
	
	@PutMapping("/updateByProductId/{id}")
	public ResponseEntity<Product> updateProductHandler(@RequestBody Product req,@PathVariable Long id) throws ProductException{
		
		Product updatedProduct=productService.updateProduct(req, id);
		
		return new ResponseEntity<Product>(updatedProduct,HttpStatus.OK);
	}
	
	@DeleteMapping("/deleteByProductId/{id}")
	public ResponseEntity<ApiResponse> deleteProductHandler(@PathVariable Long id) throws ProductException{
		System.out.println("dlete product controller .... ");
		String msg=productService.deleteProduct(id);
		System.out.println("dlete product controller .... msg "+msg);
		ApiResponse res=new ApiResponse(msg,true);
		
		return new ResponseEntity<ApiResponse>(res,HttpStatus.ACCEPTED);
		
	}

	@GetMapping("/public/products")
	public ResponseEntity<Page<Product>> getAllProducts(Optional<String> searchText, Pageable pageable) {
		Page<Product> page = productService.findAll(searchText,pageable);
		return ResponseEntity.ok(page);
	}

    
}
