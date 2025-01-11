package com.pasteleria.matilde.repository;

import com.pasteleria.matilde.model.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartItemRepository extends JpaRepository<CartItem, Long> {
}
