/** 
 * @(#)BaseService.java 1.0.0 2015年12月12日 上午10:32:27  
 *  
 * Copyright © 2015 善林金融.  All rights reserved.  
 */

package com.bojin.api.core.service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * service基类
 * 
 * @author samson
 * @version $Revision:1.0.0, $Date: 2015年12月12日 上午10:32:27 $
 */
public abstract class AbstractService {

	@PersistenceContext
	protected EntityManager entityManager;

	protected Logger logger = LoggerFactory.getLogger(this.getClass());

}
