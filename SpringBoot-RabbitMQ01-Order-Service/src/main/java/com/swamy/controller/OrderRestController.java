package com.swamy.controller;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.swamy.dto.Order;
import com.swamy.dto.OrderEvent;
import com.swamy.producer.RabbitMQOrderProducer;

@RestController
@RequestMapping("/api/v1")
public class OrderRestController {

	@Autowired
	private RabbitMQOrderProducer rabbitMQOrderProducer;

	@PostMapping("/orders")
	public ResponseEntity<String> publishOrder(@RequestBody Order order) {

		order.setId(UUID.randomUUID().toString());

		OrderEvent orderEvent = new OrderEvent();
		orderEvent.setStatus("PENDING");
		orderEvent.setOrder(order);

		String sentOrderEvent = rabbitMQOrderProducer.sendOrderEvent(orderEvent);
		return ResponseEntity.ok(sentOrderEvent);

	}

}
