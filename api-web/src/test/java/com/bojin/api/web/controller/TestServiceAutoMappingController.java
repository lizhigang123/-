/** 
 * @(#)TestServiceAutoMappingController.java 1.0.0 2016年2月29日 下午5:52:10  
 *  
 * Copyright © 2016 善林金融.  All rights reserved.  
 */

package com.bojin.api.web.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bojin.api.common.response.ResponseVo;
import com.bojin.api.common.support.springweb.service.ServiceAutoMapping;
import com.bojin.api.core.demo.service.DemoUserService;

/**
 * 服务自动分发测试用例
 * 
 * @author samson
 * @version $Revision:1.0.0, $Date: 2016年2月29日 下午5:52:10 $
 */
@RestController
@RequestMapping("/test/tsam")
@ServiceAutoMapping(serviceClass = DemoUserService.class)
public class TestServiceAutoMappingController extends BaseController {

	/**
	 * 自动调用{@code UserService}中的test_tsam_1方法
	 * 
	 * @return
	 */
	@RequestMapping("/1")
	public ResponseVo test_tsam_1() {
		return null;
	}

	/**
	 * 自动调用{@code UserService}中的test_tsam_1方法
	 * 
	 * @return
	 */
	@RequestMapping("/2")
	@ServiceAutoMapping(serviceClass = DemoUserService.class, serviceMethod = "test_tsam_1")
	public ResponseVo test_tsam_11() {
		return null;
	}

	/**
	 * 自动调用{@code UserService}中的test_tsam_1方法
	 * 
	 * @return
	 */
	@RequestMapping("/3")
	@ServiceAutoMapping(serviceClass = DemoUserService.class, serviceMethod = "test_tsam_1")
	public ResponseVo test_tsam_111() {
		return null;
	}

}
