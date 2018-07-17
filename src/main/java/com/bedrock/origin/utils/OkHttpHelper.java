package com.bedrock.origin.utils;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.TimeUnit;

import com.alibaba.fastjson.JSONObject;
import com.bedrock.origin.common.OkHttpRequestBody;
import com.bedrock.origin.constant.CodeType;

import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.Headers;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.FormBody.Builder;

/**
 * 
 * @author bedrock
 * https://www.jianshu.com/p/039d05fb4834
 */
public class OkHttpHelper
{
	/**
	 * 同步get请求
	 * @param url
	 * @return
	 * @throws IOException
	 */
	public static JSONObject get(String url) throws IOException
	{
		return executeG(url);
	}
	
	/**
	 * 异步get请求
	 * @param url
	 * @return
	 * @throws IOException
	 */
	public static JSONObject asynchronousGet(String url,Callback callback) throws IOException
	{
		return executeG(url,callback);
	}
	
	/**
	 * 同步get请求
	 * @param url
	 * @param map 参数
	 * @return
	 * @throws IOException
	 */
	public static JSONObject getByMap(String url,Map<String, Object> map) throws IOException
	{
		return executeG(mapHandle(url,map));
	}
	
	/**
	 * 异步get请求
	 * 参数为map形式
	 * @param url
	 * @param map
	 * @return
	 * @throws IOException
	 */
	public static JSONObject asynchronousGetByMap(String url,Map<String, Object> map,Callback callback) 
			throws IOException
	{
		return executeG(mapHandle(url,map),callback);
	}
	
	/**
	 * 同步get请求
	 * @param url
	 * @param bean 参数
	 * @return
	 * @throws IOException
	 * @throws NoSuchMethodException
	 * @throws SecurityException
	 * @throws IllegalAccessException
	 * @throws IllegalArgumentException
	 * @throws InvocationTargetException
	 */
	public static JSONObject getByBean(String url,Serializable bean) throws IOException,NoSuchMethodException,
	SecurityException, IllegalAccessException, IllegalArgumentException,InvocationTargetException
	{
		return executeG(beanHandle(url,bean));
	}

	/**
	 * 异步get请求
	 * @param url
	 * @param bean
	 * @param callback
	 * @return
	 * @throws IOException
	 * @throws NoSuchMethodException
	 * @throws SecurityException
	 * @throws IllegalAccessException
	 * @throws IllegalArgumentException
	 * @throws InvocationTargetException
	 */
	public static JSONObject asynchronousGetByBean(String url,Serializable bean,Callback callback) 
	throws IOException,NoSuchMethodException,
	SecurityException, IllegalAccessException, IllegalArgumentException,InvocationTargetException
	{
		return executeG(beanHandle(url,bean),callback);
	}
	
	
	/**
	 * 拼接url
	 * @param url
	 * @param map
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	private static String mapHandle(String url,Map<String,Object> map) throws UnsupportedEncodingException
	{
		String param="";
		for (Entry<String, Object> entry : map.entrySet()) {
			param+=("&"+entry.getKey()+"="+entry.getValue());
		}
		param=URLEncoder.encode(param.replace("&",""), CodeType.UTF_8);
		return url+"?"+param;
	}
	
	/**
	 * 拼接url
	 * @param url
	 * @param bean
	 * @return
	 * @throws NoSuchMethodException
	 * @throws SecurityException
	 * @throws IllegalAccessException
	 * @throws IllegalArgumentException
	 * @throws InvocationTargetException
	 * @throws UnsupportedEncodingException
	 */
	private static String beanHandle(String url,Serializable bean) throws NoSuchMethodException, SecurityException, 
	IllegalAccessException, IllegalArgumentException, InvocationTargetException, UnsupportedEncodingException
	{
		String param="";
		Class<? extends Serializable> myClass= bean.getClass();
		Field[] field= myClass.getFields();
		for(int i=0;i<field.length;i++)
		{
			String name = field[i]+"".substring(0, 1).toUpperCase() + field[i]+"".substring(1);
			myClass.getMethod("get"+name);
			Method m = myClass.getMethod("get" + name);
			Object value= m.invoke(myClass);
			if(value != null)
			{
				param+=("&"+value);
			}
		}
		param=URLEncoder.encode(param.replace("&",""), CodeType.UTF_8);
		return url+"?"+param;
	}

	private static JSONObject executeG(String url,Callback ...callbacks) throws IOException
	{		
		OkHttpClient client = new OkHttpClient();
		Request request = new Request.Builder().url(url).build();
		if(callbacks.length!=0)
		{
			client.newCall(request).enqueue(callbacks[0]);
			return null;
		}
		Response response = client.newCall(request).execute() ;
		String result= response.body().string();
		return (JSONObject) JSONObject.parseObject(result);
	}
	
	/******************************↑get*****↓post******************************/
	
	/**
	 * post 请求
	 * 当callbacks存在时为异步 否则同步
	 * json
	 * @param url
	 * @param json
	 * @param callbacks
	 * @return
	 * @throws IOException
	 */
	public static JSONObject post(String url,JSONObject json,Callback...callbacks) throws IOException
	{
		MediaType JSON = MediaType.parse("application/json; charset=utf-8");
		RequestBody body = RequestBody.create(JSON, json.toJSONString());
		Request request = new Request.Builder().url(url).post(body).build();
	    return executeP(url, request, callbacks);
	}
	
	/**
	 * post 请求
	 * 表单
	 * 当callbacks存在时为异步 否则同步
	 * @param url
	 * @param map
	 * @param callbacks
	 * @return
	 * @throws IOException
	 */
	public static JSONObject postForm(String url,Map<String,Object> map,Callback...callbacks) throws IOException
	{
		okhttp3.MultipartBody.Builder builder= new  MultipartBody.Builder().setType(MultipartBody.FORM);
		for (String key : map.keySet()) {
			builder.addFormDataPart(key, map.get(key)+"");
		}
		RequestBody requestBody  = builder.build();
		Request request = new Request.Builder().url(url).post(requestBody).build();
	    return executeP(url, request, callbacks);
	}
	
	/**
	 * post 表单提交  这个好用 上边那个有待研究
	 * 表单
	 * 当callbacks存在时为异步 否则同步
	 * @param url
	 * @param map
	 * @param callbacks
	 * @return
	 * @throws IOException
	 */
	public static JSONObject postForm(String url,Map<String,String> map,Callback callbacks) throws IOException
	{
		Builder builder = new FormBody.Builder();
		for (String key : map.keySet()) {
			builder.add(key, map.get(key));
		}
		FormBody requestBody  = builder.build();
		Request request = new Request.Builder().url(url).post(requestBody).build();
	    return executeP(url, request, callbacks);
	}
	
	/**
	 * 上传文件 并 包含参数
	 * @author liuxiangtao  
	 * @date 2018年7月16日 下午3:33:02
	 *  
	 * @param url
	 * @param file
	 * @param map
	 * @param callbacks
	 * @return
	 */
	public static JSONObject postStream(String url,File file,String fileKey,Map<String,String> map,Callback callbacks) 
	{ 	
    	OkHttpClient okHttpClient  = new OkHttpClient.Builder()
    			.connectTimeout(600, TimeUnit.SECONDS)
                .readTimeout(600, TimeUnit.SECONDS).writeTimeout(600, TimeUnit.SECONDS)
                .build();
			
		MediaType type=MediaType.parse("application/octet-stream");
		RequestBody fileBody=RequestBody.create(type,file);
		
		okhttp3.MultipartBody.Builder multipartBodyBuilder = new MultipartBody.Builder()
		        .setType(MultipartBody.ALTERNATIVE);
		   
		for (String key : map.keySet()) {
			multipartBodyBuilder.addFormDataPart(key, map.get(key));
		}
		MultipartBody  multipartBody=multipartBodyBuilder.addPart(Headers.of("Content-Disposition","form-data; name=\""+fileKey+"\"; filename=\""+file.getName()+"\""), fileBody)
		        .build();
		
		Request request = new Request.Builder()
		        .url(url)
		        .post(multipartBody)
		        .build();
		if(callbacks!=null)
		{
			okHttpClient.newCall(request).enqueue(callbacks);
		}
		else
		{                	
			try
			{
				Response response = okHttpClient.newCall(request).execute();
				if (!response.isSuccessful()) 
					throw new IOException("Unexpected code " + response);
				String bodyStr=response.body().string();
				return (JSONObject) JSONObject.parseObject(bodyStr);
				
			}catch (Exception e) 
			{
				e.printStackTrace();
			}
		}
		return null;
	 }
	
	/**
	 * post 请求
	 * 流
	 * @param url
	 * @param str
	 * @param callbacks
	 * @return
	 * @throws IOException
	 */
	public static JSONObject postStream(String url,String str,Callback...callbacks) throws IOException
	{
		RequestBody requestBody = new OkHttpRequestBody(str);
	    Request request = new Request.Builder().url(url).post(requestBody).build();
	    return executeP(url, request, callbacks);
	}

	/**
	 * post 请求
	 * 字符串
	 * @param url
	 * @param str
	 * @param callbacks
	 * @return
	 * @throws IOException
	 */
	public static JSONObject postSring(String url,String str,Callback...callbacks) throws IOException
	{
		MediaType MEDIA_TYPE_MARKDOWN= MediaType.parse("text/x-markdown; charset=utf-8");
		String postBody = str;
		Request request = new Request.Builder()
        .url(url)
        .post(RequestBody.create(MEDIA_TYPE_MARKDOWN, postBody)).build();
		return executeP(url, request, callbacks);
	}
	
	private static JSONObject executeP(String url,Request request,Callback ...callbacks) throws IOException
	{		
		JSONObject jo=null;
		OkHttpClient client = new OkHttpClient();
		if(callbacks!=null)
		{
			client.newCall(request).enqueue(callbacks[0]);
		}
		else
		{
			try (Response response = client.newCall(request).execute())
			{
				if (!response.isSuccessful()) throw new IOException("Unexpected code " + response);
				String bodyStr=response.body().string();
				jo= (JSONObject) JSONObject.parseObject(bodyStr);
			}
		}
		return jo;
	}
	
	public static void main(String[] args) throws IOException {
		Map<String,Object> map=new HashMap<>();
		map.put("phone", "18624362516");
		map.put("date", "2018-07-04");
		JSONObject jo=postForm("http://uatservice.my-audi.com.cn/UnifyApp/interfaces/gerRankHistory.do", map, null);
	}
}