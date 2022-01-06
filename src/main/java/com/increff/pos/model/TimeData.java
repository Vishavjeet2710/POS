package com.increff.pos.model;

import java.time.LocalDateTime;

public class TimeData {
	private String time;

	public TimeData() {
		time = LocalDateTime.now().toString();
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}
}
