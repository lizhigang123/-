/** 
 * @(#)TestController.java 1.0.0 2015年12月14日 下午6:55:22  
 *  
 * Copyright © 2015 善林金融.  All rights reserved.  
 */

package com.bojin.api.web.controller;

import java.util.Date;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bojin.api.common.response.ResponseVo;
import com.bojin.api.common.support.fastjson.Serialize;
import com.bojin.api.common.support.fastjson.SerializeRule;
import com.bojin.api.core.demo.entity.CarEntity;
import com.bojin.api.core.demo.entity.DemoUserEntity;
import com.bojin.api.core.demo.entity.group.ValidGroup;
import com.fasterxml.jackson.annotation.JsonView;

/**
 * 序列化测试类
 * 
 * @author samson
 * @version $Revision:1.0.0, $Date: 2015年12月14日 下午6:55:22 $
 */
@RestController
@RequestMapping("/test/serialize")
public class TestSerializeController extends BaseEntityController<DemoUserEntity> {
	/************************************************************************ 不用注解的效果 ******************************************************************************/
	/**
	 * 默认输出全部属性
	 * 
	 * @return
	 */
	@RequestMapping("/1")
	public ResponseVo _1() {
		return success(getUserEntity1());
	}

	/************************************************************************ include属性使用 ******************************************************************************/

	/**
	 * 输出user对象对应的指定include的值["id", "userName", "age", "birthday"]
	 * 
	 * @return
	 */
	@RequestMapping("/2")
	@Serialize({ @SerializeRule(clazz = DemoUserEntity.class, include = { "id", "userName", "age", "birthday" }) })
	public ResponseVo _2() {
		return success(getUserEntity1());
	}

	/**
	 * 输出User中的["id", "userName", "age", "birthday"]属性和对应Car对象下的所有属性
	 * 必须指定输出User内需要输出的对象car,不然不会输出car
	 * 
	 * @return
	 */
	@RequestMapping("/3")
	@Serialize({ @SerializeRule(clazz = DemoUserEntity.class, include = { "id", "userName", "age", "birthday", "car" }) })
	public ResponseVo _3() {
		return success(getUserEntity1());
	}

	/**
	 * 输出User中的指定属性"id", "userName", "age", "birthday", 和Car的指定属性
	 * 
	 * @return
	 */
	@RequestMapping("/4")
	@Serialize({ @SerializeRule(clazz = DemoUserEntity.class, include = { "id", "userName", "age", "birthday", "car" }), @SerializeRule(clazz = CarEntity.class, include = { "name", "capacity" }) })
	public ResponseVo _4() {
		return success(getUserEntity1());
	}

	/************************************************************************ clazz属性使用 ******************************************************************************/

	/**
	 * 输出user对象对应的指定include的值[ "createDate", "createUser" ]
	 * 
	 * @return
	 */
	@RequestMapping("/5")
	@Serialize({ @SerializeRule(clazz = DemoUserEntity.class, include = { "createDate", "createUser" }) })
	public ResponseVo _5() {
		return success(getUserEntity1());
	}

	/**
	 * 输出user指定属性["createDate", "createUser", "car"]和car指定属性["createDate",
	 * "createUser"]
	 * 
	 * @return
	 */
	@RequestMapping("/6")
	@Serialize({ @SerializeRule(clazz = DemoUserEntity.class, include = { "createDate", "createUser", "car" }), @SerializeRule(clazz = CarEntity.class, include = { "createDate", "createUser" }) })
	public ResponseVo _6() {
		return success(getUserEntity1());
	}

	/**
	 * 输出空对象(输出所有对象上的指定属性["createDate",
	 * "createUser"],由于最外层的对数据包括的ResponseVO也包括在内,所以最外层都没有输出,内层则不会去遍历)
	 * 如需输出指定请参考_8
	 * 
	 * @return
	 */
	@RequestMapping("/7")
	@Serialize({ @SerializeRule(include = { "createDate", "createUser" }) })
	public ResponseVo _7() {
		return success(getUserEntity1());
	}

	/**
	 * 输出{@code ResponseVo} 中的data 和User中的[ "data","car", "createDate",
	 * "createUser" ]字段,car中的[ "data","car", "createDate", "createUser" ]
	 * 
	 * @return
	 */
	@RequestMapping("/8")
	@Serialize({ @SerializeRule(include = { "data", "car", "createDate", "createUser" }) })
	public ResponseVo _8() {
		return success(getUserEntity1());
	}

	/************************************************************************ exclude使用 ******************************************************************************/
	/**
	 * 输出User的除指定属性exclude["id", "userName", "age", "birthday"]的所有属性
	 * 
	 * @return
	 */
	@RequestMapping("/9")
	@Serialize({ @SerializeRule(clazz = DemoUserEntity.class, exclude = { "id", "userName", "age", "birthday" }) })
	public ResponseVo _9() {
		return success(getUserEntity1());
	}

	/************************************************************************ include和exclude混合使用 ******************************************************************************/
	/**
	 * 输出User的除指定属性createDate[createDate](原理为先剔除掉exclude,然后值包含include);详细请参考
	 * {@code SerializePropertyPreFilter.apply}
	 * 
	 * 
	 * @return
	 */
	@RequestMapping("/10")
	@Serialize({ @SerializeRule(clazz = DemoUserEntity.class, include = { "createDate" }, exclude = { "id", "userName", "age", "birthday" }) })
	public ResponseVo _10() {
		return success(getUserEntity1());
	}

	/******************************************************************************************************************************************************************************/
	@RequestMapping("11")
	@JsonView({ ValidGroup.OpenAccount.class })
	public ResponseVo _11() {
		return success(getUserEntity1());
	}

	@RequestMapping("12")
	@JsonView({ ValidGroup.OpenAccount.class, ValidGroup.Play.class })
	public ResponseVo _12() {
		return success(getUserEntity1());
	}

	private DemoUserEntity getUserEntity() {
		DemoUserEntity userEntity = new DemoUserEntity();
		userEntity.setId("test11111");
		userEntity.setUserName("test_user_name");
		userEntity.setBirthday(new Date());
		return userEntity;
	}

	@SuppressWarnings("unused")
	private CarEntity getCarEntity() {
		CarEntity car = new CarEntity("samson", 7);
		return car;
	}

	private DemoUserEntity getUserEntity1() {
		DemoUserEntity userEntity = getUserEntity();
		// userEntity.setCar(getCarEntity());
		return userEntity;
	}
}
