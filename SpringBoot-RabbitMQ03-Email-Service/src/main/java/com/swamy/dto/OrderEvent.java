package com.swamy.dto;

import lombok.Data;

@Data
public class OrderEvent {

	private String status;
	private Order order;
}
