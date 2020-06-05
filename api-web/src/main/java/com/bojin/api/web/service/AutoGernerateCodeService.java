/** 
 * @(#)AutoGernerateCode.java 1.0.0 2017年7月14日 下午1:46:19  
 *  
 * Copyright © 2017 善林金融.  All rights reserved.  
 */ 

package com.bojin.api.web.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bojin.api.common.constants.Constants;
import com.bojin.api.common.entity.ParamEntity;
import com.bojin.api.common.util.ParamUtils;
import com.bojin.api.core.service.admin.ParamService;

/**   
 * 
 *  
 * @author  SangLy
 * @version $Revision:1.0.0, $Date: 2017年7月14日 下午1:46:19 $ 
 */
@Service
public class AutoGernerateCodeService {
	
	@Autowired
	private ParamService paramService;
	
	public StringBuilder getRequestJson(String interfaceId,Boolean needDefaultValue,String inputParamOrOutParam){
		StringBuilder stringBuilder = new StringBuilder();
		List<ParamEntity> paramEntityList = paramService.findByInterfaceIdAndTypeAndParentIdIsNullOrderByOrderedAsc(interfaceId, inputParamOrOutParam);
		return getJsonStringBuilderByParamEntityMapOrList(inputParamOrOutParam, new HashMap<String, Object>(), interfaceId, stringBuilder, null, 0, paramEntityList,needDefaultValue);
	}
	
	/**
	 * 
	 * @param interfaceId
	 * @param stringBuilder
	 *            最终 要生成的目标字符串
	 * @param startKeyForMapOrList
	 *            包含Map或者List<String,Object>对象的key
	 * @param numberZiBiaoFu
	 *            制表符个数
	 * @param paramEntityList
	 * @return
	 */
	private StringBuilder getJsonStringBuilderByParamEntityMapOrList(String imputOrOutParm, Object targetMapOrList, String interfaceId, StringBuilder stringBuilder, String startKeyForMapOrList, int numberZiBiaoFu, List<ParamEntity> paramEntityList,Boolean neadDefault) {
		if (numberZiBiaoFu < 0) {
			numberZiBiaoFu = 0;
		}
		if (startKeyForMapOrList != null) {
			for (int i = 0; i < numberZiBiaoFu; i++) {
				stringBuilder.append("\t");
			}
			stringBuilder.append(generateKey(startKeyForMapOrList));
			if (targetMapOrList instanceof List) {
				stringBuilder.append("[");
			}
		}
		if (CollectionUtils.isNotEmpty(paramEntityList)) {
			stringBuilder.append("{");
			stringBuilder.append("\r\n");
			for (int i = 0; i < paramEntityList.size(); i++) {
				ParamEntity paramEntity = paramEntityList.get(i);
				if (StringUtils.isBlank(paramEntity.getDataType().trim()) || StringUtils.isBlank(paramEntity.getName().trim())) {
					continue;
				}
				// 判断是不是日期
				if (ParamUtils.isDate(paramEntity.getName().trim())) {
					getStringJsonResult(numberZiBiaoFu, stringBuilder, paramEntity.getName(), new Date().getTime(), false, neadDefault);// 默认设置为当天日期
				} else if (ParamUtils.isUrlOrPath(paramEntity.getName().trim())) {
					// 1判断是不是Url或者path
					getStringJsonResult(numberZiBiaoFu, stringBuilder, paramEntity.getName(), "https://www.baidu.com/", true, neadDefault); // 默认设置为百度的链接
				} else if (ParamUtils.isTarget("mobile", paramEntity.getName().trim()) || ParamUtils.isTarget("phoneNumber", paramEntity.getName().trim()) || ParamUtils.isTarget("phone", paramEntity.getName().trim())) {
					// 2判断是不是手机
					getStringJsonResult(numberZiBiaoFu, stringBuilder, paramEntity.getName(), "18666666666", true, neadDefault);
				} else if (ParamUtils.isTarget("tel", paramEntity.getName().trim())) {
					// 3判断是不是固定电话
					getStringJsonResult(numberZiBiaoFu, stringBuilder, paramEntity.getName(), "021-9999999", true, neadDefault);
				} else if (ParamUtils.isTarget("identification", paramEntity.getName().trim()) || ParamUtils.isTarget("identityCard", paramEntity.getName().trim()) || ParamUtils.isTarget("idnum", paramEntity.getName().trim())) {
					// 4判断是不是身份证号
					getStringJsonResult(numberZiBiaoFu, stringBuilder, paramEntity.getName(), "412721198912345678", true, neadDefault);
				} else if (ParamUtils.isTarget("sex", paramEntity.getName().trim())) {
					// 5判断是不是性别
					getStringJsonResult(numberZiBiaoFu, stringBuilder, paramEntity.getName(), "男", true, neadDefault);
				} else if (ParamUtils.isTarget("age", paramEntity.getName().trim())) {
					// 6判断是不是年龄
					getStringJsonResult(numberZiBiaoFu, stringBuilder, paramEntity.getName(), 9, false, neadDefault);
				} else if (ParamUtils.isTarget("email", paramEntity.getName().trim())) {
					// 7判断是不是邮箱
					getStringJsonResult(numberZiBiaoFu, stringBuilder, paramEntity.getName(), "issanyang@sina.com", true, neadDefault);
				} else if (ParamUtils.isTarget("bankCard", paramEntity.getName().trim())) {
					// 8判断是不是邮箱
					getStringJsonResult(numberZiBiaoFu, stringBuilder, paramEntity.getName(), "6228480402564890018", true, neadDefault);
				} else if (ParamUtils.isTarget("start", paramEntity.getName().trim())) {
					// 9判断是不是start
					getStringJsonResult(numberZiBiaoFu, stringBuilder, paramEntity.getName(), 0, false, neadDefault);
				} else if (ParamUtils.isTarget("length", paramEntity.getName().trim())) {
					// 9判断是不是length
					getStringJsonResult(numberZiBiaoFu, stringBuilder, paramEntity.getName(), 10, false, neadDefault);
				} else {
					// 常规判断
					if (Constants.PARAM_TYPE_OUTPUT.equals(imputOrOutParm) && "code".equals(paramEntity.getName().trim())) {
						getStringJsonResult(numberZiBiaoFu, stringBuilder, paramEntity.getName(), "000000", true, true);
					} else if (Constants.PARAM_TYPE_OUTPUT.equals(imputOrOutParm) && "message".equals(paramEntity.getName().trim())) {
						getStringJsonResult(numberZiBiaoFu, stringBuilder, paramEntity.getName(), "操作成功", true, true);
					} else if (Constants.PARAM_Bigdecimal.equalsIgnoreCase(paramEntity.getDataType().trim())) {
						getStringJsonResult(numberZiBiaoFu, stringBuilder, paramEntity.getName(), 9.99, false, neadDefault);
					} else if (Constants.PARAM_intIntArray.equalsIgnoreCase(paramEntity.getDataType().trim())) {
						getStringJsonResult(numberZiBiaoFu, stringBuilder, paramEntity.getName(), new int[] {}, false, neadDefault); // 定义一个数组
					} else if (Constants.PARAM_int.equalsIgnoreCase(paramEntity.getDataType().trim())) {
						getStringJsonResult(numberZiBiaoFu, stringBuilder, paramEntity.getName(), 9, false, neadDefault);
					} else if (Constants.PARAM_StringArray.equalsIgnoreCase(paramEntity.getDataType().trim())) {
						getStringJsonResult(numberZiBiaoFu, stringBuilder, paramEntity.getName(), new String[] {}, false, neadDefault);
					} else if (Constants.PARAM_Double.equalsIgnoreCase(paramEntity.getDataType().trim())) {
						getStringJsonResult(numberZiBiaoFu, stringBuilder, paramEntity.getName(), 9.99, false, neadDefault);
					} else if (Constants.PARAM_List.equalsIgnoreCase(paramEntity.getDataType().trim()) || Constants.PARAM_List.equalsIgnoreCase(paramEntity.getDataType().trim())) {
						List<ParamEntity> childParamEntityList = paramService.findByInterfaceIdAndTypeAndParentIdIdOrderByOrderedAsc(interfaceId, imputOrOutParm, paramEntity.getId());
						if (CollectionUtils.isNotEmpty(childParamEntityList)) {
							stringBuilder.append("------生成的不是标准的List<Map<String, Object>>请找接口定义人员确认------");
							getJsonStringBuilderByParamEntityMapOrList(imputOrOutParm, new ArrayList<Map<String, Object>>(), interfaceId, stringBuilder, paramEntity.getName(), numberZiBiaoFu + 1, childParamEntityList, neadDefault);
							stringBuilder.append("]");
						} else {
							getStringJsonResult(numberZiBiaoFu, stringBuilder, paramEntity.getName(), null, false, neadDefault);
						}
					} else if (Constants.PARAM_String.equalsIgnoreCase(paramEntity.getDataType().trim())) {
						getStringJsonResult(numberZiBiaoFu, stringBuilder, paramEntity.getName(), paramEntity.getName(), true, neadDefault);
					} else if (Constants.PARAM_Integer.equalsIgnoreCase(paramEntity.getDataType().trim())) {
						getStringJsonResult(numberZiBiaoFu, stringBuilder, paramEntity.getName(), 9, false, neadDefault);
					} else if (Constants.PARAM_Long.equalsIgnoreCase(paramEntity.getDataType().trim())) {
						getStringJsonResult(numberZiBiaoFu, stringBuilder, paramEntity.getName(), 9, false, neadDefault);
					} else if (Constants.PARAM_Boolean.equalsIgnoreCase(paramEntity.getDataType().trim())) {
						getStringJsonResult(numberZiBiaoFu, stringBuilder, paramEntity.getName(), "true", false, neadDefault);
					} else if (Constants.PARAM_ListMapStringObject.equalsIgnoreCase(paramEntity.getDataType().trim()) || Constants.PARAM_ListMapString_Object.equalsIgnoreCase(paramEntity.getDataType().trim())) {
						List<ParamEntity> childParamEntityList = paramService.findByInterfaceIdAndTypeAndParentIdIdOrderByOrderedAsc(interfaceId, imputOrOutParm, paramEntity.getId());
						if (CollectionUtils.isNotEmpty(childParamEntityList)) {
							getJsonStringBuilderByParamEntityMapOrList(imputOrOutParm, new ArrayList<Map<String, Object>>(), interfaceId, stringBuilder, paramEntity.getName(), numberZiBiaoFu + 1, childParamEntityList, neadDefault);
							stringBuilder.append("]");
						} else {
							getStringJsonResult(numberZiBiaoFu, stringBuilder, paramEntity.getName(), null, false, neadDefault);
						}
					} else if (Constants.PARAM_MapStringObject.equalsIgnoreCase(paramEntity.getDataType().trim()) || Constants.PARAM_MapString_Object.equalsIgnoreCase(paramEntity.getDataType().trim())) {
						List<ParamEntity> childParamEntityList = paramService.findByInterfaceIdAndTypeAndParentIdIdOrderByOrderedAsc(interfaceId, imputOrOutParm, paramEntity.getId());
						if (CollectionUtils.isNotEmpty(childParamEntityList)) {
							getJsonStringBuilderByParamEntityMapOrList(imputOrOutParm, new HashMap<String, Object>(), interfaceId, stringBuilder, paramEntity.getName(), numberZiBiaoFu + 1, childParamEntityList, neadDefault);
						} else {
							getStringJsonResult(numberZiBiaoFu, stringBuilder, paramEntity.getName(), null, false, neadDefault);
						}
					} else if (Constants.PARAM_ListMapStringInteger.equalsIgnoreCase(paramEntity.getDataType().trim())) {
						stringBuilder.append("------生成的不是标准的Map<String, Object>请找接口定义人员确认------");
						List<ParamEntity> childParamEntityList = paramService.findByInterfaceIdAndTypeAndParentIdIdOrderByOrderedAsc(interfaceId, imputOrOutParm, paramEntity.getId());
						if (CollectionUtils.isNotEmpty(childParamEntityList)) {
							getJsonStringBuilderByParamEntityMapOrList(imputOrOutParm, new HashMap<String, Object>(), interfaceId, stringBuilder, paramEntity.getName(), numberZiBiaoFu + 1, childParamEntityList, neadDefault);
						} else {
							getStringJsonResult(numberZiBiaoFu, stringBuilder, paramEntity.getName(), null, false, neadDefault);
						}
					} else {
						getStringJsonResult(numberZiBiaoFu, stringBuilder, paramEntity.getName(), "-------未知数据类型-------", true, neadDefault);
					}
				}
				// 如果不是最后一个请求参数则添加,
				if (i != paramEntityList.size() - 1) {
					stringBuilder.append(",");
				}
				stringBuilder.append("\r\n");
			}
			for (int i = 0; i < numberZiBiaoFu; i++) {
				stringBuilder.append("\t");
			}
			stringBuilder.append("}");
		} else {
			stringBuilder.append("{\r\n}");
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
	private StringBuilder getStringJsonResult(int numberZiBiaoFu, StringBuilder stringBuilder, String key, Object value, Boolean neadQuote,Boolean neadDefault) {
		for (int i = 0; i <= numberZiBiaoFu; i++) {
			stringBuilder.append("\t");
		}
		stringBuilder.append(generateKey(key));
		if (neadDefault != null && neadDefault) {
			if (neadQuote) {
				stringBuilder.append("\"");
				stringBuilder.append(value);// value
				stringBuilder.append("\"");
			} else {
				if (value instanceof int[]) {
					stringBuilder.append("[3,6]");// value
				} else if (value instanceof Integer || value instanceof Double || value instanceof Long || value instanceof BigDecimal) {
					stringBuilder.append(value);// value
				} else if (value instanceof String[]) {
					stringBuilder.append("[" + "\"" + "3396ff66-d225-4b62-9ed9-ac2c5967f2f2" + "\"" + "," + "\"" + "e1c2ace9-5d53-4d1f-9c7b-0747188d52c2" + "\"" + "]");// value
				} else {
					stringBuilder.append(value);// value
				}
			}
		} else {
			stringBuilder.append("null");
		}
		return stringBuilder;
	}
	
	/**
	 * SangLy
	 * 
	 * @param key
	 *            生成目标key
	 * @return
	 */
	private StringBuilder generateKey(String key) {
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append("\"");
		stringBuilder.append(key);// key
		stringBuilder.append("\"");
		stringBuilder.append(":");
		return stringBuilder;
	}
	
}
