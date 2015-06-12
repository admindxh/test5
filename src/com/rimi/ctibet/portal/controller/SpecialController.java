package com.rimi.ctibet.portal.controller;

import javax.annotation.Resource;
import javax.servlet.ServletContext;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.ServletContextAware;

import com.google.gson.Gson;
import com.rimi.ctibet.common.controller.BaseController;
import com.rimi.ctibet.common.util.Pager;
import com.rimi.ctibet.domain.Content;
import com.rimi.ctibet.web.service.IContentService;
import com.rimi.ctibet.web.service.IPraiseService;

/**
 * 专题
 */
@Controller("portal_specialController")
@RequestMapping({"portal/specialController","special"})
public class SpecialController extends BaseController implements ServletContextAware {
	
	ServletContext application;
	
	@Resource IContentService contentService;
	@Resource IPraiseService praiseService;
	
    @ModelAttribute()
    public void initMethod(Model model){
        model.addAttribute("programNam","5");
    }
	
	/**
	 * 专题活动列表页 按时间和热度获取专题列表
	 * portal/specialController/getSpecialListOrderBy
	 */
	@RequestMapping(value="getSpecialListOrderBy", produces = "text/html; charset=utf-8")
	@ResponseBody
	public String getSpecialListOrderBy(Pager pager, int orderBy){
		pager = contentService.searchSpecailListByNameTime(pager, null, null, orderBy);
		pager.setTotalPage(pager.getTotalPage());
		return new Gson().toJson(pager);
	}
	
	/**
	 * 跳转到专题详情页
	 */
	@RequestMapping({"forSpecialDetail","detail"})
	public String forSpecialDetail(Model model, String code){
	    //查看量 +1
	    //if(!checkSessionOperate(session, code + "_check_special_detail")){
	        praiseService.updateViewCountByContentCode(code);
	    //}
	    
	    Content content = contentService.findByCodeContentType(code, Content.CONTENTTYPE_SPECIAL);
	    model.addAttribute("content", content);
	    return "portal/app/activity/special-detail";
	}
	
	/********************************************
	 * Setter Getter
	 ********************************************/
	@Override
	public void setServletContext(ServletContext arg0) {
		this.application = arg0;
	}
	
}
