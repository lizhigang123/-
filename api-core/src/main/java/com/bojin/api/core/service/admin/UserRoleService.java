/** 
 * @(#)RoleResourceService.java 1.0.0 2016年4月12日 上午10:26:53  
 *  
 * Copyright © 2016 善林金融.  All rights reserved.  
 */

package com.bojin.api.core.service.admin;

import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bojin.api.common.entity.UserRole;
import com.bojin.api.common.exception.SLException;
import com.bojin.api.core.repository.UserRoleRepository;
import com.bojin.api.core.service.BaseService;
import com.google.common.collect.Lists;

/**
 * 
 * 
 * @author LiYing
 * @version $Revision:1.0.0, $Date: 2016年4月12日 上午10:26:53 $
 */
@Service
public class UserRoleService extends BaseService<UserRole> {

	public List<String> getRoleIds(String userId) {
		List<UserRole> userRoles = getRepository().findByUserId(userId);
		List<String> roleIds = Lists.newArrayList();
		for (UserRole userRole : userRoles) {
			roleIds.add(userRole.getRoleId());
		}
		return roleIds;
	}

	@Transactional
	public void saveRoleIds(String userId, String roleIds) throws SLException {
		if(StringUtils.isBlank(roleIds)) {
			throw new SLException("roleIds-empty", "roleIds can not empty");
		}
		List<UserRole> userRoleList = Lists.newArrayList();
		String[] roleIdsArray = roleIds.split(",");
		List<String> rolelist = Arrays.asList(roleIdsArray);
		for (String roleId : rolelist) {
			userRoleList.add(new UserRole(roleId, userId));
		}
		getRepository().deleteInBatch(getAllUserRole(userId));
		getRepository().save(userRoleList);
 	}

	public List<UserRole> getAllUserRole(String userId) {
		return getRepository().findByUserId(userId);
	}
	
	public void deleteByRoleId(String roleId) {
		getRepository().deleteByRoleId(roleId);
	}
	
	public List<UserRole> findByRoleId(String roleId) {
		return getRepository().findByRoleId(roleId);
	}
	

	private UserRoleRepository getRepository() {
		return (UserRoleRepository) baseRepository;
	}

}
