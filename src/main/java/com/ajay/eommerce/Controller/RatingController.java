package com.ajay.eommerce.Controller;

import com.ajay.eommerce.Exception.ProductException;
import com.ajay.eommerce.Exception.UserException;
import com.ajay.eommerce.Model.Rating;
import com.ajay.eommerce.Model.User;
import com.ajay.eommerce.Request.RatingRequest;
import com.ajay.eommerce.Service.RatingService;
import com.ajay.eommerce.Service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class RatingController {
    private UserService userService;
    private RatingService ratingServices;

    public RatingController(UserService userService,RatingService ratingServices) {
        this.ratingServices=ratingServices;
        this.userService=userService;
        // TODO Auto-generated constructor stub
    }
    @PostMapping("/create")
    public ResponseEntity<Rating> createRatingHandler(@RequestBody RatingRequest req, @RequestHeader("Authotization") String jwt) throws UserException, ProductException {
        User user=userService.findUserProfileByJwt(jwt);
        Rating rating=ratingServices.createRating(req,user);
        return new ResponseEntity<>(rating, HttpStatus.ACCEPTED);
    }
    @GetMapping("/product/{productId}")
    public ResponseEntity<List<Rating>> getProductsReviewHandler(@PathVariable Long productId){

        List<Rating> ratings=ratingServices.getProductsRating(productId);
        return new ResponseEntity<>(ratings,HttpStatus.OK);
    }

}
