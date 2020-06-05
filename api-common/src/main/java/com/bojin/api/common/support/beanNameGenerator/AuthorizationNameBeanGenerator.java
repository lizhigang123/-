/** 
 * @(#)AuthorizationNameBeanGenerator.java 1.0.0 2016年3月16日 下午3:05:32  
 *  
 * Copyright © 2016 善林金融.  All rights reserved.  
 */

package com.bojin.api.common.support.beanNameGenerator;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.AnnotationBeanNameGenerator;

/**
 * 权限名的bean name生成器
 * 
 * @author samson
 * @version $Revision:1.0.0, $Date: 2016年3月16日 下午3:05:32 $
 */
public class AuthorizationNameBeanGenerator extends AnnotationBeanNameGenerator {

	@Override
	protected String buildDefaultBeanName(BeanDefinition definition) {
		return definition.getBeanClassName();
	}

}
