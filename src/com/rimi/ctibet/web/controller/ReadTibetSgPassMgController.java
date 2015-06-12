package com.rimi.ctibet.web.controller;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
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
import com.rimi.ctibet.domain.Member;
import com.rimi.ctibet.domain.MemberInfo;
import com.rimi.ctibet.domain.Praise;
import com.rimi.ctibet.domain.Programa;
import com.rimi.ctibet.domain.StrategyView;
import com.rimi.ctibet.domain.SysMessage;
import com.rimi.ctibet.domain.View;
import com.rimi.ctibet.web.controller.vo.DesAndViewVo;
import com.rimi.ctibet.web.controller.vo.DesticationVo;
import com.rimi.ctibet.web.controller.vo.ViewUtil;
import com.rimi.ctibet.web.service.DestinationService;
import com.rimi.ctibet.web.service.IContentService;
import com.rimi.ctibet.web.service.IMemberService;
import com.rimi.ctibet.web.service.IPraiseService;
import com.rimi.ctibet.web.service.IProgramaContentService;
import com.rimi.ctibet.web.service.IProgramaService;
import com.rimi.ctibet.web.service.IStrategyViewService;
import com.rimi.ctibet.web.service.ISysMessageService;
import com.rimi.ctibet.web.service.IViewService;

/**
 *  游西藏攻略审核通过管理列表 controller
 * @author dengxh
 *
 */
@Controller
@RequestMapping("web/readTibetSgPassMgController")
public class ReadTibetSgPassMgController extends BaseController{
	

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
	private  IMemberService  memberService;  
	
	@Resource
	private ISysMessageService sysMessageService;
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
		
		//String viewCode = request.getParameter("viewCode"); 				
		//调用查询方法  获取分页对象
		pager.setPageSize(10);
		String menu  = request.getParameter("menu");
		String proText = request.getParameter("proCodeText");
		String isOfficialText = request.getParameter("isOfficialText");
		String destinationCodeText = request.getParameter("destinationCodeText");
		String viewCodeText = request.getParameter("viewCodeText");
		String order  = request.getParameter("order");
		
		String orderText  = request.getParameter("orderText");
		
		if (!StringUtils.isNotBlank(proText)) {
			 proText = "所属板块"; 
		}
		if (!StringUtils.isNotBlank(isOfficialText)) {
			isOfficialText = "攻略类型"; 
		}
		if (!StringUtils.isNotBlank(destinationCodeText)) {
			destinationCodeText = "相关目的地"; 
		}
		if (!StringUtils.isNotBlank(viewCodeText)) {
			viewCodeText = "全部景点"; 
		}
		if (!StringUtils.isNotBlank(orderText)) {
			orderText = "快捷筛选"; 
		}
		modelMap.addAttribute("order", order);
		modelMap.addAttribute("orderText", orderText);
		
		
		modelMap.addAttribute("isOfficialText",isOfficialText );
		modelMap.addAttribute("isOfficial",isOfficial );
		modelMap.addAttribute("keyWord", keyWord);
		modelMap.addAttribute("proText",proText);
		modelMap.addAttribute("proCode",proCode);
		
		modelMap.addAttribute("destinationCode",destinationCode);
		modelMap.addAttribute("destinationCodeText",destinationCodeText);
		
		modelMap.addAttribute("viewCodeText",viewCodeText);
		modelMap.addAttribute("viewCode",viewCode);
		
		//String proCode  = request.getParameter("proCode");

        String res = request.getParameter("res");
        Map<String, Object> searchMap ;
        if ("0".equals(res)) {
            searchMap = this.getMap(Constants.STATUS_PASS, null, null, null, null, null, 1);
        } else {
            searchMap = this.getMap(Constants.STATUS_PASS, proCode, destinationCode, viewCode, isOfficial, keyWord, 1);
        }
		pager.setPageSize(20);
		pager = contentServiceImpl.searchStrategyByContentMember(pager, searchMap,order);
		modelMap.addAttribute("pager", pager);
		modelMap.addAttribute("destinationList",destinationList);
		modelMap.addAttribute("viewList",viewList);
		modelMap.addAttribute("programsList",programsList);
		return   "manage//html//travel//audit-pass";
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
		return "manage/html/travel/audit-pass-creat";
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
		
		String t = request.getParameter("tdkTitle");
		String d = request.getParameter("tdkDescription");
		String k = request.getParameter("tdkKeywords");
		c.setTdkTitle(t);
		c.setTdkDescription(d);
		c.setTdkKeywords(k);
		
		c.setContentType(Content.CONTENTTYPE_STRATEGY);//内容内型
		//后台默认审核通过
		//c.setState(Constants.Status_CHECK);
		c.setState(Constants.STATUS_PASS);
		c.setContent(content);
		c.setContentTitle(contentTitle);
		//isofficial  == 1 表示官方上传，默认用户为  天上西藏官方
		if (isOfficial!=null&&isOfficial==1) {
			authorCode = "天上西藏官方";
		}
		c.setAuthorCode(authorCode);
	
		c.setProgramaCode(programaCode);
		c.setIsOfficial(isOfficial);
		//获取当前用户
		if (this.getManagerUser()!=null) {
			//c.setCreateuserCode(this.getManagerUser().getCode());
		}
		Member member =  new Member();
		
		if (authorCode!=null&&!authorCode.equals("")) {
			MemberInfo memberInfo   =  new MemberInfo();
			
			memberInfo.setName(authorCode);
			
			
			member = memberService.saveByName(memberInfo);
			c.setCreateuserCode(member.getCode());
		}
		
		
		String code  = CodeFactory.create("yxzgl");
		String url  = this.getUrlHtml("/tourism/strage/detail", code);
	    c.setCode(code);
	    c.setUrl(url);
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
		
		try {
            //保存攻略图片
            contentServiceImpl.saveAtlas(request.getContextPath(), c);
        } catch (Exception e) {
            e.printStackTrace();
        }
		
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
		List<Programa> programsList  = programaServiceImpl.getSendPrograma("098bf91c-792e-11e4-b6ce-005056a05bc9");
			
		modelMap.addAttribute("programsList", programsList	);//前台页面保存一个proCode栏目code 
		
		
		//查询内容
		  Content content   = contentServiceImpl.findByCode(contentCode);
		 
		  //查询赞
		  		  
		  
		  
		  //查询关联的目的地 code
		  List<StrategyView> realList  =  strategyViewService.findDestinationCodeByContentCode(content.getCode());
		  
		  //展示的数据目的地
		  List<DesticationVo> destinationListReal =  new ArrayList<DesticationVo>();
		  
		  //目的地维护
		  List<Destination> destinationList  = destinationServiceImpl.getAllDestination();
		 
		  for (Destination destination : destinationList) {
			  DesticationVo desticationVo = new DesticationVo();
			  String ischecked  = "";
			   for (StrategyView real : realList) {
				  if (real.getDestinationCode().equals(destination.getCode())) {
					  ischecked= "checked";
					   break;
				  }
			    } 
			   desticationVo.setCode(destination.getCode());
			   desticationVo.setIschecked(ischecked);
			   desticationVo.setName(destination.getDestinationName());
			   destinationListReal.add(desticationVo);
		  }
		  
		  
		  
		  
		  
		  //实际的景点
		  
		  List<StrategyView> realListViews  =      strategyViewService.findViewCodeByContentCode(content.getCode());
		  
		  
		 //景点
		 	
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
		for (int i = 0; i < desandviewList.size(); i++) {
			DesAndViewVo v  = new DesAndViewVo();
			v.setDesName(desandviewList.get(i).getDesName());
			List<String[]>  views1    = desandviewList.get(i).getViews();
			List<String[]>  views2    = new ArrayList<String[]>();
			for (String[] strings : views1) {
				String [] s  = new String[3];
				s[0] = strings[0];
				s[1] = strings[1];
				  for (StrategyView vs : realListViews) {
					  if (strings[1].equals(vs.getViewCode())) {
						s[2] = "checked" ;
						break;
					}
				}
				views2.add(s);
			}
			v.setViews(views2);
			desandviewList.set(i, v);
			
		}  
		 
		 
		 
		 
		 Praise praise =  praiseService.getPraiseContentCode(content.getCode());
		 modelMap.addAttribute("praise",praise);
		 modelMap.addAttribute("destinationListReal", destinationListReal);//目的地集合
		 modelMap.addAttribute("desandviewList", desandviewList);//景点集合
		 modelMap.addAttribute("content",content );//景点集合
		
	
		
		
		request.setAttribute("content", content);
		
		return "manage//html//travel//audit-pass-update";
	}
	
	/**
	 * 游西藏审核通过攻略管理列表修改
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 */
	@RequestMapping("update")
	public String   dynamicDataUpdate(Pager pager,String praisecode,String code,Integer falseViewcount,Integer falseFavoriteNum, Integer  falsePraise,String
			content,String contentTitle,String programaCode,Integer isOfficial,String authorCode,String []vies,String[]dest,HttpServletRequest request,HttpServletResponse response,ModelMap modelMap){
		//System.out.println("----------mmmmmmmmmmmmmmmmm---------------");
		String  proCode  = request.getParameter("proCode");
		Content c  =  new Content();
		String t = request.getParameter("tdkTitle");
        String d = request.getParameter("tdkDescription");
        String k = request.getParameter("tdkKeywords");
        c.setTdkTitle(t);
        c.setTdkDescription(d);
        c.setTdkKeywords(k);
		c.setContentType(Content.CONTENTTYPE_STRATEGY);//内容内型
		c.setState(Constants.STATUS_PASS);
		c.setContent(content);
		c.setContentTitle(contentTitle);
		//isofficial  == 1 表示官方上传，默认用户为  天上西藏官方
		if (isOfficial!=null&&isOfficial==1) {
			authorCode = "天上西藏官方";
		}
		//c.setCreateuserCode()
		c.setAuthorCode(authorCode);
		c.setProgramaCode(programaCode);
		c.setIsOfficial(isOfficial);
	    c.setCode(code);
	    Content cr  =  contentServiceImpl.findByCode(c.getCode());
	    if (cr!=null) {
			c.setCreateuserCode(cr.getCreateuserCode());
		}
	    String url  = this.getUrlHtml("/tourism/strage/detail", code);
	    c.setUrl(url);
	  //获取当前用户
		/*if (this.getManagerUser()!=null) {
			c.setCreateuserCode(this.getManagerUser().getCode());
		}*/
	    contentServiceImpl.updateStrategy(c, vies, dest, request.getContextPath());
	   
	    if (cr!=null) {
	    	MemberInfo memberInfo  = memberService.findMemberInfo(cr.getCreateuserCode());
	    	if (memberInfo!=null) {
	    		memberInfo.setName(authorCode);
	    		memberService.updateMemberInfo(memberInfo);
	    	}
			
		}
	    //保存赞
		Praise praiseSave  = new Praise();
		praiseSave.setContentCode(code);
		praiseSave.setCode(praisecode);
		//赞
		//praiseSave.setTruePraise(0);
		
		praiseSave.setFalsePraise(falsePraise);
		//查看
	  //  praiseSave.setFavoriteNum(0);
		praiseSave.setFalseFavoriteNum(falseFavoriteNum);

		//浏览
		
		//praiseSave.setViewCount(0);
		praiseSave.setFalseViewcount(falseViewcount);
		praiseSave.setCode(praisecode);
	    praiseService.updatePraise(praiseSave);
	    try {
            //保存攻略图片
	    	contentServiceImpl.updateLogical(c);
            //contentServiceImpl.updateLogical(c);
        } catch (Exception e) {
            e.printStackTrace();
        }
		//保存成功后调用添加lucene 等待xiaozhen提供
		LuceneUtil.addDocuemnt(content,code);//url 待提供路径
		request.setAttribute("proCode", proCode);
		return    this.dynamicDataListUI(Constants.STATUS_PASS, null, null, null, null, null, 1, pager, request, response, modelMap) ;
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
		if(contentcodes!=null){
			for(int i=0;i<contentcodes.length;i++){
				Content c=contentServiceImpl.findByCode(contentcodes[i]);
				//提醒用户
				SysMessage sm=new SysMessage();
				sm.setAvaliable(1);
				sm.setCode(Uuid.uuid());
				
				sm.setContent("你的攻略被管理员删除");
				if(c!=null){
					if(c.getCode()!=null)
					sm.setContentCode(c.getCode());
					if(c.getContentTitle()!=null)
					sm.setContentTitle(c.getContentTitle());
					if(c.getCreateuserCode()!=null)
					sm.setMsgTo(c.getCreateuserCode());
					if(c.getContentType()!=null)
					sm.setType(Constants.Stra_Delete);
					if(c.getUrl()!=null)
					sm.setUrl(c.getUrl());
				}
				//删除攻略的评论
				contentServiceImpl.deleteReplyByPostCodeLogical(contentcodes[i], Content.DETAIL_STRATEGY_REPLY);
				sm.setCreateDate(new Date());
				sm.setTitle("攻略删除提醒");
				sysMessageService.save(sm);
			}
		}
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
