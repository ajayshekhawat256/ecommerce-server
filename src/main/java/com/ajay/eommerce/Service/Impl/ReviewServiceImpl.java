package com.ajay.eommerce.Service.Impl;

import com.ajay.eommerce.Exception.ProductException;
import com.ajay.eommerce.Model.Product;
import com.ajay.eommerce.Model.Review;
import com.ajay.eommerce.Model.User;
import com.ajay.eommerce.Repository.ProductRepository;
import com.ajay.eommerce.Repository.ReviewRepository;
import com.ajay.eommerce.Request.ReviewRequest;
import com.ajay.eommerce.Service.ProductService;
import com.ajay.eommerce.Service.ReviewService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ReviewServiceImpl implements ReviewService {
    private ReviewRepository reviewRepository;
    private ProductService productService;
    private ProductRepository productRepository;

    public ReviewServiceImpl(ReviewRepository reviewRepository,ProductService productService,ProductRepository productRepository) {
        this.reviewRepository=reviewRepository;
        this.productService=productService;
        this.productRepository=productRepository;
    }
    @Override
    public Review createReview(ReviewRequest req, User user) throws ProductException {
        Product product=productService.findProductById(req.getProductId());
        Review review=new Review();
        review.setUser(user);
        review.setProduct(product);
        review.setReview(req.getReview());
        review.setCreatedAt(LocalDateTime.now());

//		product.getReviews().add(review);
        productRepository.save(product);
        return reviewRepository.save(review);
    }

    @Override
    public List<Review> getAllReview(Long productId) {
        return reviewRepository.getAllProductsReview(productId);
    }
}
