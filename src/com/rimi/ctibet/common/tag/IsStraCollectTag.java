package com.rimi.ctibet.common.tag;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

import com.rimi.ctibet.common.util.SpringUtil;
import com.rimi.ctibet.domain.Role;
import com.rimi.ctibet.domain.UserFavorite;
import com.rimi.ctibet.domain.UserInfo;
import com.rimi.ctibet.web.controller.UserInfoController;
import com.rimi.ctibet.web.service.IPraiseAndViewManagerService;
import com.rimi.ctibet.web.service.IRoleService;
import com.rimi.ctibet.web.service.IUserFavoriteService;

/**
 * 是否收藏 tag
 * @author dengxh
 *
 */
public class IsStraCollectTag extends TagSupport {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	
	
	private String code;
	
	private String memCode;//会员code
	
	public int doStartTag() throws JspException {
	    IUserFavoriteService  userFavoriteService =	(IUserFavoriteService) SpringUtil.getAppCtx().getBean("userFavoriteService");
	    boolean b  = false;//查询 url 路径是否有权限 roleService
			 b  =  userFavoriteService.isFavAlready(memCode, code, ""+UserFavorite.User_Fav_Stra);
	    if (b) {
			return EVAL_PAGE;//继续执行后面的页面
		}else{
			return SKIP_BODY;//忽略标签中的菜单权限
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

	public String getMemCode() {
		return memCode;
	}

	public void setMemCode(String memCode) {
		this.memCode = memCode;
	}

	
	
	
}
