/** 
 * @(#)ShowParamVo.java 1.0.0 2017年8月23日 上午9:12:11  
 *  
 * Copyright © 2017 善林金融.  All rights reserved.  
 */

package com.bojin.api.web.vo;

import lombok.Data;

/**
 * 借款详情页面展示
 * 
 * @author SangLy
 * @version $Revision:1.0.0, $Date: 2017年8月23日 上午9:12:11 $
 */
@Data
public class ShowParamVo {

	private String name;

	private String dataType;

	private String need;

	private String defaultValue;

	private String description;

	private int spaceNum = 0; // 空格个数

	private String isImaginaryLine; // 是否是虚线 true 表示是虚线

	public ShowParamVo(String name, String dataType, String need, String defaultValue, String description, int spaceNum, String isImaginaryLine) {
		super();
		this.name = name;
		this.dataType = dataType;
		this.need = need;
		this.defaultValue = defaultValue;
		this.description = description;
		this.spaceNum = spaceNum;
		this.isImaginaryLine = isImaginaryLine;
	}

}
