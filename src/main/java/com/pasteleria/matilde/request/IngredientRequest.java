package com.pasteleria.matilde.request;

import lombok.Data;

@Data
public class IngredientRequest {

    private String name;
    private Long categoryId;
    private  Long bakeryId;
}
