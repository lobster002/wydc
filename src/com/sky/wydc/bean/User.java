package com.sky.wydc.bean;

import java.io.Serializable;

/**
 * �û�
 */
public class User implements Serializable {

	private static final long serialVersionUID = 1L;
	private int id;
	private String username;// �û���
	private String password;// ����
	private String avatar;// ͷ���ַ
	private String nickname;// �ǳ�
	private String address;// �ջ���ַ

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
