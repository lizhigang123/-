/** 
 * @(#)PostController.java 1.0.0 2017年7月14日 上午10:05:49  
 *  
 * Copyright © 2017 善林金融.  All rights reserved.  
 */

package com.bojin.api.web.controller;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.ResourceAccessException;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.alibaba.fastjson.parser.Feature;
import com.bojin.api.common.constants.Constants;
import com.bojin.api.common.entity.Category;
import com.bojin.api.common.entity.Interface;
import com.bojin.api.common.entity.PostLog;
import com.bojin.api.common.entity.Project;
import com.bojin.api.common.entity.ProjectProperties;
import com.bojin.api.common.entity.User;
import com.bojin.api.core.service.PostLogService;
import com.bojin.api.core.service.admin.CategoryService;
import com.bojin.api.core.service.admin.InterfaceService;
import com.bojin.api.core.service.admin.ProjectPropertiesService;
import com.bojin.api.core.service.admin.ProjectService;
import com.bojin.api.web.service.AutoGernerateCodeService;
import com.bojin.api.web.service.HttpPostService;
import com.bojin.api.web.service.ToolService;
import com.bojin.api.web.utils.CommonUtils;

/**
 * 
 * 
 * @author SangLy
 * @version $Revision:1.0.0, $Date: 2017年7月14日 上午10:05:49 $
 */
@Controller
@RequestMapping(value = "/httppost", method = { RequestMethod.GET, RequestMethod.POST })
public class HttpPostController extends BaseController {

	@Autowired
	private InterfaceService interfaceService;
	@Autowired
	private HttpPostService httpPostService;
	@Autowired
	private PostLogService postLogService;
	@Autowired
	private AutoGernerateCodeService autoGernerateCodeService;
	@Autowired
	private ProjectPropertiesService projectPropertiesService;
	@Autowired
	private CategoryService categoryService;
	@Autowired
	private ProjectService projectService;
	@Autowired
	private ToolService toolService;

	/**
	 * SangLy
	 * 
	 * @param model
	 * @param interfaceId
	 * @return
	 */
	@RequestMapping("")
	public String httppost(Model model,String interfaceId, HttpServletRequest request) {
		Interface interfaceEntity = null;
		if (StringUtils.isNotBlank(interfaceId)) {
			interfaceEntity = interfaceService.findOne(interfaceId);
		} else {
			model.addAttribute("interfaceId", null);
			model.addAttribute("interfaceInfo", null);
			// 把接口
			PostLog postLog = postLogService.findFirstByInterfaceIdIsNullOrderByCreateDateDesc();
			if (postLog != null) {
				model.addAttribute("serviceUrl", postLog.getServiceUrl());
				model.addAttribute("requestJson", postLog.getRequestJson());
				model.addAttribute("reponseContent", addSiseForJsonList(postLog.getResponseJson()));
				model.addAttribute("postLogHistory", postLogService.findByInterfaceIdIsNullOrderByCreateDateDesc());
				model.addAttribute("requestHeaders", postLog.getRequestHeaders());
				model.addAttribute("reponseTime", postLog.getReponseTime());
			}
			return "/api/httppost";
		}
		model.addAttribute("interfaceId", interfaceId);
		model.addAttribute("interfaceInfo", interfaceEntity);
		// 类目详情
		Category categoryEntity = categoryService.findOne(interfaceEntity.getCategoryId());
		model.addAttribute("categoryInfo", categoryEntity);
		Project projectEntity = projectService.findOne(categoryEntity.getProjectId());
		model.addAttribute("projectInfo", projectEntity);
		// 一条记录就是一个头信息
		List<ProjectProperties> projectProperties = projectPropertiesService.findFrontList(projectEntity.getId());
		// 从请求历史中获取最后一条请求信息如果没有找到则模拟一个json数据做填充
		PostLog postLog = postLogService.findFirstByInterfaceIdOrderByCreateDateDes(interfaceId);
		if (postLog != null) {
			model.addAttribute("serviceUrl", postLog.getServiceUrl());
			model.addAttribute("requestJson", postLog.getRequestJson());
			model.addAttribute("reponseContent", addSiseForJsonList(postLog.getResponseJson()));
			model.addAttribute("postLogHistory", postLogService.findByInterfaceIdOrderByCreateDateDes(interfaceId));
			model.addAttribute("requestHeaders", postLog.getRequestHeaders());
			model.addAttribute("reponseTime", postLog.getReponseTime());
		} else {
			model.addAttribute("requestJson", autoGernerateCodeService.getRequestJson(interfaceId, true, Constants.PARAM_TYPE_INPUT));
			model.addAttribute("serviceUrl", "http://localhost:8080" + (StringUtils.isNotBlank(interfaceEntity.getUrl()) ? interfaceEntity.getUrl() : ""));
			model.addAttribute("requestHeaders", CollectionUtils.isNotEmpty(projectProperties) ? setRequestHeadders(request, projectProperties).toString() : "{\r\n}");
		}
		return "/api/httppost";
	}
	
	@RequestMapping("/findFirstByInterfaceIdOrderByCreateDateDes")
	@ResponseBody
	public Map<String, Object> findFirstByInterfaceIdOrderByCreateDateDes(Model model, String interfaceId) {
		Map<String, Object> reusltMap = new HashMap<String, Object>();
		if (StringUtils.isNotBlank(interfaceId)) {
			reusltMap.put("data", postLogService.findFirstByInterfaceIdOrderByCreateDateDes(interfaceId));
		} else {
			reusltMap.put("data", postLogService.findFirstByInterfaceIdIsNullOrderByCreateDateDesc());
		}
		return reusltMap;
	}

	private StringBuffer setRequestHeadders(HttpServletRequest request, List<ProjectProperties> projectProperties) {
		StringBuffer stringBuffer = new StringBuffer();
		stringBuffer.append("{\r\n");
		for (int i = 0; i < projectProperties.size(); i++) {
			ProjectProperties projectPropertie = projectProperties.get(i);
			stringBuffer.append("\t\"" + projectPropertie.getName() + "\":");
			switch (projectPropertie.getName()) {
			case "v":
				stringBuffer.append("\"" + CommonUtils.serviceVersion + "\"");
				break;
			case "sign":
				stringBuffer.append("\"" + "\"");
				break;
			case "cv":
				stringBuffer.append("\"" + CommonUtils.getOSName() + "\"");
				break;
			case "di":
				stringBuffer.append("\"" + CommonUtils.getIpMask() + "\"");
				break;
			case "at":
				stringBuffer.append("\"" + "\"");
				break;
			case "ip":
				try {
					stringBuffer.append("\"" + CommonUtils.getIpAddr(request) + "\"");
				} catch (Exception e) {
					stringBuffer.append("\"localhost\"");
				}
				break;
			case "appKey":
				stringBuffer.append("\"" + CommonUtils.appKey + "\"");
				break;
			case "pf":
				stringBuffer.append("\"" + CommonUtils.platform + "\"");
				break;
			case "t":
				stringBuffer.append("\"" + new Date().getTime() + "\"");
				break;
			default:
				stringBuffer.append("\"" + "\"");
				break;
			}
			if (i < projectProperties.size() - 1) {
				stringBuffer.append(",");
			}
			stringBuffer.append("\r\n");
		}
		stringBuffer.append("}\r\n");
		return stringBuffer;
	}

	@RequestMapping("/getRequestMapOrListCode")
	@ResponseBody
	public Map<String, Object> getRequestMapOrListCode(Model model, String requestJson, Boolean needDefaultValue) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		StringBuilder stringBuilder = new StringBuilder();
		toolService.requestJsonParsing(requestJson, stringBuilder, needDefaultValue != null ? needDefaultValue : false);
		if(stringBuilder.length() == 0){
			resultMap.put("data", "目前只支持map格式json的请求");
		}else{
			resultMap.put("data", stringBuilder);
		}
		return resultMap;
	}

	/**
	 * 模拟post请求
	 * 
	 * @author SangLy
	 * @createTime 2017年7月18日 下午5:49:47
	 * @param model
	 * @param requestIp
	 * @param servicePortNumber
	 * @param serviceUrl
	 * @param requestJson
	 * @param session
	 * @param request
	 * @return
	 */
	@RequestMapping("/sendService")
	@ResponseBody
	public Map<String, Object> sendService(Model model, String serviceUrl, String requestJson, HttpSession session, HttpServletRequest request, String interfaceId, String requestHeaders, Long timestamp) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		if (StringUtils.isBlank(serviceUrl)) {
			resultMap.put("data", "请求的服务器地址不能为空");
			return resultMap;
		}
		Map<String, Object> requestHeadersMap = null;
		try {
			requestHeadersMap = JSON.parseObject(requestHeaders, new TypeReference<Map<String, Object>>() {
			});
		} catch (Exception e) {
			resultMap.put("data", "请求头信息结构不正确,请用标准的的json格式");
			return resultMap;
		}
		String requestIp = "";
		String servicePortNumber = "";
		requestIp = getIpFromServiceUrl(serviceUrl);
		servicePortNumber = getPortFromServiceUrl(serviceUrl);
		checkIpAndPort(requestIp, servicePortNumber, resultMap);
		if (StringUtils.isNotBlank(resultMap.get("data") != null ? resultMap.get("data").toString() : "")) {
			return resultMap;
		}
		session.setAttribute("serviceUrl", serviceUrl);
		// 如果访问的地址不包括http:// 则进行地址补全
		if (!serviceUrl.startsWith("http://") && !serviceUrl.startsWith("https://")) {
			serviceUrl = "http://" + serviceUrl;
		}
		// 如果是localhost则获取访问主机的ip
		String tempIp = "";
		String tempServiceUrl = "";
		try {
			tempIp = requestIp;
			tempServiceUrl = serviceUrl;
			// 如果ip是localhost则把serviceUrl中的替换成请求主机的ip
			if ("localhost".equals(requestIp)) {
				requestIp = CommonUtils.getIpAddr(request);
				serviceUrl = serviceUrl.replace("localhost", requestIp);
			}
		} catch (Exception e1) {
			resultMap.put("data", "使用localhost获取你的主机ip失败,请使用你本机具体的ip和端口号");
			return resultMap;
		}
		try {
			long requlstStartTime = System.currentTimeMillis(); // 获取开始时间  
	        long requlstEndTime = requlstStartTime; // 获取结束时间 
	        long requlstUseAlltime = 0;
	        String reponseTime = "";
			// 拼接请求地址
			String result = httpPostService.sendService(serviceUrl, requestJson, requestHeadersMap);
			requlstEndTime = System.currentTimeMillis();
			requlstUseAlltime = requlstEndTime - requlstStartTime;
			if(requlstUseAlltime>=1000){
				BigDecimal b1 = new BigDecimal(requlstUseAlltime);   
				BigDecimal b2 = new BigDecimal(1000);   
				reponseTime = b1.divide(b2,3,BigDecimal.ROUND_HALF_UP)+"s";
			}else{
				reponseTime = requlstUseAlltime+"ms";
			}
			resultMap.put("data", addSiseForJsonList(result));
			resultMap.put("reponseTime", reponseTime);
			resultMap.put("code", "000000");
			User user = (User) session.getAttribute("user");
			// 不影响页面显示逻辑try-catch处理
			try {
				postLogService.savePostLog(new PostLog(StringUtils.isBlank(interfaceId) ? null : interfaceId, tempServiceUrl, CommonUtils.getIpAddr(request), requestHeaders, requestJson, result.length()>1000?result.substring(0, 1000)+"......":result, user != null ? user.getName() : "", timestamp != null ? new Date(timestamp) : new Date(),reponseTime));
			} catch (Exception e) {
				logger.error("保存请求日志失败：", e.getMessage());
				return resultMap;
			}
		} catch (HttpClientErrorException e) {
			resultMap.put("data", e.getMessage());
			return resultMap;
		} catch (ResourceAccessException e) {
			if ("localhost".equals(tempIp)) {
				resultMap.put("data", "访问被拒绝，请确认本地服务是否启动和端口号是否正确");
				return resultMap;
			} else {
				resultMap.put("data", "访问被拒绝，请确认请求地址或路径是否正确");
				return resultMap;
			}
		} catch (Exception e) {
			resultMap.put("data", e.getMessage());
			return resultMap;
		}
		return resultMap;
	}

	/**
	 * 对返回的格式化并且添加size()
	 * @author SangLy
	 * @createTime 2017年9月19日 下午7:29:09
	 * @param result
	 * @return
	 */
	private String addSiseForJsonList(String result) {
		String formatResult = CommonUtils.formatJson(result);
		// 解析json中各个数组的大小
		List<Integer> resultListSizeList = resultListSizeParsing(result,new ArrayList<Integer>());
		if(CollectionUtils.isNotEmpty(resultListSizeList)){
			for(int i=1; i<=resultListSizeList.size();i++){
				//查询第n次出现的位置
				int index=CommonUtils.getIndexOf(i,"[",formatResult);
				if(index >= 0){
					String s1=formatResult.substring(0,index-1);
					String s2=formatResult.substring(index+1);
					formatResult=s1+"[size("+resultListSizeList.get(i-1)+")"+s2;
				}
			}
		}
		return formatResult;
	}
	
	/**
	 * SangLy
	 * 
	 * @param model
	 * @param interfaceId
	 * @return
	 */
	@RequestMapping("/httppostLog/{id}")
	public String httppostFindOne(Model model,String interfaceId, @PathVariable("id") String id) {
		PostLog postLog = postLogService.findOne(id);
		Interface interfaceEntity = null;
		if(StringUtils.isNotBlank(interfaceId)){
			interfaceEntity = interfaceService.findOne(postLog.getInterfaceId());
		}
		// 从请求历史中获取最后一条请求信息
		if (postLog != null) {
			model.addAttribute("interfaceId", postLog.getInterfaceId());
			model.addAttribute("interfaceInfo", interfaceEntity);
			model.addAttribute("requestJson", postLog.getRequestJson());
			model.addAttribute("reponseContent", addSiseForJsonList(postLog.getResponseJson()));
			model.addAttribute("serviceUrl", postLog.getServiceUrl());
			if(StringUtils.isNotBlank(interfaceId)){
				model.addAttribute("postLogHistory", postLogService.findByInterfaceIdOrderByCreateDateDes(postLog.getInterfaceId()));
			}else{
				model.addAttribute("postLogHistory", postLogService.findByInterfaceIdIsNullOrderByCreateDateDesc());
			}
			model.addAttribute("requestHeaders", postLog.getRequestHeaders());
			model.addAttribute("reponseTime", postLog.getReponseTime());
		}
		return "/api/httppost";
	}

	/**
	 * SangLy
	 * 
	 * @param model
	 * @param interfaceId
	 * @return
	 */
	@RequestMapping("/deleteHttppostLogById/{id}")
	public String deleteHttppostLogById(Model model, String interfaceId, @PathVariable("id") String id, HttpServletRequest request) {
		PostLog postLog = postLogService.findOne(id);
		if (postLog != null) {
			postLogService.delete(id);
		}
		return httppost(model, interfaceId, request);
	}

	/**
	 * 校验ip和端口号。校验规则是 ip和端口号不能同时为空
	 * 
	 * @author SangLy
	 * @createTime 2017年7月28日 下午3:36:46
	 * @param ip
	 * @param port
	 * @param resultMap
	 * @return
	 */
	public static Map<String, Object> checkIpAndPort(String ip, String port, Map<String, Object> resultMap) {
		// 如果ip和port都是空则表示访问的可能是网址
		if (StringUtils.isBlank(ip) && StringUtils.isBlank(port)) {

		} else {
			if (StringUtils.isNotBlank(ip) && StringUtils.isNotBlank(port)) {
				// 校验ip和端口号
				if (!CommonUtils.ipCheck(ip)) {
					resultMap.put("data", "服务器ip错误");
					return resultMap;
				}
				try {
					Integer.parseInt(port);
				} catch (Exception e) {
					resultMap.put("data", "服务器端口号错误");
					return resultMap;
				}
			} else {
				// 报ip或者端口号错误
				if (StringUtils.isNotBlank(ip)) {
					resultMap.put("data", "服务器ip不能为空");
					return resultMap;
				} else {
					resultMap.put("data", "服务器端口不能为空");
					return resultMap;
				}

			}
		}
		return resultMap;
	}

	public static String getIpFromServiceUrl(String serviceUrl) {
		String ip = "";
		if (serviceUrl.contains("localhost")) {
			ip = "localhost";
		} else {
			// 这种包括正常ip带端口和网址的
			if (serviceUrl.startsWith("http://")) {
				// indexOf第二个参数的意思是从哪个位置开始搜索第一次出现的位置
				if (serviceUrl.indexOf("/", 7) > 0) {
					if (serviceUrl.indexOf(":", 7) > 0) {
						ip = serviceUrl.substring(7, serviceUrl.indexOf(":", 7));
					}
				}
			}
			if (serviceUrl.startsWith("https://")) {
				if (serviceUrl.indexOf("/", 8) > 0) {
					if (serviceUrl.indexOf(":", 8) > 0) {
						ip = serviceUrl.substring(8, serviceUrl.indexOf(":", 8));
					}
				}
			}
			if (!serviceUrl.startsWith("http://") && !serviceUrl.startsWith("https://")) {
				if (serviceUrl.indexOf(":", 0) > 0) {
					ip = serviceUrl.substring(0, serviceUrl.indexOf(":", 0));
				}
			}
		}
		return ip;
	}

	public static String getPortFromServiceUrl(String serviceUrl) {
		String port = "";
		if (serviceUrl.contains("localhost")) {
			if (serviceUrl.startsWith("http://localhost:")) {
				// indexOf第二个参数的意思是从哪个位置开始搜索第一次出现的位置
				if (serviceUrl.indexOf("/", 17) > 0) {
					port = serviceUrl.substring(17, serviceUrl.indexOf("/", 17));
				} else {
					port = serviceUrl.substring(17);
				}
			}
			if (serviceUrl.startsWith("https://localhost:")) {
				// indexOf第二个参数的意思是从哪个位置开始搜索第一次出现的位置
				if (serviceUrl.indexOf("/", 18) > 0) {
					port = serviceUrl.substring(18, serviceUrl.indexOf("/", 18));
				} else {
					port = serviceUrl.substring(18);
				}
			}
			if (serviceUrl.startsWith("localhost:")) {
				if (serviceUrl.indexOf("/", 1) > 0) {
					port = serviceUrl.substring(10, serviceUrl.indexOf("/", 1));
				} else {
					port = serviceUrl.substring(10);
				}
			}
		} else {
			// 这种包括正常ip带端口和网址的
			if (serviceUrl.startsWith("http://")) {
				// indexOf第二个参数的意思是从哪个位置开始搜索第一次出现的位置
				if (serviceUrl.indexOf("/", 7) > 0) {
					if (serviceUrl.indexOf(":", 7) > 0) {
						if (serviceUrl.indexOf("/", 7) > 0) {
							port = serviceUrl.substring(serviceUrl.indexOf(":", 7) + 1, serviceUrl.indexOf("/", 7));
						}
					}
				} else {
					port = serviceUrl.substring(17);
				}
			}
			if (serviceUrl.startsWith("https://")) {
				if (serviceUrl.indexOf("/", 8) > 0) {
					if (serviceUrl.indexOf(":", 8) > 0) {
						if (serviceUrl.indexOf("/", 8) > 0) {
							port = serviceUrl.substring(serviceUrl.indexOf(":", 8) + 1, serviceUrl.indexOf("/", 8));
						}
					}
				} else {
					port = serviceUrl.substring(17);
				}
			}
			if (!serviceUrl.startsWith("http://") && !serviceUrl.startsWith("https://")) {
				if (serviceUrl.indexOf(":", 0) > 0) {
					if (serviceUrl.indexOf("/", 0) > 0) {
						port = serviceUrl.substring(serviceUrl.indexOf(":", 0) + 1, serviceUrl.indexOf("/", 0));
					}
				}
			}
		}
		return port;
	}
	
	/**
	 * 解析json中数组的大小
	 * 
	 * @author work
	 * @createTime 2017年9月15日 下午8:19:01
	 * @param requestJson
	 * @param stringBuilder
	 * @param needDefaultValue
	 */
	private List<Integer> resultListSizeParsing(String resultJson, List<Integer> listSize) {
		try {
			JSONArray jSONArray = JSONArray.parseArray(resultJson);
			Iterator<Object> it = jSONArray.iterator();
			listSize.add(jSONArray.size());
			while (it.hasNext()) {
				JSONObject ob = (JSONObject) it.next();
				Set<String> itObj = ob.keySet();
				for (String innerKey : itObj) {
					paseResultObj(ob.get(innerKey), listSize);
				}
			}
			return listSize;
		} catch (Exception e) {
			logger.debug("请求返回结果result转JSONArray失败" + e.getMessage());
		}
		try {
			JSONObject ob = JSONObject.parseObject(resultJson, Feature.OrderedField);
			Set<String> itObj = ob.keySet();
			for (String innerKey : itObj) {
				paseResultObj(ob.get(innerKey), listSize);
			}
			return listSize;
		} catch (Exception e) {
			logger.debug("请求返回结果result转JSONObject失败" + e.getMessage());
		}
		return listSize;
	}

	public void paseResultObj(Object obj, List<Integer> listSize) {
		if (obj instanceof List) {
			listResultParsing(obj, listSize);
		} else if (obj instanceof Map) {
			mapResultParsing(obj, listSize);
		}
	}

	/**
	 * 遍历解析请求Json中的Map
	 * 
	 * @author work
	 * @createTime 2017年9月15日 下午8:17:39
	 * @param mapObjectName
	 * @param key
	 * @param obj
	 * @param stringBuilder
	 * @param needDefaultValue
	 */
	private void mapResultParsing(Object obj, List<Integer> listSize) {
		JSONObject ob = (JSONObject) obj;
		Set<String> itObj = ob.keySet();
		for (String innerKey : itObj) {
			paseResultObj(ob.get(innerKey), listSize);
		}
	}

	/**
	 * 遍历解析请求Json中的List
	 * 
	 * @author
	 * @createTime 2017年9月15日 下午8:15:10
	 * @param mapObjectName
	 * @param key
	 * @param obj
	 * @param stringBuilder
	 * @param needDefaultValue
	 */
	private void listResultParsing(Object obj, List<Integer> listSize) {
		JSONArray jSONArray = (JSONArray) obj;
		Iterator<Object> it = jSONArray.iterator();
		listSize.add(jSONArray.size());
		while (it.hasNext()) {
			JSONObject ob = (JSONObject) it.next();
			Set<String> itObj = ob.keySet();
			for (String innerKey : itObj) {
				paseResultObj(ob.get(innerKey), listSize);
			}
		}
	}

}
