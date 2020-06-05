package com.bojin.api.common.entity;

import com.bojin.api.common.constants.enums.InterfaceStatusEnum;
import com.bojin.api.common.constants.enums.YesOrNoEnum;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;

/**
 * 接口表
 *
 * @author samson
 * @date 2016/3/9 9:51
 */
@Data
@NoArgsConstructor
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "API_T_INTERFACE")
public class Interface extends BaseEntity {

	private static final long serialVersionUID = 5807250281618363715L;
	/**
	 * 项目分支id
	 */
	private String branchId;
	/**
	 * 接口名称
	 */
	private String name;
	/**
	 * 访问路径
	 */
	private String url;
	/**
	 * 接口描述
	 */
	private String description;
	/**
	 * 所属类目ID
	 */
	private String categoryId;
	/**
	 * 是否需要授权
	 */
	private int authorize;
	/**
	 * 接口版本
	 */
	private Integer v;
	/**
	 * 接口状态
	 */
	@Enumerated(EnumType.STRING)
	private InterfaceStatusEnum status;
	/**
	 * 是否可见
	 */
	@Enumerated(EnumType.STRING)
	private YesOrNoEnum visible;
}
