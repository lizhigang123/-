/** 
 * @(#)TestCacheController.java 1.0.0 2015年12月17日 下午4:29:05  
 *  
 * Copyright © 2015 善林金融.  All rights reserved.  
 */

package com.bojin.api.web.controller;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.ehcache.EhCacheCacheManager;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.bojin.api.common.response.ResponseVo;
import com.bojin.api.core.demo.entity.DemoUserEntity;
import com.bojin.api.core.demo.service.DemoUserService;

/**
 * 缓存测试用例
 * 
 * @author samson
 * @version $Revision:1.0.0, $Date: 2015年12月17日 下午4:29:05 $
 */
@RestController
@RequestMapping("/test/cache")
public class TestCacheController extends BaseEntityController<DemoUserEntity> {

	// 使用的时候注意一定使用name注入,如有需要可以手工操作缓存(不过一般不建议)
	@Resource(name = "ehCacheManager")
	private EhCacheCacheManager ehCacheCacheManager;

	// 使用的时候注意一定使用name注入,如有需要可以手工操作缓存(不过一般不建议)
	@Resource(name = "redisCacheManager")
	private RedisCacheManager redisCacheManager;

	@Autowired
	private DemoUserService userService;

	/**
	 * 缓存放在redis中(需要在相应service配置@CacheConfig并指定缓存)
	 * 
	 * @return
	 */
	@RequestMapping("/1")
	@ResponseBody
	public ResponseVo test_1() {
		DemoUserEntity userEntity = baseService.findOne("c4dd02ff-de79-41b6-a48a-b879b94d354d");
		baseService.findOne("53c6458d-909a-4ae0-a1f1-d756c00b85f3");

		userService.findOneWithEhcache("0957a698-23d5-402b-99be-7ca0ba8c497a");
		userService.findOneWithEhcache("0f6d91ff-7303-4261-8f49-c7f3b90c2383");
		userEntity.setUserName("samson");
		// baseService.save(userEntity);
		return success(redisCacheManager.getCache("session").get("c4dd02ff-de79-41b6-a48a-b879b94d354d").get());
	}
}
