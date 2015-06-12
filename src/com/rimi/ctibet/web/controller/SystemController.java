package com.rimi.ctibet.web.controller;

import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.rimi.ctibet.common.controller.BaseController;
import com.rimi.ctibet.common.util.Pager;
import com.rimi.ctibet.common.util.StringUtil;
import com.rimi.ctibet.common.util.VeDate;
import com.rimi.ctibet.domain.Programa;
import com.rimi.ctibet.web.dao.IMemberDao;
import com.rimi.ctibet.web.service.IContentService;
import com.rimi.ctibet.web.service.ILoginLogService;
import com.rimi.ctibet.web.service.IMemberService;
import com.rimi.ctibet.web.service.IPageViewService;
import com.rimi.ctibet.web.service.IProgramaService;

@Controller
@RequestMapping("web/systemController")
public class SystemController  extends BaseController{
		
	    @Resource
		private IMemberService memberService;
	    
		@Resource
		private IPageViewService pageViewService;
		
		@Resource 
		private ILoginLogService loginLogService;

		@Resource
		private IContentService contentService;
		
		@Resource
		private IProgramaService programaService;
		
		
		@RequestMapping("intoSystemStatic")
		public String intoSystemStatic(String start,String end,HttpServletRequest request,HttpServletResponse response,ModelMap modelMap) throws ParseException{
		   SimpleDateFormat  sformat   = new SimpleDateFormat("yyyy-MM-dd");
		   
		   //当前时间
		   Date  currentDate  =  new Date();
		   //系统上线时间
		   //動態加載 上线时间
		   Programa  dataTime  =  programaService.getProgramaByCode("78abg9285-1b3c-1s24-bab-0211056a05bcs");
		   Date d  = dataTime.getCreateTime();
		   String sysOnline  = sformat.format(d) ;
		   Date  sysOnlineDate  =  sformat.parse(sysOnline);
		   //默认pv和会员 时间差
		   Long  dayCha  = ((currentDate.getTime()-sysOnlineDate.getTime())/(24*60*60*1000));
		   
			Date start1  = null;
			Date end1  = null;
			if (StringUtil.isNotNull(start)) {
				 start1  =   sformat.parse(start);
			}
			if (StringUtil.isNotNull(end)) {
				end1  = sformat.parse(end);
			}
			
			String menu  = request.getParameter("menu");
			//默认查询当天的时间
			if(StringUtil.isNotNull(menu)&&!StringUtil.isNotNull(start)&&!StringUtil.isNotNull(end)){
				ParsePosition pos = new ParsePosition(0);
					start1  =  new Date();
			        end1  =    new Date();
		         start = sformat.format(start1);
		         end  =   sformat.format(end1);
		        
			}
			//顶部
			
			//总的注册数量
			Integer memTotal  =  memberService.getMemCountByTime(null,null);
			modelMap.addAttribute("memTotal", memTotal);
			
			//    pv 总数
			Integer  pvTotal  = pageViewService.getMemCountByTime(null,null);
			
			modelMap.addAttribute("pvTotal", pvTotal);
			
			// 顶部  平均  pv 值
			Integer pvDayTotal  = pvTotal/Integer.valueOf(dayCha.toString());
			
			modelMap.addAttribute("pvDayTotal", pvDayTotal);
			
			//顶部总的评论数
			Integer replyTotal  =  contentService.getReplyCount(null, null);
		    Pager	pager = contentService.searchReplyByContentTypeState(new Pager(), "", 1);
		    //replyTotal = pager.getTotalCount();
			modelMap.addAttribute("replyTotal", replyTotal);
			//总的攻略数量
			Integer strageTotal  = contentService.getStrageCount(null,null);
			modelMap.addAttribute("strageTotal", strageTotal);
			
			
		    //pv 天数  和 会员注册数量差值天
			Integer  memDayCha  = 1;	
			if(!StringUtil.isNotNull(start)&&!StringUtil.isNotNull(end)){
		          memDayCha = Integer.valueOf(dayCha.toString());
			}else{
				 if (!StringUtil.isNotNull(start)&&StringUtil.isNotNull(end)) {
					  Long  mdc  = ((end1.getTime()-sysOnlineDate.getTime())/(24*60*60*1000));
					 memDayCha  = Integer.valueOf(mdc.toString());
				 }else if(StringUtil.isNotNull(start)&&!StringUtil.isNotNull(end)){
					 Long  mdc  = ((currentDate.getTime()-start1.getTime())/(24*60*60*1000));
					 memDayCha  = Integer.valueOf(mdc.toString());
				 }
			 }
			
			 //列表查询结果统计
			  
			Integer listMemCount  = memberService.getMemCountByTime(start1, end1);//查询注册数量
			
			modelMap.addAttribute("listMemCount", listMemCount);
			
			Integer listMemActiveCount  = loginLogService.getActUserNum(start1, end1); //活跃数量
			modelMap.addAttribute("listMemActiveCount", listMemActiveCount);
			
			Integer listMemActiveDayCount  =  listMemActiveCount/memDayCha;//日活跃平均数量
			modelMap.addAttribute("listMemActiveDayCount", listMemActiveDayCount);
			
			//pv 量统计列表
			
			Integer  listPvTotal  =  pageViewService.getMemCountByTime(start1, end1);
			modelMap.addAttribute("listPvTotal", listPvTotal);
			
			if(StringUtil.isNotNull(start)&&StringUtil.isNotNull(end)){
				 Long  mdc  = ((end1.getTime()-start1.getTime())/(24*60*60*1000));
				 memDayCha  = Integer.valueOf(mdc.toString());
			}
			if (memDayCha==0) {
				memDayCha = 1;
			}
			
			
			Integer  listPvDayTotal  =  listPvTotal/memDayCha ;//日 平均  pv 量
			modelMap.addAttribute("listPvDayTotal", listPvDayTotal);
			
			Integer listReplyTotal  =  contentService.getReplyCount(start1, end1);
			modelMap.addAttribute("listReplyTotal", listReplyTotal);
			
			Integer listStrageTotal  =  contentService.getStrageCount(start1, end1);
			modelMap.addAttribute("listStrageTotal", listStrageTotal);
			
			//查询条件日期
			modelMap.addAttribute("start1", start);
			modelMap.addAttribute("end1", end);
			
			
			return "manage//html//settings//dataStatis-system";
		}
		
	
	
}
