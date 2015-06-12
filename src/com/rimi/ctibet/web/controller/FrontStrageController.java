package com.rimi.ctibet.web.controller;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.rimi.ctibet.common.constants.Constants;
import com.rimi.ctibet.common.controller.BaseController;
import com.rimi.ctibet.common.util.CodeFactory;
import com.rimi.ctibet.common.util.KeyWordFilter;
import com.rimi.ctibet.common.util.LuceneUtil;
import com.rimi.ctibet.common.util.Pager;
import com.rimi.ctibet.domain.Content;
import com.rimi.ctibet.domain.Destination;
import com.rimi.ctibet.domain.LogUser;
import com.rimi.ctibet.domain.Member;
import com.rimi.ctibet.domain.MemberInfo;
import com.rimi.ctibet.domain.Praise;
import com.rimi.ctibet.domain.Programa;
import com.rimi.ctibet.domain.StrategyView;
import com.rimi.ctibet.domain.View;
import com.rimi.ctibet.portal.controller.vo.DesAndViewJson;
import com.rimi.ctibet.portal.controller.vo.ViewJson;
import com.rimi.ctibet.web.service.DestinationService;
import com.rimi.ctibet.web.service.IContentService;
import com.rimi.ctibet.web.service.IMemberService;
import com.rimi.ctibet.web.service.IPraiseService;
import com.rimi.ctibet.web.service.IProgramaContentService;
import com.rimi.ctibet.web.service.IProgramaService;
import com.rimi.ctibet.web.service.IStrategyViewService;
import com.rimi.ctibet.web.service.IViewService;

/**
 * 前端用户攻略验证
 * @author dengxh
 *
 */
@Controller
@RequestMapping({"portal/frontStrageController","tourism/upload"})
public class FrontStrageController  extends BaseController{
	
	@Resource
	private DestinationService destinationServiceImpl;
	
	@Resource
	private  IViewService viewServiceImpl;
	
	@Resource
	private IProgramaService programaServiceImpl;
	
	@Resource
	private  IMemberService  memberService;  
	
	@Resource
	private IPraiseService praiseService;
	
	
	@Resource
	private IStrategyViewService strategyViewService;
	
	@Resource
	private IContentService contentServiceImpl;
	
	@Resource
	private IContentService contentService;
	
	@ModelAttribute
	public void setNavSelectIndex(HttpServletRequest request,HttpServletResponse response,ModelMap modelMap){
		modelMap.put("programNam","1");
	}
	
	
	/**
	 * 进入攻略页面
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping("intoStragePage")
	public String intoStragePage(HttpServletRequest request,HttpServletResponse response,ModelMap model){
		LogUser logUser =  super.getFrontUser();
		
		
		
		if (logUser==null) {
			return "redirect:/portal/clientLog/loginPage";
		}
		//查询条件封装  板块栏目   code  098bf91c-792e-11e4-b6ce-005056a05bc9
		List<Programa> programsList  = programaServiceImpl.getSendPrograma("098bf91c-792e-11e4-b6ce-005056a05bc9");
		 List<Destination> destinationList  = destinationServiceImpl.getAllDestination();
		 List<DesAndViewJson> andViewJsons = new ArrayList<DesAndViewJson>();
		 if (destinationList!=null) {
			 for (int i = 0; i < destinationList.size(); i++) {
				 DesAndViewJson desAndViewJson  = new DesAndViewJson();
				desAndViewJson.setChecked(false);
				Destination destination =  destinationList.get(i);
				desAndViewJson.setName(destination.getDestinationName());
				desAndViewJson.setId(destinationList.get(i).getCode());
				//获取景点对象
				List<View> views  = viewServiceImpl.findViewByDestinationCode(destinationList.get(i).getCode());
				List<ViewJson> viewJsons =  new ArrayList<ViewJson>();
				for (View view : views) {
					ViewJson json  = new ViewJson();
					json.setChecked(false);
					json.setName(view.getViewName());
					json.setId(view.getCode());
					viewJsons.add(json);
				}
				desAndViewJson.setItems(viewJsons);
				andViewJsons.add(desAndViewJson);
			 }
			 //list 目的地和景点 json
			 String list  = JSONArray.fromObject(andViewJsons).toString();
			 //System.out.println(list);
			 model.addAttribute("list", list);
		}
		  model.addAttribute("programsList", programsList);
		 String sourceHref  =  request.getParameter("sourceHref");
		 model.addAttribute("sourceHref", sourceHref);
		return "portal//app//tourism//touristnotes";
	}
	
	
	/**
	 * 游西藏审核通过攻略管理列表保存
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 */
	@RequestMapping("save")
	@ResponseBody
	public String   dynamicDataSave(String
			content,String contentTitle,String programaCode,Integer isOfficial,String authorCode
			,String []areas,String[]spots,HttpServletRequest request,HttpServletResponse response,ModelMap modelMap){
				LogUser logUser =  super.getFrontUser();
				if (logUser==null) {
					return "redirect:/portal/clientLog/loginPage";
				}
		Content c  =  new Content();
		c.setContentType(Content.CONTENTTYPE_STRATEGY);//内容内型
		c.setState(Constants.STATUS_CHECK);
		content =  KeyWordFilter.getInstance().getReplaceStrTxtKeyWords(content, "**")  ; 
		c.setContent(content);
		c.setContentTitle(contentTitle);
		
		String  c2  = contentTitle;
	       c2 =  KeyWordFilter.getInstance().getReplaceStrTxtKeyWords(c2, "**")  ; 
		c.setContentTitle(c2);
		c.setCreateuserCode(logUser.getCode());
		c.setProgramaCode(programaCode);
		c.setIsOfficial(0);//用户上传
		//获取当前用户
		if (this.getManagerUser()!=null) {
			//c.setCreateuserCode(this.getManagerUser().getCode());
		}
		
		if (logUser!=null) {
			c.setAuthorCode(logUser.getUsername());
			c.setCreateuserCode(logUser.getCode());
		}
		
		String code  = CodeFactory.create("yxzgl");
		String url  = this.getUrl("/tourism/strage/detail", code);
	    c.setCode(code);
	    c.setUrl(url);
		contentServiceImpl.saveStrategy(c, programaCode, spots, areas);
	    //保存赞
		Praise praiseSave  = new Praise();
		praiseSave.setContentCode(code);
		//赞
		praiseSave.setTruePraise(0);
		praiseSave.setFalsePraise(0);
		//查看
	    praiseSave.setFavoriteNum(0);
		praiseSave.setFalseFavoriteNum(0);

		//浏览
		
		praiseSave.setViewCount(0);
		praiseSave.setFalseViewcount(0);
		
	    praiseService.savePraise(praiseSave);
	    
		//保存成功后调用添加lucene 等待xiaozhen提供
		//LuceneUtil.addDocuemnt(content,code);//url 待提供路径
		String sourceHref  = request.getParameter("sourceHref");
		if (!StringUtils.isNotBlank(sourceHref)) {
			 sourceHref = "/" ;
		}
		return "";
		
	}
	/**
	 * 进入攻略页面
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping("edit")
	public String edit(HttpServletRequest request,HttpServletResponse response,ModelMap model,String code){
		LogUser logUser =  super.getFrontUser();
		if (logUser==null) {
			//return "redirect:/portal/clientLog/loginPage";
		}
		String viewCodes="";
		String destCodes="";
		 //查询关联的目的地 code
		  List<StrategyView> realList  =  strategyViewService.findDestinationCodeByContentCode(code);
         //实际的景点
		  List<StrategyView> realListViews  =      strategyViewService.findViewCodeByContentCode(code);
		  
		//查询条件封装  板块栏目   code  098bf91c-792e-11e4-b6ce-005056a05bc9
		List<Programa> programsList  = programaServiceImpl.getSendPrograma("098bf91c-792e-11e4-b6ce-005056a05bc9");
		 List<Destination> destinationList  = destinationServiceImpl.getAllDestination();
		 List<DesAndViewJson> andViewJsons = new ArrayList<DesAndViewJson>();
		 if (destinationList!=null) {
			 for (int i = 0; i < destinationList.size(); i++) {
				 DesAndViewJson desAndViewJson  = new DesAndViewJson();
				 for(StrategyView sv:realList){
					 if(destinationList.get(i).getCode().equals(sv.getDestinationCode())){
						 desAndViewJson.setChecked(true);
					 }else{
						 desAndViewJson.setChecked(false);
					 }
					 destCodes=sv.getDestinationCode()+",";
				 }
				
				Destination destination =  destinationList.get(i);
				desAndViewJson.setName(destination.getDestinationName());
				desAndViewJson.setId(destinationList.get(i).getCode());
				
				//获取景点对象
				List<View> views  = viewServiceImpl.findViewByDestinationCode(destinationList.get(i).getCode());
				List<ViewJson> viewJsons =  new ArrayList<ViewJson>();
				for (View view : views) {
					ViewJson json  = new ViewJson();
					for(StrategyView s:realListViews){
						if(view.getCode().equals(s.getViewCode())){
							json.setChecked(true);
						}else{
							json.setChecked(false);
						}
						viewCodes=s.getViewCode()+",";
					}
					json.setName(view.getViewName());
					json.setId(view.getCode());
					viewJsons.add(json);
				}
				desAndViewJson.setItems(viewJsons);
				andViewJsons.add(desAndViewJson);
			 }
			 //list 目的地和景点 json
			 String list  = JSONArray.fromObject(andViewJsons).toString();
			 //System.out.println(list);
			 model.addAttribute("list", list);
		}
		 if(viewCodes.endsWith(",")){
			 viewCodes=viewCodes.substring(0,viewCodes.lastIndexOf(","));
		 }
		 if(destCodes.endsWith(",")){
			 destCodes=destCodes.substring(0,destCodes.lastIndexOf(","));
		 }
		 Content content=contentService.findByCode(code);
		 if(content!=null){
			 if(content.getProgramaCode()!=null){
				Programa p= programaServiceImpl.getProgramaByCode(content.getProgramaCode());
				if(p!=null){
					if(p.getProgramaName()!=null)
				   model.addAttribute("proName", p.getProgramaName());
				}
			 }
		 }
		  model.addAttribute("content", content);
		  model.addAttribute("code", code);
		  model.addAttribute("viewCodes", viewCodes);
		  model.addAttribute("destCodes", destCodes);
		  model.addAttribute("programsList", programsList);
		 String sourceHref  =  request.getParameter("sourceHref");
		 model.addAttribute("sourceHref", sourceHref);
		return "portal//app//tourism//touristnotesEdit";
	}
	
	/**
	 * 游西藏审核通过攻略管理列表保存
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 */
	@RequestMapping("update")
	@ResponseBody
	public String   update(String code,String
			content,String contentTitle,String programaCode,Integer isOfficial,String authorCode
			,String []areas,String[]spots,HttpServletRequest request,HttpServletResponse response,ModelMap modelMap){
				LogUser logUser =  super.getFrontUser();
				if (logUser==null) {
					return "redirect:/portal/clientLog/loginPage";
				}
	   Content c=contentService.findByCode(code);
		   if(c!=null){
			content =  KeyWordFilter.getInstance().getReplaceStrTxtKeyWords(content, "**")  ; 
			c.setContent(content);
			String  c2  = contentTitle;
		       c2 =  KeyWordFilter.getInstance().getReplaceStrTxtKeyWords(c2, "**")  ; 
			c.setContentTitle(c2);
			c.setState(0);
			c.setProgramaCode(programaCode);
			contentServiceImpl.updateStrategy(c, spots, areas, request.getContextPath());
	   }
		String sourceHref  = request.getParameter("sourceHref");
		if (!StringUtils.isNotBlank(sourceHref)) {
			 sourceHref = "/" ;
		}
		return "";
		
	}
}
