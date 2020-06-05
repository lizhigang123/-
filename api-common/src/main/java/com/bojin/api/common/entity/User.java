/** 
 * @(#)UserEntity.java 1.0.0 2016年3月15日 下午5:06:40  
 *  
 * Copyright © 2016 善林金融.  All rights reserved.  
 */

package com.bojin.api.common.entity;

import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * 
 * 
 * @author samson
 * @version $Revision:1.0.0, $Date: 2016年3月15日 下午5:06:40 $
 */
@Entity
@Table(name = "API_T_USER")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@NoArgsConstructor
public class User extends BaseEntity {

	private static final long serialVersionUID = -5084958862532423529L;
	/**
	 * 用户名
	 */
	private String name;
	/**
	 * 登录名
	 */
	private String loginName;
	/**
	 * 密码
	 */
	private String password;
	/**
	 * 邮箱
	 */
	private String email;
	/**
	 * 类型(管理者,开发者)
	 */
	private String type;

}
