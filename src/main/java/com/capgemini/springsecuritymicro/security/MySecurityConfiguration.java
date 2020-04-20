
package com.capgemini.springsecuritymicro.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.capgemini.springsecuritymicro.config.RestAuthenticationEntryPoint;
import com.capgemini.springsecuritymicro.filters.CustomUserPasswordAuthFilter;
import com.capgemini.springsecuritymicro.handlers.MyLogoutSuccessHandler;

@Configuration
@EnableWebSecurity
public class MySecurityConfiguration extends WebSecurityConfigurerAdapter {
	
	@Autowired
	private UserDetailsService userDetailsService;
	
	@Autowired
	private RestAuthenticationEntryPoint restAuthenticationEntryPoint;
	
	@Autowired
	private AuthenticationSuccessHandler authenticationSuccessHandler;
	
	@Autowired
	private MyLogoutSuccessHandler myLogoutSuccessHandler;
	
	@Autowired
	public AuthenticationFailureHandler myAuthFailureHandler;
	
	@Bean
	public UsernamePasswordAuthenticationFilter getUsernamePasswordAuthenticationFilter() throws Exception {
		CustomUserPasswordAuthFilter filter = new CustomUserPasswordAuthFilter();
		filter.setAuthenticationSuccessHandler(authenticationSuccessHandler);
		filter.setAuthenticationFailureHandler(myAuthFailureHandler);
		filter.setAuthenticationManager(authenticationManager());
		return filter;
	}
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userDetailsService);
	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.cors();
		
		http.csrf().disable()
		
		.authorizeRequests().antMatchers(HttpMethod.OPTIONS, "/basicauth").permitAll()
		.and().httpBasic()
        .and()
		.exceptionHandling()
		.authenticationEntryPoint(restAuthenticationEntryPoint)
		.and()
		.authorizeRequests()
		.antMatchers("/template/addprod", "/template/updateCost","/template/getPayments", "/template/getAllProducts","/template/deliveredOn", "/template/updateProd","/template/changeStatus").hasRole("MANUFACTURER")
		.and()
		.authorizeRequests()
		.antMatchers("/template/updateMan", "/template/getAllMans", "/template/deleteMan").hasRole("ADMIN")
		.and()
		.authorizeRequests()
		.antMatchers("/template/dealerOrder", "/template/setSellingPrice", "/template/getProds", "/template/getProd"
				,"/template/setMin", "/template/getMansProds","/template/deliveredOn","/template/updateDealerProduct").hasRole("DEALER")
		.and()
		.authorizeRequests()
		.antMatchers("/template/buyProduct", "/template/getDealerProds").hasRole("CUSTOMER")
		.and()
		.authorizeRequests()
		.antMatchers("/template/getMyOrders","/template/addtocart","/template/getItems","/template/remItem").hasAnyRole("CUSTOMER","DEALER")
		.and()
		.authorizeRequests()
		.antMatchers("/template/register").permitAll()
		.and()
		.addFilterBefore(getUsernamePasswordAuthenticationFilter(), CustomUserPasswordAuthFilter.class)
		.logout()
		.logoutSuccessHandler(myLogoutSuccessHandler);
		
	}
}
