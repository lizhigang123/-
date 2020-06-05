/** 
 * @(#)UserRepositoryTest.java 1.0.0 2016年1月8日 下午3:55:31  
 *  
 * Copyright © 2016 善林金融.  All rights reserved.  
 */

package com.bojin.api.core.demo.repository;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import com.bojin.api.common.support.repository.page.PageRequest;
import com.bojin.api.common.support.repository.page.PageResponse;
import com.bojin.api.core.BaseSpringBootTest;
import com.bojin.api.core.demo.entity.DemoUserEntity;

/**
 * 
 * 
 * @author samson
 * @version $Revision:1.0.0, $Date: 2016年1月8日 下午3:55:31 $
 */
public class UserRepositoryTest extends BaseSpringBootTest {
	@Autowired
	private DemoUserRepository userRepository;

	@SuppressWarnings("unused")
	@Autowired
	private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

	@SuppressWarnings("unused")
	@Test
	public void testFindTest2_1() {
		List<Map<String, Object>> list = userRepository.findTest2_1(null, null);

		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("name", "aaaaa");
		parameters.put("birthday", new Date());
		List<DemoUserEntity> list1 = userRepository.findTest2_2("", new Date());

		list1 = userRepository.findTest2_3("", new Date());

		List<Map<String, Object>> list2 = userRepository.findTest2_4("", new Date());

		List<Map<String, Object>> list3 = userRepository.findTest2_5("", new Date());

		PageResponse<Map<String, Object>> a = userRepository.findTest2_6("", new Date());

		PageResponse<Map<String, Object>> bb = userRepository.findTest2_7(null);
		PageResponse<Map<String, Object>> bbb = userRepository.findTest2_7(new PageRequest<Map<String, Object>>());

		PageResponse<Map<String, Object>> aa = userRepository.findTest2_8("", new Date(), null);
		PageResponse<Map<String, Object>> aaa = userRepository.findTest2_8("", new Date(), new PageRequest<Map<String, Object>>());

		PageResponse<DemoUserEntity> cc = userRepository.findTest2_10(null);
		PageResponse<DemoUserEntity> cccc = userRepository.findTest2_11(new PageRequest<Map<String, Object>>(), "", new Date());

		PageResponse<String> dd = userRepository.findTest2_12(new PageRequest<Map<String, Object>>());
		System.out.println("");
	}

}
