package com.bedrock.origin.utils;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

import com.bedrock.origin.annotation.Validate;

/**
 * 表单校验
 * @author liuxiangtao90
 *
 */
public class ValidateUtils 
{
	public static boolean validateForm(Class<?> bean) throws SecurityException, InstantiationException, IllegalAccessException
	{
		
		Field[] a = bean.getDeclaredFields();
		for(Field filed:a)
		{
			Validate validate = filed.getAnnotation(Validate.class);
			if(validate!=null)
			{
				String name= validate.name();
				String type=validate.type();
				System.out.println();
			}
		}
		
		
		Method[] annotation =bean.getClass().getDeclaredMethods();;
		
		return false;
	}
}
