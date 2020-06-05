/** 
 * @(#)AdminLoginHandlerInterceptor.java 1.0.0 2016年4月12日 上午11:04:26  
 *  
 * Copyright © 2016 善林金融.  All rights reserved.  
 */ 

package com.bojin.api.web;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.bojin.api.common.entity.Resource;
import com.bojin.api.common.entity.User;
import com.bojin.api.common.support.springweb.service.LoginUserContextHolder;
import com.bojin.api.common.support.springweb.service.ResourcesContextHolder;
import com.bojin.api.core.service.admin.ResourceService;

/**   
 * 
 *  
 * @author  LiYing
 * @version $Revision:1.0.0, $Date: 2016年4月12日 上午11:04:26 $ 
 */
@Component
public class AdminLoginHandlerInterceptor  implements HandlerInterceptor {
	
	private @Autowired ResourceService resourceService;
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		String url = request.getRequestURI();
		Object userObject = request.getSession().getAttribute("user");
		if (StringUtils.startsWith(url, "/admin") && !StringUtils.equals(url, "/admin/login") && !StringUtils.equals(url, "/admin/logout")) {
			if (userObject == null) {
				request.getRequestDispatcher("/admin/login").forward(request, response);
				return false;
			} else {
				User user = (User)userObject;
				List<Resource> roleResources = resourceService.getUserResources(user.getId());
				LoginUserContextHolder.setLoginUser(user);
				ResourcesContextHolder.setResources(roleResources);
				if(!havePremission(roleResources, url)){
					String requestType = request.getHeader("X-Requested-With");
					if(requestType != null && "XMLHttpRequest".endsWith(requestType)) {
						response.setStatus(HttpServletResponse.SC_FORBIDDEN);
						return false;
					} else {
						request.getRequestDispatcher("/admin/login").forward(request, response);
						return false;
					}
				}
			}
		} 
		return true;
	}
	
	private boolean havePremission(List<Resource> roleResources, String url) {
		if (url.equals("/admin") || url.equals("/admin/login") || url.equals("/admin/logout")) {
			return true;
		}
		for (Resource roleResource : roleResources) {
			 String resourceUrl = roleResource.getUrl();
			 String[] urls = StringUtils.split(resourceUrl, ",");
			 for(String oneUrl : urls) {
				 if (url.equals(oneUrl)) {
						return true;
					} 
			 }
		}
		return false;
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

	}

}