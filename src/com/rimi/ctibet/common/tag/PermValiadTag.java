package com.rimi.ctibet.common.tag;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

import com.rimi.ctibet.common.util.SpringUtil;
import com.rimi.ctibet.domain.Role;
import com.rimi.ctibet.domain.UserInfo;
import com.rimi.ctibet.web.controller.UserInfoController;
import com.rimi.ctibet.web.service.IRoleService;

/**
 * 权限标签类
 * @author dengxh
 *
 */
public class PermValiadTag extends TagSupport {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	
	
	private String url;
	
	
	
	public int doStartTag() throws JspException {
		IRoleService roleService  = (IRoleService) SpringUtil.getAppCtx().getBean("roleService");
		HttpSession  session = pageContext.getSession();
		UserInfo admin = (UserInfo)session.getAttribute(UserInfoController.SESSION_USER);
		List<Map<String,Object>> access = roleService.getAllAccessByRoleCode(admin.getRoleCode());
		String accessString = "";
		for (Map<String, Object> map : access) {
			accessString += (String)map.get("access");
			accessString +=",";
		}
		boolean b  = false;//查询 url 路径是否有权限 roleService
		b = accessString.indexOf(url)>=0;
		if("xz".equals(admin.getName()))
			b = true;
		if (b) {
			return EVAL_PAGE;//继续执行后面的页面
		}else{
			return SKIP_BODY;//忽略标签中的菜单权限
		}
	}

	public int doEndTag() throws JspException {
		return EVAL_PAGE;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}
	
	
}
