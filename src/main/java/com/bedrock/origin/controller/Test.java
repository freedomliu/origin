package com.bedrock.origin.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;

import com.bedrock.origin.mapper.areasizeMapper;

@Controller
public class Test {

	@Autowired 
	areasizeMapper mapper;
	
	@GetMapping("freemark.html")
	public String test1(ModelMap map)
	{
		System.out.println(mapper.deleteByPrimaryKey(5));
		return "NewFile";
	}	
}
