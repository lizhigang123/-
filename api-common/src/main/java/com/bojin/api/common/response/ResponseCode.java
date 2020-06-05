/** 
 * @(#)ResponseCode.java 1.0.0 2015年12月12日 下午3:41:41  
 *  
 * Copyright © 2015 善林金融.  All rights reserved.  
 */

package com.bojin.api.common.response;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

/**
 * 响应码
 * 
 * <pre>
 * 成功
 * 	操作成功
 * 		查询成功
 * 		创建成功
 * 		更新成功
 * 		删除成功
 * 		业务处理成功
 * 
 * </pre>
 * 
 * @author samson
 * @version $Revision:1.0.0, $Date: 2015年12月12日 下午3:41:41 $
 */
public class ResponseCode {

	/**
	 * 成功
	 */
	public static final String SUCCESS = "000000";

	/**
	 * 参数校验失败
	 */
	public static final String PARAMETERS_VALIDATE_FAILURE = "100000";

	/**
	 * 服务器异常
	 */
	public static final String SERVER_ERROR = "111111";

	public static final Map<String, String> CODE_2_DEFAULTMESSAGE = new HashMap<String, String>() {
		private static final long serialVersionUID = 1505055760615653130L;

		{
			put(SUCCESS, "操作成功!");
			put(PARAMETERS_VALIDATE_FAILURE, "参数校验失败!");
			put(SERVER_ERROR, "服务器异常");
		}
	};

	public static final void defaultMessage(ResponseVo responseVo) {
		if (responseVo != null) {
			if (StringUtils.isEmpty(responseVo.getMessage())) {
				String message = null;
				if (StringUtils.isEmpty(message = CODE_2_DEFAULTMESSAGE.get(responseVo.getCode()))) {
					// TODO
				}
				responseVo.setMessage(message);
			}
		}
	}
}
