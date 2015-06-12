package com.rimi.ctibet.common.tag;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

import org.apache.commons.lang.StringUtils;

import com.rimi.ctibet.common.util.SpringUtil;
import com.rimi.ctibet.domain.Role;
import com.rimi.ctibet.domain.UserInfo;
import com.rimi.ctibet.web.controller.UserInfoController;
import com.rimi.ctibet.web.service.IPraiseAndViewManagerService;
import com.rimi.ctibet.web.service.IRoleService;

/**
 * 是否点赞 tag
 * @author dengxh
 *
 */
public class IsPraiseTag extends TagSupport {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	
	private String falsePraise;
	
	private String code;
	
	
	
	public int doStartTag() throws JspException {
		IPraiseAndViewManagerService praiseAndViewManagerService  = (IPraiseAndViewManagerService) SpringUtil.getAppCtx().getBean("praiseAndViewManagerService");
	    boolean b  = false;//查询 url 路径是否有权限 roleService
	    b = praiseAndViewManagerService.isRecored(getIpAddr((HttpServletRequest)pageContext.getRequest()), code);
		if (b) {
			return EVAL_PAGE;//继续执行后面的页面
		}else{
			return SKIP_BODY;//忽略标签中的菜单权限
		}
	}
	/**
	 * 获取访问者IP
	 * 
	 * 在一般情况下使用Request.getRemoteAddr()即可，但是经过nginx等反向代理软件后，这个方法会失效。
	 * 
	 * 本方法先从Header中获取X-Real-IP，如果不存在再从X-Forwarded-For获得第一个IP(用,分割)，
	 * 如果还不存在则调用Request .getRemoteAddr()。
	 * 
	 * @param request
	 * @return
	 */
	public  String getIpAddr(HttpServletRequest request) {
		String ip = request.getHeader("X-Real-IP");
		if (!StringUtils.isBlank(ip) && !"unknown".equalsIgnoreCase(ip)) {
			return ip;
		}
		ip = request.getHeader("X-Forwarded-For");
		if (!StringUtils.isBlank(ip) && !"unknown".equalsIgnoreCase(ip)) {
			// 多次反向代理后会有多个IP值，第一个为真实IP。
			int index = ip.indexOf(',');
			if (index != -1) {
				return ip.substring(0, index);
			} else {
				return ip;
			}
		} else {
			return request.getRemoteAddr();
		}
	}
	public int doEndTag() throws JspException {
		return EVAL_PAGE;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}
	public String getFalsePraise() {
		return falsePraise;
	}
	public void setFalsePraise(String falsePraise) {
		this.falsePraise = falsePraise;
	}
}
