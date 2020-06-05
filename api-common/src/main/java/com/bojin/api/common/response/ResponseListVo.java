/** 
 * @(#)ResponseEntityListVo.java 1.0.0 2015年12月12日 下午4:24:50  
 *  
 * Copyright © 2015 善林金融.  All rights reserved.  
 */

package com.bojin.api.common.response;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 集合数据响应VO
 * 
 * @author samson
 * @version $Revision:1.0.0, $Date: 2015年12月12日 下午4:24:50 $
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class ResponseListVo<T> extends ResponseVo {

	private List<T> data;

	public ResponseListVo(String code, List<T> data) {
		super(code);
		this.data = data;
	}

	public ResponseListVo<T> add(T t) {
		if (data == null) {
			data = new ArrayList<T>();
		}
		data.add(t);
		return this;
	}
}
