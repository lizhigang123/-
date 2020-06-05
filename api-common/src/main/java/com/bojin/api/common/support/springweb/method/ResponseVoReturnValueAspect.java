/** 
 * @(#)ResponseVoReturnValueAspect.java 1.0.0 2015年12月14日 下午2:13:59  
 *  
 * Copyright © 2015 善林金融.  All rights reserved.  
 */

package com.bojin.api.common.support.springweb.method;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;

import com.bojin.api.common.response.ResponseCode;
import com.bojin.api.common.response.ResponseVo;

/**
 * 关于responseVo返回值添加处理时间拦截器
 * 
 * @author samson
 * @version $Revision:1.0.0, $Date: 2015年12月14日 下午2:13:59 $
 */
@Aspect
@Order(value = Ordered.HIGHEST_PRECEDENCE)
public class ResponseVoReturnValueAspect {

	private Logger logger = LoggerFactory.getLogger(this.getClass());

	/**
	 * 配置前置通知
	 * 
	 * @param joinPoint
	 * @return
	 * @throws Throwable
	 */
	// @Around("execution( com.bojin.api.common.response.ResponseVo com.bojin.api..*Controller.*(..) )")
	@Around("com.bojin.api.web.SystemArchitecture.controller()")
	public Object around(ProceedingJoinPoint joinPoint) throws Throwable {
		long time = System.currentTimeMillis();
		logger.info("before " + joinPoint);
		Object object = joinPoint.proceed();
		// 组装ResponseVo的处理时间
		populateReturnValue(object, time);
		return object;
	}

	protected void populateReturnValue(Object returnValue, long startTime) {
		if (ResponseVo.class.isAssignableFrom(returnValue.getClass())) {
			ResponseVo responseVo = (ResponseVo) returnValue;
			responseVo.setTimestamp(startTime);
			responseVo.setConsume(System.currentTimeMillis() - startTime);
			ResponseCode.defaultMessage(responseVo);
		}
	}

}
