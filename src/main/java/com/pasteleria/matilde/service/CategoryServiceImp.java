package com.pasteleria.matilde.service;

import com.pasteleria.matilde.model.Bakery;
import com.pasteleria.matilde.model.Category;
import com.pasteleria.matilde.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryServiceImp implements  CategoryService{

    @Autowired
    private BakeryService bakeryService;

    @Autowired
    private CategoryRepository categoryRepository;


    @Override
    public Category createCategory(String name, Long userId) throws Exception {
        Bakery bakery=bakeryService.getBakeryByUserId(userId);
        Category category=new Category();
        category.setName(name);
        category.setBakery(bakery);

        return categoryRepository.save(category);
    }

    @Override
    public List<Category> findCategoryByBakeryId(Long id) throws Exception {
        Bakery bakery=bakeryService.findBakeryById(id);
        return categoryRepository.findByBakeryId(id);
    }

    @Override
    public Category findCategoryById(Long id) throws Exception {
        Optional<Category>optionalCategory=categoryRepository.findById(id);

        if(optionalCategory.isEmpty()){
            throw new Exception("category not found");
        }
        return optionalCategory.get();
    }
}
