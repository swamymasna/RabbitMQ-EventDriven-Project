package com.swamy.consumer;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.swamy.dto.OrderEvent;
import com.swamy.utils.AppConstants;
import com.swamy.utils.EmailUtils;

@Component
public class OrderConsumer {

	private static final Logger LOGGER = LoggerFactory.getLogger(OrderConsumer.class);

	@Autowired
	private EmailUtils emailUtils;

	@RabbitListener(queues = { "${rabbtimq.email.queue.name}" })
	public void readOrderFromEmail(OrderEvent orderEvent) {
		LOGGER.info(String.format("Email Event Received From Order Service -> %s", orderEvent.toString()));

		String subject = AppConstants.SUBJECT;
		String receiver = AppConstants.RECEIVER;
		String body = readMailBody(orderEvent);
		String sentEmail = emailUtils.sendEmail(subject, receiver, body);
		System.out.println(sentEmail);

	}

	public String readMailBody(OrderEvent orderEvent) {
		String mailBody = null;
		StringBuffer buffer = new StringBuffer();
		try {
			List<String> allLines = Files.readAllLines(Paths.get(AppConstants.ORDER_EMAIL_BODY_TEMPLATE));

			allLines.forEach(line -> {
				buffer.append(line);
				buffer.append("\n");
			});

			mailBody = buffer.toString();
			mailBody = mailBody.replace(AppConstants.USER, AppConstants.RECEIVER);
			mailBody = mailBody.replace(AppConstants.ORDER_ID, orderEvent.getOrder().getId().toString());
			mailBody = mailBody.replace(AppConstants.ORDER_NAME, orderEvent.getOrder().getOrderName());
			mailBody = mailBody.replace(AppConstants.ORDER_QTY, orderEvent.getOrder().getOrderQty().toString());
			mailBody = mailBody.replace(AppConstants.ORDER_STATUS, orderEvent.getStatus());

		} catch (Exception e) {
			e.printStackTrace();
		}
		return mailBody;
	}
}
