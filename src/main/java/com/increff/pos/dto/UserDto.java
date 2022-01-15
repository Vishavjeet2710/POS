package com.increff.pos.dto;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import com.increff.pos.helper.UserHelper;
import com.increff.pos.model.ApiException;
import com.increff.pos.model.UserData;
import com.increff.pos.model.UserForm;
import com.increff.pos.pojo.UserPojo;
import com.increff.pos.service.UserService;

@Service
@Validated
public class UserDto {
	@Autowired
	private UserService service;
	
	@Transactional(rollbackOn = ApiException.class)
	public void add(UserForm form) throws ApiException {
		UserPojo p = UserHelper.convert(form);
		service.add(p);
	}
	
	@Transactional(rollbackOn = ApiException.class)
	public UserData get(int id) throws ApiException {
		return UserHelper.convert(service.getCheck(id)); 
	}
	
	@Transactional(rollbackOn = ApiException.class)
	public UserData getEmail(String email) throws ApiException {
		return UserHelper.convert(service.getEmailCheck(email));
	}
	
	@Transactional
	public List<UserData> getAll(){
		List<UserData> results = new ArrayList<UserData>();
		List<UserPojo> list = service.getAll();
		for(UserPojo p : list) {
			results.add(UserHelper.convert(p));
		}
		return results;
	}
	
	@Transactional(rollbackOn = ApiException.class)
	public void delete(int id) throws ApiException {
		service.delete(id);
	}
	
	@Transactional(rollbackOn = ApiException.class)
	public void update(int id,UserForm form) throws ApiException {
		service.update(id, UserHelper.convert(form));
	}
}
