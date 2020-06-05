package com.bojin.api.core;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import lombok.Data;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.alibaba.druid.filter.Filter;
import com.alibaba.druid.filter.logging.Slf4jLogFilter;
import com.alibaba.druid.pool.DruidDataSource;

/**
 * 数据源配置
 * 
 * @author samson
 *
 */
@Configuration
@EnableConfigurationProperties({ DruidDSConfig.DruidDataSourcProperties.class })
public class DruidDSConfig {

	@Autowired
	private DruidDataSourcProperties dsProperties;

	@Bean
	public DataSource dataSource() {
		return createDataSource(dsProperties);
	}

	private DataSource createDataSource(BaseDataSourcProperties properties) {
		DruidDataSource dataSource = null;
		try {
			dataSource = new DruidDataSource();
			dataSource.setUrl(properties.getUrl());
			dataSource.setUsername(properties.getUsername());
			dataSource.setPassword(properties.getPassword());
			dataSource.setFilters(properties.getFilters());
			dataSource.setInitialSize(properties.getInitialSize());
			dataSource.setMaxActive(properties.getMaxActive());
			dataSource.setMinIdle(properties.getMinIdle());
			dataSource.setDriverClassName(properties.getDriverClassName());
			Slf4jLogFilter log = new com.alibaba.druid.filter.logging.Slf4jLogFilter();
			log.setStatementExecutableSqlLogEnable(true);
			// log.set
			List<Filter> logList = new ArrayList<>();
			logList.add(log);
			dataSource.setProxyFilters(logList);
		} catch (SQLException e) {
			System.err.println(e);
		}
		return dataSource;
	}

	@Data
	public static class BaseDataSourcProperties {
		/**
		 * JDBC url of the database.
		 */
		private String url;

		/**
		 * Login user of the database.
		 */
		private String username;

		/**
		 * Login password of the database.
		 */
		private String password;

		/**
		 * 插件
		 */
		private String filters;

		/**
		 * 最大并发连接数
		 */
		private int maxActive;

		/**
		 * 初始化连接数量
		 */
		private int initialSize;

		/**
		 * 最小空闲连接数
		 */
		private int minIdle;

		private String driverClassName;
	}

	@ConfigurationProperties(prefix = "druid.datasource")
	public static class DruidDataSourcProperties extends BaseDataSourcProperties {
	}
}