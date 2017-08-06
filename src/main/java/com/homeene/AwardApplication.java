package com.homeene;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
@EnableAutoConfiguration//开启自动配置
@ComponentScan//开启bean扫描
@SpringBootApplication
public class AwardApplication {

	public static void main(String[] args) {
		SpringApplication.run(AwardApplication.class, args);
	}
}
