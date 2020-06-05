/** 
 * @(#)ErrorMessage.java 1.0.0 2015年12月17日 上午9:51:41  
 *  
 * Copyright © 2015 善林金融.  All rights reserved.  
 */

package com.bojin.api.common.validate;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * 错误封装
 * 
 * @author samson
 * @version $Revision:1.0.0, $Date: 2015年12月17日 上午9:51:41 $
 */
@Data
@AllArgsConstructor
public class ErrorMessage implements Serializable {

	private static final long serialVersionUID = 5834971825000739560L;

	private String name;
	private String message;

}
