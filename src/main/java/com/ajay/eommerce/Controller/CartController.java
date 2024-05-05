package com.ajay.eommerce.Controller;

import com.ajay.eommerce.Exception.ProductException;
import com.ajay.eommerce.Exception.UserException;
import com.ajay.eommerce.Model.Cart;
import com.ajay.eommerce.Model.CartItem;
import com.ajay.eommerce.Model.User;
import com.ajay.eommerce.Request.AddItemRequest;
import com.ajay.eommerce.Response.ApiResponse;
import com.ajay.eommerce.Service.CartService;
import com.ajay.eommerce.Service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/cart")
public class CartController {
    private CartService cartService;
    private UserService userService;

    public CartController(CartService cartService,UserService userService) {
        this.cartService=cartService;
        this.userService=userService;
    }
    @GetMapping("/")
    public ResponseEntity<Cart> findUserCartHandler(@RequestHeader("Authorization") String jwt) throws UserException {

        User user=userService.findUserProfileByJwt(jwt);

        Cart cart=cartService.findUserCart(user.getId());

        System.out.println("cart - "+cart.getUser().getEmail());

        return new ResponseEntity<Cart>(cart, HttpStatus.OK);
    }

    @PutMapping("/add")
    public ResponseEntity<ApiResponse> addItemToCart(@RequestBody AddItemRequest req,
                                                  @RequestHeader("Authorization") String jwt) throws UserException, ProductException {

        User user=userService.findUserProfileByJwt(jwt);

        CartItem item = cartService.addCartItem(user.getId(), req);

        ApiResponse res= new ApiResponse("Item Added To Cart Successfully",true);

        return new ResponseEntity<>(res,HttpStatus.ACCEPTED);

    }
}
