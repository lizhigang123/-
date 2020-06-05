/** 
 * @(#)ServiceAutoMapping.java 1.0.0 2016年2月29日 上午10:17:08  
 *  
 * Copyright © 2016 善林金融.  All rights reserved.  
 */

package com.bojin.api.common.support.springweb.service;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 服务自动映射。如在类上添加则标示当前类的所有此注解的全局配置,配置在方法上则优先查找类上的此注解然后当前方法的此注解覆盖类的注解值(优先级高于类注解)
 * 
 * @author samson
 * @version $Revision:1.0.0, $Date: 2016年2月29日 上午10:17:08 $
 */
@Target({ ElementType.TYPE, ElementType.METHOD })
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface ServiceAutoMapping {
	/**
	 * 服务类名(已类注解为主)
	 * 
	 * @return
	 */
	Class<?> serviceClass();

	/**
	 * 服务对应的方法名(为空时则自动以当前方法匹配)
	 * 
	 * @return
	 */
	String serviceMethod() default "";
}
