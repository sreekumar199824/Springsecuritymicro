package com.capgemini.springsecuritymicro.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.capgemini.springsecuritymicro.dto.*;

import com.capgemini.springsecuritymicro.exceptions.EmailAlreadyExistsException;
import com.capgemini.springsecuritymicro.exceptions.MobileNumberAlreadyExistsException;
import com.capgemini.springsecuritymicro.service.UserService;


@RestController
@CrossOrigin(origins = "*",allowCredentials = "true",allowedHeaders = "*",exposedHeaders="Access-Control-Allow-Origin")
public class UserController {
	
	@Autowired
	private UserService service;
	
	@GetMapping(path = "/basicauth", produces = MediaType.APPLICATION_JSON_VALUE) 
	public ResponseClass basicAuth() {
		ResponseClass resp = new ResponseClass();
		resp.setStatusCode(201);
		resp.setMessage("You are authenticated");
		return resp;
	}
	
	@PostMapping(path = "/template/register",produces = MediaType.APPLICATION_JSON_VALUE,consumes=MediaType.APPLICATION_JSON_VALUE)
	public ResponseClass register(@RequestBody UserInfoBean user) {
		ResponseClass resp = new ResponseClass();
		
		try {
		boolean result = service.register(user);
		if(result) {
			resp.setStatusCode(201);
			resp.setMessage("Success");
			resp.setDescription("Registered Successfully");
			resp.setUser(user);
			return resp;
		}
		} catch (EmailAlreadyExistsException exp) {
			resp.setStatusCode(407);
			resp.setMessage("Failed");
			resp.setDescription(exp.getMessage());
			return resp;
		} catch (MobileNumberAlreadyExistsException exp) {
			resp.setStatusCode(408);
			resp.setMessage("Failed");
			resp.setDescription(exp.getMessage());
			return resp;
		} catch (Exception exp) {
			resp.setStatusCode(401);
			resp.setMessage("Failed");
			resp.setDescription("Registration Unsuccessfull");
			return resp;
		}
		return resp;
	}
	
	
	@PutMapping(path = "/template/update",produces = MediaType.APPLICATION_JSON_VALUE,consumes=MediaType.APPLICATION_JSON_VALUE)
	public ResponseClass update(@RequestBody UserInfoBean user) {
		ResponseClass resp = new ResponseClass();
		if(service.update(user)) {
			resp.setStatusCode(201);
			resp.setMessage("Success");
			resp.setDescription("Updation Successfull");
			resp.setUser(user);
			return resp;
		} else {
			resp.setStatusCode(401);
			resp.setMessage("Failed");
			resp.setDescription("Updation failed");
			return resp;
		}
	}
	
	@GetMapping(path = "/template/deliveredOn",produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseClass deliveredOn(@RequestParam("orderId")int orderId, @RequestParam("deliveredOn")String deliveredOn) {
		ResponseClass resp = new ResponseClass();
		if(service.setDeliveredDate(orderId,deliveredOn)) {
			resp.setStatusCode(201);
			resp.setMessage("Success");
			resp.setDescription("Updation Successfull");
			return resp;
		} else {
			resp.setStatusCode(401);
			resp.setMessage("Failed");
			resp.setDescription("Not a valid date");
			return resp;
		}
	}
	
	@GetMapping(path = "/template/getMyOrders",produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseClass getOrder(@RequestParam("userId")int userId ) {
		ResponseClass resp = new ResponseClass();
		List<OrderDetails> order = service.getOrders(userId);
		if(order!=null) {
			resp.setStatusCode(201);
			resp.setMessage("Success");
			resp.setDescription("Order Found");
			resp.setOrders(order);
			return resp;
		} else {
			resp.setStatusCode(401);
			resp.setMessage("Failed");
			resp.setDescription("Order Not Found");
			return resp;
		}
	}
	
	@PostMapping(path = "/template/addtocart", produces =  MediaType.APPLICATION_JSON_VALUE, consumes =  MediaType.APPLICATION_JSON_VALUE)
	public ResponseClass addToCart(@RequestBody UserInfoBean bean) {
		ResponseClass resp = new ResponseClass();
		if(service.addToCart(bean)) {
			resp.setStatusCode(201);
			resp.setMessage("Success");
			resp.setDescription("Added to cart");
			return resp;
		} else {
			resp.setStatusCode(401);
			resp.setMessage("Failed");
			resp.setDescription("Failed adding to cart");
			return resp;
		}
	}
	
	@GetMapping(path = "/template/getItems",produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseClass getCartItems(@RequestParam("userId")int userId ) {
		ResponseClass resp = new ResponseClass();
		List<CartInfoBean> items = service.getCartItems(userId);
		if(items!=null) {
			resp.setStatusCode(201);
			resp.setMessage("Success");
			resp.setDescription("Items Found");
			resp.setItems(items);
			return resp;
		} else {
			resp.setStatusCode(401);
			resp.setMessage("Failed");
			resp.setDescription("Items Not Found");
			return resp;
		}
	}
	
	@GetMapping(path="/template/remItem",produces= MediaType.APPLICATION_JSON_VALUE)
	public ResponseClass removeItem(@RequestParam("itemId")int itemId ) {
		ResponseClass resp = new ResponseClass();
		if(service.removeCartItem(itemId)) {
			resp.setStatusCode(201);
			resp.setMessage("Success");
			resp.setDescription("Item deleted");
			return resp;
		} else {
			resp.setStatusCode(401);
			resp.setMessage("Failed");
			resp.setDescription("Item Not deleted");
			return resp;
		}
	}
	@PutMapping(path = "/template/changeStatus",produces = MediaType.APPLICATION_JSON_VALUE,consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseClass changeStatus(@RequestBody OrderDetails bean) {
		ResponseClass resp = new ResponseClass();
		if(service.changeStatus(bean)) {
			resp.setStatusCode(201);
			resp.setMessage("Success");
			resp.setDescription("Updation Successfull");
			return resp;
		} else {
			resp.setStatusCode(401);
			resp.setMessage("Failed");
			resp.setDescription("Updation Product Failed");
			return resp;
		}
	}
}
