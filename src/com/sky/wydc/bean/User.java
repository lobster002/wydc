package com.sky.wydc.bean;

import java.io.Serializable;

/**
 * 用户
 */
public class User implements Serializable {

	private static final long serialVersionUID = 1L;
	private int id;
	private String username;// 用户名
	private String password;// 密码
	private String avatar;// 头像地址
	private String nickname;// 昵称
	private String address;// 收货地址

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getAvatar() {
		return avatar;
	}

	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

}
