package com.bojin.api.web.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.alibaba.druid.util.StringUtils;
import com.bojin.api.common.entity.BaseEntity;
import com.bojin.api.common.entity.Category;
import com.bojin.api.common.entity.Project;
import com.bojin.api.core.repository.CategoryRepository;
import com.bojin.api.core.repository.ProjectRepository;

@Controller
public class WebIndexController extends BaseController {

	@Autowired
	private CategoryRepository categoryRepository;
	@Autowired
	private ProjectRepository projectRepository;

	@RequestMapping("")
	public String index(Model model, String projectId) {
		// 项目列表
		List<Project> projectEntityList = projectRepository.findAllProjects();
		if (projectEntityList.size() > 0 ) {
			model.addAttribute("projectEntityList", projectEntityList);
			// 当前项目信息
			Project projectEneity = (Project) getCurrent(projectId, projectEntityList);
			model.addAttribute("projectEneity", projectEneity);
			// 当前项目下所有类目
			List<Category> categoryEntityList = categoryRepository.findByProjectIdOrderBySortDesc(projectEneity.getId());
			model.addAttribute("categoryEntityList", categoryEntityList);
		}
		return "/api/project";
	}

	/**
	 * 取当前元素，默认为加载到的第一个元素
	 * 
	 * @param id
	 * @param list
	 * @return
	 */
	private <T extends BaseEntity> BaseEntity getCurrent(String id, List<T> list) {
		BaseEntity baseEntity = null;
		if (!StringUtils.isEmpty(id)) {
			for (BaseEntity be : list) {
				if (be.getId().equals(id)) {
					baseEntity = be;
					break;
				}
				continue;
			}
		}
		if (baseEntity == null) {
			baseEntity = list.get(0);
		}
		return baseEntity;
	}
	
}
