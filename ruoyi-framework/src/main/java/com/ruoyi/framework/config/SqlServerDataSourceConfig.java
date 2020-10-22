package com.ruoyi.framework.config;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;

@Configuration
public class SqlServerDataSourceConfig {

	@Bean(name = "sqlServerDataSource")
	@Qualifier("sqlServerDataSource")
	@ConfigurationProperties(prefix = "spring.datasource.druid.sqlserver")
	public DataSource getMyDataSource() {
		return DataSourceBuilder.create().build();
	}

	@Bean(name = "secondaryJdbcTemplate")
	public JdbcTemplate secondaryJdbcTemplate(
			@Qualifier("sqlServerDataSource") DataSource dataSource) {
		return new JdbcTemplate(dataSource);
	}
}
