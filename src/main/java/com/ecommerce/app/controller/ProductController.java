package com.ecommerce.app.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
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
import com.ecommerce.app.dto.ProductDto;
import com.ecommerce.app.exception.ProductException;
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
	
	@GetMapping("/getAllProduct")
	public ResponseEntity<List<Product>> findAllProduct(){
		
		List<Product> products = productService.getAllProducts();
		
		return new ResponseEntity<List<Product>>(products,HttpStatus.OK);
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
}
