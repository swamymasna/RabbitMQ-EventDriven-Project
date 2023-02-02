package com.swamy.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "ORDER_TBL")
public class Order {

	@Id
	private String id;
	private String orderName;
	private Integer orderQty;
	private String orderStatus;
}
