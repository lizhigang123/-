/** 
 * @(#)RoleController.java 1.0.0 2016年4月8日 上午10:31:45  
 *  
 * Copyright © 2016 善林金融.  All rights reserved.  
 */

package com.bojin.api.web.controller.admin;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bojin.api.common.entity.Role;
import com.bojin.api.common.exception.SLException;
import com.bojin.api.common.response.ResponseVo;
import com.bojin.api.common.support.repository.page.PageMapRequest;
import com.bojin.api.common.support.repository.page.PageResponse;
import com.bojin.api.common.support.springweb.service.ServiceAutoMapping;
import com.bojin.api.core.service.admin.RoleService;
import com.bojin.api.core.service.admin.UserService;
import com.bojin.api.web.controller.BaseController;

/**
 * 角色管理
 * 
 * @author jeff
 * @version $Revision:1.0.0, $Date: 2016年4月8日 上午10:31:45 $
 */
@Controller
@RequestMapping(value = "/admin/role", method = { RequestMethod.GET, RequestMethod.POST })
public class RoleController extends BaseController {
	@Autowired
	private UserService userService;
	@Autowired
	private RoleService roleService;

	/**
	 * 
	 * @author jeff
	 * @createTime 2016年4月8日 上午11:13:27
	 * @param model
	 * @return
	 */
	@RequestMapping("")
	public String index(Model model) {
		model.addAttribute("userId", userService.getLoginUserId());
		return toListView();
	}

	/**
	 * 角色列表
	 * 
	 * @author jeff
	 * @createTime 2016年4月26日 下午3:00:10
	 * @param pageRequest
	 * @return
	 */
	@RequestMapping("/list")
	@ResponseBody
	@ServiceAutoMapping(serviceClass = RoleService.class)
	public PageResponse<Map<String, Object>> search(PageMapRequest pageRequest) {
		return null;
	}

	/**
	 * 添加角色 页面展示
	 * 
	 * @return
	 */
	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public String create(HttpServletRequest request) {
		request.setAttribute("userId", userService.getLoginUserId());
		return toCreateView();
	}

	/**
	 * 查看编辑角色
	 * 
	 * @return
	 */
	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public String edit(HttpServletRequest request, String roleId, String type) {
		Role role = roleService.findOne(roleId);
		request.setAttribute("id", roleId);
		request.setAttribute("name", role.getName());
		request.setAttribute("type", type);
		request.setAttribute("userId", userService.getLoginUserId());
		return toEditView();
	}

	/**
	 * 删除角色
	 * 
	 * @return
	 */
	@RequestMapping("/delete")
	@ResponseBody
	public ResponseVo deleteRole(String roleId) throws SLException {
		return roleService.deleteRole(roleId);
	}

	/**
	 * 新增角色、角色资源关联
	 * 
	 * @author jeff
	 * @createTime 2016年4月26日 下午3:00:42
	 * @param it
	 * @param resourceIds
	 * @return
	 */
	@RequestMapping("/submit")
	@ResponseBody
	@ServiceAutoMapping(serviceClass = RoleService.class)
	public ResponseVo submit(Role it, String[] resourceIds) {
		return null;
	}
}
