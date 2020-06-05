/** 
 * @(#)RoleResourceService.java 1.0.0 2016年4月12日 上午10:26:53  
 *  
 * Copyright © 2016 善林金融.  All rights reserved.  
 */

package com.bojin.api.core.service.admin;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bojin.api.common.entity.Resource;
import com.bojin.api.common.entity.UserRole;
import com.bojin.api.common.exception.SLException;
import com.bojin.api.common.vo.ResourceVo;
import com.bojin.api.common.vo.ResourseChildVo;
import com.bojin.api.core.repository.ResourceRepository;
import com.bojin.api.core.service.BaseService;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;

/**
 * 
 * 
 * @author LiYing
 * @version $Revision:1.0.0, $Date: 2016年4月12日 上午10:26:53 $
 */
@Service
public class ResourceService extends BaseService<Resource> {
	
	@Autowired
	private UserRoleService userRoleService; 
	@Autowired
	private RoleResourceService roleResourceService;

	public List<Resource> getUserResources(String userId) {
		List<UserRole> roles = userRoleService.getAllUserRole(userId);
		Set<Resource> resources = Sets.newHashSet();
		for(UserRole userRole : roles) {
			resources.addAll(geRoleResources(userRole.getRoleId()));
		}
		return Lists.newArrayList(resources) ;
	}

	public List<Resource> geRoleResources(String roleId) {
		return getRepository().findResourceByRoleId(roleId);
	}

	public List<ResourceVo> getResources() {
		List<Resource> resources = getRepository().findAllByOrderByOrderedAsc();
		return ResourceVo.buildResourceResult(resources);
	}

	public List<ResourceVo> getResources(String parentId) {
		if (StringUtils.isBlank(parentId)) {
			List<Resource> resources = getRepository().findByParentIdIsNullOrderByOrderedAsc();
			List<ResourseChildVo> resourceChildVos = new ArrayList<ResourseChildVo>();
			for (Resource resource : resources) {
				ResourseChildVo resourseChildVo = new ResourseChildVo();
				resourseChildVo.setId(resource.getId());
				resourseChildVo.setName(resource.getName());
				resourseChildVo.setUrl(resource.getUrl());
				resourseChildVo.setDescription(resource.getDescription());
				resourseChildVo.setParentId(resource.getParentId());
				Integer childNo = getRepository().findChildNoByParentId(resource.getId());
				resourseChildVo.setChildNo(childNo);
				resourceChildVos.add(resourseChildVo);
			}

			return ResourceVo.buildResult(resourceChildVos);
		}
		List<Resource> resources = getRepository().findByParentIdOrderByOrderedAsc(parentId);
		List<ResourseChildVo> resourceChildVos = new ArrayList<ResourseChildVo>();
		for (Resource resource : resources) {
			ResourseChildVo resourseChildVo = new ResourseChildVo();
			resourseChildVo.setId(resource.getId());
			resourseChildVo.setName(resource.getName());
			resourseChildVo.setUrl(resource.getUrl());
			resourseChildVo.setDescription(resource.getDescription());
			resourseChildVo.setParentId(resource.getParentId());
			Integer childNo = getRepository().findChildNoByParentId(resource.getId());
			resourseChildVo.setChildNo(childNo);
			resourceChildVos.add(resourseChildVo);
		}
		return ResourceVo.buildResult(resourceChildVos);
	}

	public List<ResourceVo> getRoleResources(String roleId) {
 		List<Resource> resources = getRepository().findAllByOrderByOrderedAsc();
		return ResourceVo.buildResult(resources, geRoleResources(roleId));
	}

	@Transactional
	public Resource submit(Resource param) throws Exception {
		Resource resource = null;
		if (StringUtils.isNotBlank(param.getId())) {
			resource = getRepository().findOne(param.getId());
			if (resource == null) {
				throw new SLException("不存在的资源");
			}
			resource.setName(param.getName());
			resource.setUrl(param.getUrl());
			resource.setDescription(param.getDescription());
		} else {
			resource = param;
			if (StringUtils.isNotEmpty(param.getParentId())) {
				resource.setParentId(param.getParentId());
			} else {
				resource.setParentId(null);
			}
			Resource resourceTemp = getRepository().findTopByParentIdOrderByOrderedDesc(param.getParentId());
			Integer latestedOrder;
			if (resourceTemp == null) {
				latestedOrder = 0;
			} else {
				int ordered = resourceTemp.getOrdered();
				latestedOrder = ordered++;
			}
 			resource.setOrdered(latestedOrder);
		}
		return baseRepository.save(resource);
	}

	@Transactional
	public void delete(String id) {
		roleResourceService.deleteByRoleResourceId(id);
		getRepository().delete(id);
	}

	@Transactional
	public void updateOrdered(String[] ids) {
		for (int i = 0; i < ids.length; i++) {
			if (StringUtils.isNotBlank(ids[i])) {
				Resource resource = getRepository().findOne(ids[i]);
				if (resource != null) {
					resource.setOrdered(i);
					save(resource);
				}
			}
		}
	}

	public ResourceRepository getRepository() {
		return (ResourceRepository) baseRepository;
	}
}
