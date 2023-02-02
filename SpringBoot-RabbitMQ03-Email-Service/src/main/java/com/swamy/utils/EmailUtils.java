package com.swamy.utils;

import java.io.File;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import jakarta.mail.internet.MimeMessage;

@Component
public class EmailUtils {

	@Autowired
	private JavaMailSender javaMailSender;

	public String sendEmail(String subject, String receiver, String body) {
		String result = null;
		try {

			MimeMessage mimeMessage = javaMailSender.createMimeMessage();
			MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);
			mimeMessageHelper.setSubject(subject);
			mimeMessageHelper.setTo(receiver);
			mimeMessageHelper.setText(body);

			FileSystemResource file = new FileSystemResource(new File(AppConstants.FILE_NAME));
			mimeMessageHelper.addAttachment(file.getFilename(), file);

			javaMailSender.send(mimeMessageHelper.getMimeMessage());
			result = AppConstants.EMAIL_SENT_SUCCESS;
		} catch (Exception e) {
			e.printStackTrace();
			result = AppConstants.EMAIL_ERROR;
		}

		return result;
	}
}
