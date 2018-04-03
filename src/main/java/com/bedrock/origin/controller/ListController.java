package com.bedrock.origin.controller;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

/**
 * 加载静态页面可统一写在这里
 * <p>Title: ListController</p>  
 * <p>Description: </p>  
 * @author liuxiangtao90  
 * @date 2018年4月3日 下午3:29:59
 */
@Configuration
public class ListController extends WebMvcConfigurationSupport{

	@Override
	protected void addViewControllers(ViewControllerRegistry registry) {
		registry.addViewController("/xx").setViewName("/xx");
		registry.addViewController("/YY").setViewName("/YY");
	}

}
