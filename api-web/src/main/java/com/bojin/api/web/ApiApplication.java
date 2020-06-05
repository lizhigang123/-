package com.bojin.api.web;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.web.SpringBootServletInitializer;

import com.bojin.api.Project;
import com.bojin.api.common.support.beanNameGenerator.AuthorizationNameBeanGenerator;

@SpringBootApplication(scanBasePackageClasses = { Project.class })
public class ApiApplication extends SpringBootServletInitializer {

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(ApiApplication.class);
	}

	public static void main(String[] args) {
		SpringApplication application = new SpringApplication(ApiApplication.class);
		application.setBeanNameGenerator(new AuthorizationNameBeanGenerator());
		application.run(args);
	}
}
