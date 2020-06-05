package com.bojin.api.common.entity;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import org.springframework.format.annotation.DateTimeFormat;

/**
 * 项目表
 *
 * @author samson
 * @date 2016/3/9 9:36
 */
@Data
@NoArgsConstructor
@ToString(callSuper = true)
@Entity
@EqualsAndHashCode(callSuper = true)
@Table(name = "API_T_PROJECT")
public class Project extends BaseEntity {

	private static final long serialVersionUID = 6865326277563185492L;
	/**
	 * 父id
	 */
	private String parentId;
	/**
	 * 项目名称
	 */
	private String name;
	/**
	 * 项目开始时间
	 */
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date startDate;
	/**
	 * 负责人
	 */
	private String leader;
	/**
	 * 测试环境地址
	 */
	private String testUrl;
	/**
	 * 生产环境地址
	 */
	private String productionUrl;
	/**
	 * SVN地址
	 */
	private String svnUrl;
	/**
	 * 项目描述
	 */
	private String description;

}
