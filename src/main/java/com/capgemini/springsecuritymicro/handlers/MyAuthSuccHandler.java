package com.capgemini.springsecuritymicro.handlers;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import com.capgemini.springsecuritymicro.dto.ResponseClass;
import com.capgemini.springsecuritymicro.security.UserDetailsServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;

@Component
public class MyAuthSuccHandler implements AuthenticationSuccessHandler {

	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
			Authentication authentication) throws IOException, ServletException {
		
		ResponseClass uresp = new ResponseClass();
		uresp.setStatusCode(201);
		uresp.setMessage("Success");
		uresp.setDescription("Login Successfull");
		uresp.setUser(UserDetailsServiceImpl.user);
		response.setStatus(200);
		
		response.setContentType(MediaType.APPLICATION_JSON_VALUE);
		
		ObjectMapper mapper = new ObjectMapper();
		response.getWriter().write(mapper.writeValueAsString(uresp));
		
		/*
		 * String json = mapper.writeValueAsString(uresp);
		 * PrintWriter out = response.getWriter();
		 * out.write(json);
		 */
		
	}

}
