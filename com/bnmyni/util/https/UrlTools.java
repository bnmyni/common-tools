package com.sky.stock.jsoup.util;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class UrlTools {

	public void run(String target) throws Exception {
		// 生成一个URL对象
		URL url = new URL(target); 
		// 打开URL
		HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
		// 得到输入流，即获得了网页的内容
		BufferedReader reader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
		String line;
		// 读取输入流的数据，并显示
		while ((line = reader.readLine()) != null) {
			System.out.println(line);
		}
	}

}
