package com.bedrock.origin.controller;

import java.util.Date;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bedrock.origin.beans.User;
import com.bedrock.origin.utils.JSONResult;

@RestController
public class RestfulTest {

	@RequestMapping(value="hello")
	public JSONResult hello()
	{
		User user =new User();
		user.setName("guoyang1");
		user.setPassword("pwd1");
		user.setBirthday(new Date());
		return JSONResult.ok(user);
	}
}
