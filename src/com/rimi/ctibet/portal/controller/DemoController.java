package com.rimi.ctibet.portal.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.rimi.ctibet.common.controller.BaseController;
import com.rimi.ctibet.common.util.StringUtil;
import com.rimi.ctibet.domain.LogUser;

@Controller
@RequestMapping("portal/demoController")
public class DemoController extends BaseController {
	
	@RequestMapping("test1")
	public String test1(){
		//System.out.println(1);
		return forward("");
	}
	
	@RequestMapping("testlog")
	public void testlog(String code){
		if(StringUtil.isNotNull(code)){
			LogUser logUser = new LogUser();
			logUser.setMemberCode(code);
			logUser.setCode(code);
			session.setAttribute("logUser", logUser);
			//System.out.println(logUser.getMemberCode()+"：登录");
		}
	}
	
}
