/**
 * Copyright (c) 2005-2012 https://github.com/zhangkaitao
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 */
package com.bojin.api.common.support.repository;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.support.JpaEntityInformation;
import org.springframework.data.jpa.repository.support.JpaEntityInformationSupport;
import org.springframework.util.Assert;

/**
 * 仓库辅助类
 * <p>
 * User: Zhang Kaitao
 * <p>
 * Date: 13-4-14 下午5:28
 * <p>
 * Version: 1.0
 */
public class RepositoryHelper {

	private EntityManager entityManager;

	@SuppressWarnings("unused")
	private Class<?> entityClass;

	public RepositoryHelper(Class<?> entityClass, EntityManager entityManager) {
		this.entityClass = entityClass;
		this.entityManager = entityManager;
	}

	public EntityManager getEntityManager() {
		Assert.notNull(entityManager, "entityManager must null, please see " + "[com.sishuok.es.common.repository.RepositoryHelper#setEntityManagerFactory]");

		return entityManager;
	}

	public void flush() {
		getEntityManager().flush();
	}

	public void clear() {
		flush();
		getEntityManager().clear();
	}

	/**
	 * @param ql
	 * @param params
	 * @param <M>
	 * @return
	 * @see RepositoryHelper#findAll(String,
	 *      org.springframework.data.domain.Pageable, Object...)
	 */
	public <M> List<M> findAll(final String ql, final Object... params) {

		// 此处必须 (Pageable) null 否则默认有调用自己了 可变参列表
		return findAll(ql, (Pageable) null, params);

	}

	/**
	 * <p>
	 * 根据ql和按照索引顺序的params执行ql，pageable存储分页信息 null表示不分页<br/>
	 * 具体使用请参考测试用例：{@see
	 * com.sishuok.es.common.repository.UserRepository2ImplIT#testFindAll()}
	 *
	 * @param ql
	 * @param pageable
	 *            null表示不分页
	 * @param params
	 * @param <M>
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public <M> List<M> findAll(final String ql, final Pageable pageable, final Object... params) {

		Query query = getEntityManager().createQuery(ql + prepareOrder(pageable != null ? pageable.getSort() : null));
		setParameters(query, params);
		if (pageable != null) {
			query.setFirstResult(pageable.getOffset());
			query.setMaxResults(pageable.getPageSize());
		}

		return query.getResultList();
	}

	/**
	 * <p>
	 * 根据ql和按照索引顺序的params执行ql，sort存储排序信息 null表示不排序<br/>
	 * 具体使用请参考测试用例：{@see
	 * com.sishuok.es.common.repository.UserRepository2ImplIT#testFindAll()}
	 *
	 * @param ql
	 * @param sort
	 *            null表示不排序
	 * @param params
	 * @param <M>
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public <M> List<M> findAll(final String ql, final Sort sort, final Object... params) {

		Query query = getEntityManager().createQuery(ql + prepareOrder(sort));
		setParameters(query, params);

		return query.getResultList();
	}

	/**
	 * <p>
	 * 根据ql和按照索引顺序的params查询一个实体<br/>
	 * 具体使用请参考测试用例：{@see
	 * com.sishuok.es.common.repository.UserRepository2ImplIT#testFindOne()}
	 *
	 * @param ql
	 * @param params
	 * @param <M>
	 * @return
	 */
	public <M> M findOne(final String ql, final Object... params) {

		List<M> list = findAll(ql, new PageRequest(0, 1), params);

		if (list.size() > 0) {
			return list.get(0);
		}
		return null;
	}

	/**
	 * <p>
	 * 根据ql和按照索引顺序的params执行ql统计<br/>
	 * 具体使用请参考测试用例：com.sishuok.es.common.repository.UserRepository2ImplIT#
	 * testCountAll()
	 *
	 * @param ql
	 * @param params
	 * @return
	 */
	public long count(final String ql, final Object... params) {

		Query query = entityManager.createQuery(ql);
		setParameters(query, params);

		return (Long) query.getSingleResult();
	}

	/**
	 * <p>
	 * 执行批处理语句.如 之间insert, update, delete 等.<br/>
	 * 具体使用请参考测试用例：{@see
	 * com.sishuok.es.common.repository.UserRepository2ImplIT#testBatchUpdate()}
	 *
	 * @param ql
	 * @param params
	 * @return
	 */
	public int batchUpdate(final String ql, final Object[] params) {

		Query query = getEntityManager().createQuery(ql);
		setParameters(query, params);

		return query.executeUpdate();
	}

	/**
	 * 按顺序设置Query参数
	 *
	 * @param query
	 * @param params
	 */
	public void setParameters(Query query, Object[] params) {
		if (params != null) {
			for (int i = 0; i < params.length; i++) {
				query.setParameter(i + 1, params[i]);
			}
		}
	}

	/**
	 * 拼排序
	 *
	 * @param sort
	 * @return
	 */
	public String prepareOrder(Sort sort) {
		if (sort == null || !sort.iterator().hasNext()) {
			return "";
		}
		StringBuilder orderBy = new StringBuilder("");
		orderBy.append(" order by ");
		orderBy.append(sort.toString().replace(":", " "));
		return orderBy.toString();
	}

	public <T> JpaEntityInformation<T, ?> getMetadata(Class<T> entityClass) {
		return JpaEntityInformationSupport.getMetadata(entityClass, entityManager);
	}

	public String getEntityName(Class<?> entityClass) {
		return getMetadata(entityClass).getEntityName();
	}

}
