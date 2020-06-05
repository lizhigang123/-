/** 
 * @(#)ProjectService.java 1.0.0 2016年3月10日 上午11:12:09  
 *  
 * Copyright © 2016 善林金融.  All rights reserved.  
 */

package com.bojin.api.core.service.admin;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import com.bojin.api.common.entity.Project;
import com.bojin.api.common.exception.SLException;
import com.bojin.api.common.support.repository.page.PageMapRequest;
import com.bojin.api.common.support.repository.page.PageResponse;
import com.bojin.api.core.repository.ProjectRepository;
import com.bojin.api.core.service.BaseService;

/**
 * 
 * 
 * @author samson
 * @version $Revision:1.0.0, $Date: 2016年3月10日 上午11:12:09 $
 */
@Service
public class ProjectService extends BaseService<Project> {

	public Project submit(Project project) throws Exception {
		Project projectEntity = null;
		if (StringUtils.isNotBlank(project.getId())) {
			projectEntity = baseRepository.findOne(project.getId());
			if (projectEntity == null) {
				throw new SLException("不存在的项目");
			}
			projectEntity.setName(project.getName());
			projectEntity.setDescription(project.getDescription());
			projectEntity.setLeader(project.getLeader());
			projectEntity.setStartDate(project.getStartDate());
			projectEntity.setSvnUrl(project.getSvnUrl());
			projectEntity.setTestUrl(project.getTestUrl());
			projectEntity.setProductionUrl(project.getProductionUrl());
		} else {
			projectEntity = project;
		}
		return baseRepository.save(projectEntity);
	};

	public PageResponse<Map<String, Object>> search(PageMapRequest pageRequest) {
		return getRepository().search(pageRequest);
	}
	
	public List<Project> findAllProjects(){
		return getRepository().findAllProjects();
	}

	public ProjectRepository getRepository() {
		return (ProjectRepository) baseRepository;
	}

	/***************************************** 分支管理 *********************************************/
	public PageResponse<Map<String, Object>> subProjectSearch(PageMapRequest pageRequest) {
		return getRepository().subProjectSearch(pageRequest);
	}
	
	public Project findOne(String projectId){
		return getRepository().findOne(projectId);
	}
}
