package com.rimi.ctibet.web.interceptor;

import java.io.IOException;
import java.util.Iterator;
import java.util.Map;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import javax.servlet.http.HttpServletResponse;

public class ScriptFilter implements Filter {
	public  static final String ILLEGALWORLDS[] = { "script", "onchange", "onclick", "onblur", "onfocus" };

	@Override
	public void destroy() {

	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse resp = (HttpServletResponse) response;
		req.setCharacterEncoding("UTF-8");
		Map<String, String[]> parMap = req.getParameterMap();
		Iterator<String[]> iterator = parMap.values().iterator();
		while (iterator.hasNext()) {
			String[] s = iterator.next();
			for (int i = 0; i < s.length; i++) {
				String res = s[i];
				for (String illegalWorld : ILLEGALWORLDS) {
					String illegalWorldUpper = illegalWorld.toUpperCase();
					res = res.replaceAll("_" + illegalWorld, illegalWorld).replaceAll("_" + illegalWorldUpper, illegalWorldUpper)
							.replaceAll(illegalWorld, "_" + illegalWorld).replaceAll(illegalWorldUpper, "_" + illegalWorldUpper);
				}
				s[i] = res;
			}
		}
		chain.doFilter(new MyRequest(req), resp);
	}

	@Override
	public void init(FilterConfig config) throws ServletException {

	}

}

class MyRequest extends HttpServletRequestWrapper {

	public MyRequest(HttpServletRequest request) {
		super(request);
	}

	@Override
	public String getParameter(String name) {
		String[] values = (String[]) this.getParameterMap().get(name);
		StringBuilder sb = null;
		if (values != null) {
			sb = new StringBuilder();
			sb.append(values[0]);
			for (int i = 1; i < values.length; i++) {
				sb.append(",").append(values[i]);
			}
		}
		return sb == null ? null : sb.toString();
	}

}
