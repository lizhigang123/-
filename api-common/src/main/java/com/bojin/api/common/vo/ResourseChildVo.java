/** 
 * @(#)ResourseChildVo.java 1.0.0 2016年4月14日 下午4:05:29  
 *  
 * Copyright © 2016 善林金融.  All rights reserved.  
 */ 

package com.bojin.api.common.vo;

import lombok.Data;

/**   
 * 
 *  
 * @author  jeff
 * @version $Revision:1.0.0, $Date: 2016年4月14日 下午4:05:29 $ 
 */
@Data
public class ResourseChildVo {
	/**
	 * 资源ID
	 */
	private String id;
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
	 * 子节点个数
	 */
	private Integer childNo;
}
