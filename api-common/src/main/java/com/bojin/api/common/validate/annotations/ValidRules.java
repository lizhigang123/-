/** 
 * @(#)ValidRules.java 1.0.0 2015年12月16日 上午11:00:13  
 *  
 * Copyright © 2015 善林金融.  All rights reserved.  
 */

package com.bojin.api.common.validate.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 数据校验规则
 * 
 * @author samson
 * @version $Revision:1.0.0, $Date: 2015年12月16日 上午11:00:13 $
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.METHOD })
public @interface ValidRules {

	Rules[] value() default {};
}
