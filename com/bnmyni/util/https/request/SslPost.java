package com.pica.worldcup.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.apache.commons.httpclient.protocol.Protocol;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;


public class SslPost {

	/**
	 * @param args
	 * @throws IOException 
	 * @throws ClientProtocolException 
	 */
	public static String post(String url, List<NameValuePair> params ) throws ClientProtocolException, IOException {
	    HttpClient http = new HttpClient();  
	    Protocol myhttps = new Protocol("https", new MySSLProtocolSocketFactory(), 443);   
	    Protocol.registerProtocol("https", myhttps); 
	    
	    PostMethod postMethod = new PostMethod(url);
	    
//	    List<NameValuePair> params=new ArrayList<NameValuePair>();
//	    //建立一个NameValuePair数组，用于存储欲传送的参数
//	    params.add(new BasicNameValuePair("appid","09f974a545ad1af246ab33dd8184253a"));
//	    params.add(new BasicNameValuePair("type","artifact"));
//	    params.add(new BasicNameValuePair("gateway","1"));
//	    params.add(new BasicNameValuePair("once","98294929433"));
//	    params.add(new BasicNameValuePair("credential","98294929432"));
//	    params.add(new BasicNameValuePair("clientType","2"));
//	    HttpMethodParams p = new HttpMethodParams();
        for (NameValuePair paramv: params) {
        	postMethod.addParameter(paramv.getName(), paramv.getValue());
        } 
	    
	    int statusCode = http.executeMethod(postMethod); 
	    StringBuffer response = new StringBuffer(); 
	    boolean pretty =true;
	    if (statusCode == HttpStatus.SC_OK) { 
	    	   BufferedReader reader = new BufferedReader(new InputStreamReader(postMethod.getResponseBodyAsStream(), "UTF-8"));
               String line;
               while ((line = reader.readLine()) != null) {
                       if (pretty)
                               response.append(line).append(System.getProperty("line.separator"));
                       else
                               response.append(line);
               }
               reader.close(); 
               return response.toString();
	    }else{
	    	return null;
	    }
	    
	    
	}
}
