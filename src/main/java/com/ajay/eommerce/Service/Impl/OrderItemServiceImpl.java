package com.ajay.eommerce.Service.Impl;

import com.ajay.eommerce.Model.OrderItem;
import com.ajay.eommerce.Repository.OrderItemRepository;
import com.ajay.eommerce.Service.OrderItemService;
import com.ajay.eommerce.Service.OrderService;
import org.springframework.stereotype.Service;

@Service
public class OrderItemServiceImpl implements OrderItemService {
    private OrderItemRepository orderItemRepository;
    public OrderItemServiceImpl(OrderItemRepository orderItemRepository) {
        this.orderItemRepository=orderItemRepository;
    }
    @Override
    public OrderItem createOrderItem(OrderItem orderItem) {
        return orderItemRepository.save(orderItem);
    }
}
