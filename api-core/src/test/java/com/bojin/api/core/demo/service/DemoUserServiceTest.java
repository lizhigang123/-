/** 
 * @(#)UserServiceTest.java 1.0.0 2015年12月23日 上午9:55:16  
 *  
 * Copyright © 2015 善林金融.  All rights reserved.  
 */

package com.bojin.api.core.demo.service;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.bojin.api.core.BaseSpringBootTest;
import com.bojin.api.core.demo.entity.DemoUserEntity;
import com.bojin.api.core.demo.repository.DemoUserRepository;

/**
 * 
 * 
 * @author samson
 * @version $Revision:1.0.0, $Date: 2015年12月23日 上午9:55:16 $
 */

public class DemoUserServiceTest extends BaseSpringBootTest {

	@Autowired
	DemoUserService userService;
	@Autowired
	DemoUserRepository userRepository;

	@Test
	public void testCacheWithEhcache() {
		String id = "0f6d91ff-7303-4261-8f49-c7f3b90c2383";
		DemoUserEntity userEntity = userService.findOne(id);
		userEntity.setUserName("samson");
		DemoUserEntity userEntityNew = userRepository.save(userEntity);
		DemoUserEntity userEntityOld = userService.findOne(id);

		Assert.assertNotEquals(userEntityNew, userEntityOld);
	}
}
