package com.bojin.api.core.repository;

import com.bojin.api.common.constants.enums.InterfaceStatusEnum;
import com.bojin.api.common.constants.enums.YesOrNoEnum;
import com.bojin.api.common.entity.Interface;
import com.bojin.api.common.support.repository.BaseRepository;
import com.bojin.api.common.support.repository.page.PageRequest;
import com.bojin.api.common.support.repository.page.PageResponse;
import com.bojin.api.common.support.repository.page.QueryExtend;
import com.bojin.api.core.repository.custom.InterfaceRepositoryCust;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Map;

/**
 * @author samson
 * @date 2016/
 * 3/9 10:23
 */
public interface InterfaceRepository extends BaseRepository<Interface>, InterfaceRepositoryCust {

	@QueryExtend
	@Query(nativeQuery = true, value = "SELECT i.*,c.name AS category_name,p.name AS branch_name,pp.name AS project_name FROM api_t_interface i, api_t_category c, api_t_project p, api_t_project pp WHERE ?( i.branch_id IN ( SELECT p2. ID FROM api_t_project p1, api_t_project p2 WHERE p1. ID = p2.parent_id AND p1. ID = : projectId ))? AND i.category_id = C. ID AND i.branch_id = P . ID AND P .parent_id = pp. ID AND ?(i.authorize=cast(:authorize as signed))? AND ?(I. NAME LIKE CONCAT( '%' , :name , '%') )? AND ?(I.url LIKE CONCAT('%', :url ,'%' ) )? AND ?(I.v=cast( :v  as signed))? AND ?(I.status=:status)? AND ?(I.visible=:visible)? ORDER BY i.create_date DESC")
	public PageResponse<Map<String, Object>> searchByProjectId(PageRequest<Map<String, Object>> pageRequest);

	@QueryExtend
	@Query(nativeQuery = true, value = "SELECT i.*,c.name AS category_name,p.name AS branch_name,pp.name AS project_name FROM api_t_interface i, api_t_category c, api_t_project p, api_t_project pp WHERE ?( i.branch_id IN ( SELECT p2. ID FROM api_t_project p1, api_t_project p2 WHERE p1. ID = p2.parent_id AND p1. ID = : projectId ))? AND i.category_id = C. ID AND i.branch_id = P . ID AND P .parent_id = pp. ID AND I.category_id = :categoryId AND ?(i.authorize=cast(:authorize as signed))? AND ?(I. NAME LIKE  CONCAT('%' , :name , '%') )? AND ?(I.url LIKE  CONCAT('%', :url ,'%') )? AND ?(I.v=cast(:v as signed))? AND ?(I.status=:status)? AND ?(I.visible=:visible)?  ORDER BY i.create_date DESC")
	public PageResponse<Map<String, Object>> searchByCategoryId(PageRequest<Map<String, Object>> pageRequest);

	@QueryExtend
	@Query(nativeQuery = true, value = "SELECT i.*,c.name AS category_name,p.name AS branch_name,pp.name AS project_name FROM api_t_interface i, api_t_category c, api_t_project p, api_t_project pp WHERE ?( i.branch_id IN ( SELECT p2. ID FROM api_t_project p1, api_t_project p2 WHERE p1. ID = p2.parent_id AND p1. ID = : projectId ))? AND i.category_id = C. ID AND i.branch_id = P . ID AND P .parent_id = pp. ID AND I.branch_id = :branchId AND ?(i.authorize=cast(:authorize as signed))? AND ?(I. NAME LIKE CONCAT('%' , :name , '%') )? AND ?(I.url LIKE CONCAT('%', :url ,'%') )? AND ?(I.v=cast(:v as signed))? AND ?(I.status=:status)? AND ?(I.visible=:visible)?  ORDER BY i.create_date DESC")
	public PageResponse<Map<String, Object>> searchByBranchId(PageRequest<Map<String, Object>> pageRequest);
	
	@QueryExtend
	@Query(nativeQuery = true, value = "SELECT i.*, c.NAME AS category_name, p.NAME AS project_name FROM api_t_interface i, api_t_category c, api_t_project p WHERE i.category_id = c.ID AND c.project_id = p.ID AND ?(i.category_id = :categoryId)? AND ?(c.project_id = :projectId)? AND ?(i.authorize = CAST( :authorize AS signed) )? AND ?(i. NAME LIKE CONCAT('%' , :name , '%') )? AND ?(i.url LIKE CONCAT('%' , :url , '%') )? AND ?( i.v = CAST(:v AS signed) )? AND ?(i.status = :status)? AND ?(i.visible = :visible)? ORDER BY i.create_date DESC")
	public PageResponse<Map<String, Object>> search(PageRequest<Map<String, Object>> pageRequest);
	
	@QueryExtend
	@Query(nativeQuery = true, value = "SELECT i.*,p.id AS project_id FROM api_t_interface i,api_t_category c,api_t_project p WHERE i.category_id = c.id AND c.project_id = p.id AND i.id = :id")
	public Map<String, Object> findOneWithExtraInfo(@Param("id") String id);

	/**
	 * 前端查询类目下所有接口
	 *
	 * @param categoryId
	 * @return
	 */
	public List<Interface> findByCategoryIdAndStatusAndVisible(String categoryId, InterfaceStatusEnum status, YesOrNoEnum visible);

	public Interface findOne(@Param("interfaceId") String interfaceId);
	
	/**
	 * 模糊接口模糊匹配查询
	 *
	 * @param categoryId
	 * @return
	 */
	@QueryExtend
	@Query(nativeQuery = true, value = "SELECT i.id,i.url,i.name, C . NAME AS category_name, P . NAME AS project_name FROM api_t_interface i, api_t_category C, api_t_project P WHERE i.category_id = C . ID AND C .project_id = P . ID AND (i.url LIKE CONCAT('%' , :keyword , '%' ) OR i.name LIKE  CONCAT('%' , :keyword , '%') ) ORDER BY i.create_date DESC")
	public List<Map<String, Object>> search(@Param("keyword") String keyword);

}
