/** 
 * @(#)TestValidController.java 1.0.0 2015年12月17日 下午12:45:50  
 *  
 * Copyright © 2015 善林金融.  All rights reserved.  
 */

package com.bojin.api.web.controller;

import java.util.Map;

import javax.validation.Valid;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bojin.api.common.response.ResponseVo;
import com.bojin.api.common.support.springweb.service.ServiceAutoMapping;
import com.bojin.api.common.support.validation.ValidAssigned;
import com.bojin.api.common.validate.annotations.Rule;
import com.bojin.api.common.validate.annotations.Rules;
import com.bojin.api.common.validate.annotations.ValidRules;
import com.bojin.api.core.demo.entity.DemoUserEntity;
import com.bojin.api.core.demo.entity.group.ValidGroup;
import com.bojin.api.core.demo.service.DemoUserService;

/**
 * 数据校验类
 * 
 * @author samson
 * @version $Revision:1.0.0, $Date: 2015年12月17日 下午12:45:50 $
 */
@RestController
@RequestMapping("/test/valid")
public class TestValidController extends BaseEntityController<DemoUserEntity> {

	/************************************************************************ 不校验数据 ******************************************************************************/
	@RequestMapping("/1")
	@ServiceAutoMapping(serviceClass = DemoUserService.class)
	public ResponseVo test_1(DemoUserEntity userEntity) {
		return success();
	}

	/************************************************************************ 基于Spring mvc 基于Hibernate的数据校验@Valid|@Validated(即基于OO)--可以级联校验 ******************************************************************************/
	/**
	 * 校验所有User的所有有校验规则注解的属性,并且如果car只要有属性传过来则就会校验car所有有校验规则的属性,如果无参传递,car不会实例化
	 * ,则此时car并不会被校验,如果为空也需要校验 需要在Car对象的引用上添加{@code @Valid}注解
	 * 
	 * @param userEntity
	 * @return
	 */
	@RequestMapping("/2")
	public ResponseVo test_2(@Valid DemoUserEntity userEntity) {
		return success();
	}

	/**
	 * 同 test_2
	 * 
	 * @param userEntity
	 * @return
	 */
	@RequestMapping("/3")
	public ResponseVo test_3(@Validated DemoUserEntity userEntity) {
		return success();
	}

	/************************************************************************ 基于对Spring mvc 基于Hibernate的指定属性校验(即基于OO) ----不支持级联校验 ******************************************************************************/

	/**
	 * 只会校验给定对象的指定属性,<strong>不会级联校验</strong>
	 * 
	 * @param userEntity
	 * @return
	 */
	@RequestMapping("/4")
	public ResponseVo test_4(@ValidAssigned({ "gender" }) DemoUserEntity userEntity) {
		return success();
	}

	/************************************************************************ 基于对Spring mvc 基于Hibernate的组group校验(即基于OO) ------支持级联校验 ******************************************************************************/
	/**
	 * 值校验指定校验规则指定了组({@code ValidGroup.OpenAccount.class}
	 * )的属性,如果级联校验需要把car上的校验规则也归为{@code ValidGroup.OpenAccount.class}组
	 * 
	 * @param userEntity
	 * @return
	 */
	@RequestMapping("/5")
	public ResponseVo test_5(@Validated({ ValidGroup.OpenAccount.class }) DemoUserEntity userEntity) {
		return success();
	}

	/**
	 * 由于car上的约束没有归为({@code ValidGroup.OpenAccount.class})组,所以如果car为null
	 * 则达不到级联校验的效果,如果car不为空则会级联校验car中的为({@code ValidGroup.OpenAccount.class}
	 * )组的约束
	 * 
	 * @param userEntity
	 * @return
	 */
	@RequestMapping("/6")
	public ResponseVo test_6(@Validated({ ValidGroup.Play.class }) DemoUserEntity userEntity) {
		return success();
	}

	/************************************************************************ 自己封装的validate小插件(基于Map) ----暂时不支持级联校验 ******************************************************************************/
	@ValidRules(@Rules(value = { @Rule(name = "name", required = true, requiredMessage = "姓名不能为空"), @Rule(name = "age", required = true, requiredMessage = "年龄不能为空", max = 100, maxMessage = "年龄最大为100", min = 18, minMessage = "年龄最小为18") }))
	@RequestMapping("/7")
	public ResponseVo test_7(@RequestBody Map<String, Object> params) {
		return success();
	}
}
