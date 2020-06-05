/** 
 * @(#)AspectConfi.java 1.0.0 2015年12月23日 下午3:16:48  
 *  
 * Copyright © 2015 善林金融.  All rights reserved.  
 */

package com.bojin.api.web.cfg;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

import com.bojin.api.common.support.springweb.method.ResponseVoReturnValueAspect;
import com.bojin.api.common.support.springweb.service.ServiceAutoMappingAspect;
import com.bojin.api.web.SystemArchitecture;

/**
 * 切面配置
 * 
 * @author samson
 * @version $Revision:1.0.0, $Date: 2015年12月23日 下午3:16:48 $
 */
@Configuration
@EnableAspectJAutoProxy(proxyTargetClass = false)
public class AspectConfig {

	/**
	 * 项目全局架构切入点统一定义
	 * 
	 * @return
	 */
	@Bean
	public SystemArchitecture systemArchitecture() {
		return new SystemArchitecture();
	}

	/**
	 * 服务自动转发映射aop
	 * 
	 * @return
	 */
	@Bean
	public ServiceAutoMappingAspect serviceAutoMappingAspect() {
		return new ServiceAutoMappingAspect();
	}

	/**
	 * responseVo封装切面
	 * 
	 * @return
	 */
	@Bean
	public ResponseVoReturnValueAspect responseVoReturnValueAspect() {
		return new ResponseVoReturnValueAspect();
	}

}
