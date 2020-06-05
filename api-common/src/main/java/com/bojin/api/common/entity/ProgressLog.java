/** 
 * @(#)ProgressLog.java 1.0.0 2016年4月26日 下午1:42:13  
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
 * @author  jeff
 * @version $Revision:1.0.0, $Date: 2016年4月26日 下午1:42:13 $ 
 */
@Data
@NoArgsConstructor
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "API_T_PROGRESS_LOG")
public class ProgressLog extends BaseEntity {
  
	private static final long serialVersionUID = 2067112323307033539L;
	/**
	 * 进度ID
	 */
	private String progressId;
	/**
	 * 日志内容
	 */
	private String content;
	/**
	 * 备注
	 */
	private String memo;
}
