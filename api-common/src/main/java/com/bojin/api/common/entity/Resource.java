/** 
 * @(#)ResourceEntity.java 1.0.0 2016年4月13日 上午9:18:56  
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
 * @version $Revision:1.0.0, $Date: 2016年4月13日 上午9:18:56 $
 */
@Entity
@Table(name = "API_T_RESOURCE")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@NoArgsConstructor
public class Resource extends BaseEntity {

	private static final long serialVersionUID = 499641052366257384L;
	/**
	 * 资源名称
	 */
	private String name;
	/**
	 * 资源路径
	 */
	private String url;
	/**
	 * 资源路径
	 */
	private String description;
	/**
	 * 父ID
	 */
	private String parentId;
	/**
	 * 排序
	 */
	private Integer ordered = 0;
	
}
