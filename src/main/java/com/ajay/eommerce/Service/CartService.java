package com.ajay.eommerce.Service;

import com.ajay.eommerce.Request.AddItemRequest;
import com.ajay.eommerce.Exception.ProductException;
import com.ajay.eommerce.Model.Cart;
import com.ajay.eommerce.Model.CartItem;
import com.ajay.eommerce.Model.User;

public interface CartService {
    public Cart createCart(User user);

    public CartItem addCartItem(Long userId, AddItemRequest req) throws ProductException;

    public Cart findUserCart(Long userId);

}
