package com.bojin.api.core.repository;


import java.util.List;
import java.util.Map;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.bojin.api.common.entity.Category;
import com.bojin.api.common.support.repository.BaseRepository;
import com.bojin.api.common.support.repository.page.PageMapRequest;
import com.bojin.api.common.support.repository.page.PageResponse;
import com.bojin.api.common.support.repository.page.QueryExtend;
import com.bojin.api.core.repository.custom.CategoryRepositoryCust;

/**
 * @author samson
 * @date 2016/3/9 10:22
 */
public interface CategoryRepository extends BaseRepository<Category>, CategoryRepositoryCust {

	@QueryExtend
	@Query(nativeQuery = true, value = "select c.*,p.name AS project_name from api_t_category c,api_t_project p where ?(c.project_id = :projectParentId)? AND c.project_id=p.id order by c.sort desc,c.create_date desc")
	public PageResponse<Map<String, Object>> search(PageMapRequest pageRequest);

	/**
	 * 根据项目Id查询所有类目
	 * 
	 * @param projectId
	 * @return
	 */
	public List<Category> findByProjectIdOrderBySortDesc(@Param("projectId") String projectId);
	/**
	 * <dl>
	 * 条件为空过滤
	 * <dd>1.返回Bean集合</dd>
	 * <dd>1.需要校验不为空才加入的条件的请用?( )?包裹住</dd>
	 * </dl>
	 * 
	 * @param projectId
	 * @param visible
	 * @return
	 */
	@QueryExtend
	@Query(nativeQuery = true, value = "select * from api_t_category a where ?(a.project_id = :projectId)? and ?(a.visible = :visible)?")
	public List<Category> findByProjectIdAndVisible(@Param("projectId") String projectId, @Param("visible") String visible);
}
