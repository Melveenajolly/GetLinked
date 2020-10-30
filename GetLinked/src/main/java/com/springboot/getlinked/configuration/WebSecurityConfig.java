package com.springboot.getlinked.configuration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
//import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.springboot.getlinked.services.ProfessionalService;
import com.springboot.getlinked.services.UserService;


@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
	
	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	UserService userService;
	
	@Autowired
	ProfessionalService professionalService;
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {

		// @formatter:off

		http
			.csrf()
			.disable()
			.authorizeRequests()
				.antMatchers(
						"/",
						"/about",
						"/register",
						"/signup",
						"/viewAll",
						"/upload-profile-photo")
				.permitAll()
				.antMatchers(
						"/js/*",
						"/css/*",
						"/img/*")
				.permitAll()
			   .antMatchers("/admin")
			   .hasRole("ADMIN")
			   .antMatchers("/view", "/mywork", "/myhires", "/hirepage")
			   .authenticated()
			   .and()
			.formLogin()
				.loginPage("/login")
				.defaultSuccessUrl("/", true)
				.permitAll()
				.and()
			.logout()
				.permitAll();
		
		// @formatter:on
	}
	
//	@Autowired
//	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
//		
//		// @formatter:off
//		auth
//			.inMemoryAuthentication()
//			.withUser("melveena")
//			.password("family")
//			.roles("USER");
//		
//		// @formatter:on
//
//	}

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userService).passwordEncoder(passwordEncoder);
		auth.userDetailsService(professionalService).passwordEncoder(passwordEncoder);
	}
}

