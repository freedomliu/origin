package com.bedrock.origin.configurer;

import java.util.Arrays;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

import com.bedrock.origin.controller.interceptor.OneInterceptor;
import com.bedrock.origin.controller.interceptor.TwoInterceptor;



@Configuration
public class WebMvcConfigurer extends WebMvcConfigurationSupport  {

	@Override
	protected void addResourceHandlers(ResourceHandlerRegistry registry) 
	{
		registry.addResourceHandler("/**").addResourceLocations("classpath:/static/");  
	}

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		/**
		 * 拦截器按照顺序执行
		 */
		registry.addInterceptor(new TwoInterceptor()).addPathPatterns("/two/**")
													 .addPathPatterns("/one/**");
		registry.addInterceptor(new OneInterceptor()).excludePathPatterns(Arrays.asList("static/js/**", "/js/**", "/**.js"));
		
		super.addInterceptors(registry);
	}

}
