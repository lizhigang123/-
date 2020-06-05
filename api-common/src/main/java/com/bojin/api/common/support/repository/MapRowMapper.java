/** 
 * @(#)MapRowMapper.java 1.0.0 2016年1月5日 下午3:32:47  
 *  
 * Copyright © 2016 善林金融.  All rights reserved.  
 */

package com.bojin.api.common.support.repository;

import java.sql.Clob;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import org.springframework.jdbc.core.RowMapper;

import com.bojin.api.common.util.SqlUtil;

/**
 * 
 * 
 * @author samson
 * @version $Revision:1.0.0, $Date: 2016年1月5日 下午3:32:47 $
 */
public class MapRowMapper implements RowMapper<Map<String, Object>> {

	private Map<String, String> keyMapping = new HashMap<String, String>();
	private boolean toCamel = false;

	public MapRowMapper(Map<String, String> keyMapping, boolean toCamel) {
		this.keyMapping = keyMapping;
		this.toCamel = toCamel;
	}

	@Override
	public Map<String, Object> mapRow(ResultSet rs, int rowNum) throws SQLException {
		Map<String, Object> t = new HashMap<String, Object>();
		try {
			// 获取查询结果的某些列
			ResultSetMetaData metaData = rs.getMetaData();
			for (int i = 1; i <= metaData.getColumnCount(); i++) {
				String columnName = metaData.getColumnLabel(i);
				Object columnValue = rs.getObject(columnName);
				t.put(keyMapping.containsKey(columnName) ? keyMapping.get(columnName) : (toCamel ? SqlUtil.camelName(columnName) : columnName), columnValue instanceof java.sql.Clob ? clobToString((Clob) columnValue) : columnValue);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return t;
	}

	private String clobToString(Clob clob) throws Exception {
		return (clob != null ? clob.getSubString(1, (int) clob.length()) : null);
	}

}
