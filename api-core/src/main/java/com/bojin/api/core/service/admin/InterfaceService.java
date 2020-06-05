/**
 * @(#)ProjectService.java 1.0.0 2016年3月10日 上午11:12:09
 * <p/>
 * Copyright © 2016 善林金融.  All rights reserved.
 */

package com.bojin.api.core.service.admin;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import com.bojin.api.common.constants.enums.InterfaceStatusEnum;
import com.bojin.api.common.constants.enums.YesOrNoEnum;
import com.bojin.api.common.entity.Interface;
import com.bojin.api.common.exception.SLException;
import com.bojin.api.common.response.ResponseVo;
import com.bojin.api.common.support.repository.page.PageMapRequest;
import com.bojin.api.common.support.repository.page.PageResponse;
import com.bojin.api.core.repository.InterfaceRepository;
import com.bojin.api.core.service.BaseService;

/**
 * @author samson
 * @version $Revision:1.0.0, $Date: 2016年3月10日 上午11:12:09 $
 */
@Service
public class InterfaceService extends BaseService<Interface> {

	public Interface submit(Interface it) throws Exception {
		Interface interfaceEntity = null;
		if (StringUtils.isNotBlank(it.getId())) {
			interfaceEntity = baseRepository.findOne(it.getId());
			if (interfaceEntity == null) {
				throw new SLException("不存在的接口");
			}
			interfaceEntity.setName(it.getName());
			interfaceEntity.setUrl(it.getUrl());
			interfaceEntity.setDescription(it.getDescription());
			interfaceEntity.setV(it.getV());
			interfaceEntity.setAuthorize(it.getAuthorize());
			interfaceEntity.setVisible(it.getVisible());
			interfaceEntity.setCategoryId(it.getCategoryId());

		} else {
			interfaceEntity = it;
			interfaceEntity.setStatus(InterfaceStatusEnum.未发布);
		}
		return baseRepository.save(interfaceEntity);
	}

	;

	public PageResponse<Map<String, Object>> search(PageMapRequest pageRequest) {
//		PageResponse<Map<String, Object>> pageResponse = null;
//		String branckId = ObjectUtils.toString(pageRequest.getParam("branchId"));
//		String categoryId = ObjectUtils.toString(pageRequest.getParam("categoryId"));
//		if (StringUtils.isBlank(branckId) && StringUtils.isBlank(categoryId)) {
//			pageResponse = getRepository().searchByProjectId(pageRequest);
//		} else if (StringUtils.isNotBlank(branckId) && StringUtils.isBlank(categoryId)) {
//			pageResponse = getRepository().searchByBranchId(pageRequest);
//		} else if (StringUtils.isBlank(branckId) && StringUtils.isNotBlank(categoryId)) {
//			pageResponse = getRepository().searchByCategoryId(pageRequest);
//		}  
//		pageResponse = new PageMapResponse(0, new ArrayList<Map<String, Object>>(0));
 		return getRepository().search(pageRequest);
	}

	public Map<String, Object> findOneWithExtraInfo(String id) {
		return getRepository().findOneWithExtraInfo(id);
	}

	;

	public InterfaceRepository getRepository() {
		return (InterfaceRepository) baseRepository;
	}

	public List<Interface> findByCategoryId(String categoryId) {
		return getRepository().findByCategoryIdAndStatusAndVisible(categoryId, InterfaceStatusEnum.发布, YesOrNoEnum.是);
	}

	public Interface findOne(String interfaceId) {
		return getRepository().findOne(interfaceId);
	}

	public ResponseVo updateStatus(String id, InterfaceStatusEnum status) throws SLException {
		Interface interfaceEntity = baseRepository.findOne(id);
		if (interfaceEntity != null) {
			interfaceEntity.setStatus(status);
			baseRepository.save(interfaceEntity);
			return ResponseVo.success();
		} else {
			throw new SLException("不存在的接口");
		}
	}

}
