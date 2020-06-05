/** 
 * @(#)LoginController.java 1.0.0 2016年3月17日 下午3:55:06  
 *  
 * Copyright © 2016 善林金融.  All rights reserved.  
 */

package com.bojin.api.web.controller.admin;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.bojin.api.common.entity.User;
import com.bojin.api.common.support.springweb.service.LoginUserContextHolder;
import com.bojin.api.common.support.springweb.service.ResourcesContextHolder;
import com.bojin.api.core.service.admin.ResourceService;
import com.bojin.api.core.service.admin.UserService;
import com.bojin.api.web.controller.BaseController;

/**
 * 
 * 
 * @author samson
 * @version $Revision:1.0.0, $Date: 2016年3月17日 下午3:55:06 $
 */
@Controller
@RequestMapping("/admin")
public class LoginController extends BaseController {

	@Autowired
	private UserService userService;
	@Autowired
	private ResourceService resourceService;

	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public String login(HttpServletRequest request) {
		if (request.getSession().getAttribute("user") != null) {
			return redirectToUrl("");
		}
		return viewName("login");
	}

	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public String loginPost(HttpServletRequest request, String username, String password) {
		User user = userService.findByLoginName(username);
		if (user != null && StringUtils.equals(new Md5PasswordEncoder().encodePassword(password, null), user.getPassword())) {
			request.getSession().setAttribute("user", user);
			//登陆成功设置权限
			LoginUserContextHolder.setLoginUser(user);
			ResourcesContextHolder.setResources(resourceService.getUserResources(user.getId()));
			return redirectToUrl("");
		}
		request.setAttribute("message", "帐号密码错误");
		return viewName("login");
	}
}
