package com.pasteleria.matilde.service;

import com.pasteleria.matilde.model.Category;

import java.util.List;

public interface CategoryService {

    public Category createCategory(String name, Long userId) throws Exception;

    public List<Category> findCategoryByBakeryId(Long id) throws Exception;

    public  Category findCategoryById(Long id) throws Exception;
}
