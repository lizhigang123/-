/** 
 * @(#)ProjectService.java 1.0.0 2016年3月10日 上午11:12:09  
 *  
 * Copyright © 2016 善林金融.  All rights reserved.  
 */

package com.bojin.api.core.service.admin;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bojin.api.common.entity.User;
import com.bojin.api.common.entity.UserRole;
import com.bojin.api.common.exception.SLException;
import com.bojin.api.common.response.ResponseVo;
import com.bojin.api.common.support.repository.page.PageMapRequest;
import com.bojin.api.common.support.repository.page.PageResponse;
import com.bojin.api.core.repository.UserRepository;
import com.bojin.api.core.repository.UserRoleRepository;
import com.bojin.api.core.service.BaseService;

/**
 * 
 * 
 * @author samson
 * @version $Revision:1.0.0, $Date: 2016年3月10日 上午11:12:09 $
 */
@Service
public class UserService extends BaseService<User> {
	@Autowired
	private UserRoleRepository userRoleRepository;

	public List<User> findByType(String type) {
		return getRepository().findAllByType(type);
	}

	public String getUserNameById(String userId) {
		User user = baseRepository.findOne(userId);
		if (user != null) {
			return user.getName();
		}
		return null;
	}

	@Transactional
	public void modifyPassword(User user) throws SLException {
		User userEntity = getRepository().findOne(user.getId());
		if(userEntity != null){
			userEntity.setPassword(new Md5PasswordEncoder().encodePassword(user.getPassword(), null));
 		}else{
			throw new SLException("不存在的用户");
		}
		baseRepository.save(userEntity);
	}

	public User submit(User user) throws Exception {
		User userEntity = null;
		if (StringUtils.isNotBlank(user.getId())) {
			userEntity = baseRepository.findOne(user.getId());
			if (userEntity == null) {
				throw new SLException("不存在的用户");
			}
			userEntity.setName(user.getName());
			userEntity.setLoginName(user.getLoginName());
			userEntity.setEmail(user.getEmail());
		} else {
			userEntity = user;
			userEntity.setPassword(new Md5PasswordEncoder().encodePassword(user.getPassword(), null));
		}
		return baseRepository.save(userEntity);
	}

	public List<User> findAll() {
		return getRepository().findAll();
	}

	public PageResponse<Map<String, Object>> search(PageMapRequest pageRequest) {
		return getRepository().search(pageRequest);
	}

	/**
	 * 模糊匹配开发人员
	 * 
	 * @author fuq
	 * @createTime 2016年4月20日 上午10:52:05
	 * @param pageRequest
	 * @return
	 */
	public PageResponse<Map<String, Object>> match(PageMapRequest pageRequest) {
		return getRepository().matchUser(pageRequest);
	}

	public User findByLoginName(String loginName) {
		return getRepository().findByLoginName(loginName);
	}

	@Transactional
	public ResponseVo deleteUser(String userId) throws SLException {
		User user = getRepository().findOne(userId);
		if (user != null) {
			userRoleRepository.deleteInBatch(getAllUserRole(userId));
			getRepository().delete(userId);
			return ResponseVo.success();
		} else {
			throw new SLException("不存在的用户");
		}

	}

	public List<UserRole> getAllUserRole(String userId) {
		return userRoleRepository.findByUserId(userId);
	}

	public UserRepository getRepository() {
		return (UserRepository) baseRepository;
	}

}
