package com.bedrock.origin.controller;

import java.util.Date;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bedrock.origin.beans.User;
import com.bedrock.origin.utils.IMoocJSONResult;

@RestController
public class RestfulTest {

	@RequestMapping(value="hello")
	public IMoocJSONResult hello()
	{
		User user =new User();
		user.setName("guoyang1");
		user.setPassword("pwd1");
		user.setBirthday(new Date());
		return IMoocJSONResult.ok(user);
	}
}
