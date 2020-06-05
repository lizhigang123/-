/** 
 * @(#)JsonUtils.java 1.0.0 2015年12月15日 上午10:51:29  
 *  
 * Copyright © 2015 善林金融.  All rights reserved.  
 */

package com.bojin.api.common.util;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import com.alibaba.fastjson.serializer.AfterFilter;
import com.alibaba.fastjson.serializer.BeforeFilter;
import com.alibaba.fastjson.serializer.JSONSerializer;
import com.alibaba.fastjson.serializer.NameFilter;
import com.alibaba.fastjson.serializer.PropertyFilter;
import com.alibaba.fastjson.serializer.PropertyPreFilter;
import com.alibaba.fastjson.serializer.SerializeConfig;
import com.alibaba.fastjson.serializer.SerializeFilter;
import com.alibaba.fastjson.serializer.SerializeWriter;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.serializer.ValueFilter;
import com.bojin.api.common.support.fastjson.Serialize;
import com.bojin.api.common.support.fastjson.SerializePropertyPreFilter;
import com.bojin.api.common.support.fastjson.SerializeRule;

/**
 * 使用fastjson进行OO 和 json 进行转换
 * 
 * <pre>
 * <h3>1.Fastjson的SerializerFeature序列化属性:</h3>
 * 	QuoteFieldNames———-输出key时是否使用双引号,默认为true
 * 	WriteMapNullValue——–是否输出值为null的字段,默认为false
 * 	WriteNullNumberAsZero—-数值字段如果为null,输出为0,而非null
 * 	WriteNullListAsEmpty—–List字段如果为null,输出为[],而非null
 * 	WriteNullStringAsEmpty—字符类型字段如果为null,输出为”“,而非null
 * 	WriteNullBooleanAsFalse–Boolean字段如果为null,输出为false,而非null
 * 
 * @param joinPoint
 * </pre>
 * 
 * @author samson
 * @version $Revision:1.0.0, $Date: 2015年12月15日 上午10:51:29 $
 */
public class JSONUtils {

	public static final Map<Method, SerializeFilter[]> method2FiltersCache = new HashMap<Method, SerializeFilter[]>();

	public static final Map<Serialize, SerializeFilter[]> serialize2FiltersCache = new HashMap<Serialize, SerializeFilter[]>();

	/**
	 * 默认格式的json转换
	 * 
	 * <pre>
	 * 1.
	 * </pre>
	 * 
	 * @param object
	 * @return
	 */
	public static String toJSONString(Object object) {
		return toJSONString(object, new SerializeFilter[0]);
	}

	public static String toJSONString(Object object, Method method, SerializerFeature... features) {
		SerializeFilter[] filters = new SerializeFilter[0];
		if (!method2FiltersCache.containsKey(method)) {
			method2FiltersCache.put(method, JSONUtilsHelper.getSerializeFiletersFromMethod(method));
		}
		filters = method2FiltersCache.get(method);
		return toJSONString(object, filters, features);
	}

	public static String toJSONString(Object object, SerializeFilter[] filters, SerializerFeature... features) {
		return toJSONString(object, new SerializeConfig(), filters, features);
	}

	/**
	 * 公用对象2json转换接口
	 * 
	 * @param object
	 *            待转换值
	 * @param config
	 *            序列化配置
	 * @param filters
	 *            序列化过滤器
	 * @param features
	 *            序列化特色
	 * @return
	 */
	public static String toJSONString(Object object, SerializeConfig config, SerializeFilter[] filters, SerializerFeature... features) {
		SerializeWriter out = new SerializeWriter();
		try {
			JSONSerializer serializer = new JSONSerializer(out, config);
			for (com.alibaba.fastjson.serializer.SerializerFeature feature : features) {
				serializer.config(feature, true);
			}

			JSONUtilsHelper.setFilter(serializer, filters);

			serializer.write(object);

			return out.toString();
		} finally {
			out.close();
		}
	}

	/**
	 * JSONUtil工具
	 * 
	 * @author samson
	 *
	 */
	private static class JSONUtilsHelper {

		private static void setFilter(JSONSerializer serializer, SerializeFilter... filters) {
			for (SerializeFilter filter : filters) {
				setFilter(serializer, filter);
			}
		}

		private static void setFilter(JSONSerializer serializer, SerializeFilter filter) {
			if (filter == null) {
				return;
			}

			if (filter instanceof PropertyPreFilter) {
				serializer.getPropertyPreFilters().add((PropertyPreFilter) filter);
			}

			if (filter instanceof NameFilter) {
				serializer.getNameFilters().add((NameFilter) filter);
			}

			if (filter instanceof ValueFilter) {
				serializer.getValueFilters().add((ValueFilter) filter);
			}

			if (filter instanceof PropertyFilter) {
				serializer.getPropertyFilters().add((PropertyFilter) filter);
			}

			if (filter instanceof BeforeFilter) {
				serializer.getBeforeFilters().add((BeforeFilter) filter);
			}

			if (filter instanceof AfterFilter) {
				serializer.getAfterFilters().add((AfterFilter) filter);
			}
		}

		private static SerializeFilter[] getSerializeFiletersFromMethod(Method method) {
			Serialize serialize = method.getAnnotation(Serialize.class);
			SerializeFilter[] serializeFilters = new SerializeFilter[serialize.value().length];
			if (serialize != null && serialize.value().length > 0) {
				for (int i = 0; i < serialize.value().length; i++) {
					SerializeRule serializeRule = serialize.value()[i];
					serializeFilters[i] = new SerializePropertyPreFilter(Object.class.equals(serializeRule.clazz()) ? null : serializeRule.clazz(), serializeRule.include(), serializeRule.exclude());
				}
			}
			return serializeFilters;
		}
	}
}
