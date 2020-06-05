package com.bojin.api.common.support.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

import com.bojin.api.common.entity.AbstractEntity;

/**
 * 数据仓库基类
 *
 * @author samson
 *
 * @param <Entity>
 */
@NoRepositoryBean
public interface BaseRepository<E extends AbstractEntity> extends JpaRepository<E, String> {

	/**
	 * 根据主键删除
	 *
	 * @param ids
	 */
	public void delete(String[] ids);

	/**
	 * 更新记录状态
	 *
	 * @param id
	 *            记录id
	 * @param recordStatus
	 *            记录状态
	 */
	public int updateRecordStatus(String id, String recordStatus);

	/**
	 * 批量更新记录状态
	 *
	 * @param id
	 *            记录id
	 * @param recordStatus
	 *            记录状态
	 */
	public int batchUpdateRecordStatus(String[] ids, String recordStatus);
}
