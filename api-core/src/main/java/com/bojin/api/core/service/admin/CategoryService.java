/** 
 * @(#)ProjectService.java 1.0.0 2016年3月10日 上午11:12:09  
 *  
 * Copyright © 2016 善林金融.  All rights reserved.  
 */

package com.bojin.api.core.service.admin;

import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import com.bojin.api.common.entity.Category;
import com.bojin.api.common.exception.SLException;
import com.bojin.api.common.support.repository.page.PageMapRequest;
import com.bojin.api.common.support.repository.page.PageResponse;
import com.bojin.api.core.repository.CategoryRepository;
import com.bojin.api.core.service.BaseService;

/**
 * 
 * 
 * @author samson
 * @version $Revision:1.0.0, $Date: 2016年3月10日 上午11:12:09 $
 */
@Service
public class CategoryService extends BaseService<Category> {

	public Category submit(Category category) throws Exception {
		Category categoryEntity = null;
		if (StringUtils.isNotBlank(category.getId())) {
			categoryEntity = baseRepository.findOne(category.getId());
			if (categoryEntity == null) {
				throw new SLException("不存在的类目");
			}
			categoryEntity.setName(category.getName());
			categoryEntity.setVisible(category.getVisible());
			categoryEntity.setDescription(category.getDescription());
			categoryEntity.setSort(category.getSort());

		} else {
			categoryEntity = category;
		}
		categoryEntity.setSort(categoryEntity.getSort() == null ? 0 : categoryEntity.getSort());
		return baseRepository.save(categoryEntity);
	};

	public PageResponse<Map<String, Object>> search(PageMapRequest pageRequest) {
		return getRepository().search(pageRequest);
	}

	public CategoryRepository getRepository() {
		return (CategoryRepository) baseRepository;
	}
}
