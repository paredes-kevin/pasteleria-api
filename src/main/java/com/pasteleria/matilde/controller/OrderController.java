package com.pasteleria.matilde.controller;

import com.pasteleria.matilde.model.CartItem;
import com.pasteleria.matilde.model.Order;
import com.pasteleria.matilde.model.User;
import com.pasteleria.matilde.request.AddCartItemRequest;
import com.pasteleria.matilde.request.OrderRequest;
import com.pasteleria.matilde.response.PaymentResponse;
import com.pasteleria.matilde.service.OrderService;
import com.pasteleria.matilde.service.PaymentService;
import com.pasteleria.matilde.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @Autowired
    private PaymentService paymentService;

    @Autowired
    private UserService userService;

    @PostMapping("/order")
    public ResponseEntity<PaymentResponse> createOrder(@RequestBody OrderRequest req,
                                                       @RequestHeader("Authorization") String jwt) throws Exception {
        User user= userService.findUserByJwtToken(jwt);
        Order order=orderService.createOrder(req, user);
        PaymentResponse res = paymentService.createPaymentLink(order);
        return  new ResponseEntity<>(res, HttpStatus.OK);
    }

    @GetMapping("/order/user")
    public ResponseEntity<List<Order>> getOrderHistory(
                                             @RequestHeader("Authorization") String jwt) throws Exception {
        User user= userService.findUserByJwtToken(jwt);
        List<Order> order=orderService.getUsersOrder(user.getId());
        return  new ResponseEntity<>(order, HttpStatus.OK);
    }
}
