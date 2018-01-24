package com.pica.worldcup.util;

import java.security.MessageDigest;

import com.pica.worldcup.constant.ContactsConstant;

public class ShortUrlGenerator {
	
	public static void main(String[] args) {
		String fromId = "8";
		String index = "1";
		boolean isWap = false;
		String s = createLinkByFromId(fromId, index, isWap);
		System.out.println(s);
	}
	
	/**
	 * 该方法用于生产指定来源id的的短地址
	 * @param fromId
	 * @param index
	 * @param isWap
	 * @return
	 */
	private static String createLinkByFromId(String fromId,String index,boolean isWap){
		
		StringBuffer orginPath = new StringBuffer();
		if(isWap){
			orginPath.append("http://wap.jf.cmpassport.com/2014worldcup?");
		}else{
			orginPath.append("http://jf.cmpassport.com/2014worldcup?");
		}
		orginPath.append("fromid=").append(fromId).append("&index=").append(index);
		System.out.println(orginPath);
		return translateShortUrl(orginPath.toString());
	}

	public static String translateShortUrl(String url) {
		return shortUrl(url)[0];
	}

	private static String[] shortUrl(String url) {
		String key = ContactsConstant.SHORTURL_KEY;

		String[] chars = { "a", "b", "c", "d", "e", "f", "g", "h", "i", "j",
				"k", "l", "m", "n", "o", "p", "q", "r", "s", "t", "u", "v",
				"w", "x", "y", "z", "0", "1", "2", "3", "4", "5", "6", "7",
				"8", "9", "A", "B", "C", "D", "E", "F", "G", "H", "I", "J",
				"K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V",
				"W", "X", "Y", "Z" };

		String hex = getMD5(key + url);

		String[] resUrl = new String[4];

		for (int i = 0; i < 4; i++) {
			String tempSubString = hex.substring(0, 8);
			long hexint = 0x3FFFFFFF & Long.parseLong(tempSubString, 16);
			String outChars = "";
			for (int j = 0; j < 6; j++) {
				long index = 0x3D & hexint;

				outChars = outChars + chars[(int) index];

				hexint >>= 5;
			}

			resUrl[i] = outChars;
		}
		return resUrl;
	}

	private static String getMD5(String scrStr) {
		char[] hexDigits = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
				'a', 'b', 'c', 'd', 'e', 'f' };
		try {
			byte[] strTemp = scrStr.getBytes();
			MessageDigest mdTemp = MessageDigest.getInstance("MD5");
			mdTemp.update(strTemp);
			byte[] md = mdTemp.digest();
			int j = md.length;
			char[] str = new char[j * 2];
			int k = 0;
			for (int i = 0; i < j; i++) {
				byte byte0 = md[i];
				str[(k++)] = hexDigits[(byte0 >>> 4 & 0xF)];
				str[(k++)] = hexDigits[(byte0 & 0xF)];
			}
			return new String(str);
		} catch (Exception e) {
		}
		return null;
	}
}
