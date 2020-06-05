/** 
 * @(#)ToolService.java 1.0.0 2017年11月1日 上午9:58:04  
 *  
 * Copyright © 2017 善林金融.  All rights reserved.  
 */

/**
 * 
 */
package com.bojin.api.web.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.parser.Feature;
import com.bojin.api.common.constants.Constants;
import com.bojin.api.web.utils.CommonUtils;
import com.bojin.api.web.vo.ParsingOfVo;

/**
 * 
 * 
 * @author issanyang@sina.com
 * @version $Revision:1.0.0, $Date: 2017年11月1日 上午9:58:04 $
 */
@Service
public class ToolService {

	protected Logger logger = LoggerFactory.getLogger(this.getClass());

	private String[] PARENT_FOR_KEY = { "i", "j", "k", "m", "n", "o", "p", "q", "r", "s", "t", "u", "v", "w", "x", "y", "z" };

	/**
	 * json 自动生成请求java代码for嵌套中的当前需要用到的变量位置例如i==5中的5
	 */
	private int PARENT_FOR_KEY_COUNT = 0;

	/**
	 * json 自动生成请求java代码中生成制表符个数
	 */
	private int ZIBIAOFU_COUNT = 0;

	/**
	 * 遍历解析请求Json
	 * 
	 * @author issanyang@sina.com
	 * @createTime 2017年9月15日 下午8:19:01
	 * @param requestJson
	 * @param stringBuilder
	 * @param needDefaultValue
	 */
	public void requestJsonParsing(String requestJson, StringBuilder stringBuilder, Boolean needDefaultValue) {
		PARENT_FOR_KEY_COUNT = 0;
		ZIBIAOFU_COUNT = 0;
		List<String> variableNameList = new ArrayList<String>(); // 用户保存以及使用过的Map或者List的变量名，否则生成的代码会报变量被占用
		try {
			JSONArray obj = JSONArray.parseArray(requestJson);
			listParsing(null, "parmList", obj, stringBuilder, needDefaultValue, new ArrayList<String>(), 0, variableNameList);
			return;
		} catch (Exception e) {
		}
		try {
			JSONObject ob = JSONObject.parseObject(requestJson, Feature.OrderedField);
			Set<String> itObj = ob.keySet();
			String requestMap = "parmMap";
			variableNameList.add(requestMap);
			stringBuilder.append("\r\nMap<String, Object> " + requestMap + " = new HashMap<String, Object>();");
			for (String str : itObj) {
				paseObj(requestMap, str, ob.get(str), stringBuilder, needDefaultValue, 0, new HashMap<String, Object>(), variableNameList);
			}
			return;
		} catch (Exception e) {
		}
	}
	
	public List<ParsingOfVo> requestJsonParsingToReveiveVo(String requestJson, Boolean needDefaultValue) {
		List<String> variableNameList = new ArrayList<String>(); // 用户保存以及使用过的Map或者List的变量名，否则生成的代码会报变量被占用
		List<ParsingOfVo> parsingOfVoList = new ArrayList<ParsingOfVo>(); // 用于存放Vo
		try {
			JSONObject ob = JSONObject.parseObject(requestJson, Feature.OrderedField);
			Set<String> itObj = ob.keySet();
			String classVoName = "ParmVo";
			ParsingOfVo parsingOfVo = new ParsingOfVo();
			parsingOfVoList.add(parsingOfVo);
			StringBuilder stringBuilder = new StringBuilder();
			parsingOfVo.setStringBuilder(stringBuilder);
			parsingOfVo.setVoName(classVoName);
			stringBuilder.append("@Data\r\npublic class " + classVoName + " {\r\n");
			for (String str : itObj) {
				paseJsonObj(str, ob.get(str), stringBuilder, needDefaultValue, 0, new HashMap<String, Object>(), variableNameList, parsingOfVoList);
			}
			stringBuilder.append("}");
			stringBuilder.append("\r\n\r\n");
		} catch (Exception e) {
		}
		return parsingOfVoList;
	}
	
	public void paseJsonObj(String key, Object obj, StringBuilder stringBuilder, Boolean needDefaultValue, int numberZiBiaoFu, Object parentObj, List<String> variableNameList, List<ParsingOfVo> parsingOfVoList) {
		String targetDataType = "String";
		if (obj instanceof Integer) {
			targetDataType = Constants.VO_Integer;
		} else if (obj instanceof Long) {
			targetDataType = Constants.VO_Long;
		} else if (obj instanceof BigDecimal) {
			targetDataType = Constants.VO_BigDecimal;
		} else if (obj instanceof Boolean) {
			targetDataType = Constants.Vo_Boolean;
		} else if (obj instanceof Integer[]) {
			targetDataType = Constants.VO_IntegerArray;
		} else if (obj instanceof String[]) {
			targetDataType = Constants.VO_StringArray;
		} else if (obj instanceof List) {
			targetDataType = "List<" + CommonUtils.firstCharToUpCase(key) + "Vo>";
			listJsonParsing(key, obj, needDefaultValue, parentObj, numberZiBiaoFu, variableNameList, parsingOfVoList);
		} else if (obj instanceof Map) {
			targetDataType = CommonUtils.firstCharToUpCase(key) + "Vo";
			mapJsonParsing(key, obj, needDefaultValue, numberZiBiaoFu, parentObj, variableNameList, parsingOfVoList);
		}
		CommonUtils.getLineByTypeAndeAttribute("", targetDataType, key, stringBuilder, false, "String", "000", new StringBuilder(), "", false);
	}

	public void paseObj(String mapObjectName, String key, Object obj, StringBuilder stringBuilder, Boolean needDefaultValue, int numberZiBiaoFu, Object parentObj, List<String> variableNameList) {
		if (obj instanceof List) {
			listParsing(mapObjectName, key, obj, stringBuilder, needDefaultValue, parentObj, numberZiBiaoFu, variableNameList);
		} else if (obj instanceof Map) {
			String mapName = key + "Map";
			mapName = getMapOrListNameFromVarableNameList(variableNameList, mapName);
			stringBuilder.append("\r\nMap<String, Object> " + mapName + " = new HashMap<String, Object>();");
			stringBuilder.append("\r\n" + mapObjectName + ".put(\"" + key + "\", " + mapName + ");");
			mapParsing(mapName, key, obj, stringBuilder, needDefaultValue, numberZiBiaoFu, parentObj, variableNameList);
		} else {
			getLinePutByTypeAndeAttribute(mapObjectName, key, stringBuilder, needDefaultValue, generateDefaultValue(obj), numberZiBiaoFu);
		}
	}

	private void listJsonParsing(String key, Object obj, Boolean needDefaultValue, Object parentObj, int numberZiBiaoFu, List<String> variableNameList, List<ParsingOfVo> parsingOfVoList) {
		JSONArray jSONArray = (JSONArray) obj;
		Iterator<Object> it = jSONArray.iterator();
		if (jSONArray.size() > 0) {
			while (it.hasNext()) {
				JSONObject ob = (JSONObject) it.next();
				Set<String> itObj = ob.keySet();
				String classVoName = CommonUtils.firstCharToUpCase(key)+"Vo";
				ParsingOfVo parsingOfVo = new ParsingOfVo();
				parsingOfVoList.add(parsingOfVo);
				StringBuilder newStringBuilder = new StringBuilder();
				parsingOfVo.setStringBuilder(newStringBuilder);
				parsingOfVo.setVoName(classVoName);
				newStringBuilder.append("@Data\r\npublic class " + classVoName + " {\r\n");
				for (String innerKey : itObj) {
					paseJsonObj(innerKey, ob.get(innerKey), newStringBuilder, needDefaultValue, 0, new HashMap<String, Object>(), variableNameList, parsingOfVoList);
				}
				newStringBuilder.append("}");
				newStringBuilder.append("\r\n\r\n");
			}
			
		}
	}
	
	/**
	 * 遍历解析请求Json中的List
	 * 
	 * @author issanyang@sina.com
	 * @createTime 2017年9月15日 下午8:15:10
	 * @param mapObjectName
	 * @param key
	 * @param obj
	 * @param stringBuilder
	 * @param needDefaultValue
	 */
	private void listParsing(String parentMapObjectName, String key, Object obj, StringBuilder stringBuilder, Boolean needDefaultValue, Object parentObj, int numberZiBiaoFu, List<String> variableNameList) {
		JSONArray jSONArray = (JSONArray) obj;
		Iterator<Object> it = jSONArray.iterator();
		stringBuilder.append("\r\n");
		for (int i = 0; i < numberZiBiaoFu; i++) {
			stringBuilder.append("\t");
		}
		String listName = key;
		if (!key.endsWith("List")) {
			listName = key + "List";
		}
		listName = getMapOrListNameFromVarableNameList(variableNameList, listName);
		stringBuilder.append("List<Map<String, Object>> " + listName + " = new ArrayList<Map<String, Object>>();");
		if (StringUtils.isNotBlank(parentMapObjectName)) {
			stringBuilder.append("\r\n");
			for (int i = 0; i < numberZiBiaoFu; i++) {
				stringBuilder.append("\t");
			}
			stringBuilder.append(parentMapObjectName + "." + "put(\"" + key + "\", " + listName + ")" + ";");
		}
		if (!(parentObj instanceof List)) {
			PARENT_FOR_KEY_COUNT = 0;
			ZIBIAOFU_COUNT = 0;
		} else {
			PARENT_FOR_KEY_COUNT++;
		}
		if (jSONArray.size() > 0) {
			stringBuilder.append("\r\n");
			if (ZIBIAOFU_COUNT != 0) {
				for (int numberZiBiaoFuOfI = 0; numberZiBiaoFuOfI < ZIBIAOFU_COUNT; numberZiBiaoFuOfI++) {
					stringBuilder.append("\t");
				}
			}
			stringBuilder.append("for (int " + PARENT_FOR_KEY[PARENT_FOR_KEY_COUNT] + " = 0; " + PARENT_FOR_KEY[PARENT_FOR_KEY_COUNT] + " < " + jSONArray.size() + "; " + PARENT_FOR_KEY[PARENT_FOR_KEY_COUNT] + "++) {");
			ZIBIAOFU_COUNT++;
			String mapName = key + "Map";
			mapName = getMapOrListNameFromVarableNameList(variableNameList, mapName);
			stringBuilder.append("\r\n");
			for (int i = 0; i < ZIBIAOFU_COUNT; i++) {
				stringBuilder.append("\t");
			}
			stringBuilder.append("Map<String, Object> " + mapName + " = new HashMap<String, Object>();");
			stringBuilder.append("\r\n");
			for (int i = 0; i < ZIBIAOFU_COUNT; i++) {
				stringBuilder.append("\t");
			}
			stringBuilder.append(listName + ".add(" + mapName + ");");
			int i = 0;
			int temZiBiaoFuCount = 0;
			String ifKey = PARENT_FOR_KEY[PARENT_FOR_KEY_COUNT];
			while (it.hasNext()) {
				JSONObject ob = (JSONObject) it.next();
				Set<String> itObj = ob.keySet();
				stringBuilder.append("\r\n");
				for (int numberZiBiaoFuOfI = 0; numberZiBiaoFuOfI < ZIBIAOFU_COUNT; numberZiBiaoFuOfI++) {
					stringBuilder.append("\t");
				}
				stringBuilder.append("if (" + ifKey + " == " + i + ") {");
				temZiBiaoFuCount = ZIBIAOFU_COUNT;
				ZIBIAOFU_COUNT++;
				for (String innerKey : itObj) {
					paseObj(mapName, innerKey, ob.get(innerKey), stringBuilder, needDefaultValue, ZIBIAOFU_COUNT, new ArrayList<Map<String, Object>>(), variableNameList);
				}
				stringBuilder.append("\r\n");
				for (int numberZiBiaoFuOfI = 0; numberZiBiaoFuOfI < numberZiBiaoFu + 1; numberZiBiaoFuOfI++) {
					stringBuilder.append("\t");
				}
				ZIBIAOFU_COUNT = temZiBiaoFuCount;
				stringBuilder.append("}");
				i++;
			}
			stringBuilder.append("\r\n");
			for (int numberZiBiaoFuOfI = 0; numberZiBiaoFuOfI < numberZiBiaoFu; numberZiBiaoFuOfI++) {
				stringBuilder.append("\t");
			}
			stringBuilder.append("}");
		}
	}

	private String getMapOrListNameFromVarableNameList(List<String> variableNameList, String mapOrListName) {
		String tempMapOrListName = mapOrListName;
		if (variableNameList.contains(mapOrListName)) {
			tempMapOrListName = mapOrListName + (CommonUtils.getListKeyCount(variableNameList, mapOrListName) + 1);
		}
		variableNameList.add(mapOrListName);
		return tempMapOrListName;
	}

	/**
	 * 根据默认值返回要设置成map中value中的字符串
	 * 
	 * @author issanyang@sina.com
	 * @createTime 2017年9月15日 下午4:57:42
	 * @param param
	 * @return
	 */
	public String generateDefaultValue(Object param) {

		if (param instanceof String) {
			param = "\"" + param + "\"";
		} else if (param instanceof Long) {
			param = param + "L";
		} else if (param instanceof BigDecimal) {
			param = "new BigDecimal(\""+param+"\")";
		}
		return param != null ? param.toString() : null;
	}
	
	private void mapJsonParsing(String key, Object obj, Boolean needDefaultValue, int numberZiBiaoFu, Object parentObj, List<String> variableNameList, List<ParsingOfVo> parsingOfVoList) {
		JSONObject ob = (JSONObject) obj;
		Set<String> itObj = ob.keySet();
		String targetVoName = CommonUtils.firstCharToUpCase(key)+"Vo";
		ParsingOfVo parsingOfVo = new ParsingOfVo();
		parsingOfVoList.add(parsingOfVo);
		StringBuilder newStringBuilder = new StringBuilder();
		parsingOfVo.setVoName(targetVoName);
		parsingOfVo.setStringBuilder(newStringBuilder);
		newStringBuilder.append("@Data\r\npublic class "+ targetVoName + " {\r\n");
		for (String innerKey : itObj) {
			paseJsonObj(innerKey, ob.get(innerKey), newStringBuilder, needDefaultValue, 0, new HashMap<String, Object>(), variableNameList, parsingOfVoList);
		}
		newStringBuilder.append("}");
		newStringBuilder.append("\r\n\r\n");
	}
	
	/**
	 * 遍历解析请求Json中的Map
	 * 
	 * @author issanyang@sina.com
	 * @createTime 2017年9月15日 下午8:17:39
	 * @param mapObjectName
	 * @param key
	 * @param obj
	 * @param stringBuilder
	 * @param needDefaultValue
	 */
	private void mapParsing(String mapObjectName, String key, Object obj, StringBuilder stringBuilder, Boolean needDefaultValue, int numberZiBiaoFu, Object parentObj, List<String> variableNameList) {
		JSONObject ob = (JSONObject) obj;
		Set<String> itObj = ob.keySet();
		for (String innerKey : itObj) {
			paseObj(mapObjectName, innerKey, ob.get(innerKey), stringBuilder, needDefaultValue, numberZiBiaoFu, parentObj, variableNameList);
		}
	}

	private static StringBuilder getLinePutByTypeAndeAttribute(String requestMap, String key, StringBuilder stringBuilder, Boolean neadDefaultValue, Object defaultValue, int numberZiBiaoFu) {
		stringBuilder.append("\r\n");
		for (int i = 0; i < numberZiBiaoFu; i++) {
			stringBuilder.append("\t");
		}
		if (neadDefaultValue) {
			stringBuilder.append(CommonUtils.firstCharToLowCase(requestMap) + "." + "put(\"" + key + "\", " + defaultValue + ")" + ";");
		} else {
			stringBuilder.append(CommonUtils.firstCharToLowCase(requestMap) + "." + "put(\"" + key + "\", null)" + ";");
		}
		return stringBuilder;
	}
	
	public List<StringBuilder> requestJsonParsingForReveiveVoSetCode(String requestJson, Boolean needDefaultValue) {
		List<String> variableNameList = new ArrayList<String>(); // 用户保存以及使用过的Map或者List的变量名，否则生成的代码会报变量被占用
		List<StringBuilder> stringBuilderList = new ArrayList<StringBuilder>(); // 用于存放Vo
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilderList.add(stringBuilder);
		try {
			JSONObject ob = JSONObject.parseObject(requestJson, Feature.OrderedField);
			Set<String> itObj = ob.keySet();
			String classVoName = "Parm";
			stringBuilder.append("\r\n"+CommonUtils.firstCharToUpCase(classVoName)+"Vo "+CommonUtils.firstCharToLowCase(classVoName)+" = new "+CommonUtils.firstCharToUpCase(classVoName)+"Vo();");
			for (String str : itObj) {
				paseJsonObjForReveiveVoCode(classVoName,str, ob.get(str), stringBuilder, needDefaultValue, 0, new HashMap<String, Object>(), variableNameList);
			}
		} catch (Exception e) {
		}
		return stringBuilderList;
	}
	
	public List<StringBuilder> requestJsonParsingForReveiveVoGetCode(String requestJson, Boolean needDefaultValue) {
		List<String> variableNameList = new ArrayList<String>(); // 用户保存以及使用过的Map或者List的变量名，否则生成的代码会报变量被占用
		List<StringBuilder> stringBuilderList = new ArrayList<StringBuilder>(); // 用于存放Vo
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilderList.add(stringBuilder);
		try {
			JSONObject ob = JSONObject.parseObject(requestJson, Feature.OrderedField);
			Set<String> itObj = ob.keySet();
			String classVoName = "Parm";
			stringBuilder.append("\r\n"+CommonUtils.firstCharToUpCase(classVoName)+"Vo "+CommonUtils.firstCharToLowCase(classVoName)+" = new "+CommonUtils.firstCharToUpCase(classVoName)+"Vo();");
			for (String str : itObj) {
				paseJsonObjForReveiveVoGetCode(classVoName,str, ob.get(str), stringBuilder, needDefaultValue, 0, new HashMap<String, Object>(), variableNameList);
			}
		} catch (Exception e) {
		}
		return stringBuilderList;
	}
	
	public void paseJsonObjForReveiveVoGetCode(String classVoName,String key, Object obj, StringBuilder stringBuilder, Boolean needDefaultValue, int numberZiBiaoFu, Object parentObj, List<String> variableNameList) {
		String targetDataType = "String";
		Object valueIsMapOrList = new String();
		if (obj instanceof Integer) {
			targetDataType = Constants.VO_Integer;
		} else if (obj instanceof Long) {
			obj = obj.toString()+"L";
			targetDataType = Constants.VO_Long;
		} else if (obj instanceof BigDecimal) {
			obj = "new BigDecimal("+"\""+obj.toString()+"\")";
			targetDataType = Constants.VO_BigDecimal;
		} else if (obj instanceof Boolean) {
			targetDataType = Constants.Vo_Boolean;
		} else if (obj instanceof Integer[]) {
			targetDataType = Constants.VO_IntegerArray;
		} else if (obj instanceof String[]) {
			targetDataType = Constants.VO_StringArray;
		}
		if (obj instanceof List) {
			valueIsMapOrList = new ArrayList<>();
			targetDataType = "List<" + CommonUtils.firstCharToUpCase(key) + "Vo>";
			listJsonParsingForReveiveVoGetCode(classVoName, key, obj, needDefaultValue, parentObj, numberZiBiaoFu, variableNameList, stringBuilder);
		} else if (obj instanceof Map) {
			valueIsMapOrList = new HashMap<>();
			targetDataType = CommonUtils.firstCharToUpCase(key) + "Vo";
			mapJsonParsingForReveiveVoGetCode(key, obj, needDefaultValue, numberZiBiaoFu, parentObj, variableNameList, stringBuilder);
		} else {
			if (valueIsMapOrList instanceof Map || valueIsMapOrList instanceof List) {
				obj = key;
			}
			CommonUtils.getLineSetByTypeAndeAttribute(classVoName, "", "", key, stringBuilder, "String".equals(targetDataType) ? "\"" + obj + "\"" : obj + "", "get", needDefaultValue,numberZiBiaoFu);
		}
	}
	
	
	public void paseJsonObjForReveiveVoCode(String classVoName,String key, Object obj, StringBuilder stringBuilder, Boolean needDefaultValue, int numberZiBiaoFu, Object parentObj, List<String> variableNameList) {
		String targetDataType = "String";
		Object valueIsMapOrList = new String();
		if (obj instanceof Integer) {
			targetDataType = Constants.VO_Integer;
		} else if (obj instanceof Long) {
			obj = obj.toString()+"L";
			targetDataType = Constants.VO_Long;
		} else if (obj instanceof BigDecimal) {
			obj = "new BigDecimal("+"\""+obj.toString()+"\")";
			targetDataType = Constants.VO_BigDecimal;
		} else if (obj instanceof Boolean) {
			targetDataType = Constants.Vo_Boolean;
		} else if (obj instanceof Integer[]) {
			targetDataType = Constants.VO_IntegerArray;
		} else if (obj instanceof String[]) {
			targetDataType = Constants.VO_StringArray;
		}
		if (obj instanceof List) {
			valueIsMapOrList = new ArrayList<>();
			targetDataType = "List<" + CommonUtils.firstCharToUpCase(key) + "Vo>";
			listJsonParsingForReveiveVoCode(classVoName, key, obj, needDefaultValue, parentObj, numberZiBiaoFu, variableNameList, stringBuilder);
		} else if (obj instanceof Map) {
			valueIsMapOrList = new HashMap<>();
			targetDataType = CommonUtils.firstCharToUpCase(key) + "Vo";
			mapJsonParsingForReveiveVoCode(key, obj, needDefaultValue, numberZiBiaoFu, parentObj, variableNameList, stringBuilder);
		} else {
			if (valueIsMapOrList instanceof Map || valueIsMapOrList instanceof List) {
				obj = key;
			}
			CommonUtils.getLineSetByTypeAndeAttribute(classVoName, "", targetDataType, key, stringBuilder, "String".equals(targetDataType) ? "\"" + obj + "\"" : obj + "", "set", needDefaultValue,numberZiBiaoFu);
		}
	}
	
	private void mapJsonParsingForReveiveVoCode(String key, Object obj, Boolean needDefaultValue, int numberZiBiaoFu, Object parentObj, List<String> variableNameList,StringBuilder stringBuilder) {
		JSONObject ob = (JSONObject) obj;
		Set<String> itObj = ob.keySet();
		String classVoName = CommonUtils.firstCharToUpCase(key);
		stringBuilder.append("\r\n"+CommonUtils.firstCharToUpCase(classVoName)+"Vo "+CommonUtils.firstCharToLowCase(classVoName)+" = new "+CommonUtils.firstCharToUpCase(classVoName)+"Vo();");
		for (String innerKey : itObj) {
			paseJsonObjForReveiveVoCode(classVoName,innerKey, ob.get(innerKey), stringBuilder, needDefaultValue, 0, new HashMap<String, Object>(), variableNameList);
		}
	}
	
	
	private void mapJsonParsingForReveiveVoGetCode(String key, Object obj, Boolean needDefaultValue, int numberZiBiaoFu, Object parentObj, List<String> variableNameList,StringBuilder stringBuilder) {
		JSONObject ob = (JSONObject) obj;
		Set<String> itObj = ob.keySet();
		String classVoName = CommonUtils.firstCharToUpCase(key);
		stringBuilder.append("\r\n"+CommonUtils.firstCharToUpCase(classVoName)+"Vo "+CommonUtils.firstCharToLowCase(classVoName)+" = new "+CommonUtils.firstCharToUpCase(classVoName)+"Vo();");
		for (String innerKey : itObj) {
			paseJsonObjForReveiveVoGetCode(classVoName,innerKey, ob.get(innerKey), stringBuilder, needDefaultValue, 0, new HashMap<String, Object>(), variableNameList);
		}
	}
	
	
	private void listJsonParsingForReveiveVoGetCode(String parentMapObjectName, String key, Object obj, Boolean needDefaultValue, Object parentObj, int numberZiBiaoFu, List<String> variableNameList,StringBuilder stringBuilder) {
		JSONArray jSONArray = (JSONArray) obj;
		Iterator<Object> it = jSONArray.iterator();
		for (int i = 0; i < numberZiBiaoFu; i++) {
			stringBuilder.append("\t");
		}
		String listName = key;
		if (!key.endsWith("List")) {
			listName = key+"List";
		}
		listName = getMapOrListNameFromVarableNameList(variableNameList, listName);
//		stringBuilder.append("\r\nList<"+CommonUtils.firstCharToUpCase(key)+"Vo> "+CommonUtils.firstCharToLowCase(listName)+" = new ArrayList<"+CommonUtils.firstCharToUpCase(key)+"Vo>();");
		if (StringUtils.isNotBlank(parentMapObjectName)) {
			stringBuilder.append("\r\n");
			for (int i = 0; i < numberZiBiaoFu; i++) {
				stringBuilder.append("\t");
			}
			stringBuilder.append(CommonUtils.firstCharToLowCase(parentMapObjectName) + ".get"+CommonUtils.firstCharToUpCase(key)+"();");
		}
		if (!(parentObj instanceof List)) {
			PARENT_FOR_KEY_COUNT = 0;
			ZIBIAOFU_COUNT = 0;
		} else {
			PARENT_FOR_KEY_COUNT++;
		}
		if (jSONArray.size() > 0) {
//			stringBuilder.append("\r\n");
//			if (ZIBIAOFU_COUNT != 0) {
//				for (int numberZiBiaoFuOfI = 0; numberZiBiaoFuOfI < ZIBIAOFU_COUNT; numberZiBiaoFuOfI++) {
//					stringBuilder.append("\t");
//				}
//			}
//			stringBuilder.append("for (int " + PARENT_FOR_KEY[PARENT_FOR_KEY_COUNT] + " = 0; " + PARENT_FOR_KEY[PARENT_FOR_KEY_COUNT] + " < " + jSONArray.size() + "; " + PARENT_FOR_KEY[PARENT_FOR_KEY_COUNT] + "++) {");
//			ZIBIAOFU_COUNT++;
			String mapName = key;
			mapName = getMapOrListNameFromVarableNameList(variableNameList, mapName);
			stringBuilder.append("\r\n");
//			for (int i = 0; i < ZIBIAOFU_COUNT; i++) {
//				stringBuilder.append("\t");
//			}
			stringBuilder.append(""+CommonUtils.firstCharToUpCase(key)+"Vo "+CommonUtils.firstCharToLowCase(key)+" = new "+CommonUtils.firstCharToUpCase(key)+"Vo();");
//			for (int i = 0; i < ZIBIAOFU_COUNT; i++) {
//				stringBuilder.append("\t");
//			}
//			stringBuilder.append(listName + ".add(" + mapName + ");");
			int i = 0;
			int temZiBiaoFuCount = 0;
			String ifKey = PARENT_FOR_KEY[PARENT_FOR_KEY_COUNT];
			if (it.hasNext()) {
				JSONObject ob = (JSONObject) it.next();
				Set<String> itObj = ob.keySet();
//				stringBuilder.append("\r\n");
//				for (int numberZiBiaoFuOfI = 0; numberZiBiaoFuOfI < ZIBIAOFU_COUNT; numberZiBiaoFuOfI++) {
//					stringBuilder.append("\t");
//				}
//				stringBuilder.append("if (" + ifKey + " == " + i + ") {");
				temZiBiaoFuCount = ZIBIAOFU_COUNT;
				ZIBIAOFU_COUNT=0;
				for (String innerKey : itObj) {
					paseJsonObjForReveiveVoGetCode(mapName, innerKey, ob.get(innerKey), stringBuilder, needDefaultValue, ZIBIAOFU_COUNT, new ArrayList<Map<String, Object>>(), variableNameList);
				}
				stringBuilder.append("\r\n");
//				for (int numberZiBiaoFuOfI = 0; numberZiBiaoFuOfI < numberZiBiaoFu + 1; numberZiBiaoFuOfI++) {
//					stringBuilder.append("\t");
//				}
				ZIBIAOFU_COUNT = temZiBiaoFuCount;
//				stringBuilder.append("}");
				i++;
			}
			stringBuilder.append("\r\n");
//			for (int numberZiBiaoFuOfI = 0; numberZiBiaoFuOfI < numberZiBiaoFu; numberZiBiaoFuOfI++) {
//				stringBuilder.append("\t");
//			}
//			stringBuilder.append("}");
		}
	}
	
	/**
	 * 遍历解析请求Json中的List
	 * 
	 * @author issanyang@sina.com
	 * @createTime 2017年9月15日 下午8:15:10
	 * @param mapObjectName
	 * @param key
	 * @param obj
	 * @param stringBuilder
	 * @param needDefaultValue
	 */
	private void listJsonParsingForReveiveVoCode(String parentMapObjectName, String key, Object obj, Boolean needDefaultValue, Object parentObj, int numberZiBiaoFu, List<String> variableNameList,StringBuilder stringBuilder) {
		JSONArray jSONArray = (JSONArray) obj;
		Iterator<Object> it = jSONArray.iterator();
		for (int i = 0; i < numberZiBiaoFu; i++) {
			stringBuilder.append("\t");
		}
		String listName = key;
		if (!key.endsWith("List")) {
			listName = key+"List";
		}
		listName = getMapOrListNameFromVarableNameList(variableNameList, listName);
		stringBuilder.append("\r\nList<"+CommonUtils.firstCharToUpCase(key)+"Vo> "+CommonUtils.firstCharToLowCase(listName)+" = new ArrayList<"+CommonUtils.firstCharToUpCase(key)+"Vo>();");
		if (StringUtils.isNotBlank(parentMapObjectName)) {
			stringBuilder.append("\r\n");
			for (int i = 0; i < numberZiBiaoFu; i++) {
				stringBuilder.append("\t");
			}
			stringBuilder.append(CommonUtils.firstCharToLowCase(parentMapObjectName) + ".set"+CommonUtils.firstCharToUpCase(key)+"(" + listName + ");");
		}
		if (!(parentObj instanceof List)) {
			PARENT_FOR_KEY_COUNT = 0;
			ZIBIAOFU_COUNT = 0;
		} else {
			PARENT_FOR_KEY_COUNT++;
		}
		if (jSONArray.size() > 0) {
			stringBuilder.append("\r\n");
			if (ZIBIAOFU_COUNT != 0) {
				for (int numberZiBiaoFuOfI = 0; numberZiBiaoFuOfI < ZIBIAOFU_COUNT; numberZiBiaoFuOfI++) {
					stringBuilder.append("\t");
				}
			}
			stringBuilder.append("for (int " + PARENT_FOR_KEY[PARENT_FOR_KEY_COUNT] + " = 0; " + PARENT_FOR_KEY[PARENT_FOR_KEY_COUNT] + " < " + jSONArray.size() + "; " + PARENT_FOR_KEY[PARENT_FOR_KEY_COUNT] + "++) {");
			ZIBIAOFU_COUNT++;
			String mapName = key;
			mapName = getMapOrListNameFromVarableNameList(variableNameList, mapName);
			stringBuilder.append("\r\n");
			for (int i = 0; i < ZIBIAOFU_COUNT; i++) {
				stringBuilder.append("\t");
			}
			stringBuilder.append(""+CommonUtils.firstCharToUpCase(key)+"Vo "+CommonUtils.firstCharToLowCase(key)+" = new "+CommonUtils.firstCharToUpCase(key)+"Vo();");
			stringBuilder.append("\r\n");
			for (int i = 0; i < ZIBIAOFU_COUNT; i++) {
				stringBuilder.append("\t");
			}
			stringBuilder.append(listName + ".add(" + mapName + ");");
			int i = 0;
			int temZiBiaoFuCount = 0;
			String ifKey = PARENT_FOR_KEY[PARENT_FOR_KEY_COUNT];
			while (it.hasNext()) {
				JSONObject ob = (JSONObject) it.next();
				Set<String> itObj = ob.keySet();
				stringBuilder.append("\r\n");
				for (int numberZiBiaoFuOfI = 0; numberZiBiaoFuOfI < ZIBIAOFU_COUNT; numberZiBiaoFuOfI++) {
					stringBuilder.append("\t");
				}
				stringBuilder.append("if (" + ifKey + " == " + i + ") {");
				temZiBiaoFuCount = ZIBIAOFU_COUNT;
				ZIBIAOFU_COUNT++;
				for (String innerKey : itObj) {
					paseJsonObjForReveiveVoCode(mapName, innerKey, ob.get(innerKey), stringBuilder, needDefaultValue, ZIBIAOFU_COUNT, new ArrayList<Map<String, Object>>(), variableNameList);
				}
				stringBuilder.append("\r\n");
				for (int numberZiBiaoFuOfI = 0; numberZiBiaoFuOfI < numberZiBiaoFu + 1; numberZiBiaoFuOfI++) {
					stringBuilder.append("\t");
				}
				ZIBIAOFU_COUNT = temZiBiaoFuCount;
				stringBuilder.append("}");
				i++;
			}
			stringBuilder.append("\r\n");
			for (int numberZiBiaoFuOfI = 0; numberZiBiaoFuOfI < numberZiBiaoFu; numberZiBiaoFuOfI++) {
				stringBuilder.append("\t");
			}
			stringBuilder.append("}");
		}
	}
}
