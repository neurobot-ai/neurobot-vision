package com.nbai.utils;


import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.http.NameValuePair;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;

import lombok.extern.slf4j.Slf4j;

/**
 * @Author LD
 * @Date 2020/5/20 3:33 下午
 * @Email: lideguang@mcu32.com
 */
@Slf4j
public class HttpClientUtil {
	
    private static final Integer CONNECT_TIMEOUT = 60000;
    private static final Integer CONNECTION_REQUEST_TIMEOUT = 60000;
    private static final Integer SOCKET_TIMEOUT = 60000;
    
    /**
     * 获取请求配置对象
     * @return
     */
    public static RequestConfig getRequestConfig() {
    	return RequestConfig
    			.custom()  
		        .setConnectTimeout(CONNECT_TIMEOUT)
		        .setConnectionRequestTimeout(CONNECTION_REQUEST_TIMEOUT)  
		        .setSocketTimeout(SOCKET_TIMEOUT)
		        .build();  
    }

    public static String doGet(String url, Map<String, String> param) {
    	 log.info("========================= http get start ==========================");
        // 创建Httpclient对象
        CloseableHttpClient httpclient = HttpClients.createDefault();
        String resultString = "";
        CloseableHttpResponse response = null;
        try {
            // 创建uri
            URIBuilder builder = new URIBuilder(url);
            if (param != null) {
                for (String key : param.keySet()) {
                    builder.addParameter(key, param.get(key));
                }
            }
            URI uri = builder.build();

            // 创建http GET请求
            HttpGet httpGet = new HttpGet(uri);
            httpGet.setConfig(getRequestConfig());
            log.info("   url: " + url);
			if (param != null && !param.isEmpty()) {
				log.info("   param: " + param);
			}
            log.info("========================= http get doing ==========================");
            
            // 执行请求
            response = httpclient.execute(httpGet);
            // 判断返回状态是否为200
            if (response.getStatusLine().getStatusCode() == 200) {
                resultString = EntityUtils.toString(response.getEntity(), "UTF-8");
                log.info(resultString);
                log.info("========================= http get finish ==========================");
            }else {
                log.error("url:{},doGet is Error, errorCode:{}" ,url, response.getStatusLine().getStatusCode());
                log.error("error info:" );
            }
        } catch (Exception e) {
            log.error("doGet is error.", e);
        } finally {
            try {
                if (response != null) {
                    response.close();
                }
                httpclient.close();
            } catch (Exception e) {
                log.error("doGet is error.", e);
            }
        }
        return resultString;
    }

    public static String doGet(String url) {
        return doGet(url, null);
    }

    public static String doGet(String url,Map<String,String> param, Map<String,String> header){
        log.info("========================= http get start ==========================");
        // 创建Httpclient对象
        CloseableHttpClient httpclient = HttpClients.createDefault();
        String resultString = "";
        CloseableHttpResponse response = null;
        try {
            // 创建uri
            URIBuilder builder = new URIBuilder(url);
            if (param != null) {
                for (String key : param.keySet()) {
                    builder.addParameter(key, param.get(key));
                }
            }
            URI uri = builder.build();

            // 创建http GET请求
            HttpGet httpGet = new HttpGet(uri);
            httpGet.setConfig(getRequestConfig());
            log.info("   url: " + url);
            if (param != null && !param.isEmpty()) {
                log.info("   param: " + param);
            }
            if (header != null){
                for (String key : header.keySet()) {
                    httpGet.addHeader(key,header.get(key));
                }
                log.info("header:{}",header);
            }
            log.info("========================= http get doing ==========================");

            // 执行请求
            response = httpclient.execute(httpGet);
            // 判断返回状态是否为200
            if (response.getStatusLine().getStatusCode() == 200) {
                resultString = EntityUtils.toString(response.getEntity(), "UTF-8");
                log.info(resultString);
                log.info("========================= http get finish ==========================");
            }else {
                log.error("url:{},doGet is Error, errorCode:{}" ,url, response.getStatusLine().getStatusCode());
                log.error("error info:" );
            }
        } catch (Exception e) {
            log.error("doGet is error.", e);
        } finally {
            try {
                if (response != null) {
                    response.close();
                }
                httpclient.close();
            } catch (Exception e) {
                log.error("doGet is error.", e);
            }
        }
        return resultString;
    }


    public static String doPost(String url, Map<String, String> param) {
        log.info("========================= http post start ==========================");
        // 创建Httpclient对象
        CloseableHttpClient httpClient = HttpClients.createDefault();
        CloseableHttpResponse response = null;
        String resultString = "";
        try {
            // 创建Http Post请求
            HttpPost httpPost = new HttpPost(url);
			httpPost.setConfig(getRequestConfig());
            // 创建参数列表
            if (param != null) {
                List<NameValuePair> paramList = new ArrayList<>();
                for (String key : param.keySet()) {
                    paramList.add(new BasicNameValuePair(key, param.get(key)));
                }
                // 模拟表单
                UrlEncodedFormEntity entity = new UrlEncodedFormEntity(paramList,"utf-8");
                httpPost.setEntity(entity);
            }
            
            log.info("   url: " + url);
            log.info("   body: " + param);
            log.info("========================= http post doing ==========================");
            
            // 执行http请求
            response = httpClient.execute(httpPost);
            resultString = EntityUtils.toString(response.getEntity(), "utf-8");
            
            log.info(" result: " + resultString);
        } catch (Exception e) {
            log.error("doPost is error.", e);
        } finally {
            try {
                response.close();
            } catch (Exception e) {
                log.error("doPost is error.", e);
            }
        }
        log.info("========================= http post end ==========================");
        return resultString;
    }

    public static String doPost(String url) {
        return doPost(url, null);
    }

    public static String doPostJson(String url, String json) {
        log.info("========================= http post start ==========================");
        // 创建Httpclient对象
        CloseableHttpClient httpClient = HttpClients.createDefault();
        CloseableHttpResponse response = null;
        String resultString = "";
        log.info("   url: " + url);
        log.info("   body: " + json);
        log.info("========================= http post doing ==========================");
        try {
            // 创建Http Post请求
            HttpPost httpPost = new HttpPost(url);
            httpPost.setConfig(getRequestConfig());
            // 创建请求内容
            StringEntity entity = new StringEntity(json, ContentType.APPLICATION_JSON);
            httpPost.setEntity(entity);
            // 执行http请求
            response = httpClient.execute(httpPost);
            resultString = EntityUtils.toString(response.getEntity(), "utf-8");
        } catch (Exception e) {
            log.error("doPostJson is error.", e);
        } finally {
            try {
                response.close();
            } catch (Exception e) {
                log.error("doPostJson is error.", e);
            }
        }
        log.info("========================= http post end ==========================");
        return resultString;
    }

    public static String doPostJson(String url, String json, Map<String,String> heads) {
   	 log.info("========================= http post start ==========================");
       // 创建Httpclient对象
       CloseableHttpClient httpClient = HttpClients.createDefault();
       CloseableHttpResponse response = null;
       String resultString = "";
       try {
           // 创建Http Post请求
           HttpPost httpPost = new HttpPost(url);
           httpPost.setConfig(getRequestConfig());
           
           if (CollectionUtils.isNotEmpty(heads)) {
           	Set<String> keySet = heads.keySet();
           	keySet.forEach(key -> {
           		httpPost.setHeader(key,heads.get(key));
           	});
           }
           
           // 创建请求内容
           StringEntity entity = new StringEntity(json, ContentType.APPLICATION_JSON);
           httpPost.setEntity(entity);
           
           log.info("   url: " + url);
           log.info("   heads: " + heads);
           log.info("   body: " + json);
           log.info("========================= http post doing ==========================");
           // 执行http请求
           response = httpClient.execute(httpPost);
           resultString = EntityUtils.toString(response.getEntity(), "utf-8");
           log.info(" result: " + resultString);
       } catch (Exception e) {
           log.error("doPostJson is error : {}",e.getMessage(), e);
       } finally {
           try {
               response.close();
           } catch (Exception e) {
               log.error("doPostJson is error.", e);
           }
       }
       log.info("========================= http post end ==========================");
       return resultString;
   }

	 
}
