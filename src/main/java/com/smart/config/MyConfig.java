package com.smart.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
@EnableWebSecurity
public class MyConfig extends WebSecurityConfigurerAdapter {

	@Bean
	public UserDetailsService getCustomUserDetails() {
		return new UserDetailsService();
	}
	
	
	@Bean
	public BCryptPasswordEncoder getPasswordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	@Bean
	public DaoAuthenticationProvider getAuthenticationProvider() {
		DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
		daoAuthenticationProvider.setPasswordEncoder(getPasswordEncoder());
		daoAuthenticationProvider.setUserDetailsService(getCustomUserDetails());
		return daoAuthenticationProvider;
	}

	/// overriding the configuration method from the interface...

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		
		auth.authenticationProvider(getAuthenticationProvider());
		
	}


	@Override
	protected void configure(HttpSecurity http) throws Exception {
		
		http.authorizeRequests().antMatchers("/admin/**").hasAnyRole("ADMIN")
		.antMatchers("/user/**","/contact/**").hasRole("USER").antMatchers("/**").permitAll()
		.and().formLogin().loginPage("/signin").and().csrf().disable();
	}
	
	
}
