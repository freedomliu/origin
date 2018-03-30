package com.bedrock.origin.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URLEncoder;

import javax.servlet.http.HttpServletResponse;

public class DownloadHelper 
{
	public void download(String fileName,String filePath,HttpServletResponse response) throws IOException 
	{
		FileInputStream fis = null;
		OutputStream os = null;
		try 
		{
			response.reset();
			response.setContentType("application/force-download");// 设置强制下载不打开
			response.addHeader("Content-Disposition", "attachment;fileName="
					+ URLEncoder.encode("fileName", "utf-8"));// 设置文件名

			File fileTemp = new File(filePath);
			fileTemp.createNewFile();
			fis = new FileInputStream(fileTemp);
			os = response.getOutputStream();
			byte[] buffer = new byte[1024];
			int i;
			while ((i = fis.read(buffer)) != -1) {
				os.write(buffer);
			}
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
		} 
		finally 
		{
			fis.close();
			os.close();
		}
	}
}
