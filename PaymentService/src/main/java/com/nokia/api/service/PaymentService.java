package com.nokia.api.service;

import java.util.Random;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nokia.api.entity.Payment;
import com.nokia.api.repository.PaymentRepository;

@Service
public class PaymentService {
	
	 private static final Logger logger = LoggerFactory.getLogger(PaymentService.class);
	
	@Autowired
	private PaymentRepository paymentRepo;
	
	
	public Payment savePayment(Payment payment) throws JsonProcessingException {
		logger.info("PaymentService - request : {} ",new ObjectMapper().writeValueAsString(payment));
		payment.setPaymentStatus(paymentProcessing());
		payment.setTransactionId(UUID.randomUUID().toString());
		return paymentRepo.save(payment);
	}
	
	
	public String paymentProcessing() {
		return new Random().nextBoolean()?"success":"failure";
	}
	
	
	public Payment findPaymentByOrderId(int orderId) {
		return paymentRepo.findByOrderId(orderId);
	}

}
