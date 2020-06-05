/** 
 * @(#)ProgressEntity.java 1.0.0 2016年3月16日 上午11:33:00  
 *  
 * Copyright © 2016 善林金融.  All rights reserved.  
 */

package com.bojin.api.common.entity;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import org.springframework.format.annotation.DateTimeFormat;

import com.bojin.api.common.constants.enums.ProgressStatusEnum;

/**
 * 
 * 
 * @author samson
 * @version $Revision:1.0.0, $Date: 2016年3月16日 上午11:33:00 $
 */
@Entity
@Table(name = "API_T_PROGRESS")
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
public class Progress extends BaseEntity {

	private static final long serialVersionUID = 6112527348506403253L;
	/**
	 * 接口id
	 */
	private String interfaceId;
	/**
	 * 开始时间
	 */
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date startDate;
	/**
	 * 结束时间
	 */
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date endDate;
	/**
	 * 开发人
	 */
	private String developer;
	/**
	 * 进度
	 */
	private int progress;
	/**
	 * 状态
	 */
	@Enumerated(EnumType.STRING)
	private ProgressStatusEnum status;

}
