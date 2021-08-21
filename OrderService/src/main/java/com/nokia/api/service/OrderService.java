package com.nokia.api.service;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nokia.api.dto.Payment;
import com.nokia.api.dto.TransactionRequest;
import com.nokia.api.dto.TransactionResponse;
import com.nokia.api.entity.Order;
import com.nokia.api.repository.OrderRepository;

@Service
@RefreshScope
public class OrderService {

 private static final Logger logger = LoggerFactory.getLogger(OrderService.class);
	
 @Autowired	
 private OrderRepository orderRepo;
 
 @Autowired
 private RestTemplate restTemplate;
 
 @Value("${microservice.paymentService.endpoint.uri}")
 private String endpointUrl;
 
 
 public TransactionResponse saveOrder(TransactionRequest request) throws JsonProcessingException {
	 logger.info("OrderService - request : {} ",new ObjectMapper().writeValueAsString(request));
	 String response="";
	 Order order = request.getOrder();
	 Payment payment = request.getPayment();
	 order = orderRepo.save(order);
	 
	 payment.setOrderId(order.getId());
	 payment.setAmount(order.getQty()*order.getPrice());
	 //Payment paymentResponse = restTemplate.postForObject("http://PAYMENT-SERVICE/payment/doPayment", payment, Payment.class);
	 logger.info("Printing endPoint url : "+endpointUrl);
	 Payment paymentResponse = restTemplate.postForObject(endpointUrl, payment, Payment.class);
	 logger.info("paymentResponse - response in OrderService : {} ",new ObjectMapper().writeValueAsString(paymentResponse));	
	 response  = paymentResponse.getPaymentStatus().equals("success")?"Payment Successful" : "Payment failed";
	 
	 
	 TransactionResponse transactionResponse = new TransactionResponse(order,paymentResponse.getTransactionId(),paymentResponse.getAmount(),response);
	 return transactionResponse;
	 
 }
	
}
