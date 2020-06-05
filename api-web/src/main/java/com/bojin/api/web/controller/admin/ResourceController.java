/** 
 * @(#)ResourceController.java 1.0.0 2016年4月12日 下午2:22:50  
 *  
 * Copyright © 2016 善林金融.  All rights reserved.  
 */

package com.bojin.api.web.controller.admin;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bojin.api.common.entity.Resource;
import com.bojin.api.common.exception.SLException;
import com.bojin.api.common.response.ResponseVo;
import com.bojin.api.common.support.repository.page.PageResponse;
import com.bojin.api.common.support.springweb.service.ServiceAutoMapping;
import com.bojin.api.common.vo.ResourceVo;
import com.bojin.api.core.service.admin.ResourceService;
import com.bojin.api.core.service.admin.RoleResourceService;
import com.bojin.api.web.controller.BaseController;

/**
 * 资源管理
 * 
 * @author LiYing
 * @version $Revision:1.0.0, $Date: 2016年4月12日 下午2:22:50 $
 */
@Controller
@RequestMapping(value = "admin/resource", method = { RequestMethod.GET, RequestMethod.POST })
public class ResourceController extends BaseController {
	@Autowired
	private ResourceService resourceService;
	@Autowired
	private RoleResourceService roleResourceService;

	@RequestMapping("roleResources")
	@ResponseBody
	public PageResponse<ResourceVo> getRoleResources(String roleId) {
		List<ResourceVo> list = resourceService.getRoleResources(roleId);
		return new PageResponse<ResourceVo>(list.size(), list);
	}

	@RequestMapping("allocating")
	public void allocateResources(String roleId, List<String> resourceIds) throws SLException {

		if (StringUtils.isEmpty(roleId)) {
			throw new SLException("roleId", "roleId can not empty!");
		}
		if (resourceIds == null || resourceIds.isEmpty()) {
			throw new SLException("resourceIds", "resourceIds can not empty");
		}
		roleResourceService.allocateResources(roleId, resourceIds);
	}

	/**
	 * 列表
	 * 
	 * @param model
	 * @return
	 * 
	 */
	@RequestMapping("")
	public String show(Model model) {
		return toShowView();
	}

	@ResponseBody
	@RequestMapping("/list")
	public PageResponse<ResourceVo> getResourceList(String parentId) {
		List<ResourceVo> list = resourceService.getResources(parentId);
		return new PageResponse<ResourceVo>(list.size(), list);
	}

	@ResponseBody
	@RequestMapping("/listResource")
	public PageResponse<ResourceVo> getResourceList() {
		List<ResourceVo> list = resourceService.getResources();
		return new PageResponse<ResourceVo>(list.size(), list);
	}
    /**
     * 新增资源
     * @author jeff
     * @createTime 2016年4月26日 下午3:01:47
     * @param it
     * @return
     */
	@RequestMapping("/submit")
	@ResponseBody
	@ServiceAutoMapping(serviceClass = ResourceService.class)
	public ResponseVo submit(Resource it) {
		return null;
	}

	/**
	 * 刪除資源
	 * 
	 * @author jeff
	 * @createTime 2016年4月26日 下午3:01:26
	 * @param id
	 * @return
	 */
	@RequestMapping("/delete")
	@ResponseBody
	@ServiceAutoMapping(serviceClass = ResourceService.class)
	public ResponseVo delete(String id) {
		return null;
	}

	/**
	 * 資源排序
	 * 
	 * @author jeff
	 * @createTime 2016年4月26日 下午3:01:11
	 * @param ids
	 * @return
	 */
	@RequestMapping("/move")
	@ResponseBody
	@ServiceAutoMapping(serviceClass = ResourceService.class)
	public ResponseVo updateOrdered(String[] ids) {
		return null;
	}
}
