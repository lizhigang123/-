/** 
 * @(#)BaseService.java 1.0.0 2015年12月12日 上午10:32:27  
 *  
 * Copyright © 2015 善林金融.  All rights reserved.  
 */

package com.bojin.api.core.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.transaction.annotation.Transactional;

import com.bojin.api.common.entity.AbstractEntity;
import com.bojin.api.common.entity.User;
import com.bojin.api.common.support.repository.BaseRepository;
import com.bojin.api.common.support.springweb.service.LoginUserContextHolder;

/**
 * 基于PO 的service基类
 * 
 * @author samson
 * @version $Revision:1.0.0, $Date: 2015年12月12日 上午10:32:27 $
 */
@CacheConfig(cacheManager = "redisCacheManager", cacheResolver = "redisCacheResolver", cacheNames = { "session" })
public abstract class BaseService<Entity extends AbstractEntity> extends AbstractService {

	@Autowired
	protected BaseRepository<Entity> baseRepository;

	/**
	 * 保存单个实体
	 *
	 * @param m
	 *            实体
	 * @return 返回保存的实体
	 */
	@Transactional
	@CacheEvict(key = "#m.id")
	public Entity save(Entity m) {
		return baseRepository.save(m);
	}

	@Transactional
	public Entity saveAndFlush(Entity m) {
		m = save(m);
		baseRepository.flush();
		return m;
	}

	/**
	 * 更新单个实体
	 *
	 * @param m
	 *            实体
	 * @return 返回更新的实体
	 */
	@CacheEvict(key = "#m.id")
	@Transactional
	public Entity update(Entity m) {
		return baseRepository.save(m);
	}

	/**
	 * 根据主键删除相应实体
	 *
	 * @param id
	 *            主键
	 */
	@CacheEvict(key = "#id")
	@Transactional
	public void delete(String id) {
		baseRepository.delete(id);
	}

	/**
	 * 删除实体
	 *
	 * @param m
	 *            实体
	 */
	@CacheEvict(key = "#m.id")
	@Transactional
	public void delete(Entity m) {
		baseRepository.delete(m);
	}

	/**
	 * 根据主键删除相应实体
	 *
	 * @param ids
	 *            实体
	 */
	// TODO 批量删除缓存
	@Transactional
	public void delete(String[] ids) {
		baseRepository.delete(ids);
	}

	/**
	 * 按照主键查询
	 *
	 * @param id
	 *            主键
	 * @return 返回id对应的实体
	 */
	@Cacheable(key = "#id")
	public Entity findOne(String id) {
		return baseRepository.findOne(id);
	}

	/**
	 * 实体是否存在
	 *
	 * @param id
	 *            主键
	 * @return 存在 返回true，否则false
	 */
	@Cacheable(key = "#id")
	public boolean exists(String id) {
		return baseRepository.exists(id);
	}

	/**
	 * 统计实体总数
	 *
	 * @return 实体总数
	 */
	public long count() {
		return baseRepository.count();
	}

	/**
	 * 查询所有实体
	 *
	 * @return
	 */
	public List<Entity> findAll() {
		return baseRepository.findAll();
	}

	/**
	 * 获取当前登录用户id
	 * 
	 * @return
	 */
	public String getLoginUserId() {
		return LoginUserContextHolder.getLoginUser().getId();
	}

	/**
	 * 获取当前登录用户
	 * 
	 * @return
	 */
	public User getLoingUser() {
		return LoginUserContextHolder.getLoginUser();
	}

}
