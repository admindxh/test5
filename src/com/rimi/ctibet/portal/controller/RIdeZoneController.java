package com.rimi.ctibet.portal.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.google.gson.Gson;
import com.rimi.ctibet.common.controller.BaseController;
import com.rimi.ctibet.common.util.DateUtil;
import com.rimi.ctibet.common.util.HtmlRegexpUtil;
import com.rimi.ctibet.common.util.Pager;
import com.rimi.ctibet.domain.Content;
import com.rimi.ctibet.domain.Equipment;
import com.rimi.ctibet.domain.Image;
import com.rimi.ctibet.domain.Merchant;
import com.rimi.ctibet.domain.Programa;
import com.rimi.ctibet.domain.RecommonedSite;
import com.rimi.ctibet.domain.RideLine;
import com.rimi.ctibet.domain.RideRecommon;
import com.rimi.ctibet.domain.ServiceSite;
import com.rimi.ctibet.portal.controller.vo.PostVo;
import com.rimi.ctibet.portal.controller.vo.RideLineVo;
import com.rimi.ctibet.portal.controller.vo.RideVo;
import com.rimi.ctibet.portal.controller.vo.SiteInfoVo;
import com.rimi.ctibet.portal.controller.vo.TravalFrontPageVo;
import com.rimi.ctibet.web.controller.vo.MemberVO;
import com.rimi.ctibet.web.service.IAdAreaService;
import com.rimi.ctibet.web.service.IContentService;
import com.rimi.ctibet.web.service.IEquimentService;
import com.rimi.ctibet.web.service.IMemberService;
import com.rimi.ctibet.web.service.IMerchantService;
import com.rimi.ctibet.web.service.IPostService;
import com.rimi.ctibet.web.service.IProgramaService;
import com.rimi.ctibet.web.service.IRecommonedSiteService;
import com.rimi.ctibet.web.service.IRideCommonService;
import com.rimi.ctibet.web.service.IRideLineService;
import com.rimi.ctibet.web.service.IStrategyViewService;
import com.rimi.ctibet.web.service.IViewService;
import com.rimi.ctibet.web.service.ImageService;
import com.rimi.ctibet.web.service.IserviceSiteService;

@Controller("portal_rideZone")
@RequestMapping({"portal/rideZone","ride"})
public class RIdeZoneController extends BaseController {

    @Resource
    private  IPostService  postService;
    @Resource
    private IProgramaService  programaService;
    @Resource
    private IMemberService  memberService;
    
	@Autowired
	private ImageService imageService;

	@Autowired
	private IEquimentService equimentService;
	
	@Autowired
	private IRideLineService rideLineService;
	
	@Autowired
	private IRideCommonService  rideCommonService;
    
	@Autowired
	private IContentService contentServiceImpl;
	
	@Autowired
	private IAdAreaService areaService;
	
	@Autowired
	private IserviceSiteService siteService;
	
	@Autowired
	private IRecommonedSiteService recommonedSiteService;
	
	@Autowired
	private IContentService contentService;
	
	@Autowired
	private IMerchantService merchantService;
	
	
	@Resource
	private IStrategyViewService  strategyViewService ;
	
	@Resource
	private IViewService  viewService;
	
    @ModelAttribute
	public void setNavSelectIndex(HttpServletRequest request,HttpServletResponse response,ModelMap modelMap){
		modelMap.put("programNam","4");
	}
    
	@RequestMapping("portal/app/building")
	public ModelAndView building()
	{
		ModelAndView view =	new ModelAndView();
		view.addObject("programNam", 4);
		return view;
	}
	/**
	 * 骑行首页
	 * @return
	 */
	@RequestMapping("rideIndex")
	public String rideIndex(HttpServletRequest request,HttpServletResponse response,ModelMap  modelMap){
		//查询论坛信息
		List<PostVo> ridegg = postService.getPostByPrograma(
				Content.RIDE_GG, Content.ORDER_CREATETIME_DESC, 5);
		List<PostVo> ridegs = postService.getPostByPrograma(
				Content.RIDE_GS, Content.ORDER_CREATETIME_DESC, 5);
		List<PostVo> ridezj = postService.getPostByPrograma(
				Content.RIDE_ZJ, Content.ORDER_CREATETIME_DESC, 5);
		List<PostVo> ridezb = postService.getPostByPrograma(
				Content.RIDE_ZB, Content.ORDER_CREATETIME_DESC, 5);
		modelMap.put("ridegg", ridegg);//骑行新闻
		modelMap.put("ridegs", ridegs);//骑行故事
		modelMap.put("ridezj", ridezj);//骑行征集
		modelMap.put("ridezb", ridezb);//骑行装备
		//查询banner 信息切换
		List<Image> list = imageService.getImageByMutiImageCode("8a2bc8f-22d7-4a3e-5c4b-ae44a584cb85");
		for (Image image : list) {
			  //查询url
				image.setHyperlink(image.getName());
		}
		modelMap.put("blist", list);//默认baner 切换图数据
		Pager pager  = new Pager();
		pager.setPageSize(3);
		Pager findAll = rideLineService.searchRideLineByName(pager, "");
		//查询线路
		 List<RideLine> dataList = findAll.getDataList();
		 if (dataList!=null&&dataList.size()>=1) {
			modelMap.put("lineType", dataList.get(0).getCode());
		 }
		 modelMap.put("rlist", dataList);//线路
		 //装备推荐
		  List<RideRecommon> zbtj = rideCommonService.findByProperty("contentType", "zbtj");
		  for (RideRecommon rideRecommon : zbtj) {
			//通过   code 查询连接地址
			  String  code  =  rideRecommon.getCtorname();
			  Equipment equipment  =  equimentService.findByCode(code);
			  if (equipment!=null) {
				 rideRecommon.setUrl(equipment.getUrl());
			}
		  }
		  modelMap.put("zbtj", zbtj); //装备推荐
		  //友情连接
		  List<RideRecommon> yqlj = rideCommonService.findByProperty("contentType", "yqlj");
		  modelMap.put("yqlj", yqlj);
		  //查询广告
		  List<Map<String, Object>>  arealist = areaService.getAdAreaByProCode("e433h3hl32-25e6-11e4-b6ce-005a56a05bc9");
		  modelMap.put("arealist", arealist);
		 //经典攻略
			//查询攻略code
		 List<RideRecommon> jdgl = rideCommonService.findByProperty("contentType", "jdgl");
		 for (RideRecommon rideRecommon : jdgl) {
			Content c  = contentServiceImpl.findByCode(rideRecommon.getCtorname());
			//攻略
			if (c!=null) {
				c.setImgUrl(getContentImgSrc(c.getContent()));//获取第一个图片
				c.setContent(HtmlRegexpUtil.filterHtml(c.getContent()));
				
			}
			modelMap.put("content", c);//经典攻略
	    }
	    //往期回顾，默认最热门
	    modelMap.put("order",1);	 
		return "portal/app/rideindex";
	}
	
	
	
	
	/**
	 * 
	 * @param pager
	 * @param request
	 * @param code 站点信息
	 * @param model
	 * @return
	 */
	@RequestMapping(value="siteInfo",produces = "text/html; charset=utf-8")
	@ResponseBody
	public  String siteInfo(Pager pager,HttpServletRequest request,String code,String siteId,ModelMap model){
		SiteInfoVo  siteInfoVo =  new SiteInfoVo();
		//查询站点信息
		List<RideLineVo> rlist = new  ArrayList<RideLineVo>();//站点详情
		List<ServiceSite> serviceSite1 =  new ArrayList<ServiceSite>();	
		//查询站点
		ServiceSite serviceSite3 =  siteService.findByCode(siteId); //点击的站点
		if (serviceSite3!=null) {
			serviceSite1.add(serviceSite3);
		}
		if (serviceSite1!=null) {
				for (ServiceSite serviceSite2 : serviceSite1) {
					siteInfoVo.setSiteName(serviceSite2.getSiteName());
					siteInfoVo.setServiceName(serviceSite2.getServiceName());
					siteInfoVo.setServiceAdrress(serviceSite2.getServiceAdress());
					siteInfoVo.setServicePhone(serviceSite2.getServicePhone());
					siteInfoVo.setSiteImg(serviceSite2.getSitImg());
					siteInfoVo.setServiceImg(serviceSite2.getServiceImg());
					
                       RideLineVo lineVo = new RideLineVo();
                       //站点信息
                       lineVo.setServiceSite(serviceSite2);
                          List<RecommonedSite> rslist = recommonedSiteService.findByProperty("sitecode", serviceSite2.getCode());
                          //宿
	      		         List<Merchant> merchantShu  = new ArrayList<Merchant>();
	      		         //食
	      		         List<Merchant> merchantShi  = new ArrayList<Merchant>();
	      		         //攻略
	      		         List<Content> content  = new ArrayList<Content>();
	      		         int index  = 0;
	      		         for (RecommonedSite rs : rslist) {
	      					 if (rs.getContentType().equals("merchantShu")) {
	      						 //merchantShu.add(rs.getConentCode());
	      						  Merchant merchant  = merchantService.getMerchantByCode(rs.getConentCode());
	      						  if (merchant!=null) {
									  merchantShu.add(merchant);
									  //宿
									  siteInfoVo.setS1Name(merchant.getMerchantName());
									  siteInfoVo.setS1Url(merchant.getUrl());
									  if (index>0) {
										   siteInfoVo.setS2Name(merchant.getMerchantName());
										   siteInfoVo.setS2Url(merchant.getUrl());
									   }
									  index ++;
									  
								  }
	      					 }else if(rs.getContentType().equals("merchantShi")){
	      						Merchant merchant  = merchantService.getMerchantByCode(rs.getConentCode());
	      						 if (merchant!=null) {
									  merchantShi.add(merchant);
									  //食
									  siteInfoVo.setC1Name(merchant.getMerchantName());
									  siteInfoVo.setC1Url(merchant.getUrl());
									  if (index>1) {
										   siteInfoVo.setC2Name(merchant.getMerchantName());
										   siteInfoVo.setC2Url(merchant.getUrl());
									   }
									  index ++;
								}
	      					}else{
	      						Content c  = contentService.findByCode(rs.getConentCode());
	      						if(c!=null){
	      						Date d  = c.getCreateTime();
	      						int dString  = d.getDay();
	      					    String dateToStr = DateUtil.dateToStr(d, "yyyy-MM");
	      						siteInfoVo.setGldd(String.valueOf(dString));
	      						siteInfoVo.setGlym(dateToStr);
	      						siteInfoVo.setGlname(c.getContentTitle());
	      						siteInfoVo.setGlathor(c.getAuthorCode());
	      						siteInfoVo.setGlcontent(HtmlRegexpUtil.filterHtml(c.getContent()));
	      						siteInfoVo.setGlurl(c.getUrl());
	      						content.add(c);
	      						}
	      					}
	      				 }
	      		       lineVo.setContent(content);
	      		       lineVo.setMerchantShi(merchantShi);
	      		       lineVo.setMerchantShu(merchantShu);
	      		       rlist.add(lineVo);
				}
			}
		    Object c  = JSONObject.fromObject(siteInfoVo);
			return    c.toString();
	}
	
	
	
	/**
	 * 
	 * @param request
	 * @param lineType 线路类型
	 * @param model
	 * @return
	 */
	@RequestMapping(value="getSiteLine",produces = "text/html; charset=utf-8")
	@ResponseBody
	public String getSiteLine(String lineType,HttpServletRequest request,HttpServletResponse response){
		if (StringUtils.isNotBlank(lineType)) {
			RideLine rideLine = rideLineService.findByCode(lineType);
			if (rideLine!=null) {
				RideVo rv  = new RideVo();
				rv.setIntroduce(rideLine.getIntroduce());
				rv.setCode(rideLine.getCode());
				rv.setAchref(rideLine.getAchref());
				return JSONObject.fromObject(rv).toString();
			}
		}
		return  "";
	}
	
	@RequestMapping(value="getSiteList",produces = "text/html; charset=utf-8")
	@ResponseBody
	public String getSiteList(String lineType,HttpServletRequest request,HttpServletResponse response){
		if (StringUtils.isNotBlank(lineType)) {
			RideLine rideLine = rideLineService.findByCode(lineType);
			if (rideLine!=null) {
				List<ServiceSite>  serviceSiteList  = 	siteService.findByProperty("ridelineId", lineType);
				//List<ServiceSite>  serviceSiteList  = siteService.getPagerSite("", "", lineType, new Pager())
				return JSONArray.fromObject(serviceSiteList).toString();
			}
		}
		return  "";
	}
	
	
	
	/**
	 * 攻略 全部活动列表
	 * portal/activityController/getActivityIndexActivityList
	 */
	@RequestMapping(value="getTravalIndexActivityList", produces = "text/html; charset=utf-8")
	@ResponseBody
	public String getTravalIndexActivityList(Pager pager,Integer order,String programCode,String des,String view,String keyword,Integer isOfficial){
		//pager = activityService.getAllActivityOrder(pager, 0);
		pager.setPageSize(5);
		pager = contentServiceImpl.findPagerTravel(pager, order, programCode,des,view,keyword,isOfficial);
		List<TravalFrontPageVo>   dataList  = pager.getDataList();
		for (TravalFrontPageVo travalFrontPageVo : dataList) {
			 String srcImg   =  super.getContentImgSrc(travalFrontPageVo.getTravelContent());
			 travalFrontPageVo.setTravelImgUrl(srcImg);
			 travalFrontPageVo.setTravelContent(HtmlRegexpUtil.filterHtml(travalFrontPageVo.getTravelContent()));
		}
		pager.setTotalPage(pager.getTotalPage());
		return new Gson().toJson(pager);
	}
	
	
	
	
	
	

	/**
	 * 骑行栏目查询
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 */
	@RequestMapping("ridePropgram")
	public String ridePropgram(HttpServletRequest request,HttpServletResponse response,ModelMap  modelMap){
		List<Programa> programaTwo = programaService.getSendPrograma("a3cd82v33-75d6-1e4-b6ce-005056a05bc9");
		List<Programa> programaOne = programaService.getSendPrograma("e8876fe6-7608-11e4-b6ce-005056a05bc9");
		//这里 destinationList 名称为了适应共同的页面头部
		modelMap.put("programaTwo", programaTwo);
		modelMap.put("programaList", programaOne);
		return "portal/app/header/rideindex";
	}
	
	
	@RequestMapping({"rideCommunity","community"})
	public String rideCommunity(Model model){
	    String[] codes = {Content.RIDE_GG, Content.RIDE_GS, Content.RIDE_ZJ, Content.RIDE_ZB};
	    List<PostVo> list = new ArrayList<PostVo>();
	    for (String code : codes) {
	        int postNum = postService.getPostCountByPrograma(code);
	        int checkNum = postService.getPostcheckNumByPrograma(code); 
	        int replyNum = postService.getPostReplyNumByPrograma(code); 
	        Content newReply = postService.getPostNewReplyByPrograma(code);
	        Programa programa = programaService.getProgramaByCode(code);
	        
	        PostVo postVo = new PostVo();
	       // postVo.setUrl("community/list?plateCode=" + code);
	        postVo.setUrl("community/list/" + code+".html");
	        if(code.equals(Content.RIDE_GG)){
	            postVo.setImg("portal/assets/icon/riding_notice.png");
            }else if(code.equals(Content.RIDE_GS)){
                postVo.setImg("portal/assets/icon/riding_story.png");
            }else if(code.equals(Content.RIDE_ZJ)){
                postVo.setImg("portal/assets/icon/riding_collect.png");
            }else if(code.equals(Content.RIDE_ZB)){
                postVo.setImg("portal/assets/icon/equip_discuss.png");
            }
	        postVo.setPostNum(postNum);
	        postVo.setCheckNum(checkNum);
	        postVo.setReplyNum(replyNum);
	        postVo.setNewReply(newReply);
	        
	        if(newReply!=null){
	            MemberVO memberVO = memberService.getMemberByMemberCode(newReply.getCreateuserCode());
	            if(memberVO!=null){
	                postVo.setReplyerPic(memberVO.getPic());
	                postVo.setReplyTime(new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(newReply.getCreateTime()));
	                postVo.setContent(newReply.getContent());
	                postVo.setReplyerName(memberVO.getName());
	                postVo.setReplyCode(memberVO.getCode());
	                postVo.setPostCode(newReply.getPostCode());
	                postVo.setPostTitle(newReply.getPostTitle());
	                postVo.setPostUrl(newReply.getPostUrl());
	            }
	        }
	        if(programa!=null){
	            postVo.setProgrmaName(programa.getProgramaName());
	            postVo.setSummary(programa.getProgramaSummary());
	        }
	        list.add(postVo);
        }
	    model.addAttribute("list", list);
	    return "portal/app/ridezone/ride_community";
	}
	
}
