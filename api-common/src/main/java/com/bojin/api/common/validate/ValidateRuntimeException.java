/** 
 * @(#)ValidateRunTimeException.java 1.0.0 2015年12月17日 下午4:22:40  
 *  
 * Copyright © 2015 善林金融.  All rights reserved.  
 */

package com.bojin.api.common.validate;

/**
 * 自定义校验规则校验异常
 * 
 * @author samson
 * @version $Revision:1.0.0, $Date: 2015年12月17日 下午4:22:40 $
 */
public class ValidateRuntimeException extends RuntimeException {

	public ValidateRuntimeException(String code, String message) {
		super(message);
		this.code = code;
	}

	private static final long serialVersionUID = 5845358517216023552L;
	private String code;

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

}
