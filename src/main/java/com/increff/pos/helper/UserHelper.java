package com.increff.pos.helper;

import com.increff.pos.model.UserData;
import com.increff.pos.model.UserForm;
import com.increff.pos.pojo.UserPojo;

public class UserHelper {
	public static UserData convert(UserPojo p) {
		UserData data = new UserData();
		data.setEmail(p.getEmail());
		data.setId(p.getId());
		return data;
	}
	
	public static UserPojo convert(UserForm form) {
		UserPojo p = new UserPojo();
		p.setEmail(form.getEmail());
		p.setPassword(form.getPassword());
		return p;
	}	
}
