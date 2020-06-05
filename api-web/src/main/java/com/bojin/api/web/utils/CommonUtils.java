/** 
 * @(#)JsonFormatTool.java 1.0.0 2017年7月14日 上午11:51:50  
 *  
 * Copyright © 2017 善林金融.  All rights reserved.  
 */

package com.bojin.api.web.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringWriter;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.util.DigestUtils;

import com.bojin.api.common.constants.Constants;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * 该类提供格式化JSON字符串的方法。 该类的方法formatJson将JSON字符串格式化，方便查看JSON数据。
 * <p>
 * 例如：
 * <p>
 * JSON字符串：["yht","xzj","zwy"]
 * <p>
 * 格式化为：
 * <p>
 * [
 * <p>
 * "yht",
 * <p>
 * "xzj",
 * <p>
 * "zwy"
 * <p>
 * ]
 * 
 * <p>
 * 使用算法如下：
 * <p>
 * 对输入字符串，追个字符的遍历
 * <p>
 * 1、获取当前字符。
 * <p>
 * 2、如果当前字符是前方括号、前花括号做如下处理：
 * <p>
 * （1）如果前面还有字符，并且字符为“：”，打印：换行和缩进字符字符串。
 * <p>
 * （2）打印：当前字符。
 * <p>
 * （3）前方括号、前花括号，的后面必须换行。打印：换行。
 * <p>
 * （4）每出现一次前方括号、前花括号；缩进次数增加一次。打印：新行缩进。
 * <p>
 * （5）进行下一次循环。
 * <p>
 * 3、如果当前字符是后方括号、后花括号做如下处理：
 * <p>
 * （1）后方括号、后花括号，的前面必须换行。打印：换行。
 * <p>
 * （2）每出现一次后方括号、后花括号；缩进次数减少一次。打印：缩进。
 * <p>
 * （3）打印：当前字符。
 * <p>
 * （4）如果当前字符后面还有字符，并且字符不为“，”，打印：换行。
 * <p>
 * （5）继续下一次循环。
 * <p>
 * 4、如果当前字符是逗号。逗号后面换行，并缩进，不改变缩进次数。
 * <p>
 * 5、打印：当前字符。
 * 
 * @author yanghaitao
 * @version [版本号, 2014年9月29日]
 */
public class CommonUtils {

	/**
	 * 单位缩进字符串。
	 */
	private static String SPACE = "   ";

	private static ObjectMapper mapper = new ObjectMapper();

	private static final Charset UTF8 = StandardCharsets.UTF_8;

	public static String serviceVersion = "1.0.0";

	public static String appKey = "614727a152dec6fc";

	public static String platform = "windows";

	/**
	 * 返回格式化JSON字符串。
	 * 
	 * @param json
	 *            未格式化的JSON字符串。
	 * @return 格式化的JSON字符串。
	 */
	public static String formatJson(String json) {
		StringBuffer result = new StringBuffer();

		int length = json.length();
		int number = 0;
		char backKey = 0;
		char frontKey = 0;
		char key = 0;

		// 遍历输入字符串。
		for (int i = 0; i < length; i++) {
			// 1、获取当前字符。
			key = json.charAt(i);
			if (i + 1 < length) {
				backKey = json.charAt(i + 1);
			}
			if (i > 0) {
				frontKey = json.charAt(i - 1);
			}
			// 2、如果当前字符是前方括号、前花括号做如下处理：
			if ((key == '[') || (key == '{')) {
				// （1）如果前面还有字符，并且字符为“：”，打印：换行和缩进字符字符串。
				if ((i - 1 > 0) && (json.charAt(i - 1) == ':')) {
					result.append('\n');
					result.append(indent(number));
				}

				// （2）打印：当前字符。
				result.append(key);

				// （3）前方括号、前花括号，的后面必须换行。打印：换行。
				if (backKey != '\r' && backKey != '\n') {
					result.append('\n');
				}

				// （4）每出现一次前方括号、前花括号；缩进次数增加一次。打印：新行缩进。
				number++;
				result.append(indent(number));

				// （5）进行下一次循环。
				continue;
			}

			// 3、如果当前字符是后方括号、后花括号做如下处理：
			if ((key == ']') || (key == '}')) {
				// （1）后方括号、后花括号，的前面必须换行。打印：换行。
				if (i != length) {
					if (backKey != '\r' && backKey != '\n') {
						result.append('\n');
					}
				} else {
					if (frontKey != '\r' && frontKey != '\n') {
						result.append('\n');
					}
				}

				// （2）每出现一次后方括号、后花括号；缩进次数减少一次。打印：缩进。
				number--;
				result.append(indent(number));

				// （3）打印：当前字符。
				result.append(key);

				// （4）如果当前字符后面还有字符，并且字符不为“，”，打印：换行。
				// if (((i + 1) < length) && (json.charAt(i + 1) != ',')) {
				// result.append('\n');
				// }

				// （5）继续下一次循环。
				continue;
			}

			// 4、如果当前字符是逗号。逗号后面换行，并缩进，不改变缩进次数。
			if ((key == ',')) {
				result.append(key);
				if (backKey != '\r' && backKey != '\n') {
					result.append('\n');
				}
				result.append(indent(number));
				continue;
			}

			// 5、打印：当前字符。
			result.append(key);
		}

		return result.toString();
	}

	/**
	 * 返回指定次数的缩进字符串。每一次缩进三个空格，即SPACE。
	 * 
	 * @param number
	 *            缩进次数。
	 * @return 指定缩进次数的字符串。
	 */
	private static String indent(int number) {
		StringBuffer result = new StringBuffer();
		for (int i = 0; i < number; i++) {
			result.append(SPACE);
		}
		return result.toString();
	}

	/**
	 * 判断是否为ip
	 * 
	 * @authorS
	 * @createTime 2017年7月14日 下午6:13:15
	 * @param text
	 * @return
	 */
	public static boolean ipCheck(String text) {
		if (text != null && !text.isEmpty()) {
			if ("localhost".equals(text)) {
				return true;
			}
			// 定义正则表达式
			String regex = "^(1\\d{2}|2[0-4]\\d|25[0-5]|[1-9]\\d|[1-9])\\." + "(1\\d{2}|2[0-4]\\d|25[0-5]|[1-9]\\d|\\d)\\." + "(1\\d{2}|2[0-4]\\d|25[0-5]|[1-9]\\d|\\d)\\." + "(1\\d{2}|2[0-4]\\d|25[0-5]|[1-9]\\d|\\d)$";
			// 判断ip地址是否与正则表达式匹配
			if (text.matches(regex)) {
				// 返回判断信息
				return true;
			} else {
				// 返回判断信息
				return false;
			}
		}
		return false;
	}

	/**
	 * 获取ip
	 * 
	 * @author SangLy
	 * @createTime 2017年7月17日 上午9:33:04
	 * @param request
	 * @return
	 * @throws Exception
	 */
	public static String getIpAddr(HttpServletRequest request) throws Exception {
		String ip = request.getHeader("x-forwarded-for");
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("PRoxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("WL-Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getRemoteAddr();
		}
		if ("0:0:0:0:0:0:0:1".equals(ip)) {
			ip = "127.0.1.1";
		}
		return ip;
	}

	public static String getIpMask() {
		if (getOSName().startsWith("windows")) {
			return getWindowsMACAddress();
		} else {
			return getUnixMACAddress();
		}
	}

	public static String getOSName() {
		return System.getProperty("os.name").toLowerCase();
	}

	private static String getWindowsMACAddress() {
		try {
			InetAddress ia = InetAddress.getLocalHost();
			byte[] mac = NetworkInterface.getByInetAddress(ia).getHardwareAddress();
			StringBuffer sb = new StringBuffer("");
			for (int i = 0; i < mac.length; i++) {
				if (i != 0) {
					sb.append("-");
				}
				int temp = mac[i] & 0xff;
				String str = Integer.toHexString(temp);
				if (str.length() == 1) {
					sb.append("0" + str);
				} else {
					sb.append(str);
				}
			}
			return sb.toString().toUpperCase();
		} catch (Exception e) {
			return StringUtils.EMPTY;
		}
	}

	private static String getUnixMACAddress() {
		String mac = "";
		try {
			Process p = new ProcessBuilder("ifconfig").start();
			BufferedReader br = new BufferedReader(new InputStreamReader(p.getInputStream()));
			String line;
			while ((line = br.readLine()) != null) {
				Pattern pat = Pattern.compile("\\b\\w+:\\w+:\\w+:\\w+:\\w+:\\w+\\b");
				Matcher mat = pat.matcher(line);
				if (mat.find()) {
					mac = mat.group(0);
				}
			}
			br.close();
		} catch (IOException e) {
		}
		return mac;
	}

	@SuppressWarnings("unused")
	private String getSign(long timestamp, Object body) {
		StringWriter jsonBody = new StringWriter();
		try {
			mapper.writeValue(jsonBody, body);
		} catch (IOException e) {
			e.getStackTrace();
		}
		StringBuilder sb = new StringBuilder();
		sb.append(jsonBody.toString());
		sb.append(timestamp);
		sb = new StringBuilder(DigestUtils.md5DigestAsHex(sb.toString().getBytes(UTF8)));
		sb.append(serviceVersion);
		return DigestUtils.md5DigestAsHex(sb.toString().getBytes(UTF8));
	}

	/**
	 * 字符串首字母小写
	 * 
	 * @param target
	 * @return
	 */
	public static String firstCharToLowCase(String target) {
		char[] cs = target.toCharArray();
		char[] chars = new char[1];
		chars[0] = target.charAt(0);
		String temp = new String(chars);
		cs[0] = temp.toLowerCase().toCharArray()[0];
		return String.valueOf(cs);
	}

	/**
	 * 从目标字符串中查找出第几次出现目标字符的位置
	 * 
	 * @author issanyang@sina.com
	 * @createTime 2017年9月19日 下午4:48:03
	 * @param i
	 * @param targetChar
	 *            目标字符
	 * @param targetString
	 *            目标字符串
	 * @return
	 */
	public static int getIndexOf(int time, String targetChar, String targetString) {
		Integer index = targetString.indexOf(targetChar);
		for (int i = 0; i < time - 1; i++) {
			index = targetString.indexOf(targetChar, index + 1);
		}
		return index;
	}

	/**
	 * 字符串首字母大写
	 * 
	 * @param target
	 * @return
	 */
	public static String firstCharToUpCase(String target) {
		char[] cs = target.toCharArray();
		String temp = new String(cs);
		cs[0] = temp.toUpperCase().toCharArray()[0];
		return String.valueOf(cs);
	}

	/**
	 * 下划线转驼峰
	 * 
	 * @author issanyang@sina.com
	 * @createTime 2017年10月30日 下午4:22:01
	 * @param line
	 * @return
	 */
	public static String underline2Camel(String line) {
		boolean smallCamel = true;
		if (line == null || "".equals(line)) {
			return "";
		}
		StringBuffer sb = new StringBuffer();
		Pattern pattern = Pattern.compile("([A-Za-z\\d]+)(_)?");
		Matcher matcher = pattern.matcher(line);
		while (matcher.find()) {
			String word = matcher.group();
			sb.append(smallCamel && matcher.start() == 0 ? Character.toLowerCase(word.charAt(0)) : Character.toUpperCase(word.charAt(0)));
			int index = word.lastIndexOf('_');
			if (index > 0) {
				sb.append(word.substring(1, index).toLowerCase());
			} else {
				sb.append(word.substring(1).toLowerCase());
			}
		}
		return sb.toString();
	}

	public static int getListKeyCount(List<String> list, String key) {
		int count = 0;
		if (CollectionUtils.isNotEmpty(list)) {
			if (list.contains(key)) {
				for (String s : list) {
					if (key.equals(s)) {
						count++;
					}
				}
			}
		}
		return count;
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
	public static StringBuilder getLineByTypeAndeAttribute(String note, String type, String attribute, StringBuilder stringBuilder,Boolean neadValid,String paramEntityDataType,String prefixErrorCode,StringBuilder errorCodeStringBuilder,String parentMapOrListNote,Boolean needDefaultValue) {
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
	public static StringBuilder getLineSetByTypeAndeAttribute(String classVoName,String note, String type, String attribute, StringBuilder stringBuilder,Object defaultValue,String setOrget,Boolean needDefaultValue,int numberZiBiaoFu) {
		if(!needDefaultValue){
			defaultValue = null;
		}
		if ("set".equals(setOrget)) {
			stringBuilder.append("\r\n");
			for (int i = 0; i < numberZiBiaoFu; i++) {
				stringBuilder.append("\t");
			}
			stringBuilder.append(CommonUtils.firstCharToLowCase(classVoName) + "." + "set" + CommonUtils.firstCharToUpCase(attribute) + "(" + defaultValue + "); " + (StringUtils.isNotBlank(type + note) == true ? ("// " + type + note) : ""));
		} else if ("get".equals(setOrget)) {
			stringBuilder.append("\r\n");
			for (int i = 0; i < numberZiBiaoFu; i++) {
				stringBuilder.append("\t");
			}
			stringBuilder.append(CommonUtils.firstCharToLowCase(classVoName) + "." + "get" + CommonUtils.firstCharToUpCase(attribute) + "(); " + (StringUtils.isNotBlank(type + note) == true ? ("// " + type + note) : ""));
		}
		return stringBuilder;
	}
	
}
