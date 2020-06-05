/** 
 * @(#)ProjectUserRelationEntity.java 1.0.0 2016年4月11日 下午3:44:43  
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
 * 项目开发人员关系实体
 *  
 * @author  fuq
 * @version $Revision:1.0.0, $Date: 2016年4月11日 下午3:44:43 $ 
 */
@Entity
@Table(name = "API_T_PROJECT_USER_RELATION")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@NoArgsConstructor
public class ProjectUserRelationEntity extends AbstractEntity{

	private static final long serialVersionUID = 1L;
	
	/**
	 * 项目ID
	 */
	private String projectId;
	
	/**
	 * 开发人员ID
	 */
	private String userId;
	
	

}
