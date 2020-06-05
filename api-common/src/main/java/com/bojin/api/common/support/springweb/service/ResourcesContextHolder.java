/** 
 * @(#)LoginUserContextHolder.java 1.0.0 2016年3月17日 下午3:45:48  
 *  
 * Copyright © 2016 善林金融.  All rights reserved.  
 */

package com.bojin.api.common.support.springweb.service;

import java.util.List;

import com.bojin.api.common.entity.Resource;
import com.google.common.collect.Lists;

/**
 * 
 * @author LiYing
 * @version $Revision:1.0.0, $Date: 2016年4月11日 下午1:28:46 $
 */
public class ResourcesContextHolder {

	private static ThreadLocal<List<Resource>> resources = new ThreadLocal<List<Resource>>(){
		@Override
		protected List<Resource> initialValue() {
			return Lists.newArrayList();
		}
	};

	public static List<Resource> getResources() {
		return resources.get();
	}

	public static void setResources(List<Resource> roleResources) {
		    resources.remove();
			resources.get().addAll(roleResources);
	}
}
