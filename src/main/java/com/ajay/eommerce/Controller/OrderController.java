package com.ajay.eommerce.Controller;

import com.ajay.eommerce.Exception.OrderException;
import com.ajay.eommerce.Exception.ProductException;
import com.ajay.eommerce.Exception.UserException;
import com.ajay.eommerce.Model.Address;
import com.ajay.eommerce.Model.Order;
import com.ajay.eommerce.Model.User;
import com.ajay.eommerce.Service.OrderService;
import com.ajay.eommerce.Service.UserService;
import org.apache.coyote.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
public class OrderController {
    private OrderService orderService;
    private UserService userService;

    public OrderController(OrderService orderService,UserService userService) {
        this.orderService=orderService;
        this.userService=userService;
    }
    @PostMapping("/")
    public ResponseEntity<Order> createOrderHandler(@RequestBody Address spippingAddress,
                                                    @RequestHeader("Authorization")String jwt) throws UserException {
        User user=userService.findUserProfileByJwt(jwt);
        Order order=orderService.createOrder(user,spippingAddress);
        return new ResponseEntity<Order>(order, HttpStatus.OK);
    }

    @GetMapping("/user")
    public ResponseEntity<List<Order>> usersOrderHistoryHandler(@RequestHeader("Authorization")
                                                                 String jwt) throws OrderException, UserException{
        User user=userService.findUserProfileByJwt(jwt);
        List<Order> orders=orderService.usersOrderHistory(user.getId());
        return new ResponseEntity<>(orders,HttpStatus.ACCEPTED);
    }

    @GetMapping("/{orderId}")
    public ResponseEntity<Order> findOrderHandler(@PathVariable Long orderId,@RequestHeader("Authorization")String jwt)
            throws OrderException, UserException {
        User user=userService.findUserProfileByJwt(jwt);
        if(user==null){
            throw new UserException("you cannot check someonelse order's");
        }
        Order orders=orderService.findOrderById(orderId);
        return new ResponseEntity<>(orders,HttpStatus.ACCEPTED);
    }
}
