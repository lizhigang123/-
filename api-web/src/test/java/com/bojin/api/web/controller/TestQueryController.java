/** 
 * @(#)UserController.java 1.0.0 2015年12月12日 下午6:09:06  
 *  
 * Copyright © 2015 善林金融.  All rights reserved.  
 */

package com.bojin.api.web.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bojin.api.common.response.ResponseVo;
import com.bojin.api.common.util.JSONUtils;
import com.bojin.api.core.demo.entity.DemoUserEntity;
import com.bojin.api.core.demo.repository.DemoUserRepository;

/**
 * 
 * 
 * @author samson
 * @version $Revision:1.0.0, $Date: 2015年12月12日 下午6:09:06 $
 */

@RestController
@RequestMapping("/test/query")
public class TestQueryController extends BaseEntityController<DemoUserEntity> {

	@Autowired
	private DemoUserRepository userRepository;

	/********************************************************* 默认查询OO和非OO方式 *****************************************************************/
	@RequestMapping("1")
	public ResponseVo test_1() {
		String userName = "aaaaa";
		List<DemoUserEntity> list = userRepository.findTest1(userName);
		System.out.println(JSONUtils.toJSONString(list));

		list = userRepository.findTest1_1(userName);
		System.out.println(JSONUtils.toJSONString(list));

		list = userRepository.findTest1_2(userName);
		System.out.println(JSONUtils.toJSONString(list));

		List<Object[]> list1 = userRepository.findTest1_3(userName);
		System.out.println(JSONUtils.toJSONString(list1));
		for (Object[] objects : list1) {
			for (Object o : objects) {
				System.out.print(o);
				System.out.print("\t");
			}
			System.out.println();
		}
		System.out.println(JSONUtils.toJSONString(list1));
		return success(list1);
	}
}
