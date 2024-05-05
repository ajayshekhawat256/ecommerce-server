package com.ajay.eommerce.Service;

import com.ajay.eommerce.Exception.ProductException;
import com.ajay.eommerce.Model.Review;
import com.ajay.eommerce.Model.User;
import com.ajay.eommerce.Request.ReviewRequest;

import java.util.List;

public interface ReviewService {

    public Review createReview(ReviewRequest req, User user) throws ProductException;

    public List<Review> getAllReview(Long productId);
}
