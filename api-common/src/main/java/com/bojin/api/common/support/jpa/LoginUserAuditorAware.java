package com.bojin.api.common.support.jpa;

import org.springframework.data.domain.AuditorAware;
import org.springframework.stereotype.Component;

import com.bojin.api.common.support.springweb.service.LoginUserContextHolder;

/**   
 * 负责从安全上下文中获取系统的用户信息 设置createBy的值
 * @author  LiYing
 * @version $Revision:1.0.0, $Date: 2016年4月7日 上午11:19:36 $ 
 */
@Component
public class LoginUserAuditorAware implements AuditorAware<String> {
	@Override
	public String getCurrentAuditor() {
		return LoginUserContextHolder.getLoginUser().getId();
	}
}
