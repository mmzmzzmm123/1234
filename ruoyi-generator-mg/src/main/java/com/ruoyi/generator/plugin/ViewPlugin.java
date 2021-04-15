package com.ruoyi.generator.plugin;

import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.PluginAdapter;
import org.mybatis.generator.api.dom.java.Interface;
import org.mybatis.generator.api.dom.java.TopLevelClass;
import org.mybatis.generator.api.dom.xml.Document;
import org.mybatis.generator.api.dom.xml.XmlElement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * 视图支持 <br/>
 * 删除所有带操作的方法<br/>
 * 请放到最后
 */
public class ViewPlugin extends PluginAdapter {

    private Logger log = LoggerFactory.getLogger(this.getClass());

    @Override
    public boolean validate(List<String> warnings) {
        return true;
    }

    @Override
    public boolean clientGenerated(Interface interfaze, TopLevelClass topLevelClass,
                                   IntrospectedTable introspectedTable) {
        if (introspectedTable.getTableType().equalsIgnoreCase("VIEW")) {
            interfaze.getMethods().removeIf(method -> {
                String name = method.getName().toLowerCase();
                if (name.indexOf("insert") > -1 || name.indexOf("update") > -1
                    || name.indexOf("delete") > -1) {
                    log.warn("视图表={} 删除方法={}",
                        introspectedTable.getFullyQualifiedTableNameAtRuntime(), method.getName());
                    return true;
                }
                return false;
            });
        }
        return true;
    }

    @Override
    public boolean sqlMapDocumentGenerated(Document document, IntrospectedTable introspectedTable) {
        if (introspectedTable.getTableType().equalsIgnoreCase("VIEW")) {
            document.getRootElement().getElements().removeIf(element -> {
                if (element instanceof XmlElement) {
                    String name = ((XmlElement) element).getName();
                    if (name.indexOf("insert") > -1 || name.indexOf("update") > -1
                        || name.indexOf("delete") > -1) {
                        log.warn("视图表={} 删除xml节点={}",
                            introspectedTable.getFullyQualifiedTableNameAtRuntime(), name);
                        return true;
                    }
                }
                return false;
            });
        }
        return true;
    }

}
