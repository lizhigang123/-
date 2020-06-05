/** 
 * @(#)CurrentUser.java 1.0.0 2015年12月14日 上午11:01:30  
 *  
 * Copyright © 2015 善林金融.  All rights reserved.  
 */

package com.bojin.api.common.support.springweb.method;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 当前操作用户ID
 * 
 * @author samson
 * @version $Revision:1.0.0, $Date: 2015年12月14日 上午11:01:30 $
 */
@Target({ ElementType.PARAMETER })
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface CurrentUserId {

}
