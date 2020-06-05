/** 
 * @(#)CustomerizeLocalValidatorFactoryBean.java 1.0.0 2015年12月15日 下午8:25:51  
 *  
 * Copyright © 2015 善林金融.  All rights reserved.  
 */

package com.bojin.api.common.support.validation;

import java.util.HashSet;
import java.util.Set;

import javax.validation.ConstraintViolation;

import org.springframework.validation.Errors;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

/**
 * 扩展{@code LocalValidatorFactoryBean} 增加校验指定属性和根据注解和参数进行校验
 * 
 * @author samson
 * @version $Revision:1.0.0, $Date: 2015年12月15日 下午8:25:51 $
 */
public class CustomerizeLocalValidatorFactoryBean extends LocalValidatorFactoryBean {

	/**
	 * 新增校验指定属性
	 * 
	 * @param object
	 * @param propertyNames
	 * @param errors
	 * @param groups
	 */
	public void validateProperties(Object object, String[] propertyNames, Errors errors, Class<?>... groups) {
		processConstraintViolations(validateProperties(object, propertyNames, groups), errors);
	}

	protected Set<ConstraintViolation<Object>> validateProperties(Object object, String[] propertyNames, Class<?>... groups) {
		Set<ConstraintViolation<Object>> violations = new HashSet<ConstraintViolation<Object>>();
		for (String propertyName : propertyNames) {
			violations.addAll(validateProperty(object, propertyName, groups));
		}
		return violations;
	}

	public void validateProperties(Object object, ValidAssigned validAssigned, Errors errors) {
		Set<ConstraintViolation<Object>> violations = new HashSet<ConstraintViolation<Object>>();
		violations.addAll(validateProperties(object, validAssigned.value()));
		processConstraintViolations(violations, errors);
	}
}
