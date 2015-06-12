package com.rimi.ctibet.web.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.rimi.ctibet.common.controller.BaseController;
import com.rimi.ctibet.common.util.ListUtil;
import com.rimi.ctibet.common.util.Pager;
import com.rimi.ctibet.domain.Content;
import com.rimi.ctibet.domain.Destination;
import com.rimi.ctibet.domain.Image;
import com.rimi.ctibet.domain.MutiImage;
import com.rimi.ctibet.domain.Programa;
import com.rimi.ctibet.domain.View;
import com.rimi.ctibet.portal.controller.FronttTourismController;
import com.rimi.ctibet.portal.controller.vo.MerchantVo;
import com.rimi.ctibet.portal.controller.vo.TravelVo;
import com.rimi.ctibet.web.service.DestinationService;
import com.rimi.ctibet.web.service.IContentService;
import com.rimi.ctibet.web.service.IMerchantService;
import com.rimi.ctibet.web.service.IProgramaService;
import com.rimi.ctibet.web.service.IViewService;
import com.rimi.ctibet.web.service.ImageService;
import com.rimi.ctibet.web.service.MutiImageService;
@Controller
@RequestMapping("web/travelController")
public class TraverlController extends BaseController {
	@Resource
	private IViewService viewService;
	@Resource
	private ImageService imageService;
	@Resource
	private IContentService contentService;
	@Resource
	private MutiImageService mutiService;
	@Resource
	private IProgramaService programaService;
	@Resource
	private DestinationService destinationService;
	@Resource
	private IMerchantService merchantService;
	
	private FronttTourismController tcontroller;
	
	public static   List<Image> listImage  = new ArrayList<Image>(); //预览存放的list
	
	public static List<View>  listview  =  new ArrayList<View>();
	
	/**
	 * 通过栏目code获取图片集
	 * 
	 * @param programaCode
	 * @param model
	 * @return 返回一个图集列表 和一个 图片列表的列表
	 */
	@RequestMapping("getManageImg")
	public String getManageImg(String programaCode, ModelMap model) {
		List<MutiImage> mutiImageList = mutiService.getMutiImageByProgramaCode(programaCode);
		Programa programa = programaService.getProgramaByCode(programaCode);
		List<Destination> destinationList = destinationService.getAllDestination();
		String str = ""; // 跳转的路径
		if (programa.getCode().equals("1256se5-qe2c-52e4-a6ce-11505ca05dc9")) {
			str = "manage/html/travel/banner";  //游西藏bannerg管理
		}
		if (programa.getCode().equals("bc22ed19-b2bc-42cd-a8c9-beb96e25ed89")) {
            str= "manage/html/travel/posid"; //游西藏推荐位管理
		}
		if (programa.getCode().equals("25b327a5-7e8c-12e4-b6ce-005056b896a3")) {
			model.put("destinationList", destinationList);
			//热门景点回显
			List<Map<String,Object>> list     =  new ArrayList<Map<String,Object>>();
			if(viewService.isFirstInHotView()){
				list =  viewService.getViewByWann(10,null);
				if (list!=null) {
					String viewCode1 = "";
					for(int i = 0; i<list.size();i++){
						viewCode1 =  list.get(i).get("viewcode")+ ","+ viewCode1  ;
					}
					contentService.saveTravelViews(viewCode1);
				}
			}else{
				List<View> views = contentService.getTravelView();
	            if(ListUtil.isNotNull(views)){
    				for (View view : views) {
    				      Map<String,Object> map = new HashMap<String,Object>();
    				      if (view!=null) {
    				          map.put("viewcode", view.getCode());
    				          map.put("descode", view.getDestinationCode());
    				          list.add(map);
    				    	  /*map.put("viewcode", view.getCode());
    				    	  map.put("viewname", view.getViewName());
    				    	  Destination des = viewService.getDestinationByView(view.getCode());
    				    	  if (des!=null) {
    				    		  map.put("descode", des.getCode());
    				    		  
    				    		  map.put("desname", des.getDestinationName());
    				    	  }
    				    	  list.add(map);*/
    					}else{
    						   map.put("viewcode", "");
   				          		map.put("descode", "");
   				          		list.add(map);
    					}
    				}
	            }
			}
			model.put("list", list);
			return "manage/html/travel/scenic-spots";  //游西藏景点管理
		}
		List<List<Image>> imageList = new ArrayList<List<Image>>();
		for (MutiImage muti : mutiImageList) {
			imageList.add(imageService.getImageByMutiImageCode(muti.getCode()));
		}
		model.put("mutiList", mutiImageList);
		model.put("imageList", imageList);
		return str;
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
		String viewCode = request.getParameter("viewCode");
		if(StringUtils.isNotBlank(viewCode)){
			View view = viewService.findByCode(viewCode);
			int wantCount = view.getWantCount();
			int goCount = view.getGoneCount();
			image.setWantCount(wantCount);
			image.setGoCount(goCount);
			image.setHyperlink(view.getCode()); //超链接内容待定
		}	
		image.setName(name);
		image.setSummary(summary);
		image.setHyperlink(hyperlink);
		//System.out.println("imageName------------>"+image.getName());
		imageService.saveImage(image);
		return "11111";
	}
	
	@RequestMapping("saveTravelViews")
	public String saveTravelViews(HttpServletRequest request,
			HttpServletResponse response,String[] viewCode,ModelMap model){
		String viewCode1  = "";
		List<Destination> destinationList = destinationService.getAllDestination();
		model.put("destinationList", destinationList);
		StringBuffer sb  = new StringBuffer();
		for(int i = 0; i<viewCode.length;i++){
		   sb.append(viewCode[i]);
		   if (i==viewCode.length-1) {
			   break;
		   }
		   sb.append(",");
		}
		
		contentService.saveTravelViews(sb.toString());
		//热门景点回显  uv.viewCode as viewcode,uv.areaCode as  descode,tv.viewName as viewname ,des.destinationName as desname 
		/*List<Map<String,Object>> list     =  new ArrayList<Map<String,Object>>();
		if(viewService.isFirstInHotView()){
			list =  viewService.getViewByWann(10,null);
		}else{
			List<View> views = contentService.getTravelView();
            
			for (View view : views) {
			      Map<String,Object> map = new HashMap<String,Object>();
			      if (view!=null) {
			    	  map.put("viewcode", view.getCode());
			    	  map.put("viewname", view.getViewName());
			    	  Destination des = viewService.getDestinationByView(view.getCode());
			    	  if (des!=null) {
			    		  
			    		  map.put("descode", des.getCode());
			    		  map.put("desname", des.getDestinationName());
			    	  }
					
				  }
			      list.add(map);
			}
		}
		model.put("list", list);*/
		//return "manage/html/travel/scenic-spots";  //游西藏景点管理
		return redirect("/web/travelController/getManageImg?programaCode=25b327a5-7e8c-12e4-b6ce-005056b896a3");
	}
	
	
	
	
	/**
	 * 把预览图片添加到全局变量
	 */
	@RequestMapping("previewManageImg")
	public @ResponseBody String  previewManageImg(Image image) {
		//System.out.println("imageName------------>"+image.toString());
		if (listImage==null||listImage.size()==0) {
			listImage = new ArrayList<Image>();
		}
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
		if("ac32abcc-235f-424a-9553-55ab647c86443".equals(mutiCode)){
			url = "redirect:/tourism/strage/fontIndexPageBanner";  //跳转到banner预览页
		}
		if("ad13bd22-a3cd-521a-a8c9-beb96e25ed89".equals(mutiCode)){
			url = "redirect:/tourism/strage/indexRecommedone"; //跳转到推荐位一预览页面
		}
		if("ca35adc9-b2bc-42cd-a8c9-abc42e34ab12".equals(mutiCode)){
			url = "redirect:/tourism/strage/indexRecommedtow";  //跳转到推荐位二
		}
		if("ad1ae219-123c-a2cd-b8c9-aeb96e25e3d9".equals(mutiCode)){
			url = "redirect:/tourism/strage/indexRecommedthree";  //跳转到推荐位三预览页面
		}
		if("".equals(mutiCode)){
			
		}

		return  url;
	}
	
	/**
	 * banner预览
	 * @return
	 */
	@RequestMapping("previewBanner")
	public String previewBanner(String url,ModelMap modelMap){
		//展示导航数据 接口 xiaozheng 提供
	    // 前台首页栏目code   e8876fe6-7608-11e4-b6ce-005056a05bc9
		List<Programa> programaList  = programaService.getSendPrograma("e8876fe6-7608-11e4-b6ce-005056a05bc9") ;
		
		// 首页 目的地接口   向文琪提供
		
        List<Destination>   destinationList  =   destinationService.getAllDestination();
		
        Map<String, List<Image>>    listImage  = imageService.getTravelHomeImg();
        //banner  游西藏  
        List<Image> bannerImage  =   null;
      //推荐位
        List<Image> homeRecomOneList  =   null ;
        List<Image> homeRecomTwoList  =   null ;
        List<Image> homeRecomThreeList  =   null ;
         if (listImage!=null) {
        	 bannerImage  =   listImage.get("travelBannerList");
        	 homeRecomOneList  = listImage.get("travelRecomOneList");
        	 homeRecomTwoList  = listImage.get("travelRecomTwoList");
			 homeRecomThreeList  = listImage.get("travelRecomThreeList");
			  
		}
      
        
        // 热门景点
        	List<View> views  = null;
                 views  = new ArrayList<View>();
            views  = contentService.getTravelView();   
        
        //综合攻略
        List<TravelVo>  zhContent  = tcontroller.getListTravelVo("1b808620-79bf-11e4-b6ce-005056a05bc9");
        
        //自驾游攻略
        List<TravelVo>  zjyContent  = tcontroller.getListTravelVo("1b851671-79bf-11e4-b6ce-005056a05bc9");
        
      //骑行攻略
        List<TravelVo>  qxContent  = tcontroller.getListTravelVo("1b8a6d4a-79bf-11e4-b6ce-005056a05bc9");
        	
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
        //预览banner
        Image bimg = new Image();
        bimg.setUrl(url);
        bannerImage.clear();
        bannerImage.add(bimg);
		modelMap.addAttribute("bannerImage", bannerImage);
		
		
        return "portal//app//tourism";
	}
	
	
	/**
	 * 通过景点收藏量排序
	 * @return
	 */
	@RequestMapping("orderByFav")
	public  ModelAndView orderByFav(){
		ModelAndView view = new ModelAndView("manage/html/travel/scenic-spots");
		List<Map<String,Object>> list     =  new ArrayList<Map<String,Object>>();
		List<Destination> destinationList = destinationService.getAllDestination();
		list =  viewService.getViewByWann(10,null);
		return view.addObject("list", list).addObject("destinationList",destinationList);
	}
	@RequestMapping("preView")
	public ModelAndView preView(String[] viewCode){
		ModelAndView view = new ModelAndView("redirect:/tourism/strage/fontViewPage");
		if (listview==null) {
			listview  = new ArrayList<View>();
		}
		for (String vc : viewCode) {
			View vw = viewService.findByCode(vc);
		    listview.add(vw);
		}

		return view;
	}
	
	
	public static void main(String[] args) {
        String s = "1,8,VIEW4214166926550047,4,5,VIEW4213180677933320,VIEW4213180677933320,VIEW4214166926550047,5,1";
        String[] viewCode = s.split(",");
        String viewCode1  = "";
        for (int i = 0; i < viewCode.length; i++) {
            viewCode1+=viewCode[i];
            if(i<viewCode.length-1)viewCode1+=",";
        }
        //System.out.println(viewCode1);
    }
	
}
