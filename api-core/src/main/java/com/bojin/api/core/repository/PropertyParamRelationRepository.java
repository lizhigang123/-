package com.bojin.api.core.repository;

 
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.bojin.api.common.entity.PropertyParamRelation;
import com.bojin.api.common.support.repository.BaseRepository;
import com.bojin.api.common.support.repository.page.QueryExtend;

/**
 * @author jeff
 * @date 2016/4/1 13:02
 */
public interface PropertyParamRelationRepository extends BaseRepository<PropertyParamRelation> {
	@QueryExtend
	@Query(nativeQuery = true, value = "select ar.id  from api_t_property_param_relation ar where ar.param_id = :paramId")
 	public String getIdByParamId(@Param("paramId")String paramId);
}
