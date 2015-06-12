package com.rimi.ctibet.web.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.rimi.ctibet.common.util.Pager;
import com.rimi.ctibet.common.util.StringUtil;
import com.rimi.ctibet.web.controller.vo.ActivetyTotalVo;
import com.rimi.ctibet.web.controller.vo.FrontContentVo;
import com.rimi.ctibet.web.service.IActivityService;

/**
 * 活动统计
 * @author dengxh
 *
 */
@Controller
@RequestMapping("web/activityStatisController")
public class ActivityStatisController {

	
	@Resource
	public  IActivityService activityService;
	
	@RequestMapping("intoActivityStatis")
	public String  intoActivityStatis(Pager pager,String start,String end,HttpServletRequest request,HttpServletResponse response,ModelMap modelMap){
		
		//目的地维护
		SimpleDateFormat dateFormat  =  new SimpleDateFormat("yyyy-MM-dd");
		String menu   = request.getParameter("menu");
		if (StringUtils.isNotBlank(menu)) {
			start  =  dateFormat.format(new Date());
			end    =  dateFormat.format(new Date());
		}
		
		ActivetyTotalVo activetyTotalVo =  activityService.getActivetyTotal(start, end);
		Pager result  = activityService.getActivetyTotalPager(start, end, pager);
		activetyTotalVo.setCheckNum(getCheckNum(result));
		modelMap.addAttribute("activetyTotalVo", activetyTotalVo);
		modelMap.addAttribute("result", result);
		modelMap.addAttribute("start",start);
		modelMap.addAttribute("end",end);
		return "manage/html/settings/dataStatis-activity";
	}
	
	public int getCheckNum(Pager  pager){
		
		 List dataList = pager.getDataList();
		 int rc  =   0 ;
		 if (dataList!=null) {
			 for (Object object : dataList) {
				 ActivetyTotalVo m  = (ActivetyTotalVo) object;
				rc +=  m.getCheckNum();
			 }
			
		}
		  
		
		return  rc;
	}
	
}
