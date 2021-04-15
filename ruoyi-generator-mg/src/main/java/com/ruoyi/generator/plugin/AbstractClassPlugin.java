package com.ruoyi.generator.plugin;

import com.itfsw.mybatis.generator.plugins.CommentPlugin;
import com.itfsw.mybatis.generator.plugins.utils.BeanUtils;
import com.itfsw.mybatis.generator.plugins.utils.PluginTools;
import com.itfsw.mybatis.generator.plugins.utils.enhanced.TemplateCommentGenerator;
import com.itfsw.mybatis.generator.plugins.utils.hook.HookAggregator;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.generator.util.Constant;
import com.ruoyi.generator.util.MyBatisCodeGenerator;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.mybatis.generator.api.*;
import org.mybatis.generator.api.dom.java.CompilationUnit;
import org.mybatis.generator.api.dom.java.Interface;
import org.mybatis.generator.api.dom.java.JavaVisibility;
import org.mybatis.generator.api.dom.java.TopLevelClass;
import org.mybatis.generator.api.dom.xml.Attribute;
import org.mybatis.generator.api.dom.xml.Document;
import org.mybatis.generator.config.Context;
import org.mybatis.generator.config.PluginConfiguration;
import org.mybatis.generator.internal.util.StringUtility;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.List;
import java.util.Map;

public abstract class AbstractClassPlugin extends PluginAdapter implements Constant {

    private Logger             log           = LoggerFactory.getLogger(AbstractClassPlugin.class);

    protected final String     MANAGER_CLASS = "ManagerClass";

    protected final String     DAO_INTERFACE = "DaoInterface";

    protected CommentGenerator commentGenerator;

    @Override
    public boolean validate(List<String> warnings) {
        return true;
    }

    /**
     * Set the context under which this plugin is running.
     * @param context the new context
     */
    @Override
    public void setContext(Context context) {
        super.setContext(context);

        // 添加插件
        HookAggregator.getInstance().setContext(context);

        // 配置插件使用的模板引擎
        PluginConfiguration cfg = PluginTools.getPluginConfiguration(context, CommentPlugin.class);

        if (cfg == null || cfg.getProperty(CommentPlugin.PRO_TEMPLATE) == null) {
            commentGenerator = context.getCommentGenerator();
        } else {
            TemplateCommentGenerator templateCommentGenerator = new TemplateCommentGenerator(
                context, cfg.getProperty(CommentPlugin.PRO_TEMPLATE));

            // ITFSW 插件使用的注释生成器
            commentGenerator = templateCommentGenerator;

            // 修正系统插件
            try {
                // 先执行一次生成CommentGenerator操作，然后再替换
                context.getCommentGenerator();

                BeanUtils.setProperty(context, "commentGenerator", templateCommentGenerator);
            } catch (Exception e) {
                log.error("反射异常", e);
            }
        }
    }

    // 模版工具
    protected Configuration configuration = buildConfiguration();

    static Configuration buildConfiguration() {
        Configuration configuration = new Configuration(Configuration.VERSION_2_3_26);
        configuration.setClassForTemplateLoading(MyBatisCodeGenerator.class, "/graphql/templates");
        configuration.setDefaultEncoding("UTF-8");
        return configuration;
    }

    protected void generatedFile(Template template, String targetPath,
                                 Map<String, Object> root) throws TemplateException, IOException {
        File targetFile = new File(targetPath);
        if (!targetFile.getParentFile().exists()) {
            targetFile.getParentFile().mkdirs();
        }
        try (OutputStream outputStream = new FileOutputStream(targetFile)) {
            try (Writer out = new OutputStreamWriter(outputStream)) {
                template.process(root, out);
            }
        }
    }

    protected Interface buildInterface(String classPath) {
        Interface _interface = new Interface(classPath);
        _interface.setVisibility(JavaVisibility.PUBLIC);
        return _interface;
    }

    protected GeneratedJavaFile buildGeneratedJavaFile(IntrospectedTable introspectedTable,
                                                       CompilationUnit compilationUnit) {
        return new GeneratedJavaFile(compilationUnit, getTargetProject(introspectedTable),
            context.getJavaFormatter());
    }

    protected GeneratedXmlFile buildGeneratedXmlFile(IntrospectedTable introspectedTable,
                                                     Document document, String fileName) {
        return new GeneratedXmlFile(document, fileName,
            getProperty(introspectedTable, TARGET_DAO, null), getTargetProject(introspectedTable),
            false, context.getXmlFormatter());
    }

    protected String getTargetProject(IntrospectedTable introspectedTable) {
        return getProperty(introspectedTable, TARGET_PROJECT,
            context.getJavaClientGeneratorConfiguration().getTargetProject());
    }

    protected TopLevelClass buildTopLevelClass(String classPath) {
        TopLevelClass topLevelClass = new TopLevelClass(classPath);
        topLevelClass.setVisibility(JavaVisibility.PUBLIC);
        return topLevelClass;
    }

    protected void updateKeyColumn(final List<Attribute> attrs) {
        for (int i = 0; i < attrs.size(); i++) {
            Attribute attr = attrs.get(i);
            if (attr.getName().equals("keyColumn")) {
                attrs.remove(i);
                attrs.add(i, new Attribute("keyColumn", "GENERATED_KEY"));
            }
        }
    }

    protected boolean isOverride(IntrospectedTable introspectedTable) {
        return Boolean
            .parseBoolean(getProperty(introspectedTable, OVERRIDE, Boolean.TRUE.toString()));
    }

    protected boolean existClass(GeneratedFile file) {
        return new File(String.format("%s/%s/%s", file.getTargetProject(),
            file.getTargetPackage().replaceAll("[.]", "/"), file.getFileName())).exists();
    }

    protected String getProperty(IntrospectedTable introspectedTable, String key,
                                 String defaultValue) {
        // table 配置优先获取
        if (introspectedTable.getTableConfiguration().getProperties().containsKey(key)) {
            return StringUtils
                .trimToNull(introspectedTable.getTableConfiguration().getProperty(key));
            // 插件配置
        } else if (properties.containsKey(key)) {
            return StringUtils.trimToNull(properties.getProperty(key));
            // context 配置
        } else if (context.getProperties().containsKey(key)) {
            return StringUtils.trimToNull(context.getProperty(key));
        }
        // 默认值
        return StringUtils.trimToNull(defaultValue);
    }

    protected String getProperty(String key, String defaultValue) {
        // 插件配置
        if (properties.containsKey(key)) {
            return StringUtils.trimToNull(properties.getProperty(key));
            // context 配置
        } else if (context.getProperties().containsKey(key)) {
            return StringUtils.trimToNull(context.getProperty(key));
        }
        // 默认值
        return StringUtils.trimToNull(defaultValue);
    }

}
