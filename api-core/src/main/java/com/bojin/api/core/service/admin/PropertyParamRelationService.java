package com.bojin.api.core.service.admin;



import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bojin.api.common.entity.PropertyParamRelation;
import com.bojin.api.core.repository.PropertyParamRelationRepository;
import com.bojin.api.core.service.BaseService;
@Service
public class PropertyParamRelationService extends BaseService<PropertyParamRelation> {
	public String getIdByParamId(String paramId){
		String relationId = getRepository().getIdByParamId(paramId);
		return relationId;
	}
	public PropertyParamRelationRepository getRepository() {
		return (PropertyParamRelationRepository) baseRepository;
	}
	@Transactional
	public void delete(String id) {
		getRepository().delete(id);
	}
}
