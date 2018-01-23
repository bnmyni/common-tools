package com.aspire.hdc.common.util;

import java.math.BigDecimal;

public class DoubleUtils {
	private DoubleUtils(){};
	/**
	 * double 相加
	 * @param v1
	 * @param v2
	 * @param scale 保留小数位数，为空时 默认保留两位
	 * @return
	 */
	public static double add(double v1,double v2,Integer scale) {
		
		if(scale != null && scale < 0 ){
			throw new IllegalArgumentException("The scale must be a positive integer or zero");
		}
		BigDecimal bd1 = new BigDecimal(Double.toString(v1));
		BigDecimal bd2 = new BigDecimal(Double.toString(v2));
		if(scale == null){
			return div(bd1.add(bd2).doubleValue(),1,2);
		}else{
			return div(bd1.add(bd2).doubleValue(),1,scale);
		}
	}
	
	/**
	 * double 相减
	 * @param v1
	 * @param v2
	 * @param scale 保留小数位数，为空时 默认保留两位
	 * @return
	 */
	public static double sub(double v1,double v2,Integer scale) {
		if(scale != null && scale < 0 ){
			throw new IllegalArgumentException("The scale must be a positive integer or zero");
		}		
		BigDecimal bd1 = new BigDecimal(Double.toString(v1));
		BigDecimal bd2 = new BigDecimal(Double.toString(v2));
		//return bd1.subtract(bd2).doubleValue();
		if(scale == null){
			return div(bd1.subtract(bd2).doubleValue(),1,2);
		}else{
			return div(bd1.subtract(bd2).doubleValue(),1,scale);
		}
	}
	
	/**
	 * double 相乘
	 * @param v1
	 * @param v2
	 * @param scale 保留小数位数，为空时 默认保留两位
	 * @return
	 */	
	public static double mul(double v1,double v2,Integer scale) {
		if(scale != null && scale < 0 ){
			throw new IllegalArgumentException("The scale must be a positive integer or zero");
		}		
		BigDecimal bd1 = new BigDecimal(Double.toString(v1));
		BigDecimal bd2 = new BigDecimal(Double.toString(v2));
		//return bd1.multiply(bd2).doubleValue();
		if(scale == null){
			return div(bd1.multiply(bd2).doubleValue(),1,2);
		}else{
			return div(bd1.multiply(bd2).doubleValue(),1,scale);
		}		
	}
	
	/**
	 * double 相除
	 * @param v1
	 * @param v2
	 * @param scale 保留小数位数，为空时 默认保留两位
	 * @return
	 */		
	public static double div(double v1,double v2,Integer scale) {
		if(scale < 0 ){
			throw new IllegalArgumentException("The scale must be a positive integer or zero");
		}
		BigDecimal bd1 = new BigDecimal(Double.toString(v1));
		BigDecimal bd2 = new BigDecimal(Double.toString(v2));
		return bd1.divide(bd2, scale, BigDecimal.ROUND_HALF_UP).doubleValue();
	}
	
	/**
	 * double 比较 
	 * @param v1
	 * @param v2
	 * @return v1>v2 返回大于0 ；v1=v2 返回0  ；v1< v2 返回小于0 
	 */
	public static int compareTo(double v1,double v2) {
		return Double.compare(v1, v2);
	}
	
	public static void main(String[] args) {
		
		/**
		    System.out.println(add(1.02,1.031,0) +"-----"+(1.02+1.031));
			System.out.println(add(1.02,1.031) +"-----"+(1.02+1.031));
			System.out.println(add(1.02001,1.031) +"-----"+(1.02001+1.031));
			System.out.println(add(1.02,1.0) +"-----"+(1.02001+1.0));
			System.out.println(div(1.523,1.01,2) +"-----"+(1.523/1.01));
			System.out.println(compareTo(1.01,1.012));
		*/
		
		
		Double m =1.00001;
		Double n =1.01;
		
		System.out.println((n+m) +"---" +(m+n) );
		System.out.println(DoubleUtils.add(n,m,10));
	}
}