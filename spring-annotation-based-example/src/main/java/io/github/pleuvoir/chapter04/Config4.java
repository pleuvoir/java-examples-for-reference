package io.github.pleuvoir.chapter04;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import io.github.pleuvoir.chapter04.service.InitService;
import io.github.pleuvoir.chapter04.service.InitService2;

@Configuration
@Import(InitService2.class)
@ComponentScan("io.github.pleuvoir.chapter04")
public class Config4 {

	@Bean(initMethod = "init", destroyMethod = "destroy")
	public InitService initService() {
		return new InitService();
	}

}
