package com.bojin.api.core.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.bojin.api.common.entity.Resource;
import com.bojin.api.common.support.repository.BaseRepository;
import com.bojin.api.common.support.repository.page.QueryExtend;

/**
 * @author LiYing
 * @date 2016/4/12 10:22
 */
public interface ResourceRepository extends BaseRepository<Resource> {

	@QueryExtend
	@Query(nativeQuery = true, value = "select s.id, s.parent_id, s.name, s.url, s.description from api_t_user_role_relation u, api_t_role_resource_relation r, api_t_resource s where u.role_id = r.role_id and r.resource_id = s.id and u.user_id = :userId")
	List<Resource> findResourceByUserId(@Param("userId") String userId);

	@QueryExtend
	@Query(nativeQuery = true, value = "select s.id, s.parent_id, s.name, s.url, s.description from api_t_role_resource_relation r, api_t_resource s where r.resource_id = s.id and r.role_id = :roleId")
	List<Resource> findResourceByRoleId(@Param("roleId") String roleId);

	public Resource findTopByParentIdOrderByOrderedDesc(@Param("parentId") String parentId);

	@QueryExtend
	@Query(nativeQuery = true, value = "select count(*) from api_t_resource r  where r.parent_id = :parentId")
	public Integer findChildNoByParentId(@Param("parentId") String parentId);

	List<Resource> findByParentIdIsNullOrderByOrderedAsc();
	List<Resource> findAllByOrderByOrderedAsc();
	List<Resource> findByParentIdOrderByOrderedAsc(String parentId);

}
