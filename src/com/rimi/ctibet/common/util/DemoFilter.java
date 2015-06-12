package com.rimi.ctibet.common.util;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Map;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

import org.apache.commons.lang.StringUtils;

import com.rimi.ctibet.common.exception.JsonParseException;
public class DemoFilter implements Filter {
	private FilterConfig filterConfig;

	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		//System.out.println("Demo1过滤前");
		HttpServletRequest httpServletRequest = (HttpServletRequest)request;
		chain.doFilter(new HTMLCharacterRequest(httpServletRequest), response);//放行。让其走到下个链或目标资源中
		//System.out.println("Demo1过滤后");
		
		
	}
	public void init(FilterConfig filterConfig) throws ServletException {
		//System.out.println("初始化了");
		this.filterConfig = filterConfig;
	}

	public void destroy() {
		//System.out.println("销毁了");
	}
	
	class HTMLCharacterRequest extends HttpServletRequestWrapper {
		private  HttpServletRequest request2;
		public HTMLCharacterRequest(HttpServletRequest request) {
			super(request);
			request2 = request;
		}
		@Override
		public String getParameter(String name) {
			return filter(super.getParameter(name));
		}
		@Override
		public String[] getParameterValues(String name) {
			String[] values = super.getParameterValues(name);
			if (values == null || values.length == 0)
				return values;
			for (int i = 0; i < values.length; i++) {
				String str = values[i];
				values[i] = filter(str);
			}
			return values;
		}
		
		private String filter(String message) {
			//System.out.println("code--------------------------------->"+request2.getCharacterEncoding());
			//System.out.println("message--------------------------------->"+message);
			boolean andrTag = false;
			String paramJson = request2.getParameter("paramJson");
			if(StringUtils.isNotBlank(paramJson)){
				try {
					Map<String, String> map = JsonUtil.json2Map(paramJson);
					if(StringUtils.isNotBlank(map.get("andrTag"))&&"1".equals(map.get("andrTag"))){
						andrTag = true;
					}
				} catch (JsonParseException e) {
					e.printStackTrace();
				}
			}
			try {
				if(StringUtils.isNotBlank(message)){
					if(andrTag){
						message = new String(message.getBytes("ISO8859-1"),"UTF-8");
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			//System.out.println("message--------------------------------->"+message);
			return message;
		}
	}
}
