package com.pasteleria.matilde.repository;

import com.pasteleria.matilde.model.IngredientCategory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IngredientCategoryRepository extends JpaRepository<IngredientCategory,Long> {

    List<IngredientCategory> findByBakeryId(Long id);
}
