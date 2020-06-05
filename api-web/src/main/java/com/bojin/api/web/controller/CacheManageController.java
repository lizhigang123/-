/** 
 * @(#)CacheManageController.java 1.0.0 2015年12月23日 下午2:26:38  
 *  
 * Copyright © 2015 善林金融.  All rights reserved.  
 */

package com.bojin.api.web.controller;

import java.util.Collection;
import java.util.Iterator;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.bojin.api.common.response.ResponseCode;
import com.bojin.api.common.response.ResponseVo;
import com.bojin.api.common.validate.annotations.Rule;
import com.bojin.api.common.validate.annotations.Rules;
import com.bojin.api.common.validate.annotations.ValidRules;

/**
 * 缓存管理
 * 
 * @author samson
 * @version $Revision:1.0.0, $Date: 2015年12月23日 下午2:26:38 $
 */
@RestController
@RequestMapping("/cacheManage")
public class CacheManageController extends BaseController {

	@Resource(name = "redisCacheManager")
	private CacheManager redisCacheManager;

	@Resource(name = "ehCacheManager")
	private CacheManager ehCacheManager;

	/**
	 * 清理缓存
	 * 
	 * @param cache
	 * @param name
	 * @param key
	 * @return
	 */
	@RequestMapping("clear")
	@ValidRules({ @Rules(argsIndex = 0, value = { @Rule(required = true, name = "cache", requiredMessage = "缓存类型必须选择", inRange = { "ehcache", "redis" }, inRangeMessage = "缓存类型必须为ehcache或redis") }) })
	public ResponseVo clear(String cache, @RequestParam(required = false) String name, @RequestParam(required = false) String key) {
		CacheManager cacheManager = getCacheManager(cache);
		Cache cacheC = null;
		// 是否是清空所有缓存
		if (StringUtils.isNotBlank(name)) {
			cacheC = cacheManager.getCache(name);
		} else {
			Iterator<String> iterator = cacheManager.getCacheNames().iterator();
			String cacheName = null;
			while (iterator.hasNext()) {
				cacheName = iterator.next();
				cacheManager.getCache(cacheName).clear();

			}
			return success();
		}

		if (cacheC == null) {
			return fail(ResponseCode.PARAMETERS_VALIDATE_FAILURE, "不存在的缓存名");
		}
		// 是否是清空指定缓存块的所有缓存
		if (StringUtils.isNotBlank(key)) {
			cacheC.evict(key);
		} else {
			cacheC.clear();
		}
		return success();
	}

	@RequestMapping(value = "listCache", method = { RequestMethod.POST, RequestMethod.GET })
	@ValidRules({ @Rules(argsIndex = 0, value = { @Rule(required = true, name = "cache", requiredMessage = "缓存类型必须选择", inRange = { "ehcache", "redis" }, inRangeMessage = "缓存类型必须为ehcache或redis") }) })
	public ResponseVo listCache(String cache) {
		Collection<String> caches = getCacheManager(cache).getCacheNames();
		return success(caches);
	}

	private CacheManager getCacheManager(String cache) {
		CacheManager cacheManager = null;
		switch (cache) {
		case "ehcache":
			cacheManager = ehCacheManager;
			break;
		case "redis":
			cacheManager = redisCacheManager;
			break;
		default:
			break;
		}
		return cacheManager;
	}
}
