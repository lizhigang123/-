/** 
 * @(#)SchedulConfig.java 1.0.0 2015年12月23日 下午4:01:51  
 *  
 * Copyright © 2015 善林金融.  All rights reserved.  
 */

package com.bojin.api.core;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.quartz.CronTrigger;
import org.quartz.Trigger;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.scheduling.quartz.CronTriggerFactoryBean;
import org.springframework.scheduling.quartz.MethodInvokingJobDetailFactoryBean;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;

import com.bojin.api.common.support.schedule.ScheduleJob;
/**
 * 定时任务自定义配置<br>
 * 
 * @author samson
 * @version $Revision:1.0.0, $Date: 2015年12月23日 下午4:01:51 $
 */
@Configuration
public class ScheduleConfig {

	@Bean
	public SchedulerFactoryBean scheduleFactory(ConfigurableListableBeanFactory beanFactory) {
		SchedulerFactoryBean schedulingQuartz = new SchedulerFactoryBean();
		Map<String, Object> bean = beanFactory.getBeansWithAnnotation(ScheduleJob.class);
		List<CronTrigger> jobs = new ArrayList<CronTrigger>();
		for (Object job : bean.values()) {

			MethodInvokingJobDetailFactoryBean jobDetail = new MethodInvokingJobDetailFactoryBean();
			jobDetail.setTargetObject(job);
			jobDetail.setTargetMethod("invoke");
			jobDetail.setConcurrent(false);
			beanFactory.initializeBean(jobDetail, job.getClass().getSimpleName() + "_jobDetail");

			ScheduleJob scheduleJob = AnnotationUtils.findAnnotation(job.getClass(), ScheduleJob.class);
			CronTriggerFactoryBean cronFactoryBean = new CronTriggerFactoryBean();
			cronFactoryBean.setJobDetail(jobDetail.getObject());
			cronFactoryBean.setCronExpression(scheduleJob.cron());
			beanFactory.initializeBean(cronFactoryBean, job.getClass().getSimpleName() + "_cronBean");
			jobs.add(cronFactoryBean.getObject());
		}
		schedulingQuartz.setTriggers(jobs.toArray(new Trigger[0]));
		return schedulingQuartz;
	}
}
