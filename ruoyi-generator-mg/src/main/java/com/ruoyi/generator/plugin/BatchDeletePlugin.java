package com.ruoyi.generator.plugin;

import com.itfsw.mybatis.generator.plugins.utils.FormatTools;
import com.itfsw.mybatis.generator.plugins.utils.JavaElementGeneratorTools;
import com.itfsw.mybatis.generator.plugins.utils.XmlElementGeneratorTools;
import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.dom.java.*;
import org.mybatis.generator.api.dom.xml.*;
import org.mybatis.generator.codegen.mybatis3.ListUtilities;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 批量删除
 */
public class BatchDeletePlugin extends AbstractClassPlugin {
    private Logger log = LoggerFactory.getLogger(AbstractClassPlugin.class);
    public static final String METHOD_BATCH_INSERT = "batchDelete";

    /**
     * Java Client Methods 生成
     * 具体执行顺序 http://www.mybatis.org/generator/reference/pluggingIn.html
     *
     * @param interfaze
     * @param topLevelClass
     * @param introspectedTable
     * @return
     */
    @Override
    public boolean clientGenerated(Interface interfaze, TopLevelClass topLevelClass, IntrospectedTable introspectedTable) {
        // 1. batchInsert
        FullyQualifiedJavaType listType = FullyQualifiedJavaType.getNewArrayListInstance();
        FullyQualifiedJavaType pkFullyQualifiedJavaType = introspectedTable.getPrimaryKeyColumns().get(0).getFullyQualifiedJavaType();
        listType.addTypeArgument(introspectedTable.getRules().calculateAllFieldsClass());
        Method mBatchDelete = JavaElementGeneratorTools.generateMethod(
                METHOD_BATCH_INSERT,
                JavaVisibility.DEFAULT,
                FullyQualifiedJavaType.getIntInstance(),
                new Parameter(pkFullyQualifiedJavaType, "ids", "@Param(\"ids\")")

        );
        commentGenerator.addGeneralMethodComment(mBatchDelete, introspectedTable);
        // interface 增加方法
        FormatTools.addMethodWithBestPosition(interfaze, mBatchDelete);
        log.debug("(批量插入插件):" + interfaze.getType().getShortName() + "增加batchDelete方法。");

        return true;
    }

    /**
     * SQL Map Methods 生成
     * 具体执行顺序 http://www.mybatis.org/generator/reference/pluggingIn.html
     *
     * @param document
     * @param introspectedTable
     * @return
     */
    @Override
    public boolean sqlMapDocumentGenerated(Document document, IntrospectedTable introspectedTable) {
        // 1. batchInsert
        XmlElement batchDeleteEle = new XmlElement("delete");
        batchDeleteEle.addAttribute(new Attribute("id", METHOD_BATCH_INSERT));
        // 参数类型
        batchDeleteEle.addAttribute(new Attribute("parameterType", "String"));
        // 添加注释(!!!必须添加注释，overwrite覆盖生成时，@see XmlFileMergerJaxp.isGeneratedNode会去判断注释中是否存在OLD_ELEMENT_TAGS中的一点，例子：@mbg.generated)
        commentGenerator.addComment(batchDeleteEle);

        // 使用JDBC的getGenereatedKeys方法获取主键并赋值到keyProperty设置的领域模型属性中。所以只支持MYSQL和SQLServer
        XmlElementGeneratorTools.useGeneratedKeys(batchDeleteEle, introspectedTable);

        batchDeleteEle.addElement(new TextElement("delete from " + introspectedTable.getFullyQualifiedTableNameAtRuntime() + " where "+ introspectedTable.getPrimaryKeyColumns().get(0).getActualColumnName()+" in"));

        // 添加foreach节点
        XmlElement foreachElement = new XmlElement("foreach");
        foreachElement.addAttribute(new Attribute("collection", "array"));
        foreachElement.addAttribute(new Attribute("item", "id"));
        foreachElement.addAttribute(new Attribute("separator", ","));

//        foreachElement.addElement(element);

        batchDeleteEle.addElement(foreachElement);

        for (Element element : XmlElementGeneratorTools.generateValues(ListUtilities.removeIdentityAndGeneratedAlwaysColumns(introspectedTable.getAllColumns()), "id.")) {
            foreachElement.addElement(element);
        }

        // values 构建
        document.getRootElement().addElement(batchDeleteEle);
        log.debug("(批量插入插件):" + introspectedTable.getMyBatis3XmlMapperFileName() + "增加batchDelete实现方法。");

        return true;
    }

}