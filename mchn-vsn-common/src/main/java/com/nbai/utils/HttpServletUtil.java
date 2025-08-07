package com.nbai.utils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

/**
 * HttpServletUtil工具类
 * 	获取HttpServletRequest和HttpServletResponse
 * @author zhenmeng
 * @date 2020-07-31
 */
public class HttpServletUtil {
	
	   public static HttpServletRequest getRequet() {
	        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
	        HttpServletRequest request = attributes != null ? attributes.getRequest() : null;
	        return request;
	    }
	   
	   public static HttpServletResponse getResponse() {
		   ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
		   HttpServletResponse response = attributes != null ? attributes.getResponse() : null;
		   return response;
	   }   
}
