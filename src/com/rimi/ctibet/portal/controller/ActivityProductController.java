package com.rimi.ctibet.portal.controller;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.ServletContext;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.ServletContextAware;

import com.google.gson.Gson;
import com.rimi.ctibet.common.controller.BaseController;
import com.rimi.ctibet.common.util.CodeFactory;
import com.rimi.ctibet.common.util.FileUtil;
import com.rimi.ctibet.common.util.Pager;
import com.rimi.ctibet.domain.Activity;
import com.rimi.ctibet.domain.ActivityProduct;
import com.rimi.ctibet.domain.LogUser;
import com.rimi.ctibet.web.controller.vo.MemberEnrollDetailVO;
import com.rimi.ctibet.web.service.IActivityProductService;
import com.rimi.ctibet.web.service.IActivityService;
import com.rimi.ctibet.web.service.IMemberEnrollDetailService;

@Controller("portal_activityProductController")
@RequestMapping("portal/activityProductController")
public class ActivityProductController extends BaseController implements ServletContextAware {
	
	public static final	Logger LOGGER = Logger.getLogger(ActivityProductController.class);
	ServletContext application;
	
	@Resource IActivityProductService activityProductService;
	@Resource IActivityService activityService;
	@Resource IMemberEnrollDetailService memberEnrollDetailService;
	
	final String tempPath = "/temp/";
	/**
	 * 保存活动作品
	 * @param activityProduct
	 * @return
	 */
	@RequestMapping(value="addActivityProduct", produces = "text/html; charset=utf-8")
	@ResponseBody
	public String addActivityProduct(ActivityProduct activityProduct){
		try {
			String basePath = application.getRealPath("/");
			String newPath = activityProduct.getUrl().replace(tempPath, "");
			FileUtil.moveFile(basePath + activityProduct.getUrl(), basePath+newPath);
			
			activityProduct.setUrl(newPath);
			activityProduct.setCode(CodeFactory.createCode("ACTP"));
			activityProduct.setMemberCode(getFrontUser().getCode());
			activityProduct.setCreateTime(new Date(System.currentTimeMillis()));
			activityProductService.saveActivityProduct(activityProduct);
			return "success";
		} catch (Exception e) {
			e.printStackTrace();
			return "error";
		}
	}
	
	/**
	 * 获取活动详情页 最新上传作品
	 * portal/activityProductController/showActivityDetailActivityProductList
	 */
	@RequestMapping(value="showActivityDetailActivityProductList", produces = "text/html; charset=utf-8")
	@ResponseBody
	public String showActivityDetailActivityProductList(Pager pager, String activityCode){
		pager = activityProductService.getActivityProductsByActCodeIsTop(pager, activityCode, true, 1);
		pager.setTotalPage(pager.getTotalPage());
		return new Gson().toJson(pager);
	}
	
	/**
	 * 点赞
	 * portal/activityProductController/clickLike
	 * @return
	 */
	@RequestMapping(value="clickLike", produces = "text/html; charset=utf-8")
	@ResponseBody
	public String clickLike(String code){
		try {
		    if(!checkSessionOperate(session, code + "activityDetailtClilkLike")){
		        activityProductService.updateLikeNum(code);
		        return "success";
		    }else{
		        return "already";
		    }
		} catch (Exception e) {
			e.printStackTrace();
			return "error";
		}
	}
	
	/**
	 * 检查上传作品权限
	 * portal/activityProductController/checkActtivityProduct
	 */
	@RequestMapping(value="checkActtivityProduct", produces = "text/html; charset=utf-8")
	@ResponseBody
	public String checkActtivityProduct(String activityCode){
		LogUser user = getFrontUser();
		if(user!=null){
			String memberCode = user.getCode();
			Activity activity = activityService.findByCode(activityCode);
			int isEnroll = activity.getIsEnroll();
			if(isEnroll==1){
				List<MemberEnrollDetailVO> list = memberEnrollDetailService.getMemberEnrollDetailByActCodeMemberCode(activityCode, memberCode);
				if(list!=null && list.size()>0){
					return "";
				}else{
					//return "请先报名"; //目前报名和上传没有关系
				    return "";
				}
			}else{
				return "";
			}
		}else{
			return "请先登录";
		}
	}
	
	/********************************************
	 * Setter Getter
	 ********************************************/
	@Override
	public void setServletContext(ServletContext arg0) {
		this.application = arg0;
	}
}
