/** 
 * @(#)QueryExtend.java 1.0.0 2016年1月4日 下午2:48:18  
 *  
 * Copyright © 2016 善林金融.  All rights reserved.  
 */

package com.bojin.api.common.support.repository.page;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 查询扩展(具体实现参考{@code RepositoryQueryExtend})
 * <dl>
 * <dd>1.仅支持名称占位符,不支持数字占位符</dd>
 * <dd>2.支持参数类型:所有</dd>
 * <dd>3.特殊参数类型:{@code PageRequest},分页特殊处理,当前为null时根据返回结果判断是否分页处理</dd>
 * <dd>4.特殊参数类型:{@code PageResponse},分页特殊处理</dd>
 * <dd>3.sql不支持spel表达式</dd>
 * <dd>4.参数不支持级联</dd>
 * <dd>5.返回值支持类型:所有jdbcTemplate支持的类型</dd>
 * </dl>
 * 
 * 
 * @author samson
 * @version $Revision:1.0.0, $Date: 2016年1月4日 下午2:48:18 $
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface QueryExtend {
	/**
	 * 返回结果映射集
	 * 
	 * @return
	 */
	ReturnMapping[] value() default {};

	/**
	 * 是否剔除空参数(默认剔除),需要sql 满足?( )封装的条件
	 * 
	 * @return
	 */
	boolean eliminateBlank() default true;

	/**
	 * 结果是否驼峰转换(默认转换)
	 * 
	 * @return
	 */
	boolean toCamel() default true;

}
