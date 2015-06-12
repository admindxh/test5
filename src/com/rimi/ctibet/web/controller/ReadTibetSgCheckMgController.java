package com.rimi.ctibet.web.controller;


import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONSerializer;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.rimi.ctibet.common.constants.Constants;
import com.rimi.ctibet.common.controller.BaseController;
import com.rimi.ctibet.common.util.CodeFactory;
import com.rimi.ctibet.common.util.LuceneUtil;
import com.rimi.ctibet.common.util.Pager;
import com.rimi.ctibet.common.util.Uuid;
import com.rimi.ctibet.domain.Content;
import com.rimi.ctibet.domain.Destination;
import com.rimi.ctibet.domain.LogUser;
import com.rimi.ctibet.domain.Praise;
import com.rimi.ctibet.domain.Programa;
import com.rimi.ctibet.domain.ScoreManager;
import com.rimi.ctibet.domain.StrategyView;
import com.rimi.ctibet.domain.SysMessage;
import com.rimi.ctibet.domain.View;
import com.rimi.ctibet.web.controller.vo.DesAndViewVo;
import com.rimi.ctibet.web.controller.vo.MemberVO;
import com.rimi.ctibet.web.controller.vo.ViewUtil;
import com.rimi.ctibet.web.service.DestinationService;
import com.rimi.ctibet.web.service.IContentService;
import com.rimi.ctibet.web.service.IMemberService;
import com.rimi.ctibet.web.service.IPraiseService;
import com.rimi.ctibet.web.service.IProgramaContentService;
import com.rimi.ctibet.web.service.IProgramaService;
import com.rimi.ctibet.web.service.IScoreManagerService;
import com.rimi.ctibet.web.service.IStrategyViewService;
import com.rimi.ctibet.web.service.ISysMessageService;
import com.rimi.ctibet.web.service.IViewService;

/**
 *  游西藏攻略待审核通过管理列表 controller
 * @author dengxh
 *
 */
@Controller
@RequestMapping("web/readTibetSgCheckMgController")
public class ReadTibetSgCheckMgController extends BaseController{
	

	@Resource
	private IContentService contentServiceImpl;
	
	@Resource
	private  IProgramaContentService programaContentServiceIml;
	
	@Resource
	private IProgramaService programaServiceImpl;
	
	@Resource
	private DestinationService destinationServiceImpl;
	
	@Resource
	private  IViewService viewServiceImpl;
	
	
	@Resource
	private IPraiseService praiseService;
	
	
	@Resource
	private IStrategyViewService strategyViewService;
	
	
	@Resource
	private IScoreManagerService scoreManagerService;
	
	@Resource
	private ISysMessageService sysMessageService;
	@Resource
	private  IMemberService  memberService;  
	
	/**
	 * 通过栏目code 查询游西藏审核通过攻略管理列表栏目数据
	 * 通过 审核状态、版块、目的地、景点、官方标记、[会员名、手机、邮箱、攻略标题(keyWord)]、攻略有效性 查询攻略列表
	 * @param state	审核状态
	 * @param proCode	板块
	 * @param destinationCode 目的地
	 * @param viewCode	景点
	 * @param isOfficial	官方标记
	 * @param keyWord 关键字[会员名、手机、邮箱、攻略标题]
	 * @param avaliable	有效性
	 * @return
	 */
	@RequestMapping("list")
	public String   dynamicDataListUI(Integer state,String proCode,String  destinationCode,String  viewCode,
			Integer isOfficial,String keyWord,Integer avaliable,Pager pager,HttpServletRequest request,HttpServletResponse response,ModelMap modelMap){
		//查询条件封装  板块栏目   code  098bf91c-792e-11e4-b6ce-005056a05bc9
		List<Programa> programsList  = programaServiceImpl.getSendPrograma("098bf91c-792e-11e4-b6ce-005056a05bc9");
		//目的地维护
		 List<Destination> destinationList  = destinationServiceImpl.getAllDestination();
		//景点查询
		List<View>  viewList  = this.getListViewByDescode(destinationCode);
		
		String menu  = request.getParameter("menu");
		String stateText = "";
		String proText =  request.getParameter("proCodeText");
		if (StringUtils.isNotBlank(menu)) {
			state  =  0;
			stateText = "未审核";
		}else{
			stateText =  Constants.getTextByCode(state);
		}
		if (!StringUtils.isNotBlank(proText)) {
			 proText = "所属板块"; 
		}
		modelMap.addAttribute("keyWord", keyWord);
		modelMap.addAttribute("proText",proText);
		modelMap.addAttribute("proCode",proCode);
		modelMap.addAttribute("state", state);
		modelMap.addAttribute("stateText", stateText);
		
		
		//String viewCode = request.getParameter("viewCode"); 				
		//调用查询方法  获取分页对象
		pager.setPageSize(10);
		//String proCode  = request.getParameter("proCode");
		Map<String,Object>   searchMap = this.getMap(state, proCode, destinationCode, viewCode, isOfficial, keyWord, 1);
		pager.setPageSize(20);
		pager = contentServiceImpl.searchStrategyByContentMember(pager, searchMap,"");
		modelMap.addAttribute("pager", pager);
		modelMap.addAttribute("destinationList",destinationList);
		modelMap.addAttribute("viewList",viewList);
		modelMap.addAttribute("programsList",programsList);
		return   "manage//html//travel//audit-nopass";
	}
	/**
	 * 根据目的地查询景点
	 * @param destinationCode
	 * @return
	 */
	public List<View> getListViewByDescode(String destinationCode){
		return  viewServiceImpl.findViewByDestinationCode(destinationCode);
	} 
	  
	
	/**
	 * ajax  获取 景点 json 数据
	 * @param destinationCode
	 * @param request
	 * @param response
	 * @param modelMap
	 */
	@RequestMapping(value="getViewByDescode")
	public void  ajaxGetViewByDescode(String destinationCode, HttpServletRequest request,HttpServletResponse response,ModelMap modelMap){
		List<View>  viewList  = this.getListViewByDescode(destinationCode);
		//json lib 转化 对象
		List<ViewUtil> viewUtilList  = new ArrayList<ViewUtil>();
		for (View view : viewList) {
			ViewUtil viewUtil   = new ViewUtil();
			viewUtil.setCode(view.getCode());
			viewUtil.setViewName(view.getViewName());
			viewUtilList.add(viewUtil);
		}
		String  viewsJsonListString  = JSONSerializer.toJSON(viewUtilList).toString();	
		super.writerJson(viewsJsonListString);
	}
	/**
	 * 查询条件Map的封装
	 * @param state
	 * @param proCode
	 * @param destinationCode
	 * @param viewCode
	 * @param isOfficial
	 * @param keyWord
	 * @param avaliable
	 * @return
	 */
	public Map<String,Object> getMap(Integer state,String proCode,String  destinationCode,  String  viewCode,
			Integer isOfficial,String keyWord,Integer avaliable){
		Map<String,Object> searchMap  =  new HashMap<String, Object>();	
		if (state!=null) {
				 searchMap.put("state", state);
			}
		if (proCode!=null&&!proCode.equals("")) {
			 searchMap.put("proCode", proCode);
		}
		if (destinationCode!=null&&!destinationCode.equals("")) {
			 searchMap.put("destinationCode", destinationCode);
		}
		if (viewCode!=null&&!viewCode.equals("")) {
			 searchMap.put("viewCode", viewCode);
		}
		if (isOfficial!=null) {
			 searchMap.put("isOfficial", isOfficial);
		}
		
		if (keyWord!=null&&!keyWord.equals("")) {
			searchMap.put("keyWord", keyWord);
		}
		if (avaliable!=null) {
			 searchMap.put("avaliable", avaliable);
		}
		if (searchMap.size()<=0) {
			searchMap =  null;
		}
		return searchMap;
	}
	/**
	 * 游西藏审核通过攻略管理列表添加页面
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 */
	@RequestMapping("saveUI")
	public String   dynamicDataAddUI(HttpServletRequest request,HttpServletResponse response,ModelMap modelMap){
		String proCode  = request.getParameter("proCode");
		//查询条件封装  板块栏目   code  098bf91c-792e-11e4-b6ce-005056a05bc9
		List<Programa> programsList  = programaServiceImpl.getSendPrograma("098bf91c-792e-11e4-b6ce-005056a05bc9");
			
		modelMap.addAttribute("programsList", programsList	);//前台页面保存一个proCode栏目code
		//目的地维护
		 List<Destination> destinationList  = destinationServiceImpl.getAllDestination();
		 	
		 List<DesAndViewVo>  desandviewList  = new ArrayList<DesAndViewVo>();
		 for (Destination destination : destinationList) {
			List<View>  viewList  = this.getListViewByDescode(destination.getCode());
			DesAndViewVo desAndViewVo  = new DesAndViewVo();
		    desAndViewVo.setDesName(destination.getDestinationName());
		    List<String[]>  vieString  = new ArrayList<String[]>();
			   for (View view : viewList) {
				   String [] viewInfo  =  new String[2];
				   viewInfo[0] =  view.getViewName();
				   viewInfo[1] =  view.getCode();
				   vieString.add(viewInfo);
			   }
			   desAndViewVo.setViews(vieString);
			  desandviewList.add(desAndViewVo);
		 }
		 //景点查询
		
		 
		 modelMap.addAttribute("destinationList", destinationList);//目的地集合
		 modelMap.addAttribute("desandviewList", desandviewList);//景点集合
		  modelMap.addAttribute("proCode", proCode);//前台页面保存一个proCode栏目code
		return "manage//html//travel//audit-pass-creat";
	}
	
	/**
	 * 游西藏审核通过攻略管理列表保存
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 */
	@RequestMapping("save")
	public String   dynamicDataSave(Pager pager,Integer falseViewcount,Integer falseFavoriteNum, Integer  falsePraise,String
			content,String contentTitle,String programaCode,Integer isOfficial,String authorCode,String []vies,String[]dest,HttpServletRequest request,HttpServletResponse response,ModelMap modelMap){
		String proCode  = request.getParameter("proCode");
		Content c  =  new Content();
		c.setContentType(Content.CONTENTTYPE_STRATEGY);//内容内型
		c.setState(Constants.STATUS_CHECK);
		c.setContent(content);
		c.setContentTitle(contentTitle);
		c.setAuthorCode(authorCode);
		c.setProgramaCode(programaCode);
		c.setIsOfficial(isOfficial);
		String code  = CodeFactory.create("glgl");
	    c.setCode(code);
		contentServiceImpl.saveStrategy(c, proCode, vies, dest);
	    //保存赞
		Praise praiseSave  = new Praise();
		praiseSave.setContentCode(code);
		//赞
		praiseSave.setTruePraise(0);
		praiseSave.setFalsePraise(falsePraise);
		//查看
	    praiseSave.setFavoriteNum(0);
		praiseSave.setFalseFavoriteNum(falseFavoriteNum);

		//浏览
		
		praiseSave.setViewCount(0);
		praiseSave.setFalseViewcount(falseViewcount);
		
	    praiseService.savePraise(praiseSave);
	    
		//保存成功后调用添加lucene 等待xiaozhen提供
		LuceneUtil.addDocuemnt(content,code);//url 待提供路径
		request.setAttribute("proCode", proCode);
		return    this.dynamicDataListUI(Constants.STATUS_PASS, null, null, null, null, null, 1, pager, request, response, modelMap) ;
	}
	
	
	
	/**
	 * 游西藏审核通过攻略管理列表修改页面
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 */
	@RequestMapping("updateUI")
	public String   dynamicDataUpdateUI(String contentCode,HttpServletRequest request,HttpServletResponse response,ModelMap modelMap){
		//查询条件封装  板块栏目   code  098bf91c-792e-11e4-b6ce-005056a05bc9
		//查询内容
		  Content content   = contentServiceImpl.findByCode(contentCode);
		  Programa programa = programaServiceImpl.getProgramaByCode(content.getProgramaCode());
		  if (programa!=null) {
			content.setProgramaCode(programa.getProgramaName());
		  }
		  //会员对象
		  MemberVO   user  =  memberService.getMemberByMemberCode(content.getCreateuserCode());
		  //查询赞
		  modelMap.addAttribute("user", user);
		  //查询关联的目的地 code
		  List<StrategyView> realList  =  strategyViewService.findDestinationCodeByContentCode(content.getCode());
		  
		  List<String>  desName  = new ArrayList<String>();
		  for (StrategyView sv : realList) {
			  Destination  destination =  this.destinationServiceImpl.getDestinationByCode(sv.getDestinationCode());
			  if (destination!=null) {
				  desName.add(destination.getDestinationName());
				
			}
		  }
		  
		  //实际的景点
		  
		  List<StrategyView> realListViews  =      strategyViewService.findViewCodeByContentCode(content.getCode());
		  
		  List<String>  viewName  = new ArrayList<String>();
		  for (StrategyView sv : realListViews) {
			  View  destination =  this.viewServiceImpl.findByCode(sv.getViewCode());
			  viewName.add(destination.getViewName());
		  }
		  
		 
		 
		 
		  modelMap.addAttribute("viewName",viewName );//景点集合
		  modelMap.addAttribute("desName",desName );//景点集合
		
		  request.setAttribute("content", content);
		
		return "manage//html//travel//audit-nopass-unofficial";
	}
	
	/**
	 * 游西藏审核通过攻略管理列表修改
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 */
	@RequestMapping("update")
	public String   dynamicDataUpdate(Pager pager,Integer state,String praisecode,String code,Integer falseViewcount,Integer falseFavoriteNum, Integer  falsePraise,String
			content,String contentTitle,String programaCode,Integer isOfficial,String authorCode,String []vies,String[]dest,HttpServletRequest request,HttpServletResponse response,ModelMap modelMap){
		String proCode  = request.getParameter("proCode");
		Content c  =   contentServiceImpl.findByCode(code);
		c.setContent(content);
		c.setState(state);
		int score=0;
		if (state!=null&&state==1) {
			int sco=scoreManagerService.updateMemberScoreByMemberCode(c.getCreateuserCode(),ScoreManager.type_savestrage);
			score=sco;
		}
		
		//提醒用户
		SysMessage sm=new SysMessage();
		sm.setAvaliable(1);
		sm.setCode(CodeFactory.create("sysMsg"));
		if(state==1){
		  sm.setContent("你的攻略审核已通过");
		  sm.setType(Constants.Stra_Judge_Ok);
		  sm.setScore(score);
		}
		if(state==-1){
	      sm.setContent("你的攻略审核未通过");
	      sm.setType(Constants.Stra_Judge_Back);
		}
		sm.setContentCode(c.getCode());
		sm.setContentTitle(c.getContentTitle());
		sm.setCreateDate(new Date());
		sm.setMsgTo(c.getCreateuserCode());
		sm.setTitle("攻略审核提醒");
		
		sm.setUrl(c.getUrl());
		sysMessageService.save(sm);
		contentServiceImpl.update(c);
		
		try {
		    //保存攻略图片
            contentServiceImpl.saveAtlas(request.getContextPath(), c);
        } catch (Exception e) {
            e.printStackTrace();
        }
		
		return    this.dynamicDataListUI(null, null, null, null, null, null, 1, pager, request, response, modelMap) ;
	}
	
	
	/**
	 * 删除内容
	 * @param pager
	 * @param contentCode  分割 ,
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 */
	@RequestMapping("delete")
	public String dynamicDataDelete(Pager pager,String contentCode,HttpServletRequest request,HttpServletResponse response,ModelMap modelMap){
		String [] contentcodes   =  null;
		if (contentCode!=null) {
			contentcodes  = contentCode.split(",");
		}
		contentServiceImpl.deleteStrategyByCode(contentcodes);
		//批量删除内容 和 中间表
		String proCode  = request.getParameter("proCode");
		//删除lucene,定义成 ,分割 contentCode 
		LuceneUtil.deleteDocument(contentCode);
		request.setAttribute("proCode", proCode);
		return    this.dynamicDataListUI(null, null, null, null, null, null, 1, pager, request, response, modelMap) ;
	}
	
	/**
	 * 取消首页显示  根据 内容code
	 * @param pager
	 * @param contentCode  内容code 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 */
	@RequestMapping("cancel")
	public String dynamicDataCancel(Pager pager,String contentCode,HttpServletRequest request,HttpServletResponse response,ModelMap modelMap){
		String proCode  = request.getParameter("proCode");
		//String 
		programaContentServiceIml.deleteProgramaContentByProCodeConCode(proCode, contentCode);
		request.setAttribute("proCode", proCode);
		return    this.dynamicDataListUI(null, null, null, null, null, null, null, pager, request, response, modelMap) ;
	}
	
	
	
	
	
	public IProgramaService getProgramaServiceImpl() {
		return programaServiceImpl;
	}
	public void setProgramaServiceImpl(IProgramaService programaServiceImpl) {
		this.programaServiceImpl = programaServiceImpl;
	}
	public DestinationService getDestinationServiceImpl() {
		return destinationServiceImpl;
	}
	public void setDestinationServiceImpl(DestinationService destinationServiceImpl) {
		this.destinationServiceImpl = destinationServiceImpl;
	}
	public IViewService getViewServiceImpl() {
		return viewServiceImpl;
	}
	public void setViewServiceImpl(IViewService viewServiceImpl) {
		this.viewServiceImpl = viewServiceImpl;
	}
	public IPraiseService getPraiseService() {
		return praiseService;
	}
	public void setPraiseService(IPraiseService praiseService) {
		this.praiseService = praiseService;
	}
	public IProgramaContentService getProgramaContentServiceIml() {
		return programaContentServiceIml;
	}
	public void setProgramaContentServiceIml(
			IProgramaContentService programaContentServiceIml) {
		this.programaContentServiceIml = programaContentServiceIml;
	}
	public IContentService getContentServiceImpl() {
		return contentServiceImpl;
	}

	public void setContentServiceImpl(IContentService contentServiceImpl) {
		this.contentServiceImpl = contentServiceImpl;
	}
	public IStrategyViewService getStrategyViewService() {
		return strategyViewService;
	}
	public void setStrategyViewService(IStrategyViewService strategyViewService) {
		this.strategyViewService = strategyViewService;
	}
	
	
	
}
