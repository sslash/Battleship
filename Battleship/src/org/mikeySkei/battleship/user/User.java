package org.mikeySkei.battleship.user;

import org.mikeSkei.battleship.networking.dto.UserDTO;

public class  User{
	private String phoneNumber;
	private String username;

	public User(UserDTO userDTO) {
		username = userDTO.getUsername();
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

}
