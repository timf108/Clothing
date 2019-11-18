package com.h2o.demo.model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Size;

@Entity
@Table(name = "cloth")
public class Clothing implements Serializable{
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
	
	private ClothingType type;
	private ClothingSize size;
	private ClothingColor color;
	private String brand;
	private int rating;

	public int getRating() {
		return rating;
	}

	public void setRating(int rating) {
		this.rating = rating;
	}

	private boolean isHotProduct;
	
	@Size(max = 2000)
	private String description;

	public Long getId() {
		return id;
	}
	
	public ClothingType getType() {
		return type;
	}

	public void setType(ClothingType type) {
		this.type = type;
	}

	public ClothingSize getSize() {
		return size;
	}

	public void setSize(ClothingSize size) {
		this.size = size;
	}

	public ClothingColor getColor() {
		return color;
	}

	public void setColor(ClothingColor color) {
		this.color = color;
	}

	public boolean isHotProduct() {
		return isHotProduct;
	}

	public void setHotProduct(boolean isHotProduct) {
		this.isHotProduct = isHotProduct;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
	public String getBrand() {
		return brand;
	}

	public void setBrand(String brand) {
		this.brand = brand;
	}
	
}
