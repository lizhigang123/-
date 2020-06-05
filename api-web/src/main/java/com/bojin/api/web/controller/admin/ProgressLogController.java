/** 
 * @(#)ProgressLogController.java 1.0.0 2016年4月26日 下午2:54:55  
 *  
 * Copyright © 2016 善林金融.  All rights reserved.  
 */

package com.bojin.api.web.controller.admin;

import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bojin.api.common.support.repository.page.PageMapRequest;
import com.bojin.api.common.support.repository.page.PageResponse;
import com.bojin.api.common.support.springweb.service.ServiceAutoMapping;
import com.bojin.api.core.service.admin.ProgressLogService;
import com.bojin.api.web.controller.BaseController;

/**
 * 进度管理日志
 * 
 * @author jeff
 * @version $Revision:1.0.0, $Date: 2016年4月26日 下午2:54:55 $
 */
@Controller
@RequestMapping("/admin/progresslog")
public class ProgressLogController extends BaseController {
	/**
	 * 列表
	 * 
	 * @author jeff
	 * @createTime 2016年4月26日 下午3:07:18
	 * @param model
	 * @return
	 */
	@RequestMapping("")
	public String index(Model model) {
		return toListView();
	}

	/**
	 * 列表详情
	 * 
	 * @author jeff
	 * @createTime 2016年4月26日 下午3:07:40
	 * @param pageRequest
	 * @return
	 */
	@RequestMapping("/list")
	@ResponseBody
	@ServiceAutoMapping(serviceClass = ProgressLogService.class)
	public PageResponse<Map<String, Object>> search(PageMapRequest pageRequest) {
		return null;
	}
}
