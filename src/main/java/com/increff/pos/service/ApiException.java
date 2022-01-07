package com.increff.pos.service;

//TODO move this to model
public class ApiException extends Exception{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public ApiException(String string) {
		super(string);
	}
}
