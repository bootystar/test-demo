package com.java.bean;

public class User {
	private String account;
	private String password;
	private String gender;
	private String name;
	private int age;

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public String getAccount() {
		return account;
	}


	@Override
	public String toString() {
		return "User [account=" + account + ", password=" + password + ", gender=" + gender + ", name=" + name
				+ ", age=" + age + ", getAge()=" + getAge() + ", getAccount()=" + getAccount() + ", getPassword()="
				+ getPassword() + ", getGender()=" + getGender() + ", getName()=" + getName() + ", getClass()="
				+ getClass() + ", hashCode()=" + hashCode() + ", toString()=" + super.toString() + "]";
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getName() {
		return name;
	}

	public void setName(String string) {
		this.name = string;
	}

}
