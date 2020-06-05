/** 
 * @(#)PageMapRequest.java 1.0.0 2015年12月19日 下午5:13:15  
 *  
 * Copyright © 2015 善林金融.  All rights reserved.  
 */

package com.bojin.api.common.support.repository.page;

import java.util.HashMap;
import java.util.Map;

/**
 * 
 * 
 * @author samson
 * @version $Revision:1.0.0, $Date: 2015年12月19日 下午5:13:15 $
 */
public class PageMapRequest extends PageRequest<Map<String, Object>> {
	public PageMapRequest(int start, int length, Map<String, Object> params) {
		super(start, length, params);
		this.params = this.params == null ? new HashMap<String, Object>() : this.params;
	}

	public PageMapRequest addParam(String key, Object value) {
		this.params.put(key, value);
		return this;
	}

	public PageMapRequest addParams(Map<String, Object> params) {
		this.params.putAll(params);
		return this;
	}

	public Object getParam(String key) {
		return params.get(key);
	}

	public boolean containsKey(String key) {
		return params.containsKey(key);
	}
}
