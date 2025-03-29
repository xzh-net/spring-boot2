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

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import net.xzh.email.common.model.CommonResult;

/**
 * 发送邮件
 * 
 * @author Administrator
 *
 */
@Api(tags = "发送邮件")
@RestController
public class MailController {
	
	 private static final Logger LOGGER = LoggerFactory.getLogger(MailController.class);
	 
	@Autowired
	private JavaMailSender javaMailSender;

	@Autowired
	TemplateEngine templateEngine;
	@Value("${spring.mail.fromAddr}")
	private String from;
	@Value("${spring.mail.nickName}")
	private String nickName;
	private String to = "xcg992224@163.com";

	
	@ApiOperation("文本邮件")
	@RequestMapping(value = "/sendTextMail", method = RequestMethod.POST)
	public CommonResult<?> sendTextMail() {
		SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
		simpleMailMessage.setFrom(nickName + "<" + from + ">");
		simpleMailMessage.setTo(to);
		simpleMailMessage.setSubject("这是一个文本邮件");
		simpleMailMessage.setText("文本邮件内容在这里");
		try {
			javaMailSender.send(simpleMailMessage);
			LOGGER.info("文本邮件发送成功");
		} catch (Exception e) {
			LOGGER.error("文本邮件发送异常", e);
		}
		return CommonResult.success(System.currentTimeMillis());
	}

	
	@ApiOperation("html邮件")
	@RequestMapping(value = "/sendHtmlEmail", method = RequestMethod.POST)
	public CommonResult<?> sendHtmlEmail() {
		MimeMessage message = javaMailSender.createMimeMessage();
		try {
			MimeMessageHelper messageHelper = new MimeMessageHelper(message, true);
			messageHelper.setFrom(new InternetAddress(from, nickName, "UTF-8"));
			messageHelper.setTo(to);
			messageHelper.setSubject("这是一个html邮件");
			messageHelper.setText("html邮件内容在<a>这里<a>", true);
			javaMailSender.send(message);
			LOGGER.info("html邮件发送成功");
		} catch (MessagingException e) {
			LOGGER.error("html邮件发送失败，{}", e);
		} catch (UnsupportedEncodingException e) {
			LOGGER.error("html邮件编码异常，{}", e);
		}
		return CommonResult.success(System.currentTimeMillis());
	}

	@ApiOperation("html模板邮件")
	@RequestMapping(value = "/sendHtmlemplateMail", method = RequestMethod.POST)
	public CommonResult<?> sendHtmlemplateMail() {
		Context context = new Context();
		context.setVariable("code", "123456");
		String emailHTMLContent = templateEngine.process("email", context);
		MimeMessage message = javaMailSender.createMimeMessage();
		try {
			MimeMessageHelper messageHelper = new MimeMessageHelper(message, true);
			messageHelper.setFrom(new InternetAddress(from, nickName, "UTF-8"));
			messageHelper.setTo(to);
			messageHelper.setSubject("这是一个html模板邮件");
			messageHelper.setText(emailHTMLContent, true);
			javaMailSender.send(message);
			LOGGER.info("html模板邮件发送成功");
		}catch (MessagingException e) {
			LOGGER.error("html模板邮件发送失败，{}", e);
		} catch (UnsupportedEncodingException e) {
			LOGGER.error("html模板邮件编码异常，{}", e);
		}
		return CommonResult.success(System.currentTimeMillis());
	}

	@ApiOperation("附件邮件")
	@RequestMapping(value = "/sendAttachmentsMail", method = RequestMethod.POST)
	public CommonResult<?> sendAttachmentsMail() {
		String fileName = "图片.jpg";
		String filePath = "D:\\aa.jpg";
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
			LOGGER.info("附件邮件发送成功");
		} catch (MessagingException e) {
			LOGGER.error("附件邮件发送失败，{}", e);
		} catch (UnsupportedEncodingException e) {
			LOGGER.error("附件邮件编码异常，{}", e);
		}
		return CommonResult.success(System.currentTimeMillis());
	}
}