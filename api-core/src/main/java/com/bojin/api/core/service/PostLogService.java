package com.bojin.api.core.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bojin.api.common.entity.PostLog;
import com.bojin.api.core.repository.PostLogRepository;

@Service
public class PostLogService extends BaseService<PostLog> {
	
	public PostLog findFirstByInterfaceIdOrderByCreateDateDes(String interfaceId){
		return getRepository().findFirstByInterfaceIdOrderByCreateDateDesc(interfaceId);
	}
	
	public PostLog findFirstByInterfaceIdIsNullOrderByCreateDateDesc(){
		return getRepository().findFirstByInterfaceIdIsNullOrderByCreateDateDesc();
	}
	
	public List<PostLog> findByInterfaceIdOrderByCreateDateDes(String interfaceId){
		return getRepository().findByInterfaceIdOrderByCreateDateDesc(interfaceId);
	}
	
	public List<PostLog> findByInterfaceIdIsNullOrderByCreateDateDesc(){
		return getRepository().findByInterfaceIdIsNullOrderByCreateDateDesc();
	}
	
	@Transactional
	public void savePostLog(PostLog postLog) {
		baseRepository.save(postLog);
	}
	
	public PostLogRepository getRepository() {
		return (PostLogRepository) baseRepository;
	}
}
