package com.rimi.ctibet.web.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.aspectj.weaver.patterns.IVerificationRequired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.rimi.ctibet.common.controller.BaseController;
import com.rimi.ctibet.domain.Destination;
import com.rimi.ctibet.domain.View;
import com.rimi.ctibet.web.controller.vo.DesAndViewStatisVo;
import com.rimi.ctibet.web.service.DestinationService;
import com.rimi.ctibet.web.service.IDesAndViewService;
import com.rimi.ctibet.web.service.IViewService;

@Controller
@RequestMapping("web/desAndViewStatisController")
public class DesAndViewStatisController extends BaseController{
	
	@Resource(name="desAndViewService")
	private IDesAndViewService desAndViewService;
	
	@Resource
	private DestinationService destinationServiceImpl;
	
	
	@Resource 
	private IViewService viewService;
	
	
	
	@RequestMapping("intoDesAndViewStatis")
	public  String intoDesAndViewStatis(String start,String end,String destinationCode,String viewCode,HttpServletRequest request,HttpServletResponse response,ModelMap modelMap){
		//目的地维护
		SimpleDateFormat dateFormat  =  new SimpleDateFormat("yyyy-MM-dd");
		String menu   = request.getParameter("menu");
		if (StringUtils.isNotBlank(menu)) {
			start  =  dateFormat.format(new Date());
			end    =  dateFormat.format(new Date());
		}
		String destinationCodeText = request.getParameter("destinationCodeText");
		String viewCodeText = request.getParameter("viewCodeText");
		if (!StringUtils.isNotBlank(destinationCodeText)) {
			destinationCodeText = "全部目的地"; 
		}
		if (!StringUtils.isNotBlank(viewCodeText)) {
			viewCodeText = "全部景点"; 
		}
		
		List<Destination> destinationList  = destinationServiceImpl.getAllDestination();
		List<View>  viewList  = viewService.findViewByDestinationCode(destinationCode);
		
		DesAndViewStatisVo andViewStatisVo   =  desAndViewService.getDesViewVo(start, end, destinationCode, viewCode);
		modelMap.addAttribute("andViewStatisVo", andViewStatisVo);
		modelMap.addAttribute("destinationList",destinationList);
		modelMap.addAttribute("viewList",viewList);
		modelMap.addAttribute("viewCodeText", viewCodeText);
		modelMap.addAttribute("destinationCodeText", destinationCodeText);
		modelMap.addAttribute("destinationCode", destinationCode);
		modelMap.addAttribute("viewCode", viewCode);
		modelMap.addAttribute("end", end);
		modelMap.addAttribute("start", start);
		
		return "manage//html//settings//dataStatis-destScenic";
	}

}
