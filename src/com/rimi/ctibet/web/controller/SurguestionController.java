package com.rimi.ctibet.web.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.rimi.ctibet.common.util.Pager;
import com.rimi.ctibet.common.util.Uuid;
import com.rimi.ctibet.domain.LogUser;
import com.rimi.ctibet.domain.MemberInfo;
import com.rimi.ctibet.domain.Surguestion;
import com.rimi.ctibet.web.service.IMemberService;
import com.rimi.ctibet.web.service.ISurguestionService;

@Controller
@RequestMapping("/web/surguestion/")
public class SurguestionController {
	@Resource
	private ISurguestionService surguestionService;
	@Resource
	private IMemberService memberService;
	/**
	 * 查询列表
	 * @param req
	 * @param currentPage
	 * @return
	 */
	@RequestMapping("list")
	public String list(HttpServletRequest req,@RequestParam(defaultValue="1",required=false)int currentPage){
		Pager pager=new Pager();
	    pager.setCurrentPage(currentPage);
	    pager.setPageSize(20);
	    List param=new ArrayList();
	    surguestionService.findListBySql(param, pager);
	    List<Surguestion> list=pager.getDataList();
	    for(Surguestion s:list){
	    	if(s.getMemberCode()!=null){
	    		if("tour".equals(s.getMemberCode())){
	    			s.setUsername("游客");
	    		}else{
	    			MemberInfo mi=memberService.findMemberInfo(s.getMemberCode());
	    			if(mi!=null){
	    				if(mi.getName()!=null){
	    					s.setUsername(mi.getName());
	    				}else{
	    					s.setUsername("未知用户");
	    				}
	    			}else{
	    				s.setUsername("未知用户");
	    			}
	    		}
	    	}
	    }
	    req.setAttribute("pager", pager);
		return "manage/html/settings/surguestion";
	}
    
	/**
	 * 添加建议
	 * @param content
	 * @return
	 * @throws JSONException 
	 */
	@RequestMapping(value="save",produces="text/html;charset=utf-8")
	@ResponseBody
	public String save(HttpServletRequest req,String content) throws JSONException{
		JSONObject obj=new JSONObject();
		if(StringUtils.isBlank(content)){
			obj.put("status", -1);
			return obj.toString();
		}
		LogUser lg=(LogUser)req.getSession().getAttribute("logUser");
		Surguestion s=new Surguestion();
		  s.setCode(Uuid.uuid());
		  s.setContent(content);
		  s.setCreateDate(new Date());
		  s.setAvaliable(1);
		  if(lg!=null){
			  if(lg.getCode()!=null){
				  s.setMemberCode(lg.getCode());
			  }
		  }else{
		      s.setMemberCode("tour");
		  }
		try {
			surguestionService.save(s);
			obj.put("status", 1);
		} catch (Exception e) {
			e.printStackTrace();// TODO: handle exception
			obj.put("status", 0);
		}
		  
		return obj.toString();
	}
	
	/**
	 * 跳转到详情页
	 * @param req
	 * @param code
	 * @return
	 */
	@RequestMapping("detail")
	public String detail(HttpServletRequest req,String code){
		if(StringUtils.isBlank(code)){
			req.setAttribute("delflag", -1);
			return list(req,1);
		}
		Surguestion s=surguestionService.findByCode(code);
		if(s.getMemberCode()!=null){
    		if("tour".equals(s.getMemberCode())){
    			s.setUsername("游客");
    		}else{
    			MemberInfo mi=memberService.findMemberInfo(s.getMemberCode());
    			if(mi!=null){
    				if(mi.getName()!=null){
    					s.setUsername(mi.getName());
    				}else{
    					s.setUsername("未知用户");
    				}
    			}else{
    				s.setUsername("未知用户");
    			}
    		}
    	}
		req.setAttribute("sur", s);
		return "manage/html/settings/surguestion_detail";
	}
	
	/**
	 * 删除
	 * @param req
	 * @param code
	 * @return
	 */
	@RequestMapping("delete")
	public String delete(HttpServletRequest req,String code){
		if(StringUtils.isBlank(code)){
			req.setAttribute("delflag", -1);
			return list(req,1);
		}
		Surguestion s=surguestionService.findByCode(code);
		if(s!=null){
			s.setAvaliable(0);
			try {
				surguestionService.update(s);
				req.setAttribute("delflag", 1);
			} catch (Exception e) {
				e.printStackTrace();// TODO: handle exception
				req.setAttribute("delflag", 0);
			}
			
		}
		return list(req,1);
	}
}
