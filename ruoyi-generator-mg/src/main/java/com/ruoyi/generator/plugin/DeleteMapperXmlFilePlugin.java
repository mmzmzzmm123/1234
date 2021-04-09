package com.ruoyi.generator.plugin;

import org.mybatis.generator.api.GeneratedXmlFile;
import org.mybatis.generator.api.IntrospectedTable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.util.List;

public class DeleteMapperXmlFilePlugin extends AbstractClassPlugin {

	private Logger log = LoggerFactory.getLogger(DeleteMapperXmlFilePlugin.class);
	
	@Override
	public List<GeneratedXmlFile> contextGenerateAdditionalXmlFiles(IntrospectedTable introspectedTable) {
		introspectedTable.getGeneratedXmlFiles().forEach(xml -> {
			File xmlFile = new File(String.format("%s/%s/%s", xml.getTargetProject(), xml.getTargetPackage().replaceAll("[.]", "/"), xml.getFileName()));
			if (xmlFile.exists()) {
				log.info("删除已存在的MyBatis XML文件={}", xmlFile.getPath());
				xmlFile.delete();
			}
		});
		
		return super.contextGenerateAdditionalXmlFiles(introspectedTable);
	}
	
}
