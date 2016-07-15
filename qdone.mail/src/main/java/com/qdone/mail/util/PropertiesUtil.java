/******************************************************************************
 * Copyright (C) 2014 Qdone Web Technology Co.Ltd All Rights Reserved. 
 * 本软件为武汉擎动网络科技有限公司开发研制。 未经本公司正式书面同意，其他任何个人、
 * 团体不得使用、复制、修改或发布本软件.
 *******************************************************************************/
package com.qdone.mail.util;

import java.io.InputStream;
import java.util.Properties;

import org.apache.log4j.Logger;

import com.qdone.mail.constant.MailConstant;

/**
 * 
 * PropertiesUtil工具类
 * 
 * @author liqiao
 * @since JDK 1.6
 * @version 1.0
 * @description: TODO
 * @log:2016年5月30日 下午7:30:07 liqiao create
 */
public class PropertiesUtil {
	private static Logger logger = Logger.getLogger(PropertiesUtil.class);
	public static Properties props = new Properties();
	static {
		InputStream configStream = PropertiesUtil.class.getClassLoader().getResourceAsStream(
				MailConstant.CONFIG_FILE_LOCATION);
		try {
			props.load(configStream);
			configStream.close();
		}
		catch (Exception e) {
			logger.error("加载配置资源异常:" + MailConstant.CONFIG_FILE_LOCATION, e);
		}
	}

	public static String getProperty(String key) {
		return props.getProperty(key);
	}

}
