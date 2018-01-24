package com.pica.common.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

/**
 * 《UA管理》实现类
 * @version $Id: UserAgent.java,v 1.32 2010/05/27 23:26:21 lidebin Exp $
 */
public final class UserAgent {

	/**
	 * 从请求头中获取UserAgent信息(长UA)，获取不到返回空字符而不是返回null
	 * 
	 * @param request
	 * @return
	 */
	public static String useragent(HttpServletRequest request) {

		String ret = request.getHeader("User-Agent");
		if (ret != null && ret.length() > 5) {
			return ret;
		}

		return "";
	}

	public static String xua(HttpServletRequest request) {
		return request.getHeader("X-User-Agent");
	}

	/**
	 * 手机，如Nokia6670、MOT-L7、SonyEricssonW700c
	 * 
	 * @param request
	 * @return
	 */
	public static String phone(HttpServletRequest request) {
		String useragent = useragent(request);

		return phone(useragent);
	}

	/**
	 * 手机，如Nokia6670、MOT-L7、SonyEricssonW700c
	 * 
	 * @param useragent
	 *            UserAgent
	 * @return
	 */
	public static String phone(String useragent) {
		if (useragent == null || useragent.length() <= 0) {
			return null;
		}

		if (isSpider(useragent)) {
			return null;
		}

		String ret;
		String regex = "";
		Pattern p;
		Matcher m;

		ret = regex(useragent);
		if (ret != null) {
			return ret;
		}

		regex = "([a-z|A-Z|\\-|_|\\/]+)(\\d+)(\\D+)(.*)";
		p = Pattern.compile(regex);
		m = p.matcher(useragent);
		if (m.find()) {
			return m.replaceAll("$1$2");
		}

		String[] ss = useragent.split("[\\s,|\\/,|\\*,]+");
		if (ss[0].matches(".*(\\d+).*")) {
			return ss[0];
		}

		if (ss.length > 1) {
			return ss[0] + ss[1];
		}

		return ss[0];
	}

	/**
	 * UA分析
	 * 
	 * @param useragent
	 * @return
	 */
	private static String regex(String useragent) {
		String ret;

		ret = regex(
				useragent,
				"(.*)(dopod|Nokia|SonyEricsson)([a-z|A-Z|\\-|_|\\/]*)(\\d+)(\\w{0,1})(\\D+)(.*)",
				"$2$3$4$5");
		if (ret != null) {
			return ret;
		}

		ret = regex(
				useragent,
				"(.*)(Philips|SAMSUNG|SEC-|MOT|Motorola-|ASUS|Lenovo|LENOVO|Haier-|GIONEE)([a-z|A-Z|\\-|_|\\/]*)(\\d+)(\\D+)(.*)",
				"$2$3$4");
		if (ret != null) {
			return ret;
		}

		ret = regex(
				useragent,
				"(.*)(Panasonic|BlackBerry|BIRD|CHANGHONG|Amoi|HEDY|CoolPad|YuLong-Coolpad|ZTE-|SKYWORTH)([a-z|A-Z|\\-|_|\\/]*)(\\d+)(\\D+)(.*)",
				"$2$3$4");
		if (ret != null) {
			return ret;
		}

		ret = regex(
				useragent,
				"(.*)(TIANYU|KONKA|PANTECH|CECT|TCL)([a-z|A-Z|\\-|_|\\/]*)(\\d+)(\\D+)(.*)",
				"$2$3$4");
		if (ret != null) {
			return ret;
		}

		return null;
	}

	/**
	 * UA分析
	 * 
	 * @param useragent
	 *            UA
	 * @param regex
	 *            正则
	 * @return
	 */
	private static String regex(String useragent, String regex,
			String replacement) {
		Pattern p;
		Matcher m;

		p = Pattern.compile(regex, Pattern.CASE_INSENSITIVE);
		m = p.matcher(useragent);
		if (m.find()) {
			return m.replaceAll(replacement);
		}

		return null;
	}

	/**
	 * 判断非正规的UA信息，来自电脑、机器人等
	 * 
	 * @param useragent
	 *            UserAgent
	 * @return
	 */
	private static boolean isSpider(String useragent) {
		if (useragent == null || useragent.equals("")) {
			return false;
		}

		String regex;
		Pattern p;
		Matcher m;

		regex = "(.*)(spider|Windows-Media-Player|robot|msnbot|wget|HttpClient)(.*)";
		p = Pattern.compile(regex, Pattern.CASE_INSENSITIVE);
		m = p.matcher(useragent);

		return m.find();
	}

	/**
	 * TD手机ua处理————3G手机
	 * 
	 * @param request
	 *            请求
	 */
	public static boolean td_ua(HttpServletRequest request) {
		String useragent = UserAgent.useragent(request);
		String phone = UserAgent.phone(useragent);
		String ACTION_SAVE = "#CoolPadF608#LG-KD877#SAMSUNG-GT-S5630#SAMSUNG-GT-C3730#SAMSUNG-GT-i8180#SAMSUNG-GT-I8180#LG GW880#Lenovo_O1#LG-KT878#CoolPad6268#CoolPadF800#Zte-tu230#Zte-tu720#MOT-MT710#Nokia6788#Haier_H-U90#HS-N51#LGtb200#YL-COOLPADN900#";

		StringBuffer str = new StringBuffer("#");
		str.append(phone);
		str.append("#");
		if (ACTION_SAVE.contains(str.toString())) {
			return true;
		}
		return false;
	}

	/**
	 * Jphone Iphone手机UA判断
	 * 
	 * @param ua
	 * @return
	 */
	public static boolean isWebkit(HttpServletRequest request) {
		String ua = UserAgent.useragent(request);
		if (ua == null || ua.length() < 5) {
			return false;
		}

		String regex = "(.*)(iPhone|Android)(.*)";
		Pattern p;
		Matcher m;

		p = Pattern.compile(regex, Pattern.CASE_INSENSITIVE);
		m = p.matcher(ua);

		return m.find();
	}

}
