package com.bojin.api.common.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Version;

import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * 所有entity的基类(公共属性提出)
 *
 * @author samson
 */
@MappedSuperclass
@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true, of = {})
@NoArgsConstructor
@EntityListeners({AuditingEntityListener.class})  
public class BaseEntity extends AbstractEntity {

	private static final long serialVersionUID = -6994549768893155889L;

	/**
	 * 创建时间
	 */
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "CREATE_DATE")
	@CreatedDate
	private Date createDate = new Date();

	/**
	 * 创建人
	 */
	@Column(name = "CREATE_USER", length = 50)
	@CreatedBy
	private String createUser;

	/**
	 * 最后更新时间
	 */
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "LAST_UPDATE_DATE")
	@LastModifiedDate
	private Date lastUpdateDate = new Date();
	/**
	 * 最后更新人
	 */
	@Column(name = "LAST_UPDATE_USER", length = 50)
	@LastModifiedBy
	private String lastUpdateUser;

	@Column(name = "VERSION")
	@Version
	private int version;

}
