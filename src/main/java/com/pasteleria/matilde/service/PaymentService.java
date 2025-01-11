package com.pasteleria.matilde.service;

import com.pasteleria.matilde.model.Order;
import com.pasteleria.matilde.response.PaymentResponse;
import com.stripe.exception.StripeException;
import lombok.Data;


public interface PaymentService {

    public PaymentResponse createPaymentLink(Order order) throws StripeException;
}
