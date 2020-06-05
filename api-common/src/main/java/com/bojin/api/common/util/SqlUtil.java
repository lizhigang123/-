/** 
 * @(#)Util.java 1.0.0 2015年8月13日 上午7:58:53  
 *  
 * Copyright © 2015 善林金融.  All rights reserved.  
 */

package com.bojin.api.common.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.google.common.collect.Lists;

/**
 * sql工具类
 * 
 * @author samson
 *
 */
public class SqlUtil {
	// key:列名,value:转换后值缓存
	private static final Map<String, String> CAMEL_NAME_MAP_CACHE = new HashMap<String, String>();

	public static final Pattern WEED_QUERY_ARGS_REPLACE_REGEX = Pattern.compile("\\?\\s*\\([^\\?]*\\)\\s*\\?");
	public static final Pattern PLACEHOLDER_NAME_REGEX = Pattern.compile("(?<=:)(?=\\s*)\\S+(?=[\\s\\)])");
	public static final Pattern WORD_SPLICE_REGEX = Pattern.compile("");

	public static final String WHERE = "WHERE";
	public static final String AND = "AND";
	public static final String OR = "OR";
	public static final String ALWAYS_SET_UP = "1=1";
	public static final String NEVER_SET_UP = "1<>1";

	/**
	 * 下划线转换
	 * 
	 * @param s
	 *            hhhOoXxx
	 * @param separator
	 *            _(要替换的字符)
	 * @return hyh_oo_xx_aaaa
	 */
	public static String toUnderlineName(String s, String separator) {
		if (s == null) {
			return null;
		}
		StringBuilder sb = new StringBuilder();
		boolean upperCase = false;
		for (int i = 0; i < s.length(); i++) {
			char c = s.charAt(i);
			boolean nextUpperCase = true;
			if (i < (s.length() - 1)) {
				nextUpperCase = Character.isUpperCase(s.charAt(i + 1));
			}
			if ((i >= 0) && Character.isUpperCase(c)) {
				if (!upperCase || !nextUpperCase) {
					if (i > 0)
						sb.append(separator);
				}
				upperCase = true;
			} else {
				upperCase = false;
			}
			sb.append(Character.toLowerCase(c));
		}
		return sb.toString();
	}

	/**
	 * 驼峰转换
	 * 
	 * @param name
	 *            HHH_OO_XXX
	 * @return hhhOoXxx
	 */
	public static String camelName(String name) {
		StringBuilder result = new StringBuilder();
		if (name == null || name.isEmpty()) {
			return name;
		}
		String camelName = CAMEL_NAME_MAP_CACHE.get(name);
		if (camelName != null && !camelName.equals("")) {
			return camelName;
		}
		if (name.indexOf("_") == -1 && name.matches("(.*[a-z].*)(.*[A-Z].*)")) {
			CAMEL_NAME_MAP_CACHE.put(name, name);
			return name;
		}
		String camels[] = name.split("_");
		for (String camel : camels) {
			if (camel.isEmpty()) {
				continue;
			}
			if (result.length() == 0) {
				result.append(camel.toLowerCase());
			} else {
				result.append(camel.substring(0, 1).toUpperCase());
				result.append(camel.substring(1).toLowerCase());
			}
		}
		CAMEL_NAME_MAP_CACHE.put(name, result.toString());
		return result.toString();
	}

	public static String[] weedSqlArgs(String sql) {
		Matcher matcher = WEED_QUERY_ARGS_REPLACE_REGEX.matcher(sql);
		List<String> args = new ArrayList<String>();
		while (matcher.find()) {
			args.add(matcher.group());
		}
		return args.toArray(new String[0]);
	}

	public static String getPlaceholderName(String str) {
		String[] result = getPlaceholderNames(str);
		return result.length > 0 ? result[0] : null;
	}

	public static String[] getPlaceholderNames(String str) {
		Matcher matcher = PLACEHOLDER_NAME_REGEX.matcher(str);
		List<String> list = Lists.newArrayList();
		while (matcher.find()) {
			list.add(matcher.group());
		}
		return list.toArray(new String[0]);
	}

	/**
	 * 获取分页sql的总记录数sql
	 * 
	 * @param sql
	 * @return
	 */
	public static String getTotalSql(String sql) {
		return "SELECT COUNT(1) FROM ( " + sql + " ) _temp";
	}

	/**
	 * 分页查询sql,返回结果会带出rownum对应的key值为:ROWNUM_
	 * 
	 * @param sql
	 * @param start
	 * @param length
	 * @return
	 */
	public static String getPageSql(String sql, int start, int length) {
		return new StringBuilder(sql).append(" LIMIT ").append(length).append(" OFFSET ").append(start).toString();
	}
}
