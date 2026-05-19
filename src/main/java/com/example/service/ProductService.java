package com.example.service;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.example.entity.Product;


public interface ProductService {
	String updateProduct(Long id, String name,String description,BigDecimal price,Integer stock,String category,MultipartFile file) throws IOException;
	String deleteProduct(Long pid);
	Product viewProduct(Long pid);
	List<Product> viewAllProduct();
	String addProduct(String name,String description,BigDecimal price,Integer stock,String category,MultipartFile file) throws IOException;
}
