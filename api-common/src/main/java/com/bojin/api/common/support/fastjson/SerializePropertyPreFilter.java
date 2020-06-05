/** 
 * @(#)SerializePropertyPreFilter.java 1.0.0 2015年12月14日 下午6:33:09  
 *  
 * Copyright © 2015 善林金融.  All rights reserved.  
 */

package com.bojin.api.common.support.fastjson;

import java.util.HashSet;
import java.util.Set;

import com.alibaba.fastjson.serializer.JSONSerializer;
import com.alibaba.fastjson.serializer.PropertyPreFilter;

/**
 * 基于fastjson序列化过滤器
 * 
 * @author samson
 * @version $Revision:1.0.0, $Date: 2015年12月14日 下午6:33:09 $
 */
public class SerializePropertyPreFilter implements PropertyPreFilter {

	private final Class<?> clazz;
	private final Set<String> includes = new HashSet<String>();
	private final Set<String> excludes = new HashSet<String>();

	public SerializePropertyPreFilter(Class<?> clazz) {
		this.clazz = clazz;
	}

	public SerializePropertyPreFilter(Class<?> clazz, String[] includeNames, String[] excludeNames) {
		this(clazz);
		addInclude(includeNames);
		addExclude(excludeNames);
	}

	/**
	 * 增加包括的key
	 * 
	 * @param name
	 */
	public void addInclude(String name) {
		includes.add(name);
	}

	/**
	 * 增加剔除的key
	 * 
	 * @param name
	 */
	public void addExclude(String name) {
		excludes.add(name);
	}

	/**
	 * 批量添加序列化key
	 * 
	 * @param includes
	 */
	public void addInclude(String[] includes) {
		if (includes != null) {
			for (String name : includes) {
				this.includes.add(name);
			}
		}
	}

	/**
	 * 批量添加剔除的key
	 * 
	 * @param excludes
	 */
	public void addExclude(String[] excludes) {
		if (excludes != null) {
			for (String name : excludes) {
				this.excludes.add(name);
			}
		}
	}

	@Override
	public boolean apply(JSONSerializer serializer, Object source, String name) {
		if (source == null) {
			return true;
		}

		if (clazz != null && !clazz.isInstance(source)) {
			return true;
		}

		if (this.excludes.contains(name)) {
			return false;
		}

		if (includes.size() == 0 || includes.contains(name)) {
			return true;
		}

		return false;
	}
}
