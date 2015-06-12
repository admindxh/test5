package com.rimi.ctibet.common.tag;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

import com.rimi.ctibet.common.util.SpringUtil;
import com.rimi.ctibet.domain.LogUser;
import com.rimi.ctibet.domain.Role;
import com.rimi.ctibet.domain.UserInfo;
import com.rimi.ctibet.domain.UserView;
import com.rimi.ctibet.web.controller.UserInfoController;
import com.rimi.ctibet.web.service.IPraiseAndViewManagerService;
import com.rimi.ctibet.web.service.IRoleService;
import com.rimi.ctibet.web.service.UserViewService;

/**
 * 去过和想去
 * @author dengxh
 *
 */
public class WanAndGoTag extends TagSupport {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String userCode;
	
	private String desCode ;
	
	private String wananddes;
	
	
	public int doStartTag() throws JspException {
		UserViewService userViewService  = (UserViewService) SpringUtil.getAppCtx().getBean("userViewService");
		
		UserView userView   =  userViewService.getUserDes(userCode, desCode, wananddes);
	    boolean b  = false;//
		
	    if (userView!=null) {
			 b  = true;
		}
	    
	    if (b) {
			return EVAL_PAGE;//继续执行后面的页面
		}else{
			return SKIP_BODY;//忽略标签中的菜单权限
		}
	}

	/**
	 * 获取前台用户会员
	 * @return
	 */
	public LogUser  getFrontUser(){
		return null;
		//return 	this.getFrontUser().getAttribute("logUser")==null?null:(LogUser)this.session.getAttribute("logUser");
	}
	public int doEndTag() throws JspException {
		return EVAL_PAGE;
	}

	public String getUserCode() {
		return userCode;
	}

	public void setUserCode(String userCode) {
		this.userCode = userCode;
	}

	public String getDesCode() {
		return desCode;
	}

	public void setDesCode(String desCode) {
		this.desCode = desCode;
	}

	public String getWananddes() {
		return wananddes;
	}

	public void setWananddes(String wananddes) {
		this.wananddes = wananddes;
	}

	

	
	
	
}
