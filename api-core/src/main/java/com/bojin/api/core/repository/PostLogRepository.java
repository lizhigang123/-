package com.bojin.api.core.repository;

import java.util.List;

import com.bojin.api.common.entity.PostLog;
import com.bojin.api.common.support.repository.BaseRepository;

public interface PostLogRepository extends BaseRepository<PostLog>{

	PostLog findFirstByInterfaceIdOrderByCreateDateDesc(String interfaceId);
	
	PostLog findFirstByInterfaceIdIsNullOrderByCreateDateDesc();
	
	List<PostLog> findByInterfaceIdOrderByCreateDateDesc(String interfaceId);
	
	List<PostLog> findByInterfaceIdIsNullOrderByCreateDateDesc();
	
}
