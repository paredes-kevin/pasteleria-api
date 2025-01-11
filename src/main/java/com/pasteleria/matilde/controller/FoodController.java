package com.pasteleria.matilde.controller;

import com.pasteleria.matilde.model.Bakery;
import com.pasteleria.matilde.model.Food;
import com.pasteleria.matilde.model.User;
import com.pasteleria.matilde.request.CreateFoodRequest;
import com.pasteleria.matilde.service.BakeryService;
import com.pasteleria.matilde.service.FoodService;
import com.pasteleria.matilde.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/food")
public class FoodController {
    @Autowired
    private FoodService foodService;

    @Autowired
    private UserService userService;

    @Autowired
    private BakeryService bakeryService;


    @GetMapping("/search")
    public ResponseEntity<List<Food>> searchFood(@RequestParam String name,
                                           @RequestHeader("Authorization") String jwt) throws Exception {
        User user=userService.findUserByJwtToken(jwt);

        List<Food> foods=foodService.searchFood(name);

        return  new ResponseEntity<>(foods, HttpStatus.CREATED);
    }

    @GetMapping("/bakery/{bakeryId}")
    public ResponseEntity<List<Food>> getBakeryFood(
            @RequestParam (required = false) boolean vegetarian,
            @RequestParam (required = false) boolean seasonal,
            @RequestParam (required = false) boolean nonveg,
            @RequestParam(required = false) String food_category,
            @PathVariable Long bakeryId,
            @RequestHeader("Authorization") String jwt) throws Exception {
        User user=userService.findUserByJwtToken(jwt);

        List<Food> foods=foodService.getBakeriesFood(bakeryId, vegetarian,nonveg,seasonal,food_category);

        return  new ResponseEntity<>(foods, HttpStatus.OK);
    }
}
