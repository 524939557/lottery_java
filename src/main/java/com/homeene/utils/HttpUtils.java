package com.homeene.utils;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;

public class HttpUtils {
	public static HttpClient client;
	static {
		client = HttpClientBuilder.create().build();

	}
  
	public static String post(String url,  Map<String, String> params)
			throws Exception {
		// 处理请求地址
		URI uri = new URI(url);
		HttpPost post = new HttpPost(uri);
		String str=getRequestData(params,"utf-8");
		post.setEntity(new StringEntity(str,"utf-8"));
		// 执行请求
		HttpResponse response = client.execute(post);
		System.out.println("--------状态码----------"+response.getStatusLine().getStatusCode());
		if (response.getStatusLine().getStatusCode() == 200) {
			// 处理请求结果
			StringBuffer buffer = new StringBuffer();
			InputStream in = null;
			try {
				in = response.getEntity().getContent();
				BufferedReader reader = new BufferedReader(
						new InputStreamReader(in));
				String line = null;
				while ((line = reader.readLine()) != null) {
					buffer.append(line);
				}

			}catch(Exception e){
				e.printStackTrace();
			} finally {
				// 关闭
				if (in != null)
					in.close();
			}

			return buffer.toString();
		} else {
			return null;
		}

	}

	public static String get(String url) throws Exception {
		URI uri = new URI(url);
		HttpGet get = new HttpGet(uri);
		HttpResponse response = client.execute(get);
		if (response.getStatusLine().getStatusCode() == 200) {
			StringBuffer buffer = new StringBuffer();
			InputStream in = null;
			try {
				in = response.getEntity().getContent();
				BufferedReader reader = new BufferedReader(
						new InputStreamReader(in));
				String line = null;
				while ((line = reader.readLine()) != null) {
					buffer.append(line);
				}

			} finally {
				if (in != null)
					in.close();
			}

			return buffer.toString();
		} else {
			return null;
		}

	}
	
	  private static String getRequestData(Map<String, String> params, String encode ) {
	    	StringBuffer stringBuffer =  new StringBuffer(); //存储封装好的请求体信息
	        try {
	        	params.keySet().forEach(map->{
	        		try
					{
						stringBuffer.append(map)
									.append("=")
									.append(URLEncoder.encode(params.get(map), encode))
									.append("&");
					} catch (UnsupportedEncodingException e)
					{
						e.printStackTrace();
					}
	        	});
	            if(!params.isEmpty()){
	                stringBuffer.deleteCharAt(stringBuffer.toString().length() - 1);    //删除最后的一个"&"
	            }
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	        return stringBuffer.toString();
	    }

	  /*** 
		 * 获取客户端IP地址;这里通过了Nginx获取;X-Real-IP, 
		 * @param request 
		 * @return 
		 */  
		public static String getClientIP(HttpServletRequest request) {  
		    String fromSource = "X-Real-IP";  
		    String ip = request.getHeader("X-Real-IP");  
		    if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {  
		        ip = request.getHeader("X-Forwarded-For");  
		        fromSource = "X-Forwarded-For";  
		    }  
		    if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {  
		        ip = request.getHeader("Proxy-Client-IP");  
		        fromSource = "Proxy-Client-IP";  
		    }  
		    if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {  
		        ip = request.getHeader("WL-Proxy-Client-IP");  
		        fromSource = "WL-Proxy-Client-IP";  
		    }  
		    if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {  
		        ip = request.getRemoteAddr();  
		        fromSource = "request.getRemoteAddr";  
		    }  
		    System.out.println("App Client IP: "+ip+", fromSource: "+fromSource);  
		    if (ip.equals("0:0:0:0:0:0:0:1")) {
		    	ip = "127.0.0.1";
        	}
		    return ip;  
		}  


}
