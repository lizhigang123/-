/** 
 * @(#)CustomerizeModelAttributeMethodProcessor.java 1.0.0 2015年12月16日 上午9:55:24  
 *  
 * Copyright © 2015 善林金融.  All rights reserved.  
 */

package com.bojin.api.common.support.springweb.method;

import org.springframework.core.MethodParameter;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.servlet.mvc.method.annotation.ServletModelAttributeMethodProcessor;

/**
 * 自定义
 * 
 * @author samson
 * @version $Revision:1.0.0, $Date: 2015年12月16日 上午9:55:24 $
 */
public class CustomerizeServletModelAttributeMethodProcessor extends ServletModelAttributeMethodProcessor {

	public CustomerizeServletModelAttributeMethodProcessor(boolean annotationNotRequired) {
		super(annotationNotRequired);
	}

	@Override
	protected void validateIfApplicable(WebDataBinder binder, MethodParameter methodParam) {
		ValidateUtils.validateIfApplicable(binder, methodParam);
	}
}
