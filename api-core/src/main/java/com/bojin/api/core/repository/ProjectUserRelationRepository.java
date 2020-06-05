/** 
 * @(#)ProjectUserRelationRepository.java 1.0.0 2016年4月11日 下午3:55:12  
 *  
 * Copyright © 2016 善林金融.  All rights reserved.  
 */

package com.bojin.api.core.repository;

import java.util.List;
import java.util.Map;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.bojin.api.common.entity.ProjectUserRelationEntity;
import com.bojin.api.common.support.repository.BaseRepository;
import com.bojin.api.common.support.repository.page.QueryExtend;

/**
 * 
 * 
 * @author fuq
 * @version $Revision:1.0.0, $Date: 2016年4月11日 下午3:55:12 $
 */
public interface ProjectUserRelationRepository extends BaseRepository<ProjectUserRelationEntity> {
	/**
	 * 查询项目下所有开发人员
	 * @author fuq
	 * @createTime 2016年4月11日 下午5:54:33
	 * @param projectId
	 * @return
	 */
	@QueryExtend
	@Query(nativeQuery = true, value = "select u.id,u.name from api_t_project_user_relation pu left join api_t_user u on pu.user_id = u.id where project_id = :projectId")
	public List<Map<String,Object>> findUsersByProject(@Param("projectId")String projectId);
	
	/**
	 * 查询所有关系实体
	 * @author fuq
	 * @createTime 2016年4月11日 下午5:54:53
	 * @param projectId
	 * @return
	 */
	public List<ProjectUserRelationEntity> findAllByProjectId(String projectId);
}
