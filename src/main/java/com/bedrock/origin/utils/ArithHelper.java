package com.bedrock.origin.utils;

import java.math.BigDecimal;

/**
 * 简单运算精确帮助类
 * @author liuxiangtao90
 *
 */
public class ArithHelper 
{
	// 默认除法精度
	private static final int DEF_DIV_SCALE=10;
	
	public static double add(final double v1,final double v2)
	{
		BigDecimal b1=BigDecimal.valueOf(v1);
		BigDecimal b2=BigDecimal.valueOf(v2);
		return b1.add(b2).doubleValue();
	}
	
	public static double sub(final double v1,final double v2)
	{
		BigDecimal b1=BigDecimal.valueOf(v1);
		BigDecimal b2=BigDecimal.valueOf(v2);
		return b1.subtract(b2).doubleValue();
	}
	
	public static double mul(final double v1,final double v2)
	{
		BigDecimal b1=BigDecimal.valueOf(v1);
		BigDecimal b2=BigDecimal.valueOf(v2);
		return b1.subtract(b2).doubleValue();
	}
	
	// 当出发除不尽时  精确到小数点后十位的数字  四舍五入
	public static double div(final double v1,final double v2)
	{
		BigDecimal b1=BigDecimal.valueOf(v1);
		BigDecimal b2=BigDecimal.valueOf(v2);
		return b1.divide(b2,DEF_DIV_SCALE,BigDecimal.ROUND_HALF_UP).doubleValue();
	}
}
