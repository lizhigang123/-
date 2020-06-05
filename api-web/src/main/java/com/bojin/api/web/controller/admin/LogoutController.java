/** 
 * @(#)LogoutController.java 1.0.0 2016年3月17日 下午4:34:16  
 *  
 * Copyright © 2016 善林金融.  All rights reserved.  
 */

package com.bojin.api.web.controller.admin;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.bojin.api.web.controller.BaseController;

/**
 * 
 * 
 * @author samson
 * @version $Revision:1.0.0, $Date: 2016年3月17日 下午4:34:16 $
 */
@Controller
@RequestMapping("/admin")
public class LogoutController extends BaseController {

	@RequestMapping("/logout")
	public String logout(HttpServletRequest request) {
		request.getSession().invalidate();
		return redirectToUrl("/admin/login");
	}

}
