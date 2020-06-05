/** 
 * @(#)ValidateUtils.java 1.0.0 2015年12月17日 下午4:17:16  
 *  
 * Copyright © 2015 善林金融.  All rights reserved.  
 */

package com.bojin.api.common.support.springweb.method;

import java.lang.annotation.Annotation;

import javax.validation.Valid;

import org.springframework.core.MethodParameter;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.validation.Validator;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;

import com.bojin.api.common.response.ResponseCode;
import com.bojin.api.common.support.validation.CustomerizeLocalValidatorFactoryBean;
import com.bojin.api.common.support.validation.ValidAssigned;
import com.bojin.api.common.validate.RulesUtils;
import com.bojin.api.common.validate.ValidateRuntimeException;
import com.bojin.api.common.validate.annotations.ValidRules;
import com.bojin.api.common.validate.validator.SLValidator;

/**
 * 校验模块提取出来
 * 
 * @author samson
 * @version $Revision:1.0.0, $Date: 2015年12月17日 下午4:17:16 $
 */
public class ValidateUtils {

	/**
	 * spring mvc的入参数据校验重新封装
	 * 
	 * @param binder
	 * @param methodParam
	 */
	public static void validateIfApplicable(WebDataBinder binder, MethodParameter methodParam) {
		Annotation annotation = null;
		// spring mvc 框架自带的校验规则
		if ((annotation = methodParam.getParameterAnnotation(Valid.class)) != null || (annotation = methodParam.getParameterAnnotation(Validated.class)) != null) {
			Object hints = AnnotationUtils.getValue(annotation);
			Object[] validationHints = (hints instanceof Object[] ? (Object[]) hints : new Object[] { hints });
			binder.validate(validationHints);
			// 扩展spring mvc 框架自带的校验规则(可以校验指定属性)
		}
		validateCustomerize(binder, methodParam);
	}

	/**
	 * 基于springmvc 的入参数据校验规则的扩展
	 * 
	 * @param binder
	 * @param methodParam
	 */
	public static void validateCustomerize(WebDataBinder binder, MethodParameter methodParam) {
		validateAssigned(binder, methodParam);
		validateRules(binder, methodParam);
	}

	/**
	 * 扩展spring mvc 框架自带的校验规则(可以校验指定属性):针对不是OO的参数进行校验
	 * 
	 * @param binder
	 * @param methodParam
	 */
	public static void validateAssigned(WebDataBinder binder, MethodParameter methodParam) {
		ValidAssigned validAssigned = null;
		if ((validAssigned = methodParam.getParameterAnnotation(ValidAssigned.class)) != null) {
			for (Validator validator : binder.getValidators()) {
				if (validator instanceof CustomerizeLocalValidatorFactoryBean) {
					CustomerizeLocalValidatorFactoryBean customerizeLocalValidatorFactoryBean = (CustomerizeLocalValidatorFactoryBean) validator;
					customerizeLocalValidatorFactoryBean.validateProperties(binder.getTarget(), validAssigned, binder.getBindingResult());
				}
			}
		}
	}

	/**
	 * 针对不是OO的参数进行校验
	 * 
	 * @param binder
	 * @param methodParam
	 */
	public static void validateRules(WebDataBinder binder, MethodParameter methodParam) {
		if (methodParam.getMethodAnnotation(ValidRules.class) != null) {
			for (Validator validator : binder.getValidators()) {
				if (validator instanceof CustomerizeLocalValidatorFactoryBean) {
					SLValidator slValidator = RulesUtils.validateValidRules(binder.getTarget(), methodParam.getMethod(), methodParam.getParameterIndex());
					if (slValidator.hasErrors()) {
						throw new ValidateRuntimeException(ResponseCode.PARAMETERS_VALIDATE_FAILURE, slValidator.getMessage());
					}
				}
			}
		}
	}

}
