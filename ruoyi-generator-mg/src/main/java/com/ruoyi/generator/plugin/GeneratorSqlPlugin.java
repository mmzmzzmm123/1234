package com.ruoyi.generator.plugin;

import cn.hutool.core.date.DateTime;
import com.ruoyi.common.utils.DateUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.mybatis.generator.api.ConnectionFactory;
import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.config.Context;
import org.mybatis.generator.internal.JDBCConnectionFactory;

import java.io.File;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


/**
 * 生成sql
 * 
 * @author shaon
 */
public class GeneratorSqlPlugin extends AbstractClassPlugin  {

	private File file;

	@Override
	public void setContext(Context context) {
		super.setContext(context);

		String date =  DateFormatUtils.format(DateTime.now(),DateUtils.YYYY_MM_DD_HH_MM_SS);

		file = new File(String.format("{}/{}.sql",
				context.getProperty(Backup.SQL_TARGET_PATH),
				date));
		//todo
//		Files.newFile(file);
//		Files.clearContent(file);
//		Files.additionalPrintContent(file, "# " + date, "");
	}

	@Override
	public void initialized(IntrospectedTable introspectedTable) {
		try {
			ConnectionFactory factory = new JDBCConnectionFactory(context.getJdbcConnectionConfiguration());
			try (Connection connection = factory.getConnection()) {
				try (Statement statement = connection.createStatement()) {
					ResultSet result = statement.executeQuery(
						"show create table " + introspectedTable.getFullyQualifiedTableNameAtRuntime());
					if (result.next()) {
						String tableSql = result.getString(2);
						int indexStart = tableSql.indexOf("AUTO_INCREMENT=");
						if (indexStart > 0) {
							int indexEnd = tableSql.indexOf(" ", indexStart);
							tableSql = tableSql.substring(0, indexStart) + tableSql.substring(indexEnd);
						}
						// 把自增主键换回来
						//todo
//						Files.additionalPrintContent(file, "", "", tableSql + ';');
						// 把注释给拿出来把
						buildRemarks(introspectedTable, tableSql);
					}
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	private void buildRemarks(IntrospectedTable introspectedTable, String tableSql) {
		// 最后的边界
		int lastIndex = tableSql.lastIndexOf(')');
		int indexStart = tableSql.indexOf("COMMENT='", lastIndex);
		if (indexStart > 0) {
			// COMMENT='商户预约选项'
			String comment = tableSql.substring(indexStart, tableSql.indexOf('\'', indexStart + 9));

			introspectedTable.setRemarks(comment.substring(9));
		}
	}
}
