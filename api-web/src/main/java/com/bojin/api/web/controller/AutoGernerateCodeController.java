/** 
 * @(#)AutoGernerateCodeController.java 1.0.0 2017年7月14日 下午2:21:11  
 *  
 * Copyright © 2017 善林金融.  All rights reserved.  
 */ 

package com.bojin.api.web.controller;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bojin.api.common.constants.Constants;
import com.bojin.api.common.entity.Interface;
import com.bojin.api.common.entity.ParamEntity;
import com.bojin.api.common.util.ParamUtils;
import com.bojin.api.common.vo.ChildMapOrList;
import com.bojin.api.core.service.admin.InterfaceService;
import com.bojin.api.core.service.admin.ParamService;
import com.bojin.api.web.service.AutoGernerateCodeService;
import com.bojin.api.web.utils.CommonFileUtils;
import com.bojin.api.web.utils.CommonUtils;
import com.bojin.api.web.vo.ParsingOfVo;

/**   
 * 
 *  
 * @author  SangLy
 * @version $Revision:1.0.0, $Date: 2017年7月14日 下午2:21:11 $ 
 */
@Controller
@RequestMapping(value = "/autoGernerateCode", method = { RequestMethod.GET, RequestMethod.POST })
public class AutoGernerateCodeController extends BaseController{
	
	@Autowired
	private InterfaceService interfaceService;
	@Autowired
	private ParamService paramService;
	@Autowired
	private AutoGernerateCodeService autoGernerateCodeService;
	
	/**
	 * SangLy
	 * 
	 * @param model
	 * @param interfaceId
	 * @return
	 */
	@RequestMapping("/auto/{interfaceId}")
	public String auto(Model model, @PathVariable("interfaceId") String interfaceId) {
		model.addAttribute("interfaceId", interfaceId);
		return "/api/autointerface";
	}
	
	/**
	 * 接口详情首页
	 * 
	 * @param model
	 * @param interId
	 * @return
	 */
	@RequestMapping(value = "getRequestJson")
	@ResponseBody
	public Map<String, Object> getRequestJson(@RequestParam("interfaceId") String interfaceId,@RequestParam("needDefaultValue") Boolean needDefaultValue) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("name", autoGernerateCodeService.getRequestJson(interfaceId, needDefaultValue,Constants.PARAM_TYPE_INPUT));
		return map;
	}

	/**
	 * 接口详情首页
	 * 
	 * @param model
	 * @param interId
	 * @return
	 */
	@RequestMapping(value = "getRequestVo")
	@ResponseBody
	public Map<String, Object> getRequestVo(@RequestParam("interfaceId") String interfaceId,String prefixErrorCode,Boolean needValid,HttpSession session) {
		suffixErrorCodeInt = 0;
		if(StringUtils.isBlank(prefixErrorCode)){
			prefixErrorCode = "000";
		}
		// 接口详情信息
		Interface interfaceEntity = interfaceService.findOne(interfaceId);
		String voName = "ParmVo";
		if (interfaceEntity != null) {
			String str[] = interfaceEntity.getUrl().split("/");
			voName = CommonUtils.firstCharToUpCase(str[str.length - 1]);
		}
		// 查询出第一级别的入参
		List<ParamEntity> paramEntityList = paramService.findByInterfaceIdAndTypeAndParentIdIsNullOrderByOrderedAsc(interfaceId, Constants.PARAM_TYPE_INPUT);
		StringBuilder stringBuilder = new StringBuilder();
		List<ParsingOfVo> parsingOfVoList = new ArrayList<ParsingOfVo>(); //用于存放Vo
		ParsingOfVo parsingOfVo = new ParsingOfVo();
		parsingOfVo.setVoName(voName);
		parsingOfVo.setStringBuilder(stringBuilder);
		parsingOfVoList.add(parsingOfVo);
		StringBuilder errorCodeStringBuilder = new StringBuilder(); //用户存放错误码
		errorCodeStringBuilder.append("#注意--可以在最顶端输入框自定义错误码前三位、默认000开头\r\n");
		getVoStringBuilderByParamEntityMapOrList(interfaceEntity.getName(), voName, interfaceId, paramEntityList, stringBuilder, parsingOfVoList,prefixErrorCode,"",errorCodeStringBuilder,"",needValid);
		// 遍历结果
		StringBuilder result = new StringBuilder();
		for (ParsingOfVo iNparsingOfVo : parsingOfVoList) {
			result.append(iNparsingOfVo.getStringBuilder());
		}
		result.append(errorCodeStringBuilder);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("name", result);
		session.setAttribute("parsingOfVoList", parsingOfVoList);
		session.setAttribute("errorCodeStringBuilder", errorCodeStringBuilder);
		return map;
	}
	
	@SuppressWarnings("unchecked")
	@RequestMapping("/getRequestVoDownload")
	public ResponseEntity<byte[]> getRequestVoDownload(Boolean needValid,HttpSession session) throws IOException {
		List<ParsingOfVo> parsingOfVoList=null;
		if (session.getAttribute("parsingOfVoList") != null) {
			parsingOfVoList = (List<ParsingOfVo>) session.getAttribute("parsingOfVoList");
		} else {
			return null;
		}
		List<File> targetListFile = new ArrayList<File>();
		// 清空文件夹下的所以文件
		CommonFileUtils.deleteDir(CommonFileUtils.path);
		for (ParsingOfVo parsingOfVoFile : parsingOfVoList) {
			targetListFile.add(new File(CommonFileUtils.createFile(parsingOfVoFile.getVoName(), parsingOfVoFile.getStringBuilder() != null ? parsingOfVoFile.getStringBuilder().toString() : "",".java")));
		}
		DateFormat df = new SimpleDateFormat("yyyyMMdd");
		df.format(new Date());
		String tempName = "";
		if(needValid !=null && needValid){
			tempName = "请求对应Vo(对象带校验)" + df.format(new Date()) + ".zip";
		}else{
			tempName = "请求对应Vo(对象不带校验)" + df.format(new Date()) + ".zip";
		}
		//错误码文件
		StringBuilder errorCodeStringBuilder = (StringBuilder)session.getAttribute("errorCodeStringBuilder");
		targetListFile.add(new File(CommonFileUtils.createFile("code_message", errorCodeStringBuilder.toString(),".properties")));
		String path = CommonFileUtils.generateZip(targetListFile, tempName);
		File file = new File(path);
		HttpHeaders headers = new HttpHeaders();
		String fileName = new String(tempName.getBytes("UTF-8"), "iso-8859-1");// 为了解决中文名称乱码问题
		headers.setContentDispositionFormData("attachment", fileName);
		headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
		return new ResponseEntity<byte[]>(FileUtils.readFileToByteArray(file), headers, HttpStatus.CREATED);
	}

	/**
	 * 
	 * @author SangLy
	 * @createTime 2017年5月23日 上午9:02:49
	 * @param noteForVo
	 *            类上面的注释内容
	 * @param classVoName
	 *            类名称
	 * @param interfaceId
	 *            接口id
	 * @param paramEntityList
	 * @param stringBuilder
	 * @param stringBuilderList
	 * @return
	 */
	public StringBuilder getVoStringBuilderByParamEntityMapOrList(String noteForVo, String classVoName, String interfaceId, List<ParamEntity> paramEntityList, StringBuilder stringBuilder, List<ParsingOfVo> parsingOfVoList,String prefixErrorCode,String errorCode,StringBuilder errorCodeStringBuilder,String parentMapOrListNote,Boolean needValid) {
		getNoteForFunctionOrVo(stringBuilder, noteForVo);
		stringBuilder.append("@Data\r\npublic class "+ classVoName + " {\r\n");
		if (CollectionUtils.isNotEmpty(paramEntityList)) {
			for (ParamEntity paramEntity : paramEntityList) {
				String targetDataType = "";
				// 常规判断
				if (Constants.PARAM_Bigdecimal.equalsIgnoreCase(paramEntity.getDataType().trim())) {
					targetDataType = Constants.VO_BigDecimal;
				} else if (Constants.PARAM_intIntArray.equalsIgnoreCase(paramEntity.getDataType().trim())) {
					targetDataType = Constants.VO_IntegerArray;
				} else if (Constants.PARAM_int.equalsIgnoreCase(paramEntity.getDataType().trim()) || Constants.PARAM_Integer.equalsIgnoreCase(paramEntity.getDataType().trim())) {
					targetDataType = Constants.VO_Integer;
				} else if (Constants.PARAM_StringArray.equalsIgnoreCase(paramEntity.getDataType().trim())) {
					targetDataType = Constants.VO_StringArray;
				} else if (Constants.PARAM_Double.equalsIgnoreCase(paramEntity.getDataType().trim())) {
					targetDataType = Constants.VO_BigDecimal;
				} else if (Constants.PARAM_String.equalsIgnoreCase(paramEntity.getDataType().trim())) {
					targetDataType = Constants.VO_String;
				} else if (Constants.PARAM_Long.equalsIgnoreCase(paramEntity.getDataType().trim())) {
					targetDataType = Constants.VO_Long;
				} else if (Constants.PARAM_Boolean.equalsIgnoreCase(paramEntity.getDataType().trim())) {
					targetDataType = Constants.Vo_Boolean;
				} else if (Constants.PARAM_ListMapStringObject.equalsIgnoreCase(paramEntity.getDataType().trim()) || Constants.PARAM_ListMapString_Object.equalsIgnoreCase(paramEntity.getDataType().trim()) || Constants.PARAM_List.equalsIgnoreCase(paramEntity.getDataType().trim()) || Constants.PARAM_List.equalsIgnoreCase(paramEntity.getDataType().trim()) || Constants.PARAM_ListMapStringInteger.equalsIgnoreCase(paramEntity.getDataType().trim())) {
					//生成的类名 如果结尾是s则去掉s
					String targetClassName = paramEntity.getName();
					if(targetClassName.endsWith("s")){
						targetClassName = targetClassName.substring(0,targetClassName.length()-1);
					}
					targetClassName = CommonUtils.firstCharToUpCase(targetClassName)+"Vo";
					targetDataType = "List<" +targetClassName+ ">";
					List<ParamEntity> childParamEntityList = paramService.findByInterfaceIdAndTypeAndParentIdIdOrderByOrderedAsc(interfaceId, Constants.PARAM_TYPE_INPUT, paramEntity.getId());
					ParsingOfVo parsingOfVo = new ParsingOfVo();
					parsingOfVoList.add(parsingOfVo);
					StringBuilder newStringBuilder = new StringBuilder();
					parsingOfVo.setStringBuilder(newStringBuilder);
					parsingOfVo.setVoName(targetClassName);
					getVoStringBuilderByParamEntityMapOrList(paramEntity.getNameDesc(), CommonUtils.firstCharToUpCase(targetClassName), interfaceId, childParamEntityList, newStringBuilder, parsingOfVoList,prefixErrorCode,errorCode,errorCodeStringBuilder,paramEntity.getNameDesc(),needValid);
				} else if (Constants.PARAM_MapStringObject.equalsIgnoreCase(paramEntity.getDataType().trim()) || Constants.PARAM_MapString_Object.equalsIgnoreCase(paramEntity.getDataType().trim())) {
					targetDataType = CommonUtils.firstCharToUpCase(paramEntity.getName())+"Vo";
					List<ParamEntity> childParamEntityList = paramService.findByInterfaceIdAndTypeAndParentIdIdOrderByOrderedAsc(interfaceId, Constants.PARAM_TYPE_INPUT, paramEntity.getId());
					ParsingOfVo parsingOfVo = new ParsingOfVo();
					parsingOfVoList.add(parsingOfVo);
					StringBuilder newStringBuilder = new StringBuilder();
					parsingOfVo.setStringBuilder(newStringBuilder);
					String targetClassName =  CommonUtils.firstCharToUpCase(paramEntity.getName())+"Vo";
					parsingOfVo.setVoName(targetClassName);
					getVoStringBuilderByParamEntityMapOrList(paramEntity.getNameDesc(),targetClassName, interfaceId, childParamEntityList, newStringBuilder, parsingOfVoList,prefixErrorCode,errorCode,errorCodeStringBuilder,paramEntity.getNameDesc(),needValid);
				}
				AutoGernerateCodeController.getLineByTypeAndeAttribute(paramEntity.getNameDesc(), targetDataType, paramEntity.getName(), stringBuilder,"必须".equals(paramEntity.getNeed()),paramEntity.getDataType().trim(),prefixErrorCode,errorCodeStringBuilder,parentMapOrListNote,needValid);
			}
		}
		stringBuilder.append("}");
		stringBuilder.append("\r\n\r\n");
		return stringBuilder;
	}
	
	/**
	 * 根据属性类型和属性名自动生成一行,例如下面的格式
	 *
	 * <pre>
	 		/
	 		*借款ID
	 		*
	 		/
	 		private String loanId
	 * </pre>
	 * 
	 * @param type
	 *            字段类型
	 * @param attribute
	 *            属性名称
	 * @return
	 */
	private static StringBuilder getLineByTypeAndeAttribute(String note, String type, String attribute, StringBuilder stringBuilder,Boolean neadValid,String paramEntityDataType,String prefixErrorCode,StringBuilder errorCodeStringBuilder,String parentMapOrListNote,Boolean needDefaultValue) {
		stringBuilder.append("\t" + "/**");
		stringBuilder.append("\r\n\t * " + note);
		stringBuilder.append("\r\n\t */");
		int temp = suffixErrorCodeInt;
		
		//是否添加校验
		if(needDefaultValue !=null && needDefaultValue){
			if(neadValid != null && neadValid && Constants.PARAM_String.equalsIgnoreCase(paramEntityDataType)){
				if(temp == suffixErrorCodeInt){
					++suffixErrorCodeInt;
				}
				stringBuilder.append("\r\n\t@NotBlank(message = \""+getErrorCode(prefixErrorCode,suffixErrorCodeInt)+"\")");
			}else if(neadValid != null && neadValid){
				if(temp == suffixErrorCodeInt){
					++suffixErrorCodeInt;
				}
				stringBuilder.append("\r\n\t@NotNull(message = \""+getErrorCode(prefixErrorCode,suffixErrorCodeInt)+"\")");
			}
			String targetErrorCodeNote = "";
			if((neadValid != null && neadValid && Constants.PARAM_String.equalsIgnoreCase(paramEntityDataType)) || ((neadValid != null && neadValid))){
				targetErrorCodeNote = StringUtils.isNotBlank(parentMapOrListNote)?parentMapOrListNote+"中":"";
				targetErrorCodeNote = targetErrorCodeNote + note + "不能为空";
			}
			
			if(Constants.PARAM_int.equalsIgnoreCase(paramEntityDataType) || Constants.PARAM_Integer.equalsIgnoreCase(paramEntityDataType) || Constants.PARAM_Long.equalsIgnoreCase(paramEntityDataType)){
				if(temp == suffixErrorCodeInt){
					++suffixErrorCodeInt;
				}
				stringBuilder.append("\r\n\t@Min(value = 0, message = \""+getErrorCode(prefixErrorCode,suffixErrorCodeInt)+"\")");
				stringBuilder.append("\r\n\t@Digits(fraction = 0, integer = 20, message = \""+getErrorCode(prefixErrorCode,suffixErrorCodeInt)+"\")");
				
				if(StringUtils.isNotBlank(targetErrorCodeNote)){
					if(Constants.PARAM_Long.equalsIgnoreCase(paramEntityDataType)){
						targetErrorCodeNote = targetErrorCodeNote+"且必须为Long类型的整数";
					}else{
						targetErrorCodeNote = targetErrorCodeNote+"且必须为整数";
					}
				}else{
					if(Constants.PARAM_Long.equalsIgnoreCase(paramEntityDataType)){
						targetErrorCodeNote = StringUtils.isNotBlank(parentMapOrListNote)?parentMapOrListNote+"中":"";
						targetErrorCodeNote = targetErrorCodeNote+note+"必须为Long类型的整数";
					}else{
						targetErrorCodeNote = StringUtils.isNotBlank(parentMapOrListNote)?parentMapOrListNote+"中":"";
						targetErrorCodeNote = targetErrorCodeNote+note+"必须为整数";
					}
				}
			}else if(Constants.PARAM_Bigdecimal.equalsIgnoreCase(paramEntityDataType)){
				if(temp == suffixErrorCodeInt){
					++suffixErrorCodeInt;
				}
				stringBuilder.append("\r\n\t@Min(value = 0, message = \""+getErrorCode(prefixErrorCode,suffixErrorCodeInt)+"\")");
				stringBuilder.append("\r\n\t@Digits(fraction = 2, integer = 20, message = \""+getErrorCode(prefixErrorCode,suffixErrorCodeInt)+"\")");
				if(StringUtils.isNotBlank(targetErrorCodeNote)){
					targetErrorCodeNote = targetErrorCodeNote+"且必须是小数位数小于或等于2位的数字";
				}else{
					targetErrorCodeNote = StringUtils.isNotBlank(parentMapOrListNote)?parentMapOrListNote+"中":"";
					targetErrorCodeNote = targetErrorCodeNote+note+"必须是小数位数小于或等于2位的数字";
				}
			}
			if(StringUtils.isNotBlank(targetErrorCodeNote)){
				errorCodeStringBuilder.append(getErrorCode(prefixErrorCode,suffixErrorCodeInt)+"="+targetErrorCodeNote+"\r\n");
			}
		}
		
		if (Constants.PARAM_ListMapStringObject.equalsIgnoreCase(paramEntityDataType) || Constants.PARAM_ListMapString_Object.equalsIgnoreCase(paramEntityDataType) || Constants.PARAM_List.equalsIgnoreCase(paramEntityDataType) || Constants.PARAM_List.equalsIgnoreCase(paramEntityDataType) || Constants.PARAM_ListMapStringInteger.equalsIgnoreCase(paramEntityDataType) || Constants.PARAM_MapStringObject.equalsIgnoreCase(paramEntityDataType) || Constants.PARAM_MapString_Object.equalsIgnoreCase(paramEntityDataType)) {
			stringBuilder.append("\r\n\t@Valid");
		}
		stringBuilder.append("\r\n\t" + "private " + type + " " + attribute + ";");
		stringBuilder.append("\r\n");
		return stringBuilder;
	}
	
	/**
	 * 请求数据set属性代码
	 * 
	 * @author SangLy
	 * @createTime 2017年6月20日 上午11:49:25
	 * @param interfaceId
	 * @return
	 */
	@RequestMapping(value = "getRequestSetOrGetCode")
	@ResponseBody
	public Map<String, Object> getRequestSetOrGetCode(@RequestParam("interfaceId") String interfaceId,@RequestParam("setOrget") String setOrget,@RequestParam("needDefaultValue") Boolean needDefaultValue) {

		// 接口详情信息
		Interface interfaceEntity = interfaceService.findOne(interfaceId);
		String voName = "MainVo";
		if (interfaceEntity != null) {
			String str[] = interfaceEntity.getUrl().split("/");
			voName = CommonUtils.firstCharToUpCase(str[str.length - 1]);
		}

		// 查询出第一级别的入参
		List<ParamEntity> paramEntityList = paramService.findByInterfaceIdAndTypeAndParentIdIsNullOrderByOrderedAsc(interfaceId, Constants.PARAM_TYPE_INPUT);
		StringBuilder stringBuilder = new StringBuilder();
		List<StringBuilder> stringBuilderList = new ArrayList<StringBuilder>();
		stringBuilderList.add(stringBuilder);
		stringBuilder.append("\r\n"+voName+"Vo "+CommonUtils.firstCharToLowCase(voName)+" = new "+voName+"Vo();");
		getRequestSetCodeStringBuilderByParamEntityMapOrList(interfaceEntity.getName(), voName, interfaceId, paramEntityList, stringBuilder, stringBuilderList,setOrget,needDefaultValue);
		// 遍历结果
		StringBuilder result = new StringBuilder();
		for (StringBuilder str : stringBuilderList) {
			result.append(str);
		}
		Map<String, Object> map = new HashMap<String, Object>();
		result.append("\r\n\r\n");
		map.put("name", result);
		return map;
	}
	/**
	 * 请求数据put属性代码
	 * 
	 * @author SangLy
	 * @createTime 2017年6月20日 上午11:49:25
	 * @param interfaceId
	 * @return
	 */
	@RequestMapping(value = "getRequestPutCode")
	@ResponseBody
	public Map<String, Object> getRequestPutCode(@RequestParam("interfaceId") String interfaceId,@RequestParam("needDefaultValue") Boolean needDefaultValue) {
		// 接口详情信息
		Interface interfaceEntity = interfaceService.findOne(interfaceId);
		String voName = "MainVo";
		if (interfaceEntity != null) {
			String str[] = interfaceEntity.getUrl().split("/");
			voName = CommonUtils.firstCharToUpCase(str[str.length - 1]);
		}
		// 查询出第一级别的入参
		List<ParamEntity> paramEntityList = paramService.findByInterfaceIdAndTypeAndParentIdIsNullOrderByOrderedAsc(interfaceId, Constants.PARAM_TYPE_INPUT);
		StringBuilder stringBuilder = new StringBuilder();
		List<StringBuilder> stringBuilderList = new ArrayList<StringBuilder>();
		stringBuilderList.add(stringBuilder);
		voName = "requestParams";
		stringBuilder.append("\r\nMap<String,Object> "+voName+" = new HashMap<String,Object>();");
		getRequestPutCodeStringBuilderByParamEntityMapOrList(interfaceEntity.getName(), voName, interfaceId, paramEntityList, stringBuilder, stringBuilderList,needDefaultValue);
		// 遍历结果
		StringBuilder result = new StringBuilder();
		for (StringBuilder str : stringBuilderList) {
			result.append(str);
		}
		Map<String, Object> map = new HashMap<String, Object>();
		result.append("\r\n\r\n");
		map.put("name", result);
		return map;
	}
	
	public StringBuilder getRequestPutCodeStringBuilderByParamEntityMapOrList(String noteForVo, String classVoName, String interfaceId, List<ParamEntity> paramEntityList, StringBuilder stringBuilder, List<StringBuilder> stringBuilderList,Boolean needDefaultValue) {
		if (CollectionUtils.isNotEmpty(paramEntityList)) {
			for (ParamEntity paramEntity : paramEntityList) {
				// 常规判断
				Object  defaultValue = null;
				// 判断是不是日期
				if (ParamUtils.isDate(paramEntity.getName().trim())) {
					paramEntity.setDataType(Constants.VO_Long);
					defaultValue = new Date().getTime()+"L";
				} else if (ParamUtils.isUrlOrPath(paramEntity.getName().trim())) {
					// 1判断是不是Url或者path
					paramEntity.setDataType(Constants.VO_String);
					defaultValue = "\"https://www.baidu.com/\"";
				} else if (ParamUtils.isTarget("mobile", paramEntity.getName().trim()) || ParamUtils.isTarget("phoneNumber", paramEntity.getName().trim()) || ParamUtils.isTarget("phone", paramEntity.getName().trim())) {
					// 2判断是不是手机
					paramEntity.setDataType(Constants.VO_String);
					defaultValue = "\"18666666666\"";
				} else if (ParamUtils.isTarget("tel", paramEntity.getName().trim())) {
					// 3判断是不是固定电话
					paramEntity.setDataType(Constants.VO_String);
					defaultValue = "\"021-9999999\"";
				} else if (ParamUtils.isTarget("identification", paramEntity.getName().trim()) || ParamUtils.isTarget("identityCard", paramEntity.getName().trim()) || ParamUtils.isTarget("idnum", paramEntity.getName().trim())) {
					// 4判断是不是身份证号
					paramEntity.setDataType(Constants.VO_String);
					defaultValue = "\"412721198912345678\"";
				} else if (ParamUtils.isTarget("sex", paramEntity.getName().trim())) {
					// 5判断是不是性别
					paramEntity.setDataType(Constants.VO_String);
					defaultValue = "\"男\"";
				} else if (ParamUtils.isTarget("age", paramEntity.getName().trim())) {
					// 6判断是不是年龄
					paramEntity.setDataType(Constants.VO_Integer);
					defaultValue = 9;
				} else if (ParamUtils.isTarget("email", paramEntity.getName().trim())) {
					// 7判断是不是邮箱
					paramEntity.setDataType(Constants.VO_String);
					defaultValue = "\"issanyang@sina.com\"";
				} else if (ParamUtils.isTarget("bankCard", paramEntity.getName().trim())) {
					// 8判断是不是银行卡
					paramEntity.setDataType(Constants.VO_String);
					defaultValue = "\"6228480402564890018\"";
				} else if (ParamUtils.isTarget("start", paramEntity.getName().trim())) {
					// 9判断是不是start
					paramEntity.setDataType(Constants.VO_Integer);
					defaultValue = 0;
				} else if (ParamUtils.isTarget("length", paramEntity.getName().trim())) {
					// 9判断是不是length
					paramEntity.setDataType(Constants.VO_Integer);
					defaultValue = 10;
				} else {
					if (Constants.PARAM_Bigdecimal.equalsIgnoreCase(paramEntity.getDataType().trim())) {
						paramEntity.setDataType(Constants.VO_BigDecimal);
						defaultValue = "new BigDecimal(\"9.9\")";
					} else if (Constants.PARAM_intIntArray.equalsIgnoreCase(paramEntity.getDataType().trim())) {
						paramEntity.setDataType(Constants.VO_IntegerArray);
						defaultValue = "new int[]{6,12}";
					} else if (Constants.PARAM_int.equalsIgnoreCase(paramEntity.getDataType().trim()) || Constants.PARAM_Integer.equalsIgnoreCase(paramEntity.getDataType().trim())) {
						paramEntity.setDataType(Constants.VO_Integer);
						defaultValue = 9;
					} else if (Constants.PARAM_StringArray.equalsIgnoreCase(paramEntity.getDataType().trim())) {
						paramEntity.setDataType(Constants.VO_StringArray);
						defaultValue = "new String[]{\"str1\",\"str2\"}";
					} else if (Constants.PARAM_Double.equalsIgnoreCase(paramEntity.getDataType().trim())) {
						paramEntity.setDataType(Constants.VO_BigDecimal);
						defaultValue = 9.9;
					} else if (Constants.PARAM_String.equalsIgnoreCase(paramEntity.getDataType().trim())) {
						paramEntity.setDataType(Constants.VO_String);
						defaultValue = "\""+paramEntity.getName().trim()+"\"";
					} else if (Constants.PARAM_Long.equalsIgnoreCase(paramEntity.getDataType().trim())) {
						paramEntity.setDataType(Constants.VO_Long);
						defaultValue = new Date().getTime()+"L";
					} else if (Constants.PARAM_Boolean.equalsIgnoreCase(paramEntity.getDataType().trim())) {
						paramEntity.setDataType(Constants.Vo_Boolean);
						defaultValue = "true";
					} else if (Constants.PARAM_ListMapStringObject.equalsIgnoreCase(paramEntity.getDataType().trim()) || Constants.PARAM_ListMapString_Object.equalsIgnoreCase(paramEntity.getDataType().trim()) || Constants.PARAM_List.equalsIgnoreCase(paramEntity.getDataType().trim()) || Constants.PARAM_List.equalsIgnoreCase(paramEntity.getDataType().trim()) || Constants.PARAM_ListMapStringInteger.equalsIgnoreCase(paramEntity.getDataType().trim())) {
						//生成的类名 如果结尾是s则去掉s
						String targetClassName = paramEntity.getName();
						if(targetClassName.endsWith("s")){
							targetClassName = targetClassName.substring(0,targetClassName.length()-1);
						}
						paramEntity.setDataType("List<" + CommonUtils.firstCharToUpCase(targetClassName) + "Vo>");
						List<ParamEntity> childParamEntityList = paramService.findByInterfaceIdAndTypeAndParentIdIdOrderByOrderedAsc(interfaceId, Constants.PARAM_TYPE_INPUT, paramEntity.getId());
						StringBuilder newStringBuilder = new StringBuilder();
						stringBuilderList.add(newStringBuilder);
						//生产默认值 
						stringBuilder.append("\r\nList<Map<String,Object>> "+CommonUtils.firstCharToLowCase(paramEntity.getName())+" = new ArrayList<Map<String,Object>>();");
						stringBuilder.append("\r\nMap<String,Object> "+CommonUtils.firstCharToLowCase(targetClassName)+" = new HashMap<String,Object>();");
						stringBuilder.append("\r\n"+CommonUtils.firstCharToLowCase(paramEntity.getName())+".add("+CommonUtils.firstCharToLowCase(targetClassName)+");");
						defaultValue = CommonUtils.firstCharToLowCase(paramEntity.getName())+"";
						getRequestPutCodeStringBuilderByParamEntityMapOrList(paramEntity.getNameDesc(), CommonUtils.firstCharToUpCase(targetClassName), interfaceId, childParamEntityList, newStringBuilder, stringBuilderList,needDefaultValue);
					} else if (Constants.PARAM_MapStringObject.equalsIgnoreCase(paramEntity.getDataType().trim()) || Constants.PARAM_MapString_Object.equalsIgnoreCase(paramEntity.getDataType().trim())) {
						paramEntity.setDataType(CommonUtils.firstCharToUpCase(paramEntity.getName())+"Vo");
						List<ParamEntity> childParamEntityList = paramService.findByInterfaceIdAndTypeAndParentIdIdOrderByOrderedAsc(interfaceId, Constants.PARAM_TYPE_INPUT, paramEntity.getId());
						StringBuilder newStringBuilder = new StringBuilder();
						stringBuilderList.add(newStringBuilder);
						stringBuilder.append("\r\nMap<String,Object> "+CommonUtils.firstCharToLowCase(paramEntity.getName())+" = new HashMap<String,Object>();");
						defaultValue = CommonUtils.firstCharToLowCase(paramEntity.getName());
						getRequestPutCodeStringBuilderByParamEntityMapOrList(paramEntity.getNameDesc(), CommonUtils.firstCharToUpCase(paramEntity.getName()), interfaceId, childParamEntityList, newStringBuilder, stringBuilderList,needDefaultValue);
					}
				}
				AutoGernerateCodeController.getLinePutByTypeAndeAttribute(classVoName,paramEntity.getNameDesc(), paramEntity.getDataType(), paramEntity.getName(), stringBuilder,defaultValue,needDefaultValue);
			}
		}
		return stringBuilder;
	}
	
	
	/**
	 * 
	 * @author SangLy
	 * @createTime 2017年5月23日 上午9:02:49
	 * @param noteForVo
	 *            类上面的注释内容
	 * @param classVoName
	 *            类名称
	 * @param interfaceId
	 *            接口id
	 * @param paramEntityList
	 * @param stringBuilder
	 * @param stringBuilderList
	 * @return
	 */
	public StringBuilder getRequestSetCodeStringBuilderByParamEntityMapOrList(String noteForVo, String classVoName, String interfaceId, List<ParamEntity> paramEntityList, StringBuilder stringBuilder, List<StringBuilder> stringBuilderList,String setOrget,Boolean needDefaultValue) {
		if (CollectionUtils.isNotEmpty(paramEntityList)) {
			for (ParamEntity paramEntity : paramEntityList) {
				// 常规判断
				Object  defaultValue = null;
				
				
				// 判断是不是日期
				if (ParamUtils.isDate(paramEntity.getName().trim())) {
					paramEntity.setDataType(Constants.VO_Long);
					defaultValue = new Date().getTime()+"L";
				} else if (ParamUtils.isUrlOrPath(paramEntity.getName().trim())) {
					// 1判断是不是Url或者path
					paramEntity.setDataType(Constants.VO_String);
					defaultValue = "\"https://www.baidu.com/\"";
				} else if (ParamUtils.isTarget("mobile", paramEntity.getName().trim()) || ParamUtils.isTarget("phoneNumber", paramEntity.getName().trim()) || ParamUtils.isTarget("phone", paramEntity.getName().trim())) {
					// 2判断是不是手机
					paramEntity.setDataType(Constants.VO_String);
					defaultValue = "\"18666666666\"";
				} else if (ParamUtils.isTarget("tel", paramEntity.getName().trim())) {
					// 3判断是不是固定电话
					paramEntity.setDataType(Constants.VO_String);
					defaultValue = "\"021-9999999\"";
				} else if (ParamUtils.isTarget("identification", paramEntity.getName().trim()) || ParamUtils.isTarget("identityCard", paramEntity.getName().trim()) || ParamUtils.isTarget("idnum", paramEntity.getName().trim())) {
					// 4判断是不是身份证号
					paramEntity.setDataType(Constants.VO_String);
					defaultValue = "\"412721198912345678\"";
				} else if (ParamUtils.isTarget("sex", paramEntity.getName().trim())) {
					// 5判断是不是性别
					paramEntity.setDataType(Constants.VO_String);
					defaultValue = "\"男\"";
				} else if (ParamUtils.isTarget("age", paramEntity.getName().trim())) {
					// 6判断是不是年龄
					paramEntity.setDataType(Constants.VO_Integer);
					defaultValue = 9;
				} else if (ParamUtils.isTarget("email", paramEntity.getName().trim())) {
					// 7判断是不是邮箱
					paramEntity.setDataType(Constants.VO_String);
					defaultValue = "\"issanyang@sina.com\"";
				} else if (ParamUtils.isTarget("bankCard", paramEntity.getName().trim())) {
					// 8判断是不是银行卡
					paramEntity.setDataType(Constants.VO_String);
					defaultValue = "\"6228480402564890018\"";
				} else if (ParamUtils.isTarget("start", paramEntity.getName().trim())) {
					// 9判断是不是start
					paramEntity.setDataType(Constants.VO_Integer);
					defaultValue = 0;
				} else if (ParamUtils.isTarget("length", paramEntity.getName().trim())) {
					// 9判断是不是length
					paramEntity.setDataType(Constants.VO_Integer);
					defaultValue = 10;
				} else {
					if (Constants.PARAM_Bigdecimal.equalsIgnoreCase(paramEntity.getDataType().trim())) {
						paramEntity.setDataType(Constants.VO_BigDecimal);
						defaultValue = "new BigDecimal(\"9.9\")";
					} else if (Constants.PARAM_intIntArray.equalsIgnoreCase(paramEntity.getDataType().trim())) {
						paramEntity.setDataType(Constants.VO_IntegerArray);
						defaultValue = "new int[]{6,12}";
					} else if (Constants.PARAM_int.equalsIgnoreCase(paramEntity.getDataType().trim()) || Constants.PARAM_Integer.equalsIgnoreCase(paramEntity.getDataType().trim())) {
						paramEntity.setDataType(Constants.VO_Integer);
						defaultValue = 9;
					} else if (Constants.PARAM_StringArray.equalsIgnoreCase(paramEntity.getDataType().trim())) {
						paramEntity.setDataType(Constants.VO_StringArray);
						defaultValue = "new String[]{\"str1\",\"str2\"}";
					} else if (Constants.PARAM_Double.equalsIgnoreCase(paramEntity.getDataType().trim())) {
						paramEntity.setDataType(Constants.VO_BigDecimal);
						defaultValue = 9.9;
					} else if (Constants.PARAM_String.equalsIgnoreCase(paramEntity.getDataType().trim())) {
						paramEntity.setDataType(Constants.VO_String);
						defaultValue = "\""+paramEntity.getName().trim()+"\"";
					} else if (Constants.PARAM_Long.equalsIgnoreCase(paramEntity.getDataType().trim())) {
						paramEntity.setDataType(Constants.VO_Long);
						defaultValue = new Date().getTime()+"L";
					} else if (Constants.PARAM_Boolean.equalsIgnoreCase(paramEntity.getDataType().trim())) {
						paramEntity.setDataType(Constants.Vo_Boolean);
						defaultValue = "true";
					} else if (Constants.PARAM_ListMapStringObject.equalsIgnoreCase(paramEntity.getDataType().trim()) || Constants.PARAM_ListMapString_Object.equalsIgnoreCase(paramEntity.getDataType().trim()) || Constants.PARAM_List.equalsIgnoreCase(paramEntity.getDataType().trim()) || Constants.PARAM_List.equalsIgnoreCase(paramEntity.getDataType().trim()) || Constants.PARAM_ListMapStringInteger.equalsIgnoreCase(paramEntity.getDataType().trim())) {
						//生成的类名 如果结尾是s则去掉s
						String targetClassName = paramEntity.getName();
						if(targetClassName.endsWith("s")){
							targetClassName = targetClassName.substring(0,targetClassName.length()-1);
						}
						paramEntity.setDataType("List<" + CommonUtils.firstCharToUpCase(targetClassName) + "Vo>");
						List<ParamEntity> childParamEntityList = paramService.findByInterfaceIdAndTypeAndParentIdIdOrderByOrderedAsc(interfaceId, Constants.PARAM_TYPE_INPUT, paramEntity.getId());
						StringBuilder newStringBuilder = new StringBuilder();
						stringBuilderList.add(newStringBuilder);
						//生产默认值 
						stringBuilder.append("\r\nList<"+CommonUtils.firstCharToUpCase(targetClassName)+"Vo> "+CommonUtils.firstCharToLowCase(paramEntity.getName())+" = new ArrayList<"+CommonUtils.firstCharToUpCase(targetClassName)+"Vo>();");
						stringBuilder.append("\r\n"+CommonUtils.firstCharToUpCase(targetClassName)+"Vo "+CommonUtils.firstCharToLowCase(targetClassName)+" = new "+CommonUtils.firstCharToUpCase(targetClassName)+"Vo();");
						stringBuilder.append("\r\n"+CommonUtils.firstCharToLowCase(paramEntity.getName())+".add("+CommonUtils.firstCharToLowCase(targetClassName)+");");
						defaultValue = CommonUtils.firstCharToLowCase(paramEntity.getName())+"";
						getRequestSetCodeStringBuilderByParamEntityMapOrList(paramEntity.getNameDesc(), CommonUtils.firstCharToUpCase(targetClassName), interfaceId, childParamEntityList, newStringBuilder, stringBuilderList,setOrget,needDefaultValue);
					} else if (Constants.PARAM_MapStringObject.equalsIgnoreCase(paramEntity.getDataType().trim()) || Constants.PARAM_MapString_Object.equalsIgnoreCase(paramEntity.getDataType().trim())) {
						paramEntity.setDataType(CommonUtils.firstCharToUpCase(paramEntity.getName())+"Vo");
						List<ParamEntity> childParamEntityList = paramService.findByInterfaceIdAndTypeAndParentIdIdOrderByOrderedAsc(interfaceId, Constants.PARAM_TYPE_INPUT, paramEntity.getId());
						StringBuilder newStringBuilder = new StringBuilder();
						stringBuilderList.add(newStringBuilder);
						stringBuilder.append("\r\n"+CommonUtils.firstCharToUpCase(paramEntity.getName())+"Vo "+CommonUtils.firstCharToLowCase(paramEntity.getName())+" = new "+CommonUtils.firstCharToUpCase(paramEntity.getName())+"Vo();");
						defaultValue = CommonUtils.firstCharToLowCase(paramEntity.getName());
						getRequestSetCodeStringBuilderByParamEntityMapOrList(paramEntity.getNameDesc(), CommonUtils.firstCharToUpCase(paramEntity.getName()), interfaceId, childParamEntityList, newStringBuilder, stringBuilderList,setOrget,needDefaultValue);
					}
				}
				CommonUtils.getLineSetByTypeAndeAttribute(classVoName,paramEntity.getNameDesc(), paramEntity.getDataType(), paramEntity.getName(), stringBuilder,defaultValue,setOrget,needDefaultValue,0);
			}
		}
		return stringBuilder;
	}
	
	
	private static StringBuilder getLinePutByTypeAndeAttribute(String classVoName,String note, String type, String attribute, StringBuilder stringBuilder,Object defaultValue,Boolean neadDefaultValue) {
		if(neadDefaultValue){
			stringBuilder.append("\r\n"+CommonUtils.firstCharToLowCase(classVoName)+"."+"put(\""+CommonUtils.firstCharToLowCase(attribute)+"\","+defaultValue+")"+"; //"+type+note);
		}else{
			stringBuilder.append("\r\n"+CommonUtils.firstCharToLowCase(classVoName)+"."+"put(\""+CommonUtils.firstCharToLowCase(attribute)+"\",null)"+"; //"+type+note);
		}
		return stringBuilder;
	}

	/**
	 * 接口详情首页
	 * 
	 * @param model
	 * @param interId
	 * @return
	 */
	@RequestMapping(value = "getReponseJson")
	@ResponseBody
	public Map<String, Object> getReponseJson(@RequestParam("interfaceId") String interfaceId,@RequestParam("needDefaultValue") Boolean needDefaultValue) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("name", autoGernerateCodeService.getRequestJson(interfaceId, needDefaultValue,Constants.PARAM_TYPE_OUTPUT));
		return map;
	}

	/**
	 * 生成返回java模拟数据代码
	 * 
	 * @author SangLy
	 * @createTime 2017年5月22日 下午4:25:00
	 * @param interfaceId
	 * @return
	 */
	@RequestMapping(value = "getGenerateReponseCode")
	@ResponseBody
	public Map<String, Object> getGenerateReponseCode(@RequestParam("interfaceId") String interfaceId) {
		Map<String, Object> map = new HashMap<String, Object>();
		StringBuilder stringBuilder = new StringBuilder();
		// 接口详情信息
		Interface interfaceEntity = interfaceService.findOne(interfaceId);
		String functionName = "functionName"; // 接口的方法名
		int numberZiBiaoFu = 1;
		if (interfaceEntity != null) {
			String str[] = interfaceEntity.getUrl().split("/");
			functionName = str[str.length - 1];
			interfaceEntity.getName();//
			// 生成方法名
			getNoteForFunctionOrVo(stringBuilder, interfaceEntity.getName());
			stringBuilder.append("@RequestMapping(\"/" + functionName + "\")");
			stringBuilder.append("\r\n@ResponseBody");
			stringBuilder.append("\r\npublic ResponseVo " + functionName + "(@RequestBody Map<String, Object> params) {");
			stringBuilder.append("\r\n");
			setZhiBiaoBuCount(stringBuilder, numberZiBiaoFu);
			stringBuilder.append("// TODO 模拟数据");
			stringBuilder.append("\r\n");
		}
		// 查询出第一级别的入参
		String outputParam = Constants.PARAM_TYPE_OUTPUT;
		List<ParamEntity> paramEntityList = paramService.findByInterfaceIdAndTypeAndParentIdIsNullOrderByOrderedAsc(interfaceId, outputParam);
		ParamEntity resultParamEntity = null;
		for (ParamEntity paramEntity : paramEntityList) {
			if ("result".equals(paramEntity.getName())) {
				resultParamEntity = paramEntity;
				break;
			}
		}
		Object targetMapOrList = null;
		List<ChildMapOrList> childMapOrList = new ArrayList<ChildMapOrList>();
		if (resultParamEntity != null) {
			List<ParamEntity> childParamEntityList = paramService.findByInterfaceIdAndTypeAndParentIdIdOrderByOrderedAsc(interfaceId, outputParam, resultParamEntity.getId());
			resultParamEntity.getDataType();
			if (Constants.PARAM_ListMapStringObject.equalsIgnoreCase(resultParamEntity.getDataType().trim()) || Constants.PARAM_ListMapString_Object.equalsIgnoreCase(resultParamEntity.getDataType().trim())) {
				targetMapOrList = new ArrayList<Map<String, Object>>();
			} else if (Constants.PARAM_MapStringObject.equalsIgnoreCase(resultParamEntity.getDataType().trim()) || Constants.PARAM_MapString_Object.equalsIgnoreCase(resultParamEntity.getDataType().trim())) {
				targetMapOrList = new HashMap<String, Object>();
			}
			stringBuilder = getGenerateReponseCodeStringBuilderByParamEntityMapOrList(interfaceEntity.getName(), outputParam, new HashMap<String, Object>(), interfaceId, stringBuilder, functionName, numberZiBiaoFu, childParamEntityList,childMapOrList,null,null);
		}
		if(CollectionUtils.isNotEmpty(childMapOrList)){
			stringBuilder.append("\r\n");
		}
		//TODO   findAllTermByProductName
		for(ChildMapOrList childMapOrListMap : childMapOrList){
			//判断上级Tpye是Map或者List
			if(Constants.DEMODATA_MapString_Object.equals(childMapOrListMap.getParentType()) || Constants.DEMODATA_ListMapStringObject.equals(childMapOrListMap.getParentType())){
				if(Constants.DEMODATA_MapString_Object.equals(childMapOrListMap.getParentType()) || Constants.DEMODATA_ListMapStringObject.equals(childMapOrListMap.getParentType())){
					generateListKeyForReponseCodeLine(stringBuilder,new HashMap<String, Object>(),new ArrayList<Map<String, Object>>(),numberZiBiaoFu,childMapOrListMap.getParentKey(),childMapOrListMap.getThisKey(),childMapOrListMap.getNoteFortargetMapOrList());
				}else if(Constants.DEMODATA_ListMapStringObject.equals(childMapOrListMap.getThisType())){
					generateListKeyForReponseCodeLine(stringBuilder,new HashMap<String, Object>(),null,numberZiBiaoFu,childMapOrListMap.getParentKey(),childMapOrListMap.getThisKey(),childMapOrListMap.getNoteFortargetMapOrList());
				}
			}
			if(Constants.DEMODATA_ListMapStringObject.equals(childMapOrListMap.getThisType())){
				generateListKeyForReponseCodeLine(stringBuilder,new ArrayList<Map<String, Object>>(),null,numberZiBiaoFu,childMapOrListMap.getThisKey(),childMapOrListMap.getThisKey(),childMapOrListMap.getNoteFortargetMapOrList());
			}
		}
		if(targetMapOrList instanceof Map){
			generateReponseVoCodeLine(stringBuilder, new HashMap<String, Object>(),numberZiBiaoFu,functionName);
		}else if(targetMapOrList instanceof List){
			generateReponseVoCodeLine(stringBuilder, new ArrayList<Map<String, Object>>(),numberZiBiaoFu,functionName);
		}else{
			generateReponseVoCodeLine(stringBuilder, null,numberZiBiaoFu,functionName);
		}
		// 生成｝
		stringBuilder.append("}");
		map.put("name", stringBuilder);
		return map;
	}

	/**
	 * java模拟数据代码的字符串
	 * 
	 * @author SangLy
	 * @createTime 2017年5月24日 下午3:14:39
	 * @param noteFortargetMapOrList
	 *            注释(Map或者List后面的注释)
	 * @param imputOrOutParm
	 *            出参还是入参
	 * @param targetMapOrList
	 *            出入的是Map还是List
	 * @param interfaceId
	 *            接口Id
	 * @param stringBuilder
	 *            预生成字符串
	 * @param startKeyForMapOrList
	 *            *Map或者*MapList 中的*
	 * @param numberZiBiaoFu
	 *            制表符个数
	 * @param paramEntityList
	 *            *Map 子参数
	 * @param childMapOrList
	 *            组装Map或者List的关联关系
	 * @param parentKey
	 *            当前 Map或者List的父节点 第一次使用则为null
	 * @param parentType
	 *            当前 Map或者List的父节点的类型是Map还是List 第一次使用则为null
	 * @return
	 */
	private StringBuilder getGenerateReponseCodeStringBuilderByParamEntityMapOrList(String noteFortargetMapOrList, String imputOrOutParm, Object targetMapOrList, String interfaceId, StringBuilder stringBuilder, String startKeyForMapOrList, int numberZiBiaoFu, List<ParamEntity> paramEntityList,List<ChildMapOrList> childMapOrList,String parentKey,String parentType) {
		if (numberZiBiaoFu < 0) {
			numberZiBiaoFu = 0;
		}
		if (startKeyForMapOrList != null) {
			setZhiBiaoBuCount(stringBuilder, numberZiBiaoFu);
			ChildMapOrList childMapOrListInstance = new ChildMapOrList();
			childMapOrListInstance.setParentKey(parentKey);
			childMapOrListInstance.setParentType(parentType);
			childMapOrListInstance.setThisKey(startKeyForMapOrList);
			childMapOrListInstance.setNoteFortargetMapOrList(noteFortargetMapOrList);
			if (targetMapOrList instanceof List) {
				stringBuilder.append("//" + noteFortargetMapOrList);
				stringBuilder.append("\r\n");
				setZhiBiaoBuCount(stringBuilder, numberZiBiaoFu);
				stringBuilder.append("List<Map<String, Object>> " + startKeyForMapOrList + "MapList = new ArrayList<Map<String, Object>>();");
				stringBuilder.append("\r\n");
				setZhiBiaoBuCount(stringBuilder, numberZiBiaoFu);
				stringBuilder.append("Map<String, Object> " + startKeyForMapOrList + "Map = new HashMap<String, Object>();");
				childMapOrListInstance.setThisType(Constants.DEMODATA_ListMapStringObject);
				parentKey = startKeyForMapOrList;
				parentType = Constants.DEMODATA_ListMapStringObject;
			} else if (targetMapOrList instanceof Map) {
				stringBuilder.append("//" + noteFortargetMapOrList);
				stringBuilder.append("\r\n");
				setZhiBiaoBuCount(stringBuilder, numberZiBiaoFu);
				stringBuilder.append("Map<String, Object> " + startKeyForMapOrList + "Map = new HashMap<String, Object>();");
				childMapOrListInstance.setThisType(Constants.DEMODATA_MapString_Object);
				parentKey = startKeyForMapOrList;
				parentType = Constants.DEMODATA_MapString_Object;
			}
			childMapOrList.add(childMapOrListInstance);
		}
		if (CollectionUtils.isNotEmpty(paramEntityList)) {
			stringBuilder.append("\r\n");
			for (int i = 0; i < paramEntityList.size(); i++) {
				ParamEntity paramEntity = paramEntityList.get(i);
				if (StringUtils.isBlank(paramEntity.getDataType().trim()) || StringUtils.isBlank(paramEntity.getName().trim())) {
					continue;
				}
				// 判断是不是日期
				if (ParamUtils.isDate(paramEntity.getName().trim())) {
					getStringGenerateReponseCodeLineResult(numberZiBiaoFu, paramEntity.getNameDesc(), stringBuilder, startKeyForMapOrList, paramEntity.getName(), new Date().getTime()+"L", false);
				} else if (ParamUtils.isUrlOrPath(paramEntity.getName().trim())) {
					// 1判断是不是Url或者path
					getStringGenerateReponseCodeLineResult(numberZiBiaoFu, paramEntity.getNameDesc(), stringBuilder, startKeyForMapOrList, paramEntity.getName(), "https://www.baidu.com/", true); // 默认设置为百度的链接
				} else if (ParamUtils.isTarget("mobile", paramEntity.getName().trim()) || ParamUtils.isTarget("phoneNumber", paramEntity.getName().trim()) || ParamUtils.isTarget("phone", paramEntity.getName().trim())) {
					// 2判断是不是手机
					getStringGenerateReponseCodeLineResult(numberZiBiaoFu, paramEntity.getNameDesc(), stringBuilder, startKeyForMapOrList, paramEntity.getName(), "18666666666", true);
				} else if (ParamUtils.isTarget("tel", paramEntity.getName().trim())) {
					// 3判断是不是固定电话
					getStringGenerateReponseCodeLineResult(numberZiBiaoFu, paramEntity.getNameDesc(), stringBuilder, startKeyForMapOrList, paramEntity.getName(), "021-9999999", true);
				} else if (ParamUtils.isTarget("identification", paramEntity.getName().trim()) || ParamUtils.isTarget("identityCard", paramEntity.getName().trim()) || ParamUtils.isTarget("idnum", paramEntity.getName().trim())) {
					// 4判断是不是身份证号
					getStringGenerateReponseCodeLineResult(numberZiBiaoFu, paramEntity.getNameDesc(), stringBuilder, startKeyForMapOrList, paramEntity.getName(), "412721198912345678", true);
				} else if (ParamUtils.isTarget("sex", paramEntity.getName().trim())) {
					// 5判断是不是性别
					getStringGenerateReponseCodeLineResult(numberZiBiaoFu, paramEntity.getNameDesc(), stringBuilder, startKeyForMapOrList, paramEntity.getName(), "男", true);
				} else if (ParamUtils.isTarget("age", paramEntity.getName().trim())) {
					// 6判断是不是年龄
					getStringGenerateReponseCodeLineResult(numberZiBiaoFu, paramEntity.getNameDesc(), stringBuilder, startKeyForMapOrList, paramEntity.getName(), 9, false);
				} else if (ParamUtils.isTarget("email", paramEntity.getName().trim())) {
					// 7判断是不是邮箱
					getStringGenerateReponseCodeLineResult(numberZiBiaoFu, paramEntity.getNameDesc(), stringBuilder, startKeyForMapOrList, paramEntity.getName(), "issanyang@sina.com", true);
				} else if (ParamUtils.isTarget("bankCard", paramEntity.getName().trim())) {
					// 8判断是不是邮箱
					getStringGenerateReponseCodeLineResult(numberZiBiaoFu, paramEntity.getNameDesc(), stringBuilder, startKeyForMapOrList, paramEntity.getName(), "6228480402564890018", true);
				} else if (ParamUtils.isTarget("start", paramEntity.getName().trim())) {
					// 9判断是不是start
					getStringGenerateReponseCodeLineResult(numberZiBiaoFu, paramEntity.getNameDesc(), stringBuilder, startKeyForMapOrList, paramEntity.getName(), 0, false);
				} else if (ParamUtils.isTarget("length", paramEntity.getName().trim())) {
					// 9判断是不是length
					getStringGenerateReponseCodeLineResult(numberZiBiaoFu, paramEntity.getNameDesc(), stringBuilder, startKeyForMapOrList, paramEntity.getName(), 10, false);
				} else {
					// 常规判断
					if (Constants.PARAM_Bigdecimal.equalsIgnoreCase(paramEntity.getDataType().trim())) {
						getStringGenerateReponseCodeLineResult(numberZiBiaoFu, paramEntity.getNameDesc(), stringBuilder, startKeyForMapOrList, paramEntity.getName(), 9.99, false);
					} else if (Constants.PARAM_intIntArray.equalsIgnoreCase(paramEntity.getDataType().trim())) {
						getStringGenerateReponseCodeLineResult(numberZiBiaoFu, paramEntity.getNameDesc(), stringBuilder, startKeyForMapOrList, paramEntity.getName(), new int[] {}, false);
					} else if (Constants.PARAM_int.equalsIgnoreCase(paramEntity.getDataType().trim())) {
						getStringGenerateReponseCodeLineResult(numberZiBiaoFu, paramEntity.getNameDesc(), stringBuilder, startKeyForMapOrList, paramEntity.getName(), 9, false);
					} else if (Constants.PARAM_StringArray.equalsIgnoreCase(paramEntity.getDataType().trim())) {
						getStringGenerateReponseCodeLineResult(numberZiBiaoFu, paramEntity.getNameDesc(), stringBuilder, startKeyForMapOrList, paramEntity.getName(), new String[] {}, false);
					} else if (Constants.PARAM_Double.equalsIgnoreCase(paramEntity.getDataType().trim())) {
						getStringGenerateReponseCodeLineResult(numberZiBiaoFu, paramEntity.getNameDesc(), stringBuilder, startKeyForMapOrList, paramEntity.getName(), 9.99, false);
					} else if (Constants.PARAM_List.equalsIgnoreCase(paramEntity.getDataType().trim()) || Constants.PARAM_List.equalsIgnoreCase(paramEntity.getDataType().trim())) {
						List<ParamEntity> childParamEntityList = paramService.findByInterfaceIdAndTypeAndParentIdIdOrderByOrderedAsc(interfaceId, imputOrOutParm, paramEntity.getId());
						if (CollectionUtils.isNotEmpty(childParamEntityList)) {
							stringBuilder.append("------生成的不是标准的List<Map<String, Object>>请找接口定义人员确认------");
							getGenerateReponseCodeStringBuilderByParamEntityMapOrList(paramEntity.getNameDesc(), imputOrOutParm, new ArrayList<Map<String, Object>>(), interfaceId, stringBuilder, paramEntity.getName(), numberZiBiaoFu, childParamEntityList,childMapOrList,parentKey,parentType);
						}
					} else if (Constants.PARAM_String.equalsIgnoreCase(paramEntity.getDataType().trim())) {
						getStringGenerateReponseCodeLineResult(numberZiBiaoFu, paramEntity.getNameDesc(), stringBuilder, startKeyForMapOrList, paramEntity.getName(), paramEntity.getName(), true);
					} else if (Constants.PARAM_Integer.equalsIgnoreCase(paramEntity.getDataType().trim())) {
						getStringGenerateReponseCodeLineResult(numberZiBiaoFu, paramEntity.getNameDesc(), stringBuilder, startKeyForMapOrList, paramEntity.getName(), 9, false);
					} else if (Constants.PARAM_Long.equalsIgnoreCase(paramEntity.getDataType().trim())) {
						getStringGenerateReponseCodeLineResult(numberZiBiaoFu, paramEntity.getNameDesc(), stringBuilder, startKeyForMapOrList, paramEntity.getName(), 9, false);
					} else if (Constants.PARAM_Boolean.equalsIgnoreCase(paramEntity.getDataType().trim())) {
						getStringGenerateReponseCodeLineResult(numberZiBiaoFu, paramEntity.getNameDesc(), stringBuilder, startKeyForMapOrList, paramEntity.getName(), true, false);
					} else if (Constants.PARAM_ListMapStringObject.equalsIgnoreCase(paramEntity.getDataType().trim()) || Constants.PARAM_ListMapString_Object.equalsIgnoreCase(paramEntity.getDataType().trim())) {
						List<ParamEntity> childParamEntityList = paramService.findByInterfaceIdAndTypeAndParentIdIdOrderByOrderedAsc(interfaceId, imputOrOutParm, paramEntity.getId());
						if (CollectionUtils.isNotEmpty(childParamEntityList)) {
							getGenerateReponseCodeStringBuilderByParamEntityMapOrList(paramEntity.getNameDesc(), imputOrOutParm, new ArrayList<Map<String, Object>>(), interfaceId, stringBuilder, paramEntity.getName(), numberZiBiaoFu, childParamEntityList,childMapOrList,parentKey,parentType);
						}
					} else if (Constants.PARAM_MapStringObject.equalsIgnoreCase(paramEntity.getDataType().trim()) || Constants.PARAM_MapString_Object.equalsIgnoreCase(paramEntity.getDataType().trim())) {
						List<ParamEntity> childParamEntityList = paramService.findByInterfaceIdAndTypeAndParentIdIdOrderByOrderedAsc(interfaceId, imputOrOutParm, paramEntity.getId());
						if (CollectionUtils.isNotEmpty(childParamEntityList)) {
							getGenerateReponseCodeStringBuilderByParamEntityMapOrList(paramEntity.getNameDesc(), imputOrOutParm, new HashMap<String, Object>(), interfaceId, stringBuilder, paramEntity.getName(), numberZiBiaoFu, childParamEntityList,childMapOrList,parentKey,parentType);
						}
					} else if (Constants.PARAM_ListMapStringInteger.equalsIgnoreCase(paramEntity.getDataType().trim())) {
						stringBuilder.append("------生成的不是标准的Map<String, Object>请找接口定义人员确认------");
						List<ParamEntity> childParamEntityList = paramService.findByInterfaceIdAndTypeAndParentIdIdOrderByOrderedAsc(interfaceId, imputOrOutParm, paramEntity.getId());
						if (CollectionUtils.isNotEmpty(childParamEntityList)) {
							getGenerateReponseCodeStringBuilderByParamEntityMapOrList(paramEntity.getNameDesc(), imputOrOutParm, new HashMap<String, Object>(), interfaceId, stringBuilder, paramEntity.getName(), numberZiBiaoFu, childParamEntityList,childMapOrList,parentKey,parentType);
						}
					} else {
						getStringGenerateReponseCodeLineResult(numberZiBiaoFu, paramEntity.getNameDesc(), stringBuilder, startKeyForMapOrList, paramEntity.getName(), "-------未知数据类型-------", true);
					}
				}
			}

		}
		return stringBuilder;
	}

	/**
	 * @numberZiBiaoFu 制表符个数
	 * @param stringBuilder
	 * @param key
	 * @param value
	 * @param neadQuote=true
	 *            //需要引号
	 * @return
	 */
	private StringBuilder getStringGenerateReponseCodeLineResult(int numberZiBiaoFu, String noteDesc, StringBuilder stringBuilder, String startKeyForMapOrList, String key, Object value, Boolean neadQuote) {
		stringBuilder.append(generateKeyForReponseCodeLine(numberZiBiaoFu, startKeyForMapOrList, key));
		if (neadQuote) {
			stringBuilder.append("\""+value+"\")");// key
		} else {
			if (value instanceof int[]) {
				stringBuilder.append("new Integer[]{3,6}");// value
			} else if (value instanceof Integer || value instanceof Double || value instanceof Long || value instanceof BigDecimal) {
				stringBuilder.append(value);
			} else if (value instanceof String[]) {
				stringBuilder.append("new String[]{\"3396ff66-d225-4b62-9ed9-ac2c5967f2f2\",\"e1c2ace9-5d53-4d1f-9c7b-0747188d52c2\"}");
			} else {
				stringBuilder.append(value);
			}
			stringBuilder.append(")");
		}
		stringBuilder.append("; //" + noteDesc);
		stringBuilder.append("\r\n");
		return stringBuilder;
	}

	/**
	 * 
	 * @author SangLy
	 * @createTime 2017年5月23日 下午4:44:33
	 * @param startKeyForMapOrList
	 *            map.put("auditStatus",; 中的 map
	 * @param key
	 *            map.put("auditStatus",; 中的 auditStatus
	 * @return
	 */
	private StringBuilder generateKeyForReponseCodeLine(int numberZiBiaoFu, String startKeyForMapOrList, String key) {
		StringBuilder stringBuilder = new StringBuilder();
		setZhiBiaoBuCount(stringBuilder, numberZiBiaoFu);
		stringBuilder.append(startKeyForMapOrList + "Map.put(\""+key+"\",");
		return stringBuilder;
	}

	/**
	 * 判断生成*Map.put()还是*MapList.add()
	 * 
	 * @author SangLy
	 * @createTime 2017年5月24日 下午3:11:44
	 * @param stringBuilder
	 * @param targetMapOrList
	 *            生成*Map.put()还是*MapList.add()
	 * @param targetThisTypeMapOrList
	 * 			 主要是 判断生成的*Map.put("key",value) 中的value是Map还是List           
	 * @param numberZiBiaoFu
	 *            制表符个数
	 * @param startKeyForMapOrList
	 *            Map.put() 中的key
	 * @param key
	 * @param noteDesc
	 *            注释
	 * @return
	 */
	private StringBuilder generateListKeyForReponseCodeLine(StringBuilder stringBuilder, Object targetParentTypeMapOrList,Object targetThisTypeMapOrList,int numberZiBiaoFu, String startKeyForMapOrList, String key,String noteDesc) {
		StringBuilder stringBuilderNew = new StringBuilder();
		if (targetParentTypeMapOrList instanceof Map) {
			setZhiBiaoBuCount(stringBuilderNew, numberZiBiaoFu);
			if(targetThisTypeMapOrList instanceof List){
				stringBuilderNew.append(startKeyForMapOrList + "Map.put("+"\""+key+"\","+key+"MapList); //"+ noteDesc+"\r\n");
			}else{
				stringBuilderNew.append(startKeyForMapOrList + "Map.put("+"\""+key+"\","+key+"Map); //"+ noteDesc+"\r\n");
			}
		}else if(targetParentTypeMapOrList instanceof List){
			setZhiBiaoBuCount(stringBuilderNew, numberZiBiaoFu);
			stringBuilderNew.append(startKeyForMapOrList + "MapList.add("+key+"Map); //" + noteDesc+"\r\n");
		}
		stringBuilder.append(stringBuilderNew);
		return stringBuilder;
	}
	
	/**
	 * 生成return ResponseVo.success字符串
	 * 
	 * @author SangLy
	 * @createTime 2017年5月24日 下午3:09:27
	 * @param stringBuilder 
	 * @param targetMapOrList 放入 ResponseVo.success 中是Map还是List
	 * @param numberZiBiaoFu 制表符个数
	 * @param startKeyForMapOrList 需要放入ResponseVo.success的类名前缀
	 * @return
	 */
	private StringBuilder generateReponseVoCodeLine(StringBuilder stringBuilder, Object targetMapOrList,int numberZiBiaoFu, String startKeyForMapOrList) {
		StringBuilder stringBuilderNew = new StringBuilder();
		if (targetMapOrList instanceof Map) {
			setZhiBiaoBuCount(stringBuilderNew, numberZiBiaoFu);
			stringBuilderNew.append("return ResponseVo.success("+startKeyForMapOrList+"Map);\r\n");
		}else if(targetMapOrList instanceof List){
			setZhiBiaoBuCount(stringBuilderNew, numberZiBiaoFu);
			stringBuilderNew.append("return ResponseVo.success("+startKeyForMapOrList+"MapList);\r\n");
		}else{
			setZhiBiaoBuCount(stringBuilderNew, numberZiBiaoFu);
			stringBuilderNew.append("return ResponseVo.success();\r\n");
		}
		stringBuilder.append(stringBuilderNew);
		return stringBuilder;
	}

	/**
	 * 生成制表符个数
	 * 
	 * @author SangLy
	 * @createTime 2017年5月24日 下午3:08:51
	 * @param stringBuilder
	 * @param numberZiBiaoFu
	 * @return
	 */
	private StringBuilder setZhiBiaoBuCount(StringBuilder stringBuilder, int numberZiBiaoFu) {
		for (int i = 0; i < numberZiBiaoFu; i++) {
			stringBuilder.append("\t");
		}
		return stringBuilder;
	}

	/**
	 * 生成类或者方法上面的注释
	 * 
	 * @author SangLy
	 * @createTime 2017年5月22日 下午6:32:23
	 * @param stringBuilder
	 *            需要添加注释的字符串
	 * @param noteValue
	 *            注释值
	 * @return
	 */
	private StringBuilder getNoteForFunctionOrVo(StringBuilder stringBuilder, String noteValue) {
		stringBuilder.append("/**\r\n * " + noteValue+"\r\n */\r\n");
		return stringBuilder;
	}
	
	/**
	 * 生成错误码编号
	 * 
	 * @author SangLy
	 * @createTime 2017年7月7日 上午11:32:44
	 * @param prefixErrorCode
	 *            错误码前缀
	 * @param suffixErrorCodeInt 生成的int值
	 * @return
	 */
	public static String getErrorCode(String prefixErrorCode, int suffixErrorCodeInt) {
		if ((suffixErrorCodeInt + "").length() == 1) {
			return prefixErrorCode + "00" + suffixErrorCodeInt;
		} else if ((suffixErrorCodeInt + "").length() == 2) {
			return prefixErrorCode + "0" + suffixErrorCodeInt;
		}
		return prefixErrorCode + suffixErrorCodeInt;
	}
	
	public static int suffixErrorCodeInt = 0;
}
