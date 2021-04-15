package com.ruoyi.generator.plugin;

import org.mybatis.generator.api.IntrospectedColumn;
import org.mybatis.generator.api.IntrospectedTable;

import java.util.List;

/**
 * 字段驼峰命名<br>
 * 首字母转小写<br>
 * 保持原有大小写<br>
 * 删除【_】,并且把【_】后面的字母转大写<br>
 *
 * @author shaon
 *
 */
public class FieldHumpNamingPlugin extends AbstractClassPlugin {

    @Override
    public void initialized(IntrospectedTable introspectedTable) {
        List<IntrospectedColumn> columnList = introspectedTable.getAllColumns();
        columnList.forEach(column -> {
            column.setJavaProperty(updateColumnName(column.getActualColumnName()));
        });
    }

    // @Override
    // public boolean modelFieldGenerated(Field field, TopLevelClass topLevelClass,
    // IntrospectedColumn introspectedColumn,
    // IntrospectedTable introspectedTable, ModelClassType modelClassType) {
    // StringBuffer columnName = new
    // StringBuffer(introspectedColumn.getActualColumnName());
    // int index;
    // while ((index = columnName.indexOf("_")) >= 0) {
    // columnName.deleteCharAt(index);
    // if (index < columnName.length()) {
    // char ch = columnName.charAt(index);
    // columnName.replace(index, index + 1, String.valueOf(Character.toUpperCase(ch)));
    // }
    // }
    //
    // columnName.replace(0, 1,
    // String.valueOf(Character.toLowerCase(columnName.charAt(0))));
    //
    // String fieldName = columnName.toString();
    // field.setName(fieldName);
    // introspectedColumn.setJavaProperty(fieldName);
    // return super.modelFieldGenerated(field, topLevelClass, introspectedColumn,
    // introspectedTable, modelClassType);
    // }

    private String updateColumnName(String oldColumnName) {
        StringBuffer columnName = new StringBuffer(oldColumnName);
        int index;
        while ((index = columnName.indexOf("_")) >= 0) {
            columnName.deleteCharAt(index);
            if (index < columnName.length()) {
                char ch = columnName.charAt(index);
                columnName.replace(index, index + 1, String.valueOf(Character.toUpperCase(ch)));
            }
        }

        columnName.replace(0, 1, String.valueOf(Character.toLowerCase(columnName.charAt(0))));

        return columnName.toString();
    }

}
