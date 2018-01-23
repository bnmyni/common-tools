package com.aspire.hdc.common.util;

import java.io.IOException;
import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.JavaType;

/**
 * json转换
 * @author hexuanneng
 *
 */
public final class JacksoUtil {

	private static final ObjectMapper mapper = new ObjectMapper();

	/**
	 * json字符串转java对象
	 * @param json json字符串
	 * @param beanClass java对象 class
	 * @return
	 * @throws JsonParseException
	 * @throws JsonMappingException
	 * @throws IOException
	 */
	public static <T> T toBean(String json, Class<T> beanClass) throws JsonParseException, JsonMappingException, IOException {
		return mapper.readValue(json, beanClass);
	}

	/**
	 * json字符串转java对象(带泛型转换)
	 * @param json json字符串
	 * @param beanClass 要转换的对象 class
	 * @param genericsClass 泛型对象 class
	 * @return
	 * @throws JsonParseException
	 * @throws JsonMappingException
	 * @throws IOException
	 */
	public static <T> T toBean(String json, Class<?> collectionClass, Class<?>... elementClasses) throws JsonParseException, JsonMappingException, IOException {
		JavaType javaType = mapper.getTypeFactory().constructParametricType(collectionClass, elementClasses);
		return mapper.readValue(json, javaType);
	}

	/**
	 * java对象转json字符串
	 * @param bean
	 * @return
	 * @throws JsonGenerationException
	 * @throws JsonMappingException
	 * @throws IOException
	 */
	public static String toJson(Object bean) throws JsonGenerationException, JsonMappingException, IOException {
		return mapper.writeValueAsString(bean);
	}
}
