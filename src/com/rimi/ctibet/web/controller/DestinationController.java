
package com.rimi.ctibet.web.controller;


import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.annotation.Resource;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.groovy.GJson;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.ServletContextAware;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.rimi.ctibet.common.controller.BaseController;
import com.rimi.ctibet.common.exception.JsonParseException;
import com.rimi.ctibet.common.util.FileOperate;
import com.rimi.ctibet.common.util.FileUtil;
import com.rimi.ctibet.common.util.JsonUtil;
import com.rimi.ctibet.common.util.Pager;
import com.rimi.ctibet.common.util.ReadSettingProperties;
import com.rimi.ctibet.common.util.Uuid;
import com.rimi.ctibet.domain.Content;
import com.rimi.ctibet.domain.DesAndViewStatis;
import com.rimi.ctibet.domain.Destination;
import com.rimi.ctibet.domain.Image;
import com.rimi.ctibet.domain.LogUser;
import com.rimi.ctibet.domain.Merchant;
import com.rimi.ctibet.domain.MerchantType;
import com.rimi.ctibet.domain.Praise;
import com.rimi.ctibet.domain.UserView;
import com.rimi.ctibet.domain.View;
import com.rimi.ctibet.web.service.DestinationService;
import com.rimi.ctibet.web.service.IContentService;
import com.rimi.ctibet.web.service.IDesAndViewService;
import com.rimi.ctibet.web.service.IMerchantService;
import com.rimi.ctibet.web.service.IMerchantTypeService;
import com.rimi.ctibet.web.service.IPraiseAndViewManagerService;
import com.rimi.ctibet.web.service.IPraiseService;
import com.rimi.ctibet.web.service.IStrategyViewService;
import com.rimi.ctibet.web.service.IViewService;
import com.rimi.ctibet.web.service.ImageService;
import com.rimi.ctibet.web.service.IndexManagerService;
import com.rimi.ctibet.web.service.MutiImageService;
import com.rimi.ctibet.web.service.UserViewService;

/**
 * 目的地管理
 * @author xiangwq
 * @date 2014/11/19
 */
@Controller
@RequestMapping({"web/destinationController","tourism/des"})
public class DestinationController extends BaseController  implements ServletContextAware{
    
     public static  Logger LOGGER = Logger.getLogger(DestinationController.class);
   
    ServletContext application;
    
	@Resource
	private DestinationService destinationService;
	@Resource 
	private IViewService viewService;
	@Resource
	private IMerchantService merchantService;
	@Resource
	private IContentService contentService;
	@Resource
	private IMerchantTypeService merchantTypeService;
	@Resource
	private IPraiseService praiseService;
	@Resource
	private MutiImageService mutiService;
	@Resource
	private ImageService imageService;
	@Resource
	private IPraiseAndViewManagerService praseviewSerivce;
	
	@Resource
	private IndexManagerService indexManagerService;
	
	@Resource(name="desAndViewService")
	private IDesAndViewService desAndViewService;
	
	@Resource
	private IStrategyViewService strategyViewService;
	
	private Destination   destination  ;//预览目的地
	
	@Resource 
	private UserViewService userViewService; 
	
	
	@ModelAttribute
	public void setNavSelectIndex(HttpServletRequest request,HttpServletResponse response,ModelMap modelMap){
		modelMap.put("programNam","1");
	
	}
	
     /**
      * 得到目的地列表
      * @param destination_name 根据目的地名称模糊查询
      * @param pager
      * @param model
      * @return
      */
	@RequestMapping("getDestinationPager")
	public String getDestinationPager(Pager pager,ModelMap model)throws Exception{
         Pager resultPager = getDestinationService().getDestinationPager(pager,model);
         model.put("pager", resultPager);;
         return "manage/html/travel/des-info-manage";
	}
	
	/**
	 * 通过Code获取目的地详情
	 * @param destination_id
	 * @param model
	 * @return
	 */
	@RequestMapping("getDestinationDetailPreview")
	public String getDestinationDetailPreview(String destinationCode,ModelMap model){	
		Destination destination = getDestinationService().getDestinationByCode(destinationCode);
//		List<Content> hotviewlist = contentService.findContentByDestinationCodeAndType(destinationCode, "hotview");
		List<Content> travelList = contentService.findContentByDestinationCodeAndType(destinationCode, "travel");
//		List<Content> merchant1 = contentService.findContentByDestinationCodeAndType(destinationCode, "recMerchant1");
		List<Content> merchant2 = contentService.findContentByDestinationCodeAndType(destinationCode, "recMerchant2");
		List<Content> merchant3 = contentService.findContentByDestinationCodeAndType(destinationCode, "recMerchant3");
//		Content hotview = new Content();
//		if(hotviewlist.size()>0){
//			hotview = hotviewlist.get(0);
//		}
		//*******************初始化要传入前台的list***************************
//		List<View> frontViewList = new ArrayList<View>();
		List<Content> frontTravelList = new ArrayList<Content>();
		List<String> frontTravelUrlList = new ArrayList<String>();
//		List<Merchant> frontMerchantOneList = new ArrayList<Merchant>();
//		List<String> frontMerchantOneUrlList = new ArrayList<String>();
		List<Merchant> frontMerchantTwoList = new ArrayList<Merchant>();
		List<String> frontMerchantTwoUrlList = new ArrayList<String>();
		List<Merchant> frontMerchantThreeList = new ArrayList<Merchant>();
		List<String> frontMerchantThreeUrlList = new ArrayList<String>();
		List<Praise> travelPraiseList = new ArrayList<Praise>();
		List<Praise> merchantTwoPraiseList = new ArrayList<Praise>();
		List<Praise> merchantThreePraiseList = new ArrayList<Praise>();
//		String type1 = "";
		String type2 = "";
		String type3 = "";
		Map<String, Object> travelMap = new HashMap<String, Object>();
//		Map<String, Object> merchantOneMap = new HashMap<String, Object>();
		Map<String, Object> merchantTwoMap = new HashMap<String, Object>();
		Map<String, Object> merchantThreeMap = new HashMap<String, Object>();
		//**************************内容获取对象*************************
		if(travelList.size()>0&&merchant2.size()>0&&merchant3.size()>0){		
				if (merchantTypeService.findByCode(merchant2.get(0).getCreateuserCode())!=null) {
					type2 = merchantTypeService.findByCode(merchant2.get(0).getCreateuserCode()).getName();
				}
//				type1 = merchantTypeService.findByCode(merchant1.get(0).getCreateuserCode()).getName();
				if(merchantTypeService.findByCode(merchant3.get(0).getCreateuserCode())!=null){
					type3 = merchantTypeService.findByCode(merchant3.get(0).getCreateuserCode()).getName();
				}
					
		        //获取要传入前台的景点对象		
//				String[] viewCode = hotview.getContent().split(",");
//				for(String code : viewCode){
//					frontViewList.add(viewService.findByCode(code));
//				}
				//获取要传入前台的精彩攻略对象和url
				for(Content content : travelList){
					String code =  content.getContent();
					String url = content.getUrl();
					travelPraiseList.add(praiseService.getPraiseContentCode(code));
					Content c  = contentService.getByCode(code);
					c.setImgUrl(getContentImgSrc(c.getContent()));
		            frontTravelList.add(c);
		            frontTravelUrlList.add(url);
				}
				//获取要传入前台的商户推荐一和url
//				for(Content content : merchant1){
//					String code =  content.getContent();
//					String url = content.getUrl();
//					frontMerchantOneList.add(merchantService.getMerchantByCode(code));
//					frontMerchantOneUrlList.add(url);
//				}
				//获取要传入前台的商户推荐二和url
				for(Content content : merchant2){
					String code =  content.getContent();
					String url = content.getUrl();
					merchantTwoPraiseList.add(praiseService.getPraiseContentCode(code));
					frontMerchantTwoList.add(merchantService.getMerchantByCode(code));
					frontMerchantTwoUrlList.add(url);
				}		
				//获取要传入前台的商户推荐三和url
				for(Content content : merchant3){
					String code =  content.getContent();
					String url = content.getUrl();
					merchantThreePraiseList.add(praiseService.getPraiseContentCode(code));
					frontMerchantThreeList.add(merchantService.getMerchantByCode(code));
					frontMerchantThreeUrlList.add(url);
				}									
				travelMap.put("travelList", frontTravelList);
				travelMap.put("travelUrlList", frontTravelUrlList);
				travelMap.put("travelPraiseList", travelPraiseList);
//				merchantOneMap.put("merchantOneList", frontMerchantOneList);      
//				merchantOneMap.put("merchantOneUrlList", frontMerchantOneUrlList);
//				merchantOneMap.put("merchantOneType", type1);
				merchantTwoMap.put("merchantList", frontMerchantTwoList);
				merchantTwoMap.put("merchantUrlList", frontMerchantTwoUrlList);
				merchantTwoMap.put("merchantType", type2);
				merchantTwoMap.put("merchantPraiseList", merchantTwoPraiseList);
				merchantThreeMap.put("merchantList", frontMerchantThreeList);
				merchantThreeMap.put("merchantUrlList", frontMerchantThreeUrlList);
				merchantThreeMap.put("merchantType", type3);		
				merchantThreeMap.put("merchantPraiseList", merchantThreePraiseList);
		}	
						
//      model.put("hotviewList", frontViewList); //存到是景点对象
        model.put("travelMap", travelMap); // 存的是精彩旅程的攻略content对象，还有url
//      model.put("merchantOneMap", merchantOneMap); //猜的是推荐商位的merchant对象，url，还有商户类型name
        model.put("merchantTwoMap", merchantTwoMap);
        model.put("merchantThreeMap", merchantThreeMap);
        model.put("destination", destination);  
		
		return "portal/app/destination";
	}
	
	
	
	/**
	 * 通过Code获取目的地详情
	 * @param destination_id
	 * @param model
	 * @return
	 */
	@RequestMapping({"getDestinationDetail","detail"})
	public String getDestinatgionDetail(String destinationCode,ModelMap model){	
		
		SimpleDateFormat  simpleDateFormat =  new SimpleDateFormat("yyyy-MM-dd");
		String ct  =  simpleDateFormat.format(new Date());
		model.put("ct", ct);
		
		
		Destination destination = getDestinationService().getDestinationByCode(destinationCode);//查询目的地
		//获取关联的景点
		if (destination!=null) {
			
			List<View>  listView =  viewService.findViewByDestinationCode(destination.getCode());
			String viewString  = "";
			for (View view : listView) {
				viewString  = viewString+view.getViewName()+","; 
			}
			model.put("viewString", "{"+viewString+"}");
		}
		List<Content> hotviewlist = contentService.findContentByDestinationCodeAndType(destinationCode, "hotview");//查询景点内容
		List<View> frontViewList = new ArrayList<View>();
		if(hotviewlist.size()>0){
	     Content		hotview = hotviewlist.get(0);
			String[] viewCode = hotview.getContent().split(",");
			for(String code : viewCode){
				frontViewList.add(viewService.findByCode(code));
			}
		}
		
		List<Content> travelList = contentService.findContentByDestinationCodeAndType(destinationCode, "travel");//查询攻略内容
		
     	List<Content> merchant1 = contentService.findContentByDestinationCodeAndType(destinationCode, "recMerchant1");//查询商户1
		List<Content> merchant2 = contentService.findContentByDestinationCodeAndType(destinationCode, "recMerchant2");//查询商户2
		List<Content> merchant3 = contentService.findContentByDestinationCodeAndType(destinationCode, "recMerchant3");//查询商户3

		 
		List<Content> frontTravelList = new ArrayList<Content>();
		List<String> frontTravelUrlList = new ArrayList<String>();

		List<Merchant> frontMerchantOneList = new ArrayList<Merchant>();
		List<String> frontMerchantOneUrlList = new ArrayList<String>();
		
		List<Merchant> frontMerchantTwoList = new ArrayList<Merchant>();
		List<String> frontMerchantTwoUrlList = new ArrayList<String>();
		
		List<Merchant> frontMerchantThreeList = new ArrayList<Merchant>();
		List<String> frontMerchantThreeUrlList = new ArrayList<String>();
		
		List<Praise> travelPraiseList = new ArrayList<Praise>();
		List<Praise> merchantOnePraiseList = new ArrayList<Praise>();
		List<Praise> merchantTwoPraiseList = new ArrayList<Praise>();
		List<Praise> merchantThreePraiseList = new ArrayList<Praise>();
		
     	String type1 = "";
     	String type1Url = "";
		String type2 = "";
		String type2Url = "";
		String type3 = "";
		String type3Url = "";
		
		Map<String, Object> travelMap = new HashMap<String, Object>();

		Map<String, Object> merchantOneMap = new HashMap<String, Object>();
		Map<String, Object> merchantTwoMap = new HashMap<String, Object>();
		Map<String, Object> merchantThreeMap = new HashMap<String, Object>();
		//**************************内容获取对象*************************
		if(travelList.size()>0&&merchant2.size()>0&&merchant3.size()>0){	
			  
				if (merchantTypeService.findByCode(merchant1.get(0).getCreateuserCode())!=null) {
					MerchantType mrMerchantType =  merchantTypeService.findByCode(merchant1.get(0).getCreateuserCode());
					if (mrMerchantType!=null) {
						
						type1 = mrMerchantType.getName();
						type1Url =  mrMerchantType.getUrl();
					}
				}
			
			
				if (merchantTypeService.findByCode(merchant2.get(0).getCreateuserCode())!=null) {
					
					MerchantType mrMerchantType =  merchantTypeService.findByCode(merchant2.get(0).getCreateuserCode());
					if (mrMerchantType!=null) {
						
						type2 = mrMerchantType.getName();
						type2Url =  mrMerchantType.getUrl();
					}
				}

				if(merchantTypeService.findByCode(merchant3.get(0).getCreateuserCode())!=null){
					MerchantType mrMerchantType =  merchantTypeService.findByCode(merchant3.get(0).getCreateuserCode());
					if (mrMerchantType!=null) {
						type3 = mrMerchantType.getName();
						type3Url =  mrMerchantType.getUrl();
					}
				}
					
				//获取要传入前台的精彩攻略对象和url
				for(Content content : travelList){
					String code =  content.getContent();
					String url = content.getUrl();
					
					String number  = "" ;
					if (StringUtils.isNotBlank(url)&&url.lastIndexOf("code=")!=-1) {
					   number  =  url.split("code=")[1];
					}else{
						if ( url != null
								&& url.lastIndexOf(".html") != -1) {
							String code1 =  url.substring(
									url.lastIndexOf("/") + 1, url.lastIndexOf(".html"));
							number = code1;
						}
						
					}
					if (StringUtils.isNotBlank(code)) {
						 number  =  code;
					}
					
					Content  c  = contentService.getByCode(number);
					
					String vurl =  "";
					if (c!=null) {
					      vurl  = c.getUrl();
		            }
					 if (c!=null) {
						 if (c.getContent()!=null) {
				    	  	  c.setUrl( getContentImgSrc(c.getContent()) );//图片路径 内容中第一张图片
				       }
					}
					
					 Praise p =   new Praise();
					if (StringUtils.isNotBlank(number)) {
						 p = praiseService.getPraiseContentCode(number);
					} 
					travelPraiseList.add(p);
					
		            frontTravelList.add(c);
		            
		            
		            
		            
		            frontTravelUrlList.add(vurl);
				}
				
			    String m1  = "";
			    String m2  = "";
			    String m3  = "";
			    
				
				//获取要传入前台的商户推荐一和url
				for(Content content : merchant1){
					String code =  content.getContent();
					String url = content.getUrl();
					
					
					String number  = "" ;
					if (StringUtils.isNotBlank(url)&&url.lastIndexOf("code=")!=-1) {
						
					   number  =  url.split("code=")[1];
					   
					}else{
						
						if ( url != null
								&& url.lastIndexOf(".html") != -1) {
							String code1 =  url.substring(
									url.lastIndexOf("/") + 1, url.lastIndexOf(".html"));
							number = code1;
						}
					}
					if (StringUtils.isNotBlank(code)) {
						 number  =  code;
					}
					
					merchantOnePraiseList.add(praiseService.getPraiseContentCode(number));
					
					frontMerchantOneList.add(merchantService.getMerchantByCode(number));
					
					
					Merchant merchant  =  merchantService.getMerchantByCode(number);
					
					if (merchant!=null) {
					   url  =  merchant.getUrl();	
					}else{
						
						continue;
					}
					
					
					frontMerchantOneUrlList.add(url);
				}
				
				m1  = String.valueOf(this.validListNull(frontMerchantOneList));
				
				
				//获取要传入前台的商户推荐二和url
				for(Content content : merchant2){
					String code =  content.getContent();
					String url = content.getUrl();
					
					String number  = "" ;
					if (StringUtils.isNotBlank(url)&&url.lastIndexOf("code=")!=-1) {
						
					   number  =  url.split("code=")[1];
					   
					}else{
						
						if ( url != null
								&& url.lastIndexOf(".html") != -1) {
							String code1 =  url.substring(
									url.lastIndexOf("/") + 1, url.lastIndexOf(".html"));
							number = code1;
						}
					}
					if (StringUtils.isNotBlank(code)) {
						 number  =  code;
					}
						Merchant merchant  =  merchantService.getMerchantByCode(number);
					
					if (merchant!=null) {
					   url  =  merchant.getUrl();	
					}else{
						
						continue;
					}
					
					merchantTwoPraiseList.add(praiseService.getPraiseContentCode(number));
					frontMerchantTwoList.add(merchantService.getMerchantByCode(number));
					
					
					
					
					frontMerchantTwoUrlList.add(url);
				}		
				m2  = String.valueOf(this.validListNull(frontMerchantTwoList));
				//获取要传入前台的商户推荐三和url
				for(Content content : merchant3){
					String code =  content.getContent();
					String url = content.getUrl();
					
					String number  = "" ;
					if (StringUtils.isNotBlank(url)&&url.lastIndexOf("code=")!=-1) {
						
					   number  =  url.split("code=")[1];
					   
					}
					else{
						if ( url != null
								&& url.lastIndexOf(".html") != -1) {
							String code1 =  url.substring(
									url.lastIndexOf("/") + 1, url.lastIndexOf(".html"));
							number = code1;
						}
					}
					if (StringUtils.isNotBlank(code)) {
						 number  =  code;
					}
					
					Merchant merchant  =  merchantService.getMerchantByCode(number);
					
					if (merchant!=null) {
					   url  =  merchant.getUrl();	
					}
					else{
						
						continue;
					}
					
				
					
					merchantThreePraiseList.add(praiseService.getPraiseContentCode(code));
					frontMerchantThreeList.add(merchantService.getMerchantByCode(code));
					frontMerchantThreeUrlList.add(url);
				}		
				m3  = String.valueOf(this.validListNull(frontMerchantThreeList));
				travelMap.put("travelList", frontTravelList);
				travelMap.put("travelUrlList", frontTravelUrlList);
				travelMap.put("travelPraiseList", travelPraiseList);
				
				merchantOneMap.put("merchantList", frontMerchantOneList);      
				merchantOneMap.put("merchantUrlList", frontMerchantOneUrlList);
				merchantOneMap.put("merchantType", type1);
				merchantOneMap.put("merchantTypeUrl", type1Url);
				merchantOneMap.put("merchantPraiseList", merchantOnePraiseList);
				
				merchantTwoMap.put("merchantList", frontMerchantTwoList);
				merchantTwoMap.put("merchantUrlList", frontMerchantTwoUrlList);
				merchantTwoMap.put("merchantType", type2);
				merchantTwoMap.put("merchantTypeUrl", type2Url);
				merchantTwoMap.put("merchantPraiseList", merchantTwoPraiseList);
				
				merchantThreeMap.put("merchantList", frontMerchantThreeList);
				merchantThreeMap.put("merchantUrlList", frontMerchantThreeUrlList);
				merchantThreeMap.put("merchantType", type3);
				merchantThreeMap.put("merchantTypeUrl", type3Url);
				merchantThreeMap.put("merchantPraiseList", merchantThreePraiseList);
				
				
				
				model.put("m1", m1);
				model.put("m2", m2);
				model.put("m3", m3);
		}	
						
        model.put("views", frontViewList);
		model.put("hotviewList", hotviewlist); //存到是景点对象
        model.put("travelMap", travelMap); // 存的是精彩旅程的攻略content对象，还有url
        model.put("merchantOneMap",  merchantOneMap); //猜的是推荐商位的merchant对象，url，还有商户类型name
        model.put("merchantTwoMap", merchantTwoMap);
        model.put("merchantThreeMap", merchantThreeMap);
        model.put("destination", destination);  
		
		return "portal/app/destination";
	}
	
	
	public  boolean  validListNull(List list){
		 int count  =  0 ;
		if(list!=null){
			 for (Object object : list) {
				  if (object==null) {
					 count++;
				}
			}
			if (count==list.size()) {
				return true;
			}
			
			
		}
		return false;
		
		
		
	}

	
	/**
	 * 保存目的地
	 * @param destination
	 * @return
	 */
	@RequestMapping(value="/saveDestination",method=RequestMethod.POST)
	public ModelAndView saveDestination(HttpServletRequest request,HttpServletResponse response,Destination destination){
		 ModelAndView view = new ModelAndView("redirect:/web/destinationController/getDestinationPager");
		 String destinationIn = request.getParameter("destinationIntroduce");
		 destination.setDestinationSummary(destinationIn);
		 if (StringUtils.isNotBlank(request.getParameter("pv"))) {
			 this.destination = destination;//预览目的地数据
			 return view;
		}
		 getDestinationService().saveDestination(destination);
		 return view;
	}
	
	/**
	 * 删除目的地
	 * @param request
	 * @param response
	 * @param paramJson
	 * @return
	 */
	@RequestMapping("deleteDestination")
	public @ResponseBody String deleteDestination(HttpServletRequest request,HttpServletResponse response){
      
		
	   ModelAndView view = new ModelAndView("redirect:/web/destinationController/getDestinationPager");
	   String code = request.getParameter("code");
	   String[] codes = code.split(",");
	   for (String scode : codes) {
		   List<View> list  =  viewService.findViewByDestinationCode(scode);
		   if (list!=null&&list.size()>=1) {
			 return "error";
		  }
	   }
	   for (String scode : codes) {
		   getDestinationService().deleteByCode(scode);
		   strategyViewService.deleteBySql("delete from strategy_view where  destinationCode='"+scode+"'");
	   }
	   return "success";
	}
	/**
	 *批量删除 
	 */
	@RequestMapping("deleteDestinations")
	public ModelAndView deleteDestinations(HttpServletRequest request,HttpServletResponse response){
      
	   ModelAndView view = new ModelAndView("redirect:/web/destinationController/getDestinationPager");
	   String code = request.getParameter("code");
	   String[] codes = code.split(",");
	   for (String scode : codes) {
		    
		    getDestinationService().deleteByCode(scode);
	   }
	   return view;
	}
	/**
	 * 跳转到添加目的地界面
	 * @return
	 */
	@RequestMapping("/forDestinationAdd")
	public String forDestinationAdd(Model model){
	    return "manage/html/travel/des-info-manage-add";
	}
	
	/**
	 * 添加或者修改目的地详情
	 * @param model
	 * @param destinationCode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/destinationEdit")
	public String destinationEdit(ModelMap model,String destinationCode,HttpServletRequest request,HttpServletResponse response)throws Exception{
		Destination destination = null;
		if(StringUtils.isNotBlank(destinationCode)){
			destination = getDestinationService().getDestinationByCode(destinationCode);
			List<View> viewList = viewService.findViewByDestinationCode(destination.getCode());
			List<Content> hotviewlist = contentService.findContentByDestinationCodeAndType(destination.getCode(),"hotview");
			List<Content> travel = contentService.findContentByDestinationCodeAndType(destination.getCode(),"travel");
			if (travel==null) {
				 //travel  == null 从 内容中去 4条 默认查询  查看数去
				travel =  indexManagerService.getContentListByNumber(8, "2", Content.CONTENTTYPE_STRATEGY,destinationCode);
			}
			if (StringUtils.isNotBlank((String) request.getAttribute("travel"))) {
				  travel =  indexManagerService.getContentListByNumber(8, (String) request.getAttribute("travel"), Content.CONTENTTYPE_STRATEGY,destinationCode);
				  
			}
			
			List<MerchantType> typeList = merchantTypeService.getMerchantList();
			List<Content> merchant1 = contentService.findContentByDestinationCodeAndType(destinationCode, "recMerchant1");
			List<Content> merchant2 = contentService.findContentByDestinationCodeAndType(destinationCode, "recMerchant2");
			List<Content> merchant3 = contentService.findContentByDestinationCodeAndType(destinationCode, "recMerchant3");
			List<View> frontViewList = new ArrayList<View>();
			String type1="";//中文
			String type2="";
			String type3="";
			String  typeCod1  = "";
			String  typeCod2  = "";
			String  typeCod3  = "";
			if(merchant1!=null&&merchant1.size()>0&&merchant2!=null&&merchant2.size()>0&&merchant3!=null&&merchant3.size()>0)
			{
				Content c1 = merchant1.get(0);
				if (merchantTypeService.findByCode(merchant1.get(0).getCreateuserCode())!=null) {
					type1 = merchantTypeService.findByCode(merchant1.get(0).getCreateuserCode()).getName();
					typeCod1  = merchantTypeService.findByCode(merchant1.get(0).getCreateuserCode()).getCode();
				}
				
				if (merchantTypeService.findByCode(merchant2.get(0).getCreateuserCode())!=null) {
					type2 = merchantTypeService.findByCode(merchant2.get(0).getCreateuserCode()).getName();
					typeCod2  = merchantTypeService.findByCode(merchant2.get(0).getCreateuserCode()).getCode();
					
				}
				
				
				
				
				if (merchantTypeService.findByCode(merchant3.get(0).getCreateuserCode())!=null) {
					type3 = merchantTypeService.findByCode(merchant3.get(0).getCreateuserCode()).getName();		
					typeCod3  = merchantTypeService.findByCode(merchant3.get(0).getCreateuserCode()).getCode();
				}
				
				
				
			}
			
			String   pgType1 = request.getParameter("pgType1");
			if (StringUtils.isNotBlank(pgType1)) {
				
				type1 = merchantTypeService.findByCode(pgType1).getName();
				typeCod1  = merchantTypeService.findByCode(pgType1).getCode();
				//查询商户
				List<Map<String, Object>>  merchantList  =  merchantService.getMUrlAndCodeByMType(pgType1, 4,null);
				if (merchantList!=null) {
					 merchant1  = new ArrayList<Content>(); 
					for (Map<String, Object> map : merchantList) {
						   Content content  =   new Content();
						   content.setContent((String)map.get("code"));
						   content.setUrl((String)map.get("url"));
							merchant1.add(content);	   
					 }
				}
				
			}
			
			String   pgType2 = request.getParameter("pgType2");
			if (StringUtils.isNotBlank(pgType2)) {
				
				type2 = merchantTypeService.findByCode(pgType2).getName();
				typeCod2  = merchantTypeService.findByCode(pgType2).getCode();
				//查询商户
				List<Map<String, Object>>  merchantList  =  merchantService.getMUrlAndCodeByMType(pgType2, 4,null);
				if (merchantList!=null) {
					 merchant2  = new ArrayList<Content>(); 
					for (Map<String, Object> map : merchantList) {
						   Content content  =   new Content();
						   content.setContent((String)map.get("code"));
						   content.setUrl((String)map.get("url"));
						   merchant2.add(content);	   
					 }
				}
				
			}
			
			
			String   pgType3 = request.getParameter("pgType3");
			if (StringUtils.isNotBlank(pgType3)) {
				type3 = merchantTypeService.findByCode(pgType3).getName();
				typeCod3  = merchantTypeService.findByCode(pgType3).getCode();
				//查询商户
				List<Map<String, Object>>  merchantList  =  merchantService.getMUrlAndCodeByMType(pgType3, 4,null);
				if (merchantList!=null) {
					 merchant3  = new ArrayList<Content>(); 
					for (Map<String, Object> map : merchantList) {
						   Content content  =   new Content();
						   content.setContent((String)map.get("code"));
						   content.setUrl((String)map.get("url"));
							merchant3.add(content);	   
					 }
				}
				
			}
			
			Content hotview = new Content();
			if(hotviewlist.size()>0){
				hotview = hotviewlist.get(0);
				String[] viewCode = hotview.getContent().split(",");
				for(String code : viewCode){
					frontViewList.add(viewService.findByCode(code));
				}
			}
		    String order  = request.getParameter("order");
		    //想去
		    if (StringUtils.isNotBlank(order)&&order.equals("1")) {
			      List<Map<String, Object>> viewByWann = viewService.getViewByWann(10,destinationCode);
			      model.put("hotviewList", viewByWann);
			      request.setAttribute("gw", order);
			}else if(StringUtils.isNotBlank(order)&&order.equals("0")){
				  List<Map<String, Object>> viewByWann = viewService.getViewByGone(10,destinationCode);
				  model.put("hotviewList", viewByWann);
				  request.setAttribute("gw", order);
			}else{
				model.put("hotviewList", frontViewList);
			}
		    model.put("viewList", viewList);
		    model.put("hotview", hotview);
			model.put("destination", destination);
		
			model.put("travel", travel);
			model.put("typeList", typeList);
			model.put("merchant1", merchant1);
			model.put("merchant2", merchant2);
			model.put("merchant3", merchant3);
			model.put("type1", type1);
			model.put("type2", type2);
			model.put("type3", type3);
			model.put("typeCod1", typeCod1);
			model.put("typeCod2", typeCod2);
			model.put("typeCod3", typeCod3);
			return "manage/html/travel/des-info-manage-edit";
		}else{
			return "manage/html/travel/des-info-manage-add";
		}
	}
	/**
	 * 保存热门景点
	 * @param viewCode 景点Code的数组
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping("saveHotView")
	public void saveHotView(String[] viewCode, HttpServletRequest request,HttpServletResponse response, ModelMap model) {
		String dCode = request.getParameter("destinationCode");
		String pCode = request.getParameter("programaCode");
		String hotviewCode = request.getParameter("contentCode");
	    if(StringUtils.isNotBlank(dCode)&&StringUtils.isNotBlank(pCode))	{	    	
	    	getDestinationService().saveHotView(dCode,pCode,hotviewCode,viewCode);
	    }   
	}
	/**
	 * 保存精彩旅程
	 * @param urls
	 * @param codes
	 * @param contentCode
	 * @param request
	 * @param response
	 * @param model
	 */
	@RequestMapping("saveTravel")
	public void saveTravel(String[] urls,String[] code,String[] contentCode,HttpServletRequest request,HttpServletResponse response, ModelMap model){
		String dCode = request.getParameter("destinationCode");
		String pCode = request.getParameter("programaCode");
	    if(StringUtils.isNotBlank(dCode)&&StringUtils.isNotBlank(pCode))	{	    	
	    	getDestinationService().saveTravel(dCode,pCode,contentCode,urls,code);
	    }   
	}
	/**
	 * 保存商户推荐
	 * @param urls
	 * @param codes
	 * @param contentCode
	 * @param request
	 * @param response
	 * @param model
	 */
	@RequestMapping("saveRecomMerchant")
	public void saveRecomMerchant(String[] urls,String[] codes,String[] contentCode,HttpServletRequest request,HttpServletResponse response, ModelMap model){
		String dCode = request.getParameter("destinationCode");
		String pCode = request.getParameter("programaCode");
		String type = request.getParameter("merchantType");
		String order = request.getParameter("order");
		String typeCode = request.getParameter("typeCode");
	    if(StringUtils.isNotBlank(dCode)&&StringUtils.isNotBlank(pCode))	{	    	
	    	getDestinationService().saveRecomMerchant(order,dCode,pCode,contentCode,urls,codes,type,typeCode);
	     }   
	}
	/**
	 * 用户点击去过数和想去数
	 * @param option
	 * @param destinationCode
	 * @return
	 */
	@RequestMapping("goneandwant")
	public @ResponseBody String goneandwant(String option,String destinationCode,String cuserCode){
		
		Destination destination = getDestinationService().getDestinationByCode(destinationCode);
		//保存用户想去的，和去过的先验证是否存在
		LogUser  logUser =  super.getFrontUser();
		if (logUser==null) {
			  return "redirect:/portal/clientLog/loginPage";
		}
		
		if("1".equals(option)){
			UserView userView   =  userViewService.getUserDes(logUser.getCode(), destinationCode, "gone");
			if (userView!=null) {
				 return "exit";
			}
			destination.setFalseGoneCount(destination.getFalseGoneCount()+1);
			destination.setGoneCount(destination.getGoneCount()+1);
			getDestinationService().saveGoneWant(destination);
			
			UserView userView2 =  new UserView();
			userView2.setAreaCode(destinationCode);
			userView2.setAvaliable(1);
			userView2.setMemberCode(logUser.getCode());
			userView2.setType("gone");
			//userView2.setViewCode(viewCode)
			userViewService.save(userView2);
			
			
			//统计
			
			
			DesAndViewStatis andViewStatis = new DesAndViewStatis();
			andViewStatis.setCtime(new Date());
			andViewStatis.setCuserCode(logUser.getCode());
			andViewStatis.setDesCode(destinationCode);
			andViewStatis.setType("1");
			desAndViewService.save(andViewStatis);
			
			
			return String.valueOf(destination.getFalseGoneCount());
		}else{
			
			
			UserView userView   =  userViewService.getUserDes(logUser.getCode(), destinationCode, "wanna");
			if (userView!=null) {
				 return "exit";
			}
			destination.setFalseWantCount(destination.getFalseWantCount()+1);
			destination.setWantCount(destination.getWantCount()+1);
			getDestinationService().saveGoneWant(destination);
			UserView userView2 =  new UserView();
			userView2.setAreaCode(destinationCode);
			userView2.setAvaliable(1);
			userView2.setMemberCode(logUser.getCode());
			userView2.setType("wanna");
			//userView2.setViewCode(viewCode)
			userViewService.save(userView2);
			DesAndViewStatis andViewStatis = new DesAndViewStatis();
			andViewStatis.setCtime(new Date());
			andViewStatis.setCuserCode(logUser.getCode());
			andViewStatis.setDesCode(destinationCode);
			andViewStatis.setType("0");
			desAndViewService.save(andViewStatis);
			return String.valueOf(destination.getFalseWantCount());
		}
	}
	/**
	 * 获得真实数量
	 * @param option
	 * @param destinationCode
	 * @return
	 */
	@RequestMapping("getrealcount")
    public @ResponseBody String getrealcount(String option,String destinationCode){
		Destination destination = getDestinationService().getDestinationByCode(destinationCode);
		if("1".equals(option)){
			return String.valueOf(destination.getWantCount());
		}else{
			return String.valueOf(destination.getGoneCount());
		}
	}
	/**
	 * 目的地图集管理
	 * @param destinationCode
	 * @param model
	 * @return
	 */
	@RequestMapping("imageManaege")
	public String imageManaege(String destinationCode,String option,ModelMap model){	
		/*List<MutiImage> mutiList = mutiService.getMutiImageByProgramaCode(destinationCode);
		MutiImage officialMuti = new MutiImage();
		MutiImage userMuti = new MutiImage();
		if(mutiList!=null && mutiList.size()>0){
			for(MutiImage muti : mutiList){
				if("1".equals(muti.getIsOfficial())){
					//1为官方推荐，0为用户图集
					officialMuti = muti;				
				}
				else{
					userMuti = muti;
				}
			}
		}
		Pager pager = new Pager();
		//option 1为官方  2为用户
		
		if("1".equals(option)){			
			pager = imageService.findImagePager(officialMuti.getCode(),pager);
			model.put("official",pager);
		}else if("2".equals(option)){
			pager = imageService.findImagePager(userMuti.getCode(), pager);
			model.put("user",pager);
		}
		model.put("officialMuti", officialMuti);
		model.put("userMuti", userMuti);
		model.put("destinationCode", destinationCode);*/
	    //以上是之前的代码
        model.addAttribute("destinationCode", destinationCode);
		return "manage/html/travel/des-atlas-management";
	}
    /**
     * 获取目的地图集
     */
    @RequestMapping(value="loadDestinationAtlas", produces = "text/html; charset=utf-8")
    @ResponseBody
    public String loadDestinationAtlas(Pager pager, String destinationCode, String type){
        pager = imageService.getDestinationCodeAtlasByCodeType(pager, destinationCode, type);
        return obj2json(pager);
    }
    /**
     * 删除图集图片
     */
    @RequestMapping(value="deleteImage", produces = "text/html; charset=utf-8")
    @ResponseBody
    public String deleteImage(String[] codes, String[] urls){
        try {
            //imageService.deleteImages(application.getRealPath("/"), codes);
            imageService.deleteAtlasByUrls(application.getRealPath("/"), urls);
            return "success";
        } catch (Exception e) {
            e.printStackTrace();
            return "error";
        }
    }
	
	@RequestMapping(value = "getAjaxImage", method = RequestMethod.POST)
	public @ResponseBody ModelAndView getAjaxImage(HttpServletRequest request,HttpServletResponse response,Pager pager){
		Map<String, Object> resultMap = new HashMap<String, Object>();
		String code = request.getParameter("mutiCode");
		pager = imageService.findImagePager(code, pager);
		resultMap.put("data", pager);
		return jsonView(resultMap);
	}

	/********************************************
     * other method
     ********************************************/
    /**
     * 保存目的地官方图集
     */
    @RequestMapping(value="uploadImages", produces = "text/html; charset=utf-8")
    @ResponseBody
    public String uploadImages(String destinationCode, String floderName, @RequestParam MultipartFile file){
        Map<String, String> msgMap = new HashMap<String, String>();
        if(floderName==null || floderName.equals("")){
            LOGGER.info("需要传一个文件夹名字。");
        }
        if(file!=null){
            String basePath = application.getRealPath("/");
            String path = ReadSettingProperties.getValue("upload") + "/img/destinationImg/" + destinationCode + "/destinationAtlas/";
            String time = new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date(System.currentTimeMillis()));
            time += new DecimalFormat("0000").format(new Random().nextInt(10000));
            String ext = FileOperate.getFileExt(file.getOriginalFilename(), true);
            String fileName = time + ext;
            try {
                FileUtil.uploadFile(file, basePath + path, fileName);
                msgMap.put("filePath", path + fileName);
                msgMap.put("fileName", file.getOriginalFilename());
                
                Image image = new Image();
                image.setAvaliable(1);
                image.setCode(Uuid.uuid());
                image.setUrl(path + fileName);
                image.setMutiCode(destinationCode);
                image.setDestinationCode(destinationCode);
                image.setCreateTime(new Timestamp(System.currentTimeMillis()));
                image.setSummary(Image.ATLAS_OFFICAL);
                imageService.save(image);
                
            } catch (Exception e) {
                e.printStackTrace();
                msgMap.put("msg", file.getOriginalFilename() + "上传失败！");
                msgMap.put("errorImage", file.getOriginalFilename());
            }
        }
        return obj2json(msgMap);
    }
	
    @RequestMapping("getViewAndGone")
    @ResponseBody
    public String  getViewAndGone(ModelMap model,HttpServletRequest request,HttpServletResponse response) throws Exception{
      	String order = request.getParameter("order");
      	
      	 //想去
	    if (StringUtils.isNotBlank(order)&&order.equals("1")) {
		      List<Map<String, Object>> viewByWann = viewService.getViewByWann(10,request.getParameter("destinationCode"));
		      //model.put("hotviewList", viewByWann);
		  	 return  JSONArray.fromObject(viewByWann).toString();
		      //request.setAttribute("gw", order);
		}else if(StringUtils.isNotBlank(order)&&order.equals("0")){
			  List<Map<String, Object>> viewByWann = viewService.getViewByGone(10,request.getParameter("destinationCode"));
			  //model.put("hotviewList", viewByWann);
			  return  JSONArray.fromObject(viewByWann).toString();
		}
      	//想去数
    	return   "";
    }
    
    
    /**
     * 
     * @return
     */
    @RequestMapping("previewDes")
    @ResponseBody
    public String previewDes(ModelMap model,HttpServletRequest request,HttpServletResponse response){
    	
    	String desCode  = request.getParameter("destinationCode");
    	
    	
    	String   pgType1 = request.getParameter("pgType1");
    	
    	
		if (StringUtils.isNotBlank(pgType1)) {
			
			String type1 = merchantTypeService.findByCode(pgType1).getName();
			String typeCod1 = merchantTypeService.findByCode(pgType1).getCode();
			//查询商户
			List<Map<String, Object>>  merchantList  =  merchantService.getMUrlAndCodeByMType(pgType1, 4,desCode);
			if (merchantList!=null) {
				 ArrayList<Content> merchant1 = new ArrayList<Content>(); 
				for (Map<String, Object> map : merchantList) {
					   Content content  =   new Content();
					   content.setContent((String)map.get("code"));
					   content.setUrl((String)map.get("url"));
						merchant1.add(content);	   
				 }
				return JSONArray.fromObject(merchant1).toString();
			}
			
		}
		
		String   pgType2 = request.getParameter("pgType2");
		if (StringUtils.isNotBlank(pgType2)) {
			
			String type2 = merchantTypeService.findByCode(pgType2).getName();
			String typeCod2 = merchantTypeService.findByCode(pgType2).getCode();
			//查询商户
			List<Map<String, Object>>  merchantList  =  merchantService.getMUrlAndCodeByMType(pgType2, 4,desCode);
			if (merchantList!=null) {
				 ArrayList<Content> merchant2 = new ArrayList<Content>(); 
				for (Map<String, Object> map : merchantList) {
					   Content content  =   new Content();
					   content.setContent((String)map.get("code"));
					   content.setUrl((String)map.get("url"));
					   merchant2.add(content);	   
				 }
				return JSONArray.fromObject(merchant2).toString();
			}
			
		}
		
		
		String   pgType3 = request.getParameter("pgType3");
		if (StringUtils.isNotBlank(pgType3)) {
			String type3 = merchantTypeService.findByCode(pgType3).getName();
			String typeCod3 = merchantTypeService.findByCode(pgType3).getCode();
			//查询商户
			List<Map<String, Object>>  merchantList  =  merchantService.getMUrlAndCodeByMType(pgType3, 4,desCode);
			if (merchantList!=null) {
				 ArrayList<Content> merchant3 = new ArrayList<Content>(); 
				for (Map<String, Object> map : merchantList) {
					   Content content  =   new Content();
					   content.setContent((String)map.get("code"));
					   content.setUrl((String)map.get("url"));
						merchant3.add(content);	   
				 }
				return JSONArray.fromObject(merchant3).toString();
			}
			
		}
		return  "";
    }
    
    
    /********************************************
     * Setter Getter
     ********************************************/
    @Override
    public void setServletContext(ServletContext application) {
        this.application = application;
    }
    
   @RequestMapping("strageOrder")
   @ResponseBody
    public String strageOrder(ModelMap model,HttpServletRequest request,HttpServletResponse response) throws Exception{
    	
    	String desCode  = request.getParameter("des");
	    request.setAttribute("travel", request.getParameter("travel"));
	    List<Content>    travel =  indexManagerService.getContentListByNumber(8, (String) request.getAttribute("travel"), Content.CONTENTTYPE_STRATEGY,desCode);
    	return    JSONArray.fromObject(travel).toString();
    }
   
   
   /**
    * 验证目的地是否重复
    * @return
    */
   @RequestMapping("validDesRt")
   @ResponseBody
   public String validDesRt(ModelMap model,HttpServletRequest request,HttpServletResponse response){
	   
	   String desName = request.getParameter("desName");
	   String desCode = request.getParameter("desCode");
	   String sql = "SELECT   COUNT(id)  from  destination  WHERE  destination.avaliable=1 and  destinationName='"+ desName+ "'   ";
	   if (StringUtils.isNotBlank(desCode)) {
//		   sql +=  "and `code`!='"+desCode+"' ";
		   Destination des = getDestinationService().findByCode(desCode);
		   int result = getDestinationService().findCountBySql(sql);
		   if(result==0){
			   return "false";
		   }else if(result ==1){
			   if(desName.equals(des.getDestinationName())){
				   return "false";
			   }else{
				   return "true";
			   }
			   
		   }else{
			   return "true";
		   }
	   }else{
		   int result = getDestinationService().findCountBySql(sql);
		   return  result==0?"false":"true";
	   }
   }
	
	public void setDestinationService(DestinationService destinationService) {
		this.destinationService = destinationService;
	}
	
	public DestinationService getDestinationService() {
		return destinationService;
	}
   
    
    
}






