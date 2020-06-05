/** 
 * @(#)RoleEntity.java 1.0.0 2016年4月8日 下午4:03:49  
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
 * @author jeff
 * @version $Revision:1.0.0, $Date: 2016年4月8日 下午4:03:49 $
 */
@Entity
@Table(name = "API_T_ROLE")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@NoArgsConstructor
public class Role extends BaseEntity {

	private static final long serialVersionUID = -5149122376265890397L;
	/**
	 * 角色名称
	 */
	private String name;
}
