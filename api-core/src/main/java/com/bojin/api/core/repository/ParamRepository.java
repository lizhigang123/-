package com.bojin.api.core.repository;

import java.util.List;
import java.util.Map;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.bojin.api.common.entity.ParamEntity;
import com.bojin.api.common.support.repository.BaseRepository;
import com.bojin.api.common.support.repository.page.PageMapRequest;
import com.bojin.api.common.support.repository.page.PageResponse;
import com.bojin.api.common.support.repository.page.QueryExtend;
import com.bojin.api.core.repository.custom.ParamRepositoryCust;

/**
 * @author samson
 * @date 2016/3/9 10:25
 */
public interface ParamRepository extends BaseRepository<ParamEntity>, ParamRepositoryCust {

	public List<ParamEntity> findByInterfaceIdAndTypeOrderByOrderedAsc(String interfaceId, String type);
	
	
	@QueryExtend
	@Query(nativeQuery = true, value = "SELECT * FROM api_t_param where interface_id = :interfaceId and type = :type and (parent_id is null or parent_id = '') order by ordered asc ")
	public List<ParamEntity> findByInterfaceIdAndTypeAndParentIdIsNullOrderByOrderedAsc(@Param("interfaceId")String interfaceId, @Param("type")String type);
	
	public List<ParamEntity> findByInterfaceIdAndTypeAndParentIdOrderByOrderedAsc(String interfaceId, String type,String parentId);
	

	public List<ParamEntity> findByInterfaceIdOrderByOrderedAsc(String interfaceId);
	@QueryExtend
	@Query(nativeQuery = true, value = "SELECT p.ordered FROM api_t_interface i ,api_t_param p WHERE i.id = p.interface_id AND i.id = :id AND p.parent_id = :parentId AND p.type = :type ORDER BY p.ordered DESC LIMIT 1")
	public Integer findLastedOrderByInterfaceIdAndParentIdAndType(@Param("id") String interfaceId, @Param("parentId") String parentId, @Param("type") String type);
	@QueryExtend
	@Query(nativeQuery = true, value = "SELECT p.ordered FROM api_t_param p WHERE  p.parent_id = :parentId AND p.type = :type ORDER BY p.ordered DESC LIMIT 1")
	public Integer findLastedOrderByParentIdAndType(@Param("parentId") String parentId, @Param("type") String type);
 	@Query(nativeQuery = true, value = "select aa.* from api_t_param aa,api_t_property_param_relation bb where aa.id = bb.param_id and bb.project_properties_id = :propertiestId order by aa.ordered asc")
	public List<ParamEntity>  getProjectParamTemplate(@Param("propertiestId") String propertiestId);
 	
 	@QueryExtend
	@Query(nativeQuery = true, value = "select i.name as interface_name,p.id,p.name,p.name_desc,p.data_type from api_t_param p,api_t_interface i where i.id = p.interface_id and (p.name like CONCAT('%', :name , '%') or p.name_desc like CONCAT('%', :name ,'%')) order by p.last_update_date desc")
	public PageResponse<Map<String, Object>> getAllParamByName(PageMapRequest pageRequest);
 	
 	@QueryExtend
	@Query(nativeQuery = true, value = "select p.id,concat(i.name,'-',p.name,'-',p.name_desc,'-',p.data_type) as name from api_t_param p,api_t_interface i where p.interface_id = i.id and (p.name like CONCAT('%', :name ,'%') or p.name_desc like CONCAT('%', :name ,'%'))")
	public PageResponse<Map<String, Object>> matchParams(PageMapRequest pageRequest);
}