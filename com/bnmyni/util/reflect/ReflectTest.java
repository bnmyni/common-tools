package com.sky.stock.jsoup.util;

import java.lang.reflect.Field;
import java.util.List;

public class ReflectTest {

	public List<NameValuePair> listObjectPostParams(Object o) throws IllegalArgumentException, IllegalAccessException {

		List<NameValuePair> p = new ArrayList<NameValuePair>();

		Field[] fields = o.getClass().getDeclaredFields();
		for (Field field : fields) {
			field.setAccessible(true);
			String name = field.getName();
			if ("serialVersionUID".equals(name)) {
				continue;
			}
			Object val = field.get(o);
			p.add(new BasicNameValuePair(name, String.valueOf(val)));
		}
		return p;
	}

}
