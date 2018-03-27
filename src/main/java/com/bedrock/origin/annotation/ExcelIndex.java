package com.bedrock.origin.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Excel导入导出标记注解
 * <p>Title: ExportIndex</p>  
 * <p>Description: </p>  
 * @author liuxiangtao90  
 * @date 2018年3月27日 下午7:00:42
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface ExcelIndex 
{
	// 当前索引
	public int index();
	// 当前列名字
	public String name() default"";
	// 当前列类型
	public String type() default ValidateType.TEXT;
}
