package com.springboot.getlinked.services;

import java.util.Date;

import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Service;

@Service
public class EmailService {
	
	@Autowired
	private JavaMailSender mailSender;
	//mail server properties
	@Value("${spring.mail.properties.mail.smtp.starttls.enable}")
	private Boolean enable;
	
	private void send(MimeMessagePreparator preparator) {
		if(enable) {
		mailSender.send(preparator);
		}
		
	}
	//sending notification email for professional when they are hired
	public void sendConfirmationEmail(String emailAdress, String hiredBy, String hiredAddress, String hiredDateTime, 
			String hiredUserContact) {
		
		StringBuilder sb = new StringBuilder();
		
		sb.append("<HTML>");
		sb.append("<p>Congratulations, someone hired you. <br>You are hired by: " + hiredBy + "</p>");
		sb.append("<p>Hired address: " + hiredAddress + "</p>");
		sb.append("<p>Date and time: " + hiredDateTime + "</p>");
		sb.append("<p>User's contact details: " + hiredUserContact + "</p>");
		sb.append("<p>Please vissit GetLinked and go to mywork page to view details</p>");
		sb.append("</HTML>");
		
		
		MimeMessagePreparator preparator = new MimeMessagePreparator(){

			@Override
			public void prepare(MimeMessage mimeMessage) throws Exception {
				
				MimeMessageHelper message = new MimeMessageHelper(mimeMessage);
				
				message.setTo(emailAdress);
				message.setFrom(new InternetAddress("no-reply@findsocial.com"));
				message.setSubject("You are Hired");
				message.setSentDate(new Date());
				message.setText(sb.toString(),true);
				
				
			}
			
		};
		
		send(preparator);
	}
	
	//sending confirmation to the professional when they are verified by the system admin
	public void sendYouareVerifiedEmail(String emailAdress) {
		
		StringBuilder sb = new StringBuilder();
		
		sb.append("<HTML>");
		sb.append("<p>Hello, GetLinked is happy to inform you that your account details are"
				+ " verified now. <br>You can login to the system and view the tasks you are hired for.</p>");
		sb.append("<p>Visit GetLinked to know more</p>");
		sb.append("</HTML>");
		
		MimeMessagePreparator preparator = new MimeMessagePreparator(){

			@Override
			public void prepare(MimeMessage mimeMessage) throws Exception {
				
				MimeMessageHelper message = new MimeMessageHelper(mimeMessage);
				
				message.setTo(emailAdress);
				message.setFrom(new InternetAddress("no-reply@findsocial.com"));
				message.setSubject("Account verified");
				message.setSentDate(new Date());
				message.setText(sb.toString(),true);
			}
		};
		send(preparator);
	}

}
