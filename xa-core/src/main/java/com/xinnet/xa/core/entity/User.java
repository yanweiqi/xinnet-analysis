package com.xinnet.xa.core.entity;

/**
 * 功能描述：这是一个测试的实体类
 * @author yanweiqi
 */
public class User {
	
	protected String userId;
	protected String name;
	protected String password;
	protected String email;
	
	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String toString(){
		return String.format("<User: %s, %s, %s, %s>",userId, name, email);
	}
	

}
