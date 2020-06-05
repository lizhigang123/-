/** 
 * @(#)MyJob.java 1.0.0 2015年12月23日 下午6:19:12  
 *  
 * Copyright © 2015 善林金融.  All rights reserved.  
 */

package com.bojin.api.core.demo.job;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.bojin.api.common.constants.ConstantsJobName;
import com.bojin.api.common.support.schedule.ScheduleJob;
import com.bojin.api.core.job.AbstractJob;
import com.bojin.api.core.repository.UserRepository;
import com.bojin.api.core.service.admin.UserService;

/**
 * 定时任务DEMO
 * 
 * @author samson
 * @version $Revision:1.0.0, $Date: 2015年12月23日 下午6:19:12 $
 */
@Component("demoJob")
//@ScheduleJob(cron = "*/30 * * * * ?")
public class DemoJob extends AbstractJob {

	@Autowired
	private UserRepository userRepository;
	@Autowired
	private UserService userService;

	@Override
	public void execute() {
		userService.findOne("111111111111111111");
		userRepository.findOne("22222222222222222222");
		System.out.println(userRepository);
		System.out.println(userService);
		System.out.println("===========================定时任务DEMO执行=======================");

	}

	@Override
	public String getJobName() {
		return ConstantsJobName.DEMO_JOB;
	}
}
