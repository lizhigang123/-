/** 
 * @(#)UserRepository.java 1.0.0 2015年12月12日 下午6:04:27  
 *  
 * Copyright © 2015 善林金融.  All rights reserved.  
 */

package com.bojin.api.core.demo.repository;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.bojin.api.common.support.repository.BaseRepository;
import com.bojin.api.common.support.repository.page.PageRequest;
import com.bojin.api.common.support.repository.page.PageResponse;
import com.bojin.api.common.support.repository.page.QueryExtend;
import com.bojin.api.common.support.repository.page.ReturnMapping;
import com.bojin.api.core.demo.entity.DemoUserEntity;
import com.bojin.api.core.demo.repository.custom.DemoUserRepositoryCust;

/**
 * 
 * 
 * @author samson
 * @version $Revision:1.0.0, $Date: 2015年12月12日 下午6:04:27 $
 */
public interface DemoUserRepository extends BaseRepository<DemoUserEntity>, DemoUserRepositoryCust {

	// ===========================================JPA的功能======================================================

	@Query(nativeQuery = true, value = "select * from queue_user q where (q.user_name=?1)")
	public List<DemoUserEntity> findTest1(String userName);

	@Query(nativeQuery = true, value = "select * from queue_user q where q.user_name=:un")
	public List<DemoUserEntity> findTest1_1(@Param("un") String userName);

	@Query(nativeQuery = true, value = "select q.id,q.user_name,q.birthday from queue_user q where q.user_name=?1")
	public List<DemoUserEntity> findTest1_2(String userName);

	/**
	 * jpa获取非oo的返回值返回是是Object数组
	 * 
	 * @param userName
	 * @return
	 */
	@Query(nativeQuery = true, value = "select q.id,q.user_name,q.birthday from queue_user q where q.user_name=?1")
	public List<Object[]> findTest1_3(String userName);

	// ===========================================基于JPA的本地查询扩展功能======================================================
	/**
	 * 返回List<Map<string,Object>>
	 * 
	 * @param name
	 * @param birthday
	 * @return
	 */
	@QueryExtend
	@Query(nativeQuery = true, value = "select q.id,q.user_name,q.birthday from queue_user q where q.user_name = :name and q.birthday > :birthday")
	public List<Map<String, Object>> findTest2_1(@Param("name") String name, @Param("birthday") Date birthday);

	/**
	 * <dl>
	 * 条件为空过滤
	 * <dd>1.需要校验不为空才加入的条件的请用?( )?包裹住</dd>
	 * </dl>
	 * 
	 * @param name
	 * @param birthday
	 * @return
	 */
	@QueryExtend
	@Query(nativeQuery = true, value = "select * from queue_user q where ?(q.user_name = :name)? and ?(q.birthday < :birthday)?")
	public List<DemoUserEntity> findTest2_2(@Param("name") String name, @Param("birthday") Date birthday);

	/**
	 * <dl>
	 * 条件为空过滤
	 * <dd>1.返回Bean集合</dd>
	 * <dd>1.需要校验不为空才加入的条件的请用?( )?包裹住</dd>
	 * </dl>
	 * 
	 * @param name
	 * @param birthday
	 * @return
	 */
	@QueryExtend
	@Query(nativeQuery = true, value = "select * from queue_user q where ?(q.user_name = :name)? and ?(q.birthday > :birthday)?")
	public List<DemoUserEntity> findTest2_3(@Param("name") String name, @Param("birthday") Date birthday);

	/**
	 * <dl>
	 * 条件为空过滤
	 * <dd>1.返回map集合</dd>
	 * <dd>1.需要校验不为空才加入的条件的请用?( )?包裹住</dd>
	 * </dl>
	 * 
	 * @param name
	 * @param birthday
	 * @return
	 */
	@QueryExtend
	@Query(nativeQuery = true, value = "select * from queue_user q where ?(q.user_name = :name)? and ?(q.birthday < :birthday)?")
	public List<Map<String, Object>> findTest2_4(@Param("name") String name, @Param("birthday") Date birthday);

	/**
	 * <dl>
	 * 条件为空过滤
	 * <dd>1.返回map集合</dd>
	 * <dd>2.返回的map根据{@code ReturnMapping}来进行映射</dd>
	 * <dd>3.需要校验不为空才加入的条件的请用?( )?包裹住</dd>
	 * </dl>
	 * 
	 * @param name
	 * @param birthday
	 * @return
	 */
	@QueryExtend({ @ReturnMapping(from = "ID", to = "id"), @ReturnMapping(from = "USER_NAME", to = "userNameTest"), @ReturnMapping(from = "BIRTHDAY", to = "startTime") })
	@Query(nativeQuery = true, value = "select * from queue_user q where ?(q.user_name = :name)? and ?(q.birthday < :birthday)?")
	public List<Map<String, Object>> findTest2_5(@Param("name") String name, @Param("birthday") Date birthday);

	/**
	 * 分页返回的结果
	 * <dl>
	 * 分页返回的结果
	 * <dd>1.参数可以没有{@code PageRequest },此时是查询所有列表</dd>
	 * <dd>2.返回的map根据{@code ReturnMapping}来进行映射</dd>
	 * <dd>3.需要校验不为空才加入的条件的请用?( )?包裹住</dd>
	 * </dl>
	 * 
	 * @param name
	 * @param birthday
	 * @return
	 */
	@QueryExtend
	@Query(nativeQuery = true, value = "select * from queue_user q where ?(q.user_name = :name)? and ?(q.birthday < :birthday)?")
	public PageResponse<Map<String, Object>> findTest2_6(@Param("name") String name, @Param("birthday") Date birthday);

	/**
	 * 带{@code PageRequest} 的分页查询
	 * 
	 * @param pageRequest
	 * @return
	 */
	@QueryExtend
	@Query(nativeQuery = true, value = "select * from queue_user q where ?(q.user_name = :name)? and ?(q.birthday < :birthday)?")
	public PageResponse<Map<String, Object>> findTest2_7(PageRequest<Map<String, Object>> pageRequest);

	/**
	 * 分页查询可以传递多个参数(占位符匹配的时候按入参的先后顺序查找,直到 查找到相应key且不为空白的值填充)
	 * 
	 * @param name
	 * @param birthday
	 * @param pageRequest
	 * @return
	 */
	@QueryExtend
	@Query(nativeQuery = true, value = "select * from queue_user q where ?(q.user_name = :name)? and ?(q.birthday < :birthday)?")
	public PageResponse<Map<String, Object>> findTest2_8(@Param("name") String name, @Param("birthday") Date birthday, @Param("") PageRequest<Map<String, Object>> pageRequest);

	/**
	 * 返回实体的分页
	 * 
	 * @param pageRequest
	 * @return
	 */
	@QueryExtend
	@Query(nativeQuery = true, value = "select * from queue_user q where ?(q.user_name = :name)? and ?(q.birthday < :birthday)?")
	public PageResponse<DemoUserEntity> findTest2_10(PageRequest<Map<String, Object>> pageRequest);

	@QueryExtend
	@Query(nativeQuery = true, value = "select * from queue_user q where ?(q.user_name = :name)? and ?(q.birthday < :birthday)?")
	public PageResponse<DemoUserEntity> findTest2_11(@Param("") PageRequest<Map<String, Object>> pageRequest, @Param("name") String name, @Param("birthday") Date birthday);

	/**
	 * 分页查询返回Map,并且指定返回结果的key值映射
	 * 
	 * @param pageRequest
	 * @return
	 */
	@QueryExtend({ @ReturnMapping(from = "user_name", to = "nickName") })
	@Query(nativeQuery = true, value = "select q.user_name from queue_user q where ?(q.user_name = :name)? and ?(q.birthday < :birthday)?")
	public PageResponse<String> findTest2_12(PageRequest<Map<String, Object>> pageRequest);

}
