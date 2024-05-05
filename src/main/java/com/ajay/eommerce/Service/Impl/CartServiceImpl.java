package com.ajay.eommerce.Service.Impl;

import com.ajay.eommerce.Exception.ProductException;
import com.ajay.eommerce.Model.Cart;
import com.ajay.eommerce.Model.CartItem;
import com.ajay.eommerce.Model.Product;
import com.ajay.eommerce.Model.User;
import com.ajay.eommerce.Repository.CartRepository;
import com.ajay.eommerce.Request.AddItemRequest;
import com.ajay.eommerce.Service.CartItemService;
import com.ajay.eommerce.Service.CartService;
import com.ajay.eommerce.Service.ProductService;
import org.springframework.stereotype.Service;

@Service
public class CartServiceImpl implements CartService {
    private CartRepository cartRepository;
    private CartItemService cartItemService;
    private ProductService productService;
    public CartServiceImpl(CartRepository cartRepository,CartItemService cartItemService,ProductService productService){
        this.cartItemService=cartItemService;
        this.cartRepository=cartRepository;
        this.productService=productService;
    }

    @Override
    public Cart createCart(User user) {
        Cart cart=new Cart();
        cart.setUser(user);
        return cartRepository.save(cart);
    }

    @Override
    public CartItem addCartItem(Long userId, AddItemRequest req) throws ProductException {
        Cart cart=cartRepository.findByUserId(userId);
        Product product=productService.findProductById(req.getProductId());

        CartItem isPresent=cartItemService.isCartItemExist(cart, product, req.getSize(),userId);

        if(isPresent == null) {
            CartItem cartItem = new CartItem();
            cartItem.setProduct(product);
            cartItem.setCart(cart);
            cartItem.setQuantity(req.getQuantity());
            cartItem.setUserId(userId);


            int price=req.getQuantity()*product.getDiscountedPrice();
            cartItem.setPrice(price);
            cartItem.setSize(req.getSize());

            CartItem createdCartItem=cartItemService.createCartItem(cartItem);
            cart.getCartItems().add(createdCartItem);
            return createdCartItem;
        }
        return isPresent;
    }

    @Override
    public Cart findUserCart(Long userId) {
        Cart cart =	cartRepository.findByUserId(userId);
        int totalPrice=0;
        int totalDiscountedPrice=0;
        int totalItem=0;
        for(CartItem cartsItem : cart.getCartItems()) {
            totalPrice+=cartsItem.getPrice();
            totalDiscountedPrice+=cartsItem.getDiscountedPrice();
            totalItem+=cartsItem.getQuantity();
        }
        cart.setTotalPrice(totalPrice);
        cart.setTotalItem(cart.getCartItems().size());
        cart.setTotalDiscountedPrice(totalDiscountedPrice);
        cart.setDiscounte(totalPrice-totalDiscountedPrice);
        cart.setTotalItem(totalItem);
        return cartRepository.save(cart);
    }
}
