package com.pasteleria.matilde.request;

import com.pasteleria.matilde.model.Address;
import com.pasteleria.matilde.model.ContactInformation;
import lombok.Data;

import java.util.List;

@Data
public class CreateBakeryRequest {

    private Long id;
    private String name;
    private String description;
    private String cuisineType;
    private Address address;
    private ContactInformation contactInformation;
    private String opningHours;
    private List<String> images;

}
