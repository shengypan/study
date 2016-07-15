package com.qdone.freemaker;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import freemarker.template.Configuration;
import freemarker.template.Template;

/**
 * 
 * FreemarkerUtil:Freemarker工具类
 * 
 * @author liqiao
 * @since JDK 1.6
 * @version 1.0
 * @description: TODO
 * @log:2016年6月2日 下午2:59:39 liqiao create
 */
public final class FreemarkerUtil {
	private static final Logger logger = LoggerFactory.getLogger(FreemarkerUtil.class);
	/** 模板文件编码. */
	private static final String TEMPLATE_FILE_ENCODING = "UTF-8";
	/** 导出文件编码 . */
	private static final String FILE_OUTPUT_ENCODING = "UTF-8";

	/**
	 * 私有构造器.
	 */
	private FreemarkerUtil() {

	}

	/**
	 * @title fileExport,可以实现由Word或者Excel模板的文件导出功能!!!
	 *        (通过将Word和Excel转换为XML作为模板来进行数据填充，进行导出功能的实现！)
	 * @param data 待填充数据
	 * @param templateDirectory 模板文件所有目录
	 * @param templateFileName 模板文件名称
	 * @param fileoutputDirectory 文件输出目录
	 * @param fileoutputFileName 输出文件名
	 * @date 2016年2月2日 liqiao created
	 */
	@SuppressWarnings("unchecked")
	public static void fileExport(final Map<String, Object> data, final String templateDirectory,
			final String templateFileName, final String fileoutputPath) {
		try {
			Configuration configuration = new Configuration();
			configuration.setDefaultEncoding(FreemarkerUtil.TEMPLATE_FILE_ENCODING);
			File templateDirectoryFile = new File(templateDirectory);
			if (!templateDirectoryFile.exists()) {
				logger.info("freemarker模板目录不存在:" + templateDirectory);
				return;
			}
			configuration.setDirectoryForTemplateLoading(templateDirectoryFile);
			Template template = configuration.getTemplate(templateFileName);
			File outFile = new File(fileoutputPath);
			if (!outFile.getParentFile().exists()) {
				outFile.getParentFile().mkdirs();
			}
			Writer writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(outFile),
					FreemarkerUtil.FILE_OUTPUT_ENCODING));
			template.process(data, writer);
			writer.flush();
			writer.close();
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 
	 * @title exportString
	 * @param data 数据模型
	 * @param templateDirectory 模板文件目录
	 * @param templateFileName 模板文件名
	 * @return 生成的字符串输出
	 * @date 2016年6月2日 liqiao created
	 */
	public static String exportString(final Map<String, Object> data, final String templateDirectory,
			final String templateFileName) {
		try {
			StringBuffer resultSB = new StringBuffer();
			Configuration configuration = new Configuration();
			configuration.setDefaultEncoding(FreemarkerUtil.TEMPLATE_FILE_ENCODING);
			File templateDirectoryFile = new File(templateDirectory);
			if (!templateDirectoryFile.exists()) {
				logger.info("freemarker模板目录不存在:" + templateDirectory);
				return null;
			}
			configuration.setDirectoryForTemplateLoading(templateDirectoryFile);
			Template template = configuration.getTemplate(templateFileName);
			if (StringUtils.isEmpty(templateFileName) || template == null) {
				logger.info("freemarker模板目录不存在:" + templateDirectory);
				return null;
			}
			StringWriter writer = new StringWriter(1024);
			resultSB = writer.getBuffer();
			template.process(data, writer);
			writer.flush();
			writer.close();
			return resultSB.toString();
		}
		catch (Exception e) {
			logger.error("freemarker异常", e);
			return null;
		}

	}

}
