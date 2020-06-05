/** 
 * @(#)ProgressLogStart.java 1.0.0 2016年4月28日 上午10:27:41  
 *  
 * Copyright © 2016 善林金融.  All rights reserved.  
 */

package com.bojin.api.core.job.admin;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.bojin.api.common.constants.ConstantsJobName;
import com.bojin.api.common.support.schedule.ScheduleJob;
import com.bojin.api.core.job.AbstractJob;
import com.bojin.api.core.service.admin.ProgressService;

/**
 * 定时更新开始项目
 * 
 * @author jeff
 * @version $Revision:1.0.0, $Date: 2016年4月28日 上午10:27:41 $
 */
@Component("progressLogBeginJob")
@ScheduleJob(cron = "0 0 0 * * ?")
public class ProgressLogBeginJob extends AbstractJob {
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	@Autowired
	private ProgressService progressService;

	@Override
	public void execute() {
		logger.info("执行项目进度更改状态为开始定时任务开始");
		progressService.progressLogBeginTask();
		logger.info("执行项目进度更改状态为开始定时任务结束");

	}

	@Override
	public String getJobName() {
		return ConstantsJobName.PROGRESS_LOG_BEGIN_JOB;
	}

}
