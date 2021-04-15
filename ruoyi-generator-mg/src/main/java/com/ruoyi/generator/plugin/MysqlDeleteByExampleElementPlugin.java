package com.ruoyi.generator.plugin;

import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.dom.xml.TextElement;
import org.mybatis.generator.api.dom.xml.XmlElement;
import org.springframework.util.StringUtils;

import java.util.List;

public class MysqlDeleteByExampleElementPlugin extends AbstractClassPlugin {

    @Override
    public boolean validate(List<String> warnings) {
        // 该插件只支持MYSQL
        if ("com.mysql.jdbc.Driver".equalsIgnoreCase(
            this.getContext().getJdbcConnectionConfiguration().getDriverClass()) == false
            && "com.mysql.cj.jdbc.Driver".equalsIgnoreCase(
                this.getContext().getJdbcConnectionConfiguration().getDriverClass()) == false) {
            warnings.add("插件" + this.getClass().getTypeName() + "只支持MySQL数据库！");
            return false;
        }
        return super.validate(warnings);
    }

    @Override
    public boolean sqlMapDeleteByExampleElementGenerated(XmlElement element,
                                                         IntrospectedTable introspectedTable) {
        // 别名
        String alias = introspectedTable.getTableConfiguration().getAlias();
        if (StringUtils.isEmpty(alias)) {
            String sql = String.format("delete {} from {}", alias,
                introspectedTable.getAliasedFullyQualifiedTableNameAtRuntime());

            element.addElement(1, new TextElement(sql));
            // 移除原有的sql
            element.getElements().remove(0);
        }

        return super.sqlMapDeleteByExampleElementGenerated(element, introspectedTable);
    }

}
