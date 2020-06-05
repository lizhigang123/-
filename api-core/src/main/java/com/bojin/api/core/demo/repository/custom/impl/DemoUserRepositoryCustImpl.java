/** 
 * @(#)UserRepositoryCustImpl.java 1.0.0 2015年12月19日 下午2:14:59  
 *  
 * Copyright © 2015 善林金融.  All rights reserved.  
 */

package com.bojin.api.core.demo.repository.custom.impl;

import java.util.Map;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.bojin.api.core.demo.repository.custom.DemoUserRepositoryCust;
import com.bojin.api.core.repository.custom.impl.BaseRepositoryCustImpl;

/**
 * 
 * 
 * @author samson
 * @version $Revision:1.0.0, $Date: 2015年12月19日 下午2:14:59 $
 */
@Repository
@Transactional(readOnly = true)
public class DemoUserRepositoryCustImpl extends BaseRepositoryCustImpl implements DemoUserRepositoryCust {

	@Override
	public String testCust(Map<String, Object> map) {
		System.out.println(jdbcTemplate);
		System.out.println(namedParameterJdbcTemplate);
		System.out.println(entityManager);
		return "1111111111111111111";
	}

	@Override
	public String testCust1(String a, String b) {
		System.out.println("222222222222222222222222222222222");
		System.out.println(jdbcTemplate);
		System.out.println(namedParameterJdbcTemplate);
		System.out.println(entityManager);
		return "22222222222222222222222222222222222222222222222222222";
	}

}
