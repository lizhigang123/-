package com.bojin.api.core.repository;

import java.util.List;

import com.bojin.api.common.entity.RoleResource;
import com.bojin.api.common.support.repository.BaseRepository;

/**
 * @author LiYing
 * @date 2016/4/12 10:22
 */
public interface RoleResourceRepository extends BaseRepository<RoleResource> {

	List<RoleResource> findByRoleId(String roleId);

	public void deleteByRoleId(String roleId);
	
	void deleteByResourceId(String resourceId);
}
