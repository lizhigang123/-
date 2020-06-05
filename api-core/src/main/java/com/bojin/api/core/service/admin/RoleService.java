/** 
 * @(#)RoleService.java 1.0.0 2016年4月11日 下午3:07:21  
 *  
 * Copyright © 2016 善林金融.  All rights reserved.  
 */

package com.bojin.api.core.service.admin;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bojin.api.common.entity.Role;
import com.bojin.api.common.entity.UserRole;
import com.bojin.api.common.exception.SLException;
import com.bojin.api.common.response.ResponseVo;
import com.bojin.api.common.support.repository.page.PageMapRequest;
import com.bojin.api.common.support.repository.page.PageResponse;
import com.bojin.api.common.vo.UserRoleVo;
import com.bojin.api.core.repository.RoleRepository;
import com.bojin.api.core.service.BaseService;

/**
 * 
 * 
 * @author jeff
 * @version $Revision:1.0.0, $Date: 2016年4月11日 下午3:07:21 $
 */
@Service
public class RoleService extends BaseService<Role> {
	private @Autowired RoleResourceService roleResourceService;
	private @Autowired UserRoleService userRoleService;

	public PageResponse<Map<String, Object>> search(PageMapRequest pageRequest) {
		return getRepository().search(pageRequest);
	}

	public RoleRepository getRepository() {
		return (RoleRepository) baseRepository;
	}

	@Transactional
	public ResponseVo deleteRole(String roleId) throws SLException {
		Role role = getRepository().findOne(roleId);
		if (role != null) {
			roleResourceService.deleteByRoleId(roleId);
			userRoleService.deleteByRoleId(roleId);
			getRepository().delete(roleId);
			return ResponseVo.success();
		} else {
			throw new SLException("不存在的接口");
		}

	}

	@Transactional
	public void submit(Role param, String[] resourceIds) throws Exception {
		Role role = null;
		if (StringUtils.isNotBlank(param.getId())) {
			role = getRepository().findOne(param.getId());
			if (role == null) {
				throw new SLException("不存在的资源");
			}
			role.setName(param.getName());
		} else {
			role = param;
		}
		Role roleRst = getRepository().save(role);
		List<String> resourceIdList = Arrays.asList(resourceIds);

		roleResourceService.allocateResources(roleRst.getId(), resourceIdList);
 	}

	public List<UserRoleVo> getUserRoles(String userId) {
		List<UserRole> userRoles = userRoleService.getAllUserRole(userId);
		List<Role> RoleList = getRepository().findAll();
		return UserRoleVo.buildResult(RoleList, userRoles);
	}
}
