package com.capgemini.springsecuritymicro.filters;

import java.io.BufferedReader;
import java.io.IOException;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.MediaType;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.capgemini.springsecuritymicro.dto.UserInfoBean;
import com.fasterxml.jackson.databind.ObjectMapper;

public class CustomUserPasswordAuthFilter extends UsernamePasswordAuthenticationFilter{
	
	private UserInfoBean bean;
	@Override
	protected String obtainUsername(HttpServletRequest request) {
		if(request.getContentType().equals(MediaType.APPLICATION_JSON_VALUE)) {
			bean=null;
			try {
				UserInfoBean infoBean = getUserInfo(request);
				return infoBean.getUsername();
			} catch (Exception e) {
				e.printStackTrace();
				return "";
			}
		}
		return super.obtainUsername(request);
	}
	
	@Override
	protected String obtainPassword(HttpServletRequest request) {
		
		if(request.getContentType().equals(MediaType.APPLICATION_JSON_VALUE)) {
			try {
				UserInfoBean infoBean = getUserInfo(request);
				return infoBean.getPassword();
			} catch (Exception e) {
				e.printStackTrace();
				return "";
			}
		}
		
		return super.obtainPassword(request);
	}
	
	private UserInfoBean getUserInfo(HttpServletRequest request) throws IOException {
		if(bean==null) {
			ObjectMapper mapper = new   ObjectMapper();
			String json ="";
			BufferedReader reader = request.getReader();
			while(reader.ready()) {
				json = json+reader.readLine();
			}
			bean = mapper.readValue(json, UserInfoBean.class);
		}
		return bean;
	}
	
	
	
}
