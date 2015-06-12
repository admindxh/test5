package com.rimi.ctibet.web.controller;

import java.util.Date;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.rimi.ctibet.common.constants.Constants;
import com.rimi.ctibet.common.controller.BaseController;
import com.rimi.ctibet.domain.IndexManager;
import com.rimi.ctibet.domain.Praise;
import com.rimi.ctibet.domain.UserFavorite;
import com.rimi.ctibet.web.service.IPraiseAndViewManagerService;
import com.rimi.ctibet.web.service.IPraiseService;
import com.rimi.ctibet.web.service.IUserFavoriteService;
import com.rimi.ctibet.web.service.IndexManagerService;

/**
 * 天上社区最赞回复管理
 * @author dengxh

 *
 */
@RequestMapping("web/praiseController")
@Controller
public class PraiseController  extends BaseController{
		
	  @Resource
      private 	IPraiseService praiseService;
	  @Resource
	  private IPraiseAndViewManagerService praiseAndViewManagerService;
	  
	  @Resource
	  private IUserFavoriteService   userFavoriteService;
	  @Resource
	  private IndexManagerService indexManagerService;
	  
	  /**
		 * 天上社区最赞回复管理 列表
		 * @param request
		 * @param response
		 * @param modelMap
		 * @return
		 */
		@RequestMapping("saveUI")
		public String   dynamicDataAddUI(HttpServletRequest request,HttpServletResponse response,ModelMap modelMap){
			String proCode  = request.getParameter("proCode");
			modelMap.addAttribute("proCode", proCode);//前台页面保存一个proCode栏目code
			
			//modelMap.addAttribute("list",list);
			return "manage/html/other/天上社区最赞回复管理";
		}
		
		
		/**
		 * 点击浏览
		 * @param request
		 * @param response
		 * @param modelMap
		 * @return
		 */
		@RequestMapping(value="clickViewCount")
		@ResponseBody
		public String  clickViewCount(String code,HttpServletRequest request,HttpServletResponse response,ModelMap modelMap){
			//保存赞，
			//praiseService.updatePraiseCount(code);
			//保存是否点击赞了。。记录 验证电脑
			//返回 true  和 false  是否点击赞
			praiseService.updateViewCount(code);
			return  "";
		}
		/**
		 * 前端 点击赞 实现
		 * @param request
		 * @param response
		 * @param modelMap
		 * @return
		 */
		@RequestMapping(value="clickPraiseFront")
		@ResponseBody
		public String  clickPraiseFront(String imCode,String code,HttpServletRequest request,HttpServletResponse response,ModelMap modelMap){
			//保存赞，
			praiseService.updatePraiseCount(code);
			Praise  praise =  praiseService.getPraiseContentCode(code);
			//保存是否点击赞了。。记录 验证电脑
			praiseAndViewManagerService.saveRecord(getIpAddr(request), code);
			if(StringUtils.isNotBlank(imCode)){
				IndexManager im = indexManagerService.findByCode(imCode);
				if(im!=null){
				im.setPraise(praise.getFalsePraise().toString());
				indexManagerService.update(im);}
			}
			String ps  =  praise==null?"0":String.valueOf(praise.getFalsePraise().toString());
			return  ps;
		}
		
		
		
		/**
		 * 前端 点击收藏 实现
		 * @param request
		 * @param response
		 * @param modelMap
		 * @return
		 */
		@RequestMapping(value="clickFavateFront")
		@ResponseBody
		public String  clickFavateFront(String code,HttpServletRequest request,HttpServletResponse response,ModelMap modelMap){
			//保存赞表中的收藏
			praiseService.updateFavateCount(code);
			//保存是否点收藏了。。记录 验证电脑
			UserFavorite favorite  =  new UserFavorite();
			favorite.setCode(code);
			if (getFrontUser()!=null) {
				favorite.setMemberCode(getFrontUser().getCode());
			}
			favorite.setType(UserFavorite.User_Fav_Stra);
			favorite.setState(String.valueOf(Constants.STATUS_PASS));
			favorite.setJoinTime(new Date());
			userFavoriteService.save(favorite);
			//praiseAndViewManagerService.saveRecord(request.getServerName(), code);
			Praise  praise =  praiseService.getPraiseContentCode(code);
			String ps  =  praise==null?"0":String.valueOf(praise.getFalseFavoriteNum().toString());
			return  ps;
		}
		
		
		
		/**
		 * 攻略  本机是否点收藏了
		 * @param request
		 * @param response
		 * @param modelMap
		 * @return
		 */
		@RequestMapping(value="isRecoredFavate")
		@ResponseBody
		public String  isRecoredFavate(String code,HttpServletRequest request,HttpServletResponse response,ModelMap modelMap){
			//保存赞，
			//praiseService.updatePraiseCount(code);
			//保存是否点击赞了。。记录 验证电脑
			//返回 true  和 false  是否点击赞
			String memCode  = "";
			if (getFrontUser()!=null) {
				 memCode = getFrontUser().getCode();
			}
			return  ""+userFavoriteService.isFavAlready(memCode, code, ""+UserFavorite.User_Fav_Stra);
		}
		
		
		
		/**
		 * 商户 本机是否点收藏了
		 * @param request
		 * @param response
		 * @param modelMap
		 * @return
		 */
		@RequestMapping(value="isRecoredMerchantFavate")
		@ResponseBody
		public String  isRecoredMerchantFavate(String code,HttpServletRequest request,HttpServletResponse response,ModelMap modelMap){
			//保存赞，
			//praiseService.updatePraiseCount(code);
			//保存是否点击赞了。。记录 验证电脑
			//返回 true  和 false  是否点击赞
			String memCode  = "";
			if (getFrontUser()!=null) {
				 memCode = getFrontUser().getCode();
			}
			return  ""+userFavoriteService.isFavAlready(memCode, code, ""+UserFavorite.User_Fav_Merc);
		}
		
		/**
		 * 前端商户 点击收藏 实现
		 * @param request
		 * @param response
		 * @param modelMap
		 * @return
		 */
		@RequestMapping(value="clickFavateMerchantFront")
		@ResponseBody
		public String  clickFavateMerchantFront(String code,HttpServletRequest request,HttpServletResponse response,ModelMap modelMap){
			//保存赞表中的收藏
			praiseService.updateFavateCount(code);
			//保存是否点收藏了。。记录 验证电脑
			UserFavorite favorite  =  new UserFavorite();
			favorite.setCode(code);
			if (getFrontUser()!=null) {
				favorite.setMemberCode(getFrontUser().getCode());
			}
			favorite.setType(UserFavorite.User_Fav_Merc);
			favorite.setState(String.valueOf(Constants.STATUS_PASS));
			favorite.setJoinTime(new Date());
			userFavoriteService.save(favorite);
			//praiseAndViewManagerService.saveRecord(request.getServerName(), code);
			Praise  praise =  praiseService.getPraiseContentCode(code);
			String ps  =  praise==null?"0":String.valueOf(praise.getFalseFavoriteNum().toString());
			return  ps;
		}
		/**
		 * 本机是否点击赞了
		 * @param request
		 * @param response
		 * @param modelMap
		 * @return
		 */
		@RequestMapping(value="isRecored")
		@ResponseBody
		public String  isRecored(String code,HttpServletRequest request,HttpServletResponse response,ModelMap modelMap){
			//保存赞，
			//praiseService.updatePraiseCount(code);
			//保存是否点击赞了。。记录 验证电脑
			//返回 true  和 false  是否点击赞
			if(!praiseAndViewManagerService.isRecored(getIpAddr(request), code)){
				//praiseAndViewManagerService.saveRecord(getIpAddr(request),code);
				Praise p = praiseService.getPraiseContentCode(code);
			    if(p!=null){
			    }
				return "false";
			}
			return  ""+praiseAndViewManagerService.isRecored(getIpAddr(request), code) ;
		}
		/**
		 *攻略的浏览
		 * @param request
		 * @param response
		 * @param modelMap
		 * @return
		 */
		@RequestMapping(value="updateViewCount")
		@ResponseBody
		public String  updateViewCount(String contentCode){
			praiseService.updateViewCount(contentCode);
			return "";
		}
}
