package com.rimi.ctibet.portal.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONSerializer;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.google.gson.Gson;
import com.rimi.ctibet.common.controller.BaseController;
import com.rimi.ctibet.common.exception.JsonParseException;
import com.rimi.ctibet.common.util.CodeFactory;
import com.rimi.ctibet.common.util.JsonUtil;
import com.rimi.ctibet.common.util.Pager;
import com.rimi.ctibet.common.util.annotation.Token;
import com.rimi.ctibet.domain.AdArea;
import com.rimi.ctibet.domain.Content;
import com.rimi.ctibet.domain.Destination;
import com.rimi.ctibet.domain.Image;
import com.rimi.ctibet.domain.LogUser;
import com.rimi.ctibet.domain.Merchant;
import com.rimi.ctibet.domain.MerchantType;
import com.rimi.ctibet.domain.Praise;
import com.rimi.ctibet.domain.UserFavorite;
import com.rimi.ctibet.domain.View;
import com.rimi.ctibet.web.controller.vo.ViewUtil;
import com.rimi.ctibet.web.service.DestinationService;
import com.rimi.ctibet.web.service.IAdAreaService;
import com.rimi.ctibet.web.service.IContentService;
import com.rimi.ctibet.web.service.IGroupTravelService;
import com.rimi.ctibet.web.service.IMerchantService;
import com.rimi.ctibet.web.service.IPraiseService;
import com.rimi.ctibet.web.service.IUserFavoriteService;
import com.rimi.ctibet.web.service.IViewService;
import com.rimi.ctibet.web.service.ImageService;

/**
 * 门户的商户Controller
 * 
 * @author xiaozhen
 * 
 */
@Controller
@RequestMapping({"tourism/merchant","front/merchant"})
public class FrontMerchatController extends BaseController {

	@Resource
	private IViewService viewService;
	@Resource
	private DestinationService destinationService;
	@Resource
	private IMerchantService merchantService;
	@Resource
	private IUserFavoriteService userFavoriteService;
	@Resource
	private ImageService imageService;
	@Resource
	private IContentService contentService;
	@Resource
	private IPraiseService praiseService;
	@Resource
	private IAdAreaService adAreaService;
	@Resource
	private IGroupTravelService groupTravelService;

	public static String[] preOfficeMCodes;
	public static List<Content> preBanner;
	
    @ModelAttribute
	public void setNavSelectIndex(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) {
		modelMap.put("programNam", "1");
	}

	@RequestMapping("getViewByDescode")
	public void ajaxGetViewByDescode(String destinationCode,
			HttpServletRequest request, HttpServletResponse response,
			ModelMap modelMap) {
		List<View> viewList = this.getListViewByDescode(destinationCode);
		// json lib 转化 对象
		List<ViewUtil> viewUtilList = new ArrayList<ViewUtil>();
		for (View view : viewList) {
			ViewUtil viewUtil = new ViewUtil();
			viewUtil.setCode(view.getCode());
			viewUtil.setViewName(view.getViewName());
			viewUtilList.add(viewUtil);
		}
		String viewsJsonListString = JSONSerializer.toJSON(viewUtilList)
				.toString();
		super.writerJson(viewsJsonListString);
	}

	/**
	 * 根据目的地查询景点
	 * 
	 * @param destinationCode
	 * @return
	 */
	public List<View> getListViewByDescode(String destinationCode) {
		return viewService.findViewByDestinationCode(destinationCode);
	}

	/**
	 * 门户商户的列表
	 * 
	 * @param pager
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping({"merchantList","list"})
	public ModelAndView merchantList(Pager pager, HttpServletRequest request,
			HttpServletResponse response) {
		//System.out.println("-----------------------**");
		String type = request.getParameter("type");
		// 高级查询条件。
		String viewCode = request.getParameter("viewCode");
		String destinationCode = request.getParameter("destinationCode");
		List<View> views = viewService.findViewByDestinationCode(destinationCode);
		Destination destination = destinationService
				.getDestinationByCode(destinationCode);
		View vi = viewService.findByCode(viewCode);
		String destinationName = "所有地区";
		if (destination != null) {
			destinationName = destination.getDestinationName();
		}
		String viewName = "所有景点";
		if (vi != null) {
			viewName = vi.getViewName();
		}
		String keyWord = request.getParameter("keyWord");
		// 分类和排序的条件。
		String rule = request.getParameter("rule");
		if (!StringUtils.isNotBlank(rule)) {
			rule  =  "favorite";
		}
		if (StringUtils.isBlank(type))
			type = "0";
		if(type.equals("1")){
			ModelAndView gtView = new ModelAndView("forward:/tourism/merchant/getGroupTravelList?rule="+rule+"&keyWord="+keyWord+"&destinationCode="+destinationCode+"&viewCode="+viewCode);
			return gtView;
		}
		List<Map<String, Object>> top3Mt = merchantService.getTop3Mt();
		// 获取商户分类1,2,3
		MerchantType mt1 = new MerchantType();
		MerchantType mt2 = new MerchantType();
		MerchantType mt3 = new MerchantType();
		if (top3Mt != null && top3Mt.size() >= 1) {
			Map<String, Object> m1Map = top3Mt.get(0);
			mt1.setCode(m1Map.get("code").toString());
			mt1.setName(m1Map.get("name").toString());
		}
		if (top3Mt != null && top3Mt.size() >= 2) {
			Map<String, Object> m2Map = top3Mt.get(1);
			mt2.setCode(m2Map.get("code").toString());
			mt2.setName(m2Map.get("name").toString());
		}
		if (top3Mt != null && top3Mt.size() >= 3) {
			Map<String, Object> m3Map = top3Mt.get(2);
			mt3.setCode(m3Map.get("code").toString());
			mt3.setName(m3Map.get("name").toString());
		}

		ModelAndView view = new ModelAndView("portal/app/merchant/list");
		view.addObject("mt1", mt1);
		view.addObject("mt2", mt2);
		view.addObject("mt3", mt3);
		List<Destination> destinations = destinationService.getAllDestination();
		// viewService.f
		
		//Pager pager2 = merchantService.getFrontMerchantList(pager, viewCode,
				//destinationCode, keyWord, type, rule);
		// 判断是否是官方推荐
		String isOffice = request.getParameter("isOffice");
		//if (StringUtils.isNotBlank(isOffice))
		//pager2 = merchantService.getOfficeList(pager2);
		List<AdArea> ad = adAreaService
				.findbypro("781bc9785-1231-12a4-bab-0211056a05bce");
		 List<Map<String, Object>> viewByGone = viewService.getViewByGone(3,null);
		// 在返回时要将高级查询的条件传回，然后排序与分类通过ajax来查询。
		return view.addObject("views", views).addObject("destinations",
				destinations).addObject("destinationName", destinationName)
				.addObject("viewCode", viewCode)
				.addObject("viewName", viewName).addObject("type", type)
				.addObject("rule", rule).addObject("destinationCode",
						destinationCode).addObject("keyWord", keyWord)
				.addObject("adList", ad).addObject("viewByGone", viewByGone);
	}

	@RequestMapping(value = "avalonMerchantList", produces = "text/html; charset=utf-8")
	@ResponseBody
	public String avalonMerchantList(Pager pager, String viewCode,
			String destinationCode, String keyWord, String type, String rule) {
		Pager pager2 = merchantService.getFrontMerchantList(pager, viewCode,
				destinationCode, keyWord, type, rule);
		pager2.setTotalPage(pager.getTotalPage());
		return new Gson().toJson(pager2);
	}

	/**
	 * 门户的商户index
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping({"merchantIndex","index"})
	public ModelAndView merchantIndex(HttpServletRequest request,
			HttpServletResponse response) {
		ModelAndView view = new ModelAndView("portal/app/merchant/index");

		List<Map<String, Object>> top3Mt = merchantService.getTop3Mt();
		// 获取商户分类1,2,3
		MerchantType mt1 = new MerchantType();
		MerchantType mt2 = new MerchantType();
		MerchantType mt3 = new MerchantType();
		if (top3Mt != null && top3Mt.size() >= 1) {
			Map<String, Object> m1Map = top3Mt.get(0);
			mt1.setCode(m1Map.get("code").toString());
			mt1.setName(m1Map.get("name").toString());
		}
		if (top3Mt != null && top3Mt.size() >= 2) {
			Map<String, Object> m2Map = top3Mt.get(1);
			mt2.setCode(m2Map.get("code").toString());
			mt2.setName(m2Map.get("name").toString());
		}
		if (top3Mt != null && top3Mt.size() >= 3) {
			Map<String, Object> m3Map = top3Mt.get(2);
			mt3.setCode(m3Map.get("code").toString());
			mt3.setName(m3Map.get("name").toString());

		}
		view.addObject("mt1", mt1);
		view.addObject("mt2", mt2);
		view.addObject("mt3", mt3);
		// 1.获取到第1分类商户
		List<Map<String, Object>> hotels = merchantService.getFrontMerchantByType(mt1.getCode(), 6);
		// 2.获取到第2分类商户
		List<Map<String, Object>> foods = merchantService
				.getFrontMerchantByType(mt2.getCode(), 6);
		// 3.获取到第3分类商户
		List<Map<String, Object>> plays = merchantService
				.getFrontMerchantByType(mt3.getCode(), 6);
		// 4.获取到団游（null）
		List<Map<String, Object>> groupTravel = groupTravelService
				.getGtForMIndex();
		// 5.获取到官方推荐
		List<Map<String, Object>> offices = new ArrayList<Map<String,Object>>();
		// 6.获取banner
		List<Content> bannerList = contentService
				.findContentByProgrmaCode("35abg9123-2b3c-1s24-bac-04456056a05bcs");
		if(StringUtils.isBlank(request.getParameter("preview"))){
		   offices = merchantService.getOffice(3);
		}else{
            for(String cs : preOfficeMCodes) {
                Map<String,Object> om = new HashMap<String,Object>();
                Merchant pm = merchantService.getMerchantByCode(cs);
                if(pm!=null){
                    Praise pp = praiseService.getPraiseContentCode(pm.getCode());
                    om.put("url", pm.getUrl());
                    om.put("merchantimage", pm.getMerchantImage());
                    om.put("merchantname", pm.getMerchantName());
                    om.put("falsepraise", pp.getFalsePraise());
                    offices.add(om);
                }
            }
            bannerList = this.preBanner;
		}
		// content=lianjie url ===imgurl
		List<AdArea> ad = adAreaService
				.findbypro("78abc97d5-123s-1g24-bab-0f11056a05bcc");
		return view.addObject("hotels", hotels)
		        .addObject("foods", foods)
				.addObject("type", "0")
				.addObject("plays", plays)
				.addObject("offices", offices).
				addObject("groupTravel", groupTravel)
				.addObject("banner", bannerList)
				.addObject("adList", ad);
	}

	/**
	 * 商户详情
	 */
	@Token(save=true)
	@RequestMapping({"merchantDetail","detail"})
	public ModelAndView merchantDetail(Pager pager, HttpServletRequest request,
			HttpServletResponse response) {
		ModelAndView view = new ModelAndView("portal/app/merchant/Detail");
		String code = request.getParameter("code");
		Merchant merchant = merchantService.getMerchantByCode(code);
		String priceRef = merchant.getPrice();
		if(StringUtils.isNotBlank(priceRef)&&priceRef.indexOf("暂无")>-1){
			merchant.setPrice("0");
		}
		if (merchant != null) {
			LogUser logUser = (LogUser) request.getSession()
			.getAttribute("logUser");
			view.addObject("logUser", logUser);
			List<Map<String, Object>> views = merchantService
					.getViewsByMerchantCode(code);
			Destination destination = destinationService
					.getDestinationByCode(merchant.getDistination());
			List<Image> imgs = imageService.getImageByMutiImageCode(merchant
					.getCode());
			Pager replys = merchantService.getReplyInfoByMerchantCode(pager,
					merchant.getCode());
			List<Map<String, Object>> praiseAndView = merchantService
					.getPraiseAndView(merchant.getCode());
			//查看数增加
			Praise p = praiseService.getPraiseContentCode(merchant.getCode());
			p.setFalseViewcount(p.getFalseViewcount()+1);
			p.setViewCount(p.getViewCount()+1);
			praiseService.update(p);
			return view.addObject("merchant", merchant).addObject("views",
					views).addObject("destination", destination).addObject(
					"imgs", imgs).addObject("replys", replys.getDataList())
					.addObject("praiseAndView", praiseAndView.get(0))
					.addObject("merchantCode", code);
		}else{
			
			view.addObject("/porta");
			
			
			
		}
		
		return view;
	}

	/**
	 * 通过avalon分页回复数据
	 */
	@RequestMapping(value = "replyInfo", produces = "text/html; charset=utf-8")
	@ResponseBody
	public String replyInfo(Pager pager, String merchantCode) {
		Pager pager2 = merchantService.getReplyInfoByMerchantCode(pager,
				merchantCode);
		pager2.setPageSize(5);
		return new Gson().toJson(pager2);
	}

	/**
	 * 保存对商户的评价
	 */
	@RequestMapping("saveReply")
	@Token(remove=true)
	public @ResponseBody
	String saveReply(Content reply, HttpServletRequest request, HttpServletResponse response) {
		LogUser logUser = (LogUser) request.getSession().getAttribute("logUser");
		if (logUser != null) {
			String merchantCode = request.getParameter("merchantCode");
			String merchantType = request.getParameter("merchantType");
			reply.setProgramaCode(merchantType);
			reply.setState(0);
			reply.setContentType(Content.DETAIL_MERCHANT_REPLY);
			reply.setCode(CodeFactory.createCode("merchantReply"));
			reply.setCreateuserCode(logUser.getCode());
			contentService.saveReply(reply, merchantCode);
			// 关联praise表
			Praise p = new Praise();
			p.setContentCode(reply.getCode());
			praiseService.save(p);
			return "评论成功，请等待审核！";
		}
		return "评价失败！";
	}

	/**
	 * ajax获取到景点的列表
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws JsonParseException
	 */
	@RequestMapping("getViews")
	public @ResponseBody
	Object getViews(HttpServletRequest request, HttpServletResponse response)
			throws JsonParseException {
		String destinationCode = request.getParameter("destinationCode");
		List<Map<String, String>> viewMap = new ArrayList<Map<String, String>>();
		List<View> views = viewService
				.findViewByDestinationCode(destinationCode);
		for (View view : views) {
			Map<String, String> map = new HashMap<String, String>();
			map.put(view.getViewName(), view.getCode());
			viewMap.add(map);
		}
		return JsonUtil.toJson(viewMap);
	}

	/**
	 * 获取数据给前台banner
	 * 
	 * @return imgUrl 后台传回的图片地址 hyperlink 后台传回的超链接
	 */
	public Map<String, List<String>> getBanner() {
		List<Content> bannerList = contentService
				.findContentByProgrmaCode("35abg9123-2b3c-1s24-bac-04456056a05bcs");
		List<String> imgUrl = new ArrayList<String>();
		List<String> hyperLink = new ArrayList<String>();
		for (Content c : bannerList) {
			imgUrl.add(c.getUrl());
			hyperLink.add(c.getContent());
		}
		Map<String, List<String>> banner = new HashMap<String, List<String>>();
		banner.put("imgUrl", imgUrl);
		banner.put("hyperLink", hyperLink);
		return banner;
	}

	/**
	 * 获取数据给前台 商户推荐
	 * 
	 * @return merchant 后台通过编号获取的merchant对象 hyperlink 后台设置的超链接
	 */
	public Map<String, Object> getCommer() {
		List<Content> commerList = contentService
				.findContentByProgrmaCode("6125abc85-35ac-32c4-26ce-00q5416a05bc9");
		List<Merchant> merchant = new ArrayList<Merchant>();
		List<String> hyperLink = new ArrayList<String>();
		for (Content c : commerList) {
			hyperLink.add(c.getUrl());
			Merchant m = merchantService.getMerchantByCode(c.getContent());
			merchant.add(m);
		}
		Map<String, Object> commer = new HashMap<String, Object>();
		commer.put("merchant", merchant);
		commer.put("hyperLink", hyperLink);
		return commer;
	}

	@RequestMapping({"getGroupTravelList","group"})
	public ModelAndView getGroupTravelList(Pager pager,
			HttpServletRequest request, HttpServletResponse response) {
		//System.out.println("-----------------------**");
		List<Map<String, Object>> top3Mt = merchantService.getTop3Mt();
		// 获取商户分类1,2,3
		MerchantType mt1 = new MerchantType();
		MerchantType mt2 = new MerchantType();
		MerchantType mt3 = new MerchantType();
		if (top3Mt != null && top3Mt.size() >= 1) {
			Map<String, Object> m1Map = top3Mt.get(0);
			mt1.setCode(m1Map.get("code").toString());
			mt1.setName(m1Map.get("name").toString());
		}
		if (top3Mt != null && top3Mt.size() >= 2) {
			Map<String, Object> m2Map = top3Mt.get(1);
			mt2.setCode(m2Map.get("code").toString());
			mt2.setName(m2Map.get("name").toString());
		}
		if (top3Mt != null && top3Mt.size() >= 3) {
			Map<String, Object> m3Map = top3Mt.get(2);
			mt3.setCode(m3Map.get("code").toString());
			mt3.setName(m3Map.get("name").toString());

		}
		ModelAndView view = new ModelAndView("portal/app/merchant/GtList");
		view.addObject("mt1", mt1);
		view.addObject("mt2", mt2);
		view.addObject("mt3", mt3);
		List<View> views = viewService.findAll();
		List<Destination> destinations = destinationService.getAllDestination();
		// viewService.f
		// 高级查询条件。
		String viewCode = request.getParameter("viewCode");
		String destinationCode = request.getParameter("destinationCode");
		Destination destination = destinationService
				.getDestinationByCode(destinationCode);
		View vi = viewService.findByCode(viewCode);
		String destinationName = "所有地区";
		if (destination != null) {
			destinationName = destination.getDestinationName();
		}
		String viewName = "所有景点";
		if (vi != null) {
			viewName = vi.getViewName();
		}
		String keyWord = request.getParameter("keyWord");
		// 分类和排序的条件。
		String rule = request.getParameter("rule");
		String type = request.getParameter("type");
		if (StringUtils.isBlank(type))
			type = "1";
		if (StringUtils.isBlank(rule))
			rule = "favorite";
		view.addObject("rule", rule);
		Pager pager2 = groupTravelService.getGroupTravelList(pager,
				destinationCode, viewCode, keyWord,rule);
		// 判断是否是官方推荐
		String isOffice = request.getParameter("isOffice");
		if (StringUtils.isNotBlank(isOffice))
			pager2 = merchantService.getOfficeList(pager);
		List<AdArea> ad = adAreaService
				.findbypro("781bc9785-1231-12a4-bab-0211056a05bce");

		// 在返回时要将高级查询的条件传回，然后排序与分类通过ajax来查询。
		return view.addObject("views", views).addObject("destinations",
				destinations).addObject("destinationName", destinationName)
				.addObject("viewCode", viewCode)
				.addObject("viewName", viewName).addObject("type", type)
				.addObject("rule", rule).addObject("destinationCode",
						destinationCode).addObject("keyWord", keyWord)
				.addObject("pager", pager2).addObject("adList", ad);
	}

	@RequestMapping(value = "avalonGroupTravelList", produces = "text/html; charset=utf-8")
	@ResponseBody
	public String avalonGroupTravelList(Pager pager, String viewCode,
			String destinationCode, String keyWord, String type, String rule) {
		Pager pager2 = groupTravelService.getGroupTravelList(pager,
				destinationCode, viewCode, keyWord,rule);
		pager2.setTotalPage(pager.getTotalPage());
		return new Gson().toJson(pager2);
	}
	/**
	 * 通过avalon分页回复数据
	 */
	@RequestMapping(value = "gtReplyInfo", produces = "text/html; charset=utf-8")
	@ResponseBody
	public String gtReplyInfo(Pager pager, String gtCode) {
		Pager pager2 = groupTravelService.gtReplyInfo(pager, gtCode); 
		pager2.setPageSize(5);
		return new Gson().toJson(pager2);
	}

	@RequestMapping("collectMerchant")
	@ResponseBody
	public boolean collectMerchant(HttpServletRequest request, HttpServletResponse response){
		//System.out.println("FrontMerchatController.collectMerchant()===================================================================================================================");
		String code = request.getParameter("code");
		LogUser logUser = (LogUser) request.getSession().getAttribute("logUser");
        if (logUser != null) {
		    userFavoriteService.saveFavorite(logUser.getCode(), code, UserFavorite.User_Fav_Merc);
		    return true;
		}
		return false;
	}
	
	/**
	 * 保存对団游的评价
	 */
	@RequestMapping("gtSaveReply")
	@Token(remove=true)
	public @ResponseBody
	String gtSaveReply(Content reply, HttpServletRequest request, HttpServletResponse response) {
		LogUser logUser = (LogUser) request.getSession().getAttribute("logUser");
		if (logUser != null) {
			String gtCode = request.getParameter("gtcode");
			reply.setState(0);
			reply.setContentType(Content.DETAIL_TOUR_GROUP_REPLY);
			reply.setCode(CodeFactory.createCode("gtReply"));
			reply.setCreateuserCode(logUser.getCode());
			contentService.saveReply(reply, gtCode);
			// 关联praise表
			Praise p = new Praise();
			p.setContentCode(reply.getCode());
			praiseService.save(p);
			return "评论成功，请等待审核！";
		}
		return "评价失败！";
	}
	
	
	/**
     * 通过商户code获取评分
     * tourism/merchant/getScore
     */
    @RequestMapping(value = "getScore", produces = "text/html; charset=utf-8")
    @ResponseBody
    public String getScore(String code) {
        return merchantService.getScore(code);
    }
	
}
