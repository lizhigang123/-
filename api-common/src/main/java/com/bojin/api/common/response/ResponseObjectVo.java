/** 
 * @(#)ResponseVo.java 1.0.0 2015年12月12日 下午4:12:50  
 *  
 * Copyright © 2015 善林金融.  All rights reserved.  
 */

package com.bojin.api.common.response;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 基于对象的响应
 * 
 * @author samson
 * @version $Revision:1.0.0, $Date: 2015年12月12日 下午4:12:50 $
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class ResponseObjectVo<T> extends ResponseVo {

	private T data;

	public ResponseObjectVo(String code, String message) {
		super(code, message);
	}

	public ResponseObjectVo(String code, T data) {
		super(code);
		this.data = data;
	}

}
