package com.swamy.consumer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.swamy.dto.OrderEvent;
import com.swamy.entity.Order;
import com.swamy.repository.OrderRepository;

@Component
public class OrderConsumer {

	private static final Logger LOGGER = LoggerFactory.getLogger(OrderConsumer.class);
	
	@Autowired
	private OrderRepository orderRepository; 
	
	@RabbitListener(queues = {"${rabbtimq.order.queue.name}"})
	public void readOrder(OrderEvent orderEvent) {
		LOGGER.info(String.format("Order Event Received In Stock Service -> %s", orderEvent.toString()));

		Order order = new Order();
		order.setId(orderEvent.getOrder().getId());
		order.setOrderName(orderEvent.getOrder().getOrderName());
		order.setOrderQty(orderEvent.getOrder().getOrderQty());
		order.setOrderStatus(orderEvent.getStatus());
		
		Order savedOrder = orderRepository.save(order);
		LOGGER.info(String.format("Saved Order : -> %s", savedOrder.toString()));
		System.out.println("Saved Order : "+savedOrder);
	}
}

