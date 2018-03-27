package com.bedrock.origin.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Date;

/**
 * 时间帮助类
 * @author liuxiangtao90
 *
 */
public class TimeUtil 
{
	// 命名为日期格式最后一级 按照常用分为1 2 3...
	public static final String	DAY1="yyyy-MM-dd";
	public static final String  DAY2="yyyy/MM/dd";
	public static final String  DAY3="yyyy.MM.dd";
	public static final String	SECOND1="yyyy-MM-dd HH:mm:ss";
	
	/**
	 * 比较两个日期
	 * @param pattern 日期格式
	 * @param time1	
	 * @param time2
	 * @return
	 * @throws ParseException
	 */
	public static int compare(String pattern,String time1,String time2) throws ParseException
	{
		SimpleDateFormat format = new SimpleDateFormat(pattern);
		Date date1= format.parse(time1);
		Date date2= format.parse(time2);
		return date1.compareTo(date2);
	}
	
	/**
	 * 和当前日期做比较
	 * @param pattern 日期格式
	 * @param time 时间
	 * @return
	 * @throws ParseException
	 */
	public static int compareToNow(String pattern,String time) throws ParseException
	{
		SimpleDateFormat format = new SimpleDateFormat(pattern);
		Date date1= format.parse(time);
		Date now=new Date();
		return date1.compareTo(now);
	}
	
	/**
	 * 日期格式化
	 * @param pattern
	 * @param date
	 * @return
	 * @throws ParseException
	 */
	public static String format(String pattern,Object date)
	{
		try
		{
			SimpleDateFormat format = new SimpleDateFormat(pattern);
			String dateStr= format.format(date);
			return dateStr;
		}
		catch(Exception e) 
		{
			return null;
		}
	}
	
	
	/**
	 * 时间戳转字符串
	 * @param pattern 字符串格式
	 * @param timeStamp 时间戳
	 * @return
	 */
	public static String timeStamp2Str(String pattern,long timeStamp)
	{
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
        Date date = new Date(timeStamp*1000);
        return simpleDateFormat.format(date);
	}
	
	/**
	 * 时间字符串转时间戳
	 * @param data
	 * @param pattern
	 * @return
	 */
	public static Integer str2timeStamp(String data,String pattern)
	{
		try 
		{
		    SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
			return Integer.valueOf(simpleDateFormat.parse(data).getTime()/1000+"");
		} catch (ParseException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	/**
	 * 格式化当前日期
	 * @param pattern
	 * @return
	 */
	public static String now2Str(String pattern)
	{
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
        Date date = new Date();
        return simpleDateFormat.format(date);
	}
	
	
	/**
	 * 现在距离过去某时间的天数
	 * @author liuxiangtao  
	 * @date 2018年3月27日 下午4:03:46
	 *  
	 * @param pattern
	 * @param timeStr
	 * @return
	 */
	public static long distanceNow(String pattern,String timeStr)
	{
		LocalDate startDate = LocalDate.parse(timeStr, DateTimeFormatter.ofPattern(pattern));
        LocalDate endDate = LocalDate.now();
        long daysDiff = ChronoUnit.DAYS.between(startDate, endDate);
        return daysDiff;
	}
}
