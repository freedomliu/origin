package com.bedrock.origin.common;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

import com.bedrock.origin.controller.interceptor.OneInterceptor;
import com.bedrock.origin.controller.interceptor.TwoInterceptor;



@Configuration
public class WebMvcConfigurer extends WebMvcConfigurationSupport  {

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		/**
		 * 拦截器按照顺序执行
		 */
		registry.addInterceptor(new TwoInterceptor()).addPathPatterns("/two/**")
													 .addPathPatterns("/one/**");
		registry.addInterceptor(new OneInterceptor()).addPathPatterns("/**");
		
		super.addInterceptors(registry);
	}

}
