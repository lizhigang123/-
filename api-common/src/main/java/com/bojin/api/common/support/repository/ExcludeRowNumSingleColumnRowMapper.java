/** 
 * @(#)ExcludeRowNumSingleColumnRowMapper.java 1.0.0 2016年1月12日 上午10:48:43  
 *  
 * Copyright © 2016 善林金融.  All rights reserved.  
 */

package com.bojin.api.common.support.repository;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

import org.springframework.dao.TypeMismatchDataAccessException;
import org.springframework.jdbc.IncorrectResultSetColumnCountException;
import org.springframework.jdbc.core.SingleColumnRowMapper;

/**
 * 单列查询(并排除分页中的ROWNUM_字段)
 * 
 * @author samson
 * @version $Revision:1.0.0, $Date: 2016年1月12日 上午10:48:43 $
 */
public class ExcludeRowNumSingleColumnRowMapper<T> extends SingleColumnRowMapper<T> {
	private Class<T> requiredType;

	public ExcludeRowNumSingleColumnRowMapper() {
		super();
	}

	public ExcludeRowNumSingleColumnRowMapper(Class<T> requiredType) {
		super(requiredType);
		this.requiredType = requiredType;
	}

	@SuppressWarnings("unchecked")
	@Override
	public T mapRow(ResultSet rs, int rowNum) throws SQLException {
		// Validate column count.
		ResultSetMetaData rsmd = rs.getMetaData();
		int nrOfColumns = rsmd.getColumnCount();
		if (nrOfColumns != 2) {
			throw new IncorrectResultSetColumnCountException(2, nrOfColumns);
		}

		// Extract column value from JDBC ResultSet.
		Object result = getColumnValue(rs, 1, this.requiredType);
		if (result != null && this.requiredType != null && !this.requiredType.isInstance(result)) {
			// Extracted value does not match already: try to convert it.
			try {
				return (T) convertValueToRequiredType(result, this.requiredType);
			} catch (IllegalArgumentException ex) {
				throw new TypeMismatchDataAccessException("Type mismatch affecting row number " + rowNum + " and column type '" + rsmd.getColumnTypeName(1) + "': " + ex.getMessage());
			}
		}
		return (T) result;
	}

}
