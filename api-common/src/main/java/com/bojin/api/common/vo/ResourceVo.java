/** 
 * @(#)ResourceTreeNode.java 1.0.0 2016年4月12日 上午11:46:31  
 *  
 * Copyright © 2016 善林金融.  All rights reserved.  
 */

package com.bojin.api.common.vo;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;

import com.bojin.api.common.entity.Resource;
import com.google.common.collect.Lists;

/**
 * 
 * 
 * @author LiYing
 * @version $Revision:1.0.0, $Date: 2016年4月12日 上午11:46:31 $
 */
@Data
@AllArgsConstructor
public class ResourceVo {

	private String id;
	private String parentId;
	private String name;
	private String url;
	private String description;
	private String childNo;
	private boolean checked = false;

	public static List<ResourceVo> buildResult(List<Resource> resources, List<Resource> roleResources) {
		List<ResourceVo> result = Lists.newArrayList();
		for (Resource resource : resources) {
			if (checkedResource(resource, roleResources)) {
				result.add(new ResourceVo(resource.getId(), resource.getParentId(), resource.getName(), resource.getUrl(), resource.getDescription(),"", true));
			} else {
				result.add(new ResourceVo(resource.getId(), resource.getParentId(), resource.getName(), resource.getUrl(), resource.getDescription(),"", false));
			}
		}
		return result;
	}

	private static boolean checkedResource(Resource resource, List<Resource> roleResources) {
		for (Resource resource1 : roleResources) {
			if (resource1.getId().equals(resource.getId())) {
				return true;
			}
		}
		return false;
	}

	public static List<ResourceVo> buildResult(List<ResourseChildVo> resourceChildVos) {
		List<ResourceVo> result = Lists.newArrayList();
		for (ResourseChildVo resourseChildVo : resourceChildVos) {
			result.add(new ResourceVo(resourseChildVo.getId(), resourseChildVo.getParentId(), resourseChildVo.getName(), resourseChildVo.getUrl(), resourseChildVo.getDescription(), String.valueOf(resourseChildVo.getChildNo()), false));
		}
		return result;
	}
	public static List<ResourceVo> buildResourceResult(List<Resource> resources) {
		List<ResourceVo> result = Lists.newArrayList();
		for (Resource resource : resources) {
			result.add(new ResourceVo(resource.getId(), resource.getParentId(), resource.getName(), resource.getUrl(), resource.getDescription(),"", false));
		}
		return result;
	}
}
