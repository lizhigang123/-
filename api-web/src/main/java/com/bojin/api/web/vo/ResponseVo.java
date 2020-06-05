/**
 * @(#)ResponseVo.java 1.0.0 2015年12月12日 下午3:34:42
 * <p/>
 * Copyright © 2015 善林金融.  All rights reserved.
 */

package com.bojin.api.web.vo;

import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import com.alibaba.fastjson.annotation.JSONField;
import com.bojin.api.common.response.ResponseCode;
import com.bojin.api.common.support.repository.page.PageResponse;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.google.common.collect.Maps;

/**
 * 通用响应VO
 * 
 * @author samson
 * @version $Revision:1.0.0, $Date: 2015年12月12日 下午3:34:42 $
 */
public class ResponseVo {

	/**
	 * 响应码
	 */
	private String code;

	/**
	 * 响应信息
	 */
	private String message;

	/**
	 * 响应的结果
	 */
	private Object result;

	public ResponseVo() {
		super();
	}

	/**
	 * 没有返回的data
	 * 
	 * @author LiYing
	 * @createTime 2016年4月5日 下午1:32:35
	 * @return
	 */
	public static ResponseVo success() {
		return new ResponseVo(ResponseCode.SUCCESS, StringUtils.EMPTY, null);
	}

	/**
	 * 直接返回data
	 * 
	 * @author LiYing
	 * @createTime 2016年4月5日 下午1:33:00
	 * @param data
	 * @return
	 */
	public static ResponseVo success(Object data) {
		return new ResponseVo(ResponseCode.SUCCESS, StringUtils.EMPTY, data);
	}

	/**
	 * 返回的data需要添加key
	 * 
	 * @author LiYing
	 * @createTime 2016年4月5日 下午1:33:29
	 * @param key
	 * @param data
	 * @return
	 */
	public static ResponseVo success(String key, Object data) {
		Map<String, Object> map = Maps.newHashMap();
		map.put(key, data);
		return new ResponseVo(ResponseCode.SUCCESS, StringUtils.EMPTY, map);
	}

	/**
	 * 返回分页查询结果
	 * 
	 * @author LiYing
	 * @createTime 2016年4月5日 下午1:36:29
	 * @param pageResponse
	 * @return
	 */
	public static ResponseVo success(PageResponse<?> pageResponse) {
		return buildResult(StringUtils.EMPTY, pageResponse);
	}

	/**
	 * 返回分页查询结果：需要重新设置key
	 * 
	 * @author LiYing
	 * @createTime 2016年4月5日 下午1:36:29
	 * @param pageResponse
	 * @return
	 */
	public static ResponseVo success(String key, PageResponse<?> pageResponse) {
		return buildResult(key, pageResponse);
	}

	public static ResponseVo fail(String code, String message) {
		return new ResponseVo(code, message, null);
	}

	public static ResponseVo fail(String message) {
		return new ResponseVo(ResponseCode.SERVER_ERROR, message, null);
	}

	private static ResponseVo buildResult(String key, PageResponse<?> pageResponse) {
		Map<String, Object> map = Maps.newHashMap();
		map.put(StringUtils.isEmpty(key) ? "data" : key, pageResponse.getData());
		map.put("total", pageResponse.getTotal());
		map.put("extraData", pageResponse.getExtraData());
		return new ResponseVo(ResponseCode.SUCCESS, StringUtils.EMPTY, map);
	}

	public ResponseVo(String code, String message, Object result) {
		this.code = code;
		this.message = message;
		this.result = result;
	}

	public String getCode() {
		return code;
	}

	public String getMessage() {
		return message;
	}

	public Object getResult() {
		return result;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public void setResult(Object result) {
		this.result = result;
	}

	@JsonIgnore
	@JSONField(serialize = false)
	public boolean isSuccess() {
		return ResponseCode.SUCCESS.equals(this.code);
	}
}
