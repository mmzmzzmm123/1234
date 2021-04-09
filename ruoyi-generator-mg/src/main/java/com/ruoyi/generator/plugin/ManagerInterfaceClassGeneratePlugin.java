package com.ruoyi.generator.plugin;

import com.google.common.collect.Lists;
import org.mybatis.generator.api.GeneratedJavaFile;
import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.dom.java.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class ManagerInterfaceClassGeneratePlugin extends AbstractClassPlugin {

    private Logger log = LoggerFactory.getLogger(ManagerInterfaceClassGeneratePlugin.class);

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

        GeneratedJavaFile _interface = generateManagerInterface(introspectedTable);
        GeneratedJavaFile manager = generateManager(introspectedTable, _interface, daoInterfaceType);
        introspectedTable.getTableConfiguration().getProperties().put(MANAGER_CLASS, _interface);

        if (!existClass(_interface)) {
            list.add(_interface);
            list.add(manager);
            log.info("创建Manager Java文件={}/{}.{}", _interface.getTargetProject(), _interface.getTargetPackage(), _interface.getFileName());
            log.info("创建ManagerImpl Java文件={}/{}.{}", manager.getTargetProject(), manager.getTargetPackage(), manager.getFileName());
        } else if (isOverride(introspectedTable)) {
            list.add(_interface);
            list.add(manager);
            log.info("覆盖Manager Java文件={}/{}.{}", _interface.getTargetProject(), _interface.getTargetPackage(), _interface.getFileName());
            log.info("覆盖ManagerImpl Java文件={}/{}.{}", manager.getTargetProject(), manager.getTargetPackage(), manager.getFileName());
        } else {
            log.info("跳过Manager Java文件={}/{}.{}", _interface.getTargetProject(), _interface.getTargetPackage(), _interface.getFileName());
            log.info("跳过Manager Java文件={}/{}.{}", manager.getTargetProject(), manager.getTargetPackage(), manager.getFileName());
        }

        return list;
    }

    private String getDaoTypeName(IntrospectedTable introspectedTable) {
        FullyQualifiedJavaType entityType = new FullyQualifiedJavaType(introspectedTable.getBaseRecordType());
        return String.format("%s.%s%s", getProperty(introspectedTable, TARGET_MANAGER, null), entityType.getShortName(),
                getProperty(introspectedTable, SUFFIX_MANAGER, "Manager"));
    }

    private String getImplDaoTypeName(IntrospectedTable introspectedTable) {
        FullyQualifiedJavaType entityType = new FullyQualifiedJavaType(introspectedTable.getBaseRecordType());
        return String.format("%s.impl.%s%s", getProperty(introspectedTable, TARGET_MANAGER, null), entityType.getShortName(),
                getProperty(introspectedTable, SUFFIX_MANAGER, "ManagerImpl"));
    }


    private GeneratedJavaFile generateManagerInterface(IntrospectedTable introspectedTable) {
        String classPath = getDaoTypeName(introspectedTable);

        Interface _interface = buildInterface(classPath);

        return buildGeneratedJavaFile(introspectedTable, _interface);
    }

    private GeneratedJavaFile generateManager(IntrospectedTable introspectedTable, GeneratedJavaFile _interface, FullyQualifiedJavaType daoInterfaceType) {
        String classPath = getImplDaoTypeName(introspectedTable);

        TopLevelClass topLevelClass = buildTopLevelClass(classPath);
        // 接口
        topLevelClass.addImportedType(_interface.getCompilationUnit().getType());
        topLevelClass.addSuperInterface(_interface.getCompilationUnit().getType());

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
