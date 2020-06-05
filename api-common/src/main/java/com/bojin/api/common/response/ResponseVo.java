/** 
 * @(#)ResponseVo.java 1.0.0 2015年12月12日 下午3:34:42  
 *  
 * Copyright © 2015 善林金融.  All rights reserved.  
 */

package com.bojin.api.common.response;

import lombok.Data;

/**
 * 通用相应VO
 * 
 * @author samson
 * @version $Revision:1.0.0, $Date: 2015年12月12日 下午3:34:42 $
 */
@Data
public class ResponseVo {

	/**
	 * 平台(web:前台web,crm:后台web,ios:ios app,android:android
	 * app,creditTerminal:终端机) <br/>
	 * 服务启动组装
	 */
	private String plateform;
	/**
	 * 响应码
	 */
	private String code;

	/**
	 * 响应码对应的相应信息
	 */
	private String message;

	/**
	 * 时间戳<br/>
	 * 服务自动组装
	 */
	private long timestamp;

	/**
	 * 请求处理时间<br/>
	 * 服务自动组装
	 */
	private long consume;

	public ResponseVo(String code) {
		this.code = code;
	}

	public ResponseVo(String code, String message) {
		this.code = code;
		this.message = message;
	}

	/**
	 * 返回成功VO
	 * 
	 * @return
	 */
	public static ResponseVo success() {
		return new ResponseVo(ResponseCode.SUCCESS);
	}

	public static ResponseVo success(String message) {
		return new ResponseVo(ResponseCode.SUCCESS, message);
	}
	

	/**
	 * 返回失败VO
	 * 
	 * @param code
	 * @return
	 */
	public static ResponseVo failure(String code) {
		return new ResponseVo(code, null);
	}

	/**
	 * 返回失败VO
	 * 
	 * @param code
	 * @param message
	 * @return
	 */
	public static ResponseVo failure(String code, String message) {
		return new ResponseVo(code, message);
	}

}
