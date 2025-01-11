package com.pasteleria.matilde.repository;

import com.pasteleria.matilde.model.Address;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AddressRepository extends JpaRepository<Address, Long> {
}
