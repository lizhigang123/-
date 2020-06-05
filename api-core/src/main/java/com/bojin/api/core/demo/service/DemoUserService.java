/** 
 * @(#)UserService.java 1.0.0 2015年12月12日 下午6:08:17  
 *  
 * Copyright © 2015 善林金融.  All rights reserved.  
 */

package com.bojin.api.core.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.bojin.api.common.response.ResponseCode;
import com.bojin.api.common.response.ResponseVo;
import com.bojin.api.core.demo.entity.DemoUserEntity;
import com.bojin.api.core.demo.repository.DemoUserRepository;
import com.bojin.api.core.service.BaseService;

/**
 * 
 * 
 * @author samson
 * @version $Revision:1.0.0, $Date: 2015年12月12日 下午6:08:17 $
 */
@Service
@Transactional(readOnly = true, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
@CacheConfig(cacheManager = "ehCacheManager", cacheResolver = "ehCacheResolver", cacheNames = { "test" })
// 可自主选择缓存类型和缓存块
// @CacheConfig(cacheManager = "redisCacheManager", cacheResolver =
// "redisCacheResolver", cacheNames = { "session" })
public class DemoUserService extends BaseService<DemoUserEntity> {

	@Autowired
	DemoUserRepository userRePository;

	@Cacheable(key = "#id")
	@Transactional(readOnly = false)
	public DemoUserEntity findOneWithEhcache(String id) {
		return baseRepository.findOne(id);
	}

	public DemoUserEntity test_tsam_1() {
		return new DemoUserEntity();
	}

	public ResponseVo test_tsam_2() {
		return new ResponseVo(ResponseCode.SUCCESS);
	}

}
