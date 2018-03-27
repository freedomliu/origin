package com.bedrock.origin.controller;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

/**
 * 映射配置文件demo
 * <p>Title: Protobean</p>  
 * <p>Description: </p>  
 * @author liuxiangtao90  
 * @date 2018年3月27日 下午8:20:59
 */
@Configuration
@ConfigurationProperties(prefix="com.bedrock.origin")
@PropertySource(value="classpath:resource.properties")
public class Protobean {
	
	private String name;
	private String language;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getLanguage() {
		return language;
	}
	public void setLanguage(String language) {
		this.language = language;
	}
	
}
