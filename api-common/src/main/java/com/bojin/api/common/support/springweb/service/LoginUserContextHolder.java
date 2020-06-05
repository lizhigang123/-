/** 
 * @(#)LoginUserContextHolder.java 1.0.0 2016年3月17日 下午3:45:48  
 *  
 * Copyright © 2016 善林金融.  All rights reserved.  
 */

package com.bojin.api.common.support.springweb.service;

import com.bojin.api.common.entity.User;

/**
 * 当前登录用户本地线程变量
 * 
 * @author samson
 * @version $Revision:1.0.0, $Date: 2016年3月17日 下午3:45:48 $
 */
public class LoginUserContextHolder {

	private static ThreadLocal<User> loginUser = new ThreadLocal<User>();

	public static User getLoginUser() {
		return loginUser.get();
	}

	public static void setLoginUser(User userEntity) {
		loginUser.set(userEntity);
	}

}
