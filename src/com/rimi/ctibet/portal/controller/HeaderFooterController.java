package com.rimi.ctibet.portal.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.rimi.ctibet.common.controller.BaseController;
import com.rimi.ctibet.common.util.SpringUtil;
import com.rimi.ctibet.domain.Destination;
import com.rimi.ctibet.domain.Image;
import com.rimi.ctibet.domain.Programa;
import com.rimi.ctibet.web.controller.HomeController;
import com.rimi.ctibet.web.service.DestinationService;
import com.rimi.ctibet.web.service.IProgramaService;
import com.rimi.ctibet.web.service.ImageService;

@Controller
@RequestMapping("portal/headerFooterController")
public class HeaderFooterController extends BaseController {

	@Resource IProgramaService programaService;
	@Resource DestinationService destinationService;
	
	@RequestMapping("header")
	public String header(Model model,HttpServletRequest request,HttpServletResponse response){
		List<Programa> programaList = programaService.getSendPrograma("e8876fe6-7608-11e4-b6ce-005056a05bc9") ;
		List<Destination> destinationList = destinationService.getAllDestination();
		model.addAttribute("programaList", programaList);
		model.addAttribute("destinationList", destinationList);
		String  url = "portal/app/header/index";
		 if (StringUtils.isNotBlank(request.getParameter("seo_index"))) {
			url = "portal/app/header/seo_index";
		}
		return  url;
	}
	
	@RequestMapping("footer")
	public String footer(Model model){
		return "portal/app/footer/index";
	}
	@RequestMapping("window")
	public  String  window(HttpServletRequest request,HttpServletResponse response,ModelMap modelMap){
		
		 //浮窗体
         List<Image> homeWindowList  =  HomeController.listImage;
		
		 ImageService  imageServiceIml  =    (ImageService) SpringUtil.getAppCtx().getBean("ImageService");
		 Map<String,List<Image>>	  blist  =  imageServiceIml.getHomeImg();
		 Map<String,List<Image>>  bannerMap   =  blist;
		 if (homeWindowList!=null&&homeWindowList.size()>=1) {
    		 if (modelMap!=null) {
    			 modelMap.put("img", homeWindowList);
			}
		}
		else if (bannerMap!=null) {
	    	 if (bannerMap!=null&&bannerMap.size()>=1) {
	    		 modelMap.put("img",bannerMap.get("homeWindowList"));
			 } 
	    	
		}
		   
	     
	     HomeController.listImage =  new ArrayList<Image>();
	    //modelMap.put("img", homeWindowList.get(0));
	     return "portal//app//header//window";
	}
	
	
	
	
}
