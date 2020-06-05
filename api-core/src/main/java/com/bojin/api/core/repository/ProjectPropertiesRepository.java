package com.bojin.api.core.repository;

import java.util.List;
import java.util.Map;

import org.springframework.data.jpa.repository.Query;

import com.bojin.api.common.entity.ProjectProperties;
import com.bojin.api.common.support.repository.BaseRepository;
import com.bojin.api.common.support.repository.page.PageMapRequest;
import com.bojin.api.common.support.repository.page.PageResponse;
import com.bojin.api.common.support.repository.page.QueryExtend;

/**
 * @author samson
 * @date 2016/3/22 16:33
 */
public interface ProjectPropertiesRepository extends BaseRepository<ProjectProperties> {

	@QueryExtend
	@Query(nativeQuery = true, value = "SELECT * FROM api_t_project_properties pp WHERE pp.project_id=:projectId and pp.type in (:type)")
	public PageResponse<ProjectProperties> search(PageMapRequest pageMapRequest);

	public List<ProjectProperties> findAllByProjectIdAndTypeAndVisible(String projectId, String type, String visible);

	@QueryExtend
	@Query(nativeQuery = true, value = "select * from api_t_project_properties pp WHERE pp.project_id=:projectId and pp.type=:type order by pp.create_date desc")
	public PageResponse<Map<String, Object>> getParamsTemplates(PageMapRequest pageRequest);
}
