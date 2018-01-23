package com.aspire.hdc.common.util;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.naming.NoNameCoder;
import com.thoughtworks.xstream.io.xml.DomDriver;

/**
 * XStream 为线程安全，不要在每个地方都new XStream的对象，否则在高并发的情况下将导致性能问题
 * http://x-stream.github.io/
 */
public class XmlByXStreamUtil {
	
	public static XStream xstream = new XStream(new DomDriver("UTF-8", new NoNameCoder()));

	public static <T> T xml2Object(String str, Class<T> clazz) {
		
		xstream.autodetectAnnotations(true);
		xstream.processAnnotations(clazz);
		xstream.ignoreUnknownElements();
		T obj = null;
		try {
			obj = clazz.newInstance();
		} catch (Exception e) {
			throw new RuntimeException("无法实例化" + clazz.getName());
		}
		xstream.fromXML(str, obj);
		return obj;
	}

	public static String object2XML(Object obj) {
		xstream.autodetectAnnotations(true);
		return xstream.toXML(obj);
	}

	@SuppressWarnings("rawtypes")
	public static String object2XML(Object obj, Class clazz) {
		xstream.autodetectAnnotations(true);
		return xstream.toXML(obj);
	}
}
