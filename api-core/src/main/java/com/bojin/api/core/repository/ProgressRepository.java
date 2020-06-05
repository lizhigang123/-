package com.bojin.api.core.repository;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.bojin.api.common.constants.enums.ProgressStatusEnum;
import com.bojin.api.common.entity.Progress;
import com.bojin.api.common.support.repository.BaseRepository;
import com.bojin.api.common.support.repository.page.PageRequest;
import com.bojin.api.common.support.repository.page.PageResponse;
import com.bojin.api.common.support.repository.page.QueryExtend;
import com.bojin.api.core.repository.custom.ProgressRepositoryCust;

/**
 * @author samson
 * @date 2016/3/9 10:23
 */
public interface ProgressRepository extends BaseRepository<Progress>, ProgressRepositoryCust {

	@QueryExtend
	@Query(nativeQuery = true, value = "SELECT P .*, i. NAME,i.branch_id, i.url, i.project_name,i.project_id, i.branch_name, i.category_name,i.interface_status,i.id AS interface_interface_id,u.name AS developer_name,i.i_interface_id FROM ( SELECT _i.*, _pp. NAME AS project_name, _pp. ID AS project_id, _p. NAME AS branch_name, _c. NAME AS category_name, _i. ID AS i_interface_id,_i.status as interface_status FROM api_t_interface _i LEFT JOIN api_t_category _c ON _i.category_id = _c. ID LEFT JOIN api_t_project _p ON _i.branch_id = _p. ID LEFT JOIN api_t_project _pp ON _c.project_id = _pp. ID ) i LEFT JOIN api_t_progress P ON i. ID = P .interface_id LEFT JOIN api_t_user u ON u.id = P.developer WHERE 1=1 AND ?( i.project_id = :projectId)? AND ?(I. NAME LIKE :name)? AND ?(I.url LIKE :url)? AND ?(P.developer = :userId)? AND ?(to_char(P.start_date,'yyyy-mm-dd') >= :startDateS)? AND ?(to_char(P.start_date,'yyyy-mm-dd') <= :startDateE)? AND ?(to_char(P.end_date,'yyyy-mm-dd') >= :endDateS)? AND ?(to_char(P.end_date,'yyyy-mm-dd') <= :endDateE)? AND ?(P.status in ( :status ))? ORDER BY i.create_date DESC")
	public PageResponse<Map<String, Object>> searchByProjectId(PageRequest<Map<String, Object>> pageRequest);

	@QueryExtend
	@Query(nativeQuery = true, value = "SELECT P .*, i. NAME, i.branch_id,i.url, i.project_name,i.project_id, i.branch_name, i.category_name,i.interface_status,i.id AS interface_interface_id,u.name AS developer_name,i.i_interface_id FROM ( SELECT _i.*, _pp. NAME AS project_name, _pp. ID AS project_id, _p. NAME AS branch_name, _c. NAME AS category_name, _i. ID AS i_interface_id,_i.status as interface_status FROM api_t_interface _i LEFT JOIN api_t_category _c ON _i.category_id = _c. ID LEFT JOIN api_t_project _p ON _i.branch_id = _p. ID LEFT JOIN api_t_project _pp ON _c.project_id = _pp. ID ) i LEFT JOIN api_t_progress P ON i. ID = P .interface_id LEFT JOIN api_t_user u ON u.id = P.developer WHERE 1=1 AND ?(I. NAME LIKE :name)? AND ?(I.url LIKE :url)? AND I.category_id = :categoryId AND ?(P.developer = :userId)? AND ?(to_char(P.start_date,'yyyy-mm-dd') >= :startDateS)? AND ?(to_char(P.start_date,'yyyy-mm-dd') <= :startDateE)? AND ?(to_char(P.end_date,'yyyy-mm-dd') >= :endDateS)? AND ?(to_char(P.end_date,'yyyy-mm-dd') <= :endDateE)? AND ?(P.status in ( :status ))? ORDER BY i.create_date DESC")
	public PageResponse<Map<String, Object>> searchByCategoryId(PageRequest<Map<String, Object>> pageRequest);

	@QueryExtend
	@Query(nativeQuery = true, value = "SELECT P .*, i. NAME,i.branch_id, i.url, i.project_name,i.project_id, i.branch_name, i.category_name,i.interface_status,i.id AS interface_interface_id,u.name AS developer_name,i.i_interface_id FROM ( SELECT _i.*, _pp. NAME AS project_name, _pp. ID AS project_id, _p. NAME AS branch_name, _c. NAME AS category_name, _i. ID AS i_interface_id,_i.status as interface_status FROM api_t_interface _i LEFT JOIN api_t_category _c ON _i.category_id = _c. ID LEFT JOIN api_t_project _p ON _i.branch_id = _p. ID LEFT JOIN api_t_project _pp ON _c.project_id = _pp. ID ) i LEFT JOIN api_t_progress P ON i. ID = P .interface_id LEFT JOIN api_t_user u ON u.id = P.developer WHERE ?( i.branch_id IN ( SELECT p2. ID FROM api_t_project p1, api_t_project p2 WHERE p1. ID = p2.parent_id AND p1. ID = :projectId ))? AND ?(I. NAME LIKE :name)? AND ?(I.url LIKE :url)? AND I.branch_id = :branchId AND ?(P.developer = :userId)? AND ?(to_char(P.start_date,'yyyy-mm-dd') >= :startDateS)? AND ?(to_char(P.start_date,'yyyy-mm-dd') <= :startDateE)? AND ?(to_char(P.end_date,'yyyy-mm-dd') >= :endDateS)? AND ?(to_char(P.end_date,'yyyy-mm-dd') <= :endDateE)? AND ?(P.status in ( :status ))? ORDER BY i.create_date DESC")
	public PageResponse<Map<String, Object>> searchByBranchId(PageRequest<Map<String, Object>> pageRequest);

	@QueryExtend
	@Query(nativeQuery = true, value = "SELECT i.*,p.id AS project_id FROM api_t_interface i,api_t_category c,api_t_project p WHERE i.category_id = c.id AND c.project_id = p.id AND i.id = :id")
	public Map<String, Object> findOneWithExtraInfo(@Param("id") String id);

	public List<Progress> findByStartDateBeforeAndStatus(@Param("startDate") Date startDate, @Param("status") ProgressStatusEnum status);

	public List<Progress> findByEndDateBeforeAndStatus(@Param("endDate") Date endDate, @Param("status") ProgressStatusEnum status);
}
