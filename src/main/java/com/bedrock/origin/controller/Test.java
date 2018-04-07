package com.bedrock.origin.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.client.RestTemplate;

import com.bedrock.origin.beans.areasize;
import com.bedrock.origin.mapper.areasizeMapper;

@Controller
public class Test {

	@Autowired 
	areasizeMapper mapper;
	@Autowired
	RestTemplate restTemplate;
	
	@GetMapping("freemark.html")
	public String test1(ModelMap map)
	{
		areasize a= mapper.selectByPrimaryKey(4);
		System.out.println(mapper.deleteByPrimaryKey(5));
		return "NewFile";
	}	
	
	@GetMapping("test.html")
	public String test2(ModelMap map)
	{
		System.out.println(11111);
		return "NewFile";
	}	
}
