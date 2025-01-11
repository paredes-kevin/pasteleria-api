package com.pasteleria.matilde.controller;

import com.pasteleria.matilde.model.IngredientCategory;
import com.pasteleria.matilde.model.IngredientsItem;
import com.pasteleria.matilde.request.IngredientCategoryRequest;
import com.pasteleria.matilde.request.IngredientRequest;
import com.pasteleria.matilde.service.IngredientsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin/ingredients")
public class IngredientController {

    @Autowired
    private IngredientsService ingredientsService;

    @PostMapping("/category")
    public ResponseEntity<IngredientCategory> createIngredientCategory(
            @RequestBody IngredientCategoryRequest req
            ) throws Exception {
        IngredientCategory item = ingredientsService.createIngredientCategory(req.getName(), req.getBakeryId());
        return  new ResponseEntity<>(item, HttpStatus.CREATED);
    }

    @PostMapping()
    public ResponseEntity<IngredientsItem> createIngredientItem(
            @RequestBody IngredientRequest req
    ) throws Exception {
        IngredientsItem item = ingredientsService.createIngredientItem(req.getBakeryId(),req.getName(),req.getCategoryId());
        return  new ResponseEntity<>(item, HttpStatus.CREATED);
    }

    @PutMapping("/{id}/stoke")
    public ResponseEntity<IngredientsItem> updateIngredientStock(
            @PathVariable Long id
    ) throws Exception {
        IngredientsItem item = ingredientsService.updateStock(id);
        return  new ResponseEntity<>(item, HttpStatus.OK);
    }

    @GetMapping("/bakery/{id}")
    public ResponseEntity<List<IngredientsItem>> getBakeryIngredient(
            @PathVariable Long id
    ) throws Exception {
        List<IngredientsItem> item = ingredientsService.findBakeriesIngredients(id);
        return  new ResponseEntity<>(item, HttpStatus.OK);
    }

    @GetMapping("/bakery/{id}/category")
    public ResponseEntity<List<IngredientCategory>> getBakeryIngredientCategory(
            @PathVariable Long id
    ) throws Exception {
        List<IngredientCategory> item = ingredientsService.findIngredientCategoryByBakeryId(id);
        return  new ResponseEntity<>(item, HttpStatus.OK);
    }
}
