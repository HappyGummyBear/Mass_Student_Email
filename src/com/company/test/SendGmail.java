package com.company.test;

/**
 * Class send email
 * 
 * @author Shook Kirk
 * 
 *version 1.0
 *
 *since 06/26/2020
 */

import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class SendGmail {
	
	// Strings to hold user email and password
	private String userEmail;
	private String userPassword;
	
	// Count to keep track of number of emails not sent
	public int errorCount = 0;
	
	// Constructor
	public SendGmail(String u, String p) {
		userEmail = u;
		userPassword = p;
	}
	
	// Getter for error count
	public int getErrorCount() {
		return errorCount;
	}
	
	// Method to sent email
	public void sendMail(String reciever, String subjectLine, String bodyLine) throws MessagingException {
		// Info for console
		System.out.print("Preparing email.");
		
		Properties properties = new Properties();
		properties.put("mail.smtp.auth","true");
		properties.put("mail.smtp.starttls.enable", "true");
		properties.put("mail.smtp.host", "smtp.gmail.com");
		properties.put("mail.smtp.port", "587");
		
		Session session = Session.getInstance(properties, new Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(userEmail,userPassword);
			}
		});
		
		Message message = prepareMessage(session,userEmail, reciever, subjectLine, bodyLine);
		
		Transport.send(message);
		
		System.out.print("Message sent succesfully.");
	}
	
	// Method to create email message to send
	private Message prepareMessage(Session session, String myAccountEmail, String reciever,String subjectLine, String bodyLine) {
		Message message = new MimeMessage(session);
		try {
			message.setFrom(new InternetAddress(myAccountEmail));
			message.addRecipient(Message.RecipientType.TO, new InternetAddress(reciever));
			message.setSubject(subjectLine);
			message.setText(bodyLine);
			return message;
		} catch (Exception ex) {
			++errorCount; // if error appears then count goes up by one
		}
		return null;
	}
}
