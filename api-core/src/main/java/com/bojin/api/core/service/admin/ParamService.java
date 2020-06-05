/** 
 * @(#)ProjectService.java 1.0.0 2016年3月10日 上午11:12:09  
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

import com.bojin.api.common.constants.Constants;
import com.bojin.api.common.entity.ParamEntity;
import com.bojin.api.common.entity.PropertyParamRelation;
import com.bojin.api.common.exception.SLException;
import com.bojin.api.common.response.ResponseVo;
import com.bojin.api.common.support.repository.page.PageMapRequest;
import com.bojin.api.common.support.repository.page.PageResponse;
import com.bojin.api.core.repository.ParamRepository;
import com.bojin.api.core.service.BaseService;

/**
 * 
 * 
 * @author samson
 * @version $Revision:1.0.0, $Date: 2016年3月10日 上午11:12:09 $
 */
@Service
public class ParamService extends BaseService<ParamEntity> {

	@Autowired
	private PropertyParamRelationService propertyParamRelationService;

	@Transactional
	public ParamEntity submit(ParamEntity param) throws Exception {
		ParamEntity paramEntity = null;
		if (StringUtils.isNotBlank(param.getId())) {
			paramEntity = baseRepository.findOne(param.getId());
			if (paramEntity == null) {
				throw new SLException("不存在的参数");
			}
			paramEntity.setName(param.getName());
			paramEntity.setNameDesc(param.getNameDesc());
			paramEntity.setDataType(param.getDataType());
			paramEntity.setNeed(param.getNeed());
			paramEntity.setDefaultValue(param.getDefaultValue());
			paramEntity.setVisible(param.getVisible());
			paramEntity.setDescription(param.getDescription());

		} else {
			paramEntity = param;
			if (StringUtils.isNotEmpty(param.getParentId())) {
				paramEntity.setParentId(param.getParentId());
			} else {
				paramEntity.setParentId(null);
			}
			Integer latestedOrder = getRepository().findLastedOrderByInterfaceIdAndParentIdAndType(param.getInterfaceId(), param.getParentId(), param.getType());
			latestedOrder = latestedOrder == null ? 0 : latestedOrder++;
			paramEntity.setOrdered(latestedOrder);
		}
		return baseRepository.save(paramEntity);
	};

	@Transactional
	public void submitTemplate(String propertiesId, String interfaceId, String type) {
		List<ParamEntity> ParamEntitys = getRepository().getProjectParamTemplate(propertiesId);
		for (ParamEntity paramEntity : ParamEntitys) {
			if (StringUtils.isBlank(paramEntity.getParentId())) {
				ParamEntity paramEntityIn = new ParamEntity();
				paramEntityIn.setInterfaceId(interfaceId);
				paramEntityIn.setName(paramEntity.getName());
				paramEntityIn.setNameDesc(paramEntity.getNameDesc());
				paramEntityIn.setDescription(paramEntity.getDescription());
				paramEntityIn.setDataType(paramEntity.getDataType());
				paramEntityIn.setNeed(paramEntity.getNeed());
				paramEntityIn.setType(type);
				paramEntityIn.setVisible(paramEntity.getVisible());
				paramEntityIn.setDefaultValue(paramEntity.getDefaultValue());
				paramEntityIn.setVersion(paramEntity.getVersion());
				paramEntityIn.setParentId(null);
				Integer latestedOrder = getRepository().findLastedOrderByInterfaceIdAndParentIdAndType(paramEntity.getInterfaceId(), paramEntity.getParentId(), paramEntity.getType());
				latestedOrder = latestedOrder == null ? 0 : latestedOrder++;
				paramEntityIn.setOrdered(latestedOrder);
				baseRepository.save(paramEntityIn);
				saveParamTemplate(ParamEntitys, paramEntity.getId(), paramEntityIn.getId(), interfaceId, type);
			}

		}
	}

	public void saveParamTemplate(List<ParamEntity> ParamEntitys, String parentId, String newParentId, String interfaceId, String type) {
		for (ParamEntity paramEntity : ParamEntitys) {
			if (StringUtils.isNotBlank(paramEntity.getParentId())) {
				if (paramEntity.getParentId().equals(parentId)) {
					ParamEntity paramEntityIn = new ParamEntity();
					paramEntityIn.setInterfaceId(interfaceId);
					paramEntityIn.setName(paramEntity.getName());
					paramEntityIn.setNameDesc(paramEntity.getNameDesc());
					paramEntityIn.setDescription(paramEntity.getDescription());
					paramEntityIn.setDataType(paramEntity.getDataType());
					paramEntityIn.setNeed(paramEntity.getNeed());
					paramEntityIn.setType(type);
					paramEntityIn.setParentId(newParentId);
					paramEntityIn.setVisible(paramEntity.getVisible());
					paramEntityIn.setDefaultValue(paramEntity.getDefaultValue());
					paramEntityIn.setVersion(paramEntity.getVersion());
					Integer latestedOrder = getRepository().findLastedOrderByInterfaceIdAndParentIdAndType(paramEntity.getInterfaceId(), paramEntity.getParentId(), paramEntity.getType());
					latestedOrder = latestedOrder == null ? 0 : latestedOrder++;
					paramEntityIn.setOrdered(latestedOrder);
					baseRepository.save(paramEntityIn);
					saveParamTemplate(ParamEntitys, paramEntity.getId(), paramEntityIn.getId(), interfaceId, type);
				}
			}
		}
	}

	@Transactional
	public ParamEntity submitParam(ParamEntity param, String propertiestId) throws Exception {
		ParamEntity paramEntity = null;

		if (StringUtils.isNotBlank(param.getId())) {
			paramEntity = baseRepository.findOne(param.getId());
			if (paramEntity == null) {
				throw new SLException("不存在的参数");
			}
			paramEntity.setName(param.getName());
			paramEntity.setNameDesc(param.getNameDesc());
			paramEntity.setDataType(param.getDataType());
			paramEntity.setNeed(param.getNeed());
			paramEntity.setDefaultValue(param.getDefaultValue());
			paramEntity.setVisible(param.getVisible());
			paramEntity.setDescription(param.getDescription());

		} else {
			paramEntity = param;
			if (StringUtils.isNotEmpty(param.getParentId())) {
				paramEntity.setParentId(param.getParentId());
			} else {
				paramEntity.setParentId(null);
			}
			Integer latestedOrder = getRepository().findLastedOrderByParentIdAndType(param.getParentId(), param.getType());
			latestedOrder = latestedOrder == null ? 0 : latestedOrder++;
			paramEntity.setOrdered(latestedOrder);
		}
		paramEntity = baseRepository.save(paramEntity);
		if (StringUtils.isBlank(param.getId())) {
			PropertyParamRelation propertyParamRelationEntity = new PropertyParamRelation();
			propertyParamRelationEntity.setProjectPropertiesId(propertiestId);
			propertyParamRelationEntity.setParamId(paramEntity.getId());
			propertyParamRelationService.save(propertyParamRelationEntity);
		}
		return paramEntity;

	};

	public PageResponse<ParamEntity> getParams(String interfaceId) {
		List<ParamEntity> list = getRepository().findByInterfaceIdOrderByOrderedAsc(interfaceId);
		for (ParamEntity paramEntity : list) {
			if (StringUtils.isBlank(paramEntity.getParentId())) {
				paramEntity.setParentId(null);
			}
		}
		return new PageResponse<ParamEntity>(list.size(), list);
	}

	public PageResponse<ParamEntity> getParamsTemplate(String propertiestId) {
		List<ParamEntity> list = getRepository().getProjectParamTemplate(propertiestId);
		for (ParamEntity paramEntity : list) {
			if (StringUtils.isBlank(paramEntity.getParentId())) {
				paramEntity.setParentId(null);
			}
		}
		return new PageResponse<ParamEntity>(list.size(), list);
	}

	public PageResponse<ParamEntity> getInputParams(String interfaceId) {
		List<ParamEntity> list = getRepository().findByInterfaceIdAndTypeOrderByOrderedAsc(interfaceId, Constants.PARAM_TYPE_INPUT);
		return new PageResponse<ParamEntity>(list.size(), list);
	}

	public PageResponse<Map<String, Object>> getAllParamByName(PageMapRequest pageRequest) {
		return getRepository().getAllParamByName(pageRequest);
	}

	public PageResponse<ParamEntity> getOutputParams(String interfaceId) {
		List<ParamEntity> list = getRepository().findByInterfaceIdAndTypeOrderByOrderedAsc(interfaceId, Constants.PARAM_TYPE_OUTPUT);
		return new PageResponse<ParamEntity>(list.size(), list);
	}

	@Transactional
	public void delete(String id) {
		getRepository().delete(id);
	}

	@Transactional
	public void deleteTemplate(String id) {
		String relationId = propertyParamRelationService.getIdByParamId(id);
		propertyParamRelationService.delete(relationId);
		getRepository().delete(id);
	}

	@Transactional
	public void updateOrdered(String[] ids) {
		for (int i = 0; i < ids.length; i++) {
			if (StringUtils.isNotBlank(ids[i])) {
				ParamEntity paramEntity = getRepository().findOne(ids[i]);
				if (paramEntity != null) {
					paramEntity.setOrdered(i);
					save(paramEntity);
				}
			}
		}
	}

	/**
	 * 根据参数名来匹配参数
	 * 
	 * @author jeff
	 * @createTime 2016年5月12日 下午7:53:27
	 * @param pageRequest
	 * @return
	 */
	public PageResponse<Map<String, Object>> matchParams(PageMapRequest pageRequest) {
		return getRepository().matchParams(pageRequest);
	}

	public ResponseVo saveParams(String interfaceId, String parentId, String type, String paramIds) throws SLException {
		for (String paramId : Arrays.asList(paramIds.split(","))) {
			ParamEntity param = new ParamEntity();
			ParamEntity paramEntity = baseRepository.findOne(paramId);
			if (paramEntity == null) {
				throw new SLException("不存在的参数");
			}
			param.setInterfaceId(interfaceId);
			if (StringUtils.isNotBlank(paramId)) {
				param.setParentId(parentId);
			}
			param.setName(paramEntity.getName());
			param.setNameDesc(paramEntity.getNameDesc());
			param.setType(type);
			param.setDescription(paramEntity.getDescription());
			param.setDataType(paramEntity.getDataType());
			param.setDefaultValue(paramEntity.getDefaultValue());
			param.setNeed(paramEntity.getNeed());
			param.setVisible(paramEntity.getVisible());
			baseRepository.save(param);
		}
		return ResponseVo.success();
	}

	public ParamRepository getRepository() {
		return (ParamRepository) baseRepository;
	}

	public List<ParamEntity> findByInterfaceIdAndType(String interfaceId, String type) {
		return getRepository().findByInterfaceIdAndTypeOrderByOrderedAsc(interfaceId, type);
	}
	
	public List<ParamEntity> findByInterfaceIdAndTypeAndParentIdIsNullOrderByOrderedAsc(String interfaceId, String type) {
		return getRepository().findByInterfaceIdAndTypeAndParentIdIsNullOrderByOrderedAsc(interfaceId, type);
	}
	
	public List<ParamEntity> findByInterfaceIdAndTypeAndParentIdIdOrderByOrderedAsc(String interfaceId, String type,String parentIdId) {
		return getRepository().findByInterfaceIdAndTypeAndParentIdOrderByOrderedAsc(interfaceId, type,parentIdId);
	}
}
