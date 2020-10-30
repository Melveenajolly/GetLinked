package com.springboot.getlinked;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.servlet.view.UrlBasedViewResolver;
import org.springframework.web.servlet.view.tiles3.TilesConfigurer;
import org.springframework.web.servlet.view.tiles3.TilesView;

	
@SpringBootApplication
public class GetLinkedApplication {

	public static void main(String[] args) {
		SpringApplication.run(GetLinkedApplication.class, args);
	}
	//apache tiles
	@Bean
	public TilesConfigurer tilesConfigurer() {
		TilesConfigurer tilesConfigurer = new TilesConfigurer();
		String[] defs =  { "/WEB-INF/tiles.xml" };
		
		tilesConfigurer.setDefinitions(defs);
		return tilesConfigurer; 	
		
	}
	
	//tilesview
	@Bean
	public UrlBasedViewResolver tilesViewResolver() {
		UrlBasedViewResolver tilesViewResolver = new UrlBasedViewResolver();
		tilesViewResolver.setViewClass(TilesView.class);
		return tilesViewResolver;
		
	}
	
	
	
	@Bean
	PasswordEncoder getEncoder() {
		return new BCryptPasswordEncoder();
	}
}
