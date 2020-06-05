/** 
 * @(#)ResponseMapVo.java 1.0.0 2015年12月12日 下午4:36:44  
 *  
 * Copyright © 2015 善林金融.  All rights reserved.  
 */

package com.bojin.api.common.response;

import java.util.HashMap;
import java.util.Map;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 响应Map数据VO
 * 
 * @author samson
 * @version $Revision:1.0.0, $Date: 2015年12月12日 下午4:36:44 $
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class ResponseMapVo extends ResponseVo {

	private Map<String, Object> data;

	public ResponseMapVo(String code, Map<String, Object> data) {
		super(code);
		this.data = data;
	}

	/**
	 * 加入数据
	 * 
	 * @param key
	 * @param value
	 * @return
	 */
	public ResponseMapVo put(String key, Object value) {
		if (data == null) {
			data = new HashMap<String, Object>();
		}
		data.put(key, value);
		return this;
	}

	public ResponseMapVo putAll(Map<String, Object> data) {
		data.putAll(data);
		return this;
	}
}
