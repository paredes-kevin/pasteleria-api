package com.pasteleria.matilde.service;

import com.pasteleria.matilde.model.Order;
import com.pasteleria.matilde.model.User;
import com.pasteleria.matilde.request.OrderRequest;

import java.util.List;

public interface OrderService {

    public Order createOrder(OrderRequest order, User user) throws Exception;

    public  Order updateOrder(Long orderId, String orderStatus) throws Exception;

    public  void calcelOrder(Long orderId) throws Exception;

    public List<Order> getUsersOrder(Long userId)  throws Exception;

    public  List<Order> getBakeriesOrder(Long bakeryId, String orderStatus) throws  Exception;

    public Order findOrderById(Long orderId) throws Exception;
}
