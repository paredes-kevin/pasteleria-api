package com.pasteleria.matilde.repository;

import com.pasteleria.matilde.model.IngredientsItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IngredientItemRepository extends JpaRepository<IngredientsItem, Long> {

    List<IngredientsItem> findByBakeryId(Long id);
}
