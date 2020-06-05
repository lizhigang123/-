/** 
 * @(#)SerializeResponseVo.java 1.0.0 2015年12月14日 下午6:10:36  
 *  
 * Copyright © 2015 善林金融.  All rights reserved.  
 */

package com.bojin.api.common.support.fastjson;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 返回结果序列化指定规则
 * 
 * @author samson
 * @version $Revision:1.0.0, $Date: 2015年12月14日 下午6:10:36 $
 */
@Target({ ElementType.METHOD })
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Serialize {
	/**
	 * 序列化规则集合
	 * 
	 * @return
	 */
	SerializeRule[] value() default {};
}
