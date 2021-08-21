package com.nokia.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.nokia.api.entity.Order;


public interface OrderRepository extends JpaRepository<Order, Integer>{
	
}


