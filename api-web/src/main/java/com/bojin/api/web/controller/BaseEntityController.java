/**
 * Copyright (c) 2005-2012 https://github.com/zhangkaitao
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 */
package com.bojin.api.web.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.bojin.api.common.entity.BaseEntity;
import com.bojin.api.common.response.ResponseVo;
import com.bojin.api.common.support.springweb.method.CurrentUserId;
import com.bojin.api.common.util.ReflectUtils;
import com.bojin.api.core.service.BaseService;

/**
 * 基础的基于实体的 控制器,主要是基于{@code BaseController} 上新增了对实体的映射的增删改查公共方法
 * <p>
 * User: Zhang Kaitao
 * <p>
 * Date: 13-2-23 下午1:20
 * <p>
 * Version: 1.0
 */
public abstract class BaseEntityController<Entity extends BaseEntity> extends BaseController {
	/**
	 * 实体类型
	 */
	protected final Class<Entity> entityClass;

	/**
	 * 设置基础service
	 */
	@Autowired
	protected BaseService<Entity> baseService;

	public BaseEntityController() {
		this.entityClass = ReflectUtils.findParameterizedType(getClass(), 0);
		setViewPrefix(defaultViewPrefix());
	}

	/**
	 * 创建实体
	 * 
	 * @return
	 */
	protected Entity newModel() {
		try {
			return entityClass.newInstance();
		} catch (Exception e) {
			throw new IllegalStateException("can not instantiated model : " + this.entityClass, e);
		}
	}

	/**
	 * 查询实体
	 * 
	 * @param model
	 * @param m
	 * @return
	 */
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public ResponseVo get(@PathVariable("id") String id) {
		return success(baseService.findOne(id));
	}

	/**
	 * 新增实体
	 * 
	 * @param entity
	 * @param user
	 *            当前操作用户
	 * @return
	 */
	@RequestMapping(value = "/", method = RequestMethod.POST)
	public ResponseVo create(@Valid Entity entity, @CurrentUserId String operatorId) {
		baseService.save(entity);
		return success();
	}

	/**
	 * 更新实体
	 * 
	 * @param model
	 * @param m
	 * @param result
	 * @param redirectAttributes
	 * @return
	 */
	@RequestMapping(value = "/", method = RequestMethod.PUT)
	public ResponseVo update(@Valid Entity entity, @CurrentUserId String userId) {
		baseService.update(entity);
		return success();
	}

	/**
	 * 删除单个实体
	 * 
	 * @param id
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public ResponseVo delete(@PathVariable("id") String id) {
		baseService.delete(id);
		return success();
	}

	/**
	 * 批量删除实体
	 * 
	 * @param ids
	 * @param redirectAttributes
	 * @return
	 */
	@RequestMapping(value = "/deleteBatch", method = RequestMethod.POST)
	public ResponseVo deleteBatch(String[] ids) {
		baseService.delete(ids);
		return success();
	}
}
