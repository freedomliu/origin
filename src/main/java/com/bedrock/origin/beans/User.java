package com.bedrock.origin.beans;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;


public class User 
{
	@JsonInclude(Include.NON_NULL) // 为空不显示
	String name;
	@JsonIgnore  // 忽略  学习一下框架提供的注解  并自定义注解
	String password;
	@JsonFormat(pattern="yyyy-mm-dd",locale="zh")
	Date birthday;
	
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
	public Date getBirthday() {
		return birthday;
	}
	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}
	
}
