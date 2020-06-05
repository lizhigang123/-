/*
 * Copyright 2012-2013 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.bojin.api.web.cfg;

import java.io.IOException;
import java.lang.reflect.Method;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.validator.HibernateValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.web.WebMvcAutoConfiguration.WebMvcAutoConfigurationAdapter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.MethodParameter;
import org.springframework.core.annotation.SynthesizingMethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.util.StreamUtils;
import org.springframework.validation.Validator;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ServletModelAttributeMethodProcessor;

import com.alibaba.fastjson.serializer.SerializerFeature;
import com.bojin.api.common.support.fastjson.Serialize;
import com.bojin.api.common.support.springweb.method.CurrentUserMethodArgumentResolver;
import com.bojin.api.common.support.springweb.method.CustomerizeServletModelAttributeMethodProcessor;
import com.bojin.api.common.support.springweb.method.PageRequestArgumentResolver;
import com.bojin.api.common.support.validation.CustomerizeLocalValidatorFactoryBean;
import com.bojin.api.common.util.JSONUtils;
import com.bojin.api.web.AdminLoginHandlerInterceptor;
import com.fasterxml.jackson.annotation.JsonView;

/**
 * web配置,请不要使用{@code EnableWebMvc},此处需要自定义{@code RequestMappingHandlerAdapter}
 * 
 * @author samson
 *
 */
@EnableWebMvc
@Configuration
public class WebConfig extends WebMvcAutoConfigurationAdapter {

	private @Autowired AdminLoginHandlerInterceptor adminLoginHandlerInterceptor;
	@Override
	public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
		argumentResolvers.add(currentUserMethodArgumentResolver());
		argumentResolvers.add(pageRequestArgumentResolver());
	}

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(adminLoginHandlerInterceptor);
	}

	@Override
	public Validator getValidator() {
		return localValidatorFactoryBean();
	}

	/**
	 * 参数中添加{@code @CurrentUserId}注解的自动注入当前登录用户id,并且可以做登录操作和权限控制
	 * 
	 * @return
	 */
	@Bean
	public HandlerMethodArgumentResolver currentUserMethodArgumentResolver() {
		return new CurrentUserMethodArgumentResolver();
	}

	@Bean
	public HandlerMethodArgumentResolver pageRequestArgumentResolver() {
		return new PageRequestArgumentResolver();
	}

	/**
	 * 重定义校验,添加自定义校验方法
	 * 
	 * @return
	 */
	@Bean
	public Validator localValidatorFactoryBean() {
		CustomerizeLocalValidatorFactoryBean localValidatorFactoryBean = new CustomerizeLocalValidatorFactoryBean();
		localValidatorFactoryBean.setProviderClass(HibernateValidator.class);
		return localValidatorFactoryBean;
	}

	@Bean
	public MultipartResolver commonsMultipartResolver(RequestMappingHandlerAdapter requestMappingHandlerAdapter) throws Exception {
		List<HandlerMethodArgumentResolver> argumentResolvers = new ArrayList<HandlerMethodArgumentResolver>(requestMappingHandlerAdapter.getArgumentResolvers().size());
		List<HandlerMethodArgumentResolver> initBinderArgumentResolvers = new ArrayList<HandlerMethodArgumentResolver>(requestMappingHandlerAdapter.getInitBinderArgumentResolvers().size());
		// 当前方法
		Method currentMethod = this.getClass().getMethod(Thread.currentThread().getStackTrace()[1].getMethodName(), RequestMappingHandlerAdapter.class);
		// 测试methodParameter主要获取ServletModelAttributeMethodProcessor中的annotationNotRequired值
		MethodParameter testMethodParameter = new SynthesizingMethodParameter(currentMethod, 0);
		for (HandlerMethodArgumentResolver hmar : requestMappingHandlerAdapter.getArgumentResolvers()) {
			if (ServletModelAttributeMethodProcessor.class.isAssignableFrom(hmar.getClass())) {
				hmar = new CustomerizeServletModelAttributeMethodProcessor(hmar.supportsParameter(testMethodParameter));
			}
			argumentResolvers.add(hmar);
		}
		for (HandlerMethodArgumentResolver hmar : requestMappingHandlerAdapter.getInitBinderArgumentResolvers()) {
			if (ServletModelAttributeMethodProcessor.class.isAssignableFrom(hmar.getClass())) {
				hmar = new CustomerizeServletModelAttributeMethodProcessor(hmar.supportsParameter(testMethodParameter));
			}
			initBinderArgumentResolvers.add(hmar);
		}
		requestMappingHandlerAdapter.setArgumentResolvers(argumentResolvers);
		requestMappingHandlerAdapter.setInitBinderArgumentResolvers(initBinderArgumentResolvers);

		return new CommonsMultipartResolver();
	}

	/**
	 * 
	 * @author samson
	 *
	 */
	@ControllerAdvice
	static class SerializeResponseBodyAdvice implements ResponseBodyAdvice<Object> {
		private Logger logger = LoggerFactory.getLogger(this.getClass());

		@Override
		public boolean supports(MethodParameter returnType, Class<? extends HttpMessageConverter<?>> converterType) {
			return returnType.getMethodAnnotation(JsonView.class) == null && returnType.getMethodAnnotation(Serialize.class) != null;
		}

		@Override
		public Object beforeBodyWrite(Object body, MethodParameter returnType, MediaType selectedContentType, Class<? extends HttpMessageConverter<?>> selectedConverterType, ServerHttpRequest request, ServerHttpResponse response) {
			try {
				StreamUtils.copy(JSONUtils.toJSONString(body, returnType.getMethod(), SerializerFeature.WriteMapNullValue, SerializerFeature.WriteNullListAsEmpty, SerializerFeature.WriteNullBooleanAsFalse), Charset.defaultCharset(), response.getBody());
			} catch (IOException e) {
				logger.error("Serialize 序列化失败", e);
			}
			return null;
		}
	}
}
