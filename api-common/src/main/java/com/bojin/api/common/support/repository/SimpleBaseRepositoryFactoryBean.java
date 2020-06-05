/**
 * Copyright (c) 2005-2012 https://github.com/zhangkaitao
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 */
package com.bojin.api.common.support.repository;

import java.io.Serializable;
import java.lang.invoke.MethodHandles;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.persistence.EntityManager;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.springframework.aop.ProxyMethodInvocation;
import org.springframework.aop.framework.ProxyFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.support.JpaEntityInformation;
import org.springframework.data.jpa.repository.support.JpaRepositoryFactory;
import org.springframework.data.jpa.repository.support.JpaRepositoryFactoryBean;
import org.springframework.data.repository.Repository;
import org.springframework.data.repository.core.RepositoryInformation;
import org.springframework.data.repository.core.RepositoryMetadata;
import org.springframework.data.repository.core.support.AbstractRepositoryMetadata;
import org.springframework.data.repository.core.support.RepositoryFactorySupport;
import org.springframework.data.repository.core.support.RepositoryProxyPostProcessor;
import org.springframework.data.repository.query.QueryLookupStrategy;
import org.springframework.data.repository.query.RepositoryQuery;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import com.bojin.api.common.support.repository.page.QueryExtend;

/**
 * 基础Repostory简单实现 factory bean 请参考 spring-data-jpa-reference [1.4.2. Adding
 * custom behaviour to all repositories]
 * <p>
 * User: Zhang Kaitao
 * <p>
 * Date: 13-5-5 上午11:57
 * <p>
 * Version: 1.0
 */
public class SimpleBaseRepositoryFactoryBean<R extends JpaRepository<M, ID>, M, ID extends Serializable> extends JpaRepositoryFactoryBean<R, M, ID> {

	@Autowired
	private JdbcTemplate jdbcTemplate;
	@Autowired
	private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

	protected RepositoryFactorySupport createRepositoryFactory(EntityManager entityManager) {
		return new SimpleBaseRepositoryFactory<M, ID>(entityManager, jdbcTemplate, namedParameterJdbcTemplate);
	}
}

class SimpleBaseRepositoryFactory<M, ID extends Serializable> extends JpaRepositoryFactory {

	private static final Class<?> TRANSACTION_PROXY_TYPE = getTransactionProxyType();
	private List<RepositoryProxyPostProcessor> postProcessors;
	private static final boolean IS_JAVA_8 = org.springframework.util.ClassUtils.isPresent("java.util.Optional", RepositoryFactorySupport.class.getClassLoader());
	private ClassLoader classLoader = org.springframework.util.ClassUtils.getDefaultClassLoader();

	private EntityManager entityManager;

	private JdbcTemplate jdbcTemplate;

	private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

	public SimpleBaseRepositoryFactory(EntityManager entityManager, JdbcTemplate jdbcTemplate, NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
		super(entityManager);
		this.entityManager = entityManager;
		this.jdbcTemplate = jdbcTemplate;
		this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
	}

	/**
	 * Returns a repository instance for the given interface backed by an
	 * instance providing implementation logic for custom logic.
	 * 
	 * @param <T>
	 * @param repositoryInterface
	 * @param customImplementation
	 * @return
	 */
	@SuppressWarnings({ "unchecked" })
	public <T> T getRepository(Class<T> repositoryInterface, Object customImplementation) {

		RepositoryMetadata metadata = getRepositoryMetadata(repositoryInterface);
		Class<?> customImplementationClass = null == customImplementation ? null : customImplementation.getClass();
		RepositoryInformation information = getRepositoryInformation(metadata, customImplementationClass);

		validate(information, customImplementation);

		Object target = getTargetRepository(information);

		// Create proxy
		ProxyFactory result = new ProxyFactory();
		result.setTarget(target);
		result.setInterfaces(new Class[] { repositoryInterface, Repository.class });

		if (TRANSACTION_PROXY_TYPE != null) {
			result.addInterface(TRANSACTION_PROXY_TYPE);
		}

		for (RepositoryProxyPostProcessor processor : postProcessors) {
			processor.postProcess(result, information);
		}

		if (IS_JAVA_8) {
			result.addAdvice(new DefaultMethodInvokingMethodInterceptor());
		}

		result.addAdvice(new SimpleQueryExecutorMethodInterceptor(information, customImplementation, target));
		return (T) result.getProxy(classLoader);
	}

	@Override
	public void addRepositoryProxyPostProcessor(RepositoryProxyPostProcessor processor) {
		super.addRepositoryProxyPostProcessor(processor);
		if (this.postProcessors == null) {
			postProcessors = new ArrayList<RepositoryProxyPostProcessor>();
		}
		this.postProcessors.add(processor);
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	protected Object getTargetRepository(RepositoryMetadata metadata) {
		Class<?> repositoryInterface = metadata.getRepositoryInterface();

		if (isBaseRepository(repositoryInterface)) {

			JpaEntityInformation<M, String> entityInformation = getEntityInformation((Class<M>) metadata.getDomainType());
			SimpleBaseRepository repository = new SimpleBaseRepository(entityInformation, entityManager, jdbcTemplate, namedParameterJdbcTemplate);
			return repository;
		}
		return super.getTargetRepository(metadata);
	}

	protected Class<?> getRepositoryBaseClass(RepositoryMetadata metadata) {
		if (isBaseRepository(metadata.getRepositoryInterface())) {
			return SimpleBaseRepository.class;
		}
		return super.getRepositoryBaseClass(metadata);
	}

	private boolean isBaseRepository(Class<?> repositoryInterface) {
		return BaseRepository.class.isAssignableFrom(repositoryInterface);
	}

	@SuppressWarnings("deprecation")
	@Override
	protected QueryLookupStrategy getQueryLookupStrategy(QueryLookupStrategy.Key key) {
		return super.getQueryLookupStrategy(key);
	}

	/**
	 * Returns the {@link RepositoryMetadata} for the given repository
	 * interface.
	 * 
	 * @param repositoryInterface
	 * @return
	 */
	protected RepositoryMetadata getRepositoryMetadata(Class<?> repositoryInterface) {
		return AbstractRepositoryMetadata.getMetadata(repositoryInterface);
	}

	/**
	 * Validates the given repository interface as well as the given custom
	 * implementation.
	 * 
	 * @param repositoryInformation
	 * @param customImplementation
	 */
	private void validate(RepositoryInformation repositoryInformation, Object customImplementation) {

		if (null == customImplementation && repositoryInformation.hasCustomMethod()) {

			throw new IllegalArgumentException(String.format("You have custom methods in %s but not provided a custom implementation!", repositoryInformation.getRepositoryInterface()));
		}

		validate(repositoryInformation);
	}

	/**
	 * Returns the TransactionProxy type or {@literal null} if not on the
	 * classpath.
	 * 
	 * @return
	 */
	private static Class<?> getTransactionProxyType() {

		try {
			return org.springframework.util.ClassUtils.forName("org.springframework.transaction.interceptor.TransactionalProxy", null);
		} catch (ClassNotFoundException o_O) {
			return null;
		}
	}

	/**
	 * Method interceptor to invoke default methods on the repository proxy.
	 *
	 * @author Oliver Gierke
	 */
	private static class DefaultMethodInvokingMethodInterceptor implements MethodInterceptor {

		private final Constructor<MethodHandles.Lookup> constructor;

		/**
		 * Creates a new {@link DefaultMethodInvokingMethodInterceptor}.
		 */
		public DefaultMethodInvokingMethodInterceptor() {

			try {
				this.constructor = MethodHandles.Lookup.class.getDeclaredConstructor(Class.class, int.class);

				if (!constructor.isAccessible()) {
					constructor.setAccessible(true);
				}
			} catch (Exception o_O) {
				throw new IllegalStateException(o_O);
			}
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see
		 * org.aopalliance.intercept.MethodInterceptor#invoke(org.aopalliance
		 * .intercept.MethodInvocation)
		 */
		@Override
		public Object invoke(MethodInvocation invocation) throws Throwable {

			Method method = invocation.getMethod();

			if (!org.springframework.data.util.ReflectionUtils.isDefaultMethod(method)) {
				return invocation.proceed();
			}

			Object[] arguments = invocation.getArguments();
			Class<?> declaringClass = method.getDeclaringClass();
			Object proxy = ((ProxyMethodInvocation) invocation).getProxy();

			return constructor.newInstance(declaringClass, MethodHandles.Lookup.PRIVATE).unreflectSpecial(method, declaringClass).bindTo(proxy).invokeWithArguments(arguments);
		}
	}

	/**
	 * This {@code MethodInterceptor} intercepts calls to methods of the custom
	 * implementation and delegates the to it if configured. Furthermore it
	 * resolves method calls to finders and triggers execution of them. You can
	 * rely on having a custom repository implementation instance set if this
	 * returns true.
	 * 
	 * @author Oliver Gierke
	 */
	public class SimpleQueryExecutorMethodInterceptor extends QueryExecutorMethodInterceptor {
		protected final Map<Method, RepositoryQuery> method2Query;
		protected final Map<Method, RepositoryQueryExtend> method2QueryExtend = new ConcurrentHashMap<Method, RepositoryQueryExtend>();

		@SuppressWarnings("unchecked")
		public SimpleQueryExecutorMethodInterceptor(RepositoryInformation repositoryInformation, Object customImplementation, Object target) {
			super(repositoryInformation, customImplementation, target);
			Field field;
			try {
				field = this.getClass().getSuperclass().getDeclaredField("queries");
				field.setAccessible(true);
				method2Query = (Map<Method, RepositoryQuery>) field.get(this);
			} catch (Exception e) {
				throw new RuntimeException("扩展spring jpa repository 启动失败!");
			}
			populateCustomerQuery();
		}

		@Override
		public Object invoke(MethodInvocation invocation) throws Throwable {
			if (method2QueryExtend.containsKey(invocation.getMethod())) {
				return invokeCustomer(invocation);
			} else {
				return super.invoke(invocation);
			}
		}

		protected Object invokeCustomer(MethodInvocation invocation) {
			RepositoryQueryExtend repositoryQueryExtend = method2QueryExtend.get(invocation.getMethod());
			OptimizeSql optimizeSql = repositoryQueryExtend.getOptimizeSql(invocation.getArguments());
			return repositoryQueryExtend.execute(namedParameterJdbcTemplate, optimizeSql);
		}

		/**
		 * <dl>
		 * 是否是自定义回调方法:
		 * <dd>1.方法上含有{@code QueryExtend} 注解的</dd>
		 * <dd>2.参数有且仅为一个且为 {@code PageRequest},并且返回值类型为{@code PageResponse}</dd>
		 * </dl>
		 * 
		 * @param method
		 * @return
		 */
		protected void populateCustomerQuery() {
			for (Method method : method2Query.keySet()) {
				if (AnnotationUtils.findAnnotation(method, QueryExtend.class) != null) {
					method2QueryExtend.put(method, new RepositoryQueryExtend(method, method2Query.get(method)));
				}
			}
		}

	}
}
