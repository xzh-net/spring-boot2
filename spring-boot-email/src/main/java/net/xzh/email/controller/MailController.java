package net.xzh.email.controller;

import java.io.File;
import java.io.UnsupportedEncodingException;

import javax.mail.MessagingException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

/**
 * 发送邮件
 * 
 * @author Administrator
 *
 */
@RestController
public class MailController {

	private static final Logger log = LoggerFactory.getLogger(MailController.class);

	@Autowired
	private JavaMailSender javaMailSender;

	@Autowired
	TemplateEngine templateEngine;

	@Value("${spring.mail.fromAddr}")
	private String from;

	@Value("${spring.mail.nickName}")
	private String nickName;

	private String to = "xcg992224@163.com";

	/**
	 * 发送文本邮件
	 * @return
	 */
	@RequestMapping(value = "/sendTextMail", method = RequestMethod.POST)
	public String sendTextMail() {
		SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
		simpleMailMessage.setFrom(nickName + "<" + from + ">");
		simpleMailMessage.setTo(to);
		simpleMailMessage.setSubject("这是一个文本邮件");
		simpleMailMessage.setText("文本邮件内容在这里");
		try {
			javaMailSender.send(simpleMailMessage);
			log.debug("文本邮件发送成功");
		} catch (Exception e) {
			log.error("文本邮件发送异常", e);
		}
		return "发送成功";
	}

	/**
	 * 发送html邮件
	 * @return
	 */
	@RequestMapping(value = "/sendHtmlEmail", method = RequestMethod.POST)
	public String sendHtmlEmail() {
		MimeMessage message = javaMailSender.createMimeMessage();
		try {
			MimeMessageHelper messageHelper = new MimeMessageHelper(message, true);
			messageHelper.setFrom(new InternetAddress(from, nickName, "UTF-8"));
			messageHelper.setTo(to);
			messageHelper.setSubject("这是一个html邮件");
			messageHelper.setText("html邮件内容在<a>这里<a>", true);
			javaMailSender.send(message);
			log.debug("html邮件发送成功");
		} catch (MessagingException e) {
			log.error("html邮件发送失败，{}", e);
		} catch (UnsupportedEncodingException e) {
			log.error("html邮件编码异常，{}", e);
		}
		return "发送成功";
	}

	/**
	 * 发送html模板邮件
	 * @return
	 */
	@RequestMapping(value = "/sendHtmlemplateMail", method = RequestMethod.POST)
	public String sendHtmlemplateMail() {
		Context context = new Context();
		context.setVariable("code", "123456");
		String htmlTemplate = templateEngine.process("email", context);
		MimeMessage message = javaMailSender.createMimeMessage();
		try {
			MimeMessageHelper messageHelper = new MimeMessageHelper(message, true);
			messageHelper.setFrom(new InternetAddress(from, nickName, "UTF-8"));
			messageHelper.setTo(to);
			messageHelper.setSubject("这是一个html模板邮件");
			messageHelper.setText(htmlTemplate, true);
			javaMailSender.send(message);
			log.debug("html模板邮件发送成功");
		} catch (MessagingException e) {
			log.error("html模板邮件发送失败，{}", e);
		} catch (UnsupportedEncodingException e) {
			log.error("html模板邮件编码异常，{}", e);
		}
		return "发送成功";
	}
	
	/**
	 * 发送附件邮件
	 * @return
	 */
	@RequestMapping(value = "/sendAttachmentsMail", method = RequestMethod.POST)
	public String sendAttachmentsMail() {
		String fileName = "图片.jpg";
		String filePath = "D:\\1791.jpg";
		MimeMessage message = javaMailSender.createMimeMessage();
		try {
			MimeMessageHelper messageHelper = new MimeMessageHelper(message, true);
			messageHelper.setFrom(new InternetAddress(from, nickName, "UTF-8"));
			messageHelper.setTo(to);
			messageHelper.setSubject("测试带附件的邮件");
			messageHelper.setText("详细请查阅附件<a href='http://www.baidu.com' target='_blank'>准则<a>", true);
			FileSystemResource file = new FileSystemResource(new File(filePath));
			messageHelper.addAttachment(fileName, file);
			javaMailSender.send(message);
			log.debug("附件邮件发送成功");
		} catch (MessagingException e) {
			log.error("附件邮件发送失败，{}", e);
		} catch (UnsupportedEncodingException e) {
			log.error("附件邮件编码异常，{}", e);
		}
		return "发送成功";
	}
}