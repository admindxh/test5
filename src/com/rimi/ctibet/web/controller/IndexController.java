package com.rimi.ctibet.web.controller;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.rimi.ctibet.domain.Programa;
import com.rimi.ctibet.web.service.IContentService;
import com.rimi.ctibet.web.service.IProgramaService;


@Controller
@RequestMapping("web/index")
public class IndexController {
  @Resource
  private IProgramaService programaService;
  @Resource
  private IContentService contentService;
  
  @RequestMapping("init")
  public ModelAndView init(){
	  ModelAndView view = new ModelAndView("manage/html/index");
	  List<Programa> topProgramas = programaService.getTopPrograma();
	  List<Programa> defaultSendProgramas = programaService.getSendPrograma(topProgramas.get(0).getCode());
	  for (Programa programa : defaultSendProgramas) {
		//System.out.println(programa.getProgramaName());
	 }
	  view.addObject("topProgramas", topProgramas);
	  view.addObject("defaultSendProgramas", defaultSendProgramas);
	  return view;
  }
  
  
  
  /**
   * 跳入导航管理
   * @param request
   * @param response
   * @param modelMap
   * @return
   */
  @RequestMapping("intoPage")
  public String  intoPageIndexMn(HttpServletRequest request,HttpServletResponse response,ModelMap modelMap){
	  String  path  = request.getParameter("path");
	  
	  
	  return   path;
  }
  
  
  
	
}
