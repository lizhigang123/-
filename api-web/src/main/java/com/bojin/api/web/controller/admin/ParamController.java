/**
 * @(#)InterfaceController.java 1.0.0 2016年3月10日 上午11:08:15
 * <p/>
 * Copyright © 2016 善林金融.  All rights reserved.
 */

package com.bojin.api.web.controller.admin;

import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bojin.api.common.entity.Category;
import com.bojin.api.common.entity.Interface;
import com.bojin.api.common.entity.ParamEntity;
import com.bojin.api.common.entity.Project;
import com.bojin.api.common.response.ResponseVo;
import com.bojin.api.common.support.repository.page.PageMapRequest;
import com.bojin.api.common.support.repository.page.PageResponse;
import com.bojin.api.common.support.springweb.service.ServiceAutoMapping;
import com.bojin.api.core.service.admin.CategoryService;
import com.bojin.api.core.service.admin.InterfaceService;
import com.bojin.api.core.service.admin.ParamService;
import com.bojin.api.core.service.admin.ProjectService;
import com.bojin.api.web.controller.BaseController;

/**
 * 项目管理
 *
 * @author samson
 * @version $Revision:1.0.0, $Date: 2016年3月10日 上午11:08:15 $
 */
@Controller
@RequestMapping(value = "/admin/param", method = { RequestMethod.GET, RequestMethod.POST })
public class ParamController extends BaseController {
	@Autowired
	private ParamService paramService;
	@Autowired
	private CategoryService categoryService;
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
	@RequestMapping("/show")
	public String show(Model model, String interfaceId) {
		model.addAttribute("interfaceId", interfaceId);
		Interface interfaceEntity = interfaceService.findOne(interfaceId);
		Category categoryEntity = categoryService.findOne(interfaceEntity.getCategoryId());
		Project projectEntity = projectService.findOne(categoryEntity.getProjectId());
		if (StringUtils.isNotBlank(interfaceEntity.getBranchId())) {
			Project subProjectEntity = projectService.findOne(interfaceEntity.getBranchId());
			model.addAttribute("subProject", subProjectEntity);
		}
		model.addAttribute("interfaceEntity", interfaceEntity);
		model.addAttribute("category", categoryEntity);
		model.addAttribute("project", projectEntity);
		return toShowView();
	}

	@RequestMapping("/edit")
	public String edit() {
		return toEditView();
	}

	@ServiceAutoMapping(serviceClass = ParamService.class)
	@ResponseBody
	@RequestMapping("/list")
	public PageResponse<ParamEntity> getParams(String interfaceId) {
		return null;
	}

	@ServiceAutoMapping(serviceClass = ParamService.class)
	@ResponseBody
	@RequestMapping("/inputParams")
	public PageResponse<ParamEntity> getInputParams(String interfaceId) {
		return null;
	}

	@RequestMapping("/outputParams")
	@ResponseBody
	@ServiceAutoMapping(serviceClass = ParamService.class)
	public PageResponse<ParamEntity> getOutputParams(String interfaceId) {
		return null;
	}

	@RequestMapping("/submit")
	@ResponseBody
	@ServiceAutoMapping(serviceClass = ParamService.class)
	public ResponseVo submit(ParamEntity it) {
		return null;
	}

	@RequestMapping("/submit/templat")
	@ResponseBody
	@ServiceAutoMapping(serviceClass = ParamService.class)
	public ResponseVo submitTemplate(String propertiesId, String interfaceId, String type) {
		return null;
	}

	@RequestMapping("/del")
	@ResponseBody
	@ServiceAutoMapping(serviceClass = ParamService.class)
	public ResponseVo delete(String id) {
		return null;
	}

	@RequestMapping("/delet/template")
	@ResponseBody
	@ServiceAutoMapping(serviceClass = ParamService.class)
	public ResponseVo deleteTemplate(String id) {
		return null;
	}

	@RequestMapping("/move")
	@ResponseBody
	@ServiceAutoMapping(serviceClass = ParamService.class)
	public ResponseVo updateOrdered(String[] ids) {
		return null;
	}

	@RequestMapping("/paramtemplate/move")
	@ResponseBody
	@ServiceAutoMapping(serviceClass = ParamService.class)
	public ResponseVo updateParamTemplateOrdered(String[] ids) {
		paramService.updateOrdered(ids);
		return null;
	}

	/**
	 * @param name
	 *            根据参数名搜索所有参数
	 * @return
	 */
	@RequestMapping("/listall")
	@ResponseBody
	@ServiceAutoMapping(serviceClass = ParamService.class)
	public PageResponse<Map<String, Object>> getAllParamByName(PageMapRequest pageRequest) {
		return null;
	}

	/**
	 * 根据参数名来匹配参数
	 * 
	 * @author jeff
	 * @createTime 2016年5月12日 下午7:52:04
	 * @param pageRequest
	 * @return
	 */

	@RequestMapping("/match")
	@ResponseBody
	@ServiceAutoMapping(serviceClass = ParamService.class)
	public PageResponse<Map<String, Object>> matchParams(PageMapRequest pageRequest) {
		return null;
	}
    /**
     * 批量保存接口参数
     * @author jeff
     * @createTime 2016年5月13日 上午9:42:06
     * @param interfaceId
     * @param parentId
     * @param type
     * @param paramIds
     * @return
     */
	@RequestMapping("/saveParams")
	@ResponseBody
	@ServiceAutoMapping(serviceClass = ParamService.class)
	public ResponseVo saveParams(String interfaceId, String parentId, String type, String paramIds) {
		return null;
	}
}
