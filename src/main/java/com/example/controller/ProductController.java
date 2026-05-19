package com.example.controller;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.entity.Product;
import com.example.service.ProductService;

	

@CrossOrigin(origins = "*")
@RestController
public class ProductController {
	
	ProductService productService;

	public ProductController(ProductService productService) {
		super();
		this.productService = productService;
	}
	
	@PostMapping("/addProduct")
    public String addProduct(
            @RequestParam String name,
            @RequestParam String description,
            @RequestParam BigDecimal price,
            @RequestParam Integer stock,
            @RequestParam String category,
            @RequestParam MultipartFile image) {


        	String s;
			try {
				s = productService.addProduct(name, description,price, stock,category,image);
				if(s.equals("ok")) {
	        		return "success";
	        	}
	        	else {
	        		return "fail";
	        	}
			} catch (IOException e) {
				return e.getMessage();
			}
        	
    }

	@PutMapping("/updateProduct/{pid}")
	public String updateProduct(
	        @PathVariable Long pid,
	        @RequestParam String name,
	        @RequestParam String description,
	        @RequestParam BigDecimal price,
	        @RequestParam Integer stock,
	        @RequestParam String category,
	        @RequestParam(required = false) MultipartFile image) {

	    try {
	        return productService.updateProduct(pid,name, description,price, stock,category,image);
	    } catch (IOException e) {
	        return e.getMessage();
	    }
	}

	@DeleteMapping("/deleteProduct/{pid}")
	public String deleteProduct(@PathVariable Long pid) {
		return productService.deleteProduct(pid);
	}

	@GetMapping("/viewProduct/{pid}")
	public Product viewProduct(@PathVariable Long pid) {
		return productService.viewProduct(pid);
	}

	@GetMapping("/viewAllProducts")
	public List<Product> viewAllProducts() {
		return productService.viewAllProduct();
	}
}

