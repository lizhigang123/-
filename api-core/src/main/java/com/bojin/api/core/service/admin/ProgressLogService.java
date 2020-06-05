/** 
 * @(#)ProgressLogService.java 1.0.0 2016年4月26日 下午2:38:14  
 *  
 * Copyright © 2016 善林金融.  All rights reserved.  
 */

package com.bojin.api.core.service.admin;

import java.util.Map;

import org.springframework.stereotype.Service;

import com.bojin.api.common.entity.ProgressLog;
import com.bojin.api.common.support.repository.page.PageMapRequest;
import com.bojin.api.common.support.repository.page.PageResponse;
import com.bojin.api.core.repository.ProgressLogRespository;
import com.bojin.api.core.service.BaseService;

/**
 * 
 * 
 * @author jeff
 * @version $Revision:1.0.0, $Date: 2016年4月26日 下午2:38:14 $
 */
@Service
public class ProgressLogService extends BaseService<ProgressLog> {

	public PageResponse<Map<String, Object>> search(PageMapRequest pageRequest) {
		return getRepository().search(pageRequest);
 	}

	public ProgressLogRespository getRepository() {
		return (ProgressLogRespository) baseRepository;
	}
}
