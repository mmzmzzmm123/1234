package com.ruoyi.generator.service;

import com.alibaba.druid.pool.DruidDataSource;
import com.ruoyi.common.constant.Constants;
import com.ruoyi.common.core.text.CharsetKit;
import com.ruoyi.common.exception.CustomException;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.common.utils.file.FileUtils;
import com.ruoyi.generator.domain.GenTable;
import com.ruoyi.generator.mapper.GenTableColumnMapper;
import com.ruoyi.generator.mapper.GenTableMapper;
import com.ruoyi.generator.plugin.DaoClassGeneratePlugin;
import com.ruoyi.generator.plugin.KeyValue;
import com.ruoyi.generator.plugin.ManagerInterfaceClassGeneratePlugin;
import com.ruoyi.generator.util.MyBatisCodeGenerator;
import com.ruoyi.generator.util.VelocityInitializer;
import com.ruoyi.generator.util.VelocityUtils;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.io.File;
import java.io.IOException;
import java.io.StringWriter;
import java.net.URL;
import java.util.Arrays;
import java.util.List;

@Service
public class MybatisGenServiceImpl {
    @Autowired
    private GenTableServiceImpl genTableService;
    @Autowired
    private DataSource dataSource;
    @Autowired
    private GenTableMapper genTableMapper;
    @Autowired
    private GenTableColumnMapper genTableColumnMapper;

    public void generatorCode(String tableName) {
        // 查询表信息
        GenTable table = genTableMapper.selectGenTableByName(tableName);
        // 设置主子表信息
        genTableService.setSubTable(table);
        // 设置主键列信息
        genTableService.setPkColumn(table);

        VelocityInitializer.initVelocity();

        VelocityContext context = VelocityUtils.prepareContext(table);

        // 获取模板列表
        this.genMybatis(table);
        List<String> templates = VelocityUtils.getTemplateList(table.getTplCategory());
        templates.removeAll(Arrays.asList(new String[]{"vm/java/domain.java.vm", "vm/java/mapper.java.vm", "vm/xml/mapper.xml.vm"}));
        for (String template : templates) {
            if (!StringUtils.containsAny(template, "sql.vm", "api.js.vm", "index.vue.vm", "index-tree.vue.vm")) {
                // 渲染模板
                StringWriter sw = new StringWriter();
                Template tpl = Velocity.getTemplate(template, Constants.UTF8);
                tpl.merge(context, sw);
                try {
                    String path = GenTableServiceImpl.getGenPath(table, template);
                    FileUtils.writeStringToFile(new File(path), sw.toString(), CharsetKit.UTF_8);
                } catch (IOException e) {
                    throw new CustomException("渲染模板失败，表名：" + table.getTableName());
                }
            }
        }
    }

    public void genMybatis(GenTable genTable) {
        //todo
//        String rootPath = GenTableServiceImpl.getGenBasePath(genTable);
        URL resource = MybatisGenServiceImpl.class.getClassLoader().getResource("./");
        String rootPath = new File(resource.getFile()).getParentFile().getParent();
        //todo:测试临时
        genTable.setGenPath(rootPath+"/src");
        // 配置项目路径
        MyBatisCodeGenerator generator = MyBatisCodeGenerator.create(rootPath, "com.slabbridge.core");
        String jdbcDriver = "";
        String jdbcUrl = "";
        String jdbcUser = "";
        String jdbcPwd = "";
        if (dataSource instanceof DruidDataSource) {
            DruidDataSource druidDataSource = (DruidDataSource) dataSource;
            jdbcDriver = druidDataSource.getDriverClassName();
            jdbcUrl = druidDataSource.getUrl();
            jdbcUser = druidDataSource.getUsername();
            jdbcPwd = druidDataSource.getPassword();
        }
        // 配置数据库
        generator.setJdbcConnection(jdbcDriver,
                jdbcUrl,
                jdbcUser,
                jdbcPwd,
                KeyValue.create("nullCatalogMeansCurrent", "true"));
        // 配置插件
        // 扩展dao插件
        generator.addPlugin(DaoClassGeneratePlugin.class);
        // 业务逻辑插件
        generator.addPlugin(ManagerInterfaceClassGeneratePlugin.class);
        //设置表
        //todo:多张表的处理
        generator.createDefaultTable(genTable.getTableName(), genTable.getClassName(), "id");
        //
        generator.generate();
    }
}
