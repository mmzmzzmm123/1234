package com.ruoyi.generator.plugin;

import com.google.common.collect.Lists;
import com.ruoyi.generator.util.Constant;
import org.mybatis.generator.api.GeneratedJavaFile;
import org.mybatis.generator.api.GeneratedXmlFile;
import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.dom.java.FullyQualifiedJavaType;
import org.mybatis.generator.api.dom.java.Interface;
import org.mybatis.generator.api.dom.xml.Attribute;
import org.mybatis.generator.api.dom.xml.Document;
import org.mybatis.generator.api.dom.xml.XmlElement;
import org.mybatis.generator.codegen.XmlConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class DaoClassGeneratePlugin extends AbstractClassPlugin {

    private Logger log = LoggerFactory.getLogger(DaoClassGeneratePlugin.class);

    /**
     * 生成接口class
     */
    @Override
    public List<GeneratedJavaFile> contextGenerateAdditionalJavaFiles(IntrospectedTable introspectedTable) {
        List<GeneratedJavaFile> list = Lists.newArrayList();

        GeneratedJavaFile daoInterface = generateDaoInterface(introspectedTable);
        introspectedTable.getTableConfiguration().getProperties().put(DAO_INTERFACE, daoInterface);

        if (!existClass(daoInterface)) {
            list.add(daoInterface);
            log.info("创建DAO Java文件={}/{}.{}", daoInterface.getTargetProject(),
                daoInterface.getTargetPackage(), daoInterface.getFileName());
        } else if (isOverride(introspectedTable)) {
            list.add(daoInterface);
            log.info("覆盖DAO Java文件={}/{}.{}", daoInterface.getTargetProject(),
                daoInterface.getTargetPackage(), daoInterface.getFileName());
        } else {
            log.info("跳过DAO Java文件={}/{}.{}", daoInterface.getTargetProject(),
                daoInterface.getTargetPackage(), daoInterface.getFileName());
        }

        return list;
    }

    private String getDaoTypeName(IntrospectedTable introspectedTable) {
        FullyQualifiedJavaType entityType = new FullyQualifiedJavaType(
            introspectedTable.getBaseRecordType());
        return String.format("%s.%s%s", getProperty(introspectedTable, Constant.TARGET_DAO, null),
            entityType.getShortName(), getProperty(introspectedTable, Constant.SUFFIX_DAO, "DAO"));
    }

    private String getDaoXmlName(IntrospectedTable introspectedTable) {
        FullyQualifiedJavaType entityType = new FullyQualifiedJavaType(
            introspectedTable.getBaseRecordType());
        return String.format("%s%s.xml", entityType.getShortName(),
            getProperty(introspectedTable, Constant.SUFFIX_DAO, "DAO"));
    }

    private GeneratedJavaFile generateDaoInterface(IntrospectedTable introspectedTable) {
        String classPath = getDaoTypeName(introspectedTable);

        Interface _interface = buildInterface(classPath);
        // 继承mybatis生成的dao
        FullyQualifiedJavaType superInterface = new FullyQualifiedJavaType(
            introspectedTable.getMyBatis3JavaMapperType());
        _interface.addImportedType(superInterface);
        _interface.addSuperInterface(superInterface);

        return buildGeneratedJavaFile(introspectedTable, _interface);
    }

    /**
     * 生成xml
     */
    @Override
    public List<GeneratedXmlFile> contextGenerateAdditionalXmlFiles(IntrospectedTable introspectedTable) {
        List<GeneratedXmlFile> xmls = Lists.newArrayList();

        GeneratedXmlFile daoXmlFile = generateDaoXml(introspectedTable);
        if (!existClass(daoXmlFile)) {
            xmls.add(daoXmlFile);
            log.info("创建XML文件={}/{}.{}", daoXmlFile.getTargetProject(),
                daoXmlFile.getTargetPackage(), daoXmlFile.getFileName());
        } else if (isOverride(introspectedTable)) {
            xmls.add(daoXmlFile);
            log.info("覆盖XML文件={}/{}.{}", daoXmlFile.getTargetProject(),
                daoXmlFile.getTargetPackage(), daoXmlFile.getFileName());
        } else {
            log.info("跳过XML文件={}/{}.{}", daoXmlFile.getTargetProject(),
                daoXmlFile.getTargetPackage(), daoXmlFile.getFileName());
        }

        return xmls;
    }

    private GeneratedXmlFile generateDaoXml(IntrospectedTable introspectedTable) {
        String classPath = getDaoTypeName(introspectedTable);

        XmlElement rootElement = new XmlElement("mapper");
        rootElement.addAttribute(new Attribute("namespace", classPath));

        Document document = new Document(XmlConstants.MYBATIS3_MAPPER_PUBLIC_ID,
            XmlConstants.MYBATIS3_MAPPER_SYSTEM_ID);
        document.setRootElement(rootElement);

        String fileName = getDaoXmlName(introspectedTable);

        return buildGeneratedXmlFile(introspectedTable, document, fileName);
    }

    /**
     * 修改 namespace 为生成的DAO
     */
    @Override
    public boolean sqlMapDocumentGenerated(Document document, IntrospectedTable introspectedTable) {
        String classPath = getDaoTypeName(introspectedTable);

        List<Attribute> attrs = document.getRootElement().getAttributes();
        for (int i = 0; i < attrs.size(); i++) {
            Attribute attr = attrs.get(i);
            if (attr.getName().equals("namespace")) {
                attrs.remove(i);
                attrs.add(i, new Attribute("namespace", classPath));
            }
        }
        log.info("修改{}.{} =>标签【namespace】={}", introspectedTable.getMyBatis3XmlMapperPackage(),
            introspectedTable.getMyBatis3XmlMapperFileName(), classPath);
        return super.sqlMapDocumentGenerated(document, introspectedTable);
    }

    @Override
    public boolean sqlMapInsertSelectiveElementGenerated(XmlElement element,
                                                         IntrospectedTable introspectedTable) {
        // updateKeyColumn(element.getAttributes());
        return true;
    }

    @Override
    public boolean sqlMapInsertElementGenerated(XmlElement element,
                                                IntrospectedTable introspectedTable) {
        // updateKeyColumn(element.getAttributes());
        return true;
    }

}
