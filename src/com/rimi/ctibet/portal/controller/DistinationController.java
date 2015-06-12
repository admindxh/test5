package com.rimi.ctibet.portal.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.google.gson.Gson;
import com.rimi.ctibet.common.util.LuceneUtil;
import com.rimi.ctibet.common.util.Pager;
import com.rimi.ctibet.domain.Content;
import com.rimi.ctibet.domain.Destination;
import com.rimi.ctibet.domain.Image;
import com.rimi.ctibet.domain.Merchant;
import com.rimi.ctibet.domain.MutiImage;
import com.rimi.ctibet.domain.Praise;
import com.rimi.ctibet.domain.Programa;
import com.rimi.ctibet.domain.View;
import com.rimi.ctibet.web.service.DestinationService;
import com.rimi.ctibet.web.service.IContentService;
import com.rimi.ctibet.web.service.IMerchantService;
import com.rimi.ctibet.web.service.IMerchantTypeService;
import com.rimi.ctibet.web.service.IPraiseService;
import com.rimi.ctibet.web.service.IProgramaService;
import com.rimi.ctibet.web.service.IViewService;
import com.rimi.ctibet.web.service.ImageService;
import com.rimi.ctibet.web.service.MutiImageService;

/**
 *门户目的地前台controller
 */
@Controller
@RequestMapping("portal/distination")
public class DistinationController {
	
	@Resource
	private IMerchantService merchantService;
	@Resource
	private IViewService viewService;
	@Resource
	private DestinationService destinationService;
	@Resource
	private IContentService contentService;
	@Resource
	private IMerchantTypeService merchantTypeService;
	@Resource
	private IProgramaService programaService;
	@Resource
	private IPraiseService praiseService;
	@Resource
	private MutiImageService mutiServcie;
	@Resource
	private ImageService imageService;

	//目的地攻略列表
	@RequestMapping("getDistinationStrategyList")
	public @ResponseBody Map getDistinationStrategyList(HttpServletRequest request,HttpServletResponse response){
		return null;
	}
	//目的地攻略详情
	@RequestMapping("getDistinationStrategyDetail")
	public @ResponseBody Map getDistinationStrategyDetail(HttpServletRequest request,HttpServletResponse response){
		return null;
	}
	//攻略收藏
	@RequestMapping("collectDistinationStrategy")
	public @ResponseBody Map collectDistinationStrategy(HttpServletRequest request,HttpServletResponse response){
		return null;
	}
	//美食商户列表
	@RequestMapping("getFoodMerchatList")
	public  ModelAndView getFoodMerchatList(Pager pager,HttpServletRequest request,HttpServletResponse response){
		pager = merchantService.getMerchantByMerchantType(pager, "169245f3-c2df-4dbd-9655-76f96e1532e4");
		ModelAndView view = new ModelAndView();
		view.addObject(pager);
		return view;
	}
	//美食详情页
	@RequestMapping("getFoodMerchantDetail")
	public @ResponseBody Map getFoodMerchantDetail(HttpServletRequest request,HttpServletResponse response){
	    Map<String,Object> merchantInfo = new HashMap<String,Object>();
		String code = request.getParameter("code");
	    Merchant merchant = merchantService.getMerchantByCode(code);
	    View view = viewService.findByCode(merchant.getViewCode());
	    merchantInfo.put("merchantInfo", merchant);
	    merchantInfo.put("viewName", view.getViewName());
	    return merchantInfo;
	}
	//搜索美食
	@RequestMapping("searchFoodByLucene")
	public @ResponseBody Map searchFoodByLucene(HttpServletRequest request,HttpServletResponse response) {
		String keyWord = request.getParameter("keyWord");
		List<HashMap<String,String>> documents = LuceneUtil.search(keyWord,"","");
		Map indexInfo = new HashMap<String,String>();
		indexInfo.put("indexInfo", documents);
		return indexInfo;
	}
	//美食预定
	@RequestMapping("orderFood")
	public @ResponseBody Map orderFood(HttpServletRequest request,HttpServletResponse response) {
		return null;
	}
//	//官方美食指南
//	@RequestMapping("officialFoodGuide")
	public @ResponseBody Map officialFoodGuide(HttpServletRequest request,HttpServletResponse response){
		return null;
	}
	//美食收藏
	@RequestMapping("collectFood")
	public @ResponseBody Map collectFood(HttpServletRequest request,HttpServletResponse response){
		return null;
	}
	//住宿商户列表
	@RequestMapping("getLiveMerchatList")
	public @ResponseBody ModelAndView getLiveMerchatList(Pager pager,HttpServletRequest request,HttpServletResponse response){
		pager = merchantService.getMerchantByMerchantType(pager, "169245f3-c2df-4dbd-9655-76f96e1532e4");
		ModelAndView view = new ModelAndView();
		view.addObject(pager);
		return view;
	}
   //住宿详情页
	@RequestMapping("getLiveMerchantDetail")
	public @ResponseBody Map getLiveMerchantDetail(HttpServletRequest request,HttpServletResponse response){
	    Map<String,Object> merchantInfo = new HashMap<String,Object>();
		String code = request.getParameter("code");
	    Merchant merchant = merchantService.getMerchantByCode(code);
	    View view = viewService.findByCode(merchant.getViewCode());
	    merchantInfo.put("merchantInfo", merchant);
	    merchantInfo.put("viewName", view.getViewName());
	    return merchantInfo;
	}
    //搜索住宿
	@RequestMapping("searchLiveByLucene")
	public @ResponseBody Map searchLiveByLucene(HttpServletRequest request,HttpServletResponse response){
		String keyWord = request.getParameter("keyWord");
		List<HashMap<String,String>> documents = LuceneUtil.search(keyWord,"","");
		Map indexInfo = new HashMap<String,String>();
		indexInfo.put("indexInfo", documents);
		return indexInfo;
	}
	//住宿预定
	@RequestMapping("orderLive")
	public @ResponseBody Map orderLive(HttpServletRequest request,HttpServletResponse response) {
		return null;
	}
	//官方住宿指南
	@RequestMapping("officialLiveGuide")
	public @ResponseBody Map officialLiveGuide(HttpServletRequest request,HttpServletResponse response){
		return null;
	}
	//住宿收藏
	@RequestMapping("collectLive")
	public @ResponseBody Map collectLive(HttpServletRequest request,HttpServletResponse response){
		return null;
	}
	/**
	 * 目的地详情页中banner页面
	 */
	@RequestMapping("desbanner")
	public String desbanner(HttpServletRequest request,HttpServletResponse response,String destinationCode,ModelMap model){
		//System.out.println("desbanner-------->"+destinationCode);
		Destination destination = destinationService.getDestinationByCode(destinationCode);
		model.put("destination", destination);
		return "portal/app/banner/des_banner";
	}
	/**
	 * 目的地详情页中景点详情页
	 */
	@RequestMapping("tourist")
	public String tourist(HttpServletRequest request,HttpServletResponse response,String destinationCode,ModelMap model){
		//System.out.println("tourist-------->"+destinationCode);
		Destination destination = destinationService.getDestinationByCode(destinationCode);
		List<Content> hotviewlist = contentService.findContentByDestinationCodeAndType(destinationCode, "hotview");
		List<Content> merchant1 = contentService.findContentByDestinationCodeAndType(destinationCode, "recMerchant1");
		Content hotview = new Content();
		if(hotviewlist.size()>0){
			hotview = hotviewlist.get(0);
		}
		//*******************初始化要传入前台的list***************************
		List<View> frontViewList = new ArrayList<View>();
		List<Merchant> frontMerchantOneList = new ArrayList<Merchant>();
		List<String> frontMerchantOneUrlList = new ArrayList<String>();
		List<Praise> merchantOnePraiseList = new ArrayList<Praise>();
		String type1 = "";
		Map<String, Object> merchantOneMap = new HashMap<String, Object>();
		//**************************内容获取对象*************************
		if(hotviewlist.size()>0&&hotviewlist.size()>0&&merchant1.size()>0){	
				if(merchantTypeService.findByCode(merchant1.get(0).getCreateuserCode())!=null){
					type1 = merchantTypeService.findByCode(merchant1.get(0).getCreateuserCode()).getName();
				}
		        //获取要传入前台的景点对象		
				String[] viewCode = hotview.getContent().split(",");
				for(String code : viewCode){
					frontViewList.add(viewService.findByCode(code));
				}

				//获取要传入前台的商户推荐一和url
				for(Content content : merchant1){
					String code =  content.getContent();
					String url = content.getUrl();
					frontMerchantOneList.add(merchantService.getMerchantByCode(code));
					frontMerchantOneUrlList.add(url);
					merchantOnePraiseList.add(praiseService.getPraiseContentCode(code));
				}
				merchantOneMap.put("merchantList", frontMerchantOneList);      
				merchantOneMap.put("merchantUrlList", frontMerchantOneUrlList);
				merchantOneMap.put("merchantType", type1);
				merchantOneMap.put("merchantPraiseList", merchantOnePraiseList);
				
		}	
						
        model.put("hotviewList", frontViewList); //存到是景点对象
        model.put("merchantOneMap", merchantOneMap); //猜的是推荐商位的merchant对象，url，还有商户类型name
        model.put("destination", destination);  
		
		
		return "portal/app/tourism/des-tourist";
	}
	
	/**
	 * 目的地详情头部
	 */
	@RequestMapping("desheader")
	public String desheader(HttpServletRequest request,HttpServletResponse response,ModelMap model){
		List<Programa> programaList  = programaService.getSendPrograma("e8876fe6-7608-11e4-b6ce-005056a05bc9");
		List<Destination> destinationList = destinationService.getAllDestination();
		model.put("programaList", programaList);
		model.put("destinationList", destinationList);	
	    return "portal/app/header/index";
	}
	/**
	 * 跳转至目的地图片集
	 * @param desitnationCode
	 * @param model
	 * @return
	 */
	@RequestMapping("gallery")
	public String gallery(String destinationCode,ModelMap model){
		Destination destination = destinationService.getDestinationByCode(destinationCode);
		//通过目的地code 获取对应的官方图集和用户图集
		List<MutiImage> mutiList = mutiServcie.getMutiImageByProgramaCode(destinationCode);
		MutiImage officialMuti = null;
		MutiImage userMuti = null;
		for(MutiImage muti : mutiList){
			if(muti.getIsOfficial().equals("1")){
				officialMuti=muti;
			}else{
				userMuti = muti;
			}
		}
		//通过官方图集查找相应的图片pager
		Pager pager = new Pager();
		pager.setPageSize(32);
		pager = imageService.findImagePager(officialMuti.getCode(), pager);
		model.put("pager", pager);
		model.put("officialMuti", officialMuti);
		model.put("userMuti", userMuti);	
		model.put("destination", destination);
	    return "portal/app/tourism/photo_gallery";	
	}
	/**
	 * ajax分页
	 */
	@RequestMapping(value="/getAjaxPager",method=RequestMethod.POST)
	public @ResponseBody String getAjaxPager(String destinationCode,String flag,String mutiCode,String currentPage){
		Pager pager = new Pager();
		pager.setPageSize(32);
		pager.setCurrentPage(Integer.parseInt(currentPage));
		//如果是官方图集
		if("official".equals(flag)){
			pager = imageService.findImagePager(mutiCode, pager);
		}
		//如果是用户图集
		if("user".equals(flag)){
			//用户图集需要图片信息，和上传图片的用户信息，图片来自哪里的信息
			pager = imageService.findUserImgPager(destinationCode,mutiCode,pager);
		}
		return new Gson().toJson(pager);
	}
	
	
	
}
