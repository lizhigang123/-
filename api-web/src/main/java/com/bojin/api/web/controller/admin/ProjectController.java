/**
 * @(#)ProjectController.java 1.0.0 2016年3月10日 上午11:08:15
 * <p/>
 * Copyright © 2016 善林金融.  All rights reserved.
 */

package com.bojin.api.web.controller.admin;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bojin.api.common.entity.ParamEntity;
import com.bojin.api.common.entity.Project;
import com.bojin.api.common.entity.ProjectProperties;
import com.bojin.api.common.response.ResponseVo;
import com.bojin.api.common.support.repository.page.PageMapRequest;
import com.bojin.api.common.support.repository.page.PageResponse;
import com.bojin.api.common.support.springweb.service.ServiceAutoMapping;
import com.bojin.api.core.service.admin.ParamService;
import com.bojin.api.core.service.admin.ProjectPropertiesService;
import com.bojin.api.core.service.admin.ProjectService;
import com.bojin.api.core.service.admin.ProjectUserRelationService;
import com.bojin.api.core.service.admin.UserService;
import com.bojin.api.web.controller.BaseController;

/**
 * 项目管理
 *
 * @author samson
 * @version $Revision:1.0.0, $Date: 2016年3月10日 上午11:08:15 $
 */
@Controller
@RequestMapping(value = "/admin/project", method = { RequestMethod.GET, RequestMethod.POST })
public class ProjectController extends BaseController {

	@Autowired
	private UserService userService;
	@Autowired
	private ProjectService projectService;
	@Autowired
	private ProjectPropertiesService projectPropertiesService;
	@Autowired
	private ProjectUserRelationService projectUserRelationService;

	/**
	 * 列表
	 *
	 * @param model
	 * @return
	 */
	@RequestMapping("")
	public String index(Model model) {
		model.addAttribute("developers", userService.findAll());
		return toListView();
	}

	@RequestMapping("/list")
	@ResponseBody
	@ServiceAutoMapping(serviceClass = ProjectService.class)
	public PageResponse<Map<String, Object>> search(PageMapRequest pageRequest) {
		return null;
	}

	@RequestMapping("/submit")
	@ResponseBody
	@ServiceAutoMapping(serviceClass = ProjectService.class)
	public ResponseVo submit(Project project) {
		return null;
	}

	/******************************************** 分支管理 ***************************************************/
	/**
	 * 列表
	 *
	 * @param model
	 * @return
	 */
	@RequestMapping("/sub")
	public String subProjectIndex(Model model, String parentId) {
		model.addAttribute("parentId", parentId);
		model.addAttribute("developers", userService.findAll());
		return viewName("sublist");
	}

	/**
	 * 获取当前开发人员
	 * 
	 * @author fuq
	 * @createTime 2016年5月3日 上午10:47:00
	 * @param model
	 * @param branchId
	 * @return
	 */
	@RequestMapping("/curentDevelopers")
	@ResponseBody
	public Map<String, Object> curentDevelopers(Model model, String projectId) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("curentDevelopers", projectUserRelationService.findUsersByProject(projectId));
		return map;
	}

	/**
	 * 更新开发人员
	 * 
	 * @author fuq
	 * @createTime 2016年4月13日 下午1:38:14
	 * @param model
	 * @param projectId
	 * @param userIds
	 * @return
	 */
	@RequestMapping("/updateProjectUsers")
	@ResponseBody
	public ResponseVo updateProjectUsers(String projectId, String userIds) {
		List<String> list = new ArrayList<String>();
		if (!StringUtils.isEmpty(userIds)) {
			String[] strs = userIds.split(",");
			for (String str : strs) {
				list.add(str);
			}
		}
		projectUserRelationService.updateProjectUsers(projectId, list);
		return ResponseVo.success();
	}

	@RequestMapping("/sub/list")
	@ResponseBody
	@ServiceAutoMapping(serviceClass = ProjectService.class)
	public PageResponse<Map<String, Object>> subProjectSearch(PageMapRequest pageRequest) {
		return null;
	}

	/*********************************************** 项目属性管理 *****************************************/

	/**
	 * 项目属性列表
	 *
	 * @param projectId
	 *            项目id
	 * @return
	 */
	@RequestMapping("/properties")
	public String properties(Model model, String projectId) {
		model.addAttribute("projectId", projectId);
		model.addAttribute("project", projectService.findOne(projectId));
		return viewName("properties");
	}

	/**
	 * @param pageMapRequest
	 * @return
	 */
	@RequestMapping("/properties/list")
	@ResponseBody
	@ServiceAutoMapping(serviceClass = ProjectPropertiesService.class)
	public PageResponse<ProjectProperties> searchProperties(PageMapRequest pageMapRequest) {
		return null;
	}

	/**
	 * @param pageMapRequest
	 * @return
	 */
	@RequestMapping("/properties/submit")
	@ResponseBody
	@ServiceAutoMapping(serviceClass = ProjectPropertiesService.class)
	public ResponseVo submit(ProjectProperties projectProperties) {
		return null;
	}

	/*********************************************** 参数模板管理 *****************************************/
	/**
	 * 项目属性列表
	 *
	 * @param projectId
	 *            项目id
	 * @return
	 */
	@RequestMapping("/template")
	public String template(Model model, String projectId) {
		model.addAttribute("projectId", projectId);
		model.addAttribute("project", projectService.findOne(projectId));
		return viewName("template");
	}

	/**
	 * @param pageMapRequest
	 * @return
	 */
	@RequestMapping("/template/list")
	@ResponseBody
	@ServiceAutoMapping(serviceClass = ProjectPropertiesService.class)
	public PageResponse<ProjectProperties> searchParamTemplate(PageMapRequest pageMapRequest) {
		return null;
	}

	/**
	 * @param type
	 * @return
	 */
	@RequestMapping("/template/lists")
	@ResponseBody
	@ServiceAutoMapping(serviceClass = ProjectPropertiesService.class)
	public PageResponse<Map<String, Object>> getParamsTemplates(PageMapRequest pageRequest) {
		return null;
	}

	/**
	 * 列表
	 *
	 * @param model
	 * @return
	 */
	@RequestMapping("/paramtemplate/show")
	public String show(Model model, String propertiestId) {
		model.addAttribute("propertiestId", propertiestId);
		ProjectProperties projectProperties = projectPropertiesService.findOne(propertiestId);
		Project projectEntity = projectService.findOne(projectProperties.getProjectId());
		model.addAttribute("project", projectEntity);
		return viewName("paramtemplate/show");
	}

	/**
	 * @param propertiestId
	 * @return
	 */
	@RequestMapping("/paramtemplate/list")
	@ResponseBody
	@ServiceAutoMapping(serviceClass = ParamService.class)
	public PageResponse<ParamEntity> getParamsTemplate(String propertiestId) {
		return null;
	}

	@RequestMapping("/paramtemplate/submit")
	@ResponseBody
	@ServiceAutoMapping(serviceClass = ParamService.class)
	public ResponseVo submitParam(ParamEntity it, String propertiestId) {
		return null;
	}
}
