/** 
 * @(#)AbstractJob.java 1.0.0 2015年12月24日 上午9:11:33  
 *  
 * Copyright © 2015 善林金融.  All rights reserved.  
 */

package com.bojin.api.core.job;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.bojin.api.common.entity.User;
import com.bojin.api.common.response.ResponseVo;
import com.bojin.api.common.support.springweb.service.LoginUserContextHolder;
import com.bojin.api.core.repository.UserRepository;

/**
 * 定时任务基础类
 * 
 * @author samson
 * @version $Revision:1.0.0, $Date: 2015年12月24日 上午9:11:33 $
 */
public abstract class AbstractJob {
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	@Autowired
	protected UserRepository userRepository;

	/**
	 * 单线程池控制
	 */
	private Executor executor = Executors.newSingleThreadExecutor();

	public abstract void execute();

	public ResponseVo invoke() {
		logger.info("{}  job start thread.................", getJobName());

		// 设置当前登录用户為system
		final User user = userRepository.findByLoginName("system");

		executor.execute(new Runnable() {
			@Override
			public void run() {
				LoginUserContextHolder.setLoginUser(user);
				logger.info("{} job thread start run", getJobName());
				execute();
			}
		});
		return ResponseVo.success();
	}

	public void setExecutor(Executor executor) {
		this.executor = executor == null ? this.executor : executor;
	}

	/**
	 * 定时任务名称
	 * 
	 * @return
	 */
	public abstract String getJobName();
}
