package com.jms.MailController;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.jms.MailService.MailService;
import com.jms.ReceiverBean.ReceiverBean;
import com.jms.SenderBean.SenderBean;

@CrossOrigin
@RestController
public class MailController {
	
	@Autowired
	private MailService mailService;
	
	
	
	@RequestMapping(value = "/mailService/sendMail", method = RequestMethod.POST,
			consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.TEXT_HTML_VALUE)
	public ResponseEntity<String> toSendMail(@RequestBody ReceiverBean rd){
		
		try {
			mailService.sendMail(rd);
			return new ResponseEntity<String>("Mail sent successfully to mail id:"+rd.getReceiverMailId(), HttpStatus.OK);
		}
		catch(Exception e){
			System.out.println(e.getMessage());
			return new ResponseEntity<String>("Unable send mail please try again", HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}
