/** 
 * @(#)Car.java 1.0.0 2015年12月17日 上午10:37:49  
 *  
 * Copyright © 2015 善林金融.  All rights reserved.  
 */

package com.bojin.api.core.demo.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.GroupSequence;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import org.hibernate.validator.constraints.Length;

import com.bojin.api.common.entity.BaseEntity;
import com.bojin.api.core.demo.entity.group.ValidGroup;

/**
 * 
 * 
 * @author samson
 * @version $Revision:1.0.0, $Date: 2015年12月17日 上午10:37:49 $
 */
@Entity
@Table(name = "QUEUE_CAR")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@GroupSequence({ CarEntity.class, ValidGroup.CarGroup.class })
public class CarEntity extends BaseEntity {
	private static final long serialVersionUID = 2733594416548054692L;

	/**
	 * 车名
	 */
	@Column(name = "NAME")
	@NotNull(groups = { ValidGroup.OpenAccount.class, ValidGroup.Play.class })
	@Length(max = 50, groups = { ValidGroup.OpenAccount.class, ValidGroup.Play.class })
	private String name;
	/**
	 * 车容量(人)
	 */
	@Column(name = "CAPACITY")
	private int capacity;

}
