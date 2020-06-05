/** 
 * @(#)UserRoleVo.java 1.0.0 2016年4月18日 下午5:34:38  
 *  
 * Copyright © 2016 善林金融.  All rights reserved.  
 */

package com.bojin.api.common.vo;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;

import com.bojin.api.common.entity.Role;
import com.bojin.api.common.entity.UserRole;
import com.google.common.collect.Lists;

/**
 * 
 * 
 * @author jeff
 * @version $Revision:1.0.0, $Date: 2016年4月18日 下午5:34:38 $
 */
@Data
@AllArgsConstructor
public class UserRoleVo {
	private String id;
	private String name;
	private boolean checked = false;

	public static List<UserRoleVo> buildResult(List<Role> RoleList, List<UserRole> userRoles) {
		List<UserRoleVo> result = Lists.newArrayList();
		for (Role role : RoleList) {
			if (checkedRole(role, userRoles)) {
				result.add(new UserRoleVo(role.getId(), role.getName(), true));
			} else {
				result.add(new UserRoleVo(role.getId(), role.getName(), false));
			}
		}
		return result;
	}

	private static boolean checkedRole(Role role, List<UserRole> userRoles) {
		for (UserRole userRole : userRoles) {
			if (userRole.getRoleId().equals(role.getId())) {
				return true;
			}
		}
		return false;
	}
}
