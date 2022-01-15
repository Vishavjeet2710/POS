package com.increff.pos.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.increff.pos.dto.UserDto;
import com.increff.pos.model.ApiException;
import com.increff.pos.model.UserData;
import com.increff.pos.model.UserForm;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api
@RequestMapping(value = "/api/user")
@RestController
public class UserController {
	
	@Autowired
	private UserDto dto;
	
	@ApiOperation(value = "Add a user")
	@PostMapping(value = "")
	public void add(@RequestBody UserForm form) throws ApiException {
		dto.add(form);
	}
	
	@ApiOperation(value = "Get a user")
	@GetMapping(value = "/{id}")
	public UserData get(@PathVariable int id) throws ApiException {
		return dto.get(id);
	}
	
	@ApiOperation(value = "Get a user by Email")
	@GetMapping(value = "/email/{email}")
	public UserData getEmail(@PathVariable String email) throws ApiException {
		return dto.getEmail(email);
	}

	@ApiOperation(value = "Get all user")
	@GetMapping(value = "")
	public List<UserData> getAll(){
		return dto.getAll();
	}
	
	@ApiOperation(value = "Delete a user")
	@DeleteMapping(value = "/delete/{id}")
	public void delete(@PathVariable int id) throws ApiException {
		dto.delete(id);
	}
	
	@ApiOperation(value = "Updates a user")
	@PutMapping(value = "/{id}")
	public void update(@PathVariable int id,@RequestBody UserForm form) throws ApiException {
		dto.update(id, form);
	}
}
