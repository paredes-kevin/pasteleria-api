package com.pasteleria.matilde.repository;

import com.pasteleria.matilde.model.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderitemRepository extends JpaRepository<OrderItem, Long> {
}
