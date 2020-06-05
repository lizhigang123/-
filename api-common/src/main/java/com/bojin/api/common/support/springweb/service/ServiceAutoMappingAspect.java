/** 
 * @(#)ServiceAutoMappingAdvice.java 1.0.0 2016年2月29日 上午10:27:02  
 *  
 * Copyright © 2016 善林金融.  All rights reserved.  
 */

package com.bojin.api.common.support.springweb.service;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.core.annotation.Order;

import com.bojin.api.common.exception.SLException;
import com.bojin.api.common.response.ResponseCode;
import com.bojin.api.common.response.ResponseObjectVo;
import com.bojin.api.common.response.ResponseVo;
import com.bojin.api.common.support.repository.page.PageResponse;

/**
 * 服务自动调用切面(如果定义的服务返回值不为{@code ResponseVo} ,则把服务的返回值当作数据封装成{@code ResponseVo})
 * 
 * @author samson
 * @version $Revision:1.0.0, $Date: 2016年2月29日 上午10:27:02 $
 */
@Aspect
@Order(Ordered.LOWEST_PRECEDENCE)
public class ServiceAutoMappingAspect implements ApplicationContextAware {

	private Logger logger = LoggerFactory.getLogger(this.getClass());

	private ApplicationContext applicationContext;
	private Map<Object, ServiceMapping> mappings = new HashMap<Object, ServiceMapping>();

	@Around("com.bojin.api.web.SystemArchitecture.controller()")
	public Object around(ProceedingJoinPoint joinPoint) throws Throwable {
		if (supports(joinPoint)) {
			return handle(joinPoint);
		}
		return joinPoint.proceed();
	}

	/**
	 * 是否支持当前切面业务
	 * 
	 * @param joinPoint
	 * @return
	 */
	public boolean supports(ProceedingJoinPoint joinPoint) {
		MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
		Class<?> clazz = methodSignature.getDeclaringType();
		Method m = methodSignature.getMethod();
		return AnnotationUtils.findAnnotation(clazz, ServiceAutoMapping.class) != null || AnnotationUtils.findAnnotation(m, ServiceAutoMapping.class) != null;
	}

	/**
	 * 处理业务
	 * 
	 * @param joinPoint
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public Object handle(ProceedingJoinPoint joinPoint) throws Exception {
		Object returnValue = null;
		ServiceMapping serviceMapping = getServiceMapping(joinPoint);
		returnValue = serviceMapping.getServiceMethod().invoke(getService(serviceMapping.getServiceClass()), joinPoint.getArgs());
		Class<?> serviceReturnType = serviceMapping.getServiceMethod().getReturnType();
		if (!ResponseVo.class.isAssignableFrom(serviceReturnType) && !PageResponse.class.isAssignableFrom(serviceReturnType)) {
			returnValue = new ResponseObjectVo(ResponseCode.SUCCESS, returnValue);
		}
		return returnValue;
	}

	protected ServiceMapping getServiceMapping(ProceedingJoinPoint joinPoint) throws Exception {
		MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
		Class<?> clazz = methodSignature.getDeclaringType();
		Method m = methodSignature.getMethod();
		ServiceMapping serviceMapping = null;
		if (mappings.containsKey(m)) {
			serviceMapping = mappings.get(m);
		} else {
			ServiceAutoMapping serviceAutoMapping = AnnotationUtils.findAnnotation(m, ServiceAutoMapping.class);
			String serviceMethodName = null;
			if (serviceAutoMapping != null) {
				serviceMapping = new ServiceMapping(serviceAutoMapping.serviceClass(), null);
				serviceMethodName = serviceAutoMapping.serviceMethod();
			} else {
				serviceMapping = new ServiceMapping();
			}
			serviceAutoMapping = AnnotationUtils.findAnnotation(clazz, ServiceAutoMapping.class);
			if (serviceAutoMapping != null) {
				if (serviceMapping.getServiceClass() == null) {
					serviceMapping.setServiceClass(serviceAutoMapping.serviceClass());
				}
			}
			if (serviceMapping.getServiceClass() == null) {
				logger.error(ServiceAutoMapping.class.toString() + "定义出错");
				throw new SLException(ResponseCode.SERVER_ERROR);
			} else {
				if (StringUtils.isBlank(serviceMethodName)) {
					serviceMethodName = m.getName();
				}
				try {
					serviceMapping.setServiceMethod(serviceMapping.getServiceClass().getMethod(serviceMethodName, m.getParameterTypes()));
				} catch (Exception e) {
					logger.error("controller service 映射查找不到相应的服务方法", e);
					throw new SLException(ResponseCode.SERVER_ERROR);
				}
			}
			mappings.put(m, serviceMapping);
		}
		return serviceMapping;
	}

	protected <T> T getService(Class<T> service) {
		return applicationContext.getBean(service);
	}

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		this.applicationContext = applicationContext;
	}
}
