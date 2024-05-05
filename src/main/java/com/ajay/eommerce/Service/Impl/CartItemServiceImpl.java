package com.ajay.eommerce.Service.Impl;

import com.ajay.eommerce.Exception.CartItemException;
import com.ajay.eommerce.Exception.UserException;
import com.ajay.eommerce.Model.Cart;
import com.ajay.eommerce.Model.CartItem;
import com.ajay.eommerce.Model.Product;
import com.ajay.eommerce.Model.User;
import com.ajay.eommerce.Repository.CartItemRepository;
import com.ajay.eommerce.Repository.CartRepository;
import com.ajay.eommerce.Service.CartItemService;
import com.ajay.eommerce.Service.UserService;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CartItemServiceImpl implements CartItemService {
    private CartItemRepository cartItemRepository;
    private UserService userService;
    private CartRepository cartRepository;
    public CartItemServiceImpl(CartItemRepository cartItemRepository,UserService userService,CartRepository cartRepository){
        this.cartItemRepository=cartItemRepository;
        this.userService=userService;
        this.cartRepository=cartRepository;
    }
    @Override
    public CartItem createCartItem(CartItem cartItem) {
        cartItem.setQuantity(1);
        cartItem.setPrice(cartItem.getProduct().getPrice()*cartItem.getQuantity());
        cartItem.setDiscountedPrice(cartItem.getProduct().getDiscountedPrice()*cartItem.getQuantity());
        CartItem createdCartItem=cartItemRepository.save(cartItem);
        return createdCartItem;
    }

    @Override
    public CartItem updateCartItem(Long userId, Long id, CartItem cartItem) throws CartItemException, UserException {
        CartItem item=findCartItemById(id);
        User user=userService.findUserById(item.getUserId());
        if(user.getId().equals(userId)){
            item.setQuantity(cartItem.getQuantity());
            item.setPrice(item.getQuantity()*item.getProduct().getPrice());
            item.setDiscountedPrice(item.getQuantity()*item.getProduct().getDiscountedPrice());

            return cartItemRepository.save(item);
        }else {
            throw new CartItemException("You can't update  another users cart_item");
        }
    }

    @Override
    public CartItem isCartItemExist(Cart cart, Product product, String size, Long userId) {

        CartItem cartItem=cartItemRepository.isCartItemExist(cart, product, size, userId);

        return cartItem;
    }

    @Override
    public void removeCartItem(Long userId, Long cartItemId) throws CartItemException, UserException {
        CartItem cartItem=findCartItemById(cartItemId);
        User reqUser= userService.findUserById(userId);
        User user=userService.findUserById(cartItem.getUserId());
        if(reqUser.getId().equals(user.getId())){
            cartItemRepository.deleteById(cartItem.getId());
        }else {
            throw new UserException("You can't remove another user item");
        }

    }

    @Override
    public CartItem findCartItemById(Long cartItemId) throws CartItemException {
        Optional<CartItem> opt=cartItemRepository.findById(cartItemId);
        if(opt.isPresent()){
            return opt.get();
        }
        throw new CartItemException("cartItem not found with id:"+cartItemId);
    }
}
