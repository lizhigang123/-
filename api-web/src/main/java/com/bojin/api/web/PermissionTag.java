/** 
 * @(#)HelloTag.java 1.0.0 2016年4月11日 下午1:28:46  
 *  
 * Copyright © 2016 善林金融.  All rights reserved.  
 */

package com.bojin.api.web;

import java.util.List;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

import org.apache.commons.lang3.StringUtils;

import com.bojin.api.common.entity.Resource;
import com.bojin.api.common.support.springweb.service.ResourcesContextHolder;

/**
 * 权限标签的解析
 * 
 * @author LiYing
 * @version $Revision:1.0.0, $Date: 2016年4月11日 下午1:28:46 $
 */
public class PermissionTag extends TagSupport {

	private static final long serialVersionUID = -904317813018661396L;
	private static final String URL_SEPARATOR = ",";
	private String url;

	@Override
	public int doStartTag() throws JspException {
		boolean result = false;
		List<Resource> resources = ResourcesContextHolder.getResources();
		for (Resource resource : resources) {
			String resourceUrl = resource.getUrl();
			String[] urls = StringUtils.split(resourceUrl, URL_SEPARATOR);
			for (String oneUrl : urls) {
				if (url.equals(oneUrl)) {
					result = true;
					break;
				}
			}
		}
		return result ? EVAL_BODY_INCLUDE : SKIP_BODY;// 真：返回EVAL_BODY_INCLUDE（执行标签）；假：返回SKIP_BODY（跳过标签不执行）
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}
}