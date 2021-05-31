package com.ruoyi.generator;

import com.ruoyi.generator.plugin.DaoClassGeneratePlugin;
import com.ruoyi.generator.plugin.KeyValue;
import com.ruoyi.generator.plugin.ManagerInterfaceClassGeneratePlugin;
import com.ruoyi.generator.service.MybatisGenServiceImpl;
import com.ruoyi.generator.util.MyBatisCodeGenerator;
import org.apache.commons.io.FileUtils;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.MessageFormat;

public class UTest {

	@Test
	public void test() {
		String str = MessageFormat.format("delete {} from {}", 1, 2);
		System.out.println(str);
	}

	@Test
	public void gen() throws IOException {
		System.setProperty("line.separator", "\n");
		// 获取到项目路径
		URL resource = UTest.class.getClassLoader().getResource("./");
		String rootPath = new File(resource.getFile()).getParentFile().getParent();

		// 目录没有，会不生成
		// rootPath = "D:\\tmp\\0320\\src\\";
		// FileUtils.forceMkdir(new File(rootPath + "\\src\\main\\java\\"));

		// 配置项目路径
		MyBatisCodeGenerator generator = MyBatisCodeGenerator.create(rootPath, "com.slabbridge.core");
		// 配置数据库
		generator.setJdbcConnection("com.mysql.jdbc.Driver",
				"jdbc:mysql://120.25.66.56:3306/ed?useUnicode=true&characterEncoding=utf8&zeroDateTimeBehavior=convertToNull&useSSL=true&serverTimezone=GMT%2B8",
				"ed", "F7Pd3c7s9HS6EZAWmCm6", KeyValue.create("nullCatalogMeansCurrent", "true"));
		// 配置插件
		// 扩展dao插件
		generator.addPlugin(DaoClassGeneratePlugin.class);
		// 业务逻辑插件
		// generator.addPlugin(ManagerInterfaceClassGeneratePlugin.class);

		// 配置表
		// 自动增长主键模版
		// generator.createDefaultTable("member", "Member", "id");
		// 非自增长主键，自设主键模版
		// generator.createDefaultTable("user_info", "UserInfo");

		// 数据源
		generator.createDefaultTable("el_enterprise_info", "EnterpriseInfo", "id");
		generator.generate();
	}

}
