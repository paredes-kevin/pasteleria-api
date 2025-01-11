package com.pasteleria.matilde.service;

import com.pasteleria.matilde.model.Bakery;
import com.pasteleria.matilde.model.Category;
import com.pasteleria.matilde.model.Food;
import com.pasteleria.matilde.request.CreateFoodRequest;

import java.util.List;

public interface FoodService {

    public Food createFood(CreateFoodRequest req, Category category, Bakery bakery);

    void deleteFood(Long foodId) throws  Exception;

    public List<Food> getBakeriesFood(Long bakeryId,
                                      boolean isVegitarain,
                                      boolean isNonveg,
                                      boolean isSeasonal,
                                      String foodCategory
    );

    public List<Food> searchFood(String keyword);
    public Food findFoodById(Long foodId) throws Exception;

    public  Food updateAvailibiityStatus(Long foodId) throws Exception;
}
