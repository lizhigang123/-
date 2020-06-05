/** 
 * @(#)ProgressService.java 1.0.0 2016年3月16日 上午11:30:37  
 *  
 * Copyright © 2016 善林金融.  All rights reserved.  
 */

package com.bojin.api.core.service.admin;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bojin.api.common.constants.enums.ProgressStatusEnum;
import com.bojin.api.common.entity.Interface;
import com.bojin.api.common.entity.Progress;
import com.bojin.api.common.entity.ProgressLog;
import com.bojin.api.common.exception.SLException;
import com.bojin.api.common.support.repository.page.PageMapRequest;
import com.bojin.api.common.support.repository.page.PageMapResponse;
import com.bojin.api.common.support.repository.page.PageResponse;
import com.bojin.api.common.util.DateUtils;
import com.bojin.api.core.repository.ProgressRepository;
import com.bojin.api.core.service.BaseService;

/**
 * 
 * 
 * @author samson
 * @version $Revision:1.0.0, $Date: 2016年3月16日 上午11:30:37 $
 */
@Service
public class ProgressService extends BaseService<Progress> {
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	@Autowired
	private ProgressLogService progressLogService;
	@Autowired
	private UserService userService;
	@Autowired
	private InterfaceService interfaceService;
	@Autowired
	private ProjectService projectService;

	public PageResponse<Map<String, Object>> search(PageMapRequest pageRequest) {
		String branckId = ObjectUtils.toString(pageRequest.getParam("branchId"));
		String categoryId = ObjectUtils.toString(pageRequest.getParam("categoryId"));
		String name = ObjectUtils.toString(pageRequest.getParam("name"));
		String status = ObjectUtils.toString(pageRequest.getParam("status"));
		if (StringUtils.isNotBlank(name)) {
			pageRequest.addParam("name", "%" + name + "%");
		}
		String url = ObjectUtils.toString(pageRequest.getParam("url"));
		if (StringUtils.isNotBlank(url)) {
			pageRequest.addParam("url", "%" + url + "%");
		}

		// 多选状态特殊处理
		if (StringUtils.isNotBlank(status)) {
			pageRequest.addParam("status", Arrays.asList(status.split(",")));
		}
		
		PageResponse<Map<String, Object>> pageResponse = null;
		if (StringUtils.isBlank(branckId) && StringUtils.isBlank(categoryId)) {
			pageResponse = getRepository().searchByProjectId(pageRequest);
		} else if (StringUtils.isNotBlank(branckId) && StringUtils.isBlank(categoryId)) {
			pageResponse = getRepository().searchByBranchId(pageRequest);
		} else if (StringUtils.isBlank(branckId) && StringUtils.isNotBlank(categoryId)) {
			pageResponse = getRepository().searchByCategoryId(pageRequest);
		} else {
			pageResponse = new PageMapResponse(0, new ArrayList<Map<String, Object>>());
		}
		return pageResponse;
	}

	public Progress submit(Progress progress, String memo, String branchId) throws SLException {
		Progress progressEntity = null;
		ProgressLog progressLog = new ProgressLog();
		Interface interfaceEntity = interfaceService.findOne(progress.getInterfaceId());
		if (interfaceEntity == null) {
			throw new SLException("不存在的接口");
		}
		if (StringUtils.isNotBlank(progress.getId())) {
			progressEntity = baseRepository.findOne(progress.getId());
			if (progressEntity == null) {
				throw new SLException("不存在的进度");
			}
			String content = "";
			if (!DateUtils.getDay(progressEntity.getStartDate()).equals(DateUtils.getDay(progress.getStartDate()))) {
				content += "开始时间：" + DateUtils.getDay(progressEntity.getStartDate()) + "--" + DateUtils.getDay(progress.getStartDate()) + ",";
			}
			if (!DateUtils.getDay(progressEntity.getEndDate()).equals(DateUtils.getDay(progress.getEndDate()))) {
				content += "结束时间：" + DateUtils.getDay(progressEntity.getEndDate()) + "--" + DateUtils.getDay(progress.getEndDate()) + ",";
			}
			if (!progressEntity.getDeveloper().equals(progress.getDeveloper())) {
				content += "开发人员：" + userService.getUserNameById(progressEntity.getDeveloper()) + "--" + userService.getUserNameById(progress.getDeveloper()) + ",";
			}
			if (progressEntity.getProgress() != progress.getProgress()) {
				content += "开发进度：" + progressEntity.getProgress() + "--" + progress.getProgress() + ",";
			}
			if (!StringUtils.equals(interfaceEntity.getBranchId(), branchId)) {
				content += "分支：" + (StringUtils.isBlank(interfaceEntity.getBranchId()) ? "" : projectService.findOne(interfaceEntity.getBranchId()).getName()) + "--" + projectService.findOne(branchId).getName() + ",";
			}
			progressLog.setContent(content);
			progressEntity.setStartDate(progress.getStartDate());
			progressEntity.setEndDate(progress.getEndDate());
			progressEntity.setDeveloper(progress.getDeveloper());
			progressEntity.setProgress(progress.getProgress());
			if ((progressEntity.getStatus().equals(ProgressStatusEnum.开始) || progressEntity.getStatus().equals(ProgressStatusEnum.逾期)) && progress.getProgress() == 100) {
				progressEntity.setStatus(ProgressStatusEnum.结束);
			}
			interfaceEntity.setBranchId(branchId);
			interfaceService.save(interfaceEntity);

		} else {
			interfaceEntity.setBranchId(branchId);
			progressEntity = progress;
			progressEntity.setStatus(ProgressStatusEnum.新建);
			progressLog.setContent("开始时间：" + DateUtils.getDay(progress.getCreateDate()) + ",结束时间：" + DateUtils.getDay(progress.getEndDate()) + ",开发人员：" + userService.getUserNameById(progress.getDeveloper()) + ",当前进度：" + progress.getProgress() + ",当前版本:" + projectService.findOne(interfaceEntity.getBranchId()).getName());
			interfaceService.save(interfaceEntity);
		}
		progressEntity = baseRepository.save(progressEntity);
		progressLog.setProgressId(progressEntity.getId());
		progressLog.setMemo(memo);
		progressLogService.save(progressLog);
		return progressEntity;
	}

	/**
	 * 定时更新开始项目
	 * 
	 * @author jeff
	 * @createTime 2016年4月27日 下午3:36:18
	 */
	public void progressLogBeginTask() {
		List<Progress> progressBegin = getRepository().findByStartDateBeforeAndStatus(new Date(), ProgressStatusEnum.新建);
		if (progressBegin != null) {
			for (Progress progress : progressBegin) {
				progress.setStatus(ProgressStatusEnum.开始);
				baseRepository.save(progress);
				ProgressLog progressLog = new ProgressLog();
				progressLog.setProgressId(progress.getId());
				progressLog.setContent("系统设置进度开始");
				progressLog.setMemo("系统设置进度开始");
				progressLogService.save(progressLog);
				logger.info("更改进度ID：" + progress.getId() + "状态为开始");
			}
		}
	}

	/**
	 * 定时更新逾期项目
	 * 
	 * @author jeff
	 * @createTime 2016年4月27日 下午3:37:26
	 */
	public void progressLogOverdueTask() {
		List<Progress> progressOverdue = getRepository().findByEndDateBeforeAndStatus(new Date(), ProgressStatusEnum.开始);
		if (progressOverdue != null) {
			for (Progress progress : progressOverdue) {
				progress.setStatus(ProgressStatusEnum.逾期);
				baseRepository.save(progress);
				ProgressLog progressLog = new ProgressLog();
				progressLog.setProgressId(progress.getId());
				progressLog.setContent("系统设置进度逾期");
				progressLog.setMemo("系统设置进度逾期");
				progressLogService.save(progressLog);
				logger.info("更改进度ID：" + progress.getId() + "状态为逾期");
			}
		}
	}

	public ProgressRepository getRepository() {
		return (ProgressRepository) baseRepository;
	}

}
