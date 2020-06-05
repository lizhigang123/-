/** 
 * @(#)UserRoleEntity.java 1.0.0 2016年4月8日 下午4:18:31  
 *  
 * Copyright © 2016 善林金融.  All rights reserved.  
 */

package com.bojin.api.common.entity;

import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * 
 * 
 * @author jeff
 * @version $Revision:1.0.0, $Date: 2016年4月8日 下午4:18:31 $
 */
@Entity
@Table(name = "API_T_USER_ROLE_RELATION")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
public class UserRole extends AbstractEntity {

	private static final long serialVersionUID = 5178930605127912060L;
	/**
	 * 角色ID
	 */
	private String roleId;
	/**
	 * 用户ID
	 */
	private String userId;
}
