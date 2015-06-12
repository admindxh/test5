package com.rimi.ctibet.web.controller;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.rimi.ctibet.common.constants.Constants;
import com.rimi.ctibet.common.controller.BaseController;
import com.rimi.ctibet.common.util.CodeFactory;
import com.rimi.ctibet.common.util.HtmlRegexpUtil;
import com.rimi.ctibet.common.util.LuceneUtil2;
import com.rimi.ctibet.common.util.Pager;
import com.rimi.ctibet.common.util.StringUtil;
import com.rimi.ctibet.common.util.Uuid;
import com.rimi.ctibet.common.util.WebSearch;
import com.rimi.ctibet.domain.Content;
import com.rimi.ctibet.domain.LogUser;
import com.rimi.ctibet.domain.Member;
import com.rimi.ctibet.domain.MemberInfo;
import com.rimi.ctibet.domain.Praise;
import com.rimi.ctibet.domain.Programa;
import com.rimi.ctibet.domain.ScoreManager;
import com.rimi.ctibet.domain.SysMessage;
import com.rimi.ctibet.web.controller.vo.PostPassVo;
import com.rimi.ctibet.web.dao.IPraiseDao;
import com.rimi.ctibet.web.dao.IProgramaDao;
import com.rimi.ctibet.web.service.IContentService;
import com.rimi.ctibet.web.service.IMemberService;
import com.rimi.ctibet.web.service.IMerchantService;
import com.rimi.ctibet.web.service.IPlateService;
import com.rimi.ctibet.web.service.IPostService;
import com.rimi.ctibet.web.service.IPraiseService;
import com.rimi.ctibet.web.service.IProgramaService;
import com.rimi.ctibet.web.service.IScoreManagerService;
import com.rimi.ctibet.web.service.ISysMessageService;


/**
 * 审核通过的帖子
 * @author dengxh
 *
 */
@Controller
@RequestMapping("web/passPostController")
public class PassPostController extends BaseController{

	@Resource
	private IPostService postService;
	@Resource
	private IPlateService plateService;
	@Resource
	private IPraiseService  praiseService;
	@Resource
	private IContentService contentService;
	
	@Resource
	private IProgramaService programaService;
	
	@Resource
	private  IMemberService  memberService;  
	
	public static Content contentPriew;//预览帖子对象
	public static Praise praisePriew;//预览赞
	@Resource
	private IScoreManagerService scoreManagerService;
	@Resource
	private ISysMessageService sysMessageService;
	
	public static Programa prePlate ;
	
	/**
	 * 删除帖子
	 * @param request
	 * @param response
	 * @return
	 */
	
	@RequestMapping("deletePost")
	public  ModelAndView deletePost(Pager pager,HttpServletRequest request,HttpServletResponse response){
		//System.out.println("PostController.deletePost()");
		String code = request.getParameter("contentCode");
		String[] codes = code.split(",");
		for(int i=0;i<codes.length;i++)
		{
			//System.out.println("--------------------------------------------------"+codes[i]);
			Content post = postService.getPostByCode(codes[i]);
			//System.out.println(post);
			postService.deletePost(post);
			//lucene 删除
			WebSearch webSearch = new WebSearch();
			webSearch.setCode(code);
			LuceneUtil2.delete(webSearch);
		}
		return   this.searchPost(pager, request, response);
	}
	
	@RequestMapping("passcheckedPostList")
	public ModelAndView uncheckedPostList(String programcode,String keyword,String order,Pager pager,HttpServletRequest request,HttpServletResponse response){
		//System.out.println("PostController.UncheckedPostList()");
		ModelAndView view = new ModelAndView("//manage//html//bbs//invitation-audit-already");
		Pager obj= postService.findAllPassPost(1, programcode, keyword, order, pager);
		List<Programa> programaList = plateService.getTopPlate();
		view.addObject("plates",programaList);
		view.addObject("obj",obj);
		return view;
	}

	/**
	 * 查询帖子
	 * @param pager
	 * @param request
	 * @param response
	 * @return
	 */
	@SuppressWarnings({ "static-access", "unchecked" })
	@RequestMapping("searchPost")
	public ModelAndView searchPost( 
			Pager pager,HttpServletRequest request,HttpServletResponse response){
		List<Programa> programaList = plateService.getTopPlate();
		ModelAndView view = new ModelAndView("manage//html//bbs//invitation-audit-already");
//int state = Integer.valueOf(request.getParameter("state"));
		Integer state = 1;
		String programaCode = request.getParameter("programcode");
		//System.out.println("-----------------------------------------------"+programaCode);
		String keyword = request.getParameter("keyword");
		String  order  = request.getParameter("order");
		pager.setPageSize(20);
		Pager result = postService.findAllPassPost(1, programaCode, keyword, order, pager);
		List<PostPassVo> list  = result.getDataList();
		HtmlRegexpUtil  htmlRegexpUtil = new HtmlRegexpUtil();
		if (list!=null) {
				for (PostPassVo postPassVo : list) {
					postPassVo.setContent(htmlRegexpUtil.filterHtml(postPassVo.getContent()));
				}
		}
		
		view.addObject("plates", programaList);
		view.addObject("pager", result);
		String programcodeText =  request.getParameter("programcodeText");
		if (!StringUtils.isNotBlank(programcodeText)) {
			  programcodeText = "按版块筛选";
		}
		
		if (!StringUtils.isNotBlank(keyword)) {
			keyword  = "标题,内容";
			view.addObject("color", "color");
		}
		String orderText = request.getParameter("orderText");
		if (!StringUtils.isNotBlank(orderText)) {
			orderText  ="快捷筛选";
		}
		view.addObject("keyword", keyword);
		view.addObject("programcodeText", programcodeText);
		view.addObject("programcode",programaCode);
		view.addObject("orderText", orderText);
		view.addObject("order",order);
		return view;
	}
	/**
	 * 回复赞，获取真实值
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("restorePraise")
	@ResponseBody
	public String  restorePraise(String contentCode,HttpServletRequest request,HttpServletResponse response){
		String cCode  = contentCode;
		Praise p =  praiseService.getPraiseContentCode(cCode);
		if (p!=null) {
			p.setFalsePraise(p.getTruePraise());
			praiseService.update(p);
			//String jsonPraise  = JSONObject.fromObject(p).toString();
			return String.valueOf(p.getFalsePraise());
		}
		return  "";
	}
	
	/**
	 * 回复赞，获取真实值
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("savePraise")
	@ResponseBody
	public String  savePraise(Integer praiseCount,String contentCode,HttpServletRequest request,HttpServletResponse response){
		String cCode  = contentCode;
		Praise p =  praiseService.getPraiseContentCode(cCode);
		if (p!=null) {
			p.setFalsePraise(praiseCount);
			praiseService.update(p);
		}
		return  "保存成功!";
	}
	
	
	/**
	 * 回复浏览数，获取真实值
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("restoreView")
	@ResponseBody
	public String  restoreView(String contentCode,HttpServletRequest request,HttpServletResponse response){
		String cCode  = contentCode;
		Praise p =  praiseService.getPraiseContentCode(cCode);
		if (p!=null) {
			p.setFalseViewcount(p.getViewCount());
			praiseService.update(p);
			//String jsonView  = JSONObject.fromObject(p).toString();
			return String.valueOf(p.getFalseViewcount());
		}
		return  "";
	}
	/**
	 * 置顶
	 * @param request
	 * @param response
	 * @param pager
	 * @return
	 */
	@RequestMapping(value="retoreStick")
	public ModelAndView  retoreStick(String contentCode,Integer  isTop,HttpServletRequest request,HttpServletResponse response,Pager pager){
		Integer top  = isTop==null?0:isTop;
	   Content ct =	contentService.findByCode(contentCode);
	   ct.setIsTop(top);
	   contentService.update(ct);
		
		if (isTop!=null&&isTop==1) {
			int score=scoreManagerService.updateMemberScoreByMemberCode(ct.getCreateuserCode(),ScoreManager.type_posttop);
			if (ct!=null) {
				SysMessage sm = new SysMessage();
	    		sm.setAvaliable(1);
	    		sm.setCode(CodeFactory.create("sysMsg"));
	    		sm.setContent("您有一个帖子被置顶");
	    		sm.setContentCode(ct.getCode());
	    		sm.setMsgTo(ct.getCreateuserCode());
	    		sm.setTitle("帖子置顶提醒");
	    		sm.setUrl(ct.getUrl());
	    		sm.setType(Constants.Post_Top);
	    		sm.setCreateDate(new Date());
	    		sm.setScore(score);
	    		sm.setContentTitle(ct.getContentTitle());
	           
	            sysMessageService.save(sm);
			}
		}
		return this.searchPost(pager, request, response);
	}
	
	/**
	 * 保存 赞，浏览量信息修改的假数据
	 * @param contentCode 内容code
	 * @param praiseCount 假赞数量
	 * @param viewCount   假浏览数量 
	 * @param request 
	 * @param request
	 * @param response
	 * @param pager
	 * @return
	 */
	@RequestMapping(value="saveContentInfo")
	public ModelAndView   saveContentInfo(String contentCode,Integer praiseCount,Integer viewCount,HttpServletRequest request,HttpServletResponse response,Pager pager){
		Praise p =  praiseService.getPraiseContentCode(contentCode);
		
		if (p!=null) {
			p.setFalsePraise(praiseCount);
			p.setFalseViewcount(viewCount);
			praiseService.update(p);
		}else{
			p = new Praise();
			p.setFalsePraise(praiseCount);
			p.setTruePraise(praiseCount);
			p.setFalseViewcount(viewCount);
			p.setViewCount(viewCount);
			p.setContentCode(contentCode);
			praiseService.savePraise(p);
			
		}
		return this.searchPost(pager, request, response);
	}
	
	/**
	 * 跳转到帖子保存页面
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("addUI")
	public String addUI(HttpServletRequest request,HttpServletResponse response){
		List<Programa> programaList = plateService.getTopPlate();
		request.setAttribute("plates", programaList);
		return "manage//html//bbs//issue-invitation";
	}
	
	/**
	 * 跳转到帖子保存页面
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("updateUI")
	public String updateUI(HttpServletRequest request,HttpServletResponse response){
		List<Programa> programaList = plateService.getTopPlate();
		request.setAttribute("plates", programaList);
		String  contentCode  =  request.getParameter("contentCode");
		Content c  =  contentService.findByCode(contentCode);
		Programa programa =  programaService.getProgramaByCode(c.getProgramaCode());
		Praise p  =  praiseService.getPraiseContentCode(c.getCode());
		request.setAttribute("c", c);
		request.setAttribute("programa", programa);
		request.setAttribute("p", p);
		return "manage//html//bbs//issue-invitation-update";
	}
	
	/**
	 * 发布帖子
	 * @param request
	 * @param response
	 * @param pager
	 * @return
	 */
	@RequestMapping("savePost")
	public String  savePost(String contentTitle,String content,String authorCode,Integer isTop,
			String programaCode,Integer praiseCount,Integer viewCount  ,HttpServletRequest request,HttpServletResponse response,Pager pager){
		Content  post  =   new  Content();
		String contentCode  = CodeFactory.createCode("post");
		post.setCode(contentCode);
		post.setAvaliable(1); 
		post.setContentType(Content.CONTENTTYPE_POST);
		post.setState(1);
		post.setCreateTime(new Date());
		
		String url  = this.getUrlHtml("community/detail", contentCode);
	    post.setUrl(url);

	    String t = request.getParameter("tdkTitle");
	    String d = request.getParameter("tdkDescription");
	    String k = request.getParameter("tdkKeywords");
		
	    post.setTdkTitle(t);
	    post.setTdkDescription(d);
	    post.setTdkKeywords(k);
	    
		Member member =  new Member();
		if (authorCode!=null&&!authorCode.equals("")) {
			MemberInfo memberInfo   =  new MemberInfo();
			memberInfo.setName(authorCode);
			member = memberService.saveByName(memberInfo);
		}
		//修改内容信息
		if (post!=null) {
			post.setContent(content);
			post.setContentTitle(contentTitle);
			post.setIsTop(isTop);
			post.setProgramaCode(programaCode);
			post.setAuthorCode(authorCode);
			post.setCreateuserCode(member.getCode());
		}
		
		contentService.save(post);
		
		//lucene  查询

		WebSearch webSearch = new WebSearch();
        webSearch.setCode(post.getCode());
        webSearch.setUrl(post.getUrl());
        webSearch.setImageUrl(getImgDef());
        webSearch.setTitle(post.getContentTitle());
        webSearch.setContent(StringUtil.cleanHTML(post.getContent()));
        LuceneUtil2.add(webSearch);
		
		
		
		//修改帖子赞
		Praise p =  new Praise(contentCode);
		p.setCode(Uuid.uuid());
		praiseService.save(p);
		//return   this.searchPost(pager, request, response);
		return "redirect:/web/passPostController/searchPost";
	}
	
	
	/**
	 * 修改帖子
	 * @param request
	 * @param response
	 * @param pager
	 * @return
	 */
	@RequestMapping("updatePost")
	public ModelAndView updatePost(String contentTitle,String content,String contentCode,String authorCode,Integer isTop,
			String programaCode,String createuserCode,Integer praiseCount,Integer viewCount  ,HttpServletRequest request,HttpServletResponse response,Pager pager){
		Content  c  =  contentService.findByCode(contentCode);
		String url  = this.getUrlHtml("community/detail", c.getCode());
	    c.setUrl(url);
		//修改内容信息
		if (c!=null) {
			c.setContent(content);
			c.setContentTitle(contentTitle);
			c.setIsTop(isTop);
			c.setProgramaCode(programaCode);
			c.setAuthorCode(authorCode);
			MemberInfo memberInfo  = memberService.findMemberInfo(c.getCreateuserCode());
			if (memberInfo!=null) {
				memberInfo.setName(authorCode);
				memberService.updateMemberInfo(memberInfo);
			}
			
			String t = request.getParameter("tdkTitle");
	        String d = request.getParameter("tdkDescription");
	        String k = request.getParameter("tdkKeywords");
	        
	        c.setTdkTitle(t);
	        c.setTdkDescription(d);
	        c.setTdkKeywords(k);
			
			contentService.update(c);
		}
		//修改帖子赞
		Praise p =  praiseService.getPraiseContentCode(contentCode);
		if (p!=null) {
			p.setFalsePraise(praiseCount);
			p.setFalseViewcount(viewCount);
			praiseService.update(p);
		}
		//lucene 查询
		WebSearch webSearch = new WebSearch();
        webSearch.setCode(c.getCode());
        webSearch.setUrl(c.getUrl());
        webSearch.setImageUrl(getImgDef());
        webSearch.setTitle(c.getContentTitle());
        webSearch.setContent(StringUtil.cleanHTML(c.getContent()));
        LuceneUtil2.update(webSearch);
		
		
		
		return   this.searchPost(pager, request, response);
	}
	
	/**
	 *  修改  预览数据帖子
	 * @return
	 */
	@SuppressWarnings("static-access")
	@RequestMapping("priewPost")
	public String  priewPost(String contentTitle,String content,String contentCode,String authorCode,Integer isTop,
			String programaCode,Integer praiseCount,Integer viewCount  ,HttpServletRequest request,HttpServletResponse response,Pager pager){
		if (contentCode!=null&&!contentCode.equals("")) {
			Content  c   = this.contentService.findByCode(contentCode);
			c.setContent(content);
			c.setContentTitle(contentTitle);
			c.setIsTop(isTop);
			c.setProgramaCode(programaCode);
			Programa plate = programaService.getProgramaByCode(programaCode);
			c.setAuthorCode(authorCode);
			this.prePlate = plate;
			this.contentPriew = c;//预览内容
		}else{
			Programa plate = programaService.getProgramaByCode(programaCode);
			this.prePlate = plate;
			Content  c   =  new Content();
			c.setContent(content);
			c.setContentTitle(contentTitle);
			c.setIsTop(isTop);
			c.setProgramaCode(programaCode);
			c.setAuthorCode(authorCode);
			this.contentPriew = c;//预览内容
		}
		Praise p =  praiseService.getPraiseContentCode(contentCode);
		if (p!=null) {
			p.setFalsePraise(praiseCount);
			p.setFalseViewcount(viewCount);
			this.praisePriew = p; //预览赞
		}
		return "redirect:/community/postDetail?preview=0";
	}
	
	/**
	 * 保存  预览数据帖子
	 * @return
	 */
	@SuppressWarnings("static-access")
	@RequestMapping("savepriewPost")
	public String  savepriewPost(String contentTitle,String content,String contentCode,String authorCode,Integer isTop,
			String programaCode,Integer praiseCount,Integer viewCount  ,HttpServletRequest request,HttpServletResponse response,Pager pager){
			Content  c   =  new Content();
			c.setContent(content);
			c.setContentTitle(contentTitle);
			c.setIsTop(isTop);
			c.setProgramaCode(programaCode);
			c.setAuthorCode(authorCode);
			this.contentPriew = c;//预览内容
			Praise p =   new Praise();
			p.setFalsePraise(0);
			p.setFalseViewcount(0);
			this.praisePriew = p; //预览赞
			
			return "redirect:/community/postDetail?preview=0";
	}
	@RequestMapping("managerReply")
	public String managerReply(String contentCode,HttpServletRequest request,HttpServletResponse response,Pager pager){
		//帖子对象
		Content c  = contentService.findByCode(contentCode);
		Praise praise = praiseService.getPraiseContentCode(contentCode);//查看数
		  List<LogUser> loguser  = memberService.findLogUserByCode(c.getCreateuserCode());
		  LogUser  user  =  loguser!=null&&loguser.size()>=1?loguser.get(0):null;
		  Object[] pv =  praiseService.searchPVCount(c.getCode());
		  Pager  replyPager  =   postService.getReplysInfoByPostCode(contentCode,pager);
			request.setAttribute("pv", pv);//回复数量和赞数量
			request.setAttribute("c", c);
			request.setAttribute("user", user);
			request.setAttribute("praise", praise);
			request.setAttribute("replyPager", replyPager);
		return "manage//html//bbs//reply_manage";
	}
	
	/**
	 * 删除回复
	 * @return
	 */  
	@RequestMapping(value="deleteReply")
	public String deleteReply(String postCode,String contentCode,HttpServletRequest request,HttpServletResponse response,Pager pager){
		contentService.deleteReplyByReplyCode(contentCode);
		return   this.managerReply(postCode, request, response, pager);
	}
	
	/**
	 * 回复赞，获取真实值
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("restorePraiseFalse")
	@ResponseBody
	public String  restorePraiseFalse(String contentCode,HttpServletRequest request,HttpServletResponse response){
		String cCode  = contentCode;
		Praise p =  praiseService.getPraiseContentCode(cCode);
		if (p!=null) {
			
			 praiseService.update(p);
			 if (p.getTruePraise()==null) {
				 p.setFalsePraise(0);
				 return "0";
			}
			 p.setFalsePraise(p.getTruePraise());
			//String jsonPraise  = JSONObject.fromObject(p).toString();
			return String.valueOf(p.getFalsePraise());
		}else{
			 p   = new Praise(contentCode);
			 praiseService.save(p);
			
		}
		return  "0";
	}
	/**
	 * 保存 赞，
	 * @param contentCode 内容code
	 * @param praiseCount 假赞数量
	 * @param viewCount   假浏览数量 
	 * @param request 
	 * @param request
	 * @param response
	 * @param pager
	 * @return
	 */
	@RequestMapping(value="savemanager")
	public String   savemanager(String postCode,String contentCode,Integer praiseCount,Integer viewCount,HttpServletRequest request,HttpServletResponse response,Pager pager){
		Praise p =  praiseService.getPraiseContentCode(contentCode);
		if (p!=null) {
			p.setFalsePraise(praiseCount);
			praiseService.update(p);
		}else{
			 p   = new Praise(contentCode);
			 p.setFalsePraise(praiseCount);
			 praiseService.save(p);
			
		}
		return  this.managerReply(postCode, request, response, pager);
	}
	/**
	 * 赞
	 * @param contentCode
	 * @param praiseCount
	 * @param viewCount
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("praiseContent")
	@ResponseBody
	public String praiseContent(String contentCode,HttpServletRequest request,HttpServletResponse response){
		 praiseService.updatePraiseCount(contentCode);
		return "点赞成功!";
	}

	public IScoreManagerService getScoreManagerService() {
		return scoreManagerService;
	}

	public void setScoreManagerService(IScoreManagerService scoreManagerService) {
		this.scoreManagerService = scoreManagerService;
	}
	
	
	
	
	
	
}

