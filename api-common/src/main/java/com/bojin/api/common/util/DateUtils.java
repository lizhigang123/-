/** 
 * @(#)DateUtils.java 1.0.0 2016年4月27日 下午1:50:39  
 *  
 * Copyright © 2016 善林金融.  All rights reserved.  
 */

package com.bojin.api.common.util;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 时间工具类
 * 
 * @author jeff
 * @version $Revision:1.0.0, $Date: 2016年4月27日 下午1:50:39 $
 */
public class DateUtils {
	/**
	 * 根据时间获取yyyy-MM-dd
	 * 
	 * @author jeff
	 * @createTime 2016年4月27日 下午2:27:43
	 * @param date
	 * @return
	 */
	public static String getDay(Date date) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		return sdf.format(date);
	}
}
