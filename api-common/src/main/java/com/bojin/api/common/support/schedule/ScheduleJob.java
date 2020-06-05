/** 
 * @(#)ScheduleJob.java 1.0.0 2015年12月23日 下午6:00:09  
 *  
 * Copyright © 2015 善林金融.  All rights reserved.  
 */

package com.bojin.api.common.support.schedule;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 自定义定时任务,扩展与{@code Scheduled} 主要由于{@code Scheduled }只支持方法级的
 * 
 * @author samson
 * @version $Revision:1.0.0, $Date: 2015年12月23日 下午6:00:09 $
 */
@Target({ ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface ScheduleJob {
	/**
	 * A cron-like expression, extending the usual UN*X definition to include
	 * triggers on the second as well as minute, hour, day of month, month and
	 * day of week. e.g. {@code "0 * * * * MON-FRI"} means once per minute on
	 * weekdays (at the top of the minute - the 0th second).
	 * 
	 * @return an expression that can be parsed to a cron schedule
	 * @see org.springframework.scheduling.support.CronSequenceGenerator
	 */
	String cron() default "";
}
