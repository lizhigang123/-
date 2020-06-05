/** 
 * @(#)SystemArchitecture.java 1.0.0 2014年11月23日 下午12:31:00  
 *  
 * Copyright © 2014 善林金融.  All rights reserved.  
 */

package com.bojin.api.web;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;

/**
 * 项目架构(全局aop织入点定义)
 * 
 * @author samson
 * @version $Revision:1.0.0, $Date: 2014年11月23日 下午12:31:00 $
 */
@Aspect
public class SystemArchitecture {

	/**
	 * controller切入点
	 */
	@Pointcut("execution( * com.bojin.api.web.controller..*(..))")
	public void controller() {
	}

	@Pointcut("within(com.bojin.api.web.controller..*)")
	public void inController() {
	}

	/**
	 * 服务service的切入点
	 */
	@Pointcut("execution(* com.bojin.api.core.service..*(..))")
	public void service() {
	}

	/**
	 * dao层的repository切入点
	 */
	@Pointcut("execution(* com.bojin.api.core.repository..*(..))")
	public void repository() {
	}

}
