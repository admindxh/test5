package com.rimi.ctibet.portal.controller;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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
import com.rimi.ctibet.common.util.HtmlRegexpUtil;
import com.rimi.ctibet.common.util.KeyWordFilter;
import com.rimi.ctibet.common.util.LuceneUtil;
import com.rimi.ctibet.common.util.Pager;
import com.rimi.ctibet.common.util.cache.CacheOperation;
import com.rimi.ctibet.domain.AdArea;
import com.rimi.ctibet.domain.Content;
import com.rimi.ctibet.domain.Destination;
import com.rimi.ctibet.domain.Image;
import com.rimi.ctibet.domain.LogUser;
import com.rimi.ctibet.domain.MutiImage;
import com.rimi.ctibet.domain.Praise;
import com.rimi.ctibet.domain.Programa;
import com.rimi.ctibet.domain.StrategyView;
import com.rimi.ctibet.domain.View;
import com.rimi.ctibet.portal.controller.vo.FrontViewAndDesVo;
import com.rimi.ctibet.portal.controller.vo.HotMerchantVo;
import com.rimi.ctibet.portal.controller.vo.MerchantVo;
import com.rimi.ctibet.portal.controller.vo.TravalFrontPageVo;
import com.rimi.ctibet.portal.controller.vo.TravelVo;
import com.rimi.ctibet.web.controller.MerchantController;
import com.rimi.ctibet.web.controller.ReadTibetQxMgController;
import com.rimi.ctibet.web.controller.ReadTibetZhMgController;
import com.rimi.ctibet.web.controller.ReadTibetZjyMgController;
import com.rimi.ctibet.web.controller.TraverlController;
import com.rimi.ctibet.web.service.DestinationService;
import com.rimi.ctibet.web.service.IAdAreaService;
import com.rimi.ctibet.web.service.IContentService;
import com.rimi.ctibet.web.service.IMerchantService;
import com.rimi.ctibet.web.service.IPraiseService;
import com.rimi.ctibet.web.service.IProgramaService;
import com.rimi.ctibet.web.service.IStrategyViewService;
import com.rimi.ctibet.web.service.IViewService;
import com.rimi.ctibet.web.service.ImageService;
import com.rimi.ctibet.web.service.IndexManagerService;
import com.rimi.ctibet.web.service.MutiImageService;

/**
 * 游西藏首页展示
 * @author dengxh
 *
 */
@Controller
@RequestMapping({"web/fronttTourismController","tourism/strage"})
public class FronttTourismController  extends BaseController{
	@Resource
	private IContentService contentServiceImpl;
	
	@Resource
	private IProgramaService programaServiceIml;
	
	@Resource
	private IndexManagerService indexManagerService;
	
	@Resource
	private DestinationService  destinationServiceIml;
	@Resource 
	ImageService imageServiceIml;
	
	@Resource
	private IProgramaService programaServiceImpl;
	
	@Resource
	private IPraiseService praiseService;
	
	@Resource
	private  IMerchantService merchantService;
	
	@Resource
	private MutiImageService mutiService;
	
	@Resource
	private ImageService imageService;
	
	@Resource
	private IViewService  viewService;
	
	@Resource
	private IAdAreaService adAreaService;
	
	@Resource
	private IStrategyViewService  strategyViewService ;
	
	private  Map<String,List<Image>>  blist;
	
	
	
	@ModelAttribute
	public void setNavSelectIndex(HttpServletRequest request,HttpServletResponse response,ModelMap modelMap){
		modelMap.put("programNam","1");
	}
	
	
	/**
	 * 获取对应图片信息
	 * @param proCode
	 * @param imgType
	 * @return
	 */
	public List<Image> getListImageIndex(String proCode,String imgType){
		 Map<String,List<Image>>  bannerMap   =  blist;
	     if (bannerMap!=null) {
			
	    	 List<Image> bannerImg  =   bannerMap.get(imgType);
	    	 return bannerImg;
		}
		 return null;
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
		//blist  =  imageServiceIml.getHomeImg();
		ModelAndView view = (ModelAndView) CacheOperation.getInstance().getCacheData(this, "fontIndexPageHandler", null, Constants.intervalTime, Constants.maxVisitCount);
		 //商户
        List<MerchantVo>  merchantList  = merchantService.getPortalMerchant(4);  
        view.addObject("merchantList", merchantList);
        return view;
	}
	
	public ModelAndView fontIndexPageHandler(){
		ModelAndView modelMap = new ModelAndView("portal/app/tourism");
		modelMap.addObject("programNam", 1);
		SimpleDateFormat  simpleDateFormat =  new SimpleDateFormat("yyyy-MM-dd");
		String ct  =  simpleDateFormat.format(new Date());
		modelMap.addObject("ct", ct);
		 //浮窗管理
        List<Image> homeWindowList  =   this.getListImageIndex("e43cb722-75d6-11e4-b6ce-005056a05bc9","homeWindowList");
		//热门关键字待确认 需求是否后台维护
        modelMap.addObject("homeWindowList", homeWindowList);
		//展示导航数据 接口 xiaozheng 提供
	    // 前台首页栏目code   e8876fe6-7608-11e4-b6ce-005056a05bc9
		List<Programa> programaList  = programaServiceIml.getSendPrograma("e8876fe6-7608-11e4-b6ce-005056a05bc9") ;
		
		// 首页 目的地接口   向文琪提供
		
        List<Destination>   destinationList  =   destinationServiceIml.getAllDestination();
		
        Map<String, List<Image>>    listImage  = imageService.getTravelHomeImg();
        //banner  游西藏  
        List<Image> bannerImage  =   null;
      //推荐位
        List<Image> homeRecomOneList  =   null ;
        List<Image> homeRecomTwoList  =   null ;
        List<Image> homeRecomThreeList  =   null ;
         if (listImage!=null) {
        	 bannerImage  =   listImage.get("travelBannerList");
		 }
         homeRecomOneList  = listImage.get("travelRecomOneList");
    	 homeRecomTwoList  = listImage.get("travelRecomTwoList");
		 homeRecomThreeList  = listImage.get("travelRecomThreeList");
        
        // 热门景点
    	List<View> views  = null;
             views  = new ArrayList<View>();
        views  = this.contentServiceImpl.getTravelView();   
        
        //综合攻略
        List<TravelVo>  zhContent  = this.getListTravelVo("1b808620-79bf-11e4-b6ce-005056a05bc9");
        
        //自驾游攻略
        List<TravelVo>  zjyContent  = this.getListTravelVo("1b851671-79bf-11e4-b6ce-005056a05bc9");
        
      //骑行攻略
        List<TravelVo>  qxContent  = this.getListTravelVo("1b8a6d4a-79bf-11e4-b6ce-005056a05bc9");
        	
       
        
        
        
        modelMap.addObject("views", views);
       
        modelMap.addObject("qxContent", qxContent);
        modelMap.addObject("zhContent", zhContent);
        modelMap.addObject("zjyContent", zjyContent);
        
        modelMap.addObject("programaList", programaList);
        modelMap.addObject("destinationList", destinationList);
        
        modelMap.addObject("homeRecomOneList", homeRecomOneList);
        modelMap.addObject("homeRecomTwoList", homeRecomTwoList);
        modelMap.addObject("homeRecomThreeList", homeRecomThreeList);
		modelMap.addObject("bannerImage", bannerImage);
		return modelMap;
	}
	
	
	/**
	 * 门户社区首页数据的展示
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 */
	@RequestMapping("fontIndexPageBanner")
	public String fontIndexPageBanner(HttpServletRequest request,HttpServletResponse response,ModelMap modelMap){
		blist  =  imageServiceIml.getHomeImg();
		 //浮窗管理
        List<Image> homeWindowList  =   this.getListImageIndex("e43cb722-75d6-11e4-b6ce-005056a05bc9","homeWindowList");
		//热门关键字待确认 需求是否后台维护
        modelMap.addAttribute("homeWindowList", homeWindowList);
		//展示导航数据 接口 xiaozheng 提供
	    // 前台首页栏目code   e8876fe6-7608-11e4-b6ce-005056a05bc9
		List<Programa> programaList  = programaServiceIml.getSendPrograma("e8876fe6-7608-11e4-b6ce-005056a05bc9") ;
		
		// 首页 目的地接口   向文琪提供
		
        List<Destination>   destinationList  =   destinationServiceIml.getAllDestination();
		
        Map<String, List<Image>>    listImage  = imageService.getTravelHomeImg();
        //banner  游西藏  
        List<Image> bannerImage  =   null;
      //推荐位
        List<Image> homeRecomOneList  =   null ;
        List<Image> homeRecomTwoList  =   null ;
        List<Image> homeRecomThreeList  =   null ;
         if (listImage!=null) {
        	 bannerImage  =    TraverlController.listImage;
		}
         homeRecomOneList  = listImage.get("travelRecomOneList");
    	 homeRecomTwoList  = listImage.get("travelRecomTwoList");
		 homeRecomThreeList  = listImage.get("travelRecomThreeList");
        
        // 热门景点
        	List<View> views  = null;
                 views  = new ArrayList<View>();
            views  = this.contentServiceImpl.getTravelView();   
        
        //综合攻略
        List<TravelVo>  zhContent  = this.getListTravelVo("1b808620-79bf-11e4-b6ce-005056a05bc9");
        
        //自驾游攻略
        List<TravelVo>  zjyContent  = this.getListTravelVo("1b851671-79bf-11e4-b6ce-005056a05bc9");
        
      //骑行攻略
        List<TravelVo>  qxContent  = this.getListTravelVo("1b8a6d4a-79bf-11e4-b6ce-005056a05bc9");
        	
        //商户
        List<MerchantVo>  merchantList  = merchantService.getPortalMerchant(4);  
        
        
        
        modelMap.addAttribute("views", views);
        modelMap.addAttribute("merchantList", merchantList);
        modelMap.addAttribute("qxContent", qxContent);
        modelMap.addAttribute("zhContent", zhContent);
        modelMap.addAttribute("zjyContent", zjyContent);
        
        modelMap.addAttribute("programaList", programaList);
        modelMap.addAttribute("destinationList", destinationList);
        
        modelMap.addAttribute("homeRecomOneList", homeRecomOneList);
        modelMap.addAttribute("homeRecomTwoList", homeRecomTwoList);
        modelMap.addAttribute("homeRecomThreeList", homeRecomThreeList);
		modelMap.addAttribute("bannerImage", bannerImage);
		TraverlController.listImage =  null;
		
        return "portal//app//tourism";
	}
	
	
	/**
	 * 推荐位1 预览
	 * @param model
	 * @return
	 */
	@RequestMapping("indexRecommedone")
	public String indexRecommedone(ModelMap modelMap){
		blist  =  imageServiceIml.getHomeImg();
		 //浮窗管理
       List<Image> homeWindowList  =   this.getListImageIndex("e43cb722-75d6-11e4-b6ce-005056a05bc9","homeWindowList");
		//热门关键字待确认 需求是否后台维护
       modelMap.addAttribute("homeWindowList", homeWindowList);
		//展示导航数据 接口 xiaozheng 提供
	    // 前台首页栏目code   e8876fe6-7608-11e4-b6ce-005056a05bc9
		List<Programa> programaList  = programaServiceIml.getSendPrograma("e8876fe6-7608-11e4-b6ce-005056a05bc9") ;
		
		// 首页 目的地接口   向文琪提供
		
        List<Destination>   destinationList  =   destinationServiceIml.getAllDestination();
		
        Map<String, List<Image>>    listImage  = imageService.getTravelHomeImg();
        //banner  游西藏  
        List<Image> bannerImage  =   null;
      //推荐位
        List<Image> homeRecomOneList  =   null ;
        List<Image> homeRecomTwoList  =   null ;
        List<Image> homeRecomThreeList  =   null ;
         if (listImage!=null) {
        	 bannerImage  =   listImage.get("travelBannerList");
		}
         homeRecomOneList  = TraverlController.listImage;
         homeRecomTwoList  = listImage.get("travelRecomTwoList");
         homeRecomThreeList  = listImage.get("travelRecomThreeList");
      
        
        // 热门景点
        	List<View> views  = null;
                 views  = new ArrayList<View>();
            views  = this.contentServiceImpl.getTravelView();   
        
        //综合攻略
        List<TravelVo>  zhContent  = this.getListTravelVo("1b808620-79bf-11e4-b6ce-005056a05bc9");
        
        //自驾游攻略
        List<TravelVo>  zjyContent  = this.getListTravelVo("1b851671-79bf-11e4-b6ce-005056a05bc9");
        
      //骑行攻略
        List<TravelVo>  qxContent  = this.getListTravelVo("1b8a6d4a-79bf-11e4-b6ce-005056a05bc9");
        	
        //商户
        List<MerchantVo>  merchantList  = merchantService.getPortalMerchant(4);  
        
        
        
        modelMap.addAttribute("views", views);
        modelMap.addAttribute("merchantList", merchantList);
        modelMap.addAttribute("qxContent", qxContent);
        modelMap.addAttribute("zhContent", zhContent);
        modelMap.addAttribute("zjyContent", zjyContent);
        
        modelMap.addAttribute("programaList", programaList);
        modelMap.addAttribute("destinationList", destinationList);
        
        modelMap.addAttribute("homeRecomOneList", TraverlController.listImage);
        modelMap.addAttribute("homeRecomTwoList", homeRecomTwoList);
        modelMap.addAttribute("homeRecomThreeList", homeRecomThreeList);
		modelMap.addAttribute("bannerImage", bannerImage);
		TraverlController.listImage  = new ArrayList<Image>();
		
        return "portal//app//tourism";
	}
	
	/**
	 * 推荐位2 预览
	 * @param model
	 * @return
	 */
	@RequestMapping("indexRecommedtow")
	public String indexRecommedtow(ModelMap modelMap){
		blist  =  imageServiceIml.getHomeImg();
		 //浮窗管理
       List<Image> homeWindowList  =   this.getListImageIndex("e43cb722-75d6-11e4-b6ce-005056a05bc9","homeWindowList");
		//热门关键字待确认 需求是否后台维护
       modelMap.addAttribute("homeWindowList", homeWindowList);
		//展示导航数据 接口 xiaozheng 提供
	    // 前台首页栏目code   e8876fe6-7608-11e4-b6ce-005056a05bc9
		List<Programa> programaList  = programaServiceIml.getSendPrograma("e8876fe6-7608-11e4-b6ce-005056a05bc9") ;
		
		// 首页 目的地接口   向文琪提供
		
        List<Destination>   destinationList  =   destinationServiceIml.getAllDestination();
		
        Map<String, List<Image>>    listImage  = imageService.getTravelHomeImg();
        //banner  游西藏  
        List<Image> bannerImage  =   null;
      //推荐位
        List<Image> homeRecomOneList  =   null ;
        List<Image> homeRecomTwoList  =   null ;
        List<Image> homeRecomThreeList  =   null ;
         if (listImage!=null) {
        	 bannerImage  =   listImage.get("travelBannerList");
			  
		}
         homeRecomOneList  = listImage.get("travelRecomOneList");
         homeRecomTwoList  =  TraverlController.listImage;
         
         homeRecomThreeList  = listImage.get("travelRecomThreeList");
      
        
        // 热门景点
        	List<View> views  = null;
                 views  = new ArrayList<View>();
            views  = this.contentServiceImpl.getTravelView();   
        
        //综合攻略
        List<TravelVo>  zhContent  = this.getListTravelVo("1b808620-79bf-11e4-b6ce-005056a05bc9");
        
        //自驾游攻略
        List<TravelVo>  zjyContent  = this.getListTravelVo("1b851671-79bf-11e4-b6ce-005056a05bc9");
        
      //骑行攻略
        List<TravelVo>  qxContent  = this.getListTravelVo("1b8a6d4a-79bf-11e4-b6ce-005056a05bc9");
        	
        //商户
        List<MerchantVo>  merchantList  = merchantService.getPortalMerchant(4);  
        
        
        
        modelMap.addAttribute("views", views);
        modelMap.addAttribute("merchantList", merchantList);
        modelMap.addAttribute("qxContent", qxContent);
        modelMap.addAttribute("zhContent", zhContent);
        modelMap.addAttribute("zjyContent", zjyContent);
        
        modelMap.addAttribute("programaList", programaList);
        modelMap.addAttribute("destinationList", destinationList);
        
        modelMap.addAttribute("homeRecomOneList", homeRecomOneList);
        modelMap.addAttribute("homeRecomTwoList", TraverlController.listImage);
        modelMap.addAttribute("homeRecomThreeList", homeRecomThreeList);
		modelMap.addAttribute("bannerImage", bannerImage);
		TraverlController.listImage  = new ArrayList<Image>();
		
        return "portal//app//tourism";
	}
	
	/**
	 * 推荐位3 预览
	 * @param model
	 * @return
	 */
	@RequestMapping("indexRecommedthree")
	public String indexRecommedthree(ModelMap modelMap){
		blist  =  imageServiceIml.getHomeImg();
		 //浮窗管理
       List<Image> homeWindowList  =   this.getListImageIndex("e43cb722-75d6-11e4-b6ce-005056a05bc9","homeWindowList");
		//热门关键字待确认 需求是否后台维护
       modelMap.addAttribute("homeWindowList", homeWindowList);
		//展示导航数据 接口 xiaozheng 提供
	    // 前台首页栏目code   e8876fe6-7608-11e4-b6ce-005056a05bc9
		List<Programa> programaList  = programaServiceIml.getSendPrograma("e8876fe6-7608-11e4-b6ce-005056a05bc9") ;
		
		// 首页 目的地接口   向文琪提供
		
        List<Destination>   destinationList  =   destinationServiceIml.getAllDestination();
		
        Map<String, List<Image>>    listImage  = imageService.getTravelHomeImg();
        //banner  游西藏  
        List<Image> bannerImage  =   null;
      //推荐位
        List<Image> homeRecomOneList  =   null ;
        List<Image> homeRecomTwoList  =   null ;
        List<Image> homeRecomThreeList  =   null ;
         if (listImage!=null) {
        	 bannerImage  =   listImage.get("travelBannerList");
			  
		}
         homeRecomOneList  = listImage.get("travelRecomOneList");
         homeRecomTwoList  = listImage.get("travelRecomTwoList");
         homeRecomThreeList  = TraverlController.listImage;
      
        
        // 热门景点
        	List<View> views  = null;
                 views  = new ArrayList<View>();
            views  = this.contentServiceImpl.getTravelView();   
        
        //综合攻略
        List<TravelVo>  zhContent  = this.getListTravelVo("1b808620-79bf-11e4-b6ce-005056a05bc9");
        
        //自驾游攻略
        List<TravelVo>  zjyContent  = this.getListTravelVo("1b851671-79bf-11e4-b6ce-005056a05bc9");
        
      //骑行攻略
        List<TravelVo>  qxContent  = this.getListTravelVo("1b8a6d4a-79bf-11e4-b6ce-005056a05bc9");
        	
        //商户
        List<MerchantVo>  merchantList  = merchantService.getPortalMerchant(4);  
        
        
        
        modelMap.addAttribute("views", views);
        modelMap.addAttribute("merchantList", merchantList);
        modelMap.addAttribute("qxContent", qxContent);
        modelMap.addAttribute("zhContent", zhContent);
        modelMap.addAttribute("zjyContent", zjyContent);
        
        modelMap.addAttribute("programaList", programaList);
        modelMap.addAttribute("destinationList", destinationList);
        
        modelMap.addAttribute("homeRecomOneList", homeRecomOneList);
        modelMap.addAttribute("homeRecomTwoList", homeRecomTwoList);
        modelMap.addAttribute("homeRecomThreeList", TraverlController.listImage);
		modelMap.addAttribute("bannerImage", bannerImage);
		
		TraverlController.listImage  = new ArrayList<Image>();
        return "portal//app//tourism";
	}
	
	
	
	
	/**
	 *  综合预览
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 */
	@RequestMapping("fontIndexZhPage")
	public String fontIndexZhPage(HttpServletRequest request,HttpServletResponse response,ModelMap modelMap){
		blist  =  imageServiceIml.getHomeImg();
		 //浮窗管理
       List<Image> homeWindowList  =   this.getListImageIndex("e43cb722-75d6-11e4-b6ce-005056a05bc9","homeWindowList");
		//热门关键字待确认 需求是否后台维护
       modelMap.addAttribute("homeWindowList", homeWindowList);
		//展示导航数据 接口 xiaozheng 提供
	    // 前台首页栏目code   e8876fe6-7608-11e4-b6ce-005056a05bc9
		List<Programa> programaList  = programaServiceIml.getSendPrograma("e8876fe6-7608-11e4-b6ce-005056a05bc9") ;
		
		// 首页 目的地接口   向文琪提供
		
        List<Destination>   destinationList  =   destinationServiceIml.getAllDestination();
		
        Map<String, List<Image>>    listImage  = imageService.getTravelHomeImg();
        //banner  游西藏  
        List<Image> bannerImage  =   null;
      //推荐位
        List<Image> homeRecomOneList  =   null ;
        List<Image> homeRecomTwoList  =   null ;
        List<Image> homeRecomThreeList  =   null ;
         if (listImage!=null) {
        	 bannerImage  = listImage.get("travelBannerList");
			  
		}
         homeRecomOneList  = listImage.get("travelRecomOneList");
         homeRecomTwoList  = listImage.get("travelRecomTwoList");
         homeRecomThreeList  = listImage.get("travelRecomThreeList");
      
        
        // 热门景点
        	List<View> views  = null;
                 views  = new ArrayList<View>();
            views  = this.contentServiceImpl.getTravelView();   
        
        //综合攻略
        List<TravelVo>  zhContent  = this.getPreview(ReadTibetZhMgController.getPreviewImageList());
        ReadTibetZhMgController.setPreviewImageList(null);
        ReadTibetZhMgController.setListImage(new  ArrayList<Image>());
        //自驾游攻略
        List<TravelVo>  zjyContent  = this.getListTravelVo("1b851671-79bf-11e4-b6ce-005056a05bc9");
        
      //骑行攻略
        List<TravelVo>  qxContent  = this.getListTravelVo("1b8a6d4a-79bf-11e4-b6ce-005056a05bc9");
        	
        //商户
        List<MerchantVo>  merchantList  = merchantService.getPortalMerchant(4);  
        
        
        
        modelMap.addAttribute("views", views);
        modelMap.addAttribute("merchantList", merchantList);
        modelMap.addAttribute("qxContent", qxContent);
        modelMap.addAttribute("zhContent", zhContent);
        modelMap.addAttribute("zjyContent", zjyContent);
        
        modelMap.addAttribute("programaList", programaList);
        modelMap.addAttribute("destinationList", destinationList);
        
        modelMap.addAttribute("homeRecomOneList", homeRecomOneList);
        modelMap.addAttribute("homeRecomTwoList", homeRecomTwoList);
        modelMap.addAttribute("homeRecomThreeList", homeRecomThreeList);
		modelMap.addAttribute("bannerImage", bannerImage);
		
		
        return "portal//app//tourism";
	}
	
	
	/**
	 *  自驾游攻略预览
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 */
	@RequestMapping("fontIndexZjyPage")
	public String fontIndexZjyPage(HttpServletRequest request,HttpServletResponse response,ModelMap modelMap){
		blist  =  imageServiceIml.getHomeImg();
		 //浮窗管理
       List<Image> homeWindowList  =   this.getListImageIndex("e43cb722-75d6-11e4-b6ce-005056a05bc9","homeWindowList");
		//热门关键字待确认 需求是否后台维护
       modelMap.addAttribute("homeWindowList", homeWindowList);
		//展示导航数据 接口 xiaozheng 提供
	    // 前台首页栏目code   e8876fe6-7608-11e4-b6ce-005056a05bc9
		List<Programa> programaList  = programaServiceIml.getSendPrograma("e8876fe6-7608-11e4-b6ce-005056a05bc9") ;
		
		// 首页 目的地接口   向文琪提供
		
        List<Destination>   destinationList  =   destinationServiceIml.getAllDestination();
		
        Map<String, List<Image>>    listImage  = imageService.getTravelHomeImg();
        //banner  游西藏  
        List<Image> bannerImage  =   null;
      //推荐位
        List<Image> homeRecomOneList  =   null ;
        List<Image> homeRecomTwoList  =   null ;
        List<Image> homeRecomThreeList  =   null ;
         if (listImage!=null) {
        	 bannerImage  = listImage.get("travelBannerList");
			  
		}
         homeRecomOneList  = listImage.get("travelRecomOneList");
         homeRecomTwoList  = listImage.get("travelRecomTwoList");
         homeRecomThreeList  = listImage.get("travelRecomThreeList");
      
        
        // 热门景点
        	List<View> views  = null;
                 views  = new ArrayList<View>();
            views  = this.contentServiceImpl.getTravelView();   
        //综合攻略
            List<TravelVo>  zhContent  = this.getListTravelVo("1b808620-79bf-11e4-b6ce-005056a05bc9");
        
        
        //自驾游攻略
        List<TravelVo>  zjyContent  = this.getPreview(ReadTibetZjyMgController.getPreviewImageList());
        ReadTibetZjyMgController.setPreviewImageList(null);
        ReadTibetZjyMgController.setListImage(new ArrayList<Image>());
      //骑行攻略
        List<TravelVo>  qxContent  = this.getListTravelVo("1b8a6d4a-79bf-11e4-b6ce-005056a05bc9");
        	
        //商户
        List<MerchantVo>  merchantList  = merchantService.getPortalMerchant(4);  
        
        
        
        modelMap.addAttribute("views", views);
        modelMap.addAttribute("merchantList", merchantList);
        modelMap.addAttribute("qxContent", qxContent);
        modelMap.addAttribute("zhContent", zhContent);
        modelMap.addAttribute("zjyContent", zjyContent);
        
        modelMap.addAttribute("programaList", programaList);
        modelMap.addAttribute("destinationList", destinationList);
        
        modelMap.addAttribute("homeRecomOneList", homeRecomOneList);
        modelMap.addAttribute("homeRecomTwoList", homeRecomTwoList);
        modelMap.addAttribute("homeRecomThreeList", homeRecomThreeList);
		modelMap.addAttribute("bannerImage", bannerImage);
		
		
        return "portal//app//tourism";
	}
	
	/**
	 * 骑行预览
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 */
	@RequestMapping("fontIndexQxPage")
	public String fontIndexQxPage(HttpServletRequest request,HttpServletResponse response,ModelMap modelMap){
		blist  =  imageServiceIml.getHomeImg();
		 //浮窗管理
       List<Image> homeWindowList  =   this.getListImageIndex("e43cb722-75d6-11e4-b6ce-005056a05bc9","homeWindowList");
		//热门关键字待确认 需求是否后台维护
       modelMap.addAttribute("homeWindowList", homeWindowList);
		//展示导航数据 接口 xiaozheng 提供
	    // 前台首页栏目code   e8876fe6-7608-11e4-b6ce-005056a05bc9
		List<Programa> programaList  = programaServiceIml.getSendPrograma("e8876fe6-7608-11e4-b6ce-005056a05bc9") ;
		
		// 首页 目的地接口   向文琪提供
		
        List<Destination>   destinationList  =   destinationServiceIml.getAllDestination();
		
        Map<String, List<Image>>    listImage  = imageService.getTravelHomeImg();
        //banner  游西藏  
        List<Image> bannerImage  =   null;
      //推荐位
        List<Image> homeRecomOneList  =   null ;
        List<Image> homeRecomTwoList  =   null ;
        List<Image> homeRecomThreeList  =   null ;
         if (listImage!=null) {
        	 bannerImage  = listImage.get("travelBannerList");
			  
		}
         homeRecomOneList  = listImage.get("travelRecomOneList");
         homeRecomTwoList  = listImage.get("travelRecomTwoList");
         homeRecomThreeList  = listImage.get("travelRecomThreeList");
      
        
        // 热门景点
        	List<View> views  = null;
                 views  = new ArrayList<View>();
            views  = this.contentServiceImpl.getTravelView();   
        
        //综合攻略
        List<TravelVo>  zhContent  = this.getListTravelVo("1b808620-79bf-11e4-b6ce-005056a05bc9");
        
        //自驾游攻略
        List<TravelVo>  zjyContent  = this.getListTravelVo("1b851671-79bf-11e4-b6ce-005056a05bc9");
        
      //骑行攻略
        List<TravelVo>  qxContent  = this.getPreview(ReadTibetQxMgController.getPreviewImageList());
        ReadTibetQxMgController.setPreviewImageList(null);
        ReadTibetQxMgController.setListImage(new ArrayList<Image>());
        //商户
        List<MerchantVo>  merchantList  = merchantService.getPortalMerchant(4);  
        
        
        
        modelMap.addAttribute("views", views);
        modelMap.addAttribute("merchantList", merchantList);
        modelMap.addAttribute("qxContent", qxContent);
        modelMap.addAttribute("zhContent", zhContent);
        modelMap.addAttribute("zjyContent", zjyContent);
        
        modelMap.addAttribute("programaList", programaList);
        modelMap.addAttribute("destinationList", destinationList);
        
        modelMap.addAttribute("homeRecomOneList", homeRecomOneList);
        modelMap.addAttribute("homeRecomTwoList", homeRecomTwoList);
        modelMap.addAttribute("homeRecomThreeList", homeRecomThreeList);
		modelMap.addAttribute("bannerImage", bannerImage);
		
		
        return "portal//app//tourism";
	}
	
	
	
	/**
	 *游西藏预览商户数据
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 */
	@RequestMapping("fontMerchantPage")
	public String fontMerchantPage(HttpServletRequest request,HttpServletResponse response,ModelMap modelMap){
		blist  =  imageServiceIml.getHomeImg();
		 //浮窗管理
        List<Image> homeWindowList  =   this.getListImageIndex("e43cb722-75d6-11e4-b6ce-005056a05bc9","homeWindowList");
		//热门关键字待确认 需求是否后台维护
        modelMap.addAttribute("homeWindowList", homeWindowList);
		//展示导航数据 接口 xiaozheng 提供
	    // 前台首页栏目code   e8876fe6-7608-11e4-b6ce-005056a05bc9
		List<Programa> programaList  = programaServiceIml.getSendPrograma("e8876fe6-7608-11e4-b6ce-005056a05bc9") ;
		
		// 首页 目的地接口   向文琪提供
		
        List<Destination>   destinationList  =   destinationServiceIml.getAllDestination();
		
        Map<String, List<Image>>    listImage  = imageService.getTravelHomeImg();
        //banner  游西藏  
        List<Image> bannerImage  =   null;
      //推荐位
        List<Image> homeRecomOneList  =   null ;
        List<Image> homeRecomTwoList  =   null ;
        List<Image> homeRecomThreeList  =   null ;
         if (listImage!=null) {
        	 bannerImage  =   listImage.get("travelBannerList");
		 }
         homeRecomOneList  = listImage.get("travelRecomOneList");
    	 homeRecomTwoList  = listImage.get("travelRecomTwoList");
		 homeRecomThreeList  = listImage.get("travelRecomThreeList");
        
        // 热门景点
        	List<View> views  = null;
                 views  = new ArrayList<View>();
            views  = this.contentServiceImpl.getTravelView();   
        
        //综合攻略
        List<TravelVo>  zhContent  = this.getListTravelVo("1b808620-79bf-11e4-b6ce-005056a05bc9");
        
        //自驾游攻略
        List<TravelVo>  zjyContent  = this.getListTravelVo("1b851671-79bf-11e4-b6ce-005056a05bc9");
        
      //骑行攻略
        List<TravelVo>  qxContent  = this.getListTravelVo("1b8a6d4a-79bf-11e4-b6ce-005056a05bc9");
        	
        //商户
        List<MerchantVo>  merchantList  =   MerchantController. merchantVoList;
        
        
        
        modelMap.addAttribute("views", views);
        modelMap.addAttribute("merchantList", merchantList);
        
        
        
        
        modelMap.addAttribute("qxContent", qxContent);
        modelMap.addAttribute("zhContent", zhContent);
        modelMap.addAttribute("zjyContent", zjyContent);
        
        modelMap.addAttribute("programaList", programaList);
        modelMap.addAttribute("destinationList", destinationList);
        
        modelMap.addAttribute("homeRecomOneList", homeRecomOneList);
        modelMap.addAttribute("homeRecomTwoList", homeRecomTwoList);
        modelMap.addAttribute("homeRecomThreeList", homeRecomThreeList);
		modelMap.addAttribute("bannerImage", bannerImage);
		//释放资源
		MerchantController. merchantVoList = null;
		
        return "portal//app//tourism";
	}
	
	
	/**
	 *游西藏预览热门景点数据
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 */
	@RequestMapping("fontViewPage")
	public String fontViewPage(HttpServletRequest request,HttpServletResponse response,ModelMap modelMap){
		blist  =  imageServiceIml.getHomeImg();
		 //浮窗管理
        List<Image> homeWindowList  =   this.getListImageIndex("e43cb722-75d6-11e4-b6ce-005056a05bc9","homeWindowList");
		//热门关键字待确认 需求是否后台维护
        modelMap.addAttribute("homeWindowList", homeWindowList);
		//展示导航数据 接口 xiaozheng 提供
	    // 前台首页栏目code   e8876fe6-7608-11e4-b6ce-005056a05bc9
		List<Programa> programaList  = programaServiceIml.getSendPrograma("e8876fe6-7608-11e4-b6ce-005056a05bc9") ;
		
		// 首页 目的地接口   向文琪提供
		
        List<Destination>   destinationList  =   destinationServiceIml.getAllDestination();
		
        Map<String, List<Image>>    listImage  = imageService.getTravelHomeImg();
        //banner  游西藏  
        List<Image> bannerImage  =   null;
      //推荐位
        List<Image> homeRecomOneList  =   null ;
        List<Image> homeRecomTwoList  =   null ;
        List<Image> homeRecomThreeList  =   null ;
         if (listImage!=null) {
        	 bannerImage  =   listImage.get("travelBannerList");
		}
         homeRecomOneList  = listImage.get("travelRecomOneList");
    	 homeRecomTwoList  = listImage.get("travelRecomTwoList");
		 homeRecomThreeList  = listImage.get("travelRecomThreeList");
        
        // 热门景点
        List<View> views  = null;
                 views  = new ArrayList<View>();
            views  = TraverlController.listview;   
        
        //综合攻略
        List<TravelVo>  zhContent  = this.getListTravelVo("1b808620-79bf-11e4-b6ce-005056a05bc9");
        
        //自驾游攻略
        List<TravelVo>  zjyContent  = this.getListTravelVo("1b851671-79bf-11e4-b6ce-005056a05bc9");
        
      //骑行攻略
        List<TravelVo>  qxContent  = this.getListTravelVo("1b8a6d4a-79bf-11e4-b6ce-005056a05bc9");
        	
        //商户
        List<MerchantVo>  merchantList  =  merchantService.getPortalMerchant(4);
        
        
        
        modelMap.addAttribute("views", views);
        modelMap.addAttribute("merchantList", merchantList);
        
        
        
        
        modelMap.addAttribute("qxContent", qxContent);
        modelMap.addAttribute("zhContent", zhContent);
        modelMap.addAttribute("zjyContent", zjyContent);
        
        modelMap.addAttribute("programaList", programaList);
        modelMap.addAttribute("destinationList", destinationList);
        
        modelMap.addAttribute("homeRecomOneList", homeRecomOneList);
        modelMap.addAttribute("homeRecomTwoList", homeRecomTwoList);
        modelMap.addAttribute("homeRecomThreeList", homeRecomThreeList);
		modelMap.addAttribute("bannerImage", bannerImage);
		//释放资源
		TraverlController.listview = null;
        return "portal//app//tourism";
	}
	
	
	
	
	/**
	 * 获取攻略信息
	 * @param programacode
	 * @return
	 */
	public List<List<Image>> getTravel(String programacode){
		String programCode  = programacode;
		List<MutiImage> mutiImageList = mutiService.getMutiImageByProgramaCode(programCode);
		List<List<Image>> imageList = new ArrayList<List<Image>>();
		for (MutiImage muti : mutiImageList) {
			imageList.add(imageService.getImageByMutiImageCode(muti.getCode()));
		}
		return imageList;
	}
	
	public List<TravelVo> getListTravelVo(String programcode){
		List<List<Image>>  imgs  =  this.getTravel(programcode);
		List<TravelVo> pageVoImage = new ArrayList<TravelVo>();
		pageVoImage = this.getPreview(imgs);
		return pageVoImage;
	}
	
	public List<TravelVo>  getPreview(List<List<Image>>  imgs){
		List<TravelVo> pageVoImage = new ArrayList<TravelVo>();
		if (imgs!=null&&imgs.size()>=1) {
			List<Image> voImag  = imgs.get(0);
			for (Image image : voImag) {
				TravelVo  travelVo =  new TravelVo();
				String visitUrl   = image.getHyperlink();
				String code =  "";
				if (visitUrl!=null&&visitUrl.lastIndexOf("=")!=-1) {
					code   =  visitUrl.substring(visitUrl.lastIndexOf("=")+1);
				}else if(visitUrl!=null&&visitUrl.lastIndexOf(".html") != -1) {
						 code = visitUrl.substring(
								 visitUrl.lastIndexOf("/") + 1,visitUrl.lastIndexOf(".html"));
					
				}
				Content c  = null;
				if (!code.equals("")) {
					c  = contentServiceImpl.findByCode(code);
				}
				Praise   p  =  null;
				if (c!=null) {
					travelVo.setTitle(c.getContentTitle());
					p = praiseService.getPraiseContentCode(c.getCode());
				}
				
				if (p!=null) {
					travelVo.setFavotecount(p.getFalseFavoriteNum());
					travelVo.setViewcount(p.getFalseViewcount());
				}
				
				travelVo.setVisitUrl(visitUrl);
				travelVo.setImgrurl(image.getUrl());
				/*if (c==null) {
					travelVo.setVisitUrl("javascript:void(0)");
				}*/
				pageVoImage.add(travelVo);
			}
		}
		return pageVoImage;
		
	}
	
	/**
	 * 跳入攻略页面 和经典攻略页面
	 * @return
	 */
	@RequestMapping({"intoTraval"})
	public String intoTraval(HttpServletRequest request,HttpServletResponse response,ModelMap modelMap){
		String programCode  = request.getParameter("programCode");	
		String des  = request.getParameter("des");
//		des="dest421525134056";
		String view  = request.getParameter("view");
		String keyword  = request.getParameter("keyword");
		String isOfficial  = request.getParameter("isOfficial");
		List<Programa> programaList  = programaServiceIml.getSendPrograma("e8876fe6-7608-11e4-b6ce-005056a05bc9") ;
		// 首页 目的地接口
        List<Destination>   destinationList  =   destinationServiceIml.getAllDestination();
        if (StringUtils.isNotBlank(des)) {
        	List<View>  viewList  =viewService.findViewByDestinationCode(des);
        	modelMap.put("viewList", viewList);
		}
        List<HotMerchantVo> hotMct  =   merchantService.getHotMerchant(5,des);
        for (HotMerchantVo hotMerchantVo : hotMct) {
			  //System.out.println(hotMerchantVo.getMerchant().getMerchantName());
		}
        modelMap.addAttribute("hotMct",hotMct);
        modelMap.addAttribute("programaList", programaList);
        modelMap.addAttribute("destinationList",destinationList);
        modelMap.addAttribute("programCode", programCode);
        String order  = request.getParameter("order");	
        if (order!=null&&!order.equals("")) {
			
		}else{
			order = "1";
		}
        String   desText = "相关的目的地";
        if (StringUtils.isNotBlank(des)) {
        		Destination destination =  destinationServiceIml.getDestinationByCode(des);
        		if (destination!=null) {
					
        			desText =   destination.getDestinationName();
				}
		}
        
        String   viewText = "相关景点";
        if (StringUtils.isNotBlank(view)) {
        		View view1 =  viewService.findByCode(view);
        		if (view1!=null) {
        			viewText =   view1.getViewName();
					
				}
		}
        
        
        String   glText = "所有";
        if (StringUtils.isNotBlank(programCode)) {
        		Programa programa = programaServiceIml.getProgramaByCode(programCode);
        		if (programa!=null) {
        			glText =  programa.getProgramaName();
				}
		}
        String   gllxText = "所有";
        if (StringUtils.isNotBlank(isOfficial)) {
        		if (isOfficial.equals("0")) {
					gllxText="用户上传";
					
				}else{
					gllxText = "官方发布";
				}
		}
        
        
       
        //查询广告
        List<AdArea> adlist=adAreaService.findbypro("14dbad51-cbsb-46d1-b5ef-b3838670b3a9");
        
        List<Map<String, Object>> viewByGone = viewService.getViewByGone(3,null);
        List<Programa> programsList1  = programaServiceImpl.getSendPrograma("098bf91c-792e-11e4-b6ce-005056a05bc9");
        
        modelMap.put("programaList1", programsList1);
        modelMap.addAttribute("viewByGone", viewByGone);
        modelMap.addAttribute("viewText",viewText);
		modelMap.addAttribute("desText",desText);
		modelMap.addAttribute("gllxText",gllxText);
		modelMap.addAttribute("glText",glText);
        modelMap.addAttribute("keyword", keyword);
        modelMap.addAttribute("isOfficial", isOfficial);
        modelMap.addAttribute("des", des);
        modelMap.addAttribute("view", view);
        modelMap.addAttribute("order", Integer.valueOf(order));
        modelMap.addAttribute("adlist",adlist);
        return "portal/app/tourism/strategy_list";
	}
	
	
	
	/**
	 * 经典攻略页面
	 * @return
	 */
	@RequestMapping({"classicsStra"})
	public String classicsStra(HttpServletRequest request,HttpServletResponse response,ModelMap modelMap){
		modelMap.addAttribute("programNam", 4);
		String programCode  = request.getParameter("programCode");	
		if (!StringUtils.isNotBlank(programCode)) {
			programCode = "c1d8e87d-792e-11e4-b6ce-005056a05bc9";
		}
		
		String des  = request.getParameter("des");
//		des="dest421525134056";
		String view  = request.getParameter("view");
		String keyword  = request.getParameter("keyword");
		String isOfficial  = request.getParameter("isOfficial");
		List<Programa> programaList  = programaServiceIml.getSendPrograma("e8876fe6-7608-11e4-b6ce-005056a05bc9") ;
		// 首页 目的地接口
        List<Destination>   destinationList  =   destinationServiceIml.getAllDestination();
        if (StringUtils.isNotBlank(des)) {
        	List<View>  viewList  =viewService.findViewByDestinationCode(des);
        	modelMap.put("viewList", viewList);
		}
        List<HotMerchantVo> hotMct  =   merchantService.getHotMerchant(5,des);
        for (HotMerchantVo hotMerchantVo : hotMct) {
			  //System.out.println(hotMerchantVo.getMerchant().getMerchantName());
		}
        modelMap.addAttribute("hotMct",hotMct);
        modelMap.addAttribute("programaList", programaList);
        modelMap.addAttribute("destinationList",destinationList);
        modelMap.addAttribute("programCode", programCode);
        String order  = request.getParameter("order");	
        if (order!=null&&!order.equals("")) {
			
		}else{
			order = "1";
		}
        String   desText = "相关的目的地";
        if (StringUtils.isNotBlank(des)) {
        		Destination destination =  destinationServiceIml.getDestinationByCode(des);
        		if (destination!=null) {
					
        			desText =   destination.getDestinationName();
				}
		}
        
        String   viewText = "相关景点";
        if (StringUtils.isNotBlank(view)) {
        		View view1 =  viewService.findByCode(view);
        		if (view1!=null) {
        			viewText =   view1.getViewName();
					
				}
		}
        
        
        String   glText = "所有";
        if (StringUtils.isNotBlank(programCode)) {
        		Programa programa = programaServiceIml.getProgramaByCode(programCode);
        		if (programa!=null) {
        			glText =  programa.getProgramaName();
				}
		}
        String   gllxText = "所有";
        if (StringUtils.isNotBlank(isOfficial)) {
        		if (isOfficial.equals("0")) {
					gllxText="用户上传";
					
				}else{
					gllxText = "官方发布";
				}
		}
        
        
       
        //查询广告
        List<AdArea> adlist=adAreaService.findbypro("14dbad51-cbsb-46d1-b5ef-b3838670b3a9");
        
        List<Map<String, Object>> viewByGone = viewService.getViewByGone(3,null);
        List<Programa> programsList1  = programaServiceImpl.getSendPrograma("098bf91c-792e-11e4-b6ce-005056a05bc9");
        
        modelMap.put("programaList1", programsList1);
        modelMap.addAttribute("viewByGone", viewByGone);
        modelMap.addAttribute("viewText",viewText);
		modelMap.addAttribute("desText",desText);
		modelMap.addAttribute("gllxText",gllxText);
		modelMap.addAttribute("glText",glText);
        modelMap.addAttribute("keyword", keyword);
        modelMap.addAttribute("isOfficial", isOfficial);
        modelMap.addAttribute("des", des);
        modelMap.addAttribute("view", view);
        modelMap.addAttribute("order", Integer.valueOf(order));
        modelMap.addAttribute("adlist",adlist);
        return "portal/app/tourism/classics_strategy_list";
	}
	
	/**
	 * 攻略 全部活动列表
	 * portal/activityController/getActivityIndexActivityList
	 */
	@RequestMapping(value="getTravalIndexActivityList", produces = "text/html; charset=utf-8")
	@ResponseBody
	public String getTravalIndexActivityList(Pager pager,Integer order,String programCode,String des,String view,String keyword,Integer isOfficial){
		//pager = activityService.getAllActivityOrder(pager, 0);
		pager = contentServiceImpl.findPagerTravel(pager, order, programCode,des,view,keyword,isOfficial);
		List<TravalFrontPageVo>   dataList  = pager.getDataList();
		for (TravalFrontPageVo travalFrontPageVo : dataList) {
			 String srcImg   =  super.getContentImgSrc(travalFrontPageVo.getTravelContent());
			 travalFrontPageVo.setTravelImgUrl(srcImg);
			 travalFrontPageVo.setTravelContent(HtmlRegexpUtil.filterHtml(travalFrontPageVo.getTravelContent()));
		}
		pager.setTotalPage(pager.getTotalPage());
		//
		
		
		List<View>  listView  =  new ArrayList<View>();
		
		
		List<TravalFrontPageVo> list  =  pager.getDataList();
		for (TravalFrontPageVo content : list) {
			  listView	=  new ArrayList<View>();
			  String contentCode  = content.getCode();
			 List<StrategyView>  view1  =  strategyViewService.findViewCodeByContentCode(contentCode);
			 for (StrategyView strategyView : view1) {
				   View view2 =  new View();
				   view2.setCode(strategyView.getViewCode());
				   view2  =  viewService.findByCode(strategyView.getViewCode());
				   if (view2!=null) {
					   Destination  desName  =  destinationServiceIml.findByCode(view2.getDestinationCode());
					   	if (desName!=null) {
							 view2.setDestinationName(desName.getDestinationName());
						}
					   	listView.add(view2);
				}
			}
		    content.setViewList(listView);
		}
		
		return new Gson().toJson(pager);
	}
	/**
	 * 进入详情攻略页面
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 */
	@RequestMapping({"intoDetailTravel","detail"})
	public String  intoDetailTravel(HttpServletRequest request,HttpServletResponse response,ModelMap modelMap){
		//展示导航数据 接口 xiaozheng 提供
	    // 前台首页栏目code   e8876fe6-7608-11e4-b6ce-005056a05bc9
		String code  =  request.getParameter("code");
		if(code==null){
			code =  (String) request.getAttribute("code");
		}
		List<Programa> programaList  = programaServiceIml.getSendPrograma("e8876fe6-7608-11e4-b6ce-005056a05bc9") ;
		// 首页 目的地接口   向文琪提供
        List<Destination>   destinationList  =   destinationServiceIml.getAllDestination();
        TravalFrontPageVo frontPageVo   =  contentServiceImpl.getTravalFrontPageVo(code);
        List<Programa> programsList  = programaServiceImpl.getSendPrograma("098bf91c-792e-11e4-b6ce-005056a05bc9");
        
        List<TravalFrontPageVo>  reommendList  =   contentServiceImpl.getStrageList(7,code);
        
        List<FrontViewAndDesVo>  des  =  contentServiceImpl.getFrontViewAndDesList(Constants.SEARCH_DES, code);
        
        List<FrontViewAndDesVo>  views  =  contentServiceImpl.getFrontViewAndDesList(Constants.SEARCH_VIEW, code);
        modelMap.addAttribute("code", code);
        modelMap.addAttribute("des", des);
        modelMap.addAttribute("views", views);
        modelMap.addAttribute("reommendList", reommendList);
        modelMap.addAttribute("programsList", programsList);
        modelMap.addAttribute("frontPageVo",frontPageVo);
        modelMap.addAttribute("programaList", programaList);
        modelMap.addAttribute("destinationList", destinationList);
		//查询栏目类型
        if (frontPageVo!=null) {
        	Programa programa =  programaServiceImpl.getProgramaByCode(frontPageVo.getProgramaCode());
        	if (programa!=null) {
        		modelMap.addAttribute("prName", programa.getProgramaName());
        	}
			
		}
        //栏目code
         Content content  = contentServiceImpl.findByCode(code);
         modelMap.addAttribute("content", content);
         if (content==null) {
			return  "/portal/app/404";
		 }
		return "/portal/app/tourism/detail";
	}
	
	
	
	/**
	 * 攻略 全部活动列表
	 * portal/activityController/getActivityIndexActivityList
	 */
	@RequestMapping(value="getTravelReply", produces = "text/html; charset=utf-8")
	@ResponseBody
	public String getTravelReply(Pager pager,String code){
		//pager = activityService.getAllActivityOrder(pager, 0);
		pager = contentServiceImpl.getFrontReplyVo(pager, code);
		pager.setTotalPage(pager.getTotalPage());
		return new Gson().toJson(pager);
	}
	
	/**
	 * 保存回复
	 * @param content
	 * @param postCode
	 * @return
	 */
	@RequestMapping("saveReply")
	@ResponseBody
	public String  saveReply(Content content,String postCode,HttpServletRequest request,HttpServletResponse response,ModelMap modelMap){
		content.setCreateTime(new Date());
		LogUser  logUser =  getFrontUser();
		if (logUser==null) {
			try {
				response.sendRedirect("redirect:/portal/clientLog/loginPage");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return "";
		}
		content.setProgramaCode(content.getProgramaCode());
		content.setCreateuserCode(logUser.getCode());
		content.setContentType(Content.DETAIL_STRATEGY_REPLY);
		content.setState(Constants.STATUS_CHECK);
		String  c  = content.getContent();
	       c =  KeyWordFilter.getInstance().getReplaceStrTxtKeyWords(c, "**")  ; 
	       content.setContent(c);
		contentServiceImpl.saveReply(content, postCode);
		return  "";
	}
	
	//发表
	
	
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
		c.setIsOfficial(0);
		/*//获取当前用户
		if (this.getManagerUser()!=null) {
			//c.setCreateuserCode(this.getManagerUser().getCode());
		}
		Member member =  new Member();
		*/
		/*if (authorCode!=null&&!authorCode.equals("")) {
			MemberInfo memberInfo   =  new MemberInfo();
			memberInfo.setName(authorCode);
			member = memberService.saveByName(memberInfo);
			c.setCreateuserCode(member.getCode());
		}*/
		
		
		String code  = CodeFactory.create("yxzgl");
		String url  = this.getUrl("/tourism/strage/detail", code);
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
		return "";
	}
	
	
	
	
}
