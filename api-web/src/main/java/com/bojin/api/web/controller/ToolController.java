/** 
 * @(#)PostController.java 1.0.0 2017年7月14日 上午10:05:49  
 *  
 * Copyright © 2017 善林金融.  All rights reserved.  
 */

package com.bojin.api.web.controller;

import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpSession;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.bojin.api.web.service.ToolService;
import com.bojin.api.web.utils.CommonFileUtils;
import com.bojin.api.web.utils.CommonUtils;
import com.bojin.api.web.vo.ParsingOfEntitieVo;
import com.bojin.api.web.vo.ParsingOfVo;
import com.bojin.api.web.vo.ResponseVo;

/**
 * 
 * @author issanyang@sina.com
 *
 */
@Controller
@RequestMapping(value = "/tool", method = { RequestMethod.GET, RequestMethod.POST })
public class ToolController extends BaseController {

	@Autowired
	private ToolService toolService;

	/**
	 * issanyang@sina.com
	 * 
	 * @param model
	 * @param interfaceId
	 * @return
	 */
	@RequestMapping("")
	public String index() {
		return "/api/tool";
	}

	/**
	 * 格式化json
	 * 
	 * @author issanyang@sina.com
	 * @createTime 2017年10月31日 下午3:58:21
	 * @param model
	 * @param requestContent
	 * @return
	 */
	@RequestMapping("/formatJson")
	@ResponseBody
	public Map<String, Object> formatJson(Model model, String requestContent) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		if (StringUtils.isBlank(requestContent)) {
			resultMap.put("data", "请求内容不能为空");
			return resultMap;
		}
		try {
			JSON.parseObject(requestContent, new TypeReference<Map<String, Object>>() {
			});
		} catch (Exception e) {
			resultMap.put("data", "请求json格式不正确");
			return resultMap;
		}
		resultMap.put("data", CommonUtils.formatJson(requestContent));
		return resultMap;
	}

	@RequestMapping("/generatedEntitie")
	@ResponseBody
	public ResponseVo generatedEntitie(Model model, String requestContent,HttpSession session) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		if (StringUtils.isBlank(requestContent)) {
			return ResponseVo.fail("999999","请求内容不能为空");
		}
		List<String> tempTableContentList = new ArrayList<String>();
		String tempTableContent = "";
		String tempRequestContent = requestContent;
		tempRequestContent = tempRequestContent.toUpperCase();
		while (tempRequestContent.indexOf("CREATE TABLE") != -1) {
			tempRequestContent = tempRequestContent.substring(tempRequestContent.indexOf("CREATE TABLE"));
			// 从requestContent中解析出table 数组
			tempTableContent = tempRequestContent.substring(0, getTabelRightBracketsNum(tempRequestContent) + 1);
			tempTableContentList.add(tempTableContent);
			tempRequestContent = tempRequestContent.substring(getTabelRightBracketsNum(tempRequestContent) + 1);
		}
		List<String> tableNameList = new ArrayList<String>();
		for (String tableContent : tempTableContentList) {
			String tempTableContentString = "";
			tempTableContentString = tableContent.substring(13);
			String tempTableName = ""; // 临时表名
			tempTableName = getTempTableName(tempTableContentString);
			tableNameList.add(tempTableName);
		}
		String tablePrefix = getTablePrefix(tableNameList);// 表前缀，（生成的实体去掉表前缀）
		List<ParsingOfEntitieVo> parsingOfEntitieVoList = new ArrayList<ParsingOfEntitieVo>();
		Set<String> entitieNameSet = new HashSet<String>();
		for (String tableContent : tempTableContentList) {
			String tempTableContentString = "";
			tempTableContentString = tableContent.substring(13);
			if (StringUtils.isBlank(tempTableContentString)) {
				continue;
			}
			String tempTableName = ""; // 临时表名
			String tempLine = ""; // 临时行
			String tempField = "";// 临时字段
			String tempFieldType = ""; // 临时字段类型
			String tempNote = "";// 临时注释
			tempTableName = getTempTableName(tempTableContentString);
			tempNote = getFieldNoteFromRequestContent(requestContent, ("COMMENT ON TABLE " + tempTableName));
			if (StringUtils.isBlank(tempNote)) {
				tempNote = getFieldNoteFromRequestContent(requestContent, ("\"" + tempTableName + "\"" + " IS"));
			}
			if (StringUtils.isBlank(tempNote)) {
				tempNote = getFieldNoteFromRequestContent(requestContent, (tempTableName + "\n  IS"));
			}
			String entitieName = CommonUtils.firstCharToUpCase(formatTempTableName(tempTableName, tablePrefix,entitieNameSet));
			entitieNameSet.add(entitieName);
			ParsingOfEntitieVo parsingOfEntitieVo = new ParsingOfEntitieVo();
			parsingOfEntitieVoList.add(parsingOfEntitieVo);
			parsingOfEntitieVo.setEntitieName(entitieName);
			StringBuilder stringBuilder = new StringBuilder();
			parsingOfEntitieVo.setStringBuilder(stringBuilder);
			stringBuilder.append("/**\r\n * " + tempNote + "\r\n */\r\n");
			stringBuilder.append("//@Entity\r\n");
			stringBuilder.append("//@Table(name = \"" + tempTableName + "\")\r\n");
			stringBuilder.append("//@Data\r\n");
			stringBuilder.append("//@EqualsAndHashCode(callSuper = true)\r\n");
			stringBuilder.append("//@ToString(callSuper = true)\r\n");
			stringBuilder.append("public class " + entitieName + " {\r\n");
			while (!tempLine.equals(")")) {
				tempTableContentString = tempTableContentString.substring(tempTableContentString.indexOf("\n") + 1);
				// 如果tempString.indexOf("\n") = -1 表示文件已经结束
				if (tempTableContentString.indexOf("\n") == -1) {
					break;
				}
				tempLine = tempTableContentString.substring(0, tempTableContentString.indexOf("\n"));
				// 如果认定临时行包含constraint关键字并且包含表名 则认定此行为约束，暂时不做处理
				if ((tempLine.contains("constraint") || tempLine.contains("CONSTRAINT")) && (tempLine.contains("FOREIGN KEY") || tempLine.contains("foreign key") || tempLine.contains("primary key") || tempLine.contains("PRIMARY KEY"))) {
					continue;
				} else {
					// 初始化变量
					tempField = "";// 临时字段
					tempFieldType = ""; // 临时字段类型
					tempNote = "";// 临时注释
					tempField = paseOutField(tempLine);
					tempFieldType = getMatchingType(paseOutFieldType(tempLine));
					tempNote = getFieldNoteFromRequestContent(requestContent, (tempTableName + "." + tempField));
					if (StringUtils.isBlank(tempNote)) {
						tempNote = getFieldNoteFromRequestContent(requestContent, ("\"" + tempTableName + "\".\"" + tempField + "\" IS"));
					}
					if (StringUtils.isBlank(tempNote)) {
						tempNote = getFieldNoteFromRequestContent(requestContent, (tempTableName + "." + tempField + "\n  IS"));
					}
					tempField = CommonUtils.underline2Camel(tempField);
					if (StringUtils.isNotBlank(tempField) && !tempLine.equals(")")) {
						getLineByTypeAndeAttribute(tempNote, tempFieldType, tempField, stringBuilder);
					}
				}
			}
			stringBuilder.append("}");
			stringBuilder.append("\r\n\r\n");
		}
		StringBuilder showStringBuilder = new StringBuilder();
		for (ParsingOfEntitieVo parsingOfEntitieVo : parsingOfEntitieVoList) {
			showStringBuilder.append(parsingOfEntitieVo.getStringBuilder());
		}
		session.setAttribute("parsingOfEntitieVoList", parsingOfEntitieVoList);
		if(StringUtils.isNotBlank(showStringBuilder.toString())){
			return ResponseVo.success(showStringBuilder.toString());
		}else{
			return ResponseVo.fail("999999","此sql中未解析出实体对象");
		}
	}
	
	/**
	 * 建表sql生成实体下载
	 * 
	 * @author 
	 * @createTime 2017年12月20日 下午2:07:10
	 * @param model
	 * @param session
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("/generatedEntitieDownLoad")
	@ResponseBody
	public ResponseEntity<byte[]> generatedEntitieDownLoad(Model model,HttpSession session) throws Exception{
		List<ParsingOfEntitieVo> parsingOfEntitieVoList = null;
		if(session.getAttribute("parsingOfEntitieVoList")!=null){
			parsingOfEntitieVoList= (List<ParsingOfEntitieVo>)session.getAttribute("parsingOfEntitieVoList");
		}
		List<File> targetListFile = new ArrayList<File>();
		// 清空文件夹下的所以文件
		CommonFileUtils.deleteDir(CommonFileUtils.path);
		for (ParsingOfEntitieVo parsingOfEntitieVo : parsingOfEntitieVoList) {
			targetListFile.add(new File(CommonFileUtils.createFile(parsingOfEntitieVo.getEntitieName(), parsingOfEntitieVo.getStringBuilder() != null ? parsingOfEntitieVo.getStringBuilder().toString() : "",".java")));
		}
		DateFormat df = new SimpleDateFormat("yyyyMMdd");
		df.format(new Date());
		String tempName = "建表sql生成实体" + df.format(new Date()) + ".zip";
		String path = CommonFileUtils.generateZip(targetListFile, tempName);
		File file = new File(path);
		HttpHeaders headers = new HttpHeaders();
		String fileName = new String(tempName.getBytes("UTF-8"), "iso-8859-1");// 为了解决中文名称乱码问题
		headers.setContentDispositionFormData("attachment", fileName);
		headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
		return new ResponseEntity<byte[]>(FileUtils.readFileToByteArray(file), headers, HttpStatus.CREATED);
	}

	private String getTempTableName(String tempTableContentString) {
		String tableName = "";
		tempTableContentString = tempTableContentString.substring(0, tempTableContentString.indexOf(" ")).replace("\"", "");
		String charArray[] = tempTableContentString.split("\\.");
		tableName = charArray.length > 1 ? charArray[charArray.length - 1] : charArray[0];
		return getTempFieldNote(tableName);
	}

	private String getTablePrefix(List<String> tableNameList) {
		String tablePrefix = "";
		if (tableNameList.size() > 1) {
			String tableName = tableNameList.get(0);
			char[] firstC = tableName.toCharArray();
			char[] tempC = tableName.toCharArray();
			int eqCount = 0;
			for (int i = 0; i < firstC.length; i++) {
				eqCount = 0;
				for (String tempTableName : tableNameList) {
					tempC = tempTableName.toCharArray();
					try {
						if (firstC[i] == tempC[i]) {
							eqCount++;
						} else {
							break;
						}
					} catch (Exception e) {
						break;
					}
				}
				if (eqCount == tableNameList.size()) {
					tablePrefix = tablePrefix + firstC[i];
				} else {
					break;
				}
			}
		}
		while (StringUtils.isNotBlank(tablePrefix) && !tablePrefix.endsWith("_")) {
			tablePrefix = tablePrefix.substring(0, tablePrefix.length() - 1);
		}
		return tablePrefix;
	}

	/**
	 * 获取 最后一个")"的位置。给一个默认值-1
	 * 
	 * @author issanyang@sina.com
	 * 
	 *         <pre>
		create table SMS_T_CHANNEL  (
			ID                   VARCHAR2(50)                    not null,
			CHANNEL_NAME         VARCHAR2(50)                    not null,
			CHANNEL_CODE         VARCHAR2(50)                    not null,
			CONTACT_PERSON       VARCHAR2(50),
			CONTACT_NUMBER       VARCHAR2(50),
		)
	 *         </pre>
	 */
	private int getTabelRightBracketsNum(String tempRequestContent) {
		if (StringUtils.isNotBlank(tempRequestContent)) {
			// 查找)结尾的的位置
			char charArray[] = tempRequestContent.toCharArray();
			int leftBracketsNum = 0;
			int rightBracketsNum = 0;
			for (int i = 0; i < charArray.length; i++) {
				char c = charArray[i];
				if (c == '(') {
					leftBracketsNum++;
				}
				if (c == ')') {
					rightBracketsNum++;
					if (leftBracketsNum != 0 && (leftBracketsNum == rightBracketsNum)) {
						return i;
					}
				}
			}
		}
		return -1;
	}

	private String formatTempTableName(String tempTableName, String tablePrefix,Set<String> entitieNameSet) {
		String tableName = tempTableName;
		if (StringUtils.isNotBlank(tablePrefix)) {
			return CommonUtils.underline2Camel(tempTableName.substring(tablePrefix.length()));
		} else {
			// 默认取最后一个_后面的内容。例如：CT_T_AT_ADD 取值为ADD
			if (StringUtils.isNotBlank(tempTableName)) {
				String temp[] = tempTableName.split("_");
				tableName = CommonUtils.firstCharToUpCase(temp[temp.length - 1].toLowerCase());
				int i = 1;
				while (entitieNameSet.contains(tableName)) {
					i++;
					tableName = CommonUtils.firstCharToUpCase(temp[temp.length - 1].toLowerCase()) + i;
				}
			}
		}
		return tableName;
	}

	private String getFieldNoteFromRequestContent(String requestContent, String indexOfContent) {
		try {
			String tempRequestContentLine = "";
			String tempRequestContent = "";
			requestContent = requestContent.toUpperCase();
			indexOfContent = indexOfContent.toUpperCase();
			if (requestContent.indexOf(indexOfContent) > 0) {
				tempRequestContent = requestContent.substring(requestContent.indexOf(indexOfContent));
				tempRequestContent = tempRequestContent.substring(indexOfContent.length());
				/**
				 * COMMENT ON COLUMN "CREDIT_ADMIN"."CT_T_AM_PERMISSION"."TYPE"
				 * IS '菜单、按钮、其他'; 判断IS后面是否是换行
				 */
				if (tempRequestContent.indexOf("\n") == -1) {
					tempRequestContentLine = tempRequestContent;
				} else {
					tempRequestContentLine = tempRequestContent.substring(0, tempRequestContent.indexOf("\n") + 1);
				}
				String tempRequestContentLineArray[] = tempRequestContentLine.split(" ");
				if (tempRequestContentLineArray[tempRequestContentLineArray.length - 1].equals("IS\n") || tempRequestContentLineArray[tempRequestContentLineArray.length - 1].equals("\n")) {
					tempRequestContent = tempRequestContent.substring(tempRequestContent.indexOf("\n") + 1);
					// 如果tempString.indexOf("\n") = -1 表示文件最后一行,
					if (tempRequestContent.indexOf("\n") == -1) {
						tempRequestContentLine = tempRequestContent;
					} else {
						tempRequestContentLine = tempRequestContent.substring(0, tempRequestContent.indexOf("\n"));
					}
					return getTempFieldNote(tempRequestContentLine);
				} else {
					return getTempFieldNote(tempRequestContentLine);
				}

			}
			return "";
		} catch (Exception e) {
			logger.error("getFieldNoteFromRequestContent 报错" + e.getMessage());
			return "";
		}
	}

	/**
	 * 从注释行获取注释（去掉前后空格和引号）
	 * 
	 * <pre>
	 *  
	                执行前：'创建人';
	                执行后：创建人
	 * </pre>
	 * 
	 * @author issanyang@sina.com
	 * @createTime 2017年10月31日 上午10:15:29
	 * @param tempRequestContentLine
	 * @return
	 */
	private String getTempFieldNote(String tempRequestContentLine) {
		try {
			if (StringUtils.isNotBlank(tempRequestContentLine) && tempRequestContentLine.length() > 0) {
				while (tempRequestContentLine.startsWith("'") || tempRequestContentLine.startsWith(" ") || tempRequestContentLine.startsWith("\n")) {
					tempRequestContentLine = tempRequestContentLine.substring(1);
				}
				while (tempRequestContentLine.endsWith(";") || tempRequestContentLine.endsWith("'") || tempRequestContentLine.endsWith(" ") || tempRequestContentLine.endsWith("\n") || tempRequestContentLine.endsWith("(")) {
					tempRequestContentLine = tempRequestContentLine.substring(0, tempRequestContentLine.length() - 1);
				}
			}
			return tempRequestContentLine;
		} catch (Exception e) {
			logger.error("getTempFieldNote 报错" + e.getMessage());
			return "";
		}

	}

	/**
	 * 从一行中解析出 字段类型
	 * 
	 * <pre>
	    例如一行是 " LOAN_ID                   VARCHAR2(50)                    not null,"输出的结果是 VARCHAR2(50)
	 * 
	 * </pre>
	 * 
	 * @author issanyang@sina.com
	 * @createTime 2017年10月30日 下午5:30:32
	 * @param content
	 * @return
	 */
	private String paseOutFieldType(String content) {
		char[] chrCharArray;
		chrCharArray = content.toCharArray();
		String field = "";
		boolean jieshu = false;
		int count = 1;
		for (char aa : chrCharArray) {
			if (aa == ' ' && jieshu) {
				if (count > 1) {
					break;
				}
				jieshu = false;
				count++;
			} else {
				if (aa != ' ') {
					if (count > 1) {
						field = field + aa;
					}
					jieshu = true;
				}
			}
		}
		return field;
	}

	/**
	 * 
	 * @author issanyang@sina.com
	 * @createTime 2017年10月30日 下午4:20:24
	 * @param field
	 * @return
	 */
	private String getMatchingType(String field) {

		field = field.toUpperCase();

		if (field.contains("CHAR") || field.contains("VARCHAR") || field.contains("NCHAR") || field.contains("NVARCHA") || field.contains("CLOB")) {
			return "String";
		}
		if (field.contains("DATE") || field.contains("TIMESTAMP")) {
			return "Date";
		}
		if (field.contains("BOOL")) {
			return "Boolean";
		}
		if (field.contains("IS_SETTLE_RELOAN") || field.contains("SMALLINT")) {
			return "Boolean 手动确定是否想要返回Boolean";
		}
		if (field.contains("NUMBER(")) {
			return "BigDecimal";
		}
		if (field.contains("INT8")) {
			return "Long";
		}
		if (field.contains("NUMBER") || field.contains("INTEGER") || field.contains("INT")) {
			return "Integer";
		}
		return "";
	}

	/**
	 * 从一行中解析出 字段
	 * 
	 * <pre>
	    例如一行是 " LOAN_ID                   VARCHAR2(50)                    not null,"输出的结果是 LOAN_ID
	 * 
	 * </pre>
	 * 
	 * @author issanyang@sina.com
	 * @createTime 2017年10月30日 下午5:30:32
	 * @param content
	 * @return
	 */
	private static String paseOutField(String content) {
		char[] chrCharArray;
		chrCharArray = content.toCharArray();
		String field = "";
		boolean jieshu = false;
		for (char aa : chrCharArray) {
			if (aa == ' ' && jieshu) {
				break;
			} else {
				if (aa != ' ' && aa != '\"' && aa != '\'') {
					field = field + aa;
					jieshu = true;
				}
			}

		}
		return field;
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
	private static StringBuilder getLineByTypeAndeAttribute(String note, String type, String attribute, StringBuilder stringBuilder) {
		stringBuilder.append("\t" + "/**");
		stringBuilder.append("\r\n\t * " + note);
		stringBuilder.append("\r\n\t */");
		stringBuilder.append("\r\n\t" + "private " + type + " " + attribute + ";");
		stringBuilder.append("\r\n");
		return stringBuilder;
	}

	@RequestMapping("/getRequestMapOrListCode")
	@ResponseBody
	public Map<String, Object> getRequestMapOrListCode(Model model, String requestContent, Boolean needDefaultValue) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		StringBuilder stringBuilder = new StringBuilder();
		toolService.requestJsonParsing(requestContent, stringBuilder, needDefaultValue != null ? needDefaultValue : false);
		if (stringBuilder.length() == 0) {
			resultMap.put("data", "目前只支持map格式json的请求");
		} else {
			resultMap.put("data", stringBuilder);
		}
		return resultMap;
	}
	
	@RequestMapping("/requestJsonParsingToReveiveVo")
	@ResponseBody
	public ResponseVo requestJsonParsingToReveiveVo(Model model, String requestContent, Boolean needDefaultValue,HttpSession session) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		List<ParsingOfVo> parsingOfVoList = toolService.requestJsonParsingToReveiveVo(requestContent, needDefaultValue != null ? needDefaultValue : false);
		// 遍历结果
		StringBuilder result = new StringBuilder();
		for (ParsingOfVo parsingOfVo : parsingOfVoList) {
			result.append(parsingOfVo.getStringBuilder());
		}
		if (result.length() == 0) {
			return ResponseVo.fail("999999", "目前只支持map格式json的请求");
		} else {
			session.setAttribute("parsingOfVoList", parsingOfVoList);
			return ResponseVo.success(result.toString());
		}
	}
	
	/**
	 * json对应Vo(对象) 下载
	 * 
	 * @author SangLy
	 * @createTime 2017年12月20日 下午2:11:46
	 * @param session
	 * @return
	 * @throws IOException
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("/requestJsonParsingToReveiveVoDownload")
	public ResponseEntity<byte[]> requestJsonParsingToReveiveVoDownload(HttpSession session) throws IOException {
		List<ParsingOfVo> parsingOfVoList = null;
		if (session.getAttribute("parsingOfVoList") != null) {
			parsingOfVoList = (List<ParsingOfVo>) session.getAttribute("parsingOfVoList");
		} else {
			return null;
		}
		List<File> targetListFile = new ArrayList<File>();
		// 清空文件夹下的所以文件
		CommonFileUtils.deleteDir(CommonFileUtils.path);
		for (ParsingOfVo parsingOfVo : parsingOfVoList) {
			targetListFile.add(new File(CommonFileUtils.createFile(parsingOfVo.getVoName(), parsingOfVo.getStringBuilder() != null ? parsingOfVo.getStringBuilder().toString() : "", ".java")));
		}
		DateFormat df = new SimpleDateFormat("yyyyMMdd");
		df.format(new Date());
		String tempName = "json对应Vo(对象)" + df.format(new Date()) + ".zip";
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
	 * sql 缩进：缩进规则：去掉\n \t \r 如果联系空格则只保留一个空格 "()"的前后空格":"的后空格
	 * 
	 * @author issanyang@sina.com
	 * @createTime 2017年11月3日 下午1:37:08
	 * @param model
	 * @param requestContent
	 * @param needDefaultValue
	 * @return
	 */
	@RequestMapping("/sqlIndentation")
	@ResponseBody
	public Map<String, Object> sqlIndentation(Model model, String requestContent, Boolean needDefaultValue) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		StringBuilder stringBuilder = new StringBuilder();
		if (StringUtils.isNotBlank(requestContent) && requestContent.contains("--")) {
			stringBuilder.append("待缩进的sql不能包含注释符\"--\"");
		} else if (StringUtils.isNotBlank(requestContent)) {
			char[] cArrray = requestContent.toCharArray();
			List<String> tempList = new ArrayList<String>();
			List<String> resultList = new ArrayList<String>();
			int spaceNum = 0;
			// 先把\t \r \n 转换成空格
			for (int i = 0; i < cArrray.length; i++) {
				if (cArrray[i] == '\t') {
					cArrray[i] = ' ';
				}
			}
			for (int i = 0; i < cArrray.length; i++) {
				if (cArrray[i] == '\r') {
					cArrray[i] = ' ';
				}
			}
			for (int i = 0; i < cArrray.length; i++) {
				if (cArrray[i] == '\n') {
					cArrray[i] = ' ';
				}
			}
			// 去掉多余的空格
			for (char cChar : cArrray) {
				if (cChar == ' ') {
					spaceNum++;
				}
				if (cChar == ' ' && spaceNum == 1) {
					tempList.add(String.valueOf(cChar));
					continue;
				}
				if (cChar != ' ') {
					spaceNum = 0;
					tempList.add(String.valueOf(cChar));
				}
			}
			// 去掉"("")"前后空格
			for (int i = 0; i < tempList.size(); i++) {
				// 第一个空格不要
				if (" ".equals(tempList.get(i)) && i == 0) {
					continue;
				}

				// 如果当前元素是空格并且此空格的前一个字符是"():"符合中的任何一个则此空格不要
				if (" ".equals(tempList.get(i))) {
					if (resultList.size() >= 1) {
						if (resultList.get(resultList.size() - 1).equals("(") || resultList.get(resultList.size() - 1).equals(")") || resultList.get(resultList.size() - 1).equals(":")) {
							continue;
						}
					}
				}
				resultList.add(tempList.get(i));
				// 如果当前元素"("则去掉前一个空格
				if ("(".equals(tempList.get(i)) || ")".equals(tempList.get(i))) {
					if (resultList.size() >= 2) {
						if (resultList.get(resultList.size() - 2).equals(" ")) {
							resultList.remove(resultList.size() - 2);
							continue;
						}
					}
				}
			}
			for (String cString : resultList) {
				stringBuilder.append(cString);
			}
		} else {
			stringBuilder.append("请求内容不能为空");
		}
		resultMap.put("data", stringBuilder.toString());
		return resultMap;
	}
	
	@RequestMapping("/requestJsonParsingForReveiveVoSetCode")
	@ResponseBody
	public Map<String, Object> requestJsonParsingForReveiveVoSetCode(Model model, String requestContent, Boolean needDefaultValue) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		List<StringBuilder> stringBuilderList = toolService.requestJsonParsingForReveiveVoSetCode(requestContent, needDefaultValue != null ? needDefaultValue : false);
		// 遍历结果
		StringBuilder result = new StringBuilder();
		for (StringBuilder str : stringBuilderList) {
			result.append(str);
		}
		if (result.length() == 0) {
			resultMap.put("data", "目前只支持map格式json的请求");
		} else {
			resultMap.put("data", result);
		}
		return resultMap;
	}
	
	@RequestMapping("/requestJsonParsingForReveiveVoGetCode")
	@ResponseBody
	public Map<String, Object> requestJsonParsingForReveiveVoGetCode(Model model, String requestContent, Boolean needDefaultValue) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		List<StringBuilder> stringBuilderList = toolService.requestJsonParsingForReveiveVoGetCode(requestContent, needDefaultValue != null ? needDefaultValue : false);
		// 遍历结果
		StringBuilder result = new StringBuilder();
		for (StringBuilder str : stringBuilderList) {
			result.append(str);
		}
		if (result.length() == 0) {
			resultMap.put("data", "目前只支持map格式json的请求");
		} else {
			resultMap.put("data", result);
		}
		return resultMap;
	}

}
