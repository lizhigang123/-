package com.bojin.api.common.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * 类目表
 *
 * @author samson
 * @date 2016/3/9 9:41
 */
@Data
@NoArgsConstructor
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "API_T_CATEGORY")
public class Category extends BaseEntity {

    private static final long serialVersionUID = 8197485851440676988L;
    /**
     * 类目名称
     */
    private String name;
    /**
     * 项目ID
     */
    private String projectId;
    /**
     * 是否可见
     */
    private String visible;
    /**
     * 描述信息
     */
    private String description;

    /**
     * 排序
     */
    private Integer sort;

}
