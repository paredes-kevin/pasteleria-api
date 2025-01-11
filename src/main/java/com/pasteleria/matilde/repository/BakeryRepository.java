package com.pasteleria.matilde.repository;

import com.pasteleria.matilde.model.Bakery;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface BakeryRepository extends JpaRepository<Bakery, Long> {

    @Query("SELECT r FROM Bakery r WHERE lower(r.name) LIKE lower(concat('%', :query, '%')) " +
            "OR lower(r.cuisineType) LIKE lower(concat('%', :query, '%')) ")
    List<Bakery> findBySearchQuery(String query);
    Bakery findByOwnerId(Long userId);
}
