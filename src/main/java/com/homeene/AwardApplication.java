package com.homeene;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
/**
 * @EnableAutoConfiguration//开启自动配置
   @ComponentScan//开启bean扫描
   @Controller//三个注释==springbootapplication
 * @author wus
 *
 */
@SpringBootApplication
public class AwardApplication {

	public static void main(String[] args) {
		SpringApplication.run(AwardApplication.class, args);
	}
}
