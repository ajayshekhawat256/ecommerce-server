package com.ajay.eommerce.Service.Impl;

import com.ajay.eommerce.Exception.OrderException;
import com.ajay.eommerce.Model.*;
import com.ajay.eommerce.Repository.*;
import com.ajay.eommerce.Service.*;
import com.ajay.eommerce.constant.OrderStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class OrderServiceImpl implements OrderService {
    private OrderRepository orderRepository;
    private CartService cartService;
    private AddressRepository addressRepository;
    private UserRepository userRepository;
    private OrderItemService orderItemService;
    private OrderItemRepository orderItemRepository;


    public OrderServiceImpl(OrderRepository orderRepository,CartService cartService,
                                      AddressRepository addressRepository,UserRepository userRepository,
                                      OrderItemService orderItemService,OrderItemRepository orderItemRepository) {
        this.orderRepository=orderRepository;
        this.cartService=cartService;
        this.addressRepository=addressRepository;
        this.userRepository=userRepository;
        this.orderItemService=orderItemService;
        this.orderItemRepository=orderItemRepository;
    }

    @Override
    public Order createOrder(User user, Address shippingAdress) {
        shippingAdress.setUser(user);
        Address address=addressRepository.save(shippingAdress);
        user.getAddresses().add(address);
        userRepository.save(user);

        Cart cart=cartService.findUserCart(user.getId());
        List<OrderItem> orderItems=new ArrayList<>();
        for(CartItem item:cart.getCartItems()) {
            OrderItem orderItem = new OrderItem();
            orderItem.setPrice(item.getPrice());
            orderItem.setProduct(item.getProduct());
            orderItem.setQuantity(item.getQuantity());
            orderItem.setSize(item.getSize());
            orderItem.setUserId(item.getUserId());
            orderItem.setDiscountedPrice(item.getDiscountedPrice());
            OrderItem createdOrderItem = orderItemRepository.save(orderItem);
            orderItems.add(createdOrderItem);
        }

            Order createdOrder=new Order();
            createdOrder.setUser(user);
            createdOrder.setOrderItems(orderItems);
            createdOrder.setTotal_price(cart.getTotalPrice());
            createdOrder.setTotalDiscountedPrice(cart.getTotalDiscountedPrice());
            createdOrder.setDiscounts(cart.getDiscounte());
            createdOrder.setTotalItems(cart.getTotalItem());

            createdOrder.setShippingAddress(address);
            createdOrder.setOrderDate(LocalDateTime.now());
            createdOrder.setOrderStatus(OrderStatus.PENDING);
            //createdOrder.getPayementDetails().setStatus("PENDING");
            createdOrder.setCreatedAt(LocalDateTime.now());

            Order savedOrder=orderRepository.save(createdOrder);
            for(OrderItem item1:orderItems){
                item1.setOrder(savedOrder);
                orderItemRepository.save(item1);
            }
            return savedOrder;
        }

    @Override
    public Order findOrderById(Long orderId) throws OrderException {
        Optional<Order> opt=orderRepository.findById(orderId);
        if(opt.isPresent()){
            return opt.get();
        }
        throw new OrderException("Order cannot be found with this id"+orderId);
    }

    @Override
    public List<Order> usersOrderHistory(Long userId) {
        List<Order> optionalOrders=orderRepository.getUsersOrders(userId);
        return optionalOrders;
    }

    @Override
    public Order placedOrder(Long orderId) throws OrderException {
        Order order=findOrderById(orderId);
        order.setOrderStatus(OrderStatus.PLACED);
        order.getPayementDetails().setStatus("COMPLETED");
        return order;
    }

    @Override
    public Order confirmedOrder(Long orderId) throws OrderException {
        Order order=findOrderById(orderId);
        order.setOrderStatus(OrderStatus.CONFIRMED);
        return orderRepository.save(order);
    }

    @Override
    public Order shippedOrder(Long orderId) throws OrderException {
        Order order=findOrderById(orderId);
        order.setOrderStatus(OrderStatus.SHIPPED);
        return orderRepository.save(order);
    }

    @Override
    public Order deliveredOrder(Long orderId) throws OrderException {
        Order order=findOrderById(orderId);
        order.setOrderStatus(OrderStatus.DELIVERED);
        return orderRepository.save(order);
    }

    @Override
    public Order cancledOrder(Long orderId) throws OrderException {
        Order order=findOrderById(orderId);
        order.setOrderStatus(OrderStatus.CANCELLED);
        return orderRepository.save(order);
    }

    @Override
    public List<Order> getAllOrders() {
        return null;
    }

    @Override
    public void deleteOrder(Long orderId) throws OrderException {
        Order order =findOrderById(orderId);
        orderRepository.deleteById(orderId);
    }
}
