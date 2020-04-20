package com.capgemini.springsecuritymicro.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.capgemini.springsecuritymicro.dao.UserDAO;
import com.capgemini.springsecuritymicro.dto.CartInfoBean;
import com.capgemini.springsecuritymicro.dto.OrderDetails;
import com.capgemini.springsecuritymicro.dto.UserInfoBean;

@Service
public class UserServiceImplementation implements UserService {
	
	@Autowired
	private UserDAO dao;

	@Override
	public boolean register(UserInfoBean user) {
		return dao.register(user);
	}

	/*@Override
	public UserInfoBean login(UserInfoBean user) {
		return dao.login(user);
	} */

	@Override
	public boolean update(UserInfoBean bean) {
		return dao.update(bean);
	}

	@Override
	public List<OrderDetails> getOrders(int userId) {
		return dao.getOrders(userId);
	}

	@Override
	public boolean setDeliveredDate(int orderId, String date) {
		return dao.setDeliveredDate(orderId, date);
	}

	@Override
	public boolean addToCart(UserInfoBean bean) {
		
		return dao.addToCart(bean);
	}

	@Override
	public List<CartInfoBean> getCartItems(int userId) {
		return dao.getCartItems(userId);
	}

	@Override
	public boolean removeCartItem(int itemId) {
		return dao.removeCartItem(itemId);
	}
	
	@Override
	public boolean changeStatus(OrderDetails order) {
		return dao.changeStatus(order);
	}
}
