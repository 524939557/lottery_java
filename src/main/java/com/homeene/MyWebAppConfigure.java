package com.homeene;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import com.homeene.interceptor.UserInterceptor;

@Configuration
public class MyWebAppConfigure extends WebMvcConfigurerAdapter {

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		// TODO Auto-generated method stub
	//	registry.addInterceptor(new UserInterceptor()).addPathPatterns("/**");
	//	super.addInterceptors(registry);
	}

}
