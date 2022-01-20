package com.increff.pos.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.increff.pos.dao.UserDao;
import com.increff.pos.model.ApiException;
import com.increff.pos.pojo.UserPojo;

@Service
public class UserService {
	
	@Autowired
	private UserDao dao;
	
	@Transactional(rollbackOn = ApiException.class)
	public void add(UserPojo p) throws ApiException {
		UserPojo ex = dao.selectEmail(p.getEmail());
		if(ex!=null) {
			throw new ApiException("User with given email id already exists");
		}
		dao.add(p);
	}
	
	@Transactional
	public UserPojo get(int id) {
		return dao.select(id);
	}
	
	@Transactional(rollbackOn = ApiException.class)
	public UserPojo getCheck(int id) throws ApiException {
		UserPojo p = get(id);
		if(p==null) {
			throw new ApiException("User with given id does not exist");
		}
		return p;
	}
	
	@Transactional
	public UserPojo getEmail(String email) {
		return dao.selectEmail(email);
	}
	
	@Transactional(rollbackOn = ApiException.class)
	public UserPojo getEmailCheck(String email) throws ApiException {
		UserPojo p = getEmail(email);
		if(p==null) {
			throw new ApiException("User with given email does not exist");
		}
		return p;
	}
	
	@Transactional(rollbackOn = ApiException.class)
	public void delete(int id) throws ApiException {
		UserPojo ex = get(id);
		if(ex==null) {
			throw new ApiException("User with given id does not exist");
		}
		dao.delete(id);
	}
	
	@Transactional(rollbackOn = ApiException.class)
	public void update(int id,UserPojo p) throws ApiException {
		UserPojo ex = get(id);
		if(ex==null) {
			throw new ApiException("User with given id does not exist");
		}
		ex.setEmail(p.getEmail());
		ex.setPassword(p.getPassword());
	}

	@Transactional
	public List<UserPojo> getAll() {
		return dao.selectAll();
	}
}
