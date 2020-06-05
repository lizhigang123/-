/**
 * @(#)InterfaceController.java 1.0.0 2016年3月10日 上午11:08:15
 * <p/>
 * Copyright © 2016 善林金融.  All rights reserved.
 */

package com.bojin.api.web.controller.admin;

import com.bojin.api.common.constants.enums.InterfaceStatusEnum;
import com.bojin.api.common.entity.Interface;
import com.bojin.api.common.exception.SLException;
import com.bojin.api.common.response.ResponseVo;
import com.bojin.api.common.support.repository.page.PageMapRequest;
import com.bojin.api.common.support.repository.page.PageResponse;
import com.bojin.api.common.support.springweb.service.ServiceAutoMapping;
import com.bojin.api.core.service.admin.InterfaceService;
import com.bojin.api.core.service.admin.ProjectService;
import com.bojin.api.web.controller.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

/**
 * 项目管理
 *
 * @author samson
 * @version $Revision:1.0.0, $Date: 2016年3月10日 上午11:08:15 $
 */
@Controller
@RequestMapping(value = "/admin/interface", method = {RequestMethod.GET, RequestMethod.POST})
public class InterfaceController extends BaseController {

	@Autowired
	private ProjectService projectService;
	@Autowired
	private InterfaceService interfaceService;

	/**
	 * 列表
	 *
	 * @param model
	 * @return
	 */
	@RequestMapping("")
	public String index(Model model, String categoryId, String projectId) {
		model.addAttribute("categoryId", categoryId);
		model.addAttribute("branchId", projectId);
		model.addAttribute("projects", projectService.search(new PageMapRequest(0, 1000, new HashMap<String, Object>())));
		return toListView();
	}

	@RequestMapping("/list")
	@ResponseBody
	@ServiceAutoMapping(serviceClass = InterfaceService.class)
	public PageResponse<Map<String, Object>> search(PageMapRequest pageRequest) {
		return null;
	}

	@RequestMapping("/submit")
	@ResponseBody
	@ServiceAutoMapping(serviceClass = InterfaceService.class)
	public ResponseVo submit(Interface it) {
		return null;
	}

	@RequestMapping("/findOne")
	@ResponseBody
	@ServiceAutoMapping(serviceClass = InterfaceService.class)
	public ResponseVo findOneWithExtraInfo(String id) {
		return null;
	}

	@RequestMapping("/release")
	@ResponseBody
	public ResponseVo release(String interfaceId) throws SLException {
		return interfaceService.updateStatus(interfaceId, InterfaceStatusEnum.发布);
	}

	@RequestMapping("/revoke")
	@ResponseBody
	public ResponseVo revoke(String interfaceId) throws SLException {
		return interfaceService.updateStatus(interfaceId, InterfaceStatusEnum.作废);
	}
}
