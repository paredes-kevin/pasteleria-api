package com.pasteleria.matilde.controller;

import com.pasteleria.matilde.model.Bakery;
import com.pasteleria.matilde.model.Food;
import com.pasteleria.matilde.model.User;
import com.pasteleria.matilde.request.CreateFoodRequest;
import com.pasteleria.matilde.response.MessageResponse;
import com.pasteleria.matilde.service.BakeryService;
import com.pasteleria.matilde.service.FoodService;
import com.pasteleria.matilde.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin/food")
public class AdminFoodController {

    @Autowired
    private FoodService foodService;

    @Autowired
    private UserService userService;

    @Autowired
    private BakeryService bakeryService;


    @PostMapping
    public ResponseEntity<Food> createFood(@RequestBody CreateFoodRequest req,
                                           @RequestHeader("Authorization") String jwt) throws Exception {
        User user=userService.findUserByJwtToken(jwt);
        Bakery bakery=bakeryService.getBakeryByUserId(user.getId());
        Food food=foodService.createFood(req,req.getCategory(),bakery);

        return  new ResponseEntity<>(food, HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<MessageResponse> deleteFood(@PathVariable Long id,
                                                      @RequestHeader("Authorization") String jwt) throws Exception {
        User user=userService.findUserByJwtToken(jwt);

        foodService.deleteFood(id);
        MessageResponse res=new MessageResponse();
        res.setMessage("food deleted successfully");

        return  new ResponseEntity<>(res, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Food> updateFoodAvaibilityStatus(@PathVariable Long id,
                                                      @RequestHeader("Authorization") String jwt) throws Exception {
        User user=userService.findUserByJwtToken(jwt);

        Food food = foodService.updateAvailibiityStatus(id);

        return  new ResponseEntity<>(food, HttpStatus.CREATED);
    }
}
