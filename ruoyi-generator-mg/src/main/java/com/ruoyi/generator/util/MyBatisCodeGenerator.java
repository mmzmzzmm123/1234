package com.ruoyi.generator.util;

import com.google.common.collect.Maps;
import com.itfsw.mybatis.generator.plugins.*;
import com.ruoyi.generator.plugin.*;
import org.mybatis.generator.api.*;
import org.mybatis.generator.config.*;
import org.mybatis.generator.exception.ShellException;
import org.mybatis.generator.internal.DefaultShellCallback;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class MyBatisCodeGenerator {

	private Logger log = LoggerFactory.getLogger(MyBatisCodeGenerator.class);

	// 根目录
	private String rootPath;

	// 根包名
	private String rootPackage;

	// 上下文
	private Context context;

	private Map<String, String> transferMap = Maps.newHashMap();

	public MyBatisCodeGenerator(String rootPath, String rootPackage) {
		this.rootPath = rootPath;
		this.rootPackage = rootPackage;

		init();
	}

	public static MyBatisCodeGenerator create(String rootPath, String rootPackage) {
		return new MyBatisCodeGenerator(rootPath, rootPackage);
	}

	private void init() {
		this.context = createContext(ModelType.CONDITIONAL, "core-config", "MyBatis3");
		// 关键字打引号
		context.addProperty("autoDelimitKeywords", "true");
		context.addProperty("beginningDelimiter", "`");
		context.addProperty("endingDelimiter", "`");
		// 通用包目录配置
		context.addProperty(Constant.ROOT_PACKAGE, rootPackage);
		context.addProperty(Constant.TARGET_PROJECT, rootPath + "/src/main/java");
		context.addProperty(Constant.TARGET_ENTITY, rootPackage + ".entity");
		context.addProperty(Constant.TARGET_MAPPER, rootPackage + ".mapper");
		context.addProperty(Constant.TARGET_DAO, rootPackage + ".dao");
		context.addProperty(Constant.TARGET_MANAGER, rootPackage + ".manager");
		context.addProperty(Constant.TARGET_CONTROLLER, rootPackage + ".controller");
		context.addProperty(Constant.OVERRIDE, "false");
		// 备份配置
		context.addProperty(Constant.Backup.SQL_TARGET_PATH,
				rootPath + "/src/test/resources/mybatis-code-generator/backups/sql");
		context.addProperty(Constant.Backup.CODE_SOURCE_PATH, rootPath + "/src/main/java");
		context.addProperty(Constant.Backup.CODE_TARGET_PATH, rootPath + "/target/code-generator/backups");

		// 注解生成
		// todo
		// setCommentGenerator(CustomizedCommentGenerator.class);
		// java type映射
		setJavaTypeResolver(DefaultJavaTypeResolverImpl.class, KeyValue.create("forceBigDecimals", "false"));
		// entity生成配置
		setJavaModelGenerator(context.getProperty(Constant.TARGET_ENTITY), context.getProperty(Constant.TARGET_PROJECT),
				KeyValue.create("trimStrings", "true"), KeyValue.create("constructorBased", "false"));
		// sql生成配置
		setSqlMapGenerator(context.getProperty(Constant.TARGET_MAPPER), context.getProperty(Constant.TARGET_PROJECT));
		// dao生成配置
		setJavaClientGenerator("XMLMAPPER", null, context.getProperty(Constant.TARGET_MAPPER),
				context.getProperty(Constant.TARGET_PROJECT));

		// 默认插件
		// 添加删除原有xml插件
		addPlugin(DeleteMapperXmlFilePlugin.class);
		// 驼峰命名插件
		addPlugin(FieldHumpNamingPlugin.class);
		// 查询单条数据插件
		addPlugin(SelectOneByExamplePlugin.class);
		// Example Criteria 增强插件
		addPlugin(ExampleEnhancedPlugin.class);
		// 数据Model属性对应Column获取插件
		addPlugin(ModelColumnPlugin.class);
		// Selective 增强插件
		addPlugin(SelectiveEnhancedPlugin.class);
		// 批量插入插件
		addPlugin(BatchInsertPlugin.class);
		// 实现序列化接口
		addPlugin(SerializableInterfacePlugin.class);
		// 生成SQL文件
		addPlugin(GeneratorSqlPlugin.class);
		// 批量删除
		addPlugin(BatchDeletePlugin.class);
		// todo: 备份原始文件
		// addPlugin(SourceCodeBackupPlugin.class);
		// 逻辑删除插件
		addPlugin(LogicalDeletePlugin.class, KeyValue.create("logicalDeleteColumn", "is_deleted"),
				KeyValue.create("logicalDeleteValue", "1"), KeyValue.create("logicalUnDeleteValue", "0"));
		// 分页
		addPlugin(LimitPlugin.class);
		// 解决mysql delete 取别名语法不支持bug
		addPlugin(MysqlDeleteByExampleElementPlugin.class);
	}

	private Context createContext(ModelType modelType, String id, String targetRuntime) {
		Context context = new Context(modelType);
		context.setId(id);
		context.setTargetRuntime(targetRuntime);
		return context;
	}

	public TableConfiguration createTable() {
		TableConfiguration configuration = new TableConfiguration(this.context);
		this.context.addTableConfiguration(configuration);
		return configuration;
	}

	public TableConfiguration createDefaultTable(String tableName, String objectName, String idColumn,
			KeyValue<String, String>... keyValues) {
		TableConfiguration configuration = createTable();
		configuration.setTableName(tableName);
		configuration.setDomainObjectName(objectName);
		configuration.setAlias(objectName.toLowerCase());

		GeneratedKey generatedKey = new GeneratedKey(idColumn, "JDBC", true, null);
		configuration.setGeneratedKey(generatedKey);

		setProperty(configuration, keyValues);
		return configuration;
	}

	public TableConfiguration createDefaultTable(String tableName, String objectName, GeneratedKey generatedKey,
			KeyValue<String, String>... keyValues) {
		TableConfiguration configuration = createTable();
		configuration.setTableName(tableName);
		configuration.setDomainObjectName(objectName);
		configuration.setAlias(objectName.toLowerCase());

		configuration.setGeneratedKey(generatedKey);

		setProperty(configuration, keyValues);
		return configuration;
	}

	public TableConfiguration createDefaultTable(String tableName, String objectName,
			KeyValue<String, String>... keyValues) {
		TableConfiguration configuration = createTable();
		configuration.setTableName(tableName);
		configuration.setDomainObjectName(objectName);
		configuration.setAlias(objectName.toLowerCase());

		setProperty(configuration, keyValues);
		return configuration;
	}

	public TableConfiguration createView(String tableName, String objectName) {
		TableConfiguration configuration = createTable();
		configuration.setTableName(tableName);
		configuration.setDomainObjectName(objectName);
		configuration.setAlias(objectName.toLowerCase());

		configuration.setInsertStatementEnabled(false);
		configuration.setUpdateByExampleStatementEnabled(false);
		configuration.setUpdateByPrimaryKeyStatementEnabled(false);
		configuration.setDeleteByExampleStatementEnabled(false);
		configuration.setDeleteByPrimaryKeyStatementEnabled(false);

		return configuration;
	}

	public MyBatisCodeGenerator addPlugin(Class<? extends PluginAdapter> pluginClass,
			KeyValue<String, String>... keyValues) {
		PluginConfiguration configuration = new PluginConfiguration();
		setTypedProperty(configuration, pluginClass, keyValues);
		context.addPluginConfiguration(configuration);
		return this;
	}

	public MyBatisCodeGenerator setCommentGenerator(Class<? extends CommentGenerator> commentGeneratorClass,
			KeyValue<String, String>... keyValues) {
		CommentGeneratorConfiguration configuration = new CommentGeneratorConfiguration();
		setTypedProperty(configuration, commentGeneratorClass, keyValues);
		context.setCommentGeneratorConfiguration(configuration);
		return this;
	}

	public MyBatisCodeGenerator setJavaTypeResolver(Class<? extends JavaTypeResolver> javaTypeResolverClass,
			KeyValue<String, String>... keyValues) {
		JavaTypeResolverConfiguration configuration = new JavaTypeResolverConfiguration();
		setTypedProperty(configuration, javaTypeResolverClass, keyValues);
		context.setJavaTypeResolverConfiguration(configuration);
		return this;
	}

	public MyBatisCodeGenerator setJavaModelGenerator(String targetPackage, String targetProject,
			KeyValue<String, String>... keyValues) {
		JavaModelGeneratorConfiguration configuration = new JavaModelGeneratorConfiguration();
		configuration.setTargetPackage(targetPackage);
		configuration.setTargetProject(targetProject);
		setProperty(configuration, keyValues);
		context.setJavaModelGeneratorConfiguration(configuration);
		return this;
	}

	public MyBatisCodeGenerator setSqlMapGenerator(String targetPackage, String targetProject,
			KeyValue<String, String>... keyValues) {
		SqlMapGeneratorConfiguration configuration = new SqlMapGeneratorConfiguration();
		configuration.setTargetPackage(targetPackage);
		configuration.setTargetProject(targetProject);
		setProperty(configuration, keyValues);
		context.setSqlMapGeneratorConfiguration(configuration);
		return this;
	}

	public MyBatisCodeGenerator setJavaClientGenerator(String configurationType, String implementationPackage,
			String targetPackage, String targetProject, KeyValue<String, String>... keyValues) {
		JavaClientGeneratorConfiguration configuration = new JavaClientGeneratorConfiguration();
		configuration.setConfigurationType(configurationType);
		configuration.setImplementationPackage(implementationPackage);
		configuration.setTargetPackage(targetPackage);
		configuration.setTargetProject(targetProject);
		setProperty(configuration, keyValues);
		context.setJavaClientGeneratorConfiguration(configuration);
		return this;
	}

	public MyBatisCodeGenerator addTransferPackage(String sourcePackage, String targetCatalog) {
		transferMap.put(sourcePackage, targetCatalog);
		return this;
	}

	private void setTypedProperty(TypedPropertyHolder typedPropertyHolder, Class<?> commentGeneratorClass,
			KeyValue<String, String>... keyValues) {
		typedPropertyHolder.setConfigurationType(commentGeneratorClass.getTypeName());
		setProperty(typedPropertyHolder, keyValues);
	}

	private void setProperty(PropertyHolder propertyHolder, KeyValue<String, String>... keyValues) {
		for (KeyValue<String, String> keyValue : keyValues) {
			propertyHolder.addProperty(keyValue.getKey(), keyValue.getValue());
		}
	}

	public MyBatisCodeGenerator setJdbcConnection(String driverClass, String connectionURL, String userId,
			String password, KeyValue<String, String>... keyValues) {
		JDBCConnectionConfiguration configuration = new JDBCConnectionConfiguration();
		configuration.setDriverClass(driverClass);
		configuration.setConnectionURL(connectionURL);
		configuration.setUserId(userId);
		configuration.setPassword(password);
		setProperty(configuration, keyValues);
		context.setJdbcConnectionConfiguration(configuration);
		return this;
	}

	public void generate(ShellCallback shellCallback, List<String> warnings, ProgressCallback callback) {
		try {
			// 默认db规范检查
			// todo:去掉检查
			// addPlugin(DBTableStandardCheckPlugin.class);
			// 视图处理
			addPlugin(ViewPlugin.class);

			Configuration configuration = new Configuration();
			configuration.addContext(context);

			MyBatisGenerator myBatisGenerator = new MyBatisGenerator(configuration, shellCallback, warnings);
			log.warn("生成配置\r\n{}", configuration.toDocument().getFormattedContent());
			myBatisGenerator.generate(null);
			log.info("生成MyBatis结束");
		}
		catch (Exception e) {
			log.error("生成MyBatis配置文件失败", e);
		}
	}

	public void generate() {
		List<String> warnings = new ArrayList<String>();
		DefaultShellCallback shellCallback = new ShellCallback2(true);
		ProgressCallback callback = null;
		generate(shellCallback, warnings, callback);
	}

	public Context getContext() {
		return context;
	}

	public void setContext(Context context) {
		this.context = context;
	}

	class ShellCallback2 extends DefaultShellCallback implements ShellCallback {

		public ShellCallback2(boolean overwrite) {
			super(overwrite);
		}

		// 目录拷贝
		@Override
		public File getDirectory(String targetProject, String targetPackage) throws ShellException {
			if (transferMap.containsKey(targetPackage)) {
				return super.getDirectory(transferMap.get(targetPackage) + targetProject, targetPackage);
			}
			else {
				return super.getDirectory(targetProject, targetPackage);
			}
		}

	}

}
