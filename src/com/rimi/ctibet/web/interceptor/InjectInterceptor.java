package com.rimi.ctibet.web.interceptor;

import java.util.Collection;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

public class InjectInterceptor implements HandlerInterceptor {
	public static  Logger LOG = Logger.getLogger(InjectInterceptor.class);
	private static final String ILLEGALWORLDS[] = { "'", "`", "\"",
			"%26%2339%3b", "%60", "%26quot%3b" };
	private HttpServletRequest request;

	@Override
	public void afterCompletion(HttpServletRequest arg0,
			HttpServletResponse arg1, Object arg2, Exception arg3)
			throws Exception {
		// TODO Auto-generated method stub

	}

	@Override
	public void postHandle(HttpServletRequest arg0, HttpServletResponse arg1,
			Object arg2, ModelAndView arg3) throws Exception {
		// TODO Auto-generated method stub
	}

	@Override
	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object arg2) throws Exception {
//		String basePath = request.getScheme() + "://" + request.getServerName()
//				+ ":" + request.getServerPort() + request.getContextPath()
//				+ "/";
//		String referer = request.getHeader("Referer");
//		System.out.println("basePath:"+basePath);
//		System.out.println("referer:"+referer);
//		if(referer != null && !referer.startsWith(basePath)){
//			response.sendRedirect("/");
//			return false;
//		}
		// response.sendRedirect();
		Map map = request.getParameterMap();
		Collection collection = map.values();
		// if (!checkSqlInject(collection)) {// 没通过检查sql注入
		// return false;
		// }
		if (!checkXSS(collection)) {// 没通过跨站检查
			return false;
		}
		return true;
	}

	private void checkUri(HttpServletRequest request,
			HttpServletResponse response) {
		try {
			String uri = request.getRequestURI();
			String url = filterUrl(uri);
			if (url.length() != uri.length()) {
				response.sendRedirect(url);
			}
		} catch (Exception e) {
			//e.printStackTrace();
		}
	}

	private boolean checkXSS(Collection collection) {
		// TODO Auto-generated method stub
		return true;
	}

	private boolean checkSqlInject(Collection collection) {
		for (Object object : collection) {
			try {
				String value[] = (String[]) object;
				for (String illegal : ILLEGALWORLDS) {
					for (String string : value) {
						if (string.indexOf(illegal) != -1) {
							LOG.error("来自" + request.getRemoteAddr() + "的消息:"
									+ string + ",含有非法字符:" + illegal);
							return false;
						}
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return true;
	}

	private String filterUrl(String uri) {
		int index = uri.indexOf("http://");
		if (index != -1) {
			return uri.substring(index);
		}
		return uri;
	}
}
