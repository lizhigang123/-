/** 
 * @(#)ProgressController.java 1.0.0 2016年3月16日 上午11:27:18  
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
import org.springframework.web.bind.annotation.ResponseBody;

import com.bojin.api.common.entity.Progress;
import com.bojin.api.common.response.ResponseVo;
import com.bojin.api.common.support.repository.page.PageMapRequest;
import com.bojin.api.common.support.repository.page.PageResponse;
import com.bojin.api.common.support.springweb.service.ServiceAutoMapping;
import com.bojin.api.core.service.admin.ProgressService;
import com.bojin.api.core.service.admin.ProjectService;
import com.bojin.api.core.service.admin.UserService;
import com.bojin.api.web.controller.BaseController;

/**
 * 
 * 
 * @author samson
 * @version $Revision:1.0.0, $Date: 2016年3月16日 上午11:27:18 $
 */
@Controller
@RequestMapping("/admin/progress")
public class ProgressController extends BaseController {

	@Autowired
	private ProjectService projectService;
	@Autowired
	private UserService userService;

	@RequestMapping("")
	public String index(Model model) {
		model.addAttribute("projects", projectService.search(new PageMapRequest(0, 1000, new HashMap<String, Object>())));
		model.addAttribute("developers", userService.findAll());
		return toListView();
	}

	@RequestMapping("/list")
	@ResponseBody
	@ServiceAutoMapping(serviceClass = ProgressService.class)
	public PageResponse<Map<String, Object>> search(PageMapRequest pageRequest) {
		return null;
	}

	@RequestMapping("/submit")
	@ResponseBody
	@ServiceAutoMapping(serviceClass = ProgressService.class)
	public ResponseVo submit(Progress progress, String memo, String branchId) {
		return null;
	}

}
