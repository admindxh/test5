package com.rimi.ctibet.portal.controller;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.rimi.ctibet.common.controller.BaseController;
import com.rimi.ctibet.domain.Content;
import com.rimi.ctibet.web.service.IContentService;
/**
 * 门户其他页面
 * 
 * @author chengsl
 *
 */
@Controller("otherController")
@RequestMapping("portal/app/setting/")
public class OtherController extends BaseController {
	private static final String BASEURL="portal/app/setting/";
	@Resource
	private IContentService contentService;
	@RequestMapping("other")
	public ModelAndView other(String code)
	{
		ModelAndView view = new ModelAndView();
		view.addObject("content", contentService.findByCode(code));
		return view;
	}
	@RequestMapping("preother")
	public ModelAndView preOther(Content content)
	{
		ModelAndView view = new ModelAndView();
		view.addObject("content", content);
		view.setViewName(BASEURL+"other");
		return view;
	}
	
}
