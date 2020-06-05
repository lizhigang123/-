/** 
 * @(#)ValidAssigned.java 1.0.0 2015年12月16日 上午10:02:34  
 *  
 * Copyright © 2015 善林金融.  All rights reserved.  
 */

package com.bojin.api.common.support.validation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 校验指定属性的校验(扩展自{@code Validated}) 此注解校验不支持级联校验
 * 
 * @author samson
 * @version $Revision:1.0.0, $Date: 2015年12月16日 上午10:02:34 $
 */
@Target({ ElementType.METHOD, ElementType.PARAMETER })
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface ValidAssigned {

	/**
	 * 指定校验类对应的属性
	 * 
	 * @return
	 */
	String[] value() default {};

}
