package com.masters.utilities.mail;

import java.io.StringWriter;

import javax.mail.internet.MimeMessage;

import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.runtime.RuntimeConstants;
import org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;



public class MailHandler implements Runnable {
	
	private Class<?> mailConfig;
	private String mailTo;
	private String subject; 
	private String mailTemplate;
	
	public MailHandler(Class<?> mailConfig, String mailTo, String subject, String mailTemplate) {
		super();
		this.mailConfig = mailConfig;
		this.mailTo = mailTo;
		this.subject = subject;
		this.mailTemplate = mailTemplate;
	}

	@Override
	public void run() {	
		try (AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext()) {
			context.register(mailConfig);
			context.refresh();
			JavaMailSenderImpl mailSender = context.getBean(JavaMailSenderImpl.class);
			MimeMessage mimeMessage = mailSender.createMimeMessage();
			
			MimeMessageHelper mailMsg = new MimeMessageHelper(mimeMessage);			
			mailMsg.setFrom("noreply@teramatrix.in");
			mailMsg.setTo(mailTo);
			mailMsg.setSubject(subject);
			
			VelocityEngine velocityEngine = new VelocityEngine();
			velocityEngine.setProperty(RuntimeConstants.RESOURCE_LOADER, "classpath");
			velocityEngine.setProperty("classpath.resource.loader.class", ClasspathResourceLoader.class.getName());
			velocityEngine.init();
			
			VelocityContext velocityContext = new VelocityContext();
		    velocityContext.put("username", "User Name");
		    velocityContext.put("email", "Email Address");
		    velocityContext.put("unsubscribe", "Dont want to receive these newsletters?");
		    
		    StringWriter stringWriter = new StringWriter();
		    Template template = velocityEngine.getTemplate(mailTemplate);
		    template.merge(velocityContext, stringWriter);		    
			
		    mimeMessage.setContent(stringWriter.toString().substring(3), "text/html");		    
			mailSender.send(mimeMessage);	
		} catch (Exception e) {
			e.printStackTrace();
		}
	}	
}