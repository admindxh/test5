package com.rimi.ctibet.portal.controller;

import java.util.Date;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.rimi.ctibet.common.controller.BaseController;
import com.rimi.ctibet.domain.PageView;
import com.rimi.ctibet.web.service.IPageViewService;

/**
 * 页面浏览量统计 
 * @author dengxh
 *
 */
@Controller
@RequestMapping("portal/pageViewController")
public class PageViewController  extends  BaseController{
	 
	@Resource(name="pageViewServiceImpl")
	private IPageViewService pageViewService;
	
	
	@RequestMapping("pageViewStatistics")
	@ResponseBody
	public String  pageViewStatistics(PageView pageView,HttpServletRequest request,HttpServletResponse response){
		pageView.setIp(getIpAddr(request));
		pageView.setViewtime(new Date());
		pageViewService.save(pageView);
		return "";
	}
	
	
	

	public IPageViewService getPageViewService() {
		return pageViewService;
	}

	public void setPageViewService(IPageViewService pageViewService) {
		this.pageViewService = pageViewService;
	}
	
	
	
	
	
	
	
}
