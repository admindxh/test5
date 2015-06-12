package com.rimi.ctibet.web.controller;

import com.rimi.ctibet.common.controller.BaseController;
import com.rimi.ctibet.common.util.*;
import com.rimi.ctibet.common.util.annotation.Token;
import com.rimi.ctibet.domain.*;
import com.rimi.ctibet.web.controller.vo.DesAndViewVo;
import com.rimi.ctibet.web.controller.vo.ViewUtil;
import com.rimi.ctibet.web.service.*;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 団游管理接口
 * @author xiaozhen
 *
 */
@Controller
@RequestMapping({"web/groupTravel","tourism/group"})
public class GroupTravelController extends BaseController{

	@Resource
	private IGroupTravelService groupTravelService;
	@Resource
	private DestinationService destinationService;
	@Resource
	private ImageService imageService;
	@Resource
	private DestinationService destinationServiceImpl;
	@Resource
	private  IViewService viewServiceImpl;
	@Resource
	private IMerchantService merchantService;
	@Resource
	private IPraiseService praiseService;
	@Resource
	private IContentService contentService;
	
	
	@ModelAttribute
	public void setNavSelectIndex(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) {
		modelMap.put("programNam", "1");
	}
	
	@RequestMapping("saveOrUpdateGroupTravel")
	public ModelAndView saveOrUpdateGroupTravel(String []imgCodes,String[] urls,String []vies,String[]dest,HttpServletRequest request,HttpServletResponse response,GroupTravel gt){
		String gtViewNum = request.getParameter("gtViewNum");
		String gtCollectNum = request.getParameter("gtCollectNum");
		String gtPrice = request.getParameter("gtPrice");
		if(StringUtils.isBlank(gtPrice))
			gtPrice = "0";
		gt.setPrice(gtPrice);
		if(StringUtils.isBlank(gtCollectNum)) {
            gtCollectNum = "0";
        }
		if(StringUtils.isBlank(gtViewNum)) {
            gtViewNum = "0";
        }
		gt.setViewNum(Integer.valueOf(gtViewNum));
		gt.setCollectNum(Integer.valueOf(gtCollectNum));
		if(urls!=null&&urls.length>0) {
            gt.setImg(urls[0]);
        }
        String ctripUrl = gt.getCtripUrl();
        if (StringUtils.isNotBlank(ctripUrl) && !ctripUrl.startsWith("http://")) {
            gt.setCtripUrl("http://" + ctripUrl);
        }
		//=================================修改============================================
		if(StringUtils.isNotBlank(gt.getCode())){
			GroupTravel ogt = groupTravelService.getGroupTravelByCode(gt.getCode());
			gt.setId(ogt.getId());
			gt.setUrl(super.getUrlHtml("tourism/group/detail",gt.getCode()));
			groupTravelService.updateGroupTravel(gt);
			for (String destCode : dest){
				GroupTravelDestination gtd = new GroupTravelDestination();
				gtd.setGroupTravelCode(gt.getCode());
				gtd.setDestinationCode(destCode);
				groupTravelService.saveGroupTravelDestination(gtd);
			}
			for(String vieCode : vies){
				GroupTravelView gtv = new GroupTravelView();
				gtv.setGroupTravelCode(gt.getCode());
				gtv.setViewCode(vieCode);
				groupTravelService.saveGroupTravelView(gtv);
			}
			 for (int i = 0; i < urls.length; i++) {
					String url  = urls[i];
					Image image =  new Image(1, null,null, "","");
				 	//image.setCode(Uuid.uuid());
				 	image.setCode(imgCodes[i]);
				 	image.setUrl(url);
				 	image.setMutiCode(gt.getCode());
				 	imageService.saveImage(image);
				}

            /*修改搜索信息*/
            WebSearch webSearch = new WebSearch();
            webSearch.setCode(gt.getCode());
            webSearch.setContent(gt.getDetail());
            webSearch.setImageUrl(gt.getImg());
            webSearch.setUrl(gt.getUrl());
            webSearch.setTitle(gt.getName());
            LuceneUtil2.update(webSearch);
		}
		//==================================新增===========================================
		else{
			gt.setCode(CodeFactory.create("GroupTravel"));
			//团游详情地址
			gt.setUrl(super.getUrlHtml("tourism/group/detail",gt.getCode()));
			//关联赞表
			Praise p =new Praise();
			p.setAvaliable(1);
			p.setCode(CodeFactory.create("praise"));
			p.setContentCode(gt.getCode());
			p.setCreateTime(new Date());
			praiseService.save(p);
			if(StringUtils.isBlank(gt.getPrice()))
				gt.setPrice(merchantService.getCtripPrice(gt.getName()));
			groupTravelService.saveGroupTravel(gt);
			for (String destCode : dest){
				GroupTravelDestination gtd = new GroupTravelDestination();
				gtd.setGroupTravelCode(gt.getCode());
				gtd.setDestinationCode(destCode);
				groupTravelService.saveGroupTravelDestination(gtd);
			}
			for(String vieCode : vies){
				GroupTravelView gtv = new GroupTravelView();
				gtv.setGroupTravelCode(gt.getCode());
				gtv.setViewCode(vieCode);
				groupTravelService.saveGroupTravelView(gtv);
			}
			 for (String url : urls) {
				 	Image image =  new Image(1, null, null, "","");
				 	//image.setCode(Uuid.uuid());
				 	image.setUrl(url);
				 	image.setMutiCode(gt.getCode());
				 	imageService.saveImage(image);
			 }

            /*保存搜索信息*/
            WebSearch webSearch = new WebSearch();
            webSearch.setCode(gt.getCode());
            webSearch.setContent(gt.getDetail());
            webSearch.setImageUrl(gt.getImg());
            webSearch.setUrl(gt.getUrl());
            webSearch.setTitle(gt.getName());
            LuceneUtil2.add(webSearch);
		}
		ModelAndView view = new ModelAndView("redirect:/web/groupTravel/searchGroupTravel");
		return view;
	}
	/**
	 * 根据目的地查询景点
	 * @param destinationCode
	 * @return
	 */
	public List<View> getListViewByDescode(String destinationCode){
		return  viewServiceImpl.findViewByDestinationCode(destinationCode);
	} 
	@RequestMapping("gotoSaveOrUpdateGroupTravel")
	public ModelAndView gotoSaveOrUpdateGroupTravel(HttpServletRequest request,HttpServletResponse response){
		ModelAndView view1 = new ModelAndView("manage/html/travel/GroupTravelEdit");
		String code = request.getParameter("code");
		GroupTravel gt = groupTravelService.getGroupTravelByCode(code);
		List<Image> imgs  = imageService.getImageByMutiCode(code);
		
		//目的地维护
		 List<Destination> destinationList  = destinationServiceImpl.getAllDestination();
		 List<DesAndViewVo>  desandviewList  = new ArrayList<DesAndViewVo>();
		 for (Destination destination : destinationList) {
			List<View>  viewList  = this.getListViewByDescode(destination.getCode());
			DesAndViewVo desAndViewVo  = new DesAndViewVo();
		    desAndViewVo.setDesName(destination.getDestinationName());
		    List<String[]>  vieString  = new ArrayList<String[]>();
		    if(ListUtil.isNotNull(viewList)){
    		   for (View view : viewList) {
    			   String [] viewInfo  =  new String[2];
    			   viewInfo[0] =  view.getViewName();
    			   viewInfo[1] =  view.getCode();
    			   vieString.add(viewInfo);
    		   }
		    }
			desAndViewVo.setViews(vieString);
			desandviewList.add(desAndViewVo);
		 }
		 //景点查询
		 view1.addObject("destinationList", destinationList);//目的地集合
		 view1.addObject("desandviewList", desandviewList);//景点集合
		 
		 //查询实际的 目的和景点
		 List<Destination> myDestinationList = groupTravelService.getDestinations(code);
		 List<View> myViewList = groupTravelService.getViews(code);
		 view1.addObject("myDestinationList", myDestinationList);
		 view1.addObject("myViewList", myViewList);
		 
		 return view1.addObject("groupTravel",gt).addObject("imgs",imgs);
	}
	@RequestMapping("deleteGroupTravel")
	public @ResponseBody String deleteGroupTravel(HttpServletRequest request,HttpServletResponse response){
        String code = request.getParameter("code");
        String[] codes = code.split(",");
        for (String scode : codes) {
        	groupTravelService.deleteGroupTravelByCode(scode);
        	contentService.deleteReplyByPostCodeLogical(scode, Content.DETAIL_TOUR_GROUP_REPLY);

            /*删除搜索*/
            WebSearch webSearch = new WebSearch();
            webSearch.setCode(scode);
            LuceneUtil2.delete(webSearch);
		}
		ModelAndView view = new ModelAndView("redirect:/web/groupTravel/searchGroupTravel");
		return "success";
	}
	@RequestMapping("deleteGroupTravels")
	public ModelAndView deleteGroupTravels(HttpServletRequest request,HttpServletResponse response){
        String code = request.getParameter("code");
        String[] codes = code.split(",");
        for (String scode : codes) {
        	groupTravelService.deleteGroupTravelByCode(scode);
        	contentService.deleteReplyByPostCodeLogical(scode, Content.DETAIL_TOUR_GROUP_REPLY);

            /*删除搜索*/
            WebSearch webSearch = new WebSearch();
            webSearch.setCode(scode);
            LuceneUtil2.delete(webSearch);
		}
		ModelAndView view = new ModelAndView("redirect:/web/groupTravel/searchGroupTravel");
		return view;
	}
	
	@RequestMapping("searchGroupTravel")
	public ModelAndView searchGroupTravel(HttpServletRequest request,HttpServletResponse response,Pager pager){
		ModelAndView view = new ModelAndView("manage/html/travel/GroupTravelList");
		String viewCode = request.getParameter("viewCode");
		String destinationCode = request.getParameter("destinationCode");
		String keyWord = request.getParameter("keyWord");
		String isRecommend = request.getParameter("isRecommend");
		//查询条件回显
		view.addObject("keyWord", keyWord);
		String destinationCodeText = "全部目的地";
		if(this.destinationService.findByCode(destinationCode)!=null) {
            destinationCodeText = this.destinationService.findByCode(destinationCode).getDestinationName();
            List<View>  viewList  = this.getListViewByDescode(destinationCode);
            //json lib 转化 对象
            List<ViewUtil> viewUtilList  = new ArrayList<ViewUtil>();
            for (View view1 : viewList) {
                ViewUtil viewUtil = new ViewUtil();
                viewUtil.setCode(view1.getCode());
                viewUtil.setViewName(view1.getViewName());
                viewUtilList.add(viewUtil);
            }
            view.addObject("viewList", viewUtilList);
        }
		view.addObject("destinationCodeText",destinationCodeText);
		view.addObject("destinationCode", destinationCode);
		String viewCodeText = "全部景点";
		if(this.viewServiceImpl.findByCode(viewCode)!=null) {
            viewCodeText = this.viewServiceImpl.findByCode(viewCode).getViewName();
        }
		view.addObject("viewCodeText",viewCodeText);
		view.addObject("viewCode", viewCode);
		view.addObject("isRecommend", isRecommend);

        Pager pager2 = groupTravelService.searchGroupTravel(pager, keyWord, destinationCode, viewCode,isRecommend);
		//获取地区列表
		List<View> areaList = viewServiceImpl.findAll();
		//获取目的地列表
		List<Destination> destination = destinationService.getAllDestination();
		view.addObject("destination", destination);
		//根据団游获取相关的目的地
		
		
		return view.addObject("pager",pager2);
	}
	@RequestMapping("orderByGroupTravel")
	public ModelAndView orderByGroupTravel(HttpServletRequest request,HttpServletResponse response,Pager pager){
		ModelAndView view = new ModelAndView("manage/html/travel/GroupTravelList");
		String rule = request.getParameter("rule");
		Pager pager2 = groupTravelService.orderByGroupTravel(pager, rule);
        view.addObject("pager", pager2);
        view.addObject("rule", rule);
        //获取目的地列表
        List<Destination> destination = destinationService.getAllDestination();
        view.addObject("destination", destination);

        String viewCode = request.getParameter("viewCode");
        String destinationCode = request.getParameter("destinationCode");
        String keyWord = request.getParameter("keyWord");
        String isRecommend = request.getParameter("isRecommend");
        view.addObject("keyWord", keyWord);
        String destinationCodeText = "全部目的地";
        if(this.destinationService.findByCode(destinationCode)!=null) {
            destinationCodeText = this.destinationService.findByCode(destinationCode).getDestinationName();
            List<View>  viewList  = this.getListViewByDescode(destinationCode);
            //json lib 转化 对象
            List<ViewUtil> viewUtilList  = new ArrayList<ViewUtil>();
            for (View view1 : viewList) {
                ViewUtil viewUtil = new ViewUtil();
                viewUtil.setCode(view1.getCode());
                viewUtil.setViewName(view1.getViewName());
                viewUtilList.add(viewUtil);
            }
            view.addObject("viewList", viewUtilList);
        }
        view.addObject("destinationCodeText",destinationCodeText);
        view.addObject("destinationCode", destinationCode);
        String viewCodeText = "全部景点";
        if(this.viewServiceImpl.findByCode(viewCode)!=null) {
            viewCodeText = this.viewServiceImpl.findByCode(viewCode).getViewName();
        }
        view.addObject("viewCodeText",viewCodeText);
        view.addObject("viewCode", viewCode);
        view.addObject("isRecommend", isRecommend);
		return view;
	}

    //获取到団游详情
	@Token(save=true)
	@RequestMapping({"groupTravelDetail","detail"})
	public ModelAndView groupTravelDetail(HttpServletRequest request,HttpServletResponse response){
		ModelAndView view = new ModelAndView("portal/app/merchant/GtDetail");
		String code = request.getParameter("code");
		GroupTravel gt = groupTravelService.getGroupTravelByCode(code);
		gt.setViewNum(gt.getViewNum()+1);
		Praise p = praiseService.getPraiseContentCode(gt.getCode());
		if(p!=null){
	       gt.setPraiseNum(p.getFavoriteNum());
	       gt.setViewNum(p.getViewCount());
	       gt.setCollectNum(p.getFalseFavoriteNum());
	       p.setViewCount(p.getViewCount()+1);
	       praiseService.update(p);
		}
		groupTravelService.updateNormalPro(gt);
		String priceRef = gt.getPrice();
		if(StringUtils.isNotBlank(priceRef)&&priceRef.indexOf("暂无")>-1){
			gt.setPrice("0");
			
		}
		//获取图集
		List<Image> imgs = imageService.getImageByMutiCode(gt.getCode());
		//获取相关的景点
		List<Destination> des = groupTravelService.getDestinations(gt.getCode());
		//获取相关景点
		List<View> views = groupTravelService.getViews(gt.getCode());
		LogUser logUser = (LogUser)request.getSession().getAttribute("logUser");
		view.addObject("logUser", logUser);
		view.addObject("gtd",gt);
		view.addObject("des",des);
		view.addObject("views", views);
		view.addObject("imgs", imgs);
		return view;
	}
	
	/**
     * 通过团游code获取评分
     * tourism/group/getScore
     */
    @RequestMapping(value = "getScore", produces = "text/html; charset=utf-8")
    @ResponseBody
    public String getScore(String code) {
        return groupTravelService.getScore(code);
    }
	
}
