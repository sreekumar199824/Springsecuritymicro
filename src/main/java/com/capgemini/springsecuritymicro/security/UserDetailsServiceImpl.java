package com.capgemini.springsecuritymicro.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.capgemini.springsecuritymicro.dao.UserDAO;
import com.capgemini.springsecuritymicro.dto.UserInfoBean;

import lombok.Data;

@Data
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

	@Autowired
	private UserDAO dao;
	public static UserInfoBean user;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		UserDetailsImpl udi = new UserDetailsImpl();	
		user=dao.getUser(username);
		if(user!=null)
		udi.setBean(user);
		else {
			throw new UsernameNotFoundException("User Not Found");
		}
		return udi;
	}

}
