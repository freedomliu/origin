package com.bedrock.origin.utils;

import java.io.File;
import java.nio.charset.Charset;

import com.bedrock.origin.constant.CodeType;

import okio.BufferedSink;
import okio.BufferedSource;
import okio.ByteString;
import okio.Okio;
import okio.Sink;
import okio.Source;

/**
 * okio帮助类
 * <p>
 * Title: OkioHelper
 * </p>
 * <p>
 * Description:
 * </p>
 * 
 * @author liuxiangtao90
 * @date 2018年3月30日 下午12:21:47
 */
public class OkioHelper {
	/**
	 * 从文件中读取字符串
	 * 
	 * @author liuxiangtao
	 * @date 2018年3月30日 下午12:31:04
	 * 
	 * @param fileName
	 * @return
	 */
	public static String readString(String fileName) 
	{
		String read = null;
		try (Source source = Okio.source(new File(fileName));
			 BufferedSource bSource = Okio.buffer(source)) 
		{
			read = bSource.readString(Charset.forName(CodeType.UTF_8));
		} 
		catch (Exception e) {
			e.printStackTrace();
		}
		return read;
	}

	/**
	 * 文件写入文本
	 * @author liuxiangtao  
	 * @date 2018年3月30日 下午8:02:31
	 *  
	 * @param fileName
	 * @param text
	 */
	public static void writeString(String fileName, String text) 
	{
		File file = new File(fileName);
		try
		{
			if (!file.exists()) 
			{
				file.createNewFile();
			}
			try (Sink sink = Okio.sink(file); BufferedSink bSink = Okio.buffer(sink);) 
			{
				bSink.writeUtf8(text);
				bSink.flush();
			}
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
		}
	}
	
	/**
	 * okio加密工具
	 * @author liuxiangtao  
	 * @date 2018年3月30日 下午8:13:30
	 *  
	 * @param str
	 * @return
	 */
	public static String encrypt(String str)
	{
		// 这里还有很多好用的方法
		ByteString.of(str.getBytes()).base64();	
		ByteString.of(str.getBytes()).md5();
		ByteString.of(str.getBytes()).sha1();
		ByteString.of(str.getBytes()).sha256();	
		ByteString.of(str.getBytes()).sha256();	
		return ByteString.of(str.getBytes()).md5().toString();
	}
}
