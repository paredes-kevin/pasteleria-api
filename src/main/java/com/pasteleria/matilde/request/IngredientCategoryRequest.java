package com.pasteleria.matilde.request;

import lombok.Data;

@Data
public class IngredientCategoryRequest {
    private String name;
    private Long bakeryId;

}
