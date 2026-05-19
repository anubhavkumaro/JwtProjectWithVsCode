package com.example.service;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.example.entity.Product;
import com.example.repository.ProductRepository;

@Service
public class ProductServiceImplementation implements ProductService{


	ProductRepository productRepository;
	
	public ProductServiceImplementation(ProductRepository productRepository) {
		super();
		this.productRepository = productRepository;
	}
	
	@Value("${file.upload-dir}")
    private String uploadDir;
	private final String uploadDir1 = "uploads/images/";

	@Override
    public String addProduct(String name,String description,BigDecimal price,Integer stock,String category,MultipartFile file) throws IOException{

        String fileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();

        Path path = Paths.get(uploadDir);
        if (!Files.exists(path)) {
            Files.createDirectories(path);
        }

        Path filePath = path.resolve(fileName);
        Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

        Product product = new Product();
        product.setName(name);
        product.setDescription(description);
        product.setPrice(price);
        product.setStock(stock);
        product.setCategory(category);
        product.setImagePath(fileName);

         productRepository.save(product);
         return "ok";
        
    }
    
	@Override
	public String updateProduct(Long id, String name,String description,BigDecimal price,Integer stock,String category,MultipartFile image) {
		
		    Product product = productRepository.findById(id).orElse(null);
		    if(product == null) {
		    	return "Product Not Found";
		    }
		    
		    product.setName(name);
		    product.setDescription(description);
		    product.setPrice(price);
		    product.setStock(stock);
		    product.setCategory(category);
		    
		    if(image != null && !image.isEmpty()) {
		    	try {
		    		String oldImage = product.getImagePath();
		    		
		    		if(oldImage != null) {
		    			File oldFile = new File(uploadDir1 + oldImage);
		    			if(oldFile.exists()) {
		    				oldFile.delete();
		    			}
		    		}
		    		
		    		String newFileName = System.currentTimeMillis() + "_" + image.getOriginalFilename();
		    		Path path = Paths.get(uploadDir1 + newFileName);
		    		Files.createDirectories(path.getParent());
		    		Files.write(path, image.getBytes());
		    		
		    		product.setImagePath(newFileName);
		    	}
		    	catch(IOException e) {
		    		e.printStackTrace();
		    		return "Image Upload Failes";
		    	}
		    }
		    productRepository.save(product);
		return "success";
		 
	}

	@Override
	public String deleteProduct(Long pid) {
		Product p = productRepository.findById(pid).orElse(null);
		String imgUrl = p.getImagePath();
		if(imgUrl != null && !imgUrl.isEmpty()) {
			File imgFile = new File(uploadDir1 + imgUrl);
			if(imgFile.exists()) {
				imgFile.delete();
			}
		}
		
		productRepository.deleteById(pid);
		return "success";
	}

	@Override
	public Product viewProduct(Long pid) {
		return productRepository.findById(pid).orElseThrow();
	}

	@Override
	public List<Product> viewAllProduct() {
		return productRepository.findAll();
	}

}

