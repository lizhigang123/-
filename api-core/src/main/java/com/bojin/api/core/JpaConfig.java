/** 
 * @(#)JpaConfig.java 1.0.0 2015年12月22日 下午6:33:33  
 *  
 * Copyright © 2015 善林金融.  All rights reserved.  
 */

package com.bojin.api.core;

import javax.sql.DataSource;

import org.springframework.boot.orm.jpa.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import com.bojin.api.common.entity.BaseEntity;
import com.bojin.api.common.support.repository.SimpleBaseRepositoryFactoryBean;
import com.bojin.api.core.demo.entity.DemoUserEntity;

/**
 * JPA配置
 * 
 * @author samson
 * @version $Revision:1.0.0, $Date: 2015年12月22日 下午6:33:33 $
 */
@Configuration
@EnableJpaAuditing
@EntityScan(basePackageClasses = { DemoUserEntity.class, BaseEntity.class })
@EnableJpaRepositories(repositoryFactoryBeanClass = SimpleBaseRepositoryFactoryBean.class, repositoryImplementationPostfix = "CustImpl")
public class JpaConfig {

	@Bean
	public JdbcTemplate jdbcTemplate(DataSource dataSource) {
		return new JdbcTemplate(dataSource);
	}

	@Bean
	public NamedParameterJdbcTemplate namedParameterJdbcTemplate(JdbcTemplate jdbcTemplate) {
		return new NamedParameterJdbcTemplate(jdbcTemplate);
	}

}
