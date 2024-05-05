package com.ajay.eommerce.Controller;

import com.ajay.eommerce.Exception.ProductException;
import com.ajay.eommerce.Model.Product;
import com.ajay.eommerce.Request.CreateProductRequest;
import com.ajay.eommerce.Response.ApiResponse;
import com.ajay.eommerce.Service.ProductService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/admin/products")
public class AdminProductController {
    private ProductService productService;

    public AdminProductController(ProductService productService) {
        this.productService = productService;
    }
    @PostMapping("/")
    public ResponseEntity<Product> createProductHandler(@RequestBody CreateProductRequest req) throws ProductException {
        Product createdProduct = productService.createProduct(req);
        return new ResponseEntity<Product>(createdProduct, HttpStatus.ACCEPTED);
    }

    @PostMapping("/creates")
    public ResponseEntity<ApiResponse> createMultipleProduct(@RequestBody CreateProductRequest[] reqs)throws ProductException{
        for(CreateProductRequest product:reqs){
            productService.createProduct(product);
        }
        ApiResponse res=new ApiResponse("products created Successfully",true);
        return new ResponseEntity<ApiResponse>(res,HttpStatus.ACCEPTED);
    }
}
