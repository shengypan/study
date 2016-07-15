/******************************************************************************
 * Copyright (C) 2014 Qdone Web Technology Co.Ltd All Rights Reserved. 
 * 本软件为武汉擎动网络科技有限公司开发研制。 未经本公司正式书面同意，其他任何个人、
 * 团体不得使用、复制、修改或发布本软件.
 *****************************************************************************/
package com.qdone.mail.util;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Service;

import com.qdone.freemaker.FreemarkerUtil;
import com.qdone.mail.constant.MailConstant;

/**
 * 邮件发送服务 Module: QdoneMailUtil
 * 
 * @author liqiao
 * @since JDK 1.6
 * @version 1.0
 * @description: TODO
 * @log:2016年6月2日 下午3:01:05 liqiao create
 */
@Service
public class QdoneMailUtil {
	private static final Logger logger = LoggerFactory.getLogger(QdoneMailUtil.class);
	private static JavaMailSender mailSender;
	private static SimpleMailMessage mailMessage = new SimpleMailMessage();
	private static String from = PropertiesUtil.getProperty("email.from");
	static {

		JavaMailSenderImpl javaMailSenderImpl = new JavaMailSenderImpl();
		javaMailSenderImpl.setHost(PropertiesUtil.getProperty(MailConstant.EMAIL_HOST));
		javaMailSenderImpl.setPort(Integer.parseInt(PropertiesUtil.getProperty(MailConstant.EMAIL_PORT)));
		javaMailSenderImpl.setUsername(PropertiesUtil.getProperty(MailConstant.EMAIL_USERNAME));
		javaMailSenderImpl.setPassword(PropertiesUtil.getProperty(MailConstant.EMAIL_PASSWORD));
		javaMailSenderImpl.getJavaMailProperties().setProperty("mail.smtp.auth",
				PropertiesUtil.getProperty(MailConstant.MAIL_SMTP_AUTH));
		javaMailSenderImpl.getJavaMailProperties().setProperty("mail.smtp.timeout",
				PropertiesUtil.getProperty(MailConstant.MAIL_SMTP_TIMEOUT));

		mailSender = javaMailSenderImpl;
	}

	private QdoneMailUtil() {

	}

	/**
	 * 发送邮件
	 * 
	 * @param toAddress 接收人邮箱如有多个人，以;隔开
	 * @param subject 邮件主题
	 * @param content 邮件内容
	 */
	public static void sendSimpleMail(final String toAddress, final String subject, final String content) {
		String[] toAddresses = StringUtils.split(toAddress, ";");
		logger.info("begin send SimpleMail ……");
		mailMessage.setFrom(from);
		mailMessage.setTo(toAddresses);
		mailMessage.setSubject(subject);
		mailMessage.setText(content);
		mailSender.send(mailMessage);
		logger.info(" end send SimpleMail  ……");
	}

	/**
	 * @description: 发送模板邮件
	 * @param model 存储变量数据
	 * @param toAddress 接收人邮箱如有多个人，以;隔开
	 * @param subject 邮件主题
	 * @param templateFileName 邮件模板名称 模板必须放在TEMPLATES_DIR_LOCATION常量下面
	 */
	public static void sendTemplateMail(final Map<String, Object> model, final String toAddress, final String subject,
			final String templateFileDir, final String templateFileName) {
		logger.info("begin send TemplateMail ……");
		final String[] toAddresses = StringUtils.split(toAddress, ";");
		MimeMessagePreparator preparator = new MimeMessagePreparator() {
			public void prepare(MimeMessage mimeMessage) throws MessagingException, IOException {
				MimeMessageHelper message = new MimeMessageHelper(mimeMessage, true, "UTF-8");
				message.setTo(toAddresses);
				message.setSubject(subject);
				message.setFrom(from);
				String text = FreemarkerUtil.exportString(model, templateFileDir, templateFileName);
				message.setText(text, true);
			}
		};
		mailSender.send(preparator);
		logger.info(" end send TemplateMail  ……");
	}

	/**
	 * @description: 发送模板邮件
	 * @param model 存储变量数据
	 * @param toAddress 接收人邮箱如有多个人，以;隔开
	 * @param copyToAddress 抄送人邮箱如有多个人，以;隔开
	 * @param subject 邮件主题
	 * @param templateFileName 邮件模板名称 模板必须放在TEMPLATES_DIR_LOCATION常量下面
	 */
	public static void sendCopyToTemplateMail(final Map<String, Object> model, final String toAddress,
			final List<String> copyToAddressList, final String subject, final String templateFileDir,
			final String templateFileName) {
		logger.info("begin send CopyToTemplateMail ……");
		final String[] toAddresses = StringUtils.split(toAddress, ";");
		MimeMessagePreparator preparator = new MimeMessagePreparator() {
			public void prepare(MimeMessage mimeMessage) throws MessagingException, IOException {
				MimeMessageHelper message = new MimeMessageHelper(mimeMessage, true, "UTF-8");
				message.setTo(toAddresses);

				if (copyToAddressList != null && copyToAddressList.size() > 0) {
					for (String cc : copyToAddressList) {
						message.addCc(cc);// 抄送人
					}

				} else {
					logger.info("copyToAddressList  为空");
				}

				message.setSubject(subject);
				message.setFrom(from);
				String text = FreemarkerUtil.exportString(model, templateFileDir, templateFileName);
				message.setText(text, true);
			}
		};
		mailSender.send(preparator);
		logger.info(" end send CopyToTemplateMail  ……");
	}
}
