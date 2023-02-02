package com.swamy.config;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

	@Value("${rabbtimq.order.queue.name}")
	private String orderQueue;

	@Value("${rabbtimq.email.queue.name}")
	private String emailQueue;

	@Value("${rabbtimq.exchange.name}")
	private String exchange;

	@Value("${rabbtimq.order.routing.key}")
	private String orderRoutingKey;

	@Value("${rabbtimq.email.routing.key}")
	private String emailRoutingKey;
	
	// create order queue
	@Bean
	public Queue orderQueue() {
		return new Queue(orderQueue);
	}

	// create email queue
	@Bean
	public Queue emailQueue() {
		return new Queue(emailQueue);
	}
	
	// create exchange
	@Bean
	public TopicExchange exchange() {
		return new TopicExchange(exchange);
	}

	// create binding = [queue + echange] by using order routingKey
	@Bean
	public Binding orderBinding() {
		return BindingBuilder
				.bind(orderQueue())
				.to(exchange())
				.with(orderRoutingKey);
	}

	// create binding = [queue + echange] by using email routingKey
	@Bean
	public Binding emailBinding() {
		return BindingBuilder
				.bind(emailQueue())
				.to(exchange())
				.with(emailRoutingKey);
	}

	
	// MessageConvertor
	@Bean
	public MessageConverter messageConverter() {
		return new Jackson2JsonMessageConverter();
	}

	// AMQPTemplate to set RabbitTemplate
	@Bean
	public AmqpTemplate amqpTemplate(ConnectionFactory connectionFactory) {
		RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
		rabbitTemplate.setMessageConverter(messageConverter());
		return rabbitTemplate;
	}
}
