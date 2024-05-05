package com.ajay.eommerce.Service;

import com.ajay.eommerce.Exception.CartItemException;
import com.ajay.eommerce.Exception.UserException;
import com.ajay.eommerce.Model.Cart;
import com.ajay.eommerce.Model.CartItem;
import com.ajay.eommerce.Model.Product;

public interface CartItemService {
    public CartItem createCartItem(CartItem cartItem);

    public CartItem updateCartItem(Long userId, Long id,CartItem cartItem) throws CartItemException, UserException;

    public CartItem isCartItemExist(Cart cart,Product product,String size, Long userId);

    public void removeCartItem(Long userId,Long cartItemId) throws CartItemException, UserException;

    public CartItem findCartItemById(Long cartItemId) throws CartItemException;

}
