package com.pasteleria.matilde.request;

import com.pasteleria.matilde.model.Category;
import com.pasteleria.matilde.model.IngredientsItem;
import lombok.Data;

import java.util.List;

@Data
public class CreateFoodRequest {

    private String name;
    private String description;
    private Long price;

    private Category category;
    private List<String> images;

    private Long bakeryId;
    private boolean vegetarin;
    private boolean seasional;
    private List<IngredientsItem> ingredients;
}
