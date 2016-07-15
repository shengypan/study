/******************************************************************************
 * Copyright (C) 2014 Qdone Web Technology Co.Ltd All Rights Reserved. 
 * 本软件为武汉擎动网络科技有限公司开发研制。 未经本公司正式书面同意，其他任何个人、
 * 团体不得使用、复制、修改或发布本软件.
 *******************************************************************************/
package qdone.mail;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;

import com.qdone.mail.constant.MailConstant;
import com.qdone.mail.util.QdoneMailUtil;

/**
 * Module: MainTest
 * 
 * @author liqiao
 * @since JDK 1.6
 * @version 1.0
 * @description: TODO
 * @log:2016年6月2日 下午12:56:29 liqiao create
 */
public class MainTest {

	@Test
	public void sendSimpleMail_test() {
		QdoneMailUtil.sendSimpleMail("243368584@qq.com", "简单邮件", "注意，这不是演习，这不是演习");
	}

	@Test
	public void sendTemplateMail_test() {
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("userName", "李桥");
		model.put("password", "123456");
		String toAddress = "liqiao@qdone.com.cn";
		String subject = "模板邮件";
		String templateFileDir = System.getProperty("user.dir") + "/" + MailConstant.TEMPLATES_DIR_LOCATION;
		String templateFileName = "resetPwd.fml";
		QdoneMailUtil.sendTemplateMail(model, toAddress, subject, templateFileDir, templateFileName);
	}

	@Test
	public void sendCopyToTemplateMail_test() {
		Map<String, Object> model = new HashMap<String, Object>();
		for (int i = 0; i < 2; i++) {
			model.put("userName", "1578084221:" + i);
			model.put("password", "123456");
			String toAddress = "243368584@qq.com";
			List<String> copyToAddressList = new ArrayList<String>();
			copyToAddressList.add("jiangsanxiang@qdone.com.cn");
			String subject = "测试邮件";
			String templateFileDir = System.getProperty("user.dir") + "/" + MailConstant.TEMPLATES_DIR_LOCATION;
			String templateFileName = "resetPwd.fml";
			// QdoneMailUtil.sendCopyToTemplateMail(model, toAddress,
			// copyToAddressList, subject, templateFileDir,
			// templateFileName);
		}

	}
}
