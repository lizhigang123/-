/** 
 * @(#)UserRepositoryCust.java 1.0.0 2015年12月19日 下午2:14:12  
 *  
 * Copyright © 2015 善林金融.  All rights reserved.  
 */

package com.bojin.api.core.demo.repository.custom;

import java.util.Map;

/**
 * 自定义实现对应的接口
 * 
 * @author samson
 * @version $Revision:1.0.0, $Date: 2015年12月19日 下午2:14:12 $
 */
public interface DemoUserRepositoryCust {

	public String testCust(Map<String, Object> map);

	public String testCust1(String a, String b);

}
