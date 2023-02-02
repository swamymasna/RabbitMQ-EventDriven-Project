package com.swamy.producer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.swamy.dto.OrderEvent;

@Component
public class RabbitMQOrderProducer {

	private static final Logger LOGGER = LoggerFactory.getLogger(RabbitMQOrderProducer.class);


	@Value("${rabbtimq.exchange.name}")
	private String exchange;

	@Value("${rabbtimq.order.routing.key}")
	private String orderRoutingKey;

	@Value("${rabbtimq.email.routing.key}")
	private String emailRoutingKey;
	
	@Autowired
	private RabbitTemplate rabbitTemplate;

	public String sendOrderEvent(OrderEvent orderEvent) {
		rabbitTemplate.convertAndSend(exchange, orderRoutingKey, orderEvent);
		rabbitTemplate.convertAndSend(exchange, emailRoutingKey, orderEvent);

		LOGGER.info(String.format("Order Event Sent -> %s", orderEvent.toString()));

		return "Order Event Sent to RabbitMQ";
	}
}
