package com.bojin.api.common.entity;

import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * 参数信息表
 *
 * @author samson
 * @date 2016/3/9 9:53
 */
@Data
@NoArgsConstructor
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "API_T_PARAM")
public class ParamEntity extends BaseEntity {

	private static final long serialVersionUID = 8480613294530595469L;
	/**
	 * 接口ID
	 */
	private String interfaceId;
	/**
	 * 参数名称
	 */
	private String name;
	/**
	 * 参数中文名称
	 */
	private String nameDesc;
	/**
	 * 参数类型(输入、输出)
	 */
	private String dataType;
	/**
	 * 是否必填
	 */
	private String need;
	/**
	 * 默认值
	 */
	private String defaultValue;
	/**
	 * 描述
	 */
	private String description;
	/**
	 * 类别
	 */
	private String type;
	/**
	 * 父级ID
	 */
	private String parentId;

	/**
	 * 是否可见
	 */
	private String visible;

	/**
	 * 排序
	 */
	private Integer ordered = 0;
}
