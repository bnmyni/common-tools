package com.aspire.hdc.common.http.util;



import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HttpUtil {
	
	public static final int timeout = 5000; 
	
	public static Logger log = LoggerFactory.getLogger(HttpUtil.class);
	
	public static final String conntion_exception_desc = "网络超时，请重新提交订单。";
	
	public static String postURL(String targetURL,String urlParameters){
		URL url=null;
		HttpURLConnection connection = null;  
		try {
			//Create connection
			url = new URL(targetURL);
			connection = (HttpURLConnection)url.openConnection();
			connection.setRequestMethod("POST");
			connection.setRequestProperty("Content-Type", 
			"text/html");
			
			connection.setRequestProperty("Content-Length", "" + 
			Integer.toString(urlParameters.getBytes().length));
			connection.setRequestProperty("Content-Language", "en-US");  
			
			connection.setUseCaches (false);
			connection.setDoInput(true);
			connection.setDoOutput(true);

			//Send request
			DataOutputStream wr = new DataOutputStream (
			connection.getOutputStream ());
			wr.writeBytes (urlParameters);
			wr.flush ();
			wr.close ();

			//Get Response	
			InputStream is = connection.getInputStream();
			BufferedReader rd = new BufferedReader(new InputStreamReader(is));
			String line;
			StringBuffer response = new StringBuffer(); 
			while((line = rd.readLine()) != null) {
				response.append(line);
				response.append("\r");
			}
			rd.close();
			return response.toString();
			
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			return conntion_exception_desc;
		} finally {
			if(connection != null) {
				connection.disconnect(); 
			}
		} 
	}
	
	
	
	public static String get(String urlstr,String urlParameters) {
		StringBuffer response = new StringBuffer(); 
		HttpURLConnection httpURLConnection=null;
		try{
		    URL url = new URL(urlstr); 
		    httpURLConnection = (HttpURLConnection) url.openConnection(); 
		    httpURLConnection.setDoOutput(true); 
		    httpURLConnection.setDoInput(true);  
		    httpURLConnection.setRequestMethod("GET");
		    httpURLConnection.setConnectTimeout(timeout); 
		    httpURLConnection.setReadTimeout(timeout); 
		    httpURLConnection.connect(); 
		     
		    BufferedWriter out = new BufferedWriter(new OutputStreamWriter(httpURLConnection.getOutputStream(), "GBK")); 
		    out.write(urlParameters); 
		    out.flush(); 
		    out.close(); 
		    
		    Reader reader=new InputStreamReader(httpURLConnection.getInputStream(),"UTF-8");
			reader=new BufferedReader(reader);
			char[] buffer=new char[1024];
			for (int n = 0; n >= 0;) {
				n = reader.read(buffer, 0, buffer.length);
				if (n > 0)
				response.append(buffer, 0, n);
			}
			 
		    httpURLConnection.disconnect();
		    return response.toString();
		}catch(Exception ex){
			log.error(ex.getMessage(), ex);
			return conntion_exception_desc;
		}finally {
			if(httpURLConnection != null) {
				httpURLConnection.disconnect(); 
			}
		} 
	}
	
	public static String post(String urlstr,String urlParameters) {
		
		if(urlstr.startsWith("https")){
			StringBuffer response = new StringBuffer(); 
			javax.net.ssl.HttpsURLConnection httpURLConnection=null;
			try{
//				SSLContext sc = SSLContext.getInstance("SSL");
				System.setProperty("https.protocols", "TLSv1.2");  
				SSLContext sc = SSLContext.getInstance("TLSv1.2"); 
				sc.init(null,
						new TrustManager[] { new TrustAnyTrustManager() },
						new java.security.SecureRandom());

				
			    URL url = new URL(urlstr);
			    httpURLConnection = (javax.net.ssl.HttpsURLConnection) url.openConnection(); 
			    httpURLConnection.setSSLSocketFactory(sc.getSocketFactory());
			      httpURLConnection
					.setHostnameVerifier(new TrustAnyHostnameVerifier());

			    httpURLConnection.setDoOutput(true); 
			    httpURLConnection.setDoInput(true);
			    httpURLConnection.setRequestMethod("POST");
			    httpURLConnection.setConnectTimeout(timeout);
			    httpURLConnection.setReadTimeout(timeout); 
			    httpURLConnection.addRequestProperty("Content-type", "text/xml");
			    httpURLConnection.connect(); 
			     
			    BufferedWriter out = new BufferedWriter(new OutputStreamWriter(httpURLConnection.getOutputStream(), "UTF-8")); 
			    out.write(urlParameters);
			    out.flush(); 
			    out.close(); 
		
			    Reader reader=new InputStreamReader(httpURLConnection.getInputStream(),"UTF-8");
				reader=new BufferedReader(reader);
				char[] buffer=new char[1024];
				for (int n = 0; n >= 0;) {
					n = reader.read(buffer, 0, buffer.length);
					if (n > 0)
					response.append(buffer, 0, n);
				}
			     
			    httpURLConnection.disconnect();
			    return response.toString();
			}catch(Exception ex){
				log.error(ex.getMessage(), ex);
				return conntion_exception_desc;
			}finally {
				if(httpURLConnection != null) {
					httpURLConnection.disconnect(); 
				}
			}
		}else{
			StringBuffer response = new StringBuffer(); 
			HttpURLConnection httpURLConnection=null;
			try{
			    URL url = new URL(urlstr);
			    httpURLConnection = (HttpURLConnection) url.openConnection(); 
			    httpURLConnection.setDoOutput(true); 
			    httpURLConnection.setDoInput(true); 
			    httpURLConnection.setRequestMethod("POST"); 
			    httpURLConnection.setConnectTimeout(timeout); 
			    httpURLConnection.setReadTimeout(timeout); 
			    httpURLConnection.addRequestProperty("Content-type", "text/xml;charset=UTF-8");
			    //Soapaction
			    httpURLConnection.addRequestProperty("Soapaction", "HITV002");
			    httpURLConnection.connect(); 
			     
			    BufferedWriter out = new BufferedWriter(new OutputStreamWriter(httpURLConnection.getOutputStream(), "UTF-8")); 
			    out.write(new String(urlParameters.getBytes("UTF-8"))); 
			    out.flush(); 
			    out.close(); 
		
			    Reader reader=new InputStreamReader(httpURLConnection.getInputStream(),"UTF-8");
				reader=new BufferedReader(reader);
				char[] buffer=new char[1024];
				for (int n = 0; n >= 0;) {
					n = reader.read(buffer, 0, buffer.length);
					if (n > 0)
					response.append(buffer, 0, n);
				}
			     
			    httpURLConnection.disconnect(); 
			    return response.toString();
			}catch(Exception ex){
				log.error(ex.getMessage(), ex);
				return conntion_exception_desc;
			}finally {
				if(httpURLConnection != null) {
					httpURLConnection.disconnect(); 
				}
			} 
		}
	  } 

}
