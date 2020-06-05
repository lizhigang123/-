/** 
 * @(#)RedisConfi.java 1.0.0 2015年12月17日 下午4:31:22  
 *  
 * Copyright © 2015 善林金融.  All rights reserved.  
 */

package com.bojin.api.core;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.ArrayUtils;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.ehcache.EhCacheCacheManager;
import org.springframework.cache.ehcache.EhCacheManagerFactoryBean;
import org.springframework.cache.interceptor.CacheResolver;
import org.springframework.cache.interceptor.SimpleCacheResolver;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

/**
 * <p>
 * <strong>缓存定义</strong>
 * </p>
 * <p>
 * 注解缓存使用可以参考:http://jinnianshilongnian.iteye.com/blog/2001040/
 * </p>
 * 
 * 
 * @author samson
 * @version $Revision:1.0.0, $Date: 2015年12月17日 下午4:31:22 $
 */
@Configuration
@EnableCaching(proxyTargetClass = true)
public class CacheConfig {

	/**************************************************** redis缓存 ****************************************************************/
	@Bean(name = { "redisCacheManager" })
	@Primary
	public CacheManager redisCacheManager(@SuppressWarnings("rawtypes") RedisTemplate redisTemplate, RedisCacheConfig redisCacheConfig) {
		RedisCacheManager redisCacheManager = new RedisCacheManager(redisTemplate, Arrays.asList(redisCacheConfig.getNames()));
		redisCacheManager.setExpires(redisCacheConfig.getMap());// 设置缓存名称集合
		redisCacheManager.setDefaultExpiration(redisCacheConfig.getDefaultExpireTime());// 设置每个缓存块对应的缓存时间
		return redisCacheManager;
	}

	@Bean(name = { "redisCacheResolver" })
	CacheResolver getCacheResolver(RedisCacheManager redisCacheManager) {
		return new SimpleCacheResolver(redisCacheManager);
	}

	@Bean
	public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory factory) {
		final RedisTemplate<String, Object> template = new RedisTemplate<String, Object>();
		StringRedisSerializer stringRedisSerializer = new StringRedisSerializer();
		GenericJackson2JsonRedisSerializer genericJackson2JsonRedisSerializer = new GenericJackson2JsonRedisSerializer();
		template.setConnectionFactory(factory);
		template.setKeySerializer(stringRedisSerializer);
		template.setHashKeySerializer(stringRedisSerializer);
		template.setValueSerializer(genericJackson2JsonRedisSerializer);
		template.setHashValueSerializer(genericJackson2JsonRedisSerializer);
		template.afterPropertiesSet();
		return template;
	}

	/**************************************************** ehcache缓存 ****************************************************************/
	@Bean(name = { "ehCacheManager" })
	public CacheManager ehCacheManager(EhCacheManagerFactoryBean ehCacheManagerFactoryBean) {
		return new EhCacheCacheManager(ehCacheManagerFactoryBean.getObject());
	}

	@Bean
	public EhCacheManagerFactoryBean ehCacheManagerFactoryBean() {
		EhCacheManagerFactoryBean ehCacheManagerFactoryBean = new EhCacheManagerFactoryBean();
		ehCacheManagerFactoryBean.setConfigLocation(new ClassPathResource("ehcache.xml"));
		ehCacheManagerFactoryBean.setCacheManagerName("mp");
		ehCacheManagerFactoryBean.setShared(true);
		return ehCacheManagerFactoryBean;
	}

	@Bean(name = { "ehCacheResolver" })
	public CacheResolver ehCacheResolver(EhCacheCacheManager ehCacheCacheManager) {
		return new SimpleCacheResolver(ehCacheCacheManager);
	}

	/**
	 * 关于redis 的详细配置扩展
	 * 
	 * @author samson
	 *
	 */
	@Configuration
	@ConfigurationProperties(prefix = "spring.redis.cache")
	protected static class RedisCacheConfig implements InitializingBean {

		/**
		 * 默认过期时间
		 */
		long defaultExpireTime;
		/**
		 * 缓存块名称
		 */
		String[] names;
		/**
		 * 每个缓存块对应的过期时间
		 */
		long[] expires;

		/**
		 * 缓存块和过期时间的映射
		 */
		private Map<String, Long> map = new HashMap<String, Long>();

		public long getDefaultExpireTime() {
			return defaultExpireTime;
		}

		public void setDefaultExpireTime(long defaultExpireTime) {
			this.defaultExpireTime = defaultExpireTime;
		}

		public String[] getNames() {
			return names;
		}

		public void setNames(String[] names) {
			this.names = names;
		}

		public long[] getExpires() {
			return expires;
		}

		public void setExpires(long[] expires) {
			this.expires = expires;
		}

		public Map<String, Long> getMap() {
			return map;
		}

		@Override
		public void afterPropertiesSet() throws Exception {
			names = names == null ? ArrayUtils.EMPTY_STRING_ARRAY : names;
			expires = expires == null ? ArrayUtils.EMPTY_LONG_ARRAY : expires;
			if (names.length != expires.length) {
				throw new Exception(String.format("names%s length ne expires%s length", Arrays.toString(names), Arrays.toString(expires)));
			}
			for (int i = 0; i < names.length; i++) {
				map.put(names[i], expires[i]);
			}
		}
	}

}
