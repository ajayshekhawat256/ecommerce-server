package com.ajay.eommerce.Service;

import com.ajay.eommerce.Exception.ProductException;
import com.ajay.eommerce.Request.CreateProductRequest;
import com.ajay.eommerce.Model.Product;
import org.springframework.data.domain.Page;

import java.util.List;

public interface ProductService {
    public Product createProduct(CreateProductRequest req);
    public String deleteProduct(Long productId) throws ProductException;
    public Product updateProduct(Long productId,Product product) throws ProductException;
    public Product findProductById(Long id) throws ProductException;
    public List<Product> findProductByCategory(String category);
    public List<Product> searchProduct(String query);
    public Page<Product> getAllProduct(String category,List<String>colors,
                                       List<String>sizes,Integer minPrice,Integer maxPrice,
                                       Integer minDiscount,String sort, String stock,
                                       Integer pageNumber, Integer pageSize
    );
}
