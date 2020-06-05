/** 
 * @(#)BaseSpringBootTest.java 1.0.0 2015年12月23日 上午10:39:26  
 *  
 * Copyright © 2015 善林金融.  All rights reserved.  
 */

package com.bojin.api.core;

import org.junit.runner.RunWith;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * 测试基础类
 * 
 * @author samson
 * @version $Revision:1.0.0, $Date: 2015年12月23日 上午10:39:26 $
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(ApiDemoTestApplication.class)
@TestPropertySource("/application.properties")
public class BaseSpringBootTest {

}
