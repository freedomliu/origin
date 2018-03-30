package com.bedrock.origin.utils;

import java.io.File;
import java.nio.charset.Charset;

import com.bedrock.origin.constant.CodeType;

import okio.BufferedSource;
import okio.Okio;
import okio.Source;

/**
 * okio帮助类
 * <p>Title: OkioHelper</p>  
 * <p>Description: </p>  
 * @author liuxiangtao90  
 * @date 2018年3月30日 下午12:21:47
 */
public class OkioHelper 
{
	  /**
	   * 从文件中读取字符串
	   * @author liuxiangtao  
	   * @date 2018年3月30日 下午12:31:04
	   *  
	   * @param fileName
	   * @return
	   */
	  public static String readString(String fileName) 
	  {
		  String read=null;
		  File file = new File(fileName);
		  try(Source source = Okio.source(file); BufferedSource bSource = Okio.buffer(source))
		  {
			  read = bSource.readString(Charset.forName(CodeType.UTF_8));
		  }
		  catch (Exception e)
		  {
			  e.printStackTrace();
		  }
		  return read;
	  }
}
