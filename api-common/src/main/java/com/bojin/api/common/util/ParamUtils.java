package com.bojin.api.common.util;

import org.apache.commons.lang3.StringUtils;

public class ParamUtils {
	
	/**
	 * 判断是否是path或者url
	 * @param paramIsUrl
	 * @return
	 */
	public static Boolean isUrlOrPath(String paramIsUrlOrPath) {
		if (paramIsUrlOrPath.length() < 3) {
			return false;
		}
		if (paramIsUrlOrPath.substring(paramIsUrlOrPath.length() - 3, paramIsUrlOrPath.length()).equalsIgnoreCase("Url")) {
			return true;
		} else {
			if (paramIsUrlOrPath.length() > 3) {
				if (paramIsUrlOrPath.substring(paramIsUrlOrPath.length() - 4, paramIsUrlOrPath.length()).equalsIgnoreCase("path")) {
					return true;
				}
			}
			return false;
		}
	}
	
	/**
	 * 根据字段后缀是否是Date判断
	 * 
	 * @param paramString
	 * @return
	 */
	public static Boolean isDate(String paramString) {
		if (paramString.length() < 4) {
			return false;
		}
		if (paramString.substring(paramString.length() - 4, paramString.length()).equalsIgnoreCase("Date")) {
			return true;
		} else {
			return false;
		}
	}
	
	/**
	 * 判断输入字符串是否和目标字符串相等
	 * 
	 * @param target
	 *            下面常见字符串
	 * 
	 *            <pre>
	 *           	 mobile 手机号
	 *           	 tel 固话
	 *           	 phoneNumber 电话
	 *               phone 电话
	 *           	 identification 身份证
	 *           	 identityCard 身份证
	 *               sex 性别
	 *               age 年龄
	 *               email 电子邮箱
	 *               bankCard 银行卡
	 *            </pre>
	 * 
	 * @return
	 */
	public static Boolean isTarget(String target,String inputString) {
		if (StringUtils.isNotBlank(inputString) || StringUtils.isNotBlank(inputString)) {
			if (target.equalsIgnoreCase(inputString.trim())) {
				return true;
			}
		}
		return false;
	}

}
