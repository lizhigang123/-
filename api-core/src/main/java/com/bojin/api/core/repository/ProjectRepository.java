package com.bojin.api.core.repository;

import java.util.List;
import java.util.Map;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.bojin.api.common.entity.Project;
import com.bojin.api.common.support.repository.BaseRepository;
import com.bojin.api.common.support.repository.page.PageMapRequest;
import com.bojin.api.common.support.repository.page.PageResponse;
import com.bojin.api.common.support.repository.page.QueryExtend;
import com.bojin.api.core.repository.custom.ProjectRepositoryCust;

/**
 * @author samson
 * @date 2016/3/9 10:13
 */
public interface ProjectRepository extends BaseRepository<Project>, ProjectRepositoryCust {

	@QueryExtend
	@Query(nativeQuery = true, value = "select p.*,u.name AS leader_name from api_t_project p,api_t_user u where p.leader = u.id AND (p.parent_id is null OR p.parent_id = '') order by p.last_update_date desc")
	public PageResponse<Map<String, Object>> search(PageMapRequest pageRequest);

	@QueryExtend
	@Query(nativeQuery = true, value = "select p.*,u.name AS leader_name from api_t_project p,api_t_user u where p.leader = u.id AND p.parent_id = :parentId order by p.last_update_date desc")
	public PageResponse<Map<String, Object>> subProjectSearch(PageMapRequest pageRequest);

	
	/**
	 * 根据parentId查询项目
	 * @param projectId
	 * @return
	 */
	public List<Project> findByParentId(@Param("parentId")String parentId);
	
	/**
	 * 查找项目
	 */
	@Query(nativeQuery = true, value = "SELECT * FROM api_t_project P WHERE P.parent_id is null OR P.parent_id = ''")
	public List<Project> findAllProjects();

}
