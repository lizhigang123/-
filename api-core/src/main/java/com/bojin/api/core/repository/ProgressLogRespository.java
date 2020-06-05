/** 
 * @(#)ProgressLogRespository.java 1.0.0 2016年4月26日 下午1:56:04  
 *  
 * Copyright © 2016 善林金融.  All rights reserved.  
 */

package com.bojin.api.core.repository;

import java.util.Map;

import org.springframework.data.jpa.repository.Query;

import com.bojin.api.common.entity.ProgressLog;
import com.bojin.api.common.support.repository.BaseRepository;
import com.bojin.api.common.support.repository.page.PageMapRequest;
import com.bojin.api.common.support.repository.page.PageResponse;
import com.bojin.api.common.support.repository.page.QueryExtend;

/**
 * 
 * 
 * @author jeff
 * @version $Revision:1.0.0, $Date: 2016年4月26日 下午1:56:04 $
 */
public interface ProgressLogRespository extends BaseRepository<ProgressLog> {

 	@QueryExtend
	@Query(nativeQuery = true, value = "select * from api_t_progress_log pl where pl.progress_id = :progressId order by pl.create_date desc")
	public PageResponse<Map<String, Object>> search(PageMapRequest pageRequest);
 
}
