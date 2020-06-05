/** 
 * @(#)ServiceMapping.java 1.0.0 2016年2月29日 下午2:24:04  
 *  
 * Copyright © 2016 善林金融.  All rights reserved.  
 */

package com.bojin.api.common.support.springweb.service;

import java.lang.reflect.Method;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * {@code ServiceAutoMapping} 对应的数据封装
 * 
 * @author samson
 * @version $Revision:1.0.0, $Date: 2016年2月29日 下午2:24:04 $
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ServiceMapping {
	private Class<?> serviceClass;
	private Method serviceMethod;
}
