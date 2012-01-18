package org.mikeSkei.battleship.networking.dto;

import java.io.Serializable;

public class LoginDTO implements Serializable {
	private String phoneNumber;
	
	
	public LoginDTO () {
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
}
