/** 
 * @(#)OptimizeSql.java 1.0.0 2016年1月8日 上午11:43:02  
 *  
 * Copyright © 2016 善林金融.  All rights reserved.  
 */

package com.bojin.api.common.support.repository;

import java.util.Map;

import com.bojin.api.common.support.repository.page.PageRequest;

/**
 * sql优化
 * 
 * @author samson
 * @version $Revision:1.0.0, $Date: 2016年1月8日 上午11:43:02 $
 */
public interface OptimizeSql {

	/**
	 * 获取优化后的sql
	 * 
	 * @return
	 */
	public String getSql();

	/**
	 * 已Map形式获取优化后的参数
	 * 
	 * @return
	 */
	public Map<String, Object> getParameters();

	/**
	 * 获取原始参数数组
	 * 
	 * @return
	 */
	public Object[] getArgs();

	/**
	 * 获取分页的参数
	 * 
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public PageRequest getPageArg();

}
