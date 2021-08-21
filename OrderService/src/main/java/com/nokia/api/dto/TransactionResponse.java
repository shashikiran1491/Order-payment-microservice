package com.nokia.api.dto;

import com.nokia.api.entity.Order;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TransactionResponse {

	private Order order;
	private String transactionId;
	private double amount;
	private String message;
}
