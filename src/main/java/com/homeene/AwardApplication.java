package com.homeene;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * @EnableAutoConfiguration//开启自动配置 @ComponentScan//开启bean扫描 @Controller//三个注释==springbootapplication @author
 * wus
 *
 */
@SpringBootApplication
@EnableScheduling  
public class AwardApplication extends SpringBootServletInitializer {

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
		// TODO Auto-generated method stub
		return builder.sources(AwardApplication.class);
	}

	public static void main(String[] args) {
		SpringApplication.run(AwardApplication.class, args);
	}
}
