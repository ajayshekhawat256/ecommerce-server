package com.ajay.eommerce.Service;

import com.ajay.eommerce.Exception.ProductException;
import com.ajay.eommerce.Request.RatingRequest;
import com.ajay.eommerce.Model.Rating;
import com.ajay.eommerce.Model.User;

import java.util.List;

public interface RatingService {
    public Rating createRating(RatingRequest req, User user) throws ProductException;

    public List<Rating> getProductsRating(Long productId);

}
