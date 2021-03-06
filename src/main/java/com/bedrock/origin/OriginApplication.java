package com.bedrock.origin;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
//扫描 mybatis mapper 包路径
//@MapperScan(basePackages = "com.bedrock.origin.mapper")
//扫描 所有需要的包  @SpringBootApplication里的@ComponentScan已经默认扫描这个类所在等级及其子目录下的包
//@ComponentScan(basePackages= {"com.bedrock.origin"})
//开启定时任务
//@EnableScheduling
//开启异步调用方法
//@EnableAsync
//开启切面
@EnableAspectJAutoProxy
@EnableTransactionManagement
public class OriginApplication {

	public static void main(String[] args) {
		SpringApplication.run(OriginApplication.class, args);
	}
	
	/*@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder builder)
	{
		//独立Tomcat部署时运行入口  
		return builder.sources(OriginApplication.class);
	}*/
	
}
