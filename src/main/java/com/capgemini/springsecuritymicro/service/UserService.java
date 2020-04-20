package com.capgemini.springsecuritymicro.service;

import java.util.List;

import com.capgemini.springsecuritymicro.dto.*;


public interface UserService {
	public boolean register(UserInfoBean admin);
	//public UserInfoBean login(UserInfoBean admin);
	public boolean update(UserInfoBean bean);
	public List<OrderDetails> getOrders(int userId);
	public boolean setDeliveredDate(int orderId, String date);
	public boolean addToCart(UserInfoBean bean);
	public List<CartInfoBean> getCartItems(int userId);
	public boolean removeCartItem(int itemId);
	public boolean changeStatus(OrderDetails order);
}
