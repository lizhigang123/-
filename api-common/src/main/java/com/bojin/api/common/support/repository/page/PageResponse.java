/** 
 * @(#)PageResponse.java 1.0.0 2015年12月19日 下午5:09:28  
 *  
 * Copyright © 2015 善林金融.  All rights reserved.  
 */

package com.bojin.api.common.support.repository.page;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * 分页相应对象
 * 
 * @author samson
 * @version $Revision:1.0.0, $Date: 2015年12月19日 下午5:09:28 $
 */
@Data
@AllArgsConstructor
public class PageResponse<T> {

	protected int total;
	protected List<T> data;
	protected Map<String, Object> extraData = new HashMap<String, Object>();

	public PageResponse(int total, List<T> data) {
		this.total = total;
		this.data = data;
	}

	public int getRecordsFiltered() {
		return this.total;
	}

	public int getRecordsTotal() {
		return this.total;
	}

}
