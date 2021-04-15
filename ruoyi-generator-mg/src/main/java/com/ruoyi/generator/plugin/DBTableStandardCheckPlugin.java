package com.ruoyi.generator.plugin;

import com.google.common.collect.Lists;
import org.apache.commons.lang3.Validate;
import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.dom.java.TopLevelClass;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class DBTableStandardCheckPlugin extends AbstractClassPlugin {

	private Logger log = LoggerFactory.getLogger(DBTableStandardCheckPlugin.class);

	private String[] columns = { "isDeleted", "createdBy", "createdAt", "updatedBy", "updatedAt" };

	@Override
	public boolean modelBaseRecordClassGenerated(TopLevelClass topLevelClass, IntrospectedTable introspectedTable) {
		if (introspectedTable.getTableType().equalsIgnoreCase("VIEW")) {
			log.warn("视图表={} 忽略规范检查", introspectedTable.getFullyQualifiedTableNameAtRuntime());
			return true;
		}

		boolean[] flags = new boolean[columns.length];
		topLevelClass.getFields().forEach(field -> {
			for (int i = 0; i < columns.length; i++) {
				if (columns[i].equalsIgnoreCase(field.getName())) {
					flags[i] = true;
				}
			}
		});
		List<String> unsetColumns = Lists.newArrayList();
		for (int i = 0; i < flags.length; i++) {
			if (!flags[i]) {
				unsetColumns.add(columns[i]);
			}
		}

		Validate.isTrue(!unsetColumns.isEmpty(), "表={},需要按照规范设置以下字段【{}】，未设置字段={}",
				introspectedTable.getFullyQualifiedTableNameAtRuntime(), columns, unsetColumns);

		log.info("数据表规范检查完毕");
		return true;
	}

}
