package com.rimi.ctibet.portal.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.google.gson.Gson;
import com.rimi.ctibet.common.constants.Constants;
import com.rimi.ctibet.common.controller.BaseController;
import com.rimi.ctibet.common.util.CodeFactory;
import com.rimi.ctibet.common.util.KeyWordFilter;
import com.rimi.ctibet.common.util.Pager;
import com.rimi.ctibet.common.util.cache.CacheOperation;
import com.rimi.ctibet.domain.AdArea;
import com.rimi.ctibet.domain.Content;
import com.rimi.ctibet.domain.Image;
import com.rimi.ctibet.domain.IndexManager;
import com.rimi.ctibet.domain.LogUser;
import com.rimi.ctibet.domain.Praise;
import com.rimi.ctibet.domain.Programa;
import com.rimi.ctibet.portal.controller.vo.TssqPortalPostVo;
import com.rimi.ctibet.web.controller.PassPostController;
import com.rimi.ctibet.web.controller.TsSqController;
import com.rimi.ctibet.web.controller.vo.PostPassVo;
import com.rimi.ctibet.web.dao.IPostDao;
import com.rimi.ctibet.web.service.DestinationService;
import com.rimi.ctibet.web.service.IAdAreaService;
import com.rimi.ctibet.web.service.IContentService;
import com.rimi.ctibet.web.service.IMemberService;
import com.rimi.ctibet.web.service.IPlateService;
import com.rimi.ctibet.web.service.IPostService;
import com.rimi.ctibet.web.service.IPraiseService;
import com.rimi.ctibet.web.service.IProgramaService;
import com.rimi.ctibet.web.service.ISysMessageService;
import com.rimi.ctibet.web.service.ImageService;
import com.rimi.ctibet.web.service.IndexManagerService;
/**
 * 导航天上社区展示数据
 * @author dengxh
 *
 */
@Controller
@RequestMapping({"web/frontTsSqController","community"})
public class FrontTsSqController extends BaseController{
	@Resource
	private IContentService contentServiceImpl;
	@Resource
	private IProgramaService programaServiceIml;
	@Resource
	private DestinationService  destinationServiceIml;
	@Resource 
	private ImageService imageServiceIml;
	@Resource
	private IPostDao postDao;
	@Resource
	private  IPostService  postService;
	@Resource
	private  IPlateService  plateService;
	@Resource
	private IndexManagerService indexManagerService;
	@Resource
	private IMemberService memberService;
	@Resource
	private ISysMessageService sysMessageService;
	@Resource
	private IAdAreaService adAreaService;
	@Resource
	private IPraiseService praiseService;
	@Resource
	private IContentService contentService;
	
	//后台预览板块对象
	public static Programa preViewPlate;
	public static boolean isPrePlate;
	
	//只要进入这个controller就会先执行这个方法
	@ModelAttribute
	public void setNavSelectIndex(HttpServletRequest request,HttpServletResponse response,ModelMap modelMap){
		modelMap.put("programNam","6");
	}
	/**
	 * 门户社区首页数据的展示
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 */
	@RequestMapping("frontIndex")
	public ModelAndView fontIndexPage(HttpServletRequest request,HttpServletResponse response,ModelMap modelMap){
		modelMap.addAttribute("programNam",6);
		ModelAndView view = (ModelAndView) CacheOperation.getInstance().getCacheData(this, "fontIndexPageHandler", null, Constants.intervalTime, Constants.maxVisitCount);
		//2.最赞回复的数据
		List<IndexManager> list = indexManagerService.getListIndexManager("e43cb122-72d6-11e4-b4ce-005056a05bc9",Content.CONTENTTYPE_REPLY);
		if(StringUtils.isNotBlank(request.getParameter("prew"))){
			list  = indexManagerService.getListIndexManager("e43cb122-72d6-11e4-b4ce-005056a05bc9","0");
		}
		List<Map<String,Object>> bestPraise = new ArrayList<Map<String,Object>>();
		for (IndexManager im : list) {
			Map<String,Object> bp = new HashMap<String,Object>();
			bp.put("content",im.getReplycontent());
			bp.put("url",im.getUrl());
			bp.put("name",im.getReplyname());
			bp.put("replyername",im.getReplyname());
			bp.put("falseprise",im.getPraise());
			bp.put("imCode", im.getCode());
			String refUrl = im.getUrl();
			if(StringUtils.isNotBlank(refUrl)){
				String shit[] = refUrl.split("code=");
				if(shit!=null&&shit.length>1)
				   bp.put("code", shit[1]);
				else{
					if ( im.getUrl() != null
							&&  im.getUrl().lastIndexOf(".html") != -1) {
						String code =  im.getUrl().substring(
								 im.getUrl().lastIndexOf("/") + 1, im.getUrl().lastIndexOf(".html"));
						 bp.put("code", code);
					}
				}
			}
			if ( im.getUrl()!=null) {
				String urls  []  =  im.getUrl().split("code=");
				if(urls.length>1){
				String  code  =  urls[1];
				
				   Content  postContent =  contentServiceImpl.findByCode(code);
				   if(postContent!=null)
				    bp.put("from",postContent.getContentTitle() );
				}else{
					if ( im.getUrl() != null
							&&  im.getUrl().lastIndexOf(".html") != -1) {
						String code =  im.getUrl().substring(
								 im.getUrl().lastIndexOf("/") + 1, im.getUrl().lastIndexOf(".html"));
						Content  postContent =  contentServiceImpl.findByCode(code);
						   if(postContent!=null)
						    bp.put("from",postContent.getContentTitle() );
					}
					
				}
			}
			bestPraise.add(bp);
		} 
		//5.攻略&游记，出行搭伴，公益活动的数据
		List<TssqPortalPostVo> normalPost = new ArrayList<TssqPortalPostVo>();
		normalPost = postService.normalPost();
		view.addObject("normalPost",normalPost);
		  //1.获取全站置顶区的数据
		List<Map<String,Object>> topPost = new ArrayList<Map<String,Object>>();
		topPost = postService.getTopPost();
		//8.banner
		List<Content> clist = contentService.findContentByProgrmaCode("61ac7d5-1e8c-33e4-b6ce-005056a05bc9");
	    //判断是否是banner预览
		String preview = request.getParameter("preview");
		if(StringUtils.isNotBlank(preview)){
			clist = TsSqController.listConent;
		}
		view.addObject("banner", clist);
		view.addObject("bestPraise",bestPraise).addObject("topPost", topPost);
		return view;
	}
	
	public ModelAndView fontIndexPageHandler(){
		ModelAndView view = new ModelAndView("portal/app/bbs/FrontTsSq");
      
//		bestPraise = postService.getBestPraise();
		//3.自驾骑行的数据
		//3.1骑行公告数据
		List<Map<String,Object>> drivAannouncements = new ArrayList<Map<String,Object>>();
		drivAannouncements = postService.getDrivAannouncements();
		//3.2骑行故事数据
		List<Map<String,Object>> driveStorys = new ArrayList<Map<String,Object>>();
		driveStorys = postService.getDriveStorys();
		//3.3骑友征集数据
		List<Map<String,Object>> driveRecruits = new ArrayList<Map<String,Object>>();
		driveRecruits = postService.getDriveRecruits();
		//3.4装备讨论数据
		List<Map<String,Object>> driveEquipments = new ArrayList<Map<String,Object>>();
		driveEquipments = postService.getDriveEquipments();
		//4.回复最多与被赞最多的数据
		List<Map<String,Object>> mostRplys = new ArrayList<Map<String,Object>>();
		// mostRplys = postService.getMostRplys();
		mostRplys = postService.getMostReply(5);
		List<Map<String,Object>> mostPraise = new ArrayList<Map<String,Object>>();
		mostPraise = postService.getMostPraise();
		
		//6.论坛积分榜与发帖排行榜的数据
        List<Map<String,Object>> scoreUsers = postService.getTopFive();        
		List<Map<String,Object>> postUsers = postService.getTopFiveBuPCount();
		//7.广告位
		List<AdArea> ad=adAreaService.findbypro("3452871b-7422-33b2-b6de-349202605b2a");
	
		return view.addObject("drivAannouncements", drivAannouncements)
		.addObject("driveStorys",driveStorys).addObject("driveRecruits", driveRecruits).addObject("driveEquipments",driveEquipments).addObject("mostRplys", mostRplys)
		.addObject("mostPraise", mostPraise).addObject("scoreUsers", scoreUsers).addObject("postUsers", postUsers).addObject("adList",ad);
		
	}
	
	/**
	 * 门户显示帖子列表
	 * @param pager
	 * @param request
	 * @param response
	 * @return
	 */
	
	@RequestMapping({"getAllPassPostInProtal","more"})
	public ModelAndView getAllPassPostInProtal(Pager pager,HttpServletRequest request,HttpServletResponse response){ 
		String  rule = "createTime"; 
		if(StringUtils.isNotBlank(request.getParameter("orderBy"))){
			rule = request.getParameter("orderBy");
		}
		String isTop = request.getParameter("isTop");
		ModelAndView view = new ModelAndView("portal/app/bbs/topPostList");
        //全站置顶区固定code		
		Programa topPlate = plateService.findByCode("06ef926d-e4f5-4466-8403-ec0e37502674");
		view.addObject("plate", topPlate);
		view.addObject("isTop", isTop).addObject("orderBy", rule);
		view.addObject("pager",postService.getAllPassPostInProtal(pager,rule,null,isTop));

        //7.广告位 - 3452871b-74d2-33b2-b6de-34d202605b2d
        List<AdArea> ad=adAreaService.findbypro("3452871b-74d2-33b2-b6de-34d202605b2d");
        if (ad != null) {
            int i = ad.size();
            if (i > 0) {
                int l = (int) (Math.random() * i);
                view.addObject("ad", ad.get(l));
            }
        }

		return view;
	}

	//跳转到发表帖子页面
	@RequestMapping("gotoSavePost")
	public ModelAndView gotoSavePost(HttpServletRequest request,HttpServletResponse response,Content post){
		ModelAndView view  = new ModelAndView("portal/app/bbs/savePost");
		String plateCode = request.getParameter("plateCode");
		Programa plate = plateService.findByCode(plateCode);
		return view.addObject("plateCode",plateCode).addObject("plate", plate);	
	}
	//跳转到发表帖子页面(个人中心跳转)
	@RequestMapping("gotoSavePostUc")
	public ModelAndView gotoSavePostUc(HttpServletRequest request,HttpServletResponse response,Content post){
		ModelAndView view  = new ModelAndView("portal/app/bbs/savePostUc");
		List<Programa> list=plateService.getProList();
		request.setAttribute("pro", list);
		return view.addObject("fromUc","1").addObject("flag", "save");	
	}
	//跳转到修改帖子页面(个人中心跳转)
	@RequestMapping("gotoUpdatePostUc")
	public ModelAndView gotoUpdatePostUc(HttpServletRequest request,HttpServletResponse response,String code){
		LogUser user = (LogUser) request.getSession().getAttribute("logUser");
		
		
		if (user == null) {
			try {
				response.sendRedirect("/"+request.getContextPath());
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		ModelAndView view  = new ModelAndView("portal/app/bbs/savePostUc");
		if(StringUtils.isNotBlank(code)){
		   Content c=contentService.findByCode(code);
		   request.setAttribute("content",c);
		}
		List<Programa> list=plateService.getProList();
		request.setAttribute("pro", list);
		return view.addObject("flag","update").addObject("code", code);	
	}
	//修改帖子
	@RequestMapping("updatePost")
	public ModelAndView updatePost(HttpServletRequest request,HttpServletResponse response,String code){
		String path = request.getContextPath();
		String basePath = request.getScheme() + "://" + request.getServerName() + ":"+ request.getServerPort() + path + "/";
		ModelAndView view  = new ModelAndView("redirect:" + basePath + "portal/userPost/getPostList");
		String programaCode = request.getParameter("programaCode");
		String contentTitle=request.getParameter("contentTitle");
		String content=request.getParameter("content");
		Content post=contentService.findByCode(code);
		if(post==null){
			return view;
		}
		//获取登录人信息
		LogUser logUser = (LogUser)request.getSession().getAttribute("logUser");
        if(logUser!=null){		
        	post.setContentTitle(contentTitle);
			post.setContent(content);
			post.setProgramaCode(programaCode);
			post.setState(0);
			postService.updatePost(post);
		}
       
		return view;	
	}
	//帖子和回复点赞
	@RequestMapping("praisePostOrReply")
	public @ResponseBody String praisePostOrReply(HttpServletRequest request,HttpServletResponse response,Content post){
		String code = request.getParameter("code");
		Praise p  = praiseService.getPraiseContentCode(code);
		p.setFalsePraise(p.getFalsePraise()+1);
		p.setTruePraise(p.getTruePraise()+1);
		return "success";
	}
	//保存帖子
	@RequestMapping("savePost")
	public ModelAndView savePost(HttpServletRequest request,HttpServletResponse response,Content post){
		String plateCode = request.getParameter("programaCode");
		String fromUc=request.getParameter("fromUc");
		ModelAndView view  = new ModelAndView("redirect:/community/getPostListByPlate?plateCode="+plateCode);
		//获取登录人信息
		LogUser logUser = (LogUser)request.getSession().getAttribute("logUser");
        if(logUser!=null){		
			post.setCreateuserCode(logUser.getCode());
			post.setContentType(Content.CONTENTTYPE_POST);
			post.setState(0);
			post.setProgramaCode(plateCode);
			post.setCode(CodeFactory.create("post"));
			//屏蔽标题与内容的敏感词
			String badContent = KeyWordFilter.getInstance().getReplaceStrTxtKeyWords(post.getContent(), "**");
			post.setContent(badContent);
			String badTitle = KeyWordFilter.getInstance().getReplaceStrTxtKeyWords(post.getContentTitle(), "**");
			post.setContentTitle(badTitle);
			post.setUrl(super.getUrlHtml("community/detail",post.getCode()));
			//不确定
			post.setProgramaCode(plateCode);
			postService.savePost(post, null);
		}
        if("1".equals(fromUc)){
        	String path = request.getContextPath();
    		String basePath = request.getScheme() + "://" + request.getServerName() + ":"
    				+ request.getServerPort() + path + "/";
    		ModelAndView vi=new ModelAndView("redirect:" + basePath + "portal/userPost/getPostList");
    		return vi;
        }
		return view;	
	}
	
	//保存回复
	@RequestMapping("saveReply")
	public ModelAndView saveReply(HttpServletRequest request,HttpServletResponse response,Content reply){
		ModelAndView view = new ModelAndView("redirect:/community/detail/"+request.getParameter("postCode")+".html");
		String postCode = request.getParameter("postCode");
		LogUser logUser = (LogUser)request.getSession().getAttribute("logUser");
        if(logUser!=null){		
			reply.setState(0);
			String code = CodeFactory.createCode("reply");
			reply.setCode(code);
			Content  post  =  contentServiceImpl.findByCode(postCode);
			if (post!=null) {
				reply.setProgramaCode(post.getProgramaCode());
				reply.setCreateuserCode(logUser.getCode());
				reply.setUrl(post.getUrl());
				Praise p =praiseService.getPraiseContentCode(postCode);
				p.setFalseReplyNum(p.getFalseReplyNum()+1);
				p.setReplyNum(p.getReplyNum()+1);
				praiseService.update(p);
			}
			String badContent = KeyWordFilter.getInstance().getReplaceStrTxtKeyWords(reply.getContent(), "**");
			reply.setContent(badContent);
			reply.setContentType(Content.CONTENTTYPE_REPLY);
			postService.savePost(reply, postCode);
//			SysMessage sm = new SysMessage();
//    		sm.setAvaliable(1);
//    		sm.setCode(CodeFactory.create("sysMsg"));
//    		sm.setContent("您有一个帖子回复");
//    		sm.setContentCode(post.getCode());
//    		sm.setMsgTo(post.getCreateuserCode());
//    		sm.setTitle("帖子回复提醒");
//    		sm.setUrl(post.getUrl());
//    		sm.setType(Constants.Post_New_Reply);
//    		sm.setCreateDate(new Date());
//    		sm.setContentTitle(post.getContentTitle());
//            sysMessageService.save(sm);
		}
		return view;
	}
	
	//跳转到帖子的详情页
	@RequestMapping({"postDetail","detail"})
	public ModelAndView getPostDetail(Pager pager,HttpServletRequest request,HttpServletResponse response){
		ModelAndView view = new ModelAndView("portal/app/bbs/postDetail");
		//预览功能
		if(StringUtils.isNotBlank(request.getParameter("preview"))){
			Map<String,Object> prePost = new HashMap<String,Object>();
			prePost.put("contenttitle",PassPostController.contentPriew.getContentTitle());
			prePost.put("content",PassPostController.contentPriew.getContent());
			view.addObject("post",prePost);
			view.addObject("plate", PassPostController.prePlate);
			PassPostController.prePlate = null;
			return view;
		}
		String postCode = request.getParameter("code");
		Map<String,Object> postDetail = postService.getPostDetailByCode(postCode);
		if (postDetail==null||postDetail.size()<=0) {
			view  = new ModelAndView("//portal//app//404");
				return view ;
			 
		}
		//根据发帖人的code查询总的发帖数
        Map<String,Object> postMap = (Map<String, Object>) postDetail.get("post");
		String memCode = (String) postMap.get("mcode");
		request.setAttribute("createuserCode",memCode );
		String pcount = postService.postCountByMemCode(memCode);
		view.addObject("pcount", pcount);
		
		Map<String,Object> map =(Map<String,Object>)postDetail.get(Content.CONTENTTYPE_POST);
		String plateCode = (String)map.get("programacode");
		Programa plate = plateService.findByCode(plateCode);
		//浏览量的增加
		Map<String,Object> pmap = (Map<String,Object>)postDetail.get("post");
		Praise p  = praiseService.getPraiseContentCode((String)pmap.get("code"));
		if(p!=null){
		  p.setViewCount(p.getViewCount()+1);
		  p.setFalseViewcount(p.getFalseViewcount()+1);
	      praiseService.update(p);
		}
		view.addObject("plate", plate);
		if(postDetail!=null){
		  view.addObject("postCode", postCode);
		  view.addObject("post",postDetail.get(Content.CONTENTTYPE_POST));
		}
		view.addObject("plateCode", plateCode);
		//获取本机点赞对象的code
		String ip =  getIpAddr(request);
		List<String> codes = postService.praiseCode(ip);
		view.addObject("codes", codes);
		return view.addObject("pager", new Pager());
	}
	//预览帖子，跳转到详细页
	@RequestMapping("prePostDetail")
	public ModelAndView prePostDetail(Pager pager,HttpServletRequest request,HttpServletResponse response,Content post){
		ModelAndView view = new ModelAndView("portal/app/bbs/prePostDetail");
        String plateCode = post.getProgramaCode();
        Programa plate = plateService.findByCode(plateCode);
        LogUser logUser = (LogUser)request.getSession().getAttribute("logUser");
        String pcount = postService.postCountByMemCode(logUser.getCode());
		view.addObject("pcount", pcount);
		return view.addObject("post",post).addObject("plate", plate).addObject("user", logUser);
	}
	
	
	
	//根据板块来显示帖子列表
	@RequestMapping({"getPostListByPlate","list"})
	public ModelAndView getPostListByPlate(Pager pager,HttpServletRequest request,HttpServletResponse response){
		
		//判断是否是后台的预览
		if(isPrePlate){
			ModelAndView view = new ModelAndView("portal/app/bbs/postList");
			view.addObject("plate",FrontTsSqController.preViewPlate);
			FrontTsSqController.isPrePlate = false;
			FrontTsSqController.preViewPlate = null;
			return view;
		}
		String  rule = request.getParameter("orderBy");
		String plateCode = request.getParameter("plateCode");
		//
	
		
		String orderBy = request.getParameter("orderBy");
		if(StringUtils.isBlank(orderBy))orderBy = "createTime";
		Pager pager2 = postService.getAllPassPostInProtal(pager, orderBy, plateCode,null);
		Programa plate = plateService.findByCode(plateCode);
		List<PostPassVo> postPassVos  = pager2.getDataList();
		for (PostPassVo postPassVo : postPassVos) {
			 String mcp =  postPassVo.getReplyinfo();
			 if (mcp!=null) {
				 String m[] = mcp.split("_");
				 String replayName  =  m[0];
				 String replayTime  =  m[1];
				 String replayPic  =  m[2];
				 postPassVo.setReplyName(replayName);
				 postPassVo.setReplyPic(replayPic);
				 postPassVo.setReplyTime(replayTime);
			}
		}
		//7.广告位 - 3452871b-74d2-33b2-b6de-34d202605b2d
		List<AdArea> ad=adAreaService.findbypro("3452871b-74d2-33b2-b6de-34d202605b2d");
        ModelAndView view = new ModelAndView("portal/app/bbs/postList");
        if (ad != null) {
            int i = ad.size();
            if (i > 0) {
                int l = (int) (Math.random() * i);
                view.addObject("ad", ad.get(l));
            }
        }
       view.addObject("plateCode1",plateCode);
        if (StringUtils.isNotBlank(plateCode)&&plateCode.equals("409fb234-8639-452a-a440-88c210f5ce6f")) {
			plateCode = "notice"  ;
		}
        if (StringUtils.isNotBlank(plateCode)&&plateCode.equals("1e7e7437-5858-42a7-bd15-1d29f93326f0")) {
			plateCode = "stroty"  ;
		}
        if (StringUtils.isNotBlank(plateCode)&&plateCode.equals("2a13c8e4-e657-44e9-a29c-c7d13b9f1085")) {
			plateCode = "collect"  ;
		}
        if (StringUtils.isNotBlank(plateCode)&&plateCode.equals("d8e55a99-146b-43f1-be0c-9381b03f862f")) {
			plateCode = "discuss"  ;
		}
        view.addObject("pager",pager2).addObject("plateCode",plateCode);
        view.addObject("plate",plate).addObject("orderBy", orderBy);
		return view;
	}
	
	/**
	 * 帖子回复的信息
	 * portal/activityController/getActivityIndexActivityList
	 */
	@RequestMapping(value="getPostReplyList", produces = "text/html; charset=utf-8")
	@ResponseBody
	public String getPostReplyList(Pager pager,String  postCode){
		 Pager pager2= postService.getReplysInfoByPostCode(postCode, pager);
		 pager.setTotalPage(pager.getTotalPage());
		 return new Gson().toJson(pager2);
	}
	/**
	 * 获取对应图片信息
	 * @param proCode
	 * @param imgType
	 * @return
	 */
	public List<Image> getListImageIndex(String proCode,String imgType){
		 Map<String,List<Image>>  bannerMap  =  imageServiceIml.getHomeImg();
	     List<Image> bannerImg  =   bannerMap.get(imgType);
		 return bannerImg;
	}
	
	public DestinationService getDestinationServiceIml() {
		return destinationServiceIml;
	}

	public void setDestinationServiceIml(DestinationService destinationServiceIml) {
		this.destinationServiceIml = destinationServiceIml;
	}

	public IProgramaService getProgramaServiceIml() {
		return programaServiceIml;
	}

	public void setProgramaServiceIml(IProgramaService programaServiceIml) {
		this.programaServiceIml = programaServiceIml;
	}

	public IContentService getContentServiceImpl() {
		return contentServiceImpl;
	}

	public void setContentServiceImpl(IContentService contentServiceImpl) {
		this.contentServiceImpl = contentServiceImpl;
	}

	public ImageService getImageServiceIml() {
		return imageServiceIml;
	}

	public void setImageServiceIml(ImageService imageServiceIml) {
		this.imageServiceIml = imageServiceIml;
	}

	public IPostDao getPostDao() {
		return postDao;
	}

	public void setPostDao(IPostDao postDao) {
		this.postDao = postDao;
	}

	public IPlateService getPlateService() {
		return plateService;
	}

	public void setPlateService(IPlateService plateService) {
		this.plateService = plateService;
	}
}
