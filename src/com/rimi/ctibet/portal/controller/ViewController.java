package com.rimi.ctibet.portal.controller;

import java.text.SimpleDateFormat;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.ServletContext;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.ServletContextAware;

import com.rimi.ctibet.common.controller.BaseController;
import com.rimi.ctibet.common.util.DateUtil;
import com.rimi.ctibet.common.util.ListUtil;
import com.rimi.ctibet.common.util.Pager;
import com.rimi.ctibet.common.util.Uuid;
import com.rimi.ctibet.domain.Content;
import com.rimi.ctibet.domain.DesAndViewStatis;
import com.rimi.ctibet.domain.Destination;
import com.rimi.ctibet.domain.LogUser;
import com.rimi.ctibet.domain.UserView;
import com.rimi.ctibet.domain.View;
import com.rimi.ctibet.portal.controller.vo.HotMerchantVo;
import com.rimi.ctibet.portal.controller.vo.TravalFrontPageVo;
import com.rimi.ctibet.web.controller.vo.GroupTravelVO;
import com.rimi.ctibet.web.service.DestinationService;
import com.rimi.ctibet.web.service.IContentService;
import com.rimi.ctibet.web.service.IDesAndViewService;
import com.rimi.ctibet.web.service.IGroupTravelService;
import com.rimi.ctibet.web.service.IMerchantService;
import com.rimi.ctibet.web.service.IProgramaService;
import com.rimi.ctibet.web.service.IViewService;
import com.rimi.ctibet.web.service.ImageService;
import com.rimi.ctibet.web.service.MutiImageService;
import com.rimi.ctibet.web.service.UserViewService;

@Controller("portal_viewController")
@RequestMapping({"portal/view","tourism/view"})
public class ViewController extends BaseController implements ServletContextAware {
	
	ServletContext application;
	
	@Resource IViewService viewService;
	@Resource DestinationService destinationService;
    @Resource IContentService contentService;
    /*@Resource MutiImageDao mutiImageDao;
    @Resource IMerchantDao merchantDao;*/
    @Resource MutiImageService mutiImageService;
    @Resource IMerchantService merchantService;
    @Resource IProgramaService programaService;
    @Resource UserViewService userViewService;
    @Resource IDesAndViewService desAndViewService;
    @Resource ImageService imageService;
    @Resource IGroupTravelService groupTravelService;
	
    @ModelAttribute()
    public void initMethod(Model model){
        model.addAttribute("programNam","1");
    }
    
	/**
	 * 跳转到前台景点列表
	 */
	@RequestMapping({"forView","list"})
	public String forView(Model model, String destinationCode){
		List<Destination> listDestination = destinationService.getAllDestination();
		model.addAttribute("listDestination", listDestination);
		
		// 热门景点
        List<View> listHotView = viewService.searchViewByKeyWords(new Pager(3), null, null, null, null, View.ORDER_CHECKNUM).getDataList();
        model.addAttribute("listHotView", listHotView);
		
        Destination destination = destinationService.findByCode(destinationCode);
        if(destination!=null){
            model.addAttribute("destinationCode", destination.getCode());
        }
		model.addAttribute("ORDER_WANTCOUNT", View.ORDER_WANTCOUNT);
		model.addAttribute("ORDER_GONECOUNT", View.ORDER_GONECOUNT);
		model.addAttribute("ORDER_CHECKNUM", View.ORDER_CHECKNUM);
		model.addAttribute("ORDER_REPLYNUM", View.ORDER_REPLYNUM);
		return "portal/app/tourism/scenic_list";
	}
	
	/**
	 * 搜索前台景点列表
	 */
	@RequestMapping(value="searchViewList", produces = "text/html; charset=utf-8")
	@ResponseBody
	public String searchViewList(Pager pager, String destinationCode, String viewCode, String keyWords, int orderBy){
		pager = viewService.searchViewByKeyWords(pager, destinationCode, viewCode, null, keyWords, orderBy);
		return obj2json(pager);
	}
	
	/**
	 * 跳转到前台景点详情页
	 */
	@RequestMapping({"forViewDetail","detail"})
	public String forViewDetail(Model model, String code){
	    
	    //查看数+1
	    //if(!checkSessionOperate(session, code + "portal_for_view_detail")){
	        viewService.updateCheckNumByCode(code);
	    //}
	    // 景点详情
	    View view = viewService.findByCode(code);
	    model.addAttribute("view", view);
	    if(view!=null){
    	    Destination destination = destinationService.findByCode(view.getDestinationCode());
    	    model.addAttribute("destination", destination);
	    }
	    model.addAttribute("currentDate", new SimpleDateFormat("yyyy-MM-dd").format(DateUtil.getCurrentDate()));
	    // 精彩旅程（相关攻略）
	    List<TravalFrontPageVo> listTravalFrontPageVo = contentService.findPagerTravel(new Pager(4), null, null, null, code, null,null).getDataList();
        for (TravalFrontPageVo travalFrontPageVo : listTravalFrontPageVo) {
            String srcImg = super.getContentImgSrc(travalFrontPageVo.getTravelContent());
            travalFrontPageVo.setTravelImgUrl(srcImg);
        }
	    model.addAttribute("listTravalFrontPageVo", listTravalFrontPageVo);
	    // 相关景点
	    List<View> listView = viewService.findViewByDestinationCode(view.getDestinationCode());
	    model.addAttribute("listView", listView);
	    // 景点图集
	    List listViewAtlas = imageService.getViewAtlasByCodeType(new Pager(1), view.getCode(), null).getDataList();
	    if(ListUtil.isNotNull(listViewAtlas)){
	        model.addAttribute("haveViewAtlas", "yes");
	    }else{
	        model.addAttribute("haveViewAtlas", "no");
	    }
	    //
	    
	    // 机票
	    
        // 推荐商户
	    //List<HotMerchantVo> listHotMerchantVo = merchantService.getHotMerchant(4, view.getDestinationCode());
	    List<HotMerchantVo> listHotMerchantVo = merchantService.getHotMerchantByView(code, 4);
	    if(!ListUtil.isNotNull(listHotMerchantVo)){
	        //景点没有商户就取景点对应目的地的商户
	        listHotMerchantVo = merchantService.getHotMerchantBydestinationCode(view.getDestinationCode(), 4);
	    }
	    model.addAttribute("listHotMerchantVo", listHotMerchantVo);
	    // 团游
	    List<GroupTravelVO> listGroupTravelVO = groupTravelService.getGroupTravelRecommendByViewCode(4, code);
	    if(!ListUtil.isNotNull(listGroupTravelVO)){
            //景点没有团游就取景点对应目的地的团游
	        listGroupTravelVO = groupTravelService.getGroupTravelRecommendBydestinationCode(4, view.getDestinationCode());
        }
	    model.addAttribute("listGroupTravelVO", listGroupTravelVO);
	    
	    /*List<MerchantVo>  merchantList  = merchantService.getPortalMerchant(4);
	    model.addAttribute("merchantList", merchantList);*/
	    
	    //想去去过类型
	    model.addAttribute("User_View_Wanna", UserView.User_View_Wanna);
	    model.addAttribute("User_View_Gone", UserView.User_View_Gone);
	    
	    return "portal/app/tourism/scenic_detail";
	}
	/**
	 * 通过景点code 读取评论列表
	 */
	@RequestMapping(value="loadReply", produces = "text/html; charset=utf-8")
    @ResponseBody
	public String loadReply(Pager pager, String code){
	    pager = contentService.getReplyByPostCodeState(pager, code,Content.DETAIL_VIEW_REPLY, Content.REVIEW_STATE_PASS);
	    return obj2json(pager);
	}
	/**
	 * 保存回复
	 */
	@RequestMapping(value="reply", produces = "text/html; charset=utf-8")
	@ResponseBody
	public String reply(String replyContent, String code){
	    if(getFrontUser()!=null){
	        Content content = new Content();
	        content.setCode(Uuid.uuid());
	        content.setContentType(Content.DETAIL_VIEW_REPLY);
	        content.setContent(replyContent);
	        content.setCreateuserCode(getFrontUser().getCode());
	        content.setState(0);
	        try {
	            contentService.saveReply(content, code);
	            return "success";
	        } catch (Exception e) {
	            e.printStackTrace();
	            return "error";
	        }
	    }else{
	        return "needlogin";
	    }
	}
	/**
	 * 景点点赞
	 * portal/view/clickLike
	 */
	@RequestMapping(value="clickLike", produces = "text/html; charset=utf-8")
	@ResponseBody
	public String clickLike(String code){
	    if(!checkSessionOperate(session, code + "view_click_like")){
	        try {
                viewService.updateLikeNumByCode(code);
                return "success";
            } catch (Exception e) {
                e.printStackTrace();
                return "error";
            }
	    }else{
	        return "already";
	    }
	}
	
	/**
	 * 跳转到 景点图集列表
	 */
	@RequestMapping("forPhotoGalleryView")
	public String forPhotoGalleryView(Model model, String viewCode, String destinationCode){
	    View view = viewService.findByCode(viewCode);
	    Destination destination = destinationService.findByCode(destinationCode);
        if (view != null) {
            model.addAttribute("viewName", view.getViewName());
            model.addAttribute("viewCode", view.getCode());
            model.addAttribute("view", view);
        }
        if(destination!=null){
            model.addAttribute("destinationName", destination.getDestinationName());
            model.addAttribute("destinationCode", destination.getCode());
            model.addAttribute("destination", destination);
        }
	    return "portal/app/tourism/photo_gallery_view";
	}
    /**
     * 获取景点图集
     */
    @RequestMapping(value="loadViewAtlas", produces = "text/html; charset=utf-8")
    @ResponseBody
    public String loadViewAtlas(Pager pager, String viewCode, String type){
        pager = imageService.getViewAtlasByCodeType(pager, viewCode, type);
        return obj2json(pager);
    }
	
    /**
     * 检查想去去过
     */
    @RequestMapping(value="checkGoneOrWant", produces = "text/html; charset=utf-8")
    @ResponseBody
    public String checkGoneOrWant(String viewCode, String type){
        LogUser user = getFrontUser();
        String returnValue = "";
        if(user!=null){
            UserView userView = userViewService.getUserView(user.getCode(), viewCode, type);
            if(userView!=null && userView.getType().equals(UserView.User_View_Gone)){
                returnValue = "already_gone";
            }else if(userView!=null && userView.getType().equals(UserView.User_View_Wanna)){
                returnValue = "already_wanna";
            }
        }
        return returnValue;
    }
	/**
     * 点击想去去过
     */
    @RequestMapping(value="clickGoneOrWant", produces = "text/html; charset=utf-8")
    @ResponseBody
    public String clickGoneOrWant(String viewCode, String type){
        LogUser user = getFrontUser();
        String returnValue = "";
        if(user!=null){
            UserView userView = userViewService.getUserView(user.getCode(), viewCode, type);
            if(userView!=null && userView.getType().equals(UserView.User_View_Gone)){
                returnValue = "already_gone";
            }else if(userView!=null && userView.getType().equals(UserView.User_View_Wanna)){
                returnValue = "already_wanna";
            }else{
                try {
                    View view = viewService.findByCode(viewCode);
                    UserView obj = new UserView();
                    obj.setAvaliable(1);
                    obj.setViewCode(viewCode);
                    obj.setAreaCode(view.getDestinationCode());
                    obj.setMemberCode(user.getCode());
                    obj.setType(type);
                    userViewService.saveUserView(obj);
                    
                    //统计
                    DesAndViewStatis desAndViewStatis = new DesAndViewStatis();
                    desAndViewStatis.setCtime(DateUtil.getCurrentDate());
                    desAndViewStatis.setCuserCode(user.getCode());
                    desAndViewStatis.setViewCode(viewCode);
                    if(type.equals(UserView.User_View_Wanna)){
                        desAndViewStatis.setType("0");
                    }
                    if(type.equals(UserView.User_View_Gone)){
                        desAndViewStatis.setType("1");
                    }
                    desAndViewService.save(desAndViewStatis);
                    
                    returnValue = "success";
                } catch (Exception e) {
                    e.printStackTrace();
                    returnValue = "error";
                }
            }
        }else{
            returnValue = "need_login";
        }
        return returnValue;
    }
	
	/********************************************
	 *  Setter Getter
	 ********************************************/
	@Override
	public void setServletContext(ServletContext application) {
		this.application = application;
	}
	
}
