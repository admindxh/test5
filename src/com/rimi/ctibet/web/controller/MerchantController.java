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

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.rimi.ctibet.common.controller.BaseController;
import com.rimi.ctibet.common.util.CodeFactory;
import com.rimi.ctibet.common.util.ConnectionUtil;
import com.rimi.ctibet.common.util.FileHelper;
import com.rimi.ctibet.common.util.LuceneUtil2;
import com.rimi.ctibet.common.util.Pager;
import com.rimi.ctibet.common.util.ReadSettingProperties;
import com.rimi.ctibet.common.util.StringUtil;
import com.rimi.ctibet.common.util.WebSearch;
import com.rimi.ctibet.domain.Content;
import com.rimi.ctibet.domain.Destination;
import com.rimi.ctibet.domain.Image;
import com.rimi.ctibet.domain.Merchant;
import com.rimi.ctibet.domain.MerchantAndView;
import com.rimi.ctibet.domain.MerchantManage;
import com.rimi.ctibet.domain.MerchantType;
import com.rimi.ctibet.domain.Praise;
import com.rimi.ctibet.domain.View;
import com.rimi.ctibet.portal.controller.FrontMerchatController;
import com.rimi.ctibet.portal.controller.vo.HotMerchantVo;
import com.rimi.ctibet.portal.controller.vo.MerchantVo;
import com.rimi.ctibet.web.controller.vo.MerchantManageVo;
import com.rimi.ctibet.web.service.DestinationService;
import com.rimi.ctibet.web.service.IContentService;
import com.rimi.ctibet.web.service.IMerchantAndViewService;
import com.rimi.ctibet.web.service.IMerchantService;
import com.rimi.ctibet.web.service.IMerchantTypeService;
import com.rimi.ctibet.web.service.IPraiseService;
import com.rimi.ctibet.web.service.IViewService;
import com.rimi.ctibet.web.service.ImageService;

/**
 * 商户管理相关接口
 * 
 * @author xiaozhen
 * @date 2014--11
 * @package com.rimi.medical.web.merchant.controller
 * @copyright 成都睿峰科技有限公司
 * @version V1.0
 */

@Controller
@RequestMapping("web/merchant")
public class MerchantController extends BaseController{

	 @Resource
	 private IMerchantService merchantService;
	 @Resource
	 private IMerchantTypeService merchantTypeService;
	 @Resource
     private IViewService viewService;
	 @Resource
	 private DestinationService destinationService;
	 @Resource
	 private DestinationService destinationServiceImpl;
	 @Resource 
	 private ImageService imageService;
	 @Resource
	 private IMerchantAndViewService  merchantAndViewService;
	 @Resource
	 private IContentService contentService;
	 @Resource
	 private IPraiseService praiseService;

	
	 
	public static  List<MerchantVo> merchantVoList =  new ArrayList<MerchantVo>();//商户预览数据
	 

	 /**跳转到商户的新增与编辑界面
	  * @param request
	  * @param response
	  * @return
	  */
	 @RequestMapping("gotoSaveOrUpdateMerchant")
	 public ModelAndView gotoSaveOrUpdateMerchant(HttpServletRequest request,HttpServletResponse response){
		 String code = request.getParameter("code");
		 Merchant oldMerchant = merchantService.getMerchantByCode(code);
		 List<Destination> destinationList = destinationService.getAllDestination();
         List<MerchantType> merchantTypeList = merchantTypeService.getMerchantList();
		 ModelAndView view = new ModelAndView("manage/html/travel/commercial_edit");		 
		 return view.addObject("merchant", oldMerchant).addObject("destinationList", destinationList).addObject("merchantTypeList",merchantTypeList);
	 }

	 /**保存和修改商铺信息
	  * @param request
	  * @param response
	  * @param merchant
	  * @param merchantImgs
	  * @return
	  * @throws Exception
	  */
	 @RequestMapping("saveOrUpdateMerchant")
	 public ModelAndView saveOrUpateMerchant(HttpServletRequest request,HttpServletResponse response,@ModelAttribute Merchant merchant) throws Exception{
		 //获取上传图片集合
		 ModelAndView view = new ModelAndView("redirect:/web/merchant/merchantList");
		 String imgUrl = request.getParameter("url");
		 //=========================新增商户信息==============================
         if(StringUtils.isBlank(merchant.getCode())){
        	 //System.out.println("#######################新                    增#############################");
        	 merchant.setAvaliable(1);
        	 merchant.setMerchantImage(imgUrl);
        	 merchant.setCode(CodeFactory.createCultureCode());
        	 merchant.setCreateTime(new Date());
        	 merchant.setMerchantImage(imgUrl);
        	 merchant.setUrl("tourism/merchant/merchantDetail?code="+merchant.getCode());
 	         //==================商户url========================
 	        merchantService.saveMerchant(merchant);

             /*保存搜索信息*/
             WebSearch webSearch = new WebSearch();
             webSearch.setCode(merchant.getCode());
             webSearch.setContent(merchant.getMerchantDetail());
             webSearch.setImageUrl(merchant.getMerchantImage());
             webSearch.setUrl(merchant.getUrl());
             webSearch.setTitle(merchant.getMerchantName());
             LuceneUtil2.add(webSearch);
 			return view;
         }
         //====================编辑商户信息========================
        //System.out.println("#######################修                    改#############################");
         
        Merchant  oldMerchant = merchantService.getMerchantByCode(merchant.getCode());
 		merchant.setCreateTime(oldMerchant.getCreateTime());
 		merchant.setUrl("tourism/merchant/merchantDetail?code="+merchant.getCode());
 		merchant.setLastEditTime(new Date());
 		merchant.setId(oldMerchant.getId());
 		merchant.setAvaliable(1);
 		merchantService.updateMerchant(merchant);

         /*修改搜索信息*/
         WebSearch webSearch = new WebSearch();
         webSearch.setCode(merchant.getCode());
         webSearch.setContent(merchant.getMerchantDetail());
         webSearch.setImageUrl(merchant.getMerchantImage());
         webSearch.setUrl(merchant.getUrl());
         webSearch.setTitle(merchant.getMerchantName());
         LuceneUtil2.update(webSearch);
		return view;
	 }

	 /**批量删除商铺信息
	  * @param request
	  * @param response
	  * @param merchant
	  * @return
	  */
	 @RequestMapping("deleteMerchants")
	 public ModelAndView deleteMerchants(HttpServletRequest request,HttpServletResponse response){
		 ModelAndView view = new ModelAndView("redirect:/web/merchant/merchantList");
		 ConnectionUtil.showDSInfo();
		 String message = "";
		 String code = request.getParameter("code");
		 String[] codes = code.split(",");
		 for (String scode : codes) {
			Merchant merchant = merchantService.getMerchantByCode(scode);
			String path =  request.getSession().getServletContext().getRealPath("/")+ "/WebRoot" + ReadSettingProperties.getValue("upload")+"/img"+"/merchantImg"+"/";
			if(merchant!=null&&StringUtils.isBlank(merchant.getCode())){
				message = "必要参数为空";
				return view;
			}
			if (merchant!=null) {
			Merchant merchant2 = merchantService.getMerchantByCode(merchant.getCode());
			//将老图片全部删除
			if(merchant2!=null&&StringUtils.isNotBlank(merchant2.getMerchantImage())){
				String[] oldImgs = merchant2.getMerchantImage().split(",");
				FileHelper.deleteFile(oldImgs, path);
			}
			merchant2.setMerchantImage("");
			merchantService.deleteMerchant(merchant2);
			contentService.deleteReplyByPostCodeLogical(scode, Content.DETAIL_MERCHANT_REPLY);
			}

             /*删除搜索*/
             WebSearch webSearch = new WebSearch();
             webSearch.setCode(scode);
             LuceneUtil2.delete(webSearch);
		}

		 message = "删除成功";
		 return view;
	 }	 
	 
	 /**删除商铺信息
	  * @param request
	  * @param response
	  * @param merchant
	  * @return
	  */
	 @RequestMapping("deleteMerchant")
	 public @ResponseBody String deleteMerchant(HttpServletRequest request,HttpServletResponse response){
		 ModelAndView view = new ModelAndView("redirect:/web/merchant/merchantList");
		 ConnectionUtil.showDSInfo();
		 String message = "";
		 String code = request.getParameter("code");
		 String[] codes = code.split(",");
		 for (String scode : codes) {
			Merchant merchant = merchantService.getMerchantByCode(scode);
			String path =  request.getSession().getServletContext().getRealPath("/")+ "/WebRoot" + ReadSettingProperties.getValue("upload")+"/img"+"/merchantImg"+"/";
			if(StringUtils.isBlank(merchant.getCode())){
				message = "必要参数为空";
				return "error";
			}
			Merchant merchant2 = merchantService.getMerchantByCode(merchant.getCode());
			//将老图片全部删除
			if(StringUtils.isNotBlank(merchant2.getMerchantImage())){
				String[] oldImgs = merchant2.getMerchantImage().split(",");
				FileHelper.deleteFile(oldImgs, path);
			}
			merchant2.setMerchantImage("");
			merchantService.deleteMerchant(merchant2);
			contentService.deleteReplyByPostCodeLogical(scode, Content.DETAIL_MERCHANT_REPLY);

             /*删除搜索*/
             WebSearch webSearch = new WebSearch();
             webSearch.setCode(scode);
             LuceneUtil2.delete(webSearch);
		}
		 message = "删除成功";
		 return "success";
	 }	

	 /**按照商户code查询商户
	  * @param request
	  * @param response
	  * @return
	  */
	 @RequestMapping("getMerchantByCode")
	 public ModelAndView getMerchantByCode(HttpServletRequest request,HttpServletResponse response){
		 String merchantCode = request.getParameter("merchantCode");
		 ModelAndView view = new ModelAndView("manage/html/see/merchantManager");
		 String message = "";
         if(StringUtils.isBlank(merchantCode)){
        	     message = "必要参数为空";
        	     view.addObject(message);
                 return view;
         }
         Merchant merchant = merchantService.getMerchantByCode(merchantCode);
         view.addObject(merchant);
         return view;
	 }

	 /**获取到所有的商铺信息(高级查询)
	  * @param pager
	  * @param request
	  * @param response
	  * @return
	  */
	 @RequestMapping("merchantList")
	 public  ModelAndView merchantList(Pager pager,HttpServletRequest request,HttpServletResponse response){
		    try {
				request.setCharacterEncoding("UTF-8");
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
		    String areaCode = request.getParameter("areaCode");
			String distintionCode = request.getParameter("destinationCode"); 
			String type = request.getParameter("type");
			String isOffice = request.getParameter("isOffice"); 
			String keyWord = request.getParameter("keyWord");
			String rule = request.getParameter("rule"); 
			ModelAndView view = new ModelAndView("manage/html/travel/commercial");
		    //查询数据回显
			view.addObject("keyWord", keyWord);
			//isOffice为2默认是所有类型
			view.addObject("isOffice", StringUtils.isNotBlank(isOffice)?isOffice:2);
			view.addObject("type", this.merchantTypeService.findByCode(type));
			view.addObject("distination", this.destinationService.findByCode(distintionCode));
			
			Pager pager1=merchantService.searchMerchant(pager, areaCode, distintionCode, type, isOffice, keyWord,rule);
			//获取商户类型列表
			List<MerchantType> typeList = merchantTypeService.getMerchantList();
			//获取地区列表
			List<View> areaList = viewService.findAll();
			//获取目的地列表
			List<Destination> destinationList = destinationService.getAllDestination();
			view.addObject("pager",pager1);
			view.addObject("typeList",typeList);
			view.addObject("areaList",areaList);
			view.addObject("destinationList", destinationList);
			view.addObject("rule", rule);
			return view;
		}
	 
	 @RequestMapping("orderByMerchat")
	 public ModelAndView orderByMerchat(Pager pager,HttpServletRequest request,HttpServletResponse response){
		    try {
				request.setCharacterEncoding("UTF-8");
				response.setContentType("text/html;charset=utf-8");
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
		 ModelAndView view = new ModelAndView("manage/html/travel/commercial");
		 String rule = request.getParameter("rule");
		 String r= rule.replace("'","");
		 Pager pager2 = merchantService.orderByMerchat(pager,rule);
		 view.addObject("pager",pager2).addObject("rule", r);
         //获取目的地列表
         List<Destination> destinationList = destinationService.getAllDestination();
         view.addObject("destinationList", destinationList);
         String distintionCode = request.getParameter("destinationCode");
         String type = request.getParameter("type");
         String isOffice = request.getParameter("isOffice");
         String keyWord = request.getParameter("keyWord");
         view.addObject("keyWord", keyWord);
         view.addObject("isOffice", isOffice);
         view.addObject("type", this.merchantTypeService.findByCode(type));
         view.addObject("distination", this.destinationService.findByCode(distintionCode));
		 return view;
	 }
	 
	   /**
	    * 游西藏首页商户管理
	    * @param request
	    * @param response
	    * @return
	    */
	   @RequestMapping("gotoMerchantManage")
	   public ModelAndView merchantManage(HttpServletRequest request,HttpServletResponse response){
		   ModelAndView view = new ModelAndView("manage/html/travel/commercial-manage");
		   //获取商户类型列表
		   List<MerchantType> typeList = merchantTypeService.getMerchantList();
		   //推荐位的商户
		   List<MerchantManage> merchantManages = merchantService.getMerchantManage();
		   MerchantManage[] mms = new MerchantManage[4];
		   if(merchantManages!=null){
			   int i=0;
			   for (MerchantManage merchantManage : merchantManages) {
				   //System.out.println(merchantManage);
				   mms[i] = merchantManage;
				   //System.out.println(mms[i]);
				   i++;
			   }
           }
		   MerchantManage mm1 = mms[0];
		   MerchantManage mm2 = mms[1];
		   MerchantManage mm3 = mms[2];
		   MerchantManage mm4 = mms[3];
//		   if(mm1!=null)
		   MerchantType mt1 = new MerchantType();
		   MerchantType mt2 = new MerchantType();
		   MerchantType mt3 = new MerchantType();
		   MerchantType mt4 = new MerchantType();
		   if(mm1!=null)
		     mt1 = merchantTypeService.findByCode(mm1.getMerchantTypeCode());
		   if(mm2!=null)
		      mt2 = merchantTypeService.findByCode(mm2.getMerchantTypeCode());
		   if(mm3!=null)
		      mt3 = merchantTypeService.findByCode(mm3.getMerchantTypeCode());
		   if(mm4!=null)
		     mt4 = merchantTypeService.findByCode(mm4.getMerchantTypeCode());
		   //类别回显操作
		   view.addObject("mt1", mt1);
		   view.addObject("mt2", mt2);
		   view.addObject("mt3", mt3);
		   view.addObject("mt4", mt4);
		   
		   return view.addObject("typeList",typeList).addObject("mm1",mm1).addObject("mm2",mm2).addObject("mm3",mm3).addObject("mm4",mm4);
	   }
	   
	   @RequestMapping(value="saveMerchantManage")
	   public @ResponseBody Map<String, Object> saveMerchantManage(HttpServletRequest request,HttpServletResponse response,MerchantManageVo mmvo){
		   Map<String, Object> map=new HashMap<String, Object>();
		   try {
			   merchantService.clearOldMerchantManage();
			   merchantService.saveMerchantManage(mmvo.getMm1());
			   merchantService.saveMerchantManage(mmvo.getMm2());
			   merchantService.saveMerchantManage(mmvo.getMm3());
			   merchantService.saveMerchantManage(mmvo.getMm4());
			   map.put("msg", "操作成功");
			   return map;
		} catch (Exception e) {
			e.printStackTrace();
			map.put("msg", "操作失败");
		    return map;
		}
		  
	   }
	   
	  //=========================================================商户分类相关==============================================================
	 
	 /**获取商户分类列表
	  * @param pager
	  * @param request
	  * @param response
	  * @return
	  */
	 @RequestMapping("getMerchantTypeList")
	 public ModelAndView getMerchantTypeList(Pager pager,HttpServletRequest request,HttpServletResponse response){
		 ModelAndView view =new ModelAndView("manage/html/travel/merchantTypeList");
		 pager.setPageSize(20);
         Pager pager1 =merchantTypeService.searchMerchantType(pager, null);
         //System.out.println(request.getSession().getAttribute("nameExsist"));
         request.setAttribute("nameEx", request.getSession().getAttribute("nameExsist"));
         request.getSession().removeAttribute("nameExsist");
         view.addObject("pager",pager1);
         return view;
	 }
	 
	 /**增加或修改商户分类
	  * @param request
	  * @param response
	  * @param merchantType
	  * @return
	  */
	 @RequestMapping("saveOrUpdateMerchantType")
	 public @ResponseBody boolean saveMerchantType(MerchantType merchantType,HttpServletRequest req){
         ModelAndView view = new ModelAndView("redirect:/web/merchant/getMerchantTypeList");
         String code =CodeFactory.create("merchantType");
         if(merchantType.getName()!=null){
        	 List<MerchantType> list=merchantTypeService.findbypro("name", merchantType.getName());
        	//判断名称是否存在
        	 if(list.size()>0){
        		 ModelAndView v=new ModelAndView("redirect:/web/merchant/getMerchantTypeList");
        		 req.getSession().setAttribute("nameExsist", 1);
        		 return false;
        	 }
         }
		 //MerchantType merchantType = new MerchantType();
		 merchantType.setCode(code);
		 merchantType.setUrl("tourism/merchant/merchantList/1/1/1/1"+code+".html");
		 merchantTypeService.saveMerchantType(merchantType);
	     return true;
	 }
	 
	 /**删除商户分类
	  * @param request
	  * @param response
	  * @param merchantType
	  * @return
	  */
	 @RequestMapping("deleteMerchantType")
	 public  ModelAndView deleteMerchantType(HttpServletRequest request,HttpServletResponse response){
		 ModelAndView view = new ModelAndView("redirect:/web/merchant/getMerchantTypeList");
		 MerchantType merchantType = null;
		 String codes = request.getParameter("code");
		 String[] scode = codes.split(",");
		 for (String sscode : scode) {
			 merchantType = merchantTypeService.findByCode(sscode);
			 List<Merchant> ms = new ArrayList<Merchant>();
			 if(merchantType!=null){
				 ms= merchantService.findbypro("merchantType", merchantType.getCode());
				 if(ms!=null&&ms.size()>0){
					 return view;
				 }
				 merchantTypeService.deleteMerchantType(merchantType);
			 }
		 }
		 return view;
	 }
	 /**ajax删除商户分类
	  * @param request
	  * @param response
	  * @param merchantType
	  * @return
	  */
	 @RequestMapping("ajaxDeleteMerchantType")
	 public  @ResponseBody String ajaxDeleteMerchantType(HttpServletRequest request,HttpServletResponse response){
		 ModelAndView view = new ModelAndView("redirect:/web/merchant/getMerchantTypeList");
		 MerchantType merchantType = null;
		 String codes = request.getParameter("code");
		 String[] scode = codes.split(",");
		 for (String sscode : scode) {
			 merchantType = merchantTypeService.findByCode(sscode);
			 List<Merchant> ms = new ArrayList<Merchant>();
			 ms= merchantService.findbypro("merchantType", merchantType.getCode());
			 if(ms!=null&&ms.size()>0){
				 return "faied";
			 }
			 merchantTypeService.deleteMerchantType(merchantType);
		}
		 return "success";
	 }
	 /**
	  * 商户分类的高级查询
	  */
	 @RequestMapping("searchMerchantType")
	 public ModelAndView searchMerchantType(Pager pager,HttpServletRequest request,HttpServletResponse response){
		 ModelAndView view = new ModelAndView("manage/html/travel/merchantTypeList");
		 String keyWord = request.getParameter("keyWord");
		 Pager pager1 = merchantTypeService.searchMerchantType(pager,keyWord);
		 return view.addObject("pager",pager1).addObject("keyWord", keyWord);
	 }
	 //==============================================================================================================================================
	 /**
	  * 进入商户添加页面
	  * @param request
	  * @param response
	  * @param modelMap
	  * @return
	  */
	 @RequestMapping("intoMerchantAddUI")
	 public String intoMerchantAddUI(HttpServletRequest request,HttpServletResponse response,ModelMap modelMap){
		//目的地维护
		 List<Destination> destinationList  = destinationServiceImpl.getAllDestination();
		 
		 List<MerchantType>  merchantType  = new ArrayList<MerchantType>();
		 merchantType  = merchantTypeService.getMerchantList();
		 
		 modelMap.addAttribute("merchantType", merchantType);
		 modelMap.addAttribute("destinationList", destinationList);
		 return "manage//html//travel//commer-info-add";
	 }
	 
	 /**
	  * 保存商户
	  * @param request
	  * @param response
	  * @param modelMap
	  * @return
	  */
	 @RequestMapping("intoMerchantSave")
	 public ModelAndView intoMerchantSave(Merchant merchant,String destinationCode,String views[],String[] urls,String []imgCodes,HttpServletRequest request,HttpServletResponse response,ModelMap modelMap){
		 //System.out.println(2222);
		 //保存商户表
		 String code  = CodeFactory.createCode("merchant");
		 merchant.setAvaliable(1);
		 merchant.setCode(code);
		 merchant.setUrl(super.getUrlHtml("tourism/merchant/detail", code));
		 
		 //取第一个有效图片放到商户缩略图
		 if(urls!=null && urls.length > 0){
		     for (String url : urls) {
		         if(StringUtil.isNotNull(url)){
		             merchant.setMerchantImage(url);
		             break;
		         }
		     }
		 }
		 
		 merchant.setCreateTime(new Date());
		 //判断是否关联了携程
		 if(StringUtils.isNotBlank(merchant.getCtripUrl())){
			 String ctripPrice = merchantService.getCtripNow(merchant.getMerchantName());
		     if(StringUtils.isNotBlank(ctripPrice)){	 
			      merchant.setPrice(ctripPrice);}
		 }
		 merchantService.saveMerchant(merchant);
		//保存关联赞数据
		 Praise p = new Praise();
		 p.setContentCode(code);
		 p.setCode(CodeFactory.createCode("praise"));
		 //保存关联景点
		if(views != null&& views.length>0){
		 for (String viewCode : views) {
			 MerchantAndView merchantAndView =  new MerchantAndView();
			 merchantAndView.setMerchantCode(code);
			 merchantAndView.setViewCode(viewCode);
			 merchantAndViewService.save(merchantAndView);
		}
		}
		//如果没有景点
		else{
		 MerchantAndView merchantAndView =  new MerchantAndView();
		 merchantAndView.setMerchantCode(code);
		 merchantAndView.setViewCode(null);
		 merchantAndViewService.save(merchantAndView);}
		 //保存图片
		 if(urls!=null&&urls.length!=0){
		 for (String url : urls) {
			 	Image image =  new Image(1, null, null, "","");
			 	//image.setCode(Uuid.uuid());
			 	image.setUrl(url);
			 	image.setMutiCode(code);
			 	imageService.saveImage(image);
		 }
		 }
//		 return  this.merchantList(new Pager(), request, response);
		 ModelAndView view = new ModelAndView("redirect:/web/merchant/merchantList");
		 return view;
	 }
	 
	 
	 
	 /**
	  * 进入商户修改页面
	  * @param request
	  * @param response
	  * @param modelMap
	  * @return
	  */
	 @RequestMapping("intoMerchantUpdateUI")
	 public String intoMerchantUpdateUI(HttpServletRequest request,HttpServletResponse response,ModelMap modelMap){
		//目的地维护
		 List<Destination> destinationList  = destinationServiceImpl.getAllDestination();
		 
		 List<MerchantType>  merchantType  = new ArrayList<MerchantType>();
		 merchantType  = merchantTypeService.getMerchantList();
		 String code  =  request.getParameter("code");
		 Merchant merchant  = merchantService.getMerchantByCode(code);
		
		 MerchantType merchantType2 = merchantTypeService.findByCode(merchant.getMerchantType());
		 
		 Destination destination  =  destinationService.getDestinationByCode(merchant.getDistination());
		 List<Image> imgs  = imageService.getImageByMutiCode(code);
		 
		 List<View> merchantAndViews  =  merchantAndViewService.getViewsByMCode(code);
		 
		 modelMap.addAttribute("merchant", merchant);
		 modelMap.addAttribute("destination",destination);
		 modelMap.addAttribute("imgs",imgs);
		 modelMap.addAttribute("views",merchantAndViews);
		 
		 modelMap.addAttribute("merchantType2", merchantType2);
		 modelMap.addAttribute("merchantType", merchantType);
		 modelMap.addAttribute("destinationList", destinationList);
		 return "manage//html//travel//commer-info-update";
	 }
	 
	 /**
	  * 修改商户
	  * @param request
	  * @param response
	  * @param modelMap
	  * @return
	  */
	 @RequestMapping("intoMerchantUpdate")
	 public ModelAndView intoMerchantUpdate(Merchant merchant,String destinationCode,String views[],String[] urls,String []imgCodes,HttpServletRequest request,HttpServletResponse response,ModelMap modelMap){
		 
		 Merchant  merchant2 =  merchantService.getMerchantByCode(merchant.getCode());
		 //if(urls!=null&&urls.length>0)merchant2.setMerchantImage(urls[0]);
		 
	      //取第一个有效图片放到商户缩略图
         if(urls!=null && urls.length > 0){
             for (String url : urls) {
                 if(StringUtil.isNotNull(url)){
                     merchant2.setMerchantImage(url);
                     break;
                 }
             }
         }
		 
		 merchant2.setDistination(merchant.getDistination());
		 merchant2.setIsRecommend(merchant.getIsRecommend());
		 merchant2.setMerchantType(merchant.getMerchantType());
		 merchant2.setMerchantName(merchant.getMerchantName());
		 merchant2.setMerchantSummary(merchant.getMerchantSummary());
		 merchant2.setPrice(merchant.getPrice());
		 merchant2.setMerchantDetail(merchant.getMerchantDetail());
		 merchant2.setCtripUrl(merchant.getCtripUrl());
		 merchant2.setKeyWord(merchant.getKeyWord());
		 merchant2.setActivityUrl(merchant.getActivityUrl());
		 merchant2.setTdkTitle(merchant.getTdkTitle());
		 merchant2.setTdkDescription(merchant.getTdkDescription());
		 merchant2.setTdkKeywords(merchant.getTdkKeywords());
		 
		 //判断是否关联了携程
		 if(StringUtils.isNotBlank(merchant2.getCtripUrl())){
			 String ctripPrice = merchantService.getCtripNow(merchant.getMerchantName());
		     if(StringUtils.isNotBlank(ctripPrice)){	 
			      merchant2.setPrice(ctripPrice);}
		 }
		 merchant2.setUrl(super.getUrlHtml("tourism/merchant/detail", merchant.getCode()));
		// merchant2.setUrl("tourism/merchant/merchantDetail?code="+merchant.getCode());
		 merchantService.updateMerchant(merchant2);
		 
		 //删以前的 景点,更新现在的景点
		 //merchantAndViewServic
		//保存关联景点
	     if(views!=null&&views.length>0){
	    	 MerchantAndView merchantAndView =  new MerchantAndView();
		 for (String viewCode : views) {
			 merchantAndView.setMerchantCode(merchant.getCode());
			 merchantAndView.setViewCode(viewCode);
			 merchantAndViewService.save(merchantAndView);
		}}
		 //保存图片
		 for (int i = 0; i < urls.length; i++) {
			String url  = urls[i];
			Image image =  new Image(1, null,null, "","");
		 	//image.setCode(Uuid.uuid());
		 	image.setCode(imgCodes[i]);
		 	image.setUrl(url);
		 	image.setMutiCode(merchant.getCode());
		 	imageService.saveImage(image);
		}
		 
		 
		 ModelAndView view = new ModelAndView("redirect:/web/merchant/merchantList");
		 return view;
	 }
	 /**
	  * 商户汇总首页显示
	  * @param model
	  * @return
	  */
	 @RequestMapping("banner")
	 public String banner(ModelMap model){
		List<Content> bannerList = contentService.findContentByProgrmaCode("35abg9123-2b3c-1s24-bac-04456056a05bcs");
		List<Content> commerList = contentService.findContentByProgrmaCode("6125abc85-35ac-32c4-26ce-00q5416a05bc9");
		model.put("bannerProgramaCode", "35abg9123-2b3c-1s24-bac-04456056a05bcs");
		model.put("commerProgramaCode", "6125abc85-35ac-32c4-26ce-00q5416a05bc9");
        model.put("bannerList", bannerList);	
        model.put("commerList", commerList);
        return "manage/html/travel/commercial-show";
				 
	 }
	 
	/**
	 * 保存从前台管理页面过来的banner commercial content
	 * @param content
	 * @return
	 */
	@RequestMapping("saveContent")
	public @ResponseBody
	String saveContent(String[] url, String[] content, String[] commerLink,
			String[] commerCode, String bannerProgramaCode,
			String commerProgramaCode) {
        List<Content> bannerList = new ArrayList<Content>();
        List<Content> commerList = new ArrayList<Content>();
        //banner
		for(int i=0;i<3;i++){
			Content c = new Content();
			//content的url 保存为图片路径 content保存为连接 
			c.setProgramaCode(bannerProgramaCode);
			c.setUrl(url[i]);
			c.setContent(content[i]);
			c.setContentType("商户首页banner");
			bannerList.add(c);
		}     
		contentService.updateSeeBanner(bannerList,bannerProgramaCode);
		//commercial
		for(int i=0;i<3;i++){
			Content c = new Content();
			//content的url 保存为超链接  content保存为编号
			c.setProgramaCode(commerProgramaCode);
			c.setUrl(commerLink[i]);
			c.setContent(commerCode[i]);
			c.setContentType("商户首页推荐");
			commerList.add(c);
		}     
		contentService.updateSeeBanner(commerList,commerProgramaCode);
		return "success";
	}
	
	/**
	 * 商户预览
	 * @return
	 */
	@RequestMapping("intoFrontTourmMerchantPrev")
	public ModelAndView  intoFrontTourmMerchantPrev(HttpServletRequest request,HttpServletResponse response,MerchantManageVo mmvo){
		ModelAndView view = new ModelAndView("redirect:/tourism/strage/fontMerchantPage");
		//前台预览数据结构
		List<MerchantVo> mvos = new ArrayList<MerchantVo>();
		List<MerchantManage> mms = new ArrayList<MerchantManage>();
		mms.add(mmvo.getMm1());mms.add(mmvo.getMm2());mms.add(mmvo.getMm3());mms.add(mmvo.getMm4());
		for (MerchantManage mm : mms) {
			MerchantVo mvo = new MerchantVo();
			MerchantType mt = merchantTypeService.findByCode(mm.getMerchantTypeCode());
		    if(mt!=null){
			mvo.setmType(mt.getName());
            mvo.setmUrl(mt.getUrl());}			
			//封装商户集合
            List<Merchant> ms = new ArrayList<Merchant>();
            Merchant m1 = merchantService.getMerchantByCode(mm.getMerchantCode1());
            if(m1!=null)
              ms.add(m1);
            Merchant m2 = merchantService.getMerchantByCode(mm.getMerchantCode2());
            if(m2!=null)
              ms.add(m2);
            Merchant m3 = merchantService.getMerchantByCode(mm.getMerchantCode3());
            if(m3!=null)
              ms.add(m3);
            Merchant m4 = merchantService.getMerchantByCode(mm.getMerchantCode4());
            if(m4!=null)
              ms.add(m4);
            mvo.setMerchantList(ms);
            mvos.add(mvo);
		}
		this.merchantVoList = mvos;
		return view;
	}
	//商户汇总页预览
	@RequestMapping("preView")
	public ModelAndView preView(String[] content,String[] commerCode,String[] url,HttpServletRequest request,HttpServletResponse response){
//		ModelAndView view = new ModelAndView("redirect:/tourism/merchant/merchantList?isOffice='office'");
		ModelAndView view = new ModelAndView("redirect:/tourism/merchant/merchantIndex?preview=1");
		FrontMerchatController.preOfficeMCodes = commerCode;
//		List<Merchant> ms = new ArrayList<Merchant>();
//		for(int i=0;i<commerCode.length;i++){
//			Merchant m = merchantService.getMerchantByCode(commerCode[i]);
//			ms.add(m);
//		}
        FrontMerchatController.preBanner = new ArrayList<Content>();
        for (int i=0;i<content.length;i++) {
            Content c= new Content();
            c.setUrl(url[i]);
            c.setContent(content[i]);
            FrontMerchatController.preBanner.add(c);
        }
		return view;
	}
	/**
	 * 显示图说西藏最热
	 * @return
	 */
	@RequestMapping("showhot")
	public @ResponseBody ModelAndView showhot(String programaCode){
		Map<String, Object> resultMap = new HashMap<String, Object>();
		List<Merchant> mlist = new ArrayList<Merchant>();
		List<HotMerchantVo> vo = merchantService.getAllHotMerchant(3);
        for(HotMerchantVo v : vo){
        	mlist.add(v.getMerchant());
        }
		resultMap.put("data", mlist);
		return jsonView(resultMap);
	}
	//进入商户汇总页
	@RequestMapping("gotoMerchantBanner")
	public ModelAndView gotoMerchantBanner(HttpServletRequest request,HttpServletResponse response){
		ModelAndView view = new ModelAndView("manage/html/travel/commercial-show");
        		
		
		
		return view;
	}
}