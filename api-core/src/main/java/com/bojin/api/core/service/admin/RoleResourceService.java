/** 
 * @(#)RoleResourceService.java 1.0.0 2016年4月12日 上午10:26:53  
 *  
 * Copyright © 2016 善林金融.  All rights reserved.  
 */ 

package com.bojin.api.core.service.admin;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.bojin.api.common.entity.RoleResource;
import com.bojin.api.core.repository.RoleResourceRepository;
import com.bojin.api.core.service.BaseService;
import com.google.common.collect.Lists;

/**   
 * 
 *  
 * @author  LiYing
 * @version $Revision:1.0.0, $Date: 2016年4月12日 上午10:26:53 $ 
 */
@Service
public class RoleResourceService extends BaseService<RoleResource>{
	
	@Transactional
	public void allocateResources(String roleId, List<String> resourceIds) {
		List<RoleResource> roleResources = Lists.newArrayList();
		for(String resourceId : resourceIds) {
			roleResources.add(new RoleResource(resourceId, roleId));
		}
		getRepository().deleteInBatch(getAllocatedRoleResources(roleId));
		getRepository().save(roleResources);
	}
	
	private List<RoleResource> getAllocatedRoleResources(String roleId) {
		return getRepository().findByRoleId(roleId);
	}

	@Transactional
    public void deleteByRoleId(String roleId){
    	getRepository().deleteByRoleId(roleId);
    }
	
	@Transactional
	public void deleteByRoleResourceId(String resourceId) {
		getRepository().deleteByResourceId(resourceId);
	}
	public RoleResourceRepository getRepository() {
		return (RoleResourceRepository) baseRepository;
	}
}
