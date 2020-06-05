package com.bojin.api.core.repository;

import java.util.List;
import java.util.Map;

import org.springframework.data.jpa.repository.Query;

import com.bojin.api.common.entity.User;
import com.bojin.api.common.support.repository.BaseRepository;
import com.bojin.api.common.support.repository.page.PageMapRequest;
import com.bojin.api.common.support.repository.page.PageResponse;
import com.bojin.api.common.support.repository.page.QueryExtend;
import com.bojin.api.core.repository.custom.UserRepositoryCust;

/**
 * @author samson
 * @date 2016/3/9 10:22
 */
public interface UserRepository extends BaseRepository<User>, UserRepositoryCust {

	public List<User> findAllByType(String type);

	public User findByLoginName(String loginName);
	@QueryExtend
	@Query(nativeQuery = true, value = "select * from api_t_user t  where ?( t.name like CONCAT('%', :name ,'%') )? and ?(t.login_name like  CONCAT('%', :loginName ,'%') )?   order by t.create_date desc")
	public PageResponse<Map<String, Object>> search(PageMapRequest pageRequest);
	
	/**
	 * 模糊匹配开发人员
	 * @author fuq
	 * @createTime 2016年4月20日 上午10:52:34
	 * @param pageRequest
	 * @return
	 */
	@QueryExtend
	@Query(nativeQuery = true, value = "select t.id,t.name from api_t_user t  where (t.name like  CONCAT('%', :name ,'%') or t.login_name like CONCAT('%', :name ,'%')) and t.id not in (select user_id from api_t_project_user_relation where project_id = :projectId)")
	public PageResponse<Map<String, Object>> matchUser(PageMapRequest pageRequest);
	
	
}
