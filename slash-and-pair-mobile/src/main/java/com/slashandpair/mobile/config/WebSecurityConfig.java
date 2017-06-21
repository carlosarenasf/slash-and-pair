package com.slashandpair.mobile.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

/**
 * This class disables the crs token and authorizes any url request
 * 
 * @author Carlos
 * @author Victor
 * @author Guillermo
 */
@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

	/**
	 * Configure HttpSecuriti.
	 * 
	 * @param http
	 *            HttpSecurity
	 */
	@Override
	protected void configure(HttpSecurity http) throws Exception {

		http.csrf().disable().authorizeRequests().antMatchers("/**").permitAll();

	}

}
