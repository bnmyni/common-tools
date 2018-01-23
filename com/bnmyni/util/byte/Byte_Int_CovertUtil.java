package com.aspire.hdc.common.boss.message;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

/**
 * 提供2中int <--> byte的互相转换方法;使用ByteArrayOutputStream的方式可以转换任意的基础类型
 * 两种方法的区别是：int2byteArray 和  byte2int  方法，把最低位放在数组开始，把高位放在数组最后，ByteArrayOutputStream方式正好相反。
*********************************************************************
int转byte原理
01000001 00100010 01000100 01011000

要获取到最高位的 01000001 直接向右移动24位即可
00000000 00000000 00000000 01000001  

获取次高位方式
00000000 00000000 01000001 00100010   -- 向右移动16位
00000000 00000000 00000000 11111111   -- ff二进制
00000000 00000000 00000000 00100010   &运算结果得到次高位

获取次低位方法
01000001 00100010 01000100 01011000
01000100 01011000 00000000 00000000  先左移16位，去掉高位
00000000 00000000 00000000 01000100  向右移动24位去掉低位，得到结果为次低位

获取次低位方法二
01000001 00100010 01000100 01011000
00000000 01000001 00100010 01000100  向右移动8位
00000000 00000000 00000000 11111111   -- ff二进制
00000000 00000000 00000000 01000100  &运算结果得到次低位

获取低位的方法 
01000001 00100010 01000100 01011000   - 原始值
00000000 00000000 00000000 11111111   -- ff二进制
00000000 00000000 00000000 01011000  &运算后，直接去掉了高位

*********************************************************************

字节还原原理
01000001  byte[0]
00100010  byte[1]
01000100  byte[2] 
01011000  byte[3]

byte[0] 处理方式
01000001
01000001 00000000 00000000 00000000  左移动24位

byte[1] 处理方式
00100010 00000000 00000000 00000000  左移动24位
00000000 00100010 00000000 00000000  右移动8位

byte[2] 处理方式
01000100
01000100 00000000 00000000 00000000 左移动24位
00000000 00000000 01000100 00000000 右移动16位

byte[2] 处理方式二
01000100
                  01000100 00000000  左移动8位
00000000 00000000 11111111 00000000   -- ff00 二进制
00000000 00000000 01000100 00000000    &运算得到结果

byte[3] 处理方式
01011000
00000000 00000000 00000000 11111111   -- ff二进制
00000000 00000000 00000000 01011000   &运算得到结果

使用或运算拼接四个byte

01000001 00000000 00000000 00000000 
00000000 00100010 00000000 00000000 
00000000 00000000 01000100 00000000
00000000 00000000 00000000 01011000 
01000001 00100010 01000100 01011000   -- 或运算得到结果
*********************************************************************
 * @author sunke
 */
public class Byte_Int_CovertUtil {
	
	
	/**
	 * 将int转换成byte数组，返回的第0个是低位
	 * @param i 需要转换的值
	 * @return 转换后的byte数组，数组的第0个为低位
	 */
	public static byte[] int2byteArray(int i){
		byte[] target = new byte[4];
		target[0] =(byte)(i & 0xff);  // 最低位
		target[1] =(byte)((i>>8) & 0xff); //次低位
		target[2] =(byte)((i>>16) & 0xff); // 次高位
		target[3] =(byte)((i>>24) & 0xff); // 高位
		return target;
	}
	
	/**
	 * byte数组第0个是低位，数组以低位开始
	 * @param res byte数组
	 * @return 对应的int值
	 */
	public static int byte2int(byte[] res) {   
		int targets = (res[0] & 0xff) | ((res[1] << 8) & 0xff00) | ((res[2] << 24) >>> 8) | (res[3] << 24);   
		return targets;   
	}  
	
	/**
	 * 使用java内置的流进行byte流转换，返回的byte数组以高位开始，及第0位为高位
	 * @param val 需要转换的值
	 * @return 转换后的字节数组
	 */
	public static byte[] data2byteArray(int val){
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		DataOutputStream dos = new DataOutputStream(bos);
		try {
			//这里可以转换任意的基础类型
			dos.writeInt(val);
			return bos.toByteArray();
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			try {
				bos.close();
				dos.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return null;
	}
	
	/**
	 * 把byte数组转换成任意基础类型，byte数组以高位开始
	 * @param orgin 需要还原的字节数组
	 * @return 还原后的基础类型值
	 */
	public static Integer data2byteArray(byte[] orgin){
		ByteArrayInputStream bis = new ByteArrayInputStream(orgin);    
	    DataInputStream dis = new DataInputStream(bis); 
	    try {
			return dis.readInt();
		} catch (IOException e) {
			e.printStackTrace();
		}finally{
			try {
				bis.close();
				dis.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	    return null;
	}
}
