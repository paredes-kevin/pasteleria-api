package com.pasteleria.matilde.repository;

import com.pasteleria.matilde.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CategoryRepository extends JpaRepository<Category, Long> {

    public List<Category>findByBakeryId(Long id);
}
