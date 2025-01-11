package com.pasteleria.matilde.controller;

import com.pasteleria.matilde.model.Category;
import com.pasteleria.matilde.model.User;
import com.pasteleria.matilde.service.CategoryService;
import com.pasteleria.matilde.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private UserService userService;

    @PostMapping("/admin/category")
    public ResponseEntity<Category> createCategory(@RequestBody Category category,
                                                    @RequestHeader("Authorization") String jwt) throws Exception {
        User user = userService.findUserByJwtToken(jwt);

        Category createdCategory = categoryService.createCategory(category.getName(),user.getId());

        return  new ResponseEntity<>(createdCategory, HttpStatus.CREATED);

    }

    @GetMapping("/category/bakery/{id}")
    public ResponseEntity<List<Category>> getBakeryCategory(
            @PathVariable Long id,
                                                   @RequestHeader("Authorization") String jwt) throws Exception {
        User user = userService.findUserByJwtToken(jwt);

        List<Category> categories = categoryService.findCategoryByBakeryId(id);

        return  new ResponseEntity<>(categories, HttpStatus.CREATED);

    }
}
