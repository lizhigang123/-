/** 
 * @(#)UserEntity.java 1.0.0 2015年12月12日 下午5:50:27  
 *  
 * Copyright © 2015 善林金融.  All rights reserved.  
 */

package com.bojin.api.core.demo.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.GroupSequence;
import javax.validation.constraints.NotNull;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import org.hibernate.validator.constraints.Length;
import org.springframework.format.annotation.DateTimeFormat;

import com.bojin.api.common.entity.BaseEntity;
import com.bojin.api.core.demo.entity.group.ValidGroup;
import com.fasterxml.jackson.annotation.JsonView;

/**
 * 
 * 
 * @author samson
 * @version $Revision:1.0.0, $Date: 2015年12月12日 下午5:50:27 $
 */
@Entity
@Table(name = "QUEUE_USER")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@GroupSequence({ DemoUserEntity.class, ValidGroup.UserGroup.class })
public class DemoUserEntity extends BaseEntity {

	private static final long serialVersionUID = -7946900000604177391L;

	@JsonView({ ValidGroup.OpenAccount.class })
	@NotNull(groups = { ValidGroup.OpenAccount.class, ValidGroup.Play.class })
	@Length(max = 50)
	@Column(name = "USER_NAME")
	private String userName;

	@JsonView({ ValidGroup.OpenAccount.class, ValidGroup.Play.class })
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@Column(name = "BIRTHDAY")
	private Date birthday;

	@NotNull
	@Column(name = "GENDER")
	private String gender;

	@NotNull(groups = { ValidGroup.OpenAccount.class })
	@Column(name = "CARD_CODE")
	private String cardCode;

	// @Valid
	// @NotNull(groups = { TestValidGroup.OpenAccount.class })
	// private CarEntity car;

}
