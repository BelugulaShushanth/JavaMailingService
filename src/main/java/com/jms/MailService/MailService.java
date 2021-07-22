package com.jms.MailService;

import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.jms.JavaMailingServiceApplication;
import com.jms.ReceiverBean.ReceiverBean;
import com.jms.SenderBean.SenderBean;

@Service
public class MailService {
	
	
	public void sendMail(ReceiverBean rd) throws AddressException,MessagingException {
		SenderBean sd = new SenderBean();
		sd.setSenderMailId("employeemanagementsystem1@gmail.com");
		sd.setSenderPassword("EMS@java8");
		System.out.println("Preparing to send mail...");
		Properties properties = new Properties();
		properties.put("mail.smtp.auth", "true");
		properties.put("mail.smtp.starttls.enable", "true");
		properties.put("mail.smtp.host", "smtp.gmail.com");
		properties.put("mail.smtp.port", "587");
		
		Session session = Session.getInstance(properties, getAuthenticator(sd));
		Message message = prepareMessage(session, sd, rd);
		Transport.send(message);
		System.out.println("Email sent successfully");
	}
	
	private Authenticator getAuthenticator(SenderBean sd) {
		
		Authenticator auth = new Authenticator() {
			@Override
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(sd.getSenderMailId(), sd.getSenderPassword());
			}
		};
		return auth;
	}
	
	private Message prepareMessage(Session session, SenderBean sd, ReceiverBean rd) throws AddressException,MessagingException {
		
			Message message = new MimeMessage(session);
			try {
				message.setFrom(new InternetAddress(sd.getSenderMailId()));
				message.setRecipient(Message.RecipientType.TO, new InternetAddress(rd.getReceiverMailId()));
				message.setSubject(rd.getSubject());
				message.setContent(rd.getMessage(),"text/html");
				return message;
			} catch (AddressException e) {
				e.printStackTrace();
				Logger.getLogger(MailService.class.getName()).log(Level.SEVERE,null,e);
				throw e;
			} catch (MessagingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				throw e;
			}
	}
}