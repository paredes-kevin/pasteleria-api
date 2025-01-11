package com.pasteleria.matilde.request;

import com.pasteleria.matilde.model.Address;
import lombok.Data;

@Data
public class OrderRequest {
    private Long bakeryId;
    private Address deliveryAddress;
}
