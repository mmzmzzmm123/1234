package com.ruoyi.generator.plugin;

import com.google.common.collect.Lists;
import org.mybatis.generator.api.GeneratedJavaFile;
import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.dom.java.Field;
import org.mybatis.generator.api.dom.java.FullyQualifiedJavaType;
import org.mybatis.generator.api.dom.java.JavaVisibility;
import org.mybatis.generator.api.dom.java.TopLevelClass;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class ManagerClassGeneratePlugin extends AbstractClassPlugin {

	private Logger log = LoggerFactory.getLogger(ManagerClassGeneratePlugin.class);

    @Override
    public List<GeneratedJavaFile> contextGenerateAdditionalJavaFiles(IntrospectedTable introspectedTable) {
        List<GeneratedJavaFile> list = Lists.newArrayList();
        
        FullyQualifiedJavaType daoInterfaceType;
        if (introspectedTable.getTableConfiguration().getProperties().containsKey(DAO_INTERFACE)) {
        	 GeneratedJavaFile daoInterface = (GeneratedJavaFile) introspectedTable.getTableConfiguration().getProperties().get(DAO_INTERFACE);
        	 daoInterfaceType = daoInterface.getCompilationUnit().getType();
        } else {
        	daoInterfaceType = new FullyQualifiedJavaType(introspectedTable.getMyBatis3JavaMapperType());
        }
        
        GeneratedJavaFile manager = generateManager(introspectedTable, daoInterfaceType);
        introspectedTable.getTableConfiguration().getProperties().put(MANAGER_CLASS, manager);
        
        if (!existClass(manager)) {
			list.add(manager);
			log.info("创建Manager Java文件={}/{}.{}", manager.getTargetProject(), manager.getTargetPackage(), manager.getFileName());
		} else if (isOverride(introspectedTable)) {
			list.add(manager);
			log.info("覆盖Manager Java文件={}/{}.{}", manager.getTargetProject(), manager.getTargetPackage(), manager.getFileName());
		} else {
			log.info("跳过Manager Java文件={}/{}.{}", manager.getTargetProject(), manager.getTargetPackage(), manager.getFileName());
		}

        return list;
    }
    
    private String getDaoTypeName(IntrospectedTable introspectedTable) {
		FullyQualifiedJavaType entityType = new FullyQualifiedJavaType(introspectedTable.getBaseRecordType());
		return String.format("%s.%s%s", getProperty(introspectedTable, TARGET_MANAGER, null), entityType.getShortName(),
				getProperty(introspectedTable, SUFFIX_MANAGER, "Manager"));
	}

    private GeneratedJavaFile generateManager(IntrospectedTable introspectedTable, FullyQualifiedJavaType daoInterfaceType) {
        String classPath =getDaoTypeName(introspectedTable);

        TopLevelClass topLevelClass = buildTopLevelClass(classPath);

        topLevelClass.addImportedType("org.springframework.stereotype.Service");
        topLevelClass.addAnnotation("@Service");

        topLevelClass.addImportedType("org.springframework.beans.factory.annotation.Autowired");

        topLevelClass.addImportedType(daoInterfaceType);

        Field daoField = new Field();
        daoField.addAnnotation("@Autowired");
        daoField.setName("dao");
        daoField.setType(daoInterfaceType);
        daoField.setVisibility(JavaVisibility.PRIVATE);

        topLevelClass.addField(daoField);

        return buildGeneratedJavaFile(introspectedTable, topLevelClass);
    }

}
