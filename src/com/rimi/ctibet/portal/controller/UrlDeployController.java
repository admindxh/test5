package com.rimi.ctibet.portal.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.rimi.ctibet.common.controller.BaseController;
import com.rimi.ctibet.common.util.ReadSettingProperties;

/**
 * 主要模块控制url
 */
@Controller
public class UrlDeployController extends BaseController{
		
	@RequestMapping({"home","tourism","discover","ride","activity","community"})
	public String urlDeployInto(HttpServletRequest request,HttpServletResponse response,ModelMap modelMap){
		String requesturl  = request.getRequestURI().substring(request.getRequestURI().lastIndexOf("/")+1);
		String skipurl = ReadSettingProperties.getValue("UrlDeploy.properties", requesturl);
		try {
			request.getRequestDispatcher(skipurl).forward(request, response);
		} catch (ServletException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "";
	}
	
	@RequestMapping("wsds")
	public String wsds(HttpServletRequest request,HttpServletResponse response,ModelMap modelMap){
		
		return "portal/app/wsds";
	}
	
	
}
