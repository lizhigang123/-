/** 
 * @(#)UserController.java 1.0.0 2016年4月12日 上午11:49:55  
 *  
 * Copyright © 2016 善林金融.  All rights reserved.  
 */

package com.bojin.api.web.controller.admin;

import java.util.List;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bojin.api.common.entity.User;
import com.bojin.api.common.exception.SLException;
import com.bojin.api.common.response.ResponseVo;
import com.bojin.api.common.support.repository.page.PageMapRequest;
import com.bojin.api.common.support.repository.page.PageResponse;
import com.bojin.api.common.support.springweb.service.ServiceAutoMapping;
import com.bojin.api.common.vo.UserRoleVo;
import com.bojin.api.core.service.admin.RoleService;
import com.bojin.api.core.service.admin.UserRoleService;
import com.bojin.api.core.service.admin.UserService;
import com.bojin.api.web.controller.BaseController;

/**
 * 用户管理
 * 
 * @author jeff
 * @version $Revision:1.0.0, $Date: 2016年4月12日 上午11:49:55 $
 */
@Controller
@RequestMapping(value = "/admin/user", method = { RequestMethod.GET, RequestMethod.POST })
public class UserController extends BaseController {
	@Autowired
	private UserService userService;
	@Autowired
	private RoleService roleService;

	/**
	 * @author jeff
	 * @createTime 2016年4月12日 上午11:55:27
	 * @param model
	 * @return
	 */
	@RequestMapping("")
	public String index(Model model) {
		model.addAttribute("userId", userService.getLoginUserId());
		return toListView();
	}

	/**
	 * 用户列表
	 * 
	 * @author jeff
	 * @createTime 2016年4月26日 下午2:59:22
	 * @param pageRequest
	 * @return
	 */
	@RequestMapping("/list")
	@ResponseBody
	@ServiceAutoMapping(serviceClass = UserService.class)
	public PageResponse<Map<String, Object>> search(PageMapRequest pageRequest) {
		return null;
	}

	/**
	 * 模糊匹配开发人员
	 * 
	 * @author fuq
	 * @createTime 2016年4月20日 上午10:51:25
	 * @param pageRequest
	 * @return
	 */
	@RequestMapping(value = "/match")
	@ResponseBody
	@ServiceAutoMapping(serviceClass = UserService.class)
	public PageResponse<Map<String, Object>> match(PageMapRequest pageRequest) {
		return null;
	}

	/**
	 * 新增用户
	 * 
	 * @author jeff
	 * @createTime 2016年4月26日 下午2:59:40
	 * @param user
	 * @return
	 */
	@RequestMapping("/submit")
	@ResponseBody
	@ServiceAutoMapping(serviceClass = UserService.class)
	public ResponseVo submit(User user) {
		return null;
	}
	/**
	 * 密码修改
	 * @author jeff
	 * @createTime 2016年5月5日 上午10:56:38
	 * @param user
	 * @return
	 */
	@RequestMapping("/modifypassword")
	@ResponseBody
	@ServiceAutoMapping(serviceClass = UserService.class)
	public ResponseVo modifyPassword(User user) {
		return null;
	}
	
	/**
	 * 分配角色
	 * 
	 * @return
	 */
	@RequestMapping("/editrole")
	public String editRole(Model model, String userId) {
		model.addAttribute("userId", userId);
		List<UserRoleVo> roleList = roleService.getUserRoles(userId);
		model.addAttribute("roleList", roleList);
		return viewName("editrole");
	}

	/**
	 * 保存用户角色ID
	 * 
	 * @author jeff
	 * @createTime 2016年4月18日 下午5:32:57
	 * @param userId
	 * @param roleIds
	 * @return
	 */
	@RequestMapping("/saveRoleIds")
	@ResponseBody
	@ServiceAutoMapping(serviceClass = UserRoleService.class)
	public ResponseVo saveRoleIds(String userId, String roleIds) {
		return null;
	}

	/**
	 * 删除用户
	 * 
	 * @author jeff
	 * @createTime 2016年4月21日 下午2:45:58
	 * @param interfaceId
	 * @return
	 * @throws SLException
	 */
	@RequestMapping("/delete")
	@ResponseBody
	public ResponseVo delete(String userId) throws SLException {
		return userService.deleteUser(userId);
	}
}
