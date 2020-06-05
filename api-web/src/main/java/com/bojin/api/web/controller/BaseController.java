/**
 * Copyright (c) 2005-2012 https://github.com/zhangkaitao
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 */
package com.bojin.api.web.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;

import com.bojin.api.common.exception.SLException;
import com.bojin.api.common.response.ResponseCode;
import com.bojin.api.common.response.ResponseListVo;
import com.bojin.api.common.response.ResponseMapVo;
import com.bojin.api.common.response.ResponseObjectVo;
import com.bojin.api.common.response.ResponseVo;
import com.bojin.api.common.validate.ValidateRuntimeException;

/**
 * 基础控制器
 * <p>
 * User: Zhang Kaitao
 * <p>
 * Date: 13-2-23 下午3:56
 * <p>
 * Version: 1.0
 */
public abstract class BaseController {

	protected Logger logger = LoggerFactory.getLogger(this.getClass());

	/**
	 * 视图前缀:默认为以当前controller上的RequestMapping中的value第一个值,如果类上无此注解则默认为当前类名
	 */
	private String viewPrefix;
	
	@Value("${upload.root.path}")
	protected String uploadRootPath;
	
	public BaseController() {
		setViewPrefix(defaultViewPrefix());
	}

	/**
	 * 当前模块 视图的前缀 默认 1、获取当前类头上的@RequestMapping中的value作为前缀 2、如果没有就使用当前模型小写的简单类名
	 */
	public void setViewPrefix(String viewPrefix) {
		if (viewPrefix.startsWith("/")) {
			viewPrefix = viewPrefix.substring(1);
		}
		this.viewPrefix = viewPrefix;
	}

	public String getViewPrefix() {
		return viewPrefix;
	}

	/**
	 * 获取视图名称：即prefixViewName + "/" + suffixName
	 *
	 * @return
	 */
	public String viewName(String suffixName) {
		if (!suffixName.startsWith("/")) {
			suffixName = "/" + suffixName;
		}
		return getViewPrefix() + suffixName;
	}

	public String toListView() {
		return viewName("list");
	}

	public String toCreateView() {
		return viewName("create");
	}

	public String toEditView() {
		return viewName("edit");
	}

	public String toShowView() {
		return viewName("show");
	}

	/**
	 * 初始化参数Map
	 * 
	 * @return
	 */
	protected Map<String, Object> initParmas() {
		return new HashMap<String, Object>();
	}

	/**
	 * @param backURL
	 *            null 将重定向到默认getViewPrefix()
	 * @return
	 */
	protected String redirectToUrl(String backURL) {
		if (StringUtils.isEmpty(backURL)) {
			backURL = getViewPrefix();
		}
		if (!backURL.startsWith("/") && !backURL.startsWith("http")) {
			backURL = "/" + backURL;
		}
		return "redirect:" + backURL;
	}

	protected String defaultViewPrefix() {
		String currentViewPrefix = "";
		RequestMapping requestMapping = AnnotationUtils.findAnnotation(getClass(), RequestMapping.class);
		if (requestMapping != null && requestMapping.value().length > 0) {
			currentViewPrefix = requestMapping.value()[0];
		} else {
			getClass().getSimpleName();
		}
		return currentViewPrefix;
	}

	protected ResponseVo success() {
		return ResponseVo.success();
	}

	protected <T> ResponseObjectVo<T> success(T data) {
		return new ResponseObjectVo<T>(ResponseCode.SUCCESS, data);
	}

	protected <T> ResponseListVo<T> success(List<T> data) {
		return new ResponseListVo<T>(ResponseCode.SUCCESS, data);
	}

	protected ResponseMapVo success(Map<String, Object> data) {
		return new ResponseMapVo(ResponseCode.SUCCESS, data);
	};

	protected ResponseVo fail(String code, String message) {
		return ResponseVo.failure(code, message);
	}

	protected ResponseVo fail(String code) {
		return ResponseVo.failure(code);
	}

	/**
	 * controller 层异常统一处理
	 * 
	 * @param e
	 * @return
	 */
	@ExceptionHandler
	public ResponseVo exceptionHandler(Exception e) {
		logger.error("异常信息:", e);
		if (e instanceof SLException) {// shanlin 异常
			SLException slException = (SLException) e;
			return new ResponseVo(slException.getCode(), slException.getMessage());
		} else if (e instanceof BindException) {// 数据邦定校验异常
			BindException bindException = (BindException) e;
			FieldError fieldError = bindException.getBindingResult().getFieldError();// 获取第一条错误信息
			return fail(ResponseCode.PARAMETERS_VALIDATE_FAILURE, fieldError.getField() + fieldError.getDefaultMessage());
		} else if (e instanceof ValidateRuntimeException) {
			return new ResponseVo(ResponseCode.PARAMETERS_VALIDATE_FAILURE, e.getMessage());
		} else {// 其他非正常异常
			return new ResponseVo(ResponseCode.SERVER_ERROR);
		}
	}
}
