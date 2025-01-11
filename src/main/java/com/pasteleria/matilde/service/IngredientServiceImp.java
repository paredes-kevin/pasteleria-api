package com.pasteleria.matilde.service;

import com.pasteleria.matilde.model.Bakery;
import com.pasteleria.matilde.model.IngredientCategory;
import com.pasteleria.matilde.model.IngredientsItem;
import com.pasteleria.matilde.repository.IngredientCategoryRepository;
import com.pasteleria.matilde.repository.IngredientItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class IngredientServiceImp implements  IngredientsService {

    @Autowired
    private IngredientItemRepository ingredientItemRepository;

    @Autowired
    private IngredientCategoryRepository ingredientCategoryRepository;

    @Autowired
    private BakeryService bakeryService;


    @Override
    public IngredientCategory createIngredientCategory(String name, Long bakeryId) throws Exception {

        Bakery bakery = bakeryService.findBakeryById(bakeryId);

        IngredientCategory category= new IngredientCategory();
        category.setBakery(bakery);
        category.setName(name);

        return ingredientCategoryRepository.save(category);
    }

    @Override
    public IngredientCategory findIngredientCategoryById(Long id) throws Exception {
        Optional<IngredientCategory> opt = ingredientCategoryRepository.findById(id);

        if(opt.isEmpty()){
            throw  new Exception(("ingredient category not fount"));
        }
        return opt.get();
    }

    @Override
    public List<IngredientCategory> findIngredientCategoryByBakeryId(Long id) throws Exception {
        bakeryService.findBakeryById(id);
        return ingredientCategoryRepository.findByBakeryId(id);
    }

    @Override
    public IngredientsItem createIngredientItem(Long bakeryId, String ingredientName, Long categoryId) throws Exception {
        Bakery bakery = bakeryService.findBakeryById(bakeryId);
        IngredientCategory category = findIngredientCategoryById(categoryId);

        IngredientsItem item= new IngredientsItem();
        item.setName(ingredientName);
        item.setBakery(bakery);
        item.setCategory(category);

        IngredientsItem ingredient = ingredientItemRepository.save(item);
        category.getIngredients().add(ingredient);

        return ingredient;
    }

    @Override
    public List<IngredientsItem> findBakeriesIngredients(Long bakeryId) {
        return ingredientItemRepository.findByBakeryId(bakeryId);
    }

    @Override
    public IngredientsItem updateStock(Long id) throws Exception {
        Optional<IngredientsItem> optionalIngredientsItem=ingredientItemRepository.findById(id);
        if(optionalIngredientsItem.isEmpty()){
            throw new Exception(("ingredient not found"));
        }
        IngredientsItem ingredientsItem=optionalIngredientsItem.get();
        ingredientsItem.setInStoke(!ingredientsItem.isInStoke());
        return ingredientItemRepository.save(ingredientsItem);
    }
}
