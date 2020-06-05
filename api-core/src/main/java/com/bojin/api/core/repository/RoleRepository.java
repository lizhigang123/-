/** 
 * @(#)RoleRepository.java 1.0.0 2016年4月11日 下午3:16:26  
 *  
 * Copyright © 2016 善林金融.  All rights reserved.  
 */ 

package com.bojin.api.core.repository;

import java.util.Map;

import org.springframework.data.jpa.repository.Query;

import com.bojin.api.common.entity.Role;
import com.bojin.api.common.support.repository.BaseRepository;
import com.bojin.api.common.support.repository.page.PageMapRequest;
import com.bojin.api.common.support.repository.page.PageResponse;
import com.bojin.api.common.support.repository.page.QueryExtend;


/**   
 * 
 *  
 * @author  jeff
 * @version $Revision:1.0.0, $Date: 2016年4月11日 下午3:16:26 $ 
 */
public interface RoleRepository extends BaseRepository<Role> {
	@QueryExtend
	@Query(nativeQuery = true, value = "select r.*,u.name as user_name from api_t_role r,api_t_user u where r.create_user = u.id order by create_date desc")
	public PageResponse<Map<String, Object>> search(PageMapRequest pageRequest);

}
