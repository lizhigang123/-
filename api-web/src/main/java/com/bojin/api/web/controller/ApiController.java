package com.bojin.api.web.controller;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

import com.bojin.api.common.constants.Constants;
import com.bojin.api.common.constants.enums.InterfaceStatusEnum;
import com.bojin.api.common.constants.enums.YesOrNoEnum;
import com.bojin.api.common.entity.BaseEntity;
import com.bojin.api.common.entity.Category;
import com.bojin.api.common.entity.Interface;
import com.bojin.api.common.entity.ParamEntity;
import com.bojin.api.common.entity.Project;
import com.bojin.api.common.response.ResponseVo;
import com.bojin.api.core.repository.CategoryRepository;
import com.bojin.api.core.repository.InterfaceRepository;
import com.bojin.api.core.repository.ProjectRepository;
import com.bojin.api.core.service.admin.CategoryService;
import com.bojin.api.core.service.admin.InterfaceService;
import com.bojin.api.core.service.admin.ParamService;
import com.bojin.api.core.service.admin.ProjectPropertiesService;
import com.bojin.api.core.service.admin.ProjectService;
import com.bojin.api.web.vo.ShowParamVo;
import com.google.common.collect.Maps;

/**
 * API前端控制器
 * 
 * @author fuq
 * @version $Revision:1.0.0, $Date: 2016年3月11日 下午14:14:15 $
 * 
 */
@Controller
@RequestMapping(value = "/api", method = { RequestMethod.GET, RequestMethod.POST })
public class ApiController extends BaseController {

	@Autowired
	private CategoryRepository categoryRepository;
	@Autowired
	private ProjectRepository projectRepository;
	@Autowired
	private InterfaceRepository interfaceRepository;
	@Autowired
	private InterfaceService interfaceService;
	@Autowired
	private ParamService paramService;
	@Autowired
	private CategoryService categoryService;
	@Autowired
	private ProjectService projectService;
	@Autowired
	private ProjectPropertiesService projectPropertiesService;
	@Autowired
	private FreeMarkerConfigurer freeMarkerConfigurer;

	@RequestMapping("/project/{projectId}")
	public String index(Model model, @PathVariable String projectId) {
		// 项目列表
		List<Project> projectEntityList = projectRepository.findAllProjects();
		if (null == projectEntityList || projectEntityList.size() == 0) {
			return "/api/project";
		}
		model.addAttribute("projectEntityList", projectEntityList);
		// 当前项目信息
		Project projectEneity = projectRepository.findOne(projectId);
		model.addAttribute("projectEneity", projectEneity);
		// 当前项目下所有类目
		List<Category> categoryEntityList = categoryRepository.findByProjectIdOrderBySortDesc(projectEneity.getId());
		model.addAttribute("categoryEntityList", categoryEntityList);
		return "/api/project";
	}

	/**
	 * 类目-接口列表
	 * 
	 * @param model
	 * @param categoryId
	 *            :类目ID
	 * @return
	 */
	@RequestMapping("/category/{categoryId}")
	public String apiList(Model model, @PathVariable("categoryId") String categoryId) {
		// 当前类目
		Category categoryEntity = categoryRepository.findOne(categoryId);
		if (categoryEntity == null) {
			return "/api/category";
		}
		model.addAttribute("categoryEntity", categoryEntity);
		// 项目列表
		List<Project> projectEntityList = projectRepository.findAllProjects();
		model.addAttribute("projectEntityList", projectEntityList);
		// 当前项目信息
		Project projectEneity = projectRepository.findOne(categoryEntity.getProjectId());
		model.addAttribute("projectEneity", projectEneity);
		// 项目下所有类目
		List<Category> categoryEntityList = categoryRepository.findByProjectIdOrderBySortDesc(projectEneity.getId());
		model.addAttribute("categoryEntityList", categoryEntityList);

		// 类目下所有接口
		List<Interface> interfaceEntityList = interfaceRepository.findByCategoryIdAndStatusAndVisible(categoryEntity.getId(), InterfaceStatusEnum.发布, YesOrNoEnum.是);
		model.addAttribute("interfaceEntityList", interfaceEntityList);
		return "/api/category";
	}

	/**
	 * 接口详情首页
	 * 
	 * @param model
	 * @param interfaceId
	 * @param from
	 * @param session
	 * @return
	 */
	@RequestMapping("/interface/{interfaceId}")
	public String detail(Model model, @PathVariable("interfaceId") String interfaceId, String from,HttpSession session) {
		model.addAttribute("show", (StringUtils.isNotBlank(from) && from.startsWith("dev")) || (session.getAttribute("user") != null));
		shareGain(model,interfaceId);
		return "interface";
	}

	/**
	 * 组装需要展示参数的结构
	 * 
	 * @author SangLy
	 * @createTime 2017年8月23日 上午11:25:01
	 * @param interfaceId
	 * @param paramEntity
	 * @param showParamVoList
	 * @param spaceNum
	 * @param isImaginaryLine
	 * @return
	 */
	public List<ShowParamVo> getParamEnterShowParamVoList(String interfaceId, ParamEntity paramEntity, List<ShowParamVo> showParamVoList, int spaceNum, String isImaginaryLine, String paramTypeInputOrOut) {
		ShowParamVo showParamVo = new ShowParamVo(paramEntity.getName(), paramEntity.getDataType(), paramEntity.getNeed(), paramEntity.getDefaultValue(), paramEntity.getDescription(), spaceNum, isImaginaryLine);
		showParamVoList.add(showParamVo);
		// 查询是否有子节点
		List<ParamEntity> childParamEntityList = paramService.findByInterfaceIdAndTypeAndParentIdIdOrderByOrderedAsc(interfaceId, paramTypeInputOrOut, paramEntity.getId());
		if (CollectionUtils.isNotEmpty(childParamEntityList)) {
			spaceNum++;
			for (ParamEntity childParamEntity : childParamEntityList) {
				getParamEnterShowParamVoList(interfaceId, childParamEntity, showParamVoList, spaceNum, "true", paramTypeInputOrOut);
			}
			spaceNum--;
		}
		return showParamVoList;
	}

	/**
	 * 文件压缩
	 * 
	 * @author fmj
	 * @createTime 2017年9月21日 下午5:08:00
	 * @param interfaceIds
	 * @param request
	 * @param response
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/reduce")
	public ResponseVo reduce(String interfaceIds, HttpServletRequest request, HttpServletResponse response,Model model) {
		String uuid = UUID.randomUUID().toString().replace("-", "");
		String path = uploadRootPath + File.separator + uuid;
		File f = new File(path);
		f.mkdir();
		String templateName = "interface.ftl";
		BufferedWriter bw = null;
		String[] ids = interfaceIds.split(",");
		List<String> list = Arrays.asList(ids);
		String downloadPath = null;
		if (CollectionUtils.isNotEmpty(list)) {
			for (String interfaceId : list) {
				// 接口详情信息
				Interface interfaceEntity = interfaceService.findOne(interfaceId);
				if (interfaceEntity != null) {
					shareGain(model,interfaceId);
					Map<String, Object> map = Maps.newHashMap(model.asMap());
					File file = new File(f, File.separator + interfaceId + ".html");
					try {
						bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file), "UTF-8"));
						freeMarkerConfigurer.getConfiguration().getTemplate(templateName).process(map, bw);
					} catch (Exception e) {
						logger.error("生成文件失败", e);
					}
					downloadPath = File.separator + uuid + ".zip";
					createZip(path, uploadRootPath + downloadPath);
					try {
						if (bw != null) {
							bw.close();
						}
					} catch (Exception e) {
						logger.error("文件导出异常", e);
					}
				}
			}
			deleteFile(f);
		}
		return ResponseVo.success(downloadPath);
	}

	/**
	 * 文件下载
	 * 
	 * @author fmj
	 * @createTime 2017年9月21日 下午5:05:43
	 * @param response
	 * @param path
	 * @return
	 */
	@RequestMapping("/downLoadZip")
	public ResponseEntity<byte[]> downLoadZip(HttpServletResponse response, String path) {
		File file = new File(uploadRootPath + path);
		ResponseEntity<byte[]> re = null;
		try {
			// 处理显示中文文件名的问题
			String fileName = new String(file.getName().getBytes("utf-8"), "ISO-8859-1");
			// 设置请求头内容,告诉浏览器代开下载窗口
			HttpHeaders headers = new HttpHeaders();
			headers.setContentDispositionFormData("attachment", fileName);
			headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
			re = new ResponseEntity<byte[]>(FileUtils.readFileToByteArray(file), headers, HttpStatus.CREATED);
		} catch (Exception e) {
			logger.error("文件下载异常", e);
		}finally {
			file.delete();
		}
		return re;
	}

	/**
	 * 压缩方法
	 * 
	 * @author fmj
	 * @createTime 2017年9月20日 下午7:24:59
	 * @param sourcePath
	 *            文件或文件夹路径
	 * @param zipPath
	 *            生成的zip文件存在路径（包括文件名）
	 */
	public void createZip(String sourcePath, String zipPath) {
		FileOutputStream fos = null;
		ZipOutputStream zos = null;
		try {
			fos = new FileOutputStream(zipPath);
			zos = new ZipOutputStream(fos);
			writeZip(new File(sourcePath), "", zos);
		} catch (FileNotFoundException e) {
			logger.error("创建ZIP文件失败", e);
		} finally {
			try {
				if (zos != null) {
					zos.close();
				}
			} catch (Exception e) {
				logger.error("创建ZIP文件失败", e);
			}

		}
	}

	private void writeZip(File file, String parentPath, ZipOutputStream zos) {
		if (file.exists()) {
			if (file.isDirectory()) {// 处理文件夹
				parentPath += file.getName() + File.separator;
				File[] files = file.listFiles();
				for (File f : files) {
					writeZip(f, parentPath, zos);
				}
			} else {
				FileInputStream fis = null;
				try {
					fis = new FileInputStream(file);
					ZipEntry ze = new ZipEntry(parentPath + file.getName());
					zos.putNextEntry(ze);
					byte[] content = new byte[1024];
					int len;
					while ((len = fis.read(content)) != -1) {
						zos.write(content, 0, len);
						zos.flush();
					}
				} catch (Exception e) {
					logger.error("创建ZIP文件失败", e);
				} finally {
					try {
						if (fis != null) {
							fis.close();
						}
					} catch (Exception e) {
						logger.error("创建ZIP文件失败", e);
					}
				}
			}
		}
	}

	// 删除文件夹
	private void deleteFile(File file) {
		if (file.exists()) {
			if (file.isFile()) {
				file.delete();
			} else if (file.isDirectory()) {
				File files[] = file.listFiles();
				for (int i = 0; i < files.length; i++) {
					this.deleteFile(files[i]);
				}
			}
		}
		file.delete();
	}

	public void shareGain (Model model,String interfaceId) {
		// 接口详情信息
		Interface interfaceEntity = interfaceService.findOne(interfaceId);
		if (interfaceEntity != null) {
			model.addAttribute("interfaceInfo", interfaceEntity);
	
			// 类目详情
			Category categoryEntity = categoryService.findOne(interfaceEntity.getCategoryId());
			model.addAttribute("categoryInfo", categoryEntity);
	
			// 项目详情
			Project projectEntity = projectService.findOne(categoryEntity.getProjectId());
			model.addAttribute("projectInfo", projectEntity);
	
			// 项目列表
			List<Project> projectList = projectService.findAllProjects();
			model.addAttribute("projectList", projectList);
			// 接口list
			List<Interface> resultList = interfaceService.findByCategoryId(categoryEntity.getId());
			model.addAttribute("interList", resultList);
			// 输入参数
			List<ParamEntity> paramEnterList = paramService.findByInterfaceIdAndTypeAndParentIdIsNullOrderByOrderedAsc(interfaceId, Constants.PARAM_TYPE_INPUT);
	
			List<ShowParamVo> showParamEnterVoList = new ArrayList<ShowParamVo>();
			int spaceNum = 0;
			String isImaginaryLine = "false";
			for (ParamEntity paramEntity : paramEnterList) {
				getParamEnterShowParamVoList(interfaceId, paramEntity, showParamEnterVoList, spaceNum, isImaginaryLine, Constants.PARAM_TYPE_INPUT);
			}
			model.addAttribute("showParamEnterVoList", showParamEnterVoList);
			// 输出参数
			List<ParamEntity> paramOutList = paramService.findByInterfaceIdAndTypeAndParentIdIsNullOrderByOrderedAsc(interfaceId, Constants.PARAM_TYPE_OUTPUT);
			List<ShowParamVo> showParamOutVoList = new ArrayList<ShowParamVo>();
			spaceNum = 0;
			isImaginaryLine = "false";
			for (ParamEntity paramEntity : paramOutList) {
				getParamEnterShowParamVoList(interfaceId, paramEntity, showParamOutVoList, spaceNum, isImaginaryLine, Constants.PARAM_TYPE_OUTPUT);
			}
			model.addAttribute("showParamOutVoList", showParamOutVoList);
	
			// 项目属性
			model.addAttribute("projectProperties", projectPropertiesService.findFrontList(projectEntity.getId()));
			model.addAttribute("projectId", projectEntity.getId());
			model.addAttribute("categoryId", categoryEntity.getId());
			model.addAttribute("interfaceId", interfaceId);
		}
	}
	
	/**
	 * 接口详情首页
	 * 
	 * @param model
	 * @param keyword
	 * @param session
	 * @return
	 */
	@RequestMapping("/search")
	public String search(Model model, String keyword,HttpSession session) {
		// 项目列表
		List<Project> projectEntityList = projectRepository.findAllProjects();
		if (null == projectEntityList || projectEntityList.size() == 0) {
			return "/category/index";
		}
		model.addAttribute("projectEntityList", projectEntityList);
		// 当前项目信息
		Project projectEneity = (Project) getCurrent(null, projectEntityList);
		model.addAttribute("projectEneity", projectEneity);
		// 当前项目下所有类目
		List<Category> categoryEntityList = categoryRepository.findByProjectIdOrderBySortDesc(projectEneity.getId());
		model.addAttribute("categoryEntityList", categoryEntityList);
		
		//搜索结果
		List<Map<String, Object>> searchResultList = null;
		if (StringUtils.isNotBlank(keyword)) {
			searchResultList = interfaceRepository.search(keyword);
		}
		//封装对外显示项目列表。项目下的接口被匹配到
		List<Map<String,Object>> abloutProjectList = new ArrayList<Map<String,Object>>();
		if(CollectionUtils.isNotEmpty(searchResultList)){
			Set<String> projectNameSet = new HashSet<String>();
			for(Map<String,Object> searchResult : searchResultList){
				projectNameSet.add(searchResult.get("projectName")!=null?searchResult.get("projectName").toString():null);
			}
			for(String projectName : projectNameSet){
				Map<String,Object> projectParm = new HashMap<String,Object>();
				List<Map<String,Object>> machList = new ArrayList<Map<String,Object>>();
				projectParm.put("name",projectName);
				projectParm.put("machList",machList);
				for (Map<String, Object> searchResult : searchResultList) {
					if (projectName == null || searchResult.get("projectName").toString().equals(projectName)) {
						Map<String, Object> mach = new HashMap<String, Object>();
						mach.put("id", searchResult.get("id"));
						mach.put("name", searchResult.get("name"));
						mach.put("url", searchResult.get("url"));
						machList.add(mach);
					}
				}
				abloutProjectList.add(projectParm);
			}
		}else{
			Map<String,Object> projectParm = new HashMap<String,Object>();
			abloutProjectList.add(projectParm);
			projectParm.put("name","未找到相应记录");
			projectParm.put("machList",null);
		}
		//模拟搜索结果
		
		model.addAttribute("projectList", abloutProjectList);
		
		return "/api/searchresult";
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
