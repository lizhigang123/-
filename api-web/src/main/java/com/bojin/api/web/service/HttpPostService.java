/** 
 * @(#)HttpPostService.java 1.0.0 2017年7月14日 下午5:57:36  
 *  
 * Copyright © 2017 善林金融.  All rights reserved.  
 */ 

package com.bojin.api.web.service;

import java.util.Map;
import java.util.Map.Entry;

import javax.annotation.Resource;

import org.apache.commons.collections.MapUtils;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

/**   
 * 
 *  
 * @author  SangLy
 * @version $Revision:1.0.0, $Date: 2017年7月14日 下午5:57:36 $ 
 */
@Service
public class HttpPostService {
	@Resource(name = "restTemplate")
	RestTemplate restTemplate;

	public String sendService(String url, String requestJosn, Map<String, Object> requestHeadersMap) {
		HttpHeaders headers = new HttpHeaders();
		MediaType type = MediaType.parseMediaType("application/json; charset=UTF-8");
		headers.setContentType(type);
		if (MapUtils.isNotEmpty(requestHeadersMap)) {
			for (Entry<String, Object> entry : requestHeadersMap.entrySet()) {
				headers.add(entry.getKey().toString(), entry.getValue().toString());
			}
		}
		HttpEntity<String> entity = new HttpEntity<String>(requestJosn, headers);
		return restTemplate.postForObject(url, entity, String.class);
	}
}
