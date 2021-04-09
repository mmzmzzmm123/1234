package com.ruoyi.generator.plugin;

import com.google.common.collect.Lists;
import com.ruoyi.common.utils.StringUtils;
import org.mybatis.generator.api.CommentGenerator;
import org.mybatis.generator.api.IntrospectedColumn;
import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.dom.java.*;
import org.mybatis.generator.api.dom.xml.XmlElement;
import org.mybatis.generator.internal.util.StringUtility;

import java.util.List;
import java.util.Properties;
import java.util.Set;

/**
 * 定制注释生成
 * 
 * @author shaon
 *
 */
//todo
public class CustomizedCommentGenerator implements CommentGenerator {

	private FullyQualifiedJavaType STRING_JAVA_TYPE  = new FullyQualifiedJavaType(String.class.getName());

	@Override
	public void addConfigurationProperties(Properties properties) {
	}

	private void addFieldColumnComment(JavaElement element, IntrospectedTable introspectedTable,
                                       IntrospectedColumn introspectedColumn) {
		element.addJavaDocLine("/**");
		element.addJavaDocLine(" *");
		String remarks = introspectedColumn.getRemarks();
		if (StringUtility.stringHasValue(remarks)) {
			String[] remarkLines = remarks.split(System.getProperty("line.separator"));
			for (String remarkLine : remarkLines) {
				element.addJavaDocLine(" *   " + remarkLine + "    <br>");
			}
			element.addJavaDocLine(" *<br>");
		}
		element.addJavaDocLine(String.format(" * 字段名： %s.%s", introspectedTable.getFullyQualifiedTable(),
				introspectedColumn.getActualColumnName()));

		element.addJavaDocLine(" */");
	}

	private void addTableComment(JavaElement element, IntrospectedTable introspectedTable) {
		element.addJavaDocLine("/**");
		element.addJavaDocLine(" *");
		String remarks = introspectedTable.getRemarks();
		if (StringUtility.stringHasValue(remarks)) {
			String[] remarkLines = remarks.split(System.getProperty("line.separator"));
			for (String remarkLine : remarkLines) {
				element.addJavaDocLine(" *   " + remarkLine + "<br>");
			}
		}
		element.addJavaDocLine(" *<br>");
		element.addJavaDocLine(String.format(" * 数据表： %s", introspectedTable.getFullyQualifiedTable()));
		element.addJavaDocLine(" *");
		element.addJavaDocLine(" */");

		if (StringUtils.isNotEmpty(introspectedTable.getRemarks())) {
			element.addAnnotation(
					String.format("@Description({\"{}\"})", introspectedTable.getRemarks()));
		}
	}

	@Override
	public void addFieldComment(Field field, IntrospectedTable introspectedTable,
                                IntrospectedColumn introspectedColumn) {
		addFieldColumnComment(field, introspectedTable, introspectedColumn);

		String remarks = introspectedColumn.getRemarks();
		if (StringUtility.stringHasValue(remarks)) {
			List<String> remarkLines = Lists.newArrayList(remarks.split(System.getProperty("line.separator")));
			if (!introspectedColumn.isNullable()) {
				remarkLines.add("不能为空");
			}
			remarkLines.add("数据长度: " + introspectedColumn.getLength());
			field.addAnnotation(
					String.format("@Description({\"{}\"})", StringUtils.join(remarkLines, "\", \"")));
		}

		if (!introspectedColumn.isNullable()) {
			if ( STRING_JAVA_TYPE.compareTo(introspectedColumn.getFullyQualifiedJavaType()) == 0) {
				field.addAnnotation("@NotBlank");
			} else {
				field.addAnnotation("@NotNull");
			}
		}
	}

	@Override
	public void addFieldComment(Field field, IntrospectedTable introspectedTable) {

	}

	@Override
	public void addModelClassComment(TopLevelClass topLevelClass, IntrospectedTable introspectedTable) {
		addTableComment(topLevelClass, introspectedTable);
	}

	@Override
	public void addClassComment(InnerClass innerClass, IntrospectedTable introspectedTable) {

	}

	@Override
	public void addClassComment(InnerClass innerClass, IntrospectedTable introspectedTable, boolean markAsDoNotDelete) {
	}

	@Override
	public void addEnumComment(InnerEnum innerEnum, IntrospectedTable introspectedTable) {

	}

	@Override
	public void addGetterComment(Method method, IntrospectedTable introspectedTable,
                                 IntrospectedColumn introspectedColumn) {
		method.addJavaDocLine("/**");
		method.addJavaDocLine(" * 获取参数<br><br>");
		method.addJavaDocLine(" *");
		String remarks = introspectedColumn.getRemarks();
		if (StringUtility.stringHasValue(remarks)) {
			String[] remarkLines = remarks.split(System.getProperty("line.separator"));
			for (String remarkLine : remarkLines) {
				method.addJavaDocLine(" *   " + remarkLine + "    <br>");
			}
			method.addJavaDocLine(" *<br>");
		}
		method.addJavaDocLine(String.format(" * 字段名： %s.%s", introspectedTable.getFullyQualifiedTable(),
				introspectedColumn.getActualColumnName()));

		method.addJavaDocLine(" */");
	}

	@Override
	public void addSetterComment(Method method, IntrospectedTable introspectedTable,
                                 IntrospectedColumn introspectedColumn) {
		method.addJavaDocLine("/**");
		method.addJavaDocLine(" * 设置参数<br><br>");
		method.addJavaDocLine(" *");
		String remarks = introspectedColumn.getRemarks();
		if (StringUtility.stringHasValue(remarks)) {
			String[] remarkLines = remarks.split(System.getProperty("line.separator"));
			for (String remarkLine : remarkLines) {
				method.addJavaDocLine(" *   " + remarkLine + "<br>");
			}
			method.addJavaDocLine(" *<br>");
		}
		method.addJavaDocLine(String.format(" * 字段名： %s.%s", introspectedTable.getFullyQualifiedTable(),
				introspectedColumn.getActualColumnName()));

		method.addJavaDocLine(" */");
	}

	@Override
	public void addGeneralMethodComment(Method method, IntrospectedTable introspectedTable) {

	}

	@Override
	public void addJavaFileComment(CompilationUnit compilationUnit) {
		compilationUnit.addImportedType(new FullyQualifiedJavaType("com.vocust.framework.common.annotation.Description"));
		compilationUnit.addImportedType(new FullyQualifiedJavaType("javax.validation.constraints.NotNull"));
		compilationUnit.addImportedType(new FullyQualifiedJavaType("javax.validation.constraints.NotBlank"));
	}

	@Override
	public void addComment(XmlElement xmlElement) {

	}

	@Override
	public void addRootComment(XmlElement rootElement) {

	}

	@Override
	public void addGeneralMethodAnnotation(Method method, IntrospectedTable introspectedTable, Set<FullyQualifiedJavaType> set) {

	}

	@Override
	public void addGeneralMethodAnnotation(Method method, IntrospectedTable introspectedTable, IntrospectedColumn introspectedColumn, Set<FullyQualifiedJavaType> set) {

	}

	@Override
	public void addFieldAnnotation(Field field, IntrospectedTable introspectedTable, Set<FullyQualifiedJavaType> set) {

	}

	@Override
	public void addFieldAnnotation(Field field, IntrospectedTable introspectedTable, IntrospectedColumn introspectedColumn, Set<FullyQualifiedJavaType> set) {

	}

	@Override
	public void addClassAnnotation(InnerClass innerClass, IntrospectedTable introspectedTable, Set<FullyQualifiedJavaType> set) {

	}
}
