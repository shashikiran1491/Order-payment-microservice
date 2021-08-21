package com.nokia.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.nokia.api.entity.Payment;

public interface PaymentRepository extends JpaRepository<Payment, Integer> {
	
	public Payment findByOrderId(int orderId);

}
