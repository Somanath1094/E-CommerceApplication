package com.ecommerce.app.dto;

import java.util.HashSet;
import java.util.Set;
import com.ecommerce.app.domain.Size;

public class ProductDTO {

	private Long id;
	
	private String productName;

	private String description;

	private int price;

	private int discountedPrice;

	private int discountPersent;

	private int quantity;

	private String brand;

	private String color;

	private Set<Size> size=new HashSet<>();

	private String imageUrl;

	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public int getPrice() {
		return price;
	}
	public void setPrice(int price) {
		this.price = price;
	}
	public int getDiscountedPrice() {
		return discountedPrice;
	}
	public void setDiscountedPrice(int discountedPrice) {
		this.discountedPrice = discountedPrice;
	}
	public int getDiscountPersent() {
		return discountPersent;
	}
	public void setDiscountPersent(int discountPersent) {
		this.discountPersent = discountPersent;
	}
	public int getQuantity() {
		return quantity;
	}
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	public String getBrand() {
		return brand;
	}
	public void setBrand(String brand) {
		this.brand = brand;
	}
	public String getColor() {
		return color;
	}
	public void setColor(String color) {
		this.color = color;
	}
	public Set<Size> getSize() {
		return size;
	}
	public void setSize(Set<Size> size) {
		this.size = size;
	}
	public String getImageUrl() {
		return imageUrl;
	}
	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}
	

}
