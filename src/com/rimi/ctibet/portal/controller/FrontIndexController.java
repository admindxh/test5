package com.rimi.ctibet.portal.controller;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.rimi.ctibet.common.controller.BaseController;
import com.rimi.ctibet.common.util.SpringUtil;
import com.rimi.ctibet.common.util.StringUtil;
import com.rimi.ctibet.common.util.cache.CacheOperation;
import com.rimi.ctibet.domain.Content;
import com.rimi.ctibet.domain.Destination;
import com.rimi.ctibet.domain.Image;
import com.rimi.ctibet.domain.Praise;
import com.rimi.ctibet.domain.Programa;
import com.rimi.ctibet.web.controller.HomeController;
import com.rimi.ctibet.web.controller.vo.Culture;
import com.rimi.ctibet.web.service.DestinationService;
import com.rimi.ctibet.web.service.IContentService;
import com.rimi.ctibet.web.service.IPraiseService;
import com.rimi.ctibet.web.service.IProgramaService;
import com.rimi.ctibet.web.service.ImageService;
import com.rimi.ctibet.web.service.IndexManagerService;
/**
 * 前台首页
 * @author dengxh
 *
 */
@Controller
@RequestMapping("web/frontIndexController")
public class FrontIndexController extends BaseController   {
	
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
	private IPraiseService praiseService;
	
	private  Map<String,List<Image>>  blist;
	
	
	
	
	
	
	/**
	 *  前台首页数据的实际数据的 展示
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("frontIndex")
	public String fontIndexPage(HttpServletRequest request, HttpServletResponse response, ModelMap modelMap) {
		CacheOperation cache = CacheOperation.getInstance();
		modelMap.addAttribute("programNam", 0);
		blist = imageServiceIml.getHomeImg();
		// 展示导航数据 接口 xiaozheng 提供
		// 前台首页栏目code e8876fe6-7608-11e4-b6ce-005056a05bc9
		List<Programa> programaList = programaServiceIml.getSendPrograma("e8876fe6-7608-11e4-b6ce-005056a05bc9");

		// 首页 目的地接口 向文琪提供

		List<Destination> destinationList = destinationServiceIml.getAllDestination();

		// 首页banner 显示
		// 首页栏目 a3cd8d33-75d6-11e4-b6ce-005056a05bc9
		List<Image> homeBannerList = this.getListImageIndex("e43cb722-75d6-11e4-b6ce-005056a05bc9", "homeBannerList");

		// 浮窗管理
		List<Image> homeWindowList = this.getListImageIndex("e43cb722-75d6-11e4-b6ce-005056a05bc9", "homeWindowList");
		// 热门关键字待确认 需求是否后台维护
		modelMap.addAttribute("homeWindowList", homeWindowList);

		// 图片推荐位管理查询数据 向文琪提供 1,2,3
		List<Image> homeRecomOneList = this.getListImageIndex("13173f79-75da-11e4-b6ce-005056a05bc9", "homeRecomOneList");
		List<Image> homeRecomTwoList = this.getListImageIndex("13173f79-75da-11e4-b6ce-005056a05bc9", "homeRecomTwoList");
		List<Image> homeRecomThreeList = this.getListImageIndex("13173f79-75da-11e4-b6ce-005056a05bc9", "homeRecomThreeList");

//		// 动态列表查询3个数据 叶宇提供接口 13198b8d-75da-11e4-b6ce-005056a05bc9
//
		List<Content> dyDataList = indexManagerService.getListContent("13198b8d-75da-11e4-b6ce-005056a05bc9", "1");
//
//		// 帖子列表显示4个数据 叶宇提供接口
		List<Content> postList = indexManagerService.getListContent("131c3973-75da-11e4-b6ce-005056a05bc9", "1");
//
//		// 游西藏 更多>> 攻略&游记 4 叶宇提供接口
		List<Content> strageList = indexManagerService.getListContent("131ea221-75da-11e4-b6ce-005056a05bc9", "1");
//
//		// 视频专区 接口 叶宇 7
		List<Content> viedioList = indexManagerService.getListContent("1327011a-75da-11e4-b6ce-005056a05bc9", "1");
//
//		// 音乐
		List<Content> list1 = indexManagerService.getListContent("09c7cb0d-7e8c-11e4-b6ce-005056a05bc9", "1");
		computeScore(list1);
//		// 小说
		List<Content> list2 = indexManagerService.getListContent("45e0fd29-7e8c-11e4-b6ce-005056a05bc9", "1");
		computeScore(list2);
		// 游戏
		List<Content> list3 = indexManagerService.getListContent("61d19785-7e8c-11e4-b6ce-005056a05bc9", "1");
		computeScore(list3);
		
		// 动态列表查询3个数据 叶宇提供接口 13198b8d-75da-11e4-b6ce-005056a05bc9
		
		/*List<Content> dyDataList = (List<Content>) cache.getCacheData(indexManagerService, "getListContent", new Object[]{"13198b8d-75da-11e4-b6ce-005056a05bc9","1"}, Constants.intervalTime, Constants.maxVisitCount);
		if (dyDataList==null||dyDataList.size()<=0) {
			 dyDataList  =   indexManagerService.getListContent("13198b8d-75da-11e4-b6ce-005056a05bc9", "1");
		}*/
		
		// 帖子列表显示4个数据 叶宇提供接口
		/*List<Content> postList = (List<Content>) cache.getCacheData(indexManagerService, "getListContent", new Object[]{"131c3973-75da-11e4-b6ce-005056a05bc9","1"}, Constants.intervalTime, Constants.maxVisitCount);
		
		if (postList==null||postList.size()<=0) {
			postList  =   indexManagerService.getListContent("131c3973-75da-11e4-b6ce-005056a05bc9", "1");
		}*/
		
		
		
		// 游西藏 更多>> 攻略&游记 4 叶宇提供接口
		/*List<Content> strageList = (List<Content>) cache.getCacheData(indexManagerService, "getListContent", new Object[]{"131ea221-75da-11e4-b6ce-005056a05bc9","1"}, Constants.intervalTime, Constants.maxVisitCount);
		
		if (strageList==null||strageList.size()<=0) {
			strageList  =   indexManagerService.getListContent("131ea221-75da-11e4-b6ce-005056a05bc9", "1");
		}*/
		
		// 视频专区 接口 叶宇 7
		/*List<Content> viedioList = (List<Content>) cache.getCacheData(indexManagerService, "getListContent", new Object[]{"1327011a-75da-11e4-b6ce-005056a05bc9","1"}, Constants.intervalTime, Constants.maxVisitCount);
		if (viedioList==null||viedioList.size()<=0) {
			viedioList  =   indexManagerService.getListContent("1327011a-75da-11e4-b6ce-005056a05bc9", "1");
		}*/
		// 音乐
		/*List<Content> list1 = (List<Content>) cache.getCacheData(indexManagerService, "getListContent", new Object[]{"09c7cb0d-7e8c-11e4-b6ce-005056a05bc9","1"}, Constants.intervalTime, Constants.maxVisitCount);
		if (list1==null||list1.size()<=0) {
			list1  =   indexManagerService.getListContent("09c7cb0d-7e8c-11e4-b6ce-005056a05bc9", "1");
		}*/
		// 小说
		/*List<Content> list2 = (List<Content>) cache.getCacheData(indexManagerService, "getListContent", new Object[]{"45e0fd29-7e8c-11e4-b6ce-005056a05bc9","1"}, Constants.intervalTime, Constants.maxVisitCount);
		if (list2==null||list2.size()<=0) {
			list2  =   indexManagerService.getListContent("45e0fd29-7e8c-11e4-b6ce-005056a05bc9", "1");
		}*/
		// 游戏
		/*List<Content> list3 = (List<Content>) cache.getCacheData(indexManagerService, "getListContent", new Object[]{"61d19785-7e8c-11e4-b6ce-005056a05bc9","1"}, Constants.intervalTime, Constants.maxVisitCount);
		if (list3==null||list3.size()<=0) {
			list3  =   indexManagerService.getListContent("61d19785-7e8c-11e4-b6ce-005056a05bc9", "1");
		}*/
		modelMap.addAttribute("programaList", programaList);
		modelMap.addAttribute("destinationList", destinationList);
		modelMap.addAttribute("homeBannerList", homeBannerList);

		modelMap.addAttribute("homeRecomOneList", homeRecomOneList);
		modelMap.addAttribute("homeRecomTwoList", homeRecomTwoList);
		modelMap.addAttribute("homeRecomThreeList", homeRecomThreeList);

		modelMap.addAttribute("dyDataList", dyDataList);
		modelMap.addAttribute("postList", postList);

		modelMap.addAttribute("strageList", strageList);

		modelMap.addAttribute("viedioList", viedioList);

		modelMap.addAttribute("list1", list1);
		modelMap.addAttribute("list2", list2);
		modelMap.addAttribute("list3", list3);

		// 景点 接口 也是向文琪提供
		List<Image> homeViewList = this.getListImageIndex("a3cd8d33-75d6-11e4-b6ce-005056a05bc9", "homeViewList");

		// 图说西藏 接口 暂时 没有 提供 向文琪
		List<Image> homePictureList = this.getListImageIndex("a3cd8d33-75d6-11e4-b6ce-005056a05bc9", "homePictureList");

		// 读西藏 接口 向文琪
		// 推荐第一个图片
		List<Image> homeReadList = this.getListImageIndex("a3cd8d33-75d6-11e4-b6ce-005056a05bc9", "homeReadList");//
		// 风俗
		List<Image> homeCustomList = this.getListImageIndex("a3cd8d33-75d6-11e4-b6ce-005056a05bc9", "homeCustomList");//

		// 历史
		List<Image> homeCultureList = this.getListImageIndex("a3cd8d33-75d6-11e4-b6ce-005056a05bc9", "homeCultureList");//

		// 宗教
		List<Image> homeReligionList = this.getListImageIndex("a3cd8d33-75d6-11e4-b6ce-005056a05bc9", "homeReligionList");//
		// 其他待 向文琪 提供
		List<Image> homeOtherList = this.getListImageIndex("a3cd8d33-75d6-11e4-b6ce-005056a05bc9", "homeOtherList");//

		modelMap.addAttribute("homeViewList", homeViewList);
		modelMap.addAttribute("homePictureList", homePictureList);
		modelMap.addAttribute("homeReadList", homeReadList);
		modelMap.addAttribute("homeCustomList", homeCustomList);
		modelMap.addAttribute("homeCultureList", homeCultureList);
		modelMap.addAttribute("homeReligionList", homeReligionList);
		modelMap.addAttribute("homeOtherList", homeOtherList);

		this.getContentPVF(strageList);

		return "portal/app/index";
	}
	
	//计算分数
	public void computeScore(List<Content> list){
	    for (Content content : list) {
	        String sc = (String) content.getOthers().get(Culture.FEILD_RL_SCORE);
	        if(StringUtil.isNotNull(sc) && sc.indexOf(",")!=-1){
	            try {
	                String[] array = sc.split(",");
	                float total_sc = Float.valueOf(array[0]);
	                float total_people = Float.valueOf(array[1]);
	                String format = new DecimalFormat("0.0").format(total_sc /total_people);
	                
	                int first = new Integer(format.split("\\.")[0]);
	                int secend = new Integer(format.split("\\.")[1]);
	                if(secend==0){
	                }else if(secend<5){
	                    secend = 5;
	                }else if(secend>5){
	                    secend = 0;
	                    first += 1;
	                }
	                
	                content.getOthers().put(Culture.FEILD_SCORE, first + "." + secend);
	            } catch (NumberFormatException e) {
	                content.getOthers().put(Culture.FEILD_SCORE, "5.0");
	                e.printStackTrace();
	            }
	        }else{
	            content.getOthers().put(Culture.FEILD_SCORE, "5.0");
	        }
        }
	}
	
	/**
	 * content 帮助查询赞等信息
	 * @return
	 */
	public void getContentPVF(List<Content> c){
		if (c!=null) {
			for (Content content : c) {
				Praise  p  = praiseService.getPraiseContentCode(content.getCode());
				if (p!=null) {
					content.setFalseFavite(p.getFalseFavoriteNum());
					content.setFalseViewCount(p.getFalseViewcount());
					content.setFalsePraise(p.getFalsePraise());
				}
			}
		}
		
	}
	
	/**
	 *  前台首页数据的西藏动态列表 预览的 展示
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 */
	@RequestMapping("indexXzDtManagerController")
	public String indexXzDtManagerController(HttpServletRequest request,HttpServletResponse response,ModelMap modelMap){
		blist  =  imageServiceIml.getHomeImg();
		//展示导航数据 接口 xiaozheng 提供
	    // 前台首页栏目code   e8876fe6-7608-11e4-b6ce-005056a05bc9
		List<Programa> programaList  = programaServiceIml.getSendPrograma("e8876fe6-7608-11e4-b6ce-005056a05bc9") ;
		
		// 首页 目的地接口   向文琪提供
		
        List<Destination>   destinationList  =   destinationServiceIml.getAllDestination();
		
		// 首页banner 显示
        Image img = null;
        //首页栏目   a3cd8d33-75d6-11e4-b6ce-005056a05bc9
        List<Image> homeBannerList  =   this.getListImageIndex("e43cb722-75d6-11e4-b6ce-005056a05bc9","homeBannerList");
		//热门关键字待确认 需求是否后台维护
        //浮窗管理
        List<Image> homeWindowList  =   this.getListImageIndex("e43cb722-75d6-11e4-b6ce-005056a05bc9","homeWindowList");
		//热门关键字待确认 需求是否后台维护
        modelMap.addAttribute("homeWindowList", homeWindowList);
        
        
        //图片推荐位管理查询数据 向文琪提供 1,2,3
        List<Image> homeRecomOneList  =   this.getListImageIndex("13173f79-75da-11e4-b6ce-005056a05bc9","homeRecomOneList");
        List<Image> homeRecomTwoList  =   this.getListImageIndex("13173f79-75da-11e4-b6ce-005056a05bc9","homeRecomTwoList");
        List<Image> homeRecomThreeList  =   this.getListImageIndex("13173f79-75da-11e4-b6ce-005056a05bc9","homeRecomThreeList");
        
        
        
        
        String   avalible  = request.getParameter("avalible");
        
        //动态列表查询3个数据  叶宇提供接口  13198b8d-75da-11e4-b6ce-005056a05bc9
        
         List<Content> dyDataList  = indexManagerService.getListContent("13198b8d-75da-11e4-b6ce-005056a05bc9","0");
        
        //帖子列表显示4个数据 叶宇提供接口
          List<Content> postList  = indexManagerService.getListContent("131c3973-75da-11e4-b6ce-005056a05bc9","1");
          
        //游西藏       更多>>  攻略&游记   4 叶宇提供接口
         List<Content> strageList  = indexManagerService.getListContent("131ea221-75da-11e4-b6ce-005056a05bc9","1");
            
        // 景点  接口 也是向文琪提供  
         List<Image> homeViewList  =   this.getListImageIndex("a3cd8d33-75d6-11e4-b6ce-005056a05bc9","homeViewList");
        
       //图说西藏  接口   暂时  没有  提供    向文琪 
         List<Image> homePictureList  =   this.getListImageIndex("a3cd8d33-75d6-11e4-b6ce-005056a05bc9","homePictureList");
        
        
        //视频专区 接口  叶宇  7
          List<Content>   viedioList  =  indexManagerService.getListContent("1327011a-75da-11e4-b6ce-005056a05bc9","1");
        
        //读西藏   接口 向文琪 
          //推荐第一个图片
          List<Image> homeReadList  =   this.getListImageIndex("a3cd8d33-75d6-11e4-b6ce-005056a05bc9","homeReadList");//
           //风俗
          List<Image> homeCustomList  =   this.getListImageIndex("a3cd8d33-75d6-11e4-b6ce-005056a05bc9","homeCustomList");//
          
          //历史
          List<Image> homeCultureList  =   this.getListImageIndex("a3cd8d33-75d6-11e4-b6ce-005056a05bc9","homeCultureList");//
          
          //宗教
          List<Image> homeReligionList  =   this.getListImageIndex("a3cd8d33-75d6-11e4-b6ce-005056a05bc9","homeReligionList");//
          //其他待 向文琪 提供
          List<Image> homeOtherList  =   this.getListImageIndex("a3cd8d33-75d6-11e4-b6ce-005056a05bc9","homeOtherList");//
          
          modelMap.addAttribute("homeOtherList",homeOtherList);
          
          // 西藏文化传播  待   向文琪   接口
          
         //音乐  
          List<Content> list1  =  indexManagerService.getListContent("09c7cb0d-7e8c-11e4-b6ce-005056a05bc9","1");
          //小说
          List<Content> list2  =  indexManagerService.getListContent("45e0fd29-7e8c-11e4-b6ce-005056a05bc9","1");
         //游戏
          List<Content> list3  =  indexManagerService.getListContent("61d19785-7e8c-11e4-b6ce-005056a05bc9","1");
          List<Content> list4  =  indexManagerService.getListContent("611bs12785-7w2e8c-11e4-b6ce-005056a05bc9","1");
		
		
          
          modelMap.addAttribute("programaList", programaList);
          modelMap.addAttribute("destinationList", destinationList);
          modelMap.addAttribute("homeBannerList", homeBannerList);
         
          
          modelMap.addAttribute("homeRecomOneList", homeRecomOneList);
          modelMap.addAttribute("homeRecomTwoList", homeRecomTwoList);
          modelMap.addAttribute("homeRecomThreeList", homeRecomThreeList);

          this.getContentPVF(dyDataList);
          modelMap.addAttribute("dyDataList", dyDataList);
          this.getContentPVF(postList);
          modelMap.addAttribute("postList", postList);
          
          this.getContentPVF(strageList);
          modelMap.addAttribute("strageList", strageList);
          modelMap.addAttribute("homeViewList", homeViewList);
          modelMap.addAttribute("homePictureList", homePictureList);
          
          this.getContentPVF(viedioList);
          modelMap.addAttribute("viedioList", viedioList);
          
          modelMap.addAttribute("homeReadList", homeReadList);
          
        
          
          modelMap.addAttribute("homeCustomList", homeCustomList);
          modelMap.addAttribute("homeCultureList", homeCultureList);
          modelMap.addAttribute("homeReligionList", homeReligionList);
          
          this.getContentPVF(list1);
          this.getContentPVF(list2);
          this.getContentPVF(list3);
          modelMap.addAttribute("list1", list1);
          modelMap.addAttribute("list2", list2);
          modelMap.addAttribute("list3", list3);
          
		
		return "portal//app//index";
	}
	
	/**
	 *  前台首页数据的帖子动态 表 预览的 展示
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 */
	@RequestMapping("indexDzDtManagerController")
	public String indexDzDtManagerController(HttpServletRequest request,HttpServletResponse response,ModelMap modelMap){
		blist  =  imageServiceIml.getHomeImg();
		//展示导航数据 接口 xiaozheng 提供
	    // 前台首页栏目code   e8876fe6-7608-11e4-b6ce-005056a05bc9
		List<Programa> programaList  = programaServiceIml.getSendPrograma("e8876fe6-7608-11e4-b6ce-005056a05bc9") ;
		
		// 首页 目的地接口   向文琪提供
		
        List<Destination>   destinationList  =   destinationServiceIml.getAllDestination();
		
		
		// 首页banner 显示
        Image img = null;
        //首页栏目   a3cd8d33-75d6-11e4-b6ce-005056a05bc9
        List<Image> homeBannerList  =   this.getListImageIndex("e43cb722-75d6-11e4-b6ce-005056a05bc9","homeBannerList");
		//热门关键字待确认 需求是否后台维护
        //浮窗管理
        List<Image> homeWindowList  =   this.getListImageIndex("e43cb722-75d6-11e4-b6ce-005056a05bc9","homeWindowList");
		//热门关键字待确认 需求是否后台维护
        modelMap.addAttribute("homeWindowList", homeWindowList);
        
        
        //图片推荐位管理查询数据 向文琪提供 1,2,3
        List<Image> homeRecomOneList  =   this.getListImageIndex("13173f79-75da-11e4-b6ce-005056a05bc9","homeRecomOneList");
        List<Image> homeRecomTwoList  =   this.getListImageIndex("13173f79-75da-11e4-b6ce-005056a05bc9","homeRecomTwoList");
        List<Image> homeRecomThreeList  =   this.getListImageIndex("13173f79-75da-11e4-b6ce-005056a05bc9","homeRecomThreeList");
        
        
        
        
        String   avalible  = request.getParameter("avalible");
        
        //动态列表查询3个数据  叶宇提供接口  13198b8d-75da-11e4-b6ce-005056a05bc9
        
         List<Content> dyDataList  = indexManagerService.getListContent("13198b8d-75da-11e4-b6ce-005056a05bc9","1");
        
        //帖子列表显示4个数据 叶宇提供接口
          List<Content> postList  = indexManagerService.getListContent("131c3973-75da-11e4-b6ce-005056a05bc9","0");
          
        //游西藏       更多>>  攻略&游记   4 叶宇提供接口
         List<Content> strageList  = indexManagerService.getListContent("131ea221-75da-11e4-b6ce-005056a05bc9","1");
            
        // 景点  接口 也是向文琪提供  
         List<Image> homeViewList  =   this.getListImageIndex("a3cd8d33-75d6-11e4-b6ce-005056a05bc9","homeViewList");
        
       //图说西藏  接口   暂时  没有  提供    向文琪 
         List<Image> homePictureList  =   this.getListImageIndex("a3cd8d33-75d6-11e4-b6ce-005056a05bc9","homePictureList");
        
        
        //视频专区 接口  叶宇  7
          List<Content>   viedioList  =  indexManagerService.getListContent("1327011a-75da-11e4-b6ce-005056a05bc9","1");
        
        //读西藏   接口 向文琪 
          //推荐第一个图片
          List<Image> homeReadList  =   this.getListImageIndex("a3cd8d33-75d6-11e4-b6ce-005056a05bc9","homeReadList");//
           //风俗
          List<Image> homeCustomList  =   this.getListImageIndex("a3cd8d33-75d6-11e4-b6ce-005056a05bc9","homeCustomList");//
          
          //历史
          List<Image> homeCultureList  =   this.getListImageIndex("a3cd8d33-75d6-11e4-b6ce-005056a05bc9","homeCultureList");//
          
          //宗教
          List<Image> homeReligionList  =   this.getListImageIndex("a3cd8d33-75d6-11e4-b6ce-005056a05bc9","homeReligionList");//
          //其他待 向文琪 提供
          
List<Image> homeOtherList  =   this.getListImageIndex("a3cd8d33-75d6-11e4-b6ce-005056a05bc9","homeOtherList");//
          
          modelMap.addAttribute("homeOtherList",homeOtherList);
          // 西藏文化传播  待   向文琪   接口
          
         //音乐  
          List<Content> list1  =  indexManagerService.getListContent("09c7cb0d-7e8c-11e4-b6ce-005056a05bc9","1");
          //小说
          List<Content> list2  =  indexManagerService.getListContent("45e0fd29-7e8c-11e4-b6ce-005056a05bc9","1");
         //游戏
          List<Content> list3  =  indexManagerService.getListContent("61d19785-7e8c-11e4-b6ce-005056a05bc9","1");
  
          List<Content> list4  =  indexManagerService.getListContent("611bs12785-7w2e8c-11e4-b6ce-005056a05bc9","1");
		
          
          modelMap.addAttribute("programaList", programaList);
          modelMap.addAttribute("destinationList", destinationList);
          modelMap.addAttribute("homeBannerList", homeBannerList);
         
         
          
           
          
          modelMap.addAttribute("homeRecomOneList", homeRecomOneList);
          modelMap.addAttribute("homeRecomTwoList", homeRecomTwoList);
          modelMap.addAttribute("homeRecomThreeList", homeRecomThreeList);

          this.getContentPVF(dyDataList);
          modelMap.addAttribute("dyDataList", dyDataList);
          this.getContentPVF(postList);
          modelMap.addAttribute("postList", postList);
          
          this.getContentPVF(strageList);
          modelMap.addAttribute("strageList", strageList);
          modelMap.addAttribute("homeViewList", homeViewList);
          modelMap.addAttribute("homePictureList", homePictureList);
          
          this.getContentPVF(viedioList);
          modelMap.addAttribute("viedioList", viedioList);
          
          modelMap.addAttribute("homeReadList", homeReadList);
         
          
          modelMap.addAttribute("homeCustomList", homeCustomList);
          modelMap.addAttribute("homeCultureList", homeCultureList);
          modelMap.addAttribute("homeReligionList", homeReligionList);
          
          this.getContentPVF(list1);
          this.getContentPVF(list2);
          this.getContentPVF(list3);
          modelMap.addAttribute("list1", list1);
          modelMap.addAttribute("list2", list2);
          modelMap.addAttribute("list3", list3);
          
		
		return "portal//app//index";
	}
	
	
	/**
	 *  前台首页数据的攻略动态 表 预览的 展示
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 */
	@RequestMapping("indexGlDtManagerController")
	public String indexGlDtManagerController(HttpServletRequest request,HttpServletResponse response,ModelMap modelMap){
		blist  =  imageServiceIml.getHomeImg();
		//展示导航数据 接口 xiaozheng 提供
	    // 前台首页栏目code   e8876fe6-7608-11e4-b6ce-005056a05bc9
		List<Programa> programaList  = programaServiceIml.getSendPrograma("e8876fe6-7608-11e4-b6ce-005056a05bc9") ;
		
		// 首页 目的地接口   向文琪提供
		
        List<Destination>   destinationList  =   destinationServiceIml.getAllDestination();
		
		
		// 首页banner 显示
        Image img = null;
        //首页栏目   a3cd8d33-75d6-11e4-b6ce-005056a05bc9
        List<Image> homeBannerList  =   this.getListImageIndex("e43cb722-75d6-11e4-b6ce-005056a05bc9","homeBannerList");
		//热门关键字待确认 需求是否后台维护
        //浮窗管理
        List<Image> homeWindowList  =   this.getListImageIndex("e43cb722-75d6-11e4-b6ce-005056a05bc9","homeWindowList");
		//热门关键字待确认 需求是否后台维护
        modelMap.addAttribute("homeWindowList", homeWindowList);
        
        
        //图片推荐位管理查询数据 向文琪提供 1,2,3
        List<Image> homeRecomOneList  =   this.getListImageIndex("13173f79-75da-11e4-b6ce-005056a05bc9","homeRecomOneList");
        List<Image> homeRecomTwoList  =   this.getListImageIndex("13173f79-75da-11e4-b6ce-005056a05bc9","homeRecomTwoList");
        List<Image> homeRecomThreeList  =   this.getListImageIndex("13173f79-75da-11e4-b6ce-005056a05bc9","homeRecomThreeList");
        
        
        
        
        String   avalible  = request.getParameter("avalible");
        
        //动态列表查询3个数据  叶宇提供接口  13198b8d-75da-11e4-b6ce-005056a05bc9
        
         List<Content> dyDataList  = indexManagerService.getListContent("13198b8d-75da-11e4-b6ce-005056a05bc9","1");
        
        //帖子列表显示4个数据 叶宇提供接口
          List<Content> postList  = indexManagerService.getListContent("131c3973-75da-11e4-b6ce-005056a05bc9","1");
          
        //游西藏       更多>>  攻略&游记   4 叶宇提供接口
         List<Content> strageList  = indexManagerService.getListContent("131ea221-75da-11e4-b6ce-005056a05bc9","0");
            
        // 景点  接口 也是向文琪提供  
         List<Image> homeViewList  =   this.getListImageIndex("a3cd8d33-75d6-11e4-b6ce-005056a05bc9","homeViewList");
        
       //图说西藏  接口   暂时  没有  提供    向文琪 
         List<Image> homePictureList  =   this.getListImageIndex("a3cd8d33-75d6-11e4-b6ce-005056a05bc9","homePictureList");
        
        
        //视频专区 接口  叶宇  7
          List<Content>   viedioList  =  indexManagerService.getListContent("1327011a-75da-11e4-b6ce-005056a05bc9","1");
        
        //读西藏   接口 向文琪 
          //推荐第一个图片
          List<Image> homeReadList  =   this.getListImageIndex("a3cd8d33-75d6-11e4-b6ce-005056a05bc9","homeReadList");//
           //风俗
          List<Image> homeCustomList  =   this.getListImageIndex("a3cd8d33-75d6-11e4-b6ce-005056a05bc9","homeCustomList");//
          
          //历史
          List<Image> homeCultureList  =   this.getListImageIndex("a3cd8d33-75d6-11e4-b6ce-005056a05bc9","homeCultureList");//
          
          //宗教
          List<Image> homeReligionList  =   this.getListImageIndex("a3cd8d33-75d6-11e4-b6ce-005056a05bc9","homeReligionList");//
          //其他待 向文琪 提供
          List<Image> homeOtherList  =   this.getListImageIndex("a3cd8d33-75d6-11e4-b6ce-005056a05bc9","homeOtherList");//
          
          modelMap.addAttribute("homeOtherList",homeOtherList);
          
          // 西藏文化传播  待   向文琪   接口
          
         //音乐  
          List<Content> list1  =  indexManagerService.getListContent("09c7cb0d-7e8c-11e4-b6ce-005056a05bc9","1");
          //小说
          List<Content> list2  =  indexManagerService.getListContent("45e0fd29-7e8c-11e4-b6ce-005056a05bc9","1");
         //游戏
          List<Content> list3  =  indexManagerService.getListContent("61d19785-7e8c-11e4-b6ce-005056a05bc9","1");
  
          List<Content> list4  =  indexManagerService.getListContent("611bs12785-7w2e8c-11e4-b6ce-005056a05bc9","1");
		
          
          modelMap.addAttribute("programaList", programaList);
          modelMap.addAttribute("destinationList", destinationList);
          modelMap.addAttribute("homeBannerList", homeBannerList);
         
         
          
           
          
          modelMap.addAttribute("homeRecomOneList", homeRecomOneList);
          modelMap.addAttribute("homeRecomTwoList", homeRecomTwoList);
          modelMap.addAttribute("homeRecomThreeList", homeRecomThreeList);

          this.getContentPVF(dyDataList);
          modelMap.addAttribute("dyDataList", dyDataList);
          this.getContentPVF(postList);
          modelMap.addAttribute("postList", postList);
          
          this.getContentPVF(strageList);
          modelMap.addAttribute("strageList", strageList);
          modelMap.addAttribute("homeViewList", homeViewList);
          modelMap.addAttribute("homePictureList", homePictureList);
          
          this.getContentPVF(viedioList);
          modelMap.addAttribute("viedioList", viedioList);
          
          modelMap.addAttribute("homeReadList", homeReadList);
          
         
          
          modelMap.addAttribute("homeCustomList", homeCustomList);
          modelMap.addAttribute("homeCultureList", homeCultureList);
          modelMap.addAttribute("homeReligionList", homeReligionList);
          
          this.getContentPVF(list1);
          this.getContentPVF(list2);
          this.getContentPVF(list3);
          modelMap.addAttribute("list1", list1);
          modelMap.addAttribute("list2", list2);
          modelMap.addAttribute("list3", list3);
          
		
		return "portal//app//index";
	}
	
	
	/**
	 *  前台首页数据的视频动态 表 预览的 展示
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 */
	@RequestMapping("indexSpDtManagerController")
	public String indexSpDtManagerController(HttpServletRequest request,HttpServletResponse response,ModelMap modelMap){
		blist  =  imageServiceIml.getHomeImg();
		//展示导航数据 接口 xiaozheng 提供
	    // 前台首页栏目code   e8876fe6-7608-11e4-b6ce-005056a05bc9
		List<Programa> programaList  = programaServiceIml.getSendPrograma("e8876fe6-7608-11e4-b6ce-005056a05bc9") ;
		
		// 首页 目的地接口   向文琪提供
		
        List<Destination>   destinationList  =   destinationServiceIml.getAllDestination();
		
		
		// 首页banner 显示
        Image img = null;
        //首页栏目   a3cd8d33-75d6-11e4-b6ce-005056a05bc9
        List<Image> homeBannerList  =   this.getListImageIndex("e43cb722-75d6-11e4-b6ce-005056a05bc9","homeBannerList");
		//热门关键字待确认 需求是否后台维护
        
        
        //图片推荐位管理查询数据 向文琪提供 1,2,3
        List<Image> homeRecomOneList  =   this.getListImageIndex("13173f79-75da-11e4-b6ce-005056a05bc9","homeRecomOneList");
        List<Image> homeRecomTwoList  =   this.getListImageIndex("13173f79-75da-11e4-b6ce-005056a05bc9","homeRecomTwoList");
        List<Image> homeRecomThreeList  =   this.getListImageIndex("13173f79-75da-11e4-b6ce-005056a05bc9","homeRecomThreeList");
        
        
        
        
        String   avalible  = request.getParameter("avalible");
        
        //动态列表查询3个数据  叶宇提供接口  13198b8d-75da-11e4-b6ce-005056a05bc9
        
         List<Content> dyDataList  = indexManagerService.getListContent("13198b8d-75da-11e4-b6ce-005056a05bc9","1");
        
        //帖子列表显示4个数据 叶宇提供接口
          List<Content> postList  = indexManagerService.getListContent("131c3973-75da-11e4-b6ce-005056a05bc9","1");
          
        //游西藏       更多>>  攻略&游记   4 叶宇提供接口
         List<Content> strageList  = indexManagerService.getListContent("131ea221-75da-11e4-b6ce-005056a05bc9","1");
            
        // 景点  接口 也是向文琪提供  
         List<Image> homeViewList  =   this.getListImageIndex("a3cd8d33-75d6-11e4-b6ce-005056a05bc9","homeViewList");
        
       //图说西藏  接口   暂时  没有  提供    向文琪 
         List<Image> homePictureList  =   this.getListImageIndex("a3cd8d33-75d6-11e4-b6ce-005056a05bc9","homePictureList");
        
        
        //视频专区 接口  叶宇  7
          List<Content>   viedioList  =  indexManagerService.getListContent("1327011a-75da-11e4-b6ce-005056a05bc9","0");
        
        //读西藏   接口 向文琪 
          //推荐第一个图片
          List<Image> homeReadList  =   this.getListImageIndex("a3cd8d33-75d6-11e4-b6ce-005056a05bc9","homeReadList");//
           //风俗
          List<Image> homeCustomList  =   this.getListImageIndex("a3cd8d33-75d6-11e4-b6ce-005056a05bc9","homeCustomList");//
          
          //历史
          List<Image> homeCultureList  =   this.getListImageIndex("a3cd8d33-75d6-11e4-b6ce-005056a05bc9","homeCultureList");//
          
          //宗教
          List<Image> homeReligionList  =   this.getListImageIndex("a3cd8d33-75d6-11e4-b6ce-005056a05bc9","homeReligionList");//
          //其他待 向文琪 提供
          
List<Image> homeOtherList  =   this.getListImageIndex("a3cd8d33-75d6-11e4-b6ce-005056a05bc9","homeOtherList");//
          
          modelMap.addAttribute("homeOtherList",homeOtherList);
          // 西藏文化传播  待   向文琪   接口
          
         //音乐  
          List<Content> list1  =  indexManagerService.getListContent("09c7cb0d-7e8c-11e4-b6ce-005056a05bc9","1");
          //小说
          List<Content> list2  =  indexManagerService.getListContent("45e0fd29-7e8c-11e4-b6ce-005056a05bc9","1");
         //游戏
          List<Content> list3  =  indexManagerService.getListContent("61d19785-7e8c-11e4-b6ce-005056a05bc9","1");
  
          List<Content> list4  =  indexManagerService.getListContent("611bs12785-7w2e8c-11e4-b6ce-005056a05bc9","1");
		
          
          modelMap.addAttribute("programaList", programaList);
          modelMap.addAttribute("destinationList", destinationList);
          modelMap.addAttribute("homeBannerList", homeBannerList);
         
          
          
           
          
          modelMap.addAttribute("homeRecomOneList", homeRecomOneList);
          modelMap.addAttribute("homeRecomTwoList", homeRecomTwoList);
          modelMap.addAttribute("homeRecomThreeList", homeRecomThreeList);

          this.getContentPVF(dyDataList);
          modelMap.addAttribute("dyDataList", dyDataList);
          this.getContentPVF(postList);
          modelMap.addAttribute("postList", postList);
          
          this.getContentPVF(strageList);
          modelMap.addAttribute("strageList", strageList);
          modelMap.addAttribute("homeViewList", homeViewList);
          modelMap.addAttribute("homePictureList", homePictureList);
          
          this.getContentPVF(viedioList);
          modelMap.addAttribute("viedioList", viedioList);
          
          modelMap.addAttribute("homeReadList", homeReadList);
          
         
          
          modelMap.addAttribute("homeCustomList", homeCustomList);
          modelMap.addAttribute("homeCultureList", homeCultureList);
          modelMap.addAttribute("homeReligionList", homeReligionList);
          
          this.getContentPVF(list1);
          this.getContentPVF(list2);
          this.getContentPVF(list3);
          modelMap.addAttribute("list1", list1);
          modelMap.addAttribute("list2", list2);
          modelMap.addAttribute("list3", list3);
          List<Image> homeWindowList  =  this.getListImageIndex("a3cd8d33-75d6-11e4-b6ce-005056a05bc9","homeWindowList");//;
          modelMap.addAttribute("homeWindowList", homeWindowList);
          
          
		
		return "portal//app//index";
	}
	
    
	/**
	 *  前台首页数据的  //音乐  动态 表 预览的 展示 	// 西藏文化传播  待   向文琪   接口  //音乐
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 */
	@RequestMapping("indexyy")
	public String indexyy(HttpServletRequest request,HttpServletResponse response,ModelMap modelMap){
		blist  =  imageServiceIml.getHomeImg();
		//展示导航数据 接口 xiaozheng 提供
	    // 前台首页栏目code   e8876fe6-7608-11e4-b6ce-005056a05bc9
		List<Programa> programaList  = programaServiceIml.getSendPrograma("e8876fe6-7608-11e4-b6ce-005056a05bc9") ;
		
		// 首页 目的地接口   向文琪提供
		
        List<Destination>   destinationList  =   destinationServiceIml.getAllDestination();
		
		
		// 首页banner 显示
        Image img = null;
        //首页栏目   a3cd8d33-75d6-11e4-b6ce-005056a05bc9
        List<Image> homeBannerList  =   this.getListImageIndex("e43cb722-75d6-11e4-b6ce-005056a05bc9","homeBannerList");
		//热门关键字待确认 需求是否后台维护
        
        
        //图片推荐位管理查询数据 向文琪提供 1,2,3
        List<Image> homeRecomOneList  =   this.getListImageIndex("13173f79-75da-11e4-b6ce-005056a05bc9","homeRecomOneList");
        List<Image> homeRecomTwoList  =   this.getListImageIndex("13173f79-75da-11e4-b6ce-005056a05bc9","homeRecomTwoList");
        List<Image> homeRecomThreeList  =   this.getListImageIndex("13173f79-75da-11e4-b6ce-005056a05bc9","homeRecomThreeList");
        
        
        
        
        String   avalible  = request.getParameter("avalible");
        
        //动态列表查询3个数据  叶宇提供接口  13198b8d-75da-11e4-b6ce-005056a05bc9
        
         List<Content> dyDataList  = indexManagerService.getListContent("13198b8d-75da-11e4-b6ce-005056a05bc9","1");
        
        //帖子列表显示4个数据 叶宇提供接口
          List<Content> postList  = indexManagerService.getListContent("131c3973-75da-11e4-b6ce-005056a05bc9","1");
          
        //游西藏       更多>>  攻略&游记   4 叶宇提供接口
         List<Content> strageList  = indexManagerService.getListContent("131ea221-75da-11e4-b6ce-005056a05bc9","1");
            
        // 景点  接口 也是向文琪提供  
         List<Image> homeViewList  =   this.getListImageIndex("a3cd8d33-75d6-11e4-b6ce-005056a05bc9","homeViewList");
        
       //图说西藏  接口   暂时  没有  提供    向文琪 
         List<Image> homePictureList  =   this.getListImageIndex("a3cd8d33-75d6-11e4-b6ce-005056a05bc9","homePictureList");
        
        
        //视频专区 接口  叶宇  7
          List<Content>   viedioList  =  indexManagerService.getListContent("1327011a-75da-11e4-b6ce-005056a05bc9","1");
        
        //读西藏   接口 向文琪 
          //推荐第一个图片
          List<Image> homeReadList  =   this.getListImageIndex("a3cd8d33-75d6-11e4-b6ce-005056a05bc9","homeReadList");//
           //风俗
          List<Image> homeCustomList  =   this.getListImageIndex("a3cd8d33-75d6-11e4-b6ce-005056a05bc9","homeCustomList");//
          
          //历史
          List<Image> homeCultureList  =   this.getListImageIndex("a3cd8d33-75d6-11e4-b6ce-005056a05bc9","homeCultureList");//
          
          //宗教
          List<Image> homeReligionList  =   this.getListImageIndex("a3cd8d33-75d6-11e4-b6ce-005056a05bc9","homeReligionList");//
          //其他待 向文琪 提供
          
List<Image> homeOtherList  =   this.getListImageIndex("a3cd8d33-75d6-11e4-b6ce-005056a05bc9","homeOtherList");//
          
          modelMap.addAttribute("homeOtherList",homeOtherList);
          // 西藏文化传播  待   向文琪   接口
          
         //音乐  
          List<Content> list1  =  indexManagerService.getListContent("09c7cb0d-7e8c-11e4-b6ce-005056a05bc9","0");
          //小说
          List<Content> list2  =  indexManagerService.getListContent("45e0fd29-7e8c-11e4-b6ce-005056a05bc9","1");
         //游戏
          List<Content> list3  =  indexManagerService.getListContent("61d19785-7e8c-11e4-b6ce-005056a05bc9","1");
  
          List<Content> list4  =  indexManagerService.getListContent("611bs12785-7w2e8c-11e4-b6ce-005056a05bc9","1");
		
          
          modelMap.addAttribute("programaList", programaList);
          modelMap.addAttribute("destinationList", destinationList);
          modelMap.addAttribute("homeBannerList", homeBannerList);
         
          
           
          
          modelMap.addAttribute("homeRecomOneList", homeRecomOneList);
          modelMap.addAttribute("homeRecomTwoList", homeRecomTwoList);
          modelMap.addAttribute("homeRecomThreeList", homeRecomThreeList);

          this.getContentPVF(dyDataList);
          modelMap.addAttribute("dyDataList", dyDataList);
          this.getContentPVF(postList);
          modelMap.addAttribute("postList", postList);
          
          this.getContentPVF(strageList);
          modelMap.addAttribute("strageList", strageList);
          modelMap.addAttribute("homeViewList", homeViewList);
          modelMap.addAttribute("homePictureList", homePictureList);
          
          this.getContentPVF(viedioList);
          modelMap.addAttribute("viedioList", viedioList);
          
          modelMap.addAttribute("homeReadList", homeReadList);
          
         
          
          modelMap.addAttribute("homeCustomList", homeCustomList);
          modelMap.addAttribute("homeCultureList", homeCultureList);
          modelMap.addAttribute("homeReligionList", homeReligionList);
          
          this.getContentPVF(list1);
          this.getContentPVF(list2);
          this.getContentPVF(list3);
          modelMap.addAttribute("list1", list1);
          modelMap.addAttribute("list2", list2);
          modelMap.addAttribute("list3", list3);
          List<Image> homeWindowList  =  this.getListImageIndex("a3cd8d33-75d6-11e4-b6ce-005056a05bc9","homeWindowList");//;
          modelMap.addAttribute("homeWindowList", homeWindowList);
          
          
		
		return "portal//app//index";
	}
	
	
	/**
	 *  前台首页数据的  //小说  动态 表 预览的 展示 	// 西藏文化传播  待   向文琪   接口  ////小说
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 */
	@RequestMapping("indexxs")
	public String indexxs(HttpServletRequest request,HttpServletResponse response,ModelMap modelMap){
		blist  =  imageServiceIml.getHomeImg();
		//展示导航数据 接口 xiaozheng 提供
	    // 前台首页栏目code   e8876fe6-7608-11e4-b6ce-005056a05bc9
		List<Programa> programaList  = programaServiceIml.getSendPrograma("e8876fe6-7608-11e4-b6ce-005056a05bc9") ;
		
		// 首页 目的地接口   向文琪提供
		
        List<Destination>   destinationList  =   destinationServiceIml.getAllDestination();
		
		
		// 首页banner 显示
        Image img = null;
        //首页栏目   a3cd8d33-75d6-11e4-b6ce-005056a05bc9
        List<Image> homeBannerList  =   this.getListImageIndex("e43cb722-75d6-11e4-b6ce-005056a05bc9","homeBannerList");
		//热门关键字待确认 需求是否后台维护
        
        
        //图片推荐位管理查询数据 向文琪提供 1,2,3
        List<Image> homeRecomOneList  =   this.getListImageIndex("13173f79-75da-11e4-b6ce-005056a05bc9","homeRecomOneList");
        List<Image> homeRecomTwoList  =   this.getListImageIndex("13173f79-75da-11e4-b6ce-005056a05bc9","homeRecomTwoList");
        List<Image> homeRecomThreeList  =   this.getListImageIndex("13173f79-75da-11e4-b6ce-005056a05bc9","homeRecomThreeList");
        
        
        
        
        String   avalible  = request.getParameter("avalible");
        
        //动态列表查询3个数据  叶宇提供接口  13198b8d-75da-11e4-b6ce-005056a05bc9
        
         List<Content> dyDataList  = indexManagerService.getListContent("13198b8d-75da-11e4-b6ce-005056a05bc9","1");
        
        //帖子列表显示4个数据 叶宇提供接口
          List<Content> postList  = indexManagerService.getListContent("131c3973-75da-11e4-b6ce-005056a05bc9","1");
          
        //游西藏       更多>>  攻略&游记   4 叶宇提供接口
         List<Content> strageList  = indexManagerService.getListContent("131ea221-75da-11e4-b6ce-005056a05bc9","1");
            
        // 景点  接口 也是向文琪提供  
         List<Image> homeViewList  =   this.getListImageIndex("a3cd8d33-75d6-11e4-b6ce-005056a05bc9","homeViewList");
        
       //图说西藏  接口   暂时  没有  提供    向文琪 
         List<Image> homePictureList  =   this.getListImageIndex("a3cd8d33-75d6-11e4-b6ce-005056a05bc9","homePictureList");
        
        
        //视频专区 接口  叶宇  7
          List<Content>   viedioList  =  indexManagerService.getListContent("1327011a-75da-11e4-b6ce-005056a05bc9","1");
        
        //读西藏   接口 向文琪 
          //推荐第一个图片
          List<Image> homeReadList  =   this.getListImageIndex("a3cd8d33-75d6-11e4-b6ce-005056a05bc9","homeReadList");//
           //风俗
          List<Image> homeCustomList  =   this.getListImageIndex("a3cd8d33-75d6-11e4-b6ce-005056a05bc9","homeCustomList");//
          
          //历史
          List<Image> homeCultureList  =   this.getListImageIndex("a3cd8d33-75d6-11e4-b6ce-005056a05bc9","homeCultureList");//
          
          //宗教
          List<Image> homeReligionList  =   this.getListImageIndex("a3cd8d33-75d6-11e4-b6ce-005056a05bc9","homeReligionList");//
          //其他待 向文琪 提供
          
          
          // 西藏文化传播  待   向文琪   接口
          
         //音乐  
          List<Content> list1  =  indexManagerService.getListContent("09c7cb0d-7e8c-11e4-b6ce-005056a05bc9","1");
          //小说
          List<Content> list2  =  indexManagerService.getListContent("45e0fd29-7e8c-11e4-b6ce-005056a05bc9","0");
         //游戏
          List<Content> list3  =  indexManagerService.getListContent("61d19785-7e8c-11e4-b6ce-005056a05bc9","1");
          List<Content> list4  =  indexManagerService.getListContent("611bs12785-7w2e8c-11e4-b6ce-005056a05bc9","1");
		
		
          
          modelMap.addAttribute("programaList", programaList);
          modelMap.addAttribute("destinationList", destinationList);
          modelMap.addAttribute("homeBannerList", homeBannerList);
         
         
          
           
          
          modelMap.addAttribute("homeRecomOneList", homeRecomOneList);
          modelMap.addAttribute("homeRecomTwoList", homeRecomTwoList);
          modelMap.addAttribute("homeRecomThreeList", homeRecomThreeList);

          this.getContentPVF(dyDataList);
          modelMap.addAttribute("dyDataList", dyDataList);
          this.getContentPVF(postList);
          modelMap.addAttribute("postList", postList);
          
          this.getContentPVF(strageList);
          modelMap.addAttribute("strageList", strageList);
          modelMap.addAttribute("homeViewList", homeViewList);
          modelMap.addAttribute("homePictureList", homePictureList);
          
          this.getContentPVF(viedioList);
          modelMap.addAttribute("viedioList", viedioList);
          
          modelMap.addAttribute("homeReadList", homeReadList);
          
         
          
          modelMap.addAttribute("homeCustomList", homeCustomList);
          modelMap.addAttribute("homeCultureList", homeCultureList);
          modelMap.addAttribute("homeReligionList", homeReligionList);
          
          this.getContentPVF(list1);
          this.getContentPVF(list2);
          this.getContentPVF(list3);
          modelMap.addAttribute("list1", list1);
          modelMap.addAttribute("list2", list2);
          modelMap.addAttribute("list3", list3);
          List<Image> homeWindowList  =  this.getListImageIndex("a3cd8d33-75d6-11e4-b6ce-005056a05bc9","homeWindowList");//;
          modelMap.addAttribute("homeWindowList", homeWindowList);
          
          
		
		return "portal//app//index";
	}
	
	/**
	 *  前台首页数据的  //游戏  动态 表 预览的 展示 	// 西藏文化传播  待   向文琪   接口  //  //游戏
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 */
	@RequestMapping("indexyx")
	public String indexyx(HttpServletRequest request,HttpServletResponse response,ModelMap modelMap){
		blist  =  imageServiceIml.getHomeImg();
		//展示导航数据 接口 xiaozheng 提供
	    // 前台首页栏目code   e8876fe6-7608-11e4-b6ce-005056a05bc9
		List<Programa> programaList  = programaServiceIml.getSendPrograma("e8876fe6-7608-11e4-b6ce-005056a05bc9") ;
		
		// 首页 目的地接口   向文琪提供
		
        List<Destination>   destinationList  =   destinationServiceIml.getAllDestination();
		
		
		// 首页banner 显示
        Image img = null;
        //首页栏目   a3cd8d33-75d6-11e4-b6ce-005056a05bc9
        List<Image> homeBannerList  =   this.getListImageIndex("e43cb722-75d6-11e4-b6ce-005056a05bc9","homeBannerList");
		//热门关键字待确认 需求是否后台维护
        
        
        //图片推荐位管理查询数据 向文琪提供 1,2,3
        List<Image> homeRecomOneList  =   this.getListImageIndex("13173f79-75da-11e4-b6ce-005056a05bc9","homeRecomOneList");
        List<Image> homeRecomTwoList  =   this.getListImageIndex("13173f79-75da-11e4-b6ce-005056a05bc9","homeRecomTwoList");
        List<Image> homeRecomThreeList  =   this.getListImageIndex("13173f79-75da-11e4-b6ce-005056a05bc9","homeRecomThreeList");
        
        
        
        
        String   avalible  = request.getParameter("avalible");
        
        //动态列表查询3个数据  叶宇提供接口  13198b8d-75da-11e4-b6ce-005056a05bc9
        
         List<Content> dyDataList  = indexManagerService.getListContent("13198b8d-75da-11e4-b6ce-005056a05bc9","1");
        
        //帖子列表显示4个数据 叶宇提供接口
          List<Content> postList  = indexManagerService.getListContent("131c3973-75da-11e4-b6ce-005056a05bc9","1");
          
        //游西藏       更多>>  攻略&游记   4 叶宇提供接口
         List<Content> strageList  = indexManagerService.getListContent("131ea221-75da-11e4-b6ce-005056a05bc9","1");
            
        // 景点  接口 也是向文琪提供  
         List<Image> homeViewList  =   this.getListImageIndex("a3cd8d33-75d6-11e4-b6ce-005056a05bc9","homeViewList");
        
       //图说西藏  接口   暂时  没有  提供    向文琪 
         List<Image> homePictureList  =   this.getListImageIndex("a3cd8d33-75d6-11e4-b6ce-005056a05bc9","homePictureList");
        
        
        //视频专区 接口  叶宇  7
          List<Content>   viedioList  =  indexManagerService.getListContent("1327011a-75da-11e4-b6ce-005056a05bc9","1");
        
        //读西藏   接口 向文琪 
          //推荐第一个图片
          List<Image> homeReadList  =   this.getListImageIndex("a3cd8d33-75d6-11e4-b6ce-005056a05bc9","homeReadList");//
           //风俗
          List<Image> homeCustomList  =   this.getListImageIndex("a3cd8d33-75d6-11e4-b6ce-005056a05bc9","homeCustomList");//
          
          //历史
          List<Image> homeCultureList  =   this.getListImageIndex("a3cd8d33-75d6-11e4-b6ce-005056a05bc9","homeCultureList");//
          
          //宗教
          List<Image> homeReligionList  =   this.getListImageIndex("a3cd8d33-75d6-11e4-b6ce-005056a05bc9","homeReligionList");//
          //其他待 向文琪 提供
          
          List<Image> homeOtherList  =   this.getListImageIndex("a3cd8d33-75d6-11e4-b6ce-005056a05bc9","homeOtherList");//
          
          modelMap.addAttribute("homeOtherList",homeOtherList);
          // 西藏文化传播  待   向文琪   接口
          
         //音乐  
          List<Content> list1  =  indexManagerService.getListContent("09c7cb0d-7e8c-11e4-b6ce-005056a05bc9","1");
          //小说
          List<Content> list2  =  indexManagerService.getListContent("45e0fd29-7e8c-11e4-b6ce-005056a05bc9","1");
         //游戏
          List<Content> list3  =  indexManagerService.getListContent("61d19785-7e8c-11e4-b6ce-005056a05bc9","0");
  
          List<Content> list4  =  indexManagerService.getListContent("611bs12785-7w2e8c-11e4-b6ce-005056a05bc9","1");
		
          
          modelMap.addAttribute("programaList", programaList);
          modelMap.addAttribute("destinationList", destinationList);
          modelMap.addAttribute("homeBannerList", homeBannerList);
         
         
          
           
          
          modelMap.addAttribute("homeRecomOneList", homeRecomOneList);
          modelMap.addAttribute("homeRecomTwoList", homeRecomTwoList);
          modelMap.addAttribute("homeRecomThreeList", homeRecomThreeList);

          this.getContentPVF(dyDataList);
          modelMap.addAttribute("dyDataList", dyDataList);
          this.getContentPVF(postList);
          modelMap.addAttribute("postList", postList);
          
          this.getContentPVF(strageList);
          modelMap.addAttribute("strageList", strageList);
          modelMap.addAttribute("homeViewList", homeViewList);
          modelMap.addAttribute("homePictureList", homePictureList);
          
          this.getContentPVF(viedioList);
          modelMap.addAttribute("viedioList", viedioList);
          
          modelMap.addAttribute("homeReadList", homeReadList);
          
         
          
          modelMap.addAttribute("homeCustomList", homeCustomList);
          modelMap.addAttribute("homeCultureList", homeCultureList);
          modelMap.addAttribute("homeReligionList", homeReligionList);
          
          this.getContentPVF(list1);
          this.getContentPVF(list2);
          this.getContentPVF(list3);
          modelMap.addAttribute("list1", list1);
          modelMap.addAttribute("list2", list2);
          modelMap.addAttribute("list3", list3);
          
          
          List<Image> homeWindowList  =  this.getListImageIndex("a3cd8d33-75d6-11e4-b6ce-005056a05bc9","homeWindowList");//;
          modelMap.addAttribute("homeWindowList", homeWindowList);
          HomeController.listImage  = new ArrayList<Image>();
		return "portal//app//index";
	}
	
	
	/**
	 *  前台首页数据的  其他  动态 表 预览的 展示 	// 西藏文化传播  待   向文琪   接口  //  //游戏
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 */
	@RequestMapping("indexother")
	public String indeother(HttpServletRequest request,HttpServletResponse response,ModelMap modelMap){
		blist  =  imageServiceIml.getHomeImg();
		//展示导航数据 接口 xiaozheng 提供
	    // 前台首页栏目code   e8876fe6-7608-11e4-b6ce-005056a05bc9
		List<Programa> programaList  = programaServiceIml.getSendPrograma("e8876fe6-7608-11e4-b6ce-005056a05bc9") ;
		
		// 首页 目的地接口   向文琪提供
		
        List<Destination>   destinationList  =   destinationServiceIml.getAllDestination();
		
		// 首页banner 显示
        Image img = null;
        //首页栏目   a3cd8d33-75d6-11e4-b6ce-005056a05bc9
        List<Image> homeBannerList  =   this.getListImageIndex("e43cb722-75d6-11e4-b6ce-005056a05bc9","homeBannerList");
		//热门关键字待确认 需求是否后台维护
        
        
        //图片推荐位管理查询数据 向文琪提供 1,2,3
        List<Image> homeRecomOneList  =   this.getListImageIndex("13173f79-75da-11e4-b6ce-005056a05bc9","homeRecomOneList");
        List<Image> homeRecomTwoList  =   this.getListImageIndex("13173f79-75da-11e4-b6ce-005056a05bc9","homeRecomTwoList");
        List<Image> homeRecomThreeList  =   this.getListImageIndex("13173f79-75da-11e4-b6ce-005056a05bc9","homeRecomThreeList");
        
        
        
        
        String   avalible  = request.getParameter("avalible");
        
        //动态列表查询3个数据  叶宇提供接口  13198b8d-75da-11e4-b6ce-005056a05bc9
        
         List<Content> dyDataList  = indexManagerService.getListContent("13198b8d-75da-11e4-b6ce-005056a05bc9","1");
        
        //帖子列表显示4个数据 叶宇提供接口
          List<Content> postList  = indexManagerService.getListContent("131c3973-75da-11e4-b6ce-005056a05bc9","1");
          
        //游西藏       更多>>  攻略&游记   4 叶宇提供接口
         List<Content> strageList  = indexManagerService.getListContent("131ea221-75da-11e4-b6ce-005056a05bc9","1");
            
        // 景点  接口 也是向文琪提供  
         List<Image> homeViewList  =   this.getListImageIndex("a3cd8d33-75d6-11e4-b6ce-005056a05bc9","homeViewList");
        
       //图说西藏  接口   暂时  没有  提供    向文琪 
         List<Image> homePictureList  =   this.getListImageIndex("a3cd8d33-75d6-11e4-b6ce-005056a05bc9","homePictureList");
        
        
        //视频专区 接口  叶宇  7
          List<Content>   viedioList  =  indexManagerService.getListContent("1327011a-75da-11e4-b6ce-005056a05bc9","1");
        
        //读西藏   接口 向文琪 
          //推荐第一个图片
          List<Image> homeReadList  =   this.getListImageIndex("a3cd8d33-75d6-11e4-b6ce-005056a05bc9","homeReadList");//
           //风俗
          List<Image> homeCustomList  =   this.getListImageIndex("a3cd8d33-75d6-11e4-b6ce-005056a05bc9","homeCustomList");//
          
          //历史
          List<Image> homeCultureList  =   this.getListImageIndex("a3cd8d33-75d6-11e4-b6ce-005056a05bc9","homeCultureList");//
          
          //宗教
          List<Image> homeReligionList  =   this.getListImageIndex("a3cd8d33-75d6-11e4-b6ce-005056a05bc9","homeReligionList");//
          //其他待 向文琪 提供
          
List<Image> homeOtherList  =   this.getListImageIndex("a3cd8d33-75d6-11e4-b6ce-005056a05bc9","homeOtherList");//
          
          modelMap.addAttribute("homeOtherList",homeOtherList);
          // 西藏文化传播  待   向文琪   接口
          
         //音乐  
          List<Content> list1  =  indexManagerService.getListContent("09c7cb0d-7e8c-11e4-b6ce-005056a05bc9","1");
          //小说
          List<Content> list2  =  indexManagerService.getListContent("45e0fd29-7e8c-11e4-b6ce-005056a05bc9","1");
         //游戏
          List<Content> list3  =  indexManagerService.getListContent("61d19785-7e8c-11e4-b6ce-005056a05bc9","1");
          
          List<Content> list4  =  indexManagerService.getListContent("611bs12785-7w2e8c-11e4-b6ce-005056a05bc9","0");
  
		
		
          
          modelMap.addAttribute("programaList", programaList);
          modelMap.addAttribute("destinationList", destinationList);
          modelMap.addAttribute("homeBannerList", homeBannerList);
         
         
          
           
          
          modelMap.addAttribute("homeRecomOneList", homeRecomOneList);
          modelMap.addAttribute("homeRecomTwoList", homeRecomTwoList);
          modelMap.addAttribute("homeRecomThreeList", homeRecomThreeList);

          this.getContentPVF(dyDataList);
          modelMap.addAttribute("dyDataList", dyDataList);
          this.getContentPVF(postList);
          modelMap.addAttribute("postList", postList);
          
          this.getContentPVF(strageList);
          modelMap.addAttribute("strageList", strageList);
          modelMap.addAttribute("homeViewList", homeViewList);
          modelMap.addAttribute("homePictureList", homePictureList);
          
          this.getContentPVF(viedioList);
          modelMap.addAttribute("viedioList", viedioList);
          
          modelMap.addAttribute("homeReadList", homeReadList);
          
         
          
          modelMap.addAttribute("homeCustomList", homeCustomList);
          modelMap.addAttribute("homeCultureList", homeCultureList);
          modelMap.addAttribute("homeReligionList", homeReligionList);
          
          this.getContentPVF(list1);
          this.getContentPVF(list2);
          this.getContentPVF(list3);
          modelMap.addAttribute("list1", list1);
          modelMap.addAttribute("list2", list2);
          modelMap.addAttribute("list3", list3);
          
          
          List<Image> homeWindowList  =  this.getListImageIndex("a3cd8d33-75d6-11e4-b6ce-005056a05bc9","homeWindowList");//;
          modelMap.addAttribute("homeWindowList", homeWindowList);
          HomeController.listImage  = new ArrayList<Image>();
		return "portal//app//index";
	}
	
	/**
	 *  前台首页banner 预览
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 */
	@RequestMapping("indexbanner")
	public String indexbanner(HttpServletRequest request,HttpServletResponse response,ModelMap modelMap){
		blist  =  imageServiceIml.getHomeImg();
		//展示导航数据 接口 xiaozheng 提供
	    // 前台首页栏目code   e8876fe6-7608-11e4-b6ce-005056a05bc9
		List<Programa> programaList  = programaServiceIml.getSendPrograma("e8876fe6-7608-11e4-b6ce-005056a05bc9") ;
		
		// 首页 目的地接口   向文琪提供
		
        List<Destination>   destinationList  =   destinationServiceIml.getAllDestination();
		
		
		// 首页banner 显示
        Image img = null;
        //首页栏目   a3cd8d33-75d6-11e4-b6ce-005056a05bc9
        List<Image> homeBannerList  =   HomeController.listImage;
		//热门关键字待确认 需求是否后台维护
        
        
        //图片推荐位管理查询数据 向文琪提供 1,2,3
        List<Image> homeRecomOneList  =   this.getListImageIndex("13173f79-75da-11e4-b6ce-005056a05bc9","homeRecomOneList");
        List<Image> homeRecomTwoList  =   this.getListImageIndex("13173f79-75da-11e4-b6ce-005056a05bc9","homeRecomTwoList");
        List<Image> homeRecomThreeList  =   this.getListImageIndex("13173f79-75da-11e4-b6ce-005056a05bc9","homeRecomThreeList");
        
        
        
        
        String   avalible  = request.getParameter("avalible");
        
        //动态列表查询3个数据  叶宇提供接口  13198b8d-75da-11e4-b6ce-005056a05bc9
        
         List<Content> dyDataList  = indexManagerService.getListContent("13198b8d-75da-11e4-b6ce-005056a05bc9","1");
        
        //帖子列表显示4个数据 叶宇提供接口
          List<Content> postList  = indexManagerService.getListContent("131c3973-75da-11e4-b6ce-005056a05bc9","1");
          
        //游西藏       更多>>  攻略&游记   4 叶宇提供接口
         List<Content> strageList  = indexManagerService.getListContent("131ea221-75da-11e4-b6ce-005056a05bc9","1");
            
        // 景点  接口 也是向文琪提供  
         List<Image> homeViewList  =   this.getListImageIndex("a3cd8d33-75d6-11e4-b6ce-005056a05bc9","homeViewList");
        
       //图说西藏  接口   暂时  没有  提供    向文琪 
         List<Image> homePictureList  =   this.getListImageIndex("a3cd8d33-75d6-11e4-b6ce-005056a05bc9","homePictureList");
        
        
        //视频专区 接口  叶宇  7
          List<Content>   viedioList  =  indexManagerService.getListContent("1327011a-75da-11e4-b6ce-005056a05bc9","1");
        
        //读西藏   接口 向文琪 
          //推荐第一个图片
          List<Image> homeReadList  =   this.getListImageIndex("a3cd8d33-75d6-11e4-b6ce-005056a05bc9","homeReadList");//
           //风俗
          List<Image> homeCustomList  =   this.getListImageIndex("a3cd8d33-75d6-11e4-b6ce-005056a05bc9","homeCustomList");//
          
          //历史
          List<Image> homeCultureList  =   this.getListImageIndex("a3cd8d33-75d6-11e4-b6ce-005056a05bc9","homeCultureList");//
          
          //宗教
          List<Image> homeReligionList  =   this.getListImageIndex("a3cd8d33-75d6-11e4-b6ce-005056a05bc9","homeReligionList");//
          //其他待 向文琪 提供
          
          List<Image> homeOtherList  =   this.getListImageIndex("a3cd8d33-75d6-11e4-b6ce-005056a05bc9","homeOtherList");//
          
          modelMap.addAttribute("homeOtherList",homeOtherList);
          // 西藏文化传播  待   向文琪   接口
          
         //音乐  
          List<Content> list1  =  indexManagerService.getListContent("09c7cb0d-7e8c-11e4-b6ce-005056a05bc9","1");
          //小说
          List<Content> list2  =  indexManagerService.getListContent("45e0fd29-7e8c-11e4-b6ce-005056a05bc9","1");
         //游戏
          List<Content> list3  =  indexManagerService.getListContent("61d19785-7e8c-11e4-b6ce-005056a05bc9","1");
  
          List<Content> list4  =  indexManagerService.getListContent("611bs12785-7w2e8c-11e4-b6ce-005056a05bc9","1");
		
          
          modelMap.addAttribute("programaList", programaList);
          modelMap.addAttribute("destinationList", destinationList);
          modelMap.addAttribute("homeBannerList", homeBannerList);
        
          
           
          
          modelMap.addAttribute("homeRecomOneList", homeRecomOneList);
          modelMap.addAttribute("homeRecomTwoList", homeRecomTwoList);
          modelMap.addAttribute("homeRecomThreeList", homeRecomThreeList);

          this.getContentPVF(dyDataList);
          modelMap.addAttribute("dyDataList", dyDataList);
          this.getContentPVF(postList);
          modelMap.addAttribute("postList", postList);
          
          this.getContentPVF(strageList);
          modelMap.addAttribute("strageList", strageList);
          modelMap.addAttribute("homeViewList", homeViewList);
          modelMap.addAttribute("homePictureList", homePictureList);
          
          this.getContentPVF(viedioList);
          modelMap.addAttribute("viedioList", viedioList);
          
          modelMap.addAttribute("homeReadList", homeReadList);
          
        
          
          modelMap.addAttribute("homeCustomList", homeCustomList);
          modelMap.addAttribute("homeCultureList", homeCultureList);
          modelMap.addAttribute("homeReligionList", homeReligionList);
          
          this.getContentPVF(list1);
          this.getContentPVF(list2);
          this.getContentPVF(list3);
          modelMap.addAttribute("list1", list1);
          modelMap.addAttribute("list2", list2);
          modelMap.addAttribute("list3", list3);
          
          
          List<Image> homeWindowList  =  this.getListImageIndex("a3cd8d33-75d6-11e4-b6ce-005056a05bc9","homeWindowList");//;
          modelMap.addAttribute("homeWindowList", homeWindowList);
          HomeController.listImage  = new ArrayList<Image>();
		return "portal//app//index";
	}
	
	
	
	
	
	/**
	 *  推荐1
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 */
	@RequestMapping("indexRecommedone")
	public String indexRecommedone(HttpServletRequest request,HttpServletResponse response,ModelMap modelMap){
		blist  =  imageServiceIml.getHomeImg();
		//展示导航数据 接口 xiaozheng 提供
	    // 前台首页栏目code   e8876fe6-7608-11e4-b6ce-005056a05bc9
		List<Programa> programaList  = programaServiceIml.getSendPrograma("e8876fe6-7608-11e4-b6ce-005056a05bc9") ;
		
		// 首页 目的地接口   向文琪提供
		
        List<Destination>   destinationList  =   destinationServiceIml.getAllDestination();
		
		
		// 首页banner 显示
        Image img = null;
        //首页栏目   a3cd8d33-75d6-11e4-b6ce-005056a05bc9
        List<Image> homeBannerList  =   this.getListImageIndex("e43cb722-75d6-11e4-b6ce-005056a05bc9","homeBannerList");
		//热门关键字待确认 需求是否后台维护
        
        
        //图片推荐位管理查询数据 向文琪提供 1,2,3
        List<Image> homeRecomOneList  =   HomeController.listImage;
        List<Image> homeRecomTwoList  =   this.getListImageIndex("13173f79-75da-11e4-b6ce-005056a05bc9","homeRecomTwoList");
        List<Image> homeRecomThreeList  =   this.getListImageIndex("13173f79-75da-11e4-b6ce-005056a05bc9","homeRecomThreeList");
        
        
        
        
        String   avalible  = request.getParameter("avalible");
        
        //动态列表查询3个数据  叶宇提供接口  13198b8d-75da-11e4-b6ce-005056a05bc9
        
         List<Content> dyDataList  = indexManagerService.getListContent("13198b8d-75da-11e4-b6ce-005056a05bc9","1");
        
        //帖子列表显示4个数据 叶宇提供接口
          List<Content> postList  = indexManagerService.getListContent("131c3973-75da-11e4-b6ce-005056a05bc9","1");
          
        //游西藏       更多>>  攻略&游记   4 叶宇提供接口
         List<Content> strageList  = indexManagerService.getListContent("131ea221-75da-11e4-b6ce-005056a05bc9","1");
            
        // 景点  接口 也是向文琪提供  
         List<Image> homeViewList  =   this.getListImageIndex("a3cd8d33-75d6-11e4-b6ce-005056a05bc9","homeViewList");
        
       //图说西藏  接口   暂时  没有  提供    向文琪 
         List<Image> homePictureList  =   this.getListImageIndex("a3cd8d33-75d6-11e4-b6ce-005056a05bc9","homePictureList");
        
        
        //视频专区 接口  叶宇  7
          List<Content>   viedioList  =  indexManagerService.getListContent("1327011a-75da-11e4-b6ce-005056a05bc9","1");
        
        //读西藏   接口 向文琪 
          //推荐第一个图片
          List<Image> homeReadList  =   this.getListImageIndex("a3cd8d33-75d6-11e4-b6ce-005056a05bc9","homeReadList");//
           //风俗
          List<Image> homeCustomList  =   this.getListImageIndex("a3cd8d33-75d6-11e4-b6ce-005056a05bc9","homeCustomList");//
          
          //历史
          List<Image> homeCultureList  =   this.getListImageIndex("a3cd8d33-75d6-11e4-b6ce-005056a05bc9","homeCultureList");//
          
          //宗教
          List<Image> homeReligionList  =   this.getListImageIndex("a3cd8d33-75d6-11e4-b6ce-005056a05bc9","homeReligionList");//
          //其他待 向文琪 提供
List<Image> homeOtherList  =   this.getListImageIndex("a3cd8d33-75d6-11e4-b6ce-005056a05bc9","homeOtherList");//
          
          modelMap.addAttribute("homeOtherList",homeOtherList);
          
          // 西藏文化传播  待   向文琪   接口
          
         //音乐  
          List<Content> list1  =  indexManagerService.getListContent("09c7cb0d-7e8c-11e4-b6ce-005056a05bc9","1");
          //小说
          List<Content> list2  =  indexManagerService.getListContent("45e0fd29-7e8c-11e4-b6ce-005056a05bc9","1");
         //游戏
          List<Content> list3  =  indexManagerService.getListContent("61d19785-7e8c-11e4-b6ce-005056a05bc9","1");
  
          List<Content> list4  =  indexManagerService.getListContent("611bs12785-7w2e8c-11e4-b6ce-005056a05bc9","1");
		
          
          modelMap.addAttribute("programaList", programaList);
          modelMap.addAttribute("destinationList", destinationList);
          modelMap.addAttribute("homeBannerList", homeBannerList);
         
         
          
           
          
          modelMap.addAttribute("homeRecomOneList", homeRecomOneList);
          modelMap.addAttribute("homeRecomTwoList", homeRecomTwoList);
          modelMap.addAttribute("homeRecomThreeList", homeRecomThreeList);

          this.getContentPVF(dyDataList);
          modelMap.addAttribute("dyDataList", dyDataList);
          this.getContentPVF(postList);
          modelMap.addAttribute("postList", postList);
          
          this.getContentPVF(strageList);
          modelMap.addAttribute("strageList", strageList);
          modelMap.addAttribute("homeViewList", homeViewList);
          modelMap.addAttribute("homePictureList", homePictureList);
          
          this.getContentPVF(viedioList);
          modelMap.addAttribute("viedioList", viedioList);
          
          modelMap.addAttribute("homeReadList", homeReadList);
          
         
          
          modelMap.addAttribute("homeCustomList", homeCustomList);
          modelMap.addAttribute("homeCultureList", homeCultureList);
          modelMap.addAttribute("homeReligionList", homeReligionList);
          
          this.getContentPVF(list1);
          this.getContentPVF(list2);
          this.getContentPVF(list3);
          modelMap.addAttribute("list1", list1);
          modelMap.addAttribute("list2", list2);
          modelMap.addAttribute("list3", list3);
          List<Image> homeWindowList  =  this.getListImageIndex("a3cd8d33-75d6-11e4-b6ce-005056a05bc9","homeWindowList");//;
          modelMap.addAttribute("homeWindowList", homeWindowList);
          HomeController.listImage  = new ArrayList<Image>();
          
		
		return "portal//app//index";
	}
	
	
	/**
	 *  推荐2 预览
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 */
	@RequestMapping("indexRecommedtwo")
	public String indexRecommedtwo(HttpServletRequest request,HttpServletResponse response,ModelMap modelMap){
		blist  =  imageServiceIml.getHomeImg();
		//展示导航数据 接口 xiaozheng 提供
	    // 前台首页栏目code   e8876fe6-7608-11e4-b6ce-005056a05bc9
		List<Programa> programaList  = programaServiceIml.getSendPrograma("e8876fe6-7608-11e4-b6ce-005056a05bc9") ;
		
		// 首页 目的地接口   向文琪提供
		
        List<Destination>   destinationList  =   destinationServiceIml.getAllDestination();
		
		
		// 首页banner 显示
        Image img = null;
        //首页栏目   a3cd8d33-75d6-11e4-b6ce-005056a05bc9
        List<Image> homeBannerList  =   this.getListImageIndex("e43cb722-75d6-11e4-b6ce-005056a05bc9","homeBannerList");
		//热门关键字待确认 需求是否后台维护
        
        
        //图片推荐位管理查询数据 向文琪提供 1,2,3
        List<Image> homeRecomOneList  =   this.getListImageIndex("13173f79-75da-11e4-b6ce-005056a05bc9","homeRecomOneList");
        List<Image> homeRecomTwoList  =    HomeController.listImage;
        List<Image> homeRecomThreeList  =   this.getListImageIndex("13173f79-75da-11e4-b6ce-005056a05bc9","homeRecomThreeList");
        
        
        
        
        String   avalible  = request.getParameter("avalible");
        
        //动态列表查询3个数据  叶宇提供接口  13198b8d-75da-11e4-b6ce-005056a05bc9
        
         List<Content> dyDataList  = indexManagerService.getListContent("13198b8d-75da-11e4-b6ce-005056a05bc9","1");
        
        //帖子列表显示4个数据 叶宇提供接口
          List<Content> postList  = indexManagerService.getListContent("131c3973-75da-11e4-b6ce-005056a05bc9","1");
          
        //游西藏       更多>>  攻略&游记   4 叶宇提供接口
         List<Content> strageList  = indexManagerService.getListContent("131ea221-75da-11e4-b6ce-005056a05bc9","1");
            
        // 景点  接口 也是向文琪提供  
         List<Image> homeViewList  =   this.getListImageIndex("a3cd8d33-75d6-11e4-b6ce-005056a05bc9","homeViewList");
        
       //图说西藏  接口   暂时  没有  提供    向文琪 
         List<Image> homePictureList  =   this.getListImageIndex("a3cd8d33-75d6-11e4-b6ce-005056a05bc9","homePictureList");
        
        
        //视频专区 接口  叶宇  7
          List<Content>   viedioList  =  indexManagerService.getListContent("1327011a-75da-11e4-b6ce-005056a05bc9","1");
        
        //读西藏   接口 向文琪 
          //推荐第一个图片
          List<Image> homeReadList  =   this.getListImageIndex("a3cd8d33-75d6-11e4-b6ce-005056a05bc9","homeReadList");//
           //风俗
          List<Image> homeCustomList  =   this.getListImageIndex("a3cd8d33-75d6-11e4-b6ce-005056a05bc9","homeCustomList");//
          
          //历史
          List<Image> homeCultureList  =   this.getListImageIndex("a3cd8d33-75d6-11e4-b6ce-005056a05bc9","homeCultureList");//
          
          //宗教
          List<Image> homeReligionList  =   this.getListImageIndex("a3cd8d33-75d6-11e4-b6ce-005056a05bc9","homeReligionList");//
          //其他待 向文琪 提供
          List<Image> homeOtherList  =   this.getListImageIndex("a3cd8d33-75d6-11e4-b6ce-005056a05bc9","homeOtherList");//
          
          modelMap.addAttribute("homeOtherList",homeOtherList);
          
          // 西藏文化传播  待   向文琪   接口
          
         //音乐  
          List<Content> list1  =  indexManagerService.getListContent("09c7cb0d-7e8c-11e4-b6ce-005056a05bc9","1");
          //小说
          List<Content> list2  =  indexManagerService.getListContent("45e0fd29-7e8c-11e4-b6ce-005056a05bc9","1");
         //游戏
          List<Content> list3  =  indexManagerService.getListContent("61d19785-7e8c-11e4-b6ce-005056a05bc9","1");
  
          List<Content> list4  =  indexManagerService.getListContent("611bs12785-7w2e8c-11e4-b6ce-005056a05bc9","1");
		
          
          modelMap.addAttribute("programaList", programaList);
          modelMap.addAttribute("destinationList", destinationList);
          modelMap.addAttribute("homeBannerList", homeBannerList);
         
           
          
          modelMap.addAttribute("homeRecomOneList", homeRecomOneList);
          modelMap.addAttribute("homeRecomTwoList", homeRecomTwoList);
          modelMap.addAttribute("homeRecomThreeList", homeRecomThreeList);

          this.getContentPVF(dyDataList);
          modelMap.addAttribute("dyDataList", dyDataList);
          this.getContentPVF(postList);
          modelMap.addAttribute("postList", postList);
          
          this.getContentPVF(strageList);
          modelMap.addAttribute("strageList", strageList);
          modelMap.addAttribute("homeViewList", homeViewList);
          modelMap.addAttribute("homePictureList", homePictureList);
          
          this.getContentPVF(viedioList);
          modelMap.addAttribute("viedioList", viedioList);
          
          modelMap.addAttribute("homeReadList", homeReadList);
          
       
          modelMap.addAttribute("homeCustomList", homeCustomList);
          modelMap.addAttribute("homeCultureList", homeCultureList);
          modelMap.addAttribute("homeReligionList", homeReligionList);
          
          this.getContentPVF(list1);
          this.getContentPVF(list2);
          this.getContentPVF(list3);
          modelMap.addAttribute("list1", list1);
          modelMap.addAttribute("list2", list2);
          modelMap.addAttribute("list3", list3);
          
          List<Image> homeWindowList  =  this.getListImageIndex("a3cd8d33-75d6-11e4-b6ce-005056a05bc9","homeWindowList");//;
          modelMap.addAttribute("homeWindowList", homeWindowList);
          HomeController.listImage  = new ArrayList<Image>();
		
		return "portal//app//index";
	}
	
	/**
	 *  推荐3 预览
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 */
	@RequestMapping("indexRecommedthree")
	public String indexRecommedthree(HttpServletRequest request,HttpServletResponse response,ModelMap modelMap){
		blist  =  imageServiceIml.getHomeImg();
		//展示导航数据 接口 xiaozheng 提供
	    // 前台首页栏目code   e8876fe6-7608-11e4-b6ce-005056a05bc9
		List<Programa> programaList  = programaServiceIml.getSendPrograma("e8876fe6-7608-11e4-b6ce-005056a05bc9") ;
		
		// 首页 目的地接口   向文琪提供
		
        List<Destination>   destinationList  =   destinationServiceIml.getAllDestination();
		
		
		// 首页banner 显示
        Image img = null;
        //首页栏目   a3cd8d33-75d6-11e4-b6ce-005056a05bc9
        List<Image> homeBannerList  =   this.getListImageIndex("e43cb722-75d6-11e4-b6ce-005056a05bc9","homeBannerList");
		//热门关键字待确认 需求是否后台维护
        
        
        //图片推荐位管理查询数据 向文琪提供 1,2,3
        List<Image> homeRecomOneList  =   this.getListImageIndex("13173f79-75da-11e4-b6ce-005056a05bc9","homeRecomOneList");
        List<Image> homeRecomTwoList  =   this.getListImageIndex("13173f79-75da-11e4-b6ce-005056a05bc9","homeRecomTwoList");


        List<Image> homeRecomThreeList  =   HomeController.listImage;
        
        
        
        
        String   avalible  = request.getParameter("avalible");
        
        //动态列表查询3个数据  叶宇提供接口  13198b8d-75da-11e4-b6ce-005056a05bc9
        
         List<Content> dyDataList  = indexManagerService.getListContent("13198b8d-75da-11e4-b6ce-005056a05bc9","1");
        
        //帖子列表显示4个数据 叶宇提供接口
          List<Content> postList  = indexManagerService.getListContent("131c3973-75da-11e4-b6ce-005056a05bc9","1");
          
        //游西藏       更多>>  攻略&游记   4 叶宇提供接口
         List<Content> strageList  = indexManagerService.getListContent("131ea221-75da-11e4-b6ce-005056a05bc9","1");
            
        // 景点  接口 也是向文琪提供  
         List<Image> homeViewList  =   this.getListImageIndex("a3cd8d33-75d6-11e4-b6ce-005056a05bc9","homeViewList");
        
       //图说西藏  接口   暂时  没有  提供    向文琪 
         List<Image> homePictureList  =   this.getListImageIndex("a3cd8d33-75d6-11e4-b6ce-005056a05bc9","homePictureList");
        
        
        //视频专区 接口  叶宇  7
          List<Content>   viedioList  =  indexManagerService.getListContent("1327011a-75da-11e4-b6ce-005056a05bc9","1");
        
        //读西藏   接口 向文琪 
          //推荐第一个图片
          List<Image> homeReadList  =   this.getListImageIndex("a3cd8d33-75d6-11e4-b6ce-005056a05bc9","homeReadList");//
           //风俗
          List<Image> homeCustomList  =   this.getListImageIndex("a3cd8d33-75d6-11e4-b6ce-005056a05bc9","homeCustomList");//
          
          //历史
          List<Image> homeCultureList  =   this.getListImageIndex("a3cd8d33-75d6-11e4-b6ce-005056a05bc9","homeCultureList");//
          
          //宗教
          List<Image> homeReligionList  =   this.getListImageIndex("a3cd8d33-75d6-11e4-b6ce-005056a05bc9","homeReligionList");//
          //其他待 向文琪 提供
          
List<Image> homeOtherList  =   this.getListImageIndex("a3cd8d33-75d6-11e4-b6ce-005056a05bc9","homeOtherList");//
          
          modelMap.addAttribute("homeOtherList",homeOtherList);
          // 西藏文化传播  待   向文琪   接口
          
         //音乐  
          List<Content> list1  =  indexManagerService.getListContent("09c7cb0d-7e8c-11e4-b6ce-005056a05bc9","1");
          //小说
          List<Content> list2  =  indexManagerService.getListContent("45e0fd29-7e8c-11e4-b6ce-005056a05bc9","1");
         //游戏
          List<Content> list3  =  indexManagerService.getListContent("61d19785-7e8c-11e4-b6ce-005056a05bc9","1");
          List<Content> list4  =  indexManagerService.getListContent("611bs12785-7w2e8c-11e4-b6ce-005056a05bc9","1");
		
		
          
          modelMap.addAttribute("programaList", programaList);
          modelMap.addAttribute("destinationList", destinationList);
          modelMap.addAttribute("homeBannerList", homeBannerList);
         
         
           
          
          modelMap.addAttribute("homeRecomOneList", homeRecomOneList);
          modelMap.addAttribute("homeRecomTwoList", homeRecomTwoList);
          modelMap.addAttribute("homeRecomThreeList", homeRecomThreeList);

          this.getContentPVF(dyDataList);
          modelMap.addAttribute("dyDataList", dyDataList);
          this.getContentPVF(postList);
          modelMap.addAttribute("postList", postList);
          
          this.getContentPVF(strageList);
          modelMap.addAttribute("strageList", strageList);
          modelMap.addAttribute("homeViewList", homeViewList);
          modelMap.addAttribute("homePictureList", homePictureList);
          
          this.getContentPVF(viedioList);
          modelMap.addAttribute("viedioList", viedioList);
          
          modelMap.addAttribute("homeReadList", homeReadList);
          
         
          
          modelMap.addAttribute("homeCustomList", homeCustomList);
          modelMap.addAttribute("homeCultureList", homeCultureList);
          modelMap.addAttribute("homeReligionList", homeReligionList);
          
          this.getContentPVF(list1);
          this.getContentPVF(list2);
          this.getContentPVF(list3);
          modelMap.addAttribute("list1", list1);
          modelMap.addAttribute("list2", list2);
          modelMap.addAttribute("list3", list3);
          
          List<Image> homeWindowList  =  this.getListImageIndex("a3cd8d33-75d6-11e4-b6ce-005056a05bc9","homeWindowList");//;
          modelMap.addAttribute("homeWindowList", homeWindowList);
          HomeController.listImage  = new ArrayList<Image>();
		
		return "portal//app//index";
	}
	
	
	
	
	/**
	 *  景点 预览
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 */
	@RequestMapping("indexview")
	public String indexview(HttpServletRequest request,HttpServletResponse response,ModelMap modelMap){
		blist  =  imageServiceIml.getHomeImg();
		//展示导航数据 接口 xiaozheng 提供
	    // 前台首页栏目code   e8876fe6-7608-11e4-b6ce-005056a05bc9
		List<Programa> programaList  = programaServiceIml.getSendPrograma("e8876fe6-7608-11e4-b6ce-005056a05bc9") ;
		
		// 首页 目的地接口   向文琪提供
		
        List<Destination>   destinationList  =   destinationServiceIml.getAllDestination();
		
		
		// 首页banner 显示
        Image img = null;
        //首页栏目   a3cd8d33-75d6-11e4-b6ce-005056a05bc9
        List<Image> homeBannerList  =   this.getListImageIndex("e43cb722-75d6-11e4-b6ce-005056a05bc9","homeBannerList");
		//热门关键字待确认 需求是否后台维护
        
        
        //图片推荐位管理查询数据 向文琪提供 1,2,3
        List<Image> homeRecomOneList  =   this.getListImageIndex("13173f79-75da-11e4-b6ce-005056a05bc9","homeRecomOneList");
        List<Image> homeRecomTwoList  =   this.getListImageIndex("13173f79-75da-11e4-b6ce-005056a05bc9","homeRecomTwoList");


        List<Image> homeRecomThreeList  =   this.getListImageIndex("13173f79-75da-11e4-b6ce-005056a05bc9","homeRecomThreeList");

        
        
        
        
        String   avalible  = request.getParameter("avalible");
        
        //动态列表查询3个数据  叶宇提供接口  13198b8d-75da-11e4-b6ce-005056a05bc9
        
         List<Content> dyDataList  = indexManagerService.getListContent("13198b8d-75da-11e4-b6ce-005056a05bc9","1");
        
        //帖子列表显示4个数据 叶宇提供接口
          List<Content> postList  = indexManagerService.getListContent("131c3973-75da-11e4-b6ce-005056a05bc9","1");
          
        //游西藏       更多>>  攻略&游记   4 叶宇提供接口
         List<Content> strageList  = indexManagerService.getListContent("131ea221-75da-11e4-b6ce-005056a05bc9","1");
            
        // 景点  接口 也是向文琪提供  
         	//List<Image> homeViewList  =   this.getListImageIndex("a3cd8d33-75d6-11e4-b6ce-005056a05bc9","homeViewList");
         List<Image> homeViewList  =   HomeController.listImage;
        
       //图说西藏  接口   暂时  没有  提供    向文琪 
         List<Image> homePictureList  =   this.getListImageIndex("a3cd8d33-75d6-11e4-b6ce-005056a05bc9","homePictureList");
        
        
        //视频专区 接口  叶宇  7
          List<Content>   viedioList  =  indexManagerService.getListContent("1327011a-75da-11e4-b6ce-005056a05bc9","1");
        
        //读西藏   接口 向文琪 
          //推荐第一个图片
          List<Image> homeReadList  =   this.getListImageIndex("a3cd8d33-75d6-11e4-b6ce-005056a05bc9","homeReadList");//
           //风俗
          List<Image> homeCustomList  =   this.getListImageIndex("a3cd8d33-75d6-11e4-b6ce-005056a05bc9","homeCustomList");//
          
          //历史
          List<Image> homeCultureList  =   this.getListImageIndex("a3cd8d33-75d6-11e4-b6ce-005056a05bc9","homeCultureList");//
          
          //宗教
          List<Image> homeReligionList  =   this.getListImageIndex("a3cd8d33-75d6-11e4-b6ce-005056a05bc9","homeReligionList");//
          //其他待 向文琪 提供
          List<Image> homeOtherList  =   this.getListImageIndex("a3cd8d33-75d6-11e4-b6ce-005056a05bc9","homeOtherList");//
          
          modelMap.addAttribute("homeOtherList",homeOtherList);
          
          // 西藏文化传播  待   向文琪   接口
          
         //音乐  
          List<Content> list1  =  indexManagerService.getListContent("09c7cb0d-7e8c-11e4-b6ce-005056a05bc9","1");
          //小说
          List<Content> list2  =  indexManagerService.getListContent("45e0fd29-7e8c-11e4-b6ce-005056a05bc9","1");
         //游戏
          List<Content> list3  =  indexManagerService.getListContent("61d19785-7e8c-11e4-b6ce-005056a05bc9","1");
  
          List<Content> list4  =  indexManagerService.getListContent("611bs12785-7w2e8c-11e4-b6ce-005056a05bc9","1");
		
          
          modelMap.addAttribute("programaList", programaList);
          modelMap.addAttribute("destinationList", destinationList);
          modelMap.addAttribute("homeBannerList", homeBannerList);
         
        
          
           
          
          modelMap.addAttribute("homeRecomOneList", homeRecomOneList);
          modelMap.addAttribute("homeRecomTwoList", homeRecomTwoList);
          modelMap.addAttribute("homeRecomThreeList", homeRecomThreeList);

          this.getContentPVF(dyDataList);
          modelMap.addAttribute("dyDataList", dyDataList);
          this.getContentPVF(postList);
          modelMap.addAttribute("postList", postList);
          
          this.getContentPVF(strageList);
          modelMap.addAttribute("strageList", strageList);
          modelMap.addAttribute("homeViewList", homeViewList);
          modelMap.addAttribute("homePictureList", homePictureList);
          
          this.getContentPVF(viedioList);
          modelMap.addAttribute("viedioList", viedioList);
          
          modelMap.addAttribute("homeReadList", homeReadList);
          
         
          
          modelMap.addAttribute("homeCustomList", homeCustomList);
          modelMap.addAttribute("homeCultureList", homeCultureList);
          modelMap.addAttribute("homeReligionList", homeReligionList);
          
          this.getContentPVF(list1);
          this.getContentPVF(list2);
          this.getContentPVF(list3);
          modelMap.addAttribute("list1", list1);
          modelMap.addAttribute("list2", list2);
          modelMap.addAttribute("list3", list3);
          List<Image> homeWindowList  =  this.getListImageIndex("a3cd8d33-75d6-11e4-b6ce-005056a05bc9","homeWindowList");//;
          modelMap.addAttribute("homeWindowList", homeWindowList);
          
          HomeController.listImage  = new ArrayList<Image>();
		
		return "portal//app//index";
	}
	
	
	/**
	 *  图书西藏 预览
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 */
	@RequestMapping("indextsxz")
	public String indextsxz(HttpServletRequest request,HttpServletResponse response,ModelMap modelMap){
		blist  =  imageServiceIml.getHomeImg();
		//展示导航数据 接口 xiaozheng 提供
	    // 前台首页栏目code   e8876fe6-7608-11e4-b6ce-005056a05bc9
		List<Programa> programaList  = programaServiceIml.getSendPrograma("e8876fe6-7608-11e4-b6ce-005056a05bc9") ;
		
		// 首页 目的地接口   向文琪提供
		
        List<Destination>   destinationList  =   destinationServiceIml.getAllDestination();
		
		
		// 首页banner 显示
        Image img = null;
        //首页栏目   a3cd8d33-75d6-11e4-b6ce-005056a05bc9
        List<Image> homeBannerList  =   this.getListImageIndex("e43cb722-75d6-11e4-b6ce-005056a05bc9","homeBannerList");
		//热门关键字待确认 需求是否后台维护
        
        
        //图片推荐位管理查询数据 向文琪提供 1,2,3
        List<Image> homeRecomOneList  =   this.getListImageIndex("13173f79-75da-11e4-b6ce-005056a05bc9","homeRecomOneList");
        List<Image> homeRecomTwoList  =   this.getListImageIndex("13173f79-75da-11e4-b6ce-005056a05bc9","homeRecomTwoList");
        List<Image> homeRecomThreeList  =   this.getListImageIndex("13173f79-75da-11e4-b6ce-005056a05bc9","homeRecomThreeList");

        
        
        
        
        String   avalible  = request.getParameter("avalible");
        
        //动态列表查询3个数据  叶宇提供接口  13198b8d-75da-11e4-b6ce-005056a05bc9
        
         List<Content> dyDataList  = indexManagerService.getListContent("13198b8d-75da-11e4-b6ce-005056a05bc9","1");
        
        //帖子列表显示4个数据 叶宇提供接口
          List<Content> postList  = indexManagerService.getListContent("131c3973-75da-11e4-b6ce-005056a05bc9","1");
          
        //游西藏       更多>>  攻略&游记   4 叶宇提供接口
         List<Content> strageList  = indexManagerService.getListContent("131ea221-75da-11e4-b6ce-005056a05bc9","1");
            
        // 景点  接口 也是向文琪提供  
         List<Image> homeViewList  =   this.getListImageIndex("a3cd8d33-75d6-11e4-b6ce-005056a05bc9","homeViewList");
        // List<Image> homeViewList  =   HomeController.listImage;
        
       //图说西藏  接口   暂时  没有  提供    向文琪 
         //List<Image> homePictureList  =   this.getListImageIndex("a3cd8d33-75d6-11e4-b6ce-005056a05bc9","homePictureList");
          List<Image> homePictureList  =   HomeController.listImage;
        
        
        //视频专区 接口  叶宇  7
          List<Content>   viedioList  =  indexManagerService.getListContent("1327011a-75da-11e4-b6ce-005056a05bc9","1");
        
        //读西藏   接口 向文琪 
          //推荐第一个图片
          List<Image> homeReadList  =   this.getListImageIndex("a3cd8d33-75d6-11e4-b6ce-005056a05bc9","homeReadList");//
           //风俗
          List<Image> homeCustomList  =   this.getListImageIndex("a3cd8d33-75d6-11e4-b6ce-005056a05bc9","homeCustomList");//
          
          //历史
          List<Image> homeCultureList  =   this.getListImageIndex("a3cd8d33-75d6-11e4-b6ce-005056a05bc9","homeCultureList");//
          
          //宗教
          List<Image> homeReligionList  =   this.getListImageIndex("a3cd8d33-75d6-11e4-b6ce-005056a05bc9","homeReligionList");//
          //其他待 向文琪 提供
List<Image> homeOtherList  =   this.getListImageIndex("a3cd8d33-75d6-11e4-b6ce-005056a05bc9","homeOtherList");//
          
          modelMap.addAttribute("homeOtherList",homeOtherList);
          
          // 西藏文化传播  待   向文琪   接口
          
         //音乐  
          List<Content> list1  =  indexManagerService.getListContent("09c7cb0d-7e8c-11e4-b6ce-005056a05bc9","1");
          //小说
          List<Content> list2  =  indexManagerService.getListContent("45e0fd29-7e8c-11e4-b6ce-005056a05bc9","1");
         //游戏
          List<Content> list3  =  indexManagerService.getListContent("61d19785-7e8c-11e4-b6ce-005056a05bc9","1");
  
		
          List<Content> list4  =  indexManagerService.getListContent("611bs12785-7w2e8c-11e4-b6ce-005056a05bc9","1");
          
          modelMap.addAttribute("programaList", programaList);
          modelMap.addAttribute("destinationList", destinationList);
          modelMap.addAttribute("homeBannerList", homeBannerList);
         
           
          
          modelMap.addAttribute("homeRecomOneList", homeRecomOneList);
          modelMap.addAttribute("homeRecomTwoList", homeRecomTwoList);
          modelMap.addAttribute("homeRecomThreeList", homeRecomThreeList);

          this.getContentPVF(dyDataList);
          modelMap.addAttribute("dyDataList", dyDataList);
          this.getContentPVF(postList);
          modelMap.addAttribute("postList", postList);
          
          this.getContentPVF(strageList);
          modelMap.addAttribute("strageList", strageList);
          modelMap.addAttribute("homeViewList", homeViewList);
          modelMap.addAttribute("homePictureList", homePictureList);
          
          this.getContentPVF(viedioList);
          modelMap.addAttribute("viedioList", viedioList);
          
          modelMap.addAttribute("homeReadList", homeReadList);
          
         
          
          modelMap.addAttribute("homeCustomList", homeCustomList);
          modelMap.addAttribute("homeCultureList", homeCultureList);
          modelMap.addAttribute("homeReligionList", homeReligionList);
          
          this.getContentPVF(list1);
          this.getContentPVF(list2);
          this.getContentPVF(list3);
          modelMap.addAttribute("list1", list1);
          modelMap.addAttribute("list2", list2);
          modelMap.addAttribute("list3", list3);
          
          List<Image> homeWindowList  =  this.getListImageIndex("a3cd8d33-75d6-11e4-b6ce-005056a05bc9","homeWindowList");//;
          modelMap.addAttribute("homeWindowList", homeWindowList);
          
          HomeController.listImage  = new ArrayList<Image>();
		return "portal//app//index";
	}
	
	
	
	
	
	
	
	/**
	 *  读西藏 推荐1预览
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 */
	@RequestMapping("indexread")
	public String indexread(HttpServletRequest request,HttpServletResponse response,ModelMap modelMap){
		blist  =  imageServiceIml.getHomeImg();
		//展示导航数据 接口 xiaozheng 提供
	    // 前台首页栏目code   e8876fe6-7608-11e4-b6ce-005056a05bc9
		List<Programa> programaList  = programaServiceIml.getSendPrograma("e8876fe6-7608-11e4-b6ce-005056a05bc9") ;
		
		// 首页 目的地接口   向文琪提供
		
        List<Destination>   destinationList  =   destinationServiceIml.getAllDestination();
		
		
		// 首页banner 显示
        Image img = null;
        //首页栏目   a3cd8d33-75d6-11e4-b6ce-005056a05bc9
        List<Image> homeBannerList  =   this.getListImageIndex("e43cb722-75d6-11e4-b6ce-005056a05bc9","homeBannerList");
		//热门关键字待确认 需求是否后台维护
        
        
        //图片推荐位管理查询数据 向文琪提供 1,2,3
        List<Image> homeRecomOneList  =   this.getListImageIndex("13173f79-75da-11e4-b6ce-005056a05bc9","homeRecomOneList");
        List<Image> homeRecomTwoList  =   this.getListImageIndex("13173f79-75da-11e4-b6ce-005056a05bc9","homeRecomTwoList");
        List<Image> homeRecomThreeList  =   this.getListImageIndex("13173f79-75da-11e4-b6ce-005056a05bc9","homeRecomThreeList");

        
        
        
        
        String   avalible  = request.getParameter("avalible");
        
        //动态列表查询3个数据  叶宇提供接口  13198b8d-75da-11e4-b6ce-005056a05bc9
        
         List<Content> dyDataList  = indexManagerService.getListContent("13198b8d-75da-11e4-b6ce-005056a05bc9","1");
        
        //帖子列表显示4个数据 叶宇提供接口
          List<Content> postList  = indexManagerService.getListContent("131c3973-75da-11e4-b6ce-005056a05bc9","1");
          
        //游西藏       更多>>  攻略&游记   4 叶宇提供接口
         List<Content> strageList  = indexManagerService.getListContent("131ea221-75da-11e4-b6ce-005056a05bc9","1");
            
        // 景点  接口 也是向文琪提供  
         List<Image> homeViewList  =   this.getListImageIndex("a3cd8d33-75d6-11e4-b6ce-005056a05bc9","homeViewList");
        // List<Image> homeViewList  =   HomeController.listImage;
        
       //图说西藏  接口   暂时  没有  提供    向文琪 
         List<Image> homePictureList  =   this.getListImageIndex("a3cd8d33-75d6-11e4-b6ce-005056a05bc9","homePictureList");
          	//List<Image> homePictureList  =   HomeController.listImage;
        
        
        //视频专区 接口  叶宇  7
          List<Content>   viedioList  =  indexManagerService.getListContent("1327011a-75da-11e4-b6ce-005056a05bc9","1");
        
        //读西藏   接口 向文琪 
          //推荐第一个图片
          List<Image> homeReadList  =   HomeController.listImage;
           //风俗
          List<Image> homeCustomList  =   this.getListImageIndex("a3cd8d33-75d6-11e4-b6ce-005056a05bc9","homeCustomList");//
          
          //历史
          List<Image> homeCultureList  =   this.getListImageIndex("a3cd8d33-75d6-11e4-b6ce-005056a05bc9","homeCultureList");//
          
          //宗教
          List<Image> homeReligionList  =   this.getListImageIndex("a3cd8d33-75d6-11e4-b6ce-005056a05bc9","homeReligionList");//
          //其他待 向文琪 提供
          List<Image> homeOtherList  =   this.getListImageIndex("a3cd8d33-75d6-11e4-b6ce-005056a05bc9","homeOtherList");//
          
          modelMap.addAttribute("homeOtherList",homeOtherList);
          
          // 西藏文化传播  待   向文琪   接口
          
         //音乐  
          List<Content> list1  =  indexManagerService.getListContent("09c7cb0d-7e8c-11e4-b6ce-005056a05bc9","1");
          //小说
          List<Content> list2  =  indexManagerService.getListContent("45e0fd29-7e8c-11e4-b6ce-005056a05bc9","1");
         //游戏
          List<Content> list3  =  indexManagerService.getListContent("61d19785-7e8c-11e4-b6ce-005056a05bc9","1");
          List<Content> list4  =  indexManagerService.getListContent("611bs12785-7w2e8c-11e4-b6ce-005056a05bc9","1");
		
		
          
          modelMap.addAttribute("programaList", programaList);
          modelMap.addAttribute("destinationList", destinationList);
          modelMap.addAttribute("homeBannerList", homeBannerList);
         
         
          
           
          
          modelMap.addAttribute("homeRecomOneList", homeRecomOneList);
          modelMap.addAttribute("homeRecomTwoList", homeRecomTwoList);
          modelMap.addAttribute("homeRecomThreeList", homeRecomThreeList);

          this.getContentPVF(dyDataList);
          modelMap.addAttribute("dyDataList", dyDataList);
          this.getContentPVF(postList);
          modelMap.addAttribute("postList", postList);
          
          this.getContentPVF(strageList);
          modelMap.addAttribute("strageList", strageList);
          modelMap.addAttribute("homeViewList", homeViewList);
          modelMap.addAttribute("homePictureList", homePictureList);
          
          this.getContentPVF(viedioList);
          modelMap.addAttribute("viedioList", viedioList);
          
          modelMap.addAttribute("homeReadList", homeReadList);
          
         
          
          modelMap.addAttribute("homeCustomList", homeCustomList);
          modelMap.addAttribute("homeCultureList", homeCultureList);
          modelMap.addAttribute("homeReligionList", homeReligionList);
          
          this.getContentPVF(list1);
          this.getContentPVF(list2);
          this.getContentPVF(list3);
          modelMap.addAttribute("list1", list1);
          modelMap.addAttribute("list2", list2);
          modelMap.addAttribute("list3", list3);
          
          
          List<Image> homeWindowList  =  this.getListImageIndex("a3cd8d33-75d6-11e4-b6ce-005056a05bc9","homeWindowList");//;
          modelMap.addAttribute("homeWindowList", homeWindowList);
          HomeController.listImage  = new ArrayList<Image>();
		return "portal//app//index";
	}
	
	
	
	
	/**
	 *  读西藏风俗 预览
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 */
	@RequestMapping("indexcustom")
	public String indexcustom(HttpServletRequest request,HttpServletResponse response,ModelMap modelMap){
		blist  =  imageServiceIml.getHomeImg();
		//展示导航数据 接口 xiaozheng 提供
	    // 前台首页栏目code   e8876fe6-7608-11e4-b6ce-005056a05bc9
		List<Programa> programaList  = programaServiceIml.getSendPrograma("e8876fe6-7608-11e4-b6ce-005056a05bc9") ;
		
		// 首页 目的地接口   向文琪提供
		
        List<Destination>   destinationList  =   destinationServiceIml.getAllDestination();
		
		
		// 首页banner 显示
        Image img = null;
        //首页栏目   a3cd8d33-75d6-11e4-b6ce-005056a05bc9
        List<Image> homeBannerList  =   this.getListImageIndex("e43cb722-75d6-11e4-b6ce-005056a05bc9","homeBannerList");
		//热门关键字待确认 需求是否后台维护
        
        
        //图片推荐位管理查询数据 向文琪提供 1,2,3
        List<Image> homeRecomOneList  =   this.getListImageIndex("13173f79-75da-11e4-b6ce-005056a05bc9","homeRecomOneList");
        List<Image> homeRecomTwoList  =   this.getListImageIndex("13173f79-75da-11e4-b6ce-005056a05bc9","homeRecomTwoList");
        List<Image> homeRecomThreeList  =   this.getListImageIndex("13173f79-75da-11e4-b6ce-005056a05bc9","homeRecomThreeList");

        
        
        
        
        String   avalible  = request.getParameter("avalible");
        
        //动态列表查询3个数据  叶宇提供接口  13198b8d-75da-11e4-b6ce-005056a05bc9
        
         List<Content> dyDataList  = indexManagerService.getListContent("13198b8d-75da-11e4-b6ce-005056a05bc9","1");
        
        //帖子列表显示4个数据 叶宇提供接口
          List<Content> postList  = indexManagerService.getListContent("131c3973-75da-11e4-b6ce-005056a05bc9","1");
          
        //游西藏       更多>>  攻略&游记   4 叶宇提供接口
         List<Content> strageList  = indexManagerService.getListContent("131ea221-75da-11e4-b6ce-005056a05bc9","1");
            
        // 景点  接口 也是向文琪提供  
         List<Image> homeViewList  =   this.getListImageIndex("a3cd8d33-75d6-11e4-b6ce-005056a05bc9","homeViewList");
        // List<Image> homeViewList  =   HomeController.listImage;
        
       //图说西藏  接口   暂时  没有  提供    向文琪 
         List<Image> homePictureList  =   this.getListImageIndex("a3cd8d33-75d6-11e4-b6ce-005056a05bc9","homePictureList");
          	//List<Image> homePictureList  =   HomeController.listImage;
        
        
        //视频专区 接口  叶宇  7
          List<Content>   viedioList  =  indexManagerService.getListContent("1327011a-75da-11e4-b6ce-005056a05bc9","1");
        
        //读西藏   接口 向文琪 
          //推荐第一个图片
          List<Image> homeReadList  =   this.getListImageIndex("a3cd8d33-75d6-11e4-b6ce-005056a05bc9","homeReadList");//
           //风俗
          List<Image> homeCustomList  =   HomeController.listImage;
          
          
          //历史
          List<Image> homeCultureList  =   this.getListImageIndex("a3cd8d33-75d6-11e4-b6ce-005056a05bc9","homeCultureList");//
          
          //宗教
          List<Image> homeReligionList  =   this.getListImageIndex("a3cd8d33-75d6-11e4-b6ce-005056a05bc9","homeReligionList");//
          //其他待 向文琪 提供
List<Image> homeOtherList  =   this.getListImageIndex("a3cd8d33-75d6-11e4-b6ce-005056a05bc9","homeOtherList");//
          
          modelMap.addAttribute("homeOtherList",homeOtherList);
          
          // 西藏文化传播  待   向文琪   接口
          
         //音乐  
          List<Content> list1  =  indexManagerService.getListContent("09c7cb0d-7e8c-11e4-b6ce-005056a05bc9","1");
          //小说
          List<Content> list2  =  indexManagerService.getListContent("45e0fd29-7e8c-11e4-b6ce-005056a05bc9","1");
         //游戏
          List<Content> list3  =  indexManagerService.getListContent("61d19785-7e8c-11e4-b6ce-005056a05bc9","1");
  
          List<Content> list4  =  indexManagerService.getListContent("611bs12785-7w2e8c-11e4-b6ce-005056a05bc9","1");
		
          
          modelMap.addAttribute("programaList", programaList);
          modelMap.addAttribute("destinationList", destinationList);
          modelMap.addAttribute("homeBannerList", homeBannerList);
         
        
          
           
          
          modelMap.addAttribute("homeRecomOneList", homeRecomOneList);
          modelMap.addAttribute("homeRecomTwoList", homeRecomTwoList);
          modelMap.addAttribute("homeRecomThreeList", homeRecomThreeList);

          this.getContentPVF(dyDataList);
          modelMap.addAttribute("dyDataList", dyDataList);
          this.getContentPVF(postList);
          modelMap.addAttribute("postList", postList);
          
          this.getContentPVF(strageList);
          modelMap.addAttribute("strageList", strageList);
          modelMap.addAttribute("homeViewList", homeViewList);
          modelMap.addAttribute("homePictureList", homePictureList);
          
          this.getContentPVF(viedioList);
          modelMap.addAttribute("viedioList", viedioList);
          
          modelMap.addAttribute("homeReadList", homeReadList);
          
         
          
          modelMap.addAttribute("homeCustomList", homeCustomList);
          modelMap.addAttribute("homeCultureList", homeCultureList);
          modelMap.addAttribute("homeReligionList", homeReligionList);
          
          this.getContentPVF(list1);
          this.getContentPVF(list2);
          this.getContentPVF(list3);
          modelMap.addAttribute("list1", list1);
          modelMap.addAttribute("list2", list2);
          modelMap.addAttribute("list3", list3);
          
          List<Image> homeWindowList  =  this.getListImageIndex("a3cd8d33-75d6-11e4-b6ce-005056a05bc9","homeWindowList");//;
          modelMap.addAttribute("homeWindowList", homeWindowList);
          
          HomeController.listImage  = new ArrayList<Image>();
		return "portal//app//index";
	}
	
	
	
	
	
	
	
	
	
	/**
	 *  读西藏   文化 预览
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 */
	@RequestMapping("indexculture")
	public String indexculture(HttpServletRequest request,HttpServletResponse response,ModelMap modelMap){
		blist  =  imageServiceIml.getHomeImg();
		//展示导航数据 接口 xiaozheng 提供
	    // 前台首页栏目code   e8876fe6-7608-11e4-b6ce-005056a05bc9
		List<Programa> programaList  = programaServiceIml.getSendPrograma("e8876fe6-7608-11e4-b6ce-005056a05bc9") ;
		
		// 首页 目的地接口   向文琪提供
		
        List<Destination>   destinationList  =   destinationServiceIml.getAllDestination();
		
		
		// 首页banner 显示
        Image img = null;
        //首页栏目   a3cd8d33-75d6-11e4-b6ce-005056a05bc9
        List<Image> homeBannerList  =   this.getListImageIndex("e43cb722-75d6-11e4-b6ce-005056a05bc9","homeBannerList");
		//热门关键字待确认 需求是否后台维护
        
        
        //图片推荐位管理查询数据 向文琪提供 1,2,3
        List<Image> homeRecomOneList  =   this.getListImageIndex("13173f79-75da-11e4-b6ce-005056a05bc9","homeRecomOneList");
        List<Image> homeRecomTwoList  =   this.getListImageIndex("13173f79-75da-11e4-b6ce-005056a05bc9","homeRecomTwoList");
        List<Image> homeRecomThreeList  =   this.getListImageIndex("13173f79-75da-11e4-b6ce-005056a05bc9","homeRecomThreeList");

        
        
        
        
        String   avalible  = request.getParameter("avalible");
        
        //动态列表查询3个数据  叶宇提供接口  13198b8d-75da-11e4-b6ce-005056a05bc9
        
         List<Content> dyDataList  = indexManagerService.getListContent("13198b8d-75da-11e4-b6ce-005056a05bc9","1");
        
        //帖子列表显示4个数据 叶宇提供接口
          List<Content> postList  = indexManagerService.getListContent("131c3973-75da-11e4-b6ce-005056a05bc9","1");
          
        //游西藏       更多>>  攻略&游记   4 叶宇提供接口
         List<Content> strageList  = indexManagerService.getListContent("131ea221-75da-11e4-b6ce-005056a05bc9","1");
            
        // 景点  接口 也是向文琪提供  
         List<Image> homeViewList  =   this.getListImageIndex("a3cd8d33-75d6-11e4-b6ce-005056a05bc9","homeViewList");
        // List<Image> homeViewList  =   HomeController.listImage;
        
       //图说西藏  接口   暂时  没有  提供    向文琪 
         List<Image> homePictureList  =   this.getListImageIndex("a3cd8d33-75d6-11e4-b6ce-005056a05bc9","homePictureList");
          	//List<Image> homePictureList  =   HomeController.listImage;
        
        
        //视频专区 接口  叶宇  7
          List<Content>   viedioList  =  indexManagerService.getListContent("1327011a-75da-11e4-b6ce-005056a05bc9","1");
        
        //读西藏   接口 向文琪 
          //推荐第一个图片
          List<Image> homeReadList  =  this.getListImageIndex("a3cd8d33-75d6-11e4-b6ce-005056a05bc9","homeCustomList");
           //风俗
          List<Image> homeCustomList  =   this.getListImageIndex("a3cd8d33-75d6-11e4-b6ce-005056a05bc9","homeCustomList");;
          
          //文化
          List<Image> homeCultureList  =   HomeController.listImage;//
          
          //宗教
          List<Image> homeReligionList  =   this.getListImageIndex("a3cd8d33-75d6-11e4-b6ce-005056a05bc9","homeReligionList");//
          //其他待 向文琪 提供
          List<Image> homeOtherList  =   this.getListImageIndex("a3cd8d33-75d6-11e4-b6ce-005056a05bc9","homeOtherList");//
          
          modelMap.addAttribute("homeOtherList",homeOtherList);
          
          // 西藏文化传播  待   向文琪   接口
          
         //音乐  
          List<Content> list1  =  indexManagerService.getListContent("09c7cb0d-7e8c-11e4-b6ce-005056a05bc9","1");
          //小说
          List<Content> list2  =  indexManagerService.getListContent("45e0fd29-7e8c-11e4-b6ce-005056a05bc9","1");
         //游戏
          List<Content> list3  =  indexManagerService.getListContent("61d19785-7e8c-11e4-b6ce-005056a05bc9","1");
  
		
          List<Content> list4  =  indexManagerService.getListContent("611bs12785-7w2e8c-11e4-b6ce-005056a05bc9","1");
          
          modelMap.addAttribute("programaList", programaList);
          modelMap.addAttribute("destinationList", destinationList);
          modelMap.addAttribute("homeBannerList", homeBannerList);
         
        
          
           
          
          modelMap.addAttribute("homeRecomOneList", homeRecomOneList);
          modelMap.addAttribute("homeRecomTwoList", homeRecomTwoList);
          modelMap.addAttribute("homeRecomThreeList", homeRecomThreeList);

          this.getContentPVF(dyDataList);
          modelMap.addAttribute("dyDataList", dyDataList);
          this.getContentPVF(postList);
          modelMap.addAttribute("postList", postList);
          
          this.getContentPVF(strageList);
          modelMap.addAttribute("strageList", strageList);
          modelMap.addAttribute("homeViewList", homeViewList);
          modelMap.addAttribute("homePictureList", homePictureList);
          
          this.getContentPVF(viedioList);
          modelMap.addAttribute("viedioList", viedioList);
          
          modelMap.addAttribute("homeReadList", homeReadList);
          
          
          
          modelMap.addAttribute("homeCustomList", homeCustomList);
          modelMap.addAttribute("homeCultureList", homeCultureList);
          modelMap.addAttribute("homeReligionList", homeReligionList);
          
          this.getContentPVF(list1);
          this.getContentPVF(list2);
          this.getContentPVF(list3);
          modelMap.addAttribute("list1", list1);
          modelMap.addAttribute("list2", list2);
          modelMap.addAttribute("list3", list3);
          List<Image> homeWindowList  =  this.getListImageIndex("a3cd8d33-75d6-11e4-b6ce-005056a05bc9","homeWindowList");//;
          modelMap.addAttribute("homeWindowList", homeWindowList);
          HomeController.listImage = new ArrayList<Image>();
          
		
		return "portal//app//index";
	}
	
	
	
	
	
	
	
		
	
	
	/**
	 *  读西藏   宗教  预览
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 */
	@RequestMapping("indexreligion")
	public String indexreligion(HttpServletRequest request,HttpServletResponse response,ModelMap modelMap){
		blist  =  imageServiceIml.getHomeImg();
		//展示导航数据 接口 xiaozheng 提供
	    // 前台首页栏目code   e8876fe6-7608-11e4-b6ce-005056a05bc9
		List<Programa> programaList  = programaServiceIml.getSendPrograma("e8876fe6-7608-11e4-b6ce-005056a05bc9") ;
		
		// 首页 目的地接口   向文琪提供
		
        List<Destination>   destinationList  =   destinationServiceIml.getAllDestination();
		
		
		// 首页banner 显示
        Image img = null;
        //首页栏目   a3cd8d33-75d6-11e4-b6ce-005056a05bc9
        List<Image> homeBannerList  =   this.getListImageIndex("e43cb722-75d6-11e4-b6ce-005056a05bc9","homeBannerList");
		//热门关键字待确认 需求是否后台维护
        
        
        //图片推荐位管理查询数据 向文琪提供 1,2,3
        List<Image> homeRecomOneList  =   this.getListImageIndex("13173f79-75da-11e4-b6ce-005056a05bc9","homeRecomOneList");
        List<Image> homeRecomTwoList  =   this.getListImageIndex("13173f79-75da-11e4-b6ce-005056a05bc9","homeRecomTwoList");
        List<Image> homeRecomThreeList  =   this.getListImageIndex("13173f79-75da-11e4-b6ce-005056a05bc9","homeRecomThreeList");

        
        
        
        
        String   avalible  = request.getParameter("avalible");
        
        //动态列表查询3个数据  叶宇提供接口  13198b8d-75da-11e4-b6ce-005056a05bc9
        
         List<Content> dyDataList  = indexManagerService.getListContent("13198b8d-75da-11e4-b6ce-005056a05bc9","1");
        
        //帖子列表显示4个数据 叶宇提供接口
          List<Content> postList  = indexManagerService.getListContent("131c3973-75da-11e4-b6ce-005056a05bc9","1");
          
        //游西藏       更多>>  攻略&游记   4 叶宇提供接口
         List<Content> strageList  = indexManagerService.getListContent("131ea221-75da-11e4-b6ce-005056a05bc9","1");
            
        // 景点  接口 也是向文琪提供  
         List<Image> homeViewList  =   this.getListImageIndex("a3cd8d33-75d6-11e4-b6ce-005056a05bc9","homeViewList");
        // List<Image> homeViewList  =   HomeController.listImage;
        
       //图说西藏  接口   暂时  没有  提供    向文琪 
         List<Image> homePictureList  =   this.getListImageIndex("a3cd8d33-75d6-11e4-b6ce-005056a05bc9","homePictureList");
          	//List<Image> homePictureList  =   HomeController.listImage;
        
        
        //视频专区 接口  叶宇  7
          List<Content>   viedioList  =  indexManagerService.getListContent("1327011a-75da-11e4-b6ce-005056a05bc9","1");
        
        //读西藏   接口 向文琪 
          //推荐第一个图片
          List<Image> homeReadList  =   this.getListImageIndex("a3cd8d33-75d6-11e4-b6ce-005056a05bc9","homeReadList");//
           //风俗
          List<Image> homeCustomList  =   this.getListImageIndex("a3cd8d33-75d6-11e4-b6ce-005056a05bc9","homeCustomList");//;
          
          //文化
          List<Image> homeCultureList  =   this.getListImageIndex("a3cd8d33-75d6-11e4-b6ce-005056a05bc9","homeCultureList");//
          
          //宗教
          List<Image> homeReligionList  =  HomeController.listImage;
          //其他待 向文琪 提供
          List<Image> homeOtherList  =   this.getListImageIndex("a3cd8d33-75d6-11e4-b6ce-005056a05bc9","homeOtherList");//
          
          modelMap.addAttribute("homeOtherList",homeOtherList);
          
          // 西藏文化传播  待   向文琪   接口
          
         //音乐  
          List<Content> list1  =  indexManagerService.getListContent("09c7cb0d-7e8c-11e4-b6ce-005056a05bc9","1");
          //小说
          List<Content> list2  =  indexManagerService.getListContent("45e0fd29-7e8c-11e4-b6ce-005056a05bc9","1");
         //游戏
          List<Content> list3  =  indexManagerService.getListContent("61d19785-7e8c-11e4-b6ce-005056a05bc9","1");
          List<Content> list4  =  indexManagerService.getListContent("611bs12785-7w2e8c-11e4-b6ce-005056a05bc9","1");
		
		
          
          modelMap.addAttribute("programaList", programaList);
          modelMap.addAttribute("destinationList", destinationList);
          modelMap.addAttribute("homeBannerList", homeBannerList);
        
          
           
          
          modelMap.addAttribute("homeRecomOneList", homeRecomOneList);
          modelMap.addAttribute("homeRecomTwoList", homeRecomTwoList);
          modelMap.addAttribute("homeRecomThreeList", homeRecomThreeList);

          this.getContentPVF(dyDataList);
          modelMap.addAttribute("dyDataList", dyDataList);
          this.getContentPVF(postList);
          modelMap.addAttribute("postList", postList);
          
          this.getContentPVF(strageList);
          modelMap.addAttribute("strageList", strageList);
          modelMap.addAttribute("homeViewList", homeViewList);
          modelMap.addAttribute("homePictureList", homePictureList);
          
          this.getContentPVF(viedioList);
          modelMap.addAttribute("viedioList", viedioList);
          
          modelMap.addAttribute("homeReadList", homeReadList);
          
        
          
          modelMap.addAttribute("homeCustomList", homeCustomList);
          modelMap.addAttribute("homeCultureList", homeCultureList);
          modelMap.addAttribute("homeReligionList", homeReligionList);
          
          this.getContentPVF(list1);
          this.getContentPVF(list2);
          this.getContentPVF(list3);
          modelMap.addAttribute("list1", list1);
          modelMap.addAttribute("list2", list2);
          modelMap.addAttribute("list3", list3);
          List<Image> homeWindowList  =  this.getListImageIndex("a3cd8d33-75d6-11e4-b6ce-005056a05bc9","homeWindowList");//;
          modelMap.addAttribute("homeWindowList", homeWindowList);
          HomeController.listImage  = new ArrayList<Image>();
          
		
		return "portal//app//index";
	}
	
	
	
	
	
	
	
	
	
	
	/**
	 *  浮 窗体 预览
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 */
	@RequestMapping("indexwindow")
	public String indexwindow(HttpServletRequest request,HttpServletResponse response,ModelMap modelMap){
		blist  =  imageServiceIml.getHomeImg();
		//展示导航数据 接口 xiaozheng 提供
	    // 前台首页栏目code   e8876fe6-7608-11e4-b6ce-005056a05bc9
		List<Programa> programaList  = programaServiceIml.getSendPrograma("e8876fe6-7608-11e4-b6ce-005056a05bc9") ;
		
		// 首页 目的地接口   向文琪提供
		
        List<Destination>   destinationList  =   destinationServiceIml.getAllDestination();
		
		
		// 首页banner 显示
        Image img = null;
        //首页栏目   a3cd8d33-75d6-11e4-b6ce-005056a05bc9
        List<Image> homeBannerList  =   this.getListImageIndex("e43cb722-75d6-11e4-b6ce-005056a05bc9","homeBannerList");
		//热门关键字待确认 需求是否后台维护
        
        
        //图片推荐位管理查询数据 向文琪提供 1,2,3
        List<Image> homeRecomOneList  =   this.getListImageIndex("13173f79-75da-11e4-b6ce-005056a05bc9","homeRecomOneList");
        List<Image> homeRecomTwoList  =   this.getListImageIndex("13173f79-75da-11e4-b6ce-005056a05bc9","homeRecomTwoList");
        List<Image> homeRecomThreeList  =   this.getListImageIndex("13173f79-75da-11e4-b6ce-005056a05bc9","homeRecomThreeList");

        
        
        
        
        String   avalible  = request.getParameter("avalible");
        
        //动态列表查询3个数据  叶宇提供接口  13198b8d-75da-11e4-b6ce-005056a05bc9
        
         List<Content> dyDataList  = indexManagerService.getListContent("13198b8d-75da-11e4-b6ce-005056a05bc9","1");
        
        //帖子列表显示4个数据 叶宇提供接口
          List<Content> postList  = indexManagerService.getListContent("131c3973-75da-11e4-b6ce-005056a05bc9","1");
          
        //游西藏       更多>>  攻略&游记   4 叶宇提供接口
         List<Content> strageList  = indexManagerService.getListContent("131ea221-75da-11e4-b6ce-005056a05bc9","1");
            
        // 景点  接口 也是向文琪提供  
         List<Image> homeViewList  =   this.getListImageIndex("a3cd8d33-75d6-11e4-b6ce-005056a05bc9","homeViewList");
        // List<Image> homeViewList  =   HomeController.listImage;
        
       //图说西藏  接口   暂时  没有  提供    向文琪 
         List<Image> homePictureList  =   this.getListImageIndex("a3cd8d33-75d6-11e4-b6ce-005056a05bc9","homePictureList");
          	//List<Image> homePictureList  =   HomeController.listImage;
        
        
        //视频专区 接口  叶宇  7
          List<Content>   viedioList  =  indexManagerService.getListContent("1327011a-75da-11e4-b6ce-005056a05bc9","1");
        
        //读西藏   接口 向文琪 
          //推荐第一个图片
          List<Image> homeReadList  =  this.getListImageIndex("a3cd8d33-75d6-11e4-b6ce-005056a05bc9","homeCustomList");
           //风俗
          List<Image> homeCustomList  =   this.getListImageIndex("a3cd8d33-75d6-11e4-b6ce-005056a05bc9","homeCustomList");;
          
          //文化
          List<Image> homeCultureList  =   this.getListImageIndex("a3cd8d33-75d6-11e4-b6ce-005056a05bc9","homeCultureList");;;//
          
          //宗教
          List<Image> homeReligionList  =   this.getListImageIndex("a3cd8d33-75d6-11e4-b6ce-005056a05bc9","homeReligionList");//
          //其他待 向文琪 提供
          List<Image> homeOtherList  =   this.getListImageIndex("a3cd8d33-75d6-11e4-b6ce-005056a05bc9","homeOtherList");//
          
          modelMap.addAttribute("homeOtherList",homeOtherList);
          // 西藏文化传播  待   向文琪   接口
          
         //音乐  
          List<Content> list1  =  indexManagerService.getListContent("09c7cb0d-7e8c-11e4-b6ce-005056a05bc9","1");
          //小说
          List<Content> list2  =  indexManagerService.getListContent("45e0fd29-7e8c-11e4-b6ce-005056a05bc9","1");
         //游戏
          List<Content> list3  =  indexManagerService.getListContent("61d19785-7e8c-11e4-b6ce-005056a05bc9","1");
          List<Content> list4  =  indexManagerService.getListContent("611bs12785-7w2e8c-11e4-b6ce-005056a05bc9","1");
		
		
         
          
          
          modelMap.addAttribute("programaList", programaList);
          modelMap.addAttribute("destinationList", destinationList);
          modelMap.addAttribute("homeBannerList", homeBannerList);
         
        
          
           
          
          modelMap.addAttribute("homeRecomOneList", homeRecomOneList);
          modelMap.addAttribute("homeRecomTwoList", homeRecomTwoList);
          modelMap.addAttribute("homeRecomThreeList", homeRecomThreeList);

          this.getContentPVF(dyDataList);
          modelMap.addAttribute("dyDataList", dyDataList);
          this.getContentPVF(postList);
          modelMap.addAttribute("postList", postList);
          
          this.getContentPVF(strageList);
          modelMap.addAttribute("strageList", strageList);
          modelMap.addAttribute("homeViewList", homeViewList);
          modelMap.addAttribute("homePictureList", homePictureList);
          
          this.getContentPVF(viedioList);
          modelMap.addAttribute("viedioList", viedioList);
          
          modelMap.addAttribute("homeReadList", homeReadList);
          
         
          
          modelMap.addAttribute("homeCustomList", homeCustomList);
          modelMap.addAttribute("homeCultureList", homeCultureList);
          modelMap.addAttribute("homeReligionList", homeReligionList);
          
          this.getContentPVF(list1);
          this.getContentPVF(list2);
          this.getContentPVF(list3);
          modelMap.addAttribute("list1", list1);
          modelMap.addAttribute("list2", list2);
          modelMap.addAttribute("list3", list3);
         
         //modelMap.addAttribute("homeWindowList", homeWindowList);
          
        
		return "portal/app/index";
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

	@RequestMapping("window")
	public  String  window(HttpServletRequest request,HttpServletResponse response,ModelMap modelMap){
		
		 //浮窗体
         List<Image> homeWindowList  =  HomeController.listImage;
		
		 ImageService  imageServiceIml  =    (ImageService) SpringUtil.getAppCtx().getBean("ImageService");
		 Map<String,List<Image>>	  blist  =  imageServiceIml.getHomeImg();
		 Map<String,List<Image>>  bannerMap   =  blist;
		 if (homeWindowList!=null&&homeWindowList.size()>=1) {
    		 if (modelMap!=null) {
    			 modelMap.put("img", homeWindowList);
			}
		}
		else if (bannerMap!=null) {
	    	 if (bannerMap!=null&&bannerMap.size()>=1) {
	    		 modelMap.put("img",bannerMap.get("homeWindowList"));
			 } 
	    	
		}
		   
	     
	     HomeController.listImage =  new ArrayList<Image>();
	    //modelMap.put("img", homeWindowList.get(0));
	     return "portal//app//header//window";
	}
	
	
	
}
