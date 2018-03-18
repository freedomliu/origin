package com.bedrock.origin.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class Test {

	@Autowired
	Protobean pro;
	
	@GetMapping("test")
	@ResponseBody
	public void test0()
	{
		System.out.println(pro.getName());
	}
	
	@GetMapping("freemark.html")
	public String test1(ModelMap map)
	{
		System.out.println(pro.getName());
		map.put("name", "!!!!!!!!!");
		System.out.println(1/0);
		return "NewFile";
	}
	
	@GetMapping("ajaxerror.html")
	public String ajaxerror(ModelMap map)
	{
		return "ajaxerror";
	}
	@PostMapping("ajaxerror.do")
	public String ajaxerrordo(ModelMap map)
	{
		System.out.println(1/0);
		return "ajaxerror";
	}
	
}
