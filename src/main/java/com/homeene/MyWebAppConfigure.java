package com.homeene;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import com.homeene.interceptor.UserInterceptor;
@EnableWebMvc
@Configuration
public class MyWebAppConfigure extends WebMvcConfigurerAdapter {

	@Bean
	UserInterceptor userInterceptor() {
        return new UserInterceptor();
    }

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		// TODO Auto-generated method stub
//		registry.addInterceptor(userInterceptor()).addPathPatterns("/**").excludePathPatterns("/user/*");
//		super.addInterceptors(registry);
		
	}

	@Override
	public void addCorsMappings(CorsRegistry registry) {
		// TODO Auto-generated method stub
		  registry.addMapping("/**")  
          .allowedOrigins("*")  
          .allowCredentials(true)  
          .allowedMethods("GET", "POST", "DELETE", "PUT")  
          .maxAge(3600);  
	}

}
