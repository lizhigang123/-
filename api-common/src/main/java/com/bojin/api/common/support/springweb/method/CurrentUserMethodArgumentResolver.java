package com.bojin.api.common.support.springweb.method;

import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

/**
 * 用于对springmvc中controller方法的参数有{@code CurrentUser} 注解的参数进行自动注入当前操作用户ID
 * 
 * @author samson
 *
 */
public class CurrentUserMethodArgumentResolver implements HandlerMethodArgumentResolver {

	public CurrentUserMethodArgumentResolver() {
	}

	@Override
	public boolean supportsParameter(MethodParameter parameter) {
		parameter.getMethod().getParameterAnnotations();
		if (parameter.hasParameterAnnotation(CurrentUserId.class)) {
			return true;
		}
		return false;
	}

	@Override
	public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
		// TODO
		return "testUserId";
	}
}
