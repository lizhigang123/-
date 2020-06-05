package com.bojin.api.common.entity;

import javax.persistence.Entity;

import javax.persistence.Table;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * 参数信息表
 *
 * @author jeff
 * @date 2016/4/1 11:53
 */
@Entity
@Table(name = "API_T_PROPERTY_PARAM_RELATION")
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper=true)
public class PropertyParamRelation extends AbstractEntity {
	
	private static final long serialVersionUID = -2968806067309692854L;
	/**
	 * 项目属性ID
	 */
	private String projectPropertiesId;
	/**
	 * 参数ID
	 */
	private String paramId;
	
}
