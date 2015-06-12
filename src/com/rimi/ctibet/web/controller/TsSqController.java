package com.rimi.ctibet.web.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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

import com.rimi.ctibet.common.constants.Constants;
import com.rimi.ctibet.common.controller.BaseController;
import com.rimi.ctibet.common.util.CodeFactory;
import com.rimi.ctibet.common.util.LuceneUtil2;
import com.rimi.ctibet.common.util.Pager;
import com.rimi.ctibet.common.util.StringUtil;
import com.rimi.ctibet.common.util.WebSearch;
import com.rimi.ctibet.domain.Content;
import com.rimi.ctibet.domain.Image;
import com.rimi.ctibet.domain.Praise;
import com.rimi.ctibet.domain.Programa;
import com.rimi.ctibet.domain.Reply;
import com.rimi.ctibet.domain.ScoreManager;
import com.rimi.ctibet.domain.SysMessage;
import com.rimi.ctibet.portal.controller.FrontTsSqController;
import com.rimi.ctibet.web.service.IContentService;
import com.rimi.ctibet.web.service.IPlateService;
import com.rimi.ctibet.web.service.IPostService;
import com.rimi.ctibet.web.service.IPraiseService;
import com.rimi.ctibet.web.service.IProgramaService;
import com.rimi.ctibet.web.service.IReplyService;
import com.rimi.ctibet.web.service.IScoreManagerService;
import com.rimi.ctibet.web.service.ISysMessageService;
import com.rimi.ctibet.web.service.ImageService;
import com.rimi.ctibet.web.service.MutiImageService;



/**
 * 天上社区的管理模块
 * @author xiaozhen
 *
 */
@RequestMapping("web/tssq")
@Controller
public class TsSqController extends BaseController{
	
	@Resource
	private IPlateService plateService;
	@Resource
	private ImageService imageService;
	@Resource
	private IPostService postService;
	@Resource
	private IContentService contentService;
	@Resource
	private IProgramaService programaService;
	@Resource
	private MutiImageService mutiService;
	@Resource
	private IPraiseService praiseService;
	@Resource
	private IScoreManagerService scoreManagerService;
	@Resource
	private ISysMessageService sysMessageService;
	@Resource
	private IReplyService replyService;
	
	public static   List<Image> listImage  = new ArrayList<Image>(); //预览存放的list
	public static   List<Content> listConent  = new ArrayList<Content>(); //预览存放的list

	//后台社区首页
	@RequestMapping("index")
	public ModelAndView indexTsSq(HttpServletRequest request,HttpServletResponse response){
		ModelAndView view = new ModelAndView("manage/html/bbs/bbs");
		return view;
	}
	//================================板块相关====================================
	//板块列表
	@RequestMapping("plateList")
	public ModelAndView plateList(Pager pager,HttpServletRequest request,HttpServletResponse response){
		ModelAndView view = new ModelAndView("manage/html/bbs/plateList");
		Pager pager1 = plateService.plateList(pager);
		String topNum = plateService.postCountByIsTop();
	    view.addObject("topNum", topNum);
		view.addObject("pager", pager1);
		return view;
	}
	//新建或是编辑板块
	@RequestMapping("saveOrUpdatePlate")
	public ModelAndView saveOrUpdatePlate(HttpServletRequest request,HttpServletResponse response,@ModelAttribute Programa plate){
		ModelAndView view = new ModelAndView("redirect:/web/tssq/plateList");
		String imgUrl = request.getParameter("url");
		//System.out.println("=========================================url============================================================");
		//================================================新增====================================
		if(StringUtils.isBlank(plate.getCode())){
	        //System.out.println("#######################新                    增#############################");
			plate.setImageUrl(imgUrl);
			plate.setCode(CodeFactory.createCultureCode());
	        plate.setpCode("0c60136a-91f8-48b6-bd3b-1bee73f0114f");
	        plate.setProgramaUrl("community/list?plateCode="+plate.getCode());
	        plateService.savePlate(plate);
			//=================预览跳转=================
	        FrontTsSqController.isPrePlate = true;
			FrontTsSqController.preViewPlate = plate;
			return view;
		}
		//====================================修改=================================================
		//System.out.println("#######################修                    改#############################"+plate);
		Programa oldPlate = plateService.findByCode(plate.getCode());
		oldPlate.setImageUrl(imgUrl);
		oldPlate.setProgramaName(plate.getProgramaName());
		oldPlate.setProgramaSummary(plate.getProgramaSummary());
		oldPlate.setLastEditTime(new Date());
		oldPlate.setProgramaUrl("community/list?plateCode="+plate.getCode());
		plateService.updatePlate(oldPlate);
		//=================预览跳转=================
		FrontTsSqController.isPrePlate = true;
		FrontTsSqController.preViewPlate = oldPlate;
       return view.addObject("plate",oldPlate);
	}
	//删除板块
	@RequestMapping("deletePlate")
	public @ResponseBody String deletePlate(HttpServletRequest request,HttpServletResponse response){ 
//	   ModelAndView view = new ModelAndView("redirect:/web/tssq/plateList");
	   String code = request.getParameter("code");
	   String[] codes = code.split(",");
	   for (String scode : codes) {
		   Programa plate = plateService.findByCode(scode);
		   plateService.deletePlate(plate);
	   }
	   return "success";
	}
	//批量删除版块
	@RequestMapping("deletePlates")
	public ModelAndView deletePlates(HttpServletRequest request,HttpServletResponse response){ 
		   ModelAndView view = new ModelAndView("redirect:/web/tssq/plateList");
		   String code = request.getParameter("code");
		   String[] codes = code.split(",");
		   for (String scode : codes) {
			   Programa plate = plateService.findByCode(scode);
			   plateService.deletePlate(plate);
		   }
		   return view;
		}
	//进入编辑与增加界面
	@RequestMapping("gotoSaveOrUpdatePlate")
	public ModelAndView gotoSaveOrUpdatePlate(){
		String code = request.getParameter("code");
		Programa oldPlate = null; 
		oldPlate = plateService.findByCode(code);
		ModelAndView view = new ModelAndView("manage/html/bbs/saveOrUpdatePlate");
        view.addObject("plate", oldPlate);	
	    return view;
	}
	//预览板块对象
	@RequestMapping("pewViewPlate")
	public ModelAndView pewViewPlate(Programa prePlate){
		ModelAndView view = new ModelAndView("redirect:/community/getPostListByPlate");
		String imgUrl = request.getParameter("url");
		prePlate.setImageUrl(imgUrl);
		FrontTsSqController.preViewPlate = prePlate;
		FrontTsSqController.isPrePlate = true;
		return view;
	}
    //==================================帖子相关===================================
    //门户首页帖子管理(null)
	@RequestMapping("postList")
    public ModelAndView postList(Pager pager,HttpServletRequest request,HttpServletResponse response){
    	ModelAndView view = new ModelAndView("manage/html/bbs/postList");
//    	Pager pager1 = postService.
    	return view;
    }
	
	//待审核的帖子
	@RequestMapping("uncheckedPostList")
	public ModelAndView uncheckedPostList(Pager pager,HttpServletRequest request,HttpServletResponse response){
	    String keyWord = request.getParameter("keyWord");
	    String programaCode = request.getParameter("programaCode");
	    //贴子与回复的判断
	    String type = request.getParameter("type");
	    //System.out.println(type);
	    if(StringUtils.isBlank(type)){
	    	type = "post";
	    }
	    ModelAndView view = new ModelAndView("manage/html/bbs/uncheckedPostList");	
	    Pager pager1 = postService.searchPost(0,type,programaCode,keyWord,pager);
	    List<Programa> plates = plateService.getTopPlate();
	    view.addObject("pager", pager1);
	    view.addObject("plates", plates);
	    view.addObject("keyWord",keyWord);
	    view.addObject("type",type);
	    return view;
	}
	//审核帖子通过
	@RequestMapping("passPost")
	public ModelAndView passPost(HttpServletRequest request,HttpServletResponse response){
		String from = request.getParameter("from");
		ModelAndView view = new ModelAndView("redirect:/web/tssq/uncheckedPostList");
		view.addObject("type", from);
	    String code = request.getParameter("code");
	    Content post = contentService.findByCode(code);
	  //关联赞表数据
		Praise p  = praiseService.getPraiseContentCode(code);
		if(p!=null){
			p.setFalseReplyNum(p.getFalseReplyNum()+1);
			p.setReplyNum(p.getReplyNum()+1);
			praiseService.update(p);
		}
		if(post!=null){
	    	post.setState(1);
	        contentService.update(post);
	        if (post.getContentType().equals("post")) {
	            int score=	scoreManagerService.updateMemberScoreByMemberCode(post.getCreateuserCode(), ScoreManager.type_savepost);
	        	SysMessage sm = new SysMessage();
	    		sm.setAvaliable(1);
	    		sm.setCode(CodeFactory.create("sysMsg"));
	    		sm.setContent("您有一个帖子被审核通过");
	    		sm.setContentCode(post.getCode());
	    		sm.setMsgTo(post.getCreateuserCode());
	    		sm.setTitle("帖子审核通过提醒");
	    		sm.setUrl(post.getUrl());
	    		sm.setType(Constants.Post_Judge_Ok);
	    		sm.setCreateDate(new Date());
	    		sm.setContentTitle(post.getContentTitle());
	            sm.setScore(score);
	            sysMessageService.save(sm);
	            //审核通过添加 lucene
	  	      //lucene  查询

	  			WebSearch webSearch = new WebSearch();
	  	        webSearch.setCode(post.getCode());
	  	        webSearch.setUrl(post.getUrl());
	  	        webSearch.setImageUrl(getImgDef());
	  	        webSearch.setTitle(post.getContentTitle());
	  	        webSearch.setContent(StringUtil.cleanHTML(post.getContent()));
	  	        LuceneUtil2.add(webSearch);
			}
	        else{
	        	int returnScore = scoreManagerService.updateMemberScoreByMemberCode(post.getCreateuserCode(), ScoreManager.type_replypost);
	        	List<Reply> list=replyService.findByProperty("contentCode", post.getCode());
	        	if(list.size()>0){
	    			Reply reply = list.get(0);
	    			Content content = contentService.findByCode(reply.getPostCode());
	    			if(content != null){
	    				SysMessage sm = new SysMessage();
	    	    		sm.setAvaliable(1);
	    	    		sm.setCode(CodeFactory.create("sysMsg"));
	    	    		sm.setContent("您有一个帖子的回复被审核通过");
	    	    		sm.setContentCode(content.getCode());
	    	    		sm.setMsgTo(content.getCreateuserCode());
	    	    		sm.setTitle("帖子回复通过提醒");
	    	    		sm.setUrl(content.getUrl());
	    	    		sm.setType(Constants.Post_New_Reply);
	    	    		sm.setCreateDate(new Date());
	    	    		sm.setContentTitle(content.getContentTitle());
	    	            sm.setScore(returnScore);
	    	            sysMessageService.save(sm);
	    			}
	    		}
	        	
	        }
	    }
	    return view;
	}
	//批量删除帖子
	@RequestMapping("deletePost")
	public ModelAndView deletePost(HttpServletRequest request,HttpServletResponse response){
		ModelAndView view = new ModelAndView("redirect:/web/tssq/uncheckedPostList");
	    String code = request.getParameter("code");
	    String[] codes = code.split(",");
	    for (String scode : codes) {
	    	Content post = postService.getPostByCode(scode);
	    	postService.deletePost(post);
		}
	    return view;
	}
	//删除帖子
	@RequestMapping("deleteSinglePost")
	public @ResponseBody String deleteSinglePost(HttpServletRequest request,HttpServletResponse response){
	    String code = request.getParameter("code");
	    String[] codes = code.split(",");
	    for (String scode : codes) {
	    	Content post = postService.getPostByCode(scode);
	    	postService.deletePost(post);
		}
	    return "success";
	}
	//退回帖子
	@RequestMapping("returnPost")
	public ModelAndView returnPost(HttpServletRequest request,HttpServletResponse response){
		String from = request.getParameter("from");
		ModelAndView view = new ModelAndView("redirect:/web/tssq/uncheckedPostList");
		view.addObject("type", from);
	   String code = request.getParameter("code");
	   String[] codes = code.split(",");
	    for (String scode : codes) {
	    	Content content = contentService.findByCode(scode);
	    	contentService.returnPost(content);
		}
	   return view;
	}
	/**
	 * 保存图片
	 */
	@RequestMapping("saveManageImg")
	public @ResponseBody String saveManageImg(HttpServletRequest request,
			HttpServletResponse response,Image image) {
		String name= request.getParameter("name");
		String summary = request.getParameter("summary");
		String hyperlink = request.getParameter("hyperlink");		
//		String viewCode = request.getParameter("viewCode");
//		if(StringUtils.isNotBlank(viewCode)){
//			View view = viewService.findByCode(viewCode);
//			int wantCount = view.getWantCount();
//			int goCount = view.getGoneCount();
//			image.setWantCount(wantCount);
//			image.setGoCount(goCount);
//			image.setHyperlink(view.getCode()); //超链接内容待定
//		}	
		image.setName(name);
		image.setSummary(summary);
		image.setHyperlink(hyperlink);
		//System.out.println("imageName------------>"+image.getName());
		imageService.saveImage(image);
		return "11111";
	}
  

	/**
	 * 跳转至看西藏banner管理
	 */
	@RequestMapping("banner")
	public String banner(ModelMap model){
		List<Content> clist = contentService.findContentByProgrmaCode("61ac7d5-1e8c-33e4-b6ce-005056a05bc9");
		model.put("programaCode", "61ac7d5-1e8c-33e4-b6ce-005056a05bc9");
        model.put("contentList", clist);		
		return "manage/html/bbs/banner";
	}
  
	/**
	 * 保存从前台管理页面过来的banner content
	 * @param content
	 * @return
	 */
	@RequestMapping("saveContent")
	public  String saveContent(String[] url,String[] content,String[] contentTitle,String programaCode){
      List<Content> clist = new ArrayList<Content>();
		for(int i=0;i<5;i++){
			Content c = new Content();
			//content的url 保存为图片路径 content保存为连接 contentTitle保存为编号
			c.setProgramaCode(programaCode);
			c.setUrl(url[i]);
			c.setContent(content[i]); //content  连接地址
			//c.setContentTitle(contentTitle[i]); //编号
			c.setContentType("天上社区banner");
			clist.add(c);
		} 
		if (StringUtils.isNotBlank(request.getParameter("preview"))) {
			listConent  =  clist;
			return "redirect:/community/frontIndex?preview=1";
		}
		contentService.updateSeeBanner(clist,programaCode);
		return "success";
	}
  
	/**
	 * 把预览图片添加到全局变量
	 */
	@RequestMapping("previewManageImg")
	public @ResponseBody String  previewManageImg(Image image) {
		//System.out.println("imageName------------>"+image.toString());
		listImage.add(image);
		return "";
	}
	/**
	 * 跳转到指定的预览页面
	 */
	@RequestMapping("previewManageFront")
	public String previewManageFront(HttpServletRequest request,
			HttpServletResponse response) {
		if(listImage.size()==0){
			return "false";
		}
		String Path = request.getSession().getServletContext().getContextPath();
		//System.out.println("path----->"+Path);
		Image image = listImage.get(0);
		String mutiCode = image.getMutiCode();
		//System.out.println("programaCode----------------->"+mutiCode);
		String url = "";
		if("2ab2378f-17b7-5a3e-a74b-ae44a584cb85".equals(mutiCode)){
			url = "redirect:/web/frontIndexController/indexbanner";  //跳转到banner预览页 (应该是前台)
		}

		return  url;
	}
}