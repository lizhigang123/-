package com.bojin.api.common.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import org.hibernate.annotations.GenericGenerator;

/**
 * 抽象实体 所有PO VO 都需要继承此实体
 * 
 * @author samson
 *
 */
@MappedSuperclass
@Data
@EqualsAndHashCode(of = { "id" })
@NoArgsConstructor
public abstract class AbstractEntity implements Serializable {

	private static final long serialVersionUID = -8186818510051674309L;

	/**
	 * 主键id
	 */
	@Id
	@GeneratedValue(generator = "uuid")
	@GenericGenerator(name = "uuid", strategy = "uuid2")
	@Column(length = 50)
	private String id;

}
