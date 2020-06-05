/** 
 * @(#)RoleRepository.java 1.0.0 2016年4月12日 上午10:37:35  
 *  
 * Copyright © 2016 善林金融.  All rights reserved.  
 */ 

package com.bojin.api.core.repository;

import java.util.List;

import com.bojin.api.common.entity.UserRole;
import com.bojin.api.common.support.repository.BaseRepository;

/**   
 * 
 *  
 * @author  LiYing
 * @version $Revision:1.0.0, $Date: 2016年4月12日 上午10:37:35 $ 
 */
public interface UserRoleRepository extends BaseRepository<UserRole>{

	List<UserRole> findByUserId(String userId);
	
	void deleteByRoleId(String roleId);
	
	List<UserRole> findByRoleId(String roleId);
}
