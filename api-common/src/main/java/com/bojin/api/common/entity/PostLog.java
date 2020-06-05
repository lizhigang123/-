package com.bojin.api.common.entity;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * 
 * 
 * @author SangLy
 * @version $Revision:1.0.0, $Date: 2016年4月26日 下午1:42:13 $
 */
@Data
@NoArgsConstructor
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "API_T_POST_LOG")
public class PostLog extends AbstractEntity {

	private static final long serialVersionUID = 2067112323307033500L;
	
	/**
	 * 接口id
	 */
	String interfaceId;
	/**
	 * 请求地址
	 */
	String serviceUrl;
	/**
	 * 发起请求人的ip
	 */
	String requestIp;
	/**
	 * 请求头json
	 */
	String requestHeaders;
	/**
	 * 请求json体-格式化好的
	 */
	String requestJson;
	/**
	 * 响应信息-格式化好的
	 */
	String responseJson;
	/**
	 * 响应时间--示例值：100ms
	 */
	String reponseTime;
	/**
	 * 创建人
	 */
	String createUser;
	/**
	 * 创建时间
	 */
	Date createDate;

	public PostLog(String interfaceId,String serviceUrl,String requestIp,String requestHeaders,String requestJson, String responseJson, String createUser,Date createDate,String reponseTime) {
		this.interfaceId = interfaceId;
		this.serviceUrl = serviceUrl;
		this.requestIp = requestIp;
		this.requestHeaders = requestHeaders;
		this.requestJson = requestJson;
		this.responseJson = responseJson;
		this.createUser = createUser;
		this.createDate = createDate;
		this.reponseTime = reponseTime;
	}
	
}
