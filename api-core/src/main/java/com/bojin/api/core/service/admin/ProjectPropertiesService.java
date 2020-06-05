package com.bojin.api.core.service.admin;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import com.bojin.api.common.constants.Constants;
import com.bojin.api.common.entity.ProjectProperties;
import com.bojin.api.common.exception.SLException;
import com.bojin.api.common.support.repository.page.PageMapRequest;
import com.bojin.api.common.support.repository.page.PageResponse;
import com.bojin.api.core.repository.ProjectPropertiesRepository;
import com.bojin.api.core.service.BaseService;

/**
 * @author samson
 * @date 2016/3/22 16:34
 */
@Service
public class ProjectPropertiesService extends BaseService<ProjectProperties> {

	public PageResponse<ProjectProperties> searchProperties(PageMapRequest pageMapRequest) {
 		return getRepository().search(pageMapRequest);
	}
	public PageResponse<ProjectProperties> searchParamTemplate(PageMapRequest pageMapRequest) {
		String type = ObjectUtils.toString(pageMapRequest.getParam("type"));
		if(StringUtils.isNotBlank(type)){
			pageMapRequest.addParam("type", Arrays.asList(type.split(",")));
		}
		return getRepository().search(pageMapRequest);
 	}
 
	public PageResponse<Map<String, Object>> getParamsTemplates(PageMapRequest pageRequest) {
		return getRepository().getParamsTemplates(pageRequest);
 	}
	public ProjectProperties submit(ProjectProperties properties) throws SLException {
		ProjectProperties projectProperties = null;
		if (StringUtils.isNotBlank(properties.getId())) {
			projectProperties = baseRepository.findOne(properties.getId());
			if (projectProperties == null) {
				throw new SLException("不存在的项目属性");
			}
			projectProperties.setType(properties.getType());
			projectProperties.setName(properties.getName());
			projectProperties.setNameDesc(properties.getNameDesc());
			projectProperties.setDataType(properties.getDataType());
			projectProperties.setVisible(properties.getVisible());
			projectProperties.setNeed(properties.getNeed());
			projectProperties.setDescription(properties.getDescription());

		} else {
			projectProperties = properties;
		}
		return baseRepository.save(projectProperties);
	}

	public List<ProjectProperties> findFrontList(String projectId) {
		return getRepository().findAllByProjectIdAndTypeAndVisible(projectId, Constants.PROJECT_PROPERTIES_TYPE_COMMONREQUESTPARAM, Constants.COMMON_YES);
	}

	public ProjectPropertiesRepository getRepository() {
		return (ProjectPropertiesRepository) baseRepository;
	}

}
