/** 
 * @(#)PageRequestArgumentResolver.java 1.0.0 2016年3月10日 下午6:57:03  
 *  
 * Copyright © 2016 善林金融.  All rights reserved.  
 */

package com.bojin.api.common.support.springweb.method;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import com.bojin.api.common.exception.SLException;
import com.bojin.api.common.response.ResponseCode;
import com.bojin.api.common.support.repository.page.PageMapRequest;
import com.bojin.api.common.support.repository.page.PageRequest;

/**
 * PageRequest 参数注入
 * 
 * @author samson
 * @version $Revision:1.0.0, $Date: 2016年3月10日 下午6:57:03 $
 */
public class PageRequestArgumentResolver implements HandlerMethodArgumentResolver {

	private String searchParamPrefix = "search_";

	@Override
	public boolean supportsParameter(MethodParameter parameter) {
		return PageRequest.class.isAssignableFrom(parameter.getParameterType());
	}

	@Override
	public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
		int start = NumberUtils.toInt(webRequest.getParameter("start"), 0);
		int length = NumberUtils.toInt(webRequest.getParameter("length"), 0);
		if (PageMapRequest.class.isAssignableFrom(parameter.getParameterType())) {
			return new PageMapRequest(start, length, getParam(webRequest.getParameterMap()));
		} else {
			throw new SLException(ResponseCode.SERVER_ERROR, "目前只支持Map泛型的分页参数");
		}
	}

	private Map<String, Object> getParam(Map<String, String[]> parameters) {
		Map<String, Object> p = new HashMap<String, Object>();
		for (Iterator<String> iterator = parameters.keySet().iterator(); iterator.hasNext();) {
			String key = iterator.next();
			if (key != null && key.startsWith(searchParamPrefix)) {
				String[] values = parameters.get(key);
				p.put(key.substring(searchParamPrefix.length()), values.length > 1 ? values : values[0]);
			}
		}
		return p;
	}
}
