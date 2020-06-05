/** 
 * @(#)CategoryController.java 1.0.0 2016年3月10日 上午11:08:15  
 *  
 * Copyright © 2016 善林金融.  All rights reserved.  
 */

package com.bojin.api.web.controller.admin;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bojin.api.common.entity.Category;
import com.bojin.api.common.response.ResponseVo;
import com.bojin.api.common.support.repository.page.PageMapRequest;
import com.bojin.api.common.support.repository.page.PageResponse;
import com.bojin.api.common.support.springweb.service.ServiceAutoMapping;
import com.bojin.api.core.service.admin.CategoryService;
import com.bojin.api.core.service.admin.ProjectService;
import com.bojin.api.web.controller.BaseController;

/**
 * 项目管理
 * 
 * @author samson
 * @version $Revision:1.0.0, $Date: 2016年3月10日 上午11:08:15 $
 */
@Controller
@RequestMapping(value = "/admin/category", method = { RequestMethod.GET, RequestMethod.POST })
public class CategoryController extends BaseController {

	@Autowired
	private ProjectService projectService;

	/**
	 * 列表
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping("")
	public String index(Model model, String projectId, String projectParentId) {
		model.addAttribute("projectId", projectId);
		model.addAttribute("projectParentId", projectParentId);
		model.addAttribute("projects", projectService.search(new PageMapRequest(0, 1000, new HashMap<String, Object>())));
		return toListView();
	}

	@RequestMapping("/list")
	@ResponseBody
	@ServiceAutoMapping(serviceClass = CategoryService.class)
	public PageResponse<Map<String, Object>> search(PageMapRequest pageRequest) {
		return null;
	}

	@RequestMapping("/submit")
	@ResponseBody
	@ServiceAutoMapping(serviceClass = CategoryService.class)
	public ResponseVo submit(Category category) {
		return null;
	}
}
