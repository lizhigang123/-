/** 
 * @(#)PageMapResponse.java 1.0.0 2015年12月19日 下午5:19:42  
 *  
 * Copyright © 2015 善林金融.  All rights reserved.  
 */

package com.bojin.api.common.support.repository.page;

import java.util.List;
import java.util.Map;

/**
 * 
 * 
 * @author samson
 * @version $Revision:1.0.0, $Date: 2015年12月19日 下午5:19:42 $
 */
public class PageMapResponse extends PageResponse<Map<String, Object>> {

	public PageMapResponse(int total, List<Map<String, Object>> data, Map<String, Object> extraData) {
		super(total, data, extraData);
	}

	public PageMapResponse(int total, List<Map<String, Object>> data) {
		super(total, data);
	}

	public PageMapResponse putExtra(String key, Object value) {
		this.extraData.put(key, value);
		return this;
	}

	public PageMapResponse putExtras(Map<String, Object> extraData) {
		this.extraData.putAll(extraData);
		return this;
	}

}
