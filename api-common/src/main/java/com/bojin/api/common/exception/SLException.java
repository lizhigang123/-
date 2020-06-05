/** 
 * @(#)SLException.java 1.0.0 2015年12月14日 上午9:36:14  
 *  
 * Copyright © 2015 善林金融.  All rights reserved.  
 */

package com.bojin.api.common.exception;

/**
 * 封装异常
 * 
 * @author samson
 * @version $Revision:1.0.0, $Date: 2015年12月14日 上午9:36:14 $
 */
public class SLException extends Exception {

	private static final long serialVersionUID = -1410570443387344326L;
	private String code;

	public SLException(String code, String message) {
		super(message);
		this.code = code;
	}

	public SLException(String code) {
		this.code = code;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

}
