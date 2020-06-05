/** 
 * @(#)SLValidator.java 1.0.0 2014年12月20日 下午2:33:56  
 *  
 * Copyright © 2014 善林金融.  All rights reserved.  
 */

package com.bojin.api.common.validate.validator;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bojin.api.common.validate.ErrorMessage;
import com.bojin.api.common.validate.RuleUtils;

/**
 * 一般通常的验证器
 * 
 * @author 孟山
 * @version $Revision:1.0.0, $Date: 2014年12月20日 下午2:33:56 $
 */
public abstract class SLValidatorAbstract implements SLValidator {

	protected Logger logger = LoggerFactory.getLogger(this.getClass());

	/**
	 * 所有异常信息汇总
	 */
	private Set<ErrorMessage> messages = new HashSet<ErrorMessage>();
	/**
	 * 错误字段总数
	 */
	private int count = 0;

	public String getMessage() {
		StringBuffer sb = new StringBuffer();
		for (Iterator<ErrorMessage> iterator = messages.iterator(); iterator.hasNext();) {
			ErrorMessage errorMessage = iterator.next();
			sb.append(errorMessage.getName()).append(":").append(errorMessage.getMessage()).append(";");
		}
		return sb.toString();
	}

	@Override
	public Set<ErrorMessage> getMessages() {
		return this.messages;
	}

	@Override
	public String toString() {
		return getMessage();
	}

	public int getErrorsCount() {
		return count;
	}

	/**
	 * 检查是否有已有错误信息
	 * 
	 * @return
	 */
	public boolean hasErrors() {
		return count > 0;
	}

	/**
	 * 组装错误信息 目前只保留第一个出现错误的信息
	 * 
	 * @param flag
	 * @param message
	 */
	protected void populate(String key, boolean flag, String message) {
		if (flag == false) {
			messages.add(new ErrorMessage(key, formatMessage(message)));
			count++;
		}
	}

	/**
	 * 格式化错误信息
	 * 
	 * @param message
	 * @return
	 */
	protected abstract String formatMessage(String message);

	/**
	 * 验证必填
	 * 
	 * @param key
	 *            验证值
	 * @param message
	 *            验证失败时的错误信息
	 * @return
	 */
	protected SLValidatorAbstract required(String key, boolean check, String message) {
		if (check) {
			populate(key, RuleUtils.required(getValue(key)), formatMessage(message));
		}
		return this;
	}

	/**
	 * 验证是否为手机号
	 * 
	 * @param mobile
	 *            待验证值
	 * @param message
	 *            验证失败时的错误信息
	 * @return
	 */
	protected SLValidatorAbstract mobile(String key, boolean check, String message) {
		if (check) {
			populate(key, RuleUtils.isMobile(getValue(key)), formatMessage(message));
		}
		return this;
	}

	/**
	 * 验证是否为电话
	 * 
	 * @param key
	 *            待验证值
	 * @param message
	 *            验证失败时的错误信息
	 * @return
	 */
	protected SLValidatorAbstract tel(String key, boolean check, String message) {
		if (check) {
			populate(key, RuleUtils.isTel(getValue(key)), formatMessage(message));
		}
		return this;
	}

	/**
	 * 验证是否是邮箱格式
	 * 
	 * @param key
	 *            待验证值
	 * @param message
	 *            验证失败时的错误信息
	 * @return
	 */
	protected SLValidatorAbstract email(String key, boolean check, String message) {
		if (check) {
			populate(key, RuleUtils.isEmail(getValue(key)), formatMessage(message));
		}
		return this;
	}

	/**
	 * 验证是否是银行卡
	 * 
	 * @param key
	 *            待验证值
	 * @param check
	 *            是否检查
	 * @param message
	 *            验证失败时的错误信息
	 * @return
	 */
	protected SLValidatorAbstract bankCard(String key, boolean check, String message) {
		if (check) {
			populate(key, RuleUtils.isBankCard(getValue(key)), formatMessage(message));
		}
		return this;
	}

	/**
	 * 验证是否是数字
	 * 
	 * @param key
	 *            待验证值
	 * @param message
	 *            验证失败时的错误信息
	 * @return
	 */
	protected SLValidatorAbstract number(String key, boolean check, String message) {
		if (check) {
			populate(key, RuleUtils.isNumber(getValue(key)), formatMessage(message));
		}
		return this;
	}

	/**
	 * 验证是否是整数
	 * 
	 * @param key
	 *            待验证值
	 * @param message
	 *            验证失败时的错误信息
	 * @return
	 */
	protected SLValidatorAbstract digist(String key, boolean check, String message) {
		if (check) {
			populate(key, RuleUtils.isDigist(getValue(key)), formatMessage(message));
		}
		return this;
	}

	protected SLValidator identification(String key, boolean check, String message) {
		if (check) {
			populate(key, RuleUtils.isIdentification(getValue(key)), formatMessage(message));
		}
		return this;
	}

	/**
	 * 验证某两个值是否是相等
	 * 
	 * @param dest
	 * @param source
	 * @param message
	 *            验证失败时的错误信息
	 * @return
	 */
	protected SLValidatorAbstract equalTo(String dest, String source, String message) {
		if (source != null && !"".equals(source)) {
			populate(dest + "--" + source, RuleUtils.equalTo(getValue(dest), getValue(source)), formatMessage(message));
		}
		return this;
	}

	/**
	 * 验证长度必须大于最小值
	 * 
	 * @param key
	 *            待验证值
	 * @param minLength
	 * @param message
	 *            验证失败时的错误信息
	 * @return
	 */
	protected SLValidatorAbstract minLength(String key, int minLength, String message) {
		if (minLength < Integer.MIN_VALUE) {
			populate(key, RuleUtils.minLength(getValue(key), minLength), formatMessage(message));
		}
		return this;
	}

	/**
	 * 验证长度必须小于最大值
	 * 
	 * @param key
	 *            待验证值
	 * @param maxLength
	 * @param message
	 *            验证失败时的错误信息
	 * @return
	 */
	protected SLValidatorAbstract maxLength(String key, int maxLength, String message) {
		if (maxLength > Integer.MAX_VALUE) {
			populate(key, RuleUtils.maxLength(getValue(key), maxLength), formatMessage(message));
		}
		return this;
	}

	/**
	 * 验证必须大于最小值
	 * 
	 * @param key
	 *            待验证值
	 * @param min
	 * @param message
	 *            验证失败时的错误信息
	 * @return
	 */
	protected SLValidatorAbstract min(String key, double min, String message) {
		if (min > Double.MIN_VALUE) {
			populate(key, RuleUtils.min(getValue(key), min), formatMessage(message));
		}
		return this;
	}

	/**
	 * 验证必须小于最大值
	 * 
	 * @param key
	 *            待验证值
	 * @param max
	 * @param message
	 *            验证失败时的错误信息
	 * @return
	 */
	protected SLValidatorAbstract max(String key, double max, String message) {
		if (max < Double.MAX_VALUE) {
			populate(key, RuleUtils.max(getValue(key), max), formatMessage(message));
		}
		return this;
	}

	/**
	 * 验证值在数组范围内
	 * 
	 * @param key
	 *            待验证值
	 * @param array
	 * @param message
	 *            验证失败时的错误信息
	 * @return
	 */
	protected SLValidatorAbstract inRange(String key, String[] array, String message) {
		if (array.length > 0) {
			populate(key, RuleUtils.inRange(getValue(key), array), formatMessage(message));
		}
		return this;
	}

	/**
	 * 验证倍数
	 * 
	 * @param key
	 *            待验证值
	 * @param mult
	 *            整数的倍数
	 * @param message
	 *            验证失败时的错误信息
	 * @return
	 */
	protected SLValidatorAbstract multiple(String key, int mult, String message) {
		if (mult > 0) {
			populate(key, RuleUtils.multiple(getValue(key), mult), formatMessage(message));
		}
		return this;
	}

	/**
	 * 日期格式校验
	 * 
	 * @param key
	 *            待验证值
	 * @param format
	 *            日期格式 支持{@code SimpleDateFormat} 所支持的格式
	 * @param message
	 *            验证失败时的错误信息
	 * @return
	 */
	protected SLValidatorAbstract dateFormat(String key, String format, String message) {
		if (format != null && !"".equals(format)) {
			populate(key, RuleUtils.dateFormat(getValue(key), format), formatMessage(message));
		}
		return this;
	}

	/**
	 * 小数位数校验
	 * 
	 * @param key
	 *            待验证值
	 * @param decimalsLength
	 *            允许最大小数位数
	 * @param message
	 *            验证失败时的错误信息
	 * @return
	 */
	protected SLValidatorAbstract decimals(String key, int decimalsLength, String message) {
		if (decimalsLength >= 0) {
			populate(key, RuleUtils.decimals(getValue(key), decimalsLength), formatMessage(message));
		}
		return this;
	}

	/**
	 * 指定正则表达式验证
	 * 
	 * @param key
	 * @param regex
	 * @param message
	 * @return
	 */
	protected SLValidatorAbstract regex(String key, String regex, String message) {
		if (regex != null && !RuleUtils.isBlank(regex)) {
			boolean match = false;
			try {
				match = Pattern.matches(regex, getValue(key).toString());
			} catch (Exception e) {
				logger.error("web规则注解正则表达式错误", e);
			}
			populate(key, match, formatMessage(message));
		}
		return this;
	}
}
