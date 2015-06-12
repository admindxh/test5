package com.rimi.ctibet.common.shiro;

import java.util.List;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.rimi.ctibet.domain.Access;
import com.rimi.ctibet.web.service.IAccessService;

public class LoadSourceServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public static List<Access> accesses;
	private ApplicationContext appctx = null;// 定义全局变量context
	@Override
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		appctx = WebApplicationContextUtils
				.getRequiredWebApplicationContext(getServletContext());
		IAccessService accessService = appctx.getBean(IAccessService.class);
		accesses = accessService.accessList();
		for (Access access : accesses) {
			//System.out.println(access);
		}
	}
}
