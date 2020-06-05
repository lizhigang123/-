/** 
 * @(#)AnnotationValidateAspect.java 1.0.0 2014年12月23日 下午1:00:00  
 *  
 * Copyright © 2014 善林金融.  All rights reserved.  
 */

package com.bojin.api.common.validate;

import java.lang.reflect.Method;
import java.util.Map;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bojin.api.common.exception.SLException;
import com.bojin.api.common.util.JSONUtils;
import com.bojin.api.common.validate.annotations.Rules;
import com.bojin.api.common.validate.validator.SLValidator;
import com.bojin.api.common.validate.validator.SLValidatorMapAnnotation;

/**
 * 注解验证切面 目前只针对一个参数的如果有多个参数则默认以第一个为准
 * 
 * @author 孟山
 * @version $Revision:1.0.0, $Date: 2014年12月23日 下午1:00:00 $
 */
public class AnnotationValidateAspect {

	private Logger logger = LoggerFactory.getLogger(this.getClass());

	/**
	 * 环绕通知 验证不通过则直接抛出异常信息
	 * 
	 * @param joinPoint
	 * @return
	 * @throws Throwable
	 */
	public Object around(ProceedingJoinPoint joinPoint) throws Throwable {
		MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
		Method method = methodSignature.getMethod();
		Object[] args = joinPoint.getArgs();
		Rules rules = RulesCache.getAnnotationRulesByMethod(method);
		if (rules != null && args.length > 0) {
			Object args0 = args[0];
			SLValidator slValidator = validate(args0, rules);
			if (slValidator.hasErrors()) {
				String messages = slValidator.toString();
				logger.warn("{} 注解数据校验不通过:入参为{},校验结果为:{}", method.toString(), args0.toString(), messages);
				throw new SLException(messages);
			}
		}
		Object result = joinPoint.proceed();

		/**
		 * 打印输入输出日志 并且不影响正常流程
		 */
		try {
			if (logger.isDebugEnabled()) {
				StringBuilder logMessage = new StringBuilder();
				if (args != null && args.length > 0) {
					for (int i = 0; i < args.length; i++) {
						logMessage.append("第" + i + "个参数:").append(JSONUtils.toJSONString(args[i])).append("\n");
					}
				}

				logMessage.append("\t输出结果为:").append(JSONUtils.toJSONString(result));
				logger.debug(logMessage.toString());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	/**
	 * 支持验证类型
	 * <p>
	 * Map
	 * </p>
	 * <p>
	 * 
	 * </p>
	 * 
	 * @param parameters
	 * @param rules
	 * @return
	 * @throws SLException
	 */
	@SuppressWarnings("unchecked")
	private SLValidator validate(Object parameters, Rules rules) throws SLException {
		SLValidator slValidator = null;
		if (parameters instanceof Map) {
			slValidator = new SLValidatorMapAnnotation((Map<String, Object>) parameters, rules);
		} else {
			throw new SLException("数据验证失败");
		}
		return slValidator;
	}
}
