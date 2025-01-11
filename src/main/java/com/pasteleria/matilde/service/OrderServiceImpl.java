package com.pasteleria.matilde.service;

import com.pasteleria.matilde.model.*;
import com.pasteleria.matilde.repository.*;
import com.pasteleria.matilde.request.OrderRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class OrderServiceImpl implements  OrderService{

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OrderitemRepository orderitemRepository;

    @Autowired
    private AddressRepository addressRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BakeryService bakeryService;

    @Autowired
    private CartService cartService;
    @Override
    public Order createOrder(OrderRequest order, User user) throws Exception {

        Address shippAddress= order.getDeliveryAddress();

        Address saveAddress = addressRepository.save(shippAddress);

        if (!user.getAddresses().contains(saveAddress)) {
            user.getAddresses().add(saveAddress);
            userRepository.save(user);
        }

        Bakery bakery= bakeryService.findBakeryById(order.getBakeryId());

        Order createdOrder = new Order();
        createdOrder.setCustomer(user);
        createdOrder.setCreateAt(new Date());
        createdOrder.setOrderStatus("PENDIENTE");
        createdOrder.setDeliveryAddress(saveAddress);
        createdOrder.setBakery(bakery);

        Cart cart = cartService.findCartByUserId(user.getId());

        List<OrderItem> orderItems= new ArrayList<>();

        for(CartItem cartItem: cart.getItems()){
            OrderItem orderItem = new OrderItem();
            orderItem.setFood(cartItem.getFood());
            orderItem.setIngredients(cartItem.getIngredients());
            orderItem.setQuantity(cartItem.getQuantity());
            orderItem.setTotalPrice(cartItem.getTotalPrice());

            OrderItem savedOrderItem=orderitemRepository.save(orderItem);
            orderItems.add(savedOrderItem);
        }
        Long totalPrice = cartService.calculateCartTotals(cart);

        createdOrder.setItems(orderItems);
        createdOrder.setTotalPrice(totalPrice);

        Order savedOrder = orderRepository.save(createdOrder);
        bakery.getOrders().add(savedOrder);


        return createdOrder;
    }

    @Override
    public Order updateOrder(Long orderId, String orderStatus) throws Exception {
        Order order = findOrderById(orderId);
        if(orderStatus.equals("FUERA DE ENTREGA")
                || orderStatus.equals("ENTREGADO")
                || orderStatus.equals("COMPLETADO")
                || orderStatus.equals("PENDIENTE")
        ){
            order.setOrderStatus(orderStatus);
            return orderRepository.save(order);
        }
        throw new Exception("Please select a valid order status");
    }

    @Override
    public void calcelOrder(Long orderId) throws Exception {

        Order order = findOrderById(orderId);
        orderRepository.deleteById(orderId);


    }

    @Override
    public List<Order> getUsersOrder(Long userId) throws Exception {
        return orderRepository.findByCustomerId(userId);
    }

    @Override
    public List<Order> getBakeriesOrder(Long bakeryId, String orderStatus) throws Exception {
        List<Order> orders= orderRepository.findByBakeryId(bakeryId);
        if(orderStatus!=null){
            orders=orders.stream().filter(order->
                    order.getOrderStatus().equals(orderStatus)).collect(Collectors.toList());
        }

        return  orders;
    }

    @Override
    public Order findOrderById(Long orderId) throws Exception {
        Optional<Order> optionalOrder=orderRepository.findById(orderId);
        if(optionalOrder.isEmpty()){
            throw new Exception("order not found");
        }
        return optionalOrder.get();
    }
}
