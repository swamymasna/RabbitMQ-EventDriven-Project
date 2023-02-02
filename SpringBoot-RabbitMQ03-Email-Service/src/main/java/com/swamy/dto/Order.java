package com.swamy.dto;

import lombok.Data;

@Data
public class Order {

	private String id;
	private String orderName;
	private Integer orderQty;
}
