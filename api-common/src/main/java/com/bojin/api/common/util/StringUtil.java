/** 
 * @(#)StringUtils.java 1.0.0 2015年12月21日 下午1:44:22  
 *  
 * Copyright © 2015 善林金融.  All rights reserved.  
 */

package com.bojin.api.common.util;

import org.apache.commons.lang3.ArrayUtils;

/**
 * 
 * 
 * @author samson
 * @version $Revision:1.0.0, $Date: 2015年12月21日 下午1:44:22 $
 */
public class StringUtil {

	/**
	 * 查找下一个单词(默认以空格隔开)
	 * 
	 * @param str
	 * @param startIndex
	 *            开始位置
	 * @return
	 */
	public static final String nextWord(String str, int startIndex) {
		return nextWord(str, startIndex, null);
	}

	/**
	 * 查找下一个单词(默认以空格隔开)
	 * 
	 * @param str
	 * @param startIndex
	 *            开始位置
	 * @param additionalSeperators
	 *            额外的分隔符
	 * @return
	 */
	public static final String nextWord(String str, int startIndex, String[] additionalSeperators) {
		return getWord(str, startIndex, str.length(), true, true, additionalSeperators);
	}

	/**
	 * 查找上一个单词(默认空格分隔)
	 * 
	 * @param str
	 * @param startIndex
	 *            开始位置
	 * @return
	 */
	public static final String previewWord(String str, int startIndex) {
		return previewxWord(str, startIndex, null);
	}

	/**
	 * 查找上一个单词
	 * 
	 * @param str
	 * @param startIndex
	 *            开始位置
	 * @param additionalSeperators
	 *            额外的分隔符
	 * @return
	 */
	public static final String previewxWord(String str, int startIndex, String[] additionalSeperators) {
		return getWord(str, startIndex, -1, false, true, additionalSeperators);
	}

	/**
	 * 查找上|下一个单词
	 * 
	 * @param str
	 * @param startIndex
	 *            起始位置
	 * @param endIndex
	 *            结束位置
	 * @param rightDirect
	 *            右向遍历
	 * @param whiteSeperator
	 *            是否空格分隔
	 * @param additionalSeperators
	 *            额外的分隔符
	 * @return
	 */
	public static final String getWord(String str, int startIndex, int endIndex, boolean rightDirect, boolean whiteSeperator, String[] additionalSeperators) {
		if (str == null || (rightDirect ? (startIndex > endIndex || startIndex < 0 || endIndex > str.length()) : (startIndex < endIndex || startIndex > str.length() || endIndex < -1))) {
			return "";
		} else {
			char[] chars = str.toCharArray();
			boolean isStart = false;
			int start = startIndex;
			int end = startIndex;
			for (int i = startIndex; rightDirect ? i < endIndex : i > endIndex;) {
				if (Character.isWhitespace(chars[i]) || (additionalSeperators != null && additionalSeperators.length > 0 && ArrayUtils.contains(additionalSeperators, chars[i]))) {
					end = i;
					if (isStart) {
						end = i;
						break;
					}
				} else {
					if (!isStart) {
						isStart = true;
						start = i;
					}
				}
				i = rightDirect ? i + 1 : i - 1;
			}
			if (isStart) {
				if (rightDirect) {
					end = end == startIndex ? start + 1 : end;
				} else {
					end = end == startIndex ? start - 1 : end;
				}
			}
			return isStart ? (rightDirect ? str.substring(start, end) : str.substring(end + 1, start + 1)) : "";
		}
	}

}
