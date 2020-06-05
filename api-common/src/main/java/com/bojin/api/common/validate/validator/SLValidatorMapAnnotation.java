/** 
 * @(#)SLValidatorMapAnnotation.java 1.0.0 2014年12月23日 上午10:48:55  
 *  
 * Copyright © 2014 善林金融.  All rights reserved.  
 */

package com.bojin.api.common.validate.validator;

import java.lang.reflect.Method;
import java.util.Map;

import com.bojin.api.common.validate.RulesCache;
import com.bojin.api.common.validate.annotations.Rule;
import com.bojin.api.common.validate.annotations.Rules;

/**
 * 
 * 
 * @author 孟山
 * @version $Revision:1.0.0, $Date: 2014年12月23日 上午10:48:55 $
 */
public class SLValidatorMapAnnotation extends SLValidatorMap {

	private Rules rules;

	public SLValidatorMapAnnotation(Map<String, Object> parameters, String className, String methodName) {
		super(parameters);
		rules = RulesCache.getAnnotationRules(className, methodName, Rules.class);
		validate();
	}

	public SLValidatorMapAnnotation(Map<String, Object> parameters, Rules rules) {
		super(parameters);
		this.rules = rules;
		validate();
	}

	@Override
	protected void validate() {
		long start = System.currentTimeMillis();
		if (rules != null) {
			Rule[] rule = rules.value();
			for (Rule currentRule : rule) {
				Method[] methods = Rule.class.getDeclaredMethods();
				for (Method method : methods) {
					// 不是错误提示信息 且不为 name
					if ((!method.getName().equals("name")) && method.getName().lastIndexOf("Message") == -1) {
						Method validateMethod = null;
						Object[] validateArgs = null;
						Class<?> type = method.getReturnType();
						try {
							validateMethod = SLValidatorAbstract.class.getDeclaredMethod(method.getName(), String.class, type, String.class);
							validateArgs = new Object[] { currentRule.name(), Rule.class.getDeclaredMethod(method.getName()).invoke(currentRule), Rule.class.getDeclaredMethod(method.getName() + "Message").invoke(currentRule) };
							if (validateMethod != null) {
								validateMethod.setAccessible(true);
								validateMethod.invoke(this, validateArgs);
							}
						} catch (NoSuchMethodException e) {
							e.printStackTrace();
							logger.warn("注解规则验证 方法找不到", e);
						} catch (SecurityException e) {
							e.printStackTrace();
							logger.warn("注解规则验证 方法查找安全异常", e);
						} catch (Exception e) {
							e.printStackTrace();
							logger.warn("注解规则验证 反射出错", e);
						}
					}
				}
			}
		}
		long end = System.currentTimeMillis();
		logger.debug("======================================annotation validate rules use {} millseconds", end - start);
	}
}
