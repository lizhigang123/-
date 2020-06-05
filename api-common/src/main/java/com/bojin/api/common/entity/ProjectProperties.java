package com.bojin.api.common.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * 项目属性
 *
 * @author samson
 * @date 2016/3/22 16:22
 */
@Entity
@Table(name = "API_T_PROJECT_PROPERTIES")
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class ProjectProperties extends BaseEntity {

	private static final long serialVersionUID = -1790725057374729051L;
	/**
	 * 项目id
	 */
	private String projectId;
	/**
	 * 属性名
	 */
	private String name;
	/**
	 * 属性中文名
	 */
	private String nameDesc;
	/**
	 * 属性数据类型
	 */
	private String dataType;
	/**
	 * 是否必须
	 */
	private String need;
	/**
	 * 描述
	 */
	private String description;
	/**
	 * 属性类别(公共请求参数,参数模版)
	 */
	private String type;
	/**
	 * 是否可见
	 */
	private String visible;
}
