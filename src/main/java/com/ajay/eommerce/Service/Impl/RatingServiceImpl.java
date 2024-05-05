package com.ajay.eommerce.Service.Impl;

import com.ajay.eommerce.Exception.ProductException;
import com.ajay.eommerce.Model.Product;
import com.ajay.eommerce.Model.Rating;
import com.ajay.eommerce.Model.User;
import com.ajay.eommerce.Repository.RatingRepository;
import com.ajay.eommerce.Request.RatingRequest;
import com.ajay.eommerce.Service.ProductService;
import com.ajay.eommerce.Service.RatingService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class RatingServiceImpl implements RatingService {
    private RatingRepository ratingRepository;
    private ProductService productService;
    public RatingServiceImpl(RatingRepository ratingRepository,ProductService productService) {
        this.ratingRepository=ratingRepository;
        this.productService=productService;
    }

    @Override
    public Rating createRating(RatingRequest req, User user) throws ProductException {
        Product product= productService.findProductById(req.getProductId());

        Rating rating=new Rating();
        rating.setProduct(product);
        rating.setUser(user);
        rating.setRating(req.getRating());
        rating.setCreatedAt(LocalDateTime .now());

        return ratingRepository.save(rating);
    }

    @Override
    public List<Rating> getProductsRating(Long productId) {
        return ratingRepository.getAllProductsRating(productId);
    }
}
