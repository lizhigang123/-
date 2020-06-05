/**
 * Copyright (c) 2005-2012 https://github.com/zhangkaitao
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 */
package com.bojin.api.common.support.repository;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.support.JpaEntityInformation;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import com.bojin.api.common.entity.AbstractEntity;

/**
 * <p>
 * 抽象基础Custom Repository 实现
 * </p>
 * <p>
 * User: Zhang Kaitao
 * <p>
 * Date: 13-1-15 下午7:33
 * <p>
 * Version: 1.0
 */
public class SimpleBaseRepository<E extends AbstractEntity> extends SimpleJpaRepository<E, String> implements BaseRepository<E> {

	public static final String UPDATE_RECORDSTATUS_QUERY_STRING = "update %s x set x.recordStatus=':recordStatus' where x in (?2)";
	public static final String DELETE_ALL_QUERY_STRING = "delete from %s x where x in (?1)";
	public static final String FIND_QUERY_STRING = "from %s x where 1=1 ";
	public static final String COUNT_QUERY_STRING = "select count(1) from %s x where 1=1 ";

	protected final EntityManager em;
	protected final JpaEntityInformation<E, String> entityInformation;

	protected final RepositoryHelper repositoryHelper;

	protected JdbcTemplate jdbcTemplate;
	protected NamedParameterJdbcTemplate namedParameterJdbcTemplate;

	private Class<E> entityClass;
	private String entityName;
	private String idName;

	/**
	 * 查询所有的QL
	 */
	private String findAllQL;
	/**
	 * 统计QL
	 */
	private String countAllQL;

	public SimpleBaseRepository(JpaEntityInformation<E, String> entityInformation, EntityManager entityManager, JdbcTemplate jdbcTemplate, NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
		super(entityInformation, entityManager);
		this.jdbcTemplate = jdbcTemplate;
		this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;

		this.entityInformation = entityInformation;
		this.entityClass = this.entityInformation.getJavaType();
		this.entityName = this.entityInformation.getEntityName();
		this.idName = this.entityInformation.getIdAttributeNames().iterator().next();
		this.em = entityManager;

		repositoryHelper = new RepositoryHelper(entityClass, entityManager);

		findAllQL = String.format(FIND_QUERY_STRING, entityName);
		countAllQL = String.format(COUNT_QUERY_STRING, entityName);
	}

	/**
	 * 设置查询所有的ql
	 *
	 * @param findAllQL
	 */
	public void setFindAllQL(String findAllQL) {
		this.findAllQL = findAllQL;
	}

	/**
	 * 设置统计的ql
	 *
	 * @param countAllQL
	 */
	public void setCountAllQL(String countAllQL) {
		this.countAllQL = countAllQL;
	}

	/**
	 * 根据主键删除相应实体
	 *
	 * @param id
	 *            主键
	 */
	@Override
	public void delete(final String id) {
		E m = findOne(id);
		delete(m);
	}

	/**
	 * 删除实体
	 *
	 * @param m
	 *            实体
	 */
	@Override
	public void delete(final E m) {
		if (m == null) {
			return;
		}
		super.delete(m);
	}

	/**
	 * 根据主键删除相应实体
	 *
	 * @param ids
	 *            实体
	 */

	@Override
	public void delete(final String[] ids) {
		if (ArrayUtils.isEmpty(ids)) {
			return;
		}
		List<E> models = new ArrayList<E>();
		for (String id : ids) {
			E model = null;
			try {
				model = entityClass.newInstance();
			} catch (Exception e) {
				throw new RuntimeException("batch delete " + entityClass + " error", e);
			}
			try {
				BeanUtils.setProperty(model, idName, id);
			} catch (Exception e) {
				throw new RuntimeException("batch delete " + entityClass + " error, can not set id", e);
			}
			models.add(model);
		}
		deleteInBatch(models);
	}

	/**
	 * 按照主键查询
	 *
	 * @param id
	 *            主键
	 * @return 返回id对应的实体
	 */

	@Override
	public E findOne(String id) {
		return super.findOne(id);
	}

	@Override
	public List<E> findAll() {
		return repositoryHelper.findAll(findAllQL);
	}

	@Override
	public List<E> findAll(final Sort sort) {
		return repositoryHelper.findAll(findAllQL, sort);
	}

	@Override
	public Page<E> findAll(final Pageable pageable) {
		return new PageImpl<E>(repositoryHelper.<E> findAll(findAllQL, pageable), pageable, repositoryHelper.count(countAllQL));
	}

	@Override
	public long count() {
		return repositoryHelper.count(countAllQL);
	}

	@Override
	public boolean exists(String id) {
		return findOne(id) != null;
	}

	@Override
	public int updateRecordStatus(String id, String recordStatus) {
		return repositoryHelper.batchUpdate(UPDATE_RECORDSTATUS_QUERY_STRING.replace(":recordStatus", recordStatus), new Object[] { id });
	}

	@Override
	public int batchUpdateRecordStatus(String[] ids, String recordStatus) {
		return repositoryHelper.batchUpdate(UPDATE_RECORDSTATUS_QUERY_STRING.replace(":recordStatus", recordStatus), ids);

	}
}
