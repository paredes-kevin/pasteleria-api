package com.pasteleria.matilde.service;

import com.pasteleria.matilde.model.IngredientCategory;
import com.pasteleria.matilde.model.IngredientsItem;

import java.util.List;

public interface IngredientsService {

    public  IngredientCategory createIngredientCategory(String name, Long bakeryId) throws Exception;

    public IngredientCategory findIngredientCategoryById(Long id) throws  Exception;

    public List<IngredientCategory> findIngredientCategoryByBakeryId(Long id) throws  Exception;

    public IngredientsItem createIngredientItem(Long bakeryId, String ingredientName, Long categoryId) throws Exception;

    public  List<IngredientsItem> findBakeriesIngredients(Long bakeryId);

    public IngredientsItem updateStock(Long id) throws  Exception;


}
