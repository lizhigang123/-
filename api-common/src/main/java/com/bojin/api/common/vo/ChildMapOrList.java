package com.bojin.api.common.vo;

import lombok.Data;

/**
 * 生成模拟数据时候用到的vo
 * 
 * @author SangLy
 *
 */
@Data
public class ChildMapOrList {

	private String parentKey;
	
	private String parentType;
	
	private String thisKey;
	
	private String thisType;
	
	private String noteFortargetMapOrList;
	
}
