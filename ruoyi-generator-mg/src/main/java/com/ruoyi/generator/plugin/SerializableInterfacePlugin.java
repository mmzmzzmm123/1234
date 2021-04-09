package com.ruoyi.generator.plugin;

import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.dom.java.Field;
import org.mybatis.generator.api.dom.java.FullyQualifiedJavaType;
import org.mybatis.generator.api.dom.java.JavaVisibility;
import org.mybatis.generator.api.dom.java.TopLevelClass;

/**
 * 实现序列化接口
 */
public class SerializableInterfacePlugin extends AbstractClassPlugin  {

	private FullyQualifiedJavaType serializableType = new FullyQualifiedJavaType("java.io.Serializable");
	
	private Field serialVersionUIDField = new Field() {
		{
			setVisibility(JavaVisibility.PRIVATE);
			setStatic(true);
			setFinal(true);
	        setType(new FullyQualifiedJavaType("long"));
	        setName("serialVersionUID");
	        setInitializationString("1L");
		}
	};

	@Override
	public boolean modelBaseRecordClassGenerated(TopLevelClass topLevelClass, IntrospectedTable introspectedTable) {
		topLevelClass.addImportedType(serializableType);
		topLevelClass.addSuperInterface(serializableType);
		topLevelClass.addField(serialVersionUIDField);
		
//		private static final long serialVersionUID = 1L;
		return super.modelBaseRecordClassGenerated(topLevelClass, introspectedTable);
	}
}
