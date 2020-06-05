/** 
 * @(#)ProjectUserRelationService.java 1.0.0 2016年4月11日 下午3:52:00  
 *  
 * Copyright © 2016 善林金融.  All rights reserved.  
 */

package com.bojin.api.core.service.admin;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bojin.api.common.entity.ProjectUserRelationEntity;
import com.bojin.api.core.repository.ProjectUserRelationRepository;
import com.bojin.api.core.service.BaseService;

/**
 * 项目开发人员关系服务
 * 
 * @author fuq
 * @version $Revision:1.0.0, $Date: 2016年4月11日 下午3:52:00 $
 */
@Service
public class ProjectUserRelationService extends BaseService<ProjectUserRelationEntity> {
	
	/**
	 * 查询项目下的所有开发人员
	 * @author fuq
	 * @createTime 2016年4月11日 下午5:37:40
	 * @param projectId
	 * @return
	 */
	public List<Map<String,Object>> findUsersByProject(String projectId){
		return getRepository().findUsersByProject(projectId);
	}
	
	/**
	 * 更新项目下关系实体
	 * @author fuq
	 * @createTime 2016年4月12日 上午9:08:20
	 * @param projectId
	 * @param users
	 * @return
	 */
	@Transactional
	public void updateProjectUsers(String projectId,List<String> users){
		ProjectUserRelationRepository repository = getRepository();
		List<ProjectUserRelationEntity> projectUsers = repository.findAllByProjectId(projectId);
		repository.delete(projectUsers);
		for(String userStr : users){
			ProjectUserRelationEntity pu = new ProjectUserRelationEntity();
			pu.setProjectId(projectId);
			pu.setUserId(userStr);
			repository.save(pu);
		}
	}

	public ProjectUserRelationRepository getRepository() {
		return (ProjectUserRelationRepository) baseRepository;
	}

}
