/** 
 * @(#)PageRequest.java 1.0.0 2015年12月19日 下午3:13:47  
 *  
 * Copyright © 2015 善林金融.  All rights reserved.  
 */

package com.bojin.api.common.support.repository.page;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 分页请求对象
 * 
 * @author samson
 * @version $Revision:1.0.0, $Date: 2015年12月19日 下午3:13:47 $
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PageRequest<T> {
	protected int start = 0;
	protected int length = 10;
	protected T params;

	public PageRequest(int start, int length) {
		this.start = start;
		this.length = length;
	}

}
