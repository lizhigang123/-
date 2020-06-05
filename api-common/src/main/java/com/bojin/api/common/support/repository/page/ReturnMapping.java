/** 
 * @(#)ReturnMapping.java 1.0.0 2016年1月4日 下午3:17:36  
 *  
 * Copyright © 2016 善林金融.  All rights reserved.  
 */

package com.bojin.api.common.support.repository.page;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 返回{@code Map}结果key映射
 * 
 * @author samson
 * @version $Revision:1.0.0, $Date: 2016年1月4日 下午3:17:36 $
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface ReturnMapping {

	/**
	 * 返回结果的key
	 * 
	 * @return
	 */
	String from();

	/**
	 * 返回结果的key对应的需要转换的key
	 * 
	 * @return
	 */
	String to();
}
