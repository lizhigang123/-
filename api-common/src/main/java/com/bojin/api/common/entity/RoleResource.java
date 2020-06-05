/** 
 * @(#)ResourceEntity.java 1.0.0 2016年4月8日 下午4:13:30  
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
 * @version $Revision:1.0.0, $Date: 2016年4月8日 下午4:13:30 $
 */
@Entity
@Table(name = "API_T_ROLE_RESOURCE_RELATION")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
public class RoleResource extends AbstractEntity {

	private static final long serialVersionUID = 5425524930379231522L;
 
	/**
	 * 资源ID
	 */
	private String resourceId;
	/**
	 * 角色id
	 */
	private String roleId;

}
