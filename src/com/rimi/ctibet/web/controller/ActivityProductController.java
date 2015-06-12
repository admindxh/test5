package com.rimi.ctibet.web.controller;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.rimi.ctibet.common.controller.BaseController;
import com.rimi.ctibet.common.util.Pager;
import com.rimi.ctibet.web.service.IActivityProductService;

@Controller
@RequestMapping("web/activityProductController")
public class ActivityProductController extends BaseController {

	@Resource IActivityProductService activityProductService;
	
	/**
	 * 获取活动管理页 最新上传作品
	 * web/activityProductController/showActManageActivityProductList
	 */
	@RequestMapping(value="showActManageActivityProductList", produces = "text/html; charset=utf-8")
	@ResponseBody
	public String showActManageActivityProductList(Pager pager, String activityCode){
		pager = activityProductService.getActivityProductsByActCodeIsTop(pager, activityCode, false, 100);
		return obj2json(pager);
	}
	
	/**
	 * 审核作品
	 * web/activityProductController/reviewActivityProduct
	 * @return
	 */
	@RequestMapping(value="reviewActivityProduct", produces = "text/html; charset=utf-8")
	@ResponseBody
	public String reviewActivityProduct(String code){
		try {
			activityProductService.updateState(code, 1);
			return "success";
		} catch (Exception e) {
			e.printStackTrace();
			return "error";
		}
	}
	/**
	 * 审核活动管理页作品
	 * @return
	 */
	@RequestMapping("reviewManageActivityProduct")
	public String reviewManageActivityProduct(Model model, String code, String activityCode, Integer actProductPage, Integer enrollFormPage){
		activityProductService.updateState(code, 1);
		model.addAttribute("actProductPage", (actProductPage==null)?1:actProductPage);
		model.addAttribute("enrollFormPage", (enrollFormPage==null)?1:enrollFormPage);
		model.addAttribute("activityCode", activityCode);
		return redirect("/web/activityController/forActivityManage");
	}
	
	/**
	 * 置顶作品
	 * web/activityProductController/topActivityProduct
	 * @return
	 */
	@RequestMapping(value="topActivityProduct", produces = "text/html; charset=utf-8")
	@ResponseBody
	public String topActivityProduct(String code){
		try {
			activityProductService.updateIsTop(code, 1);
			return "success";
		} catch (Exception e) {
			e.printStackTrace();
			return "error";
		}
	}
	/**
	 * 置顶活动管理页作品
	 * @return
	 */
	@RequestMapping("topManageActivityProduct")
	public String topManageActivityProduct(Model model, String code, String activityCode, Integer actProductPage, Integer enrollFormPage){
		activityProductService.updateIsTop(code, 1);
		model.addAttribute("actProductPage", (actProductPage==null)?1:actProductPage);
		model.addAttribute("enrollFormPage", (enrollFormPage==null)?1:enrollFormPage);
		model.addAttribute("activityCode", activityCode);
		return redirect("/web/activityController/forActivityManage");
	}
	/**
	 * 取消置顶活动管理页作品
	 * @return
	 */
	@RequestMapping("unTopManageActivityProduct")
	public String unTopManageActivityProduct(Model model, String code, String activityCode, Integer actProductPage, Integer enrollFormPage){
	    activityProductService.updateIsTop(code, 0);
	    model.addAttribute("actProductPage", (actProductPage==null)?1:actProductPage);
	    model.addAttribute("enrollFormPage", (enrollFormPage==null)?1:enrollFormPage);
	    model.addAttribute("activityCode", activityCode);
	    return redirect("/web/activityController/forActivityManage");
	}
	
	/**
	 * 删除作品
	 * web/activityProductController/deleteActivityProduct
	 * @return
	 */
	@RequestMapping(value="deleteActivityProduct", produces = "text/html; charset=utf-8")
	@ResponseBody
	public String deleteActivityProduct(String code){
		try {
			activityProductService.deleteLogicalByCode(code);
			return "success";
		} catch (Exception e) {
			e.printStackTrace();
			return "error";
		}
	}
	/**
	 * 删除活动管理页作品
	 * @return
	 */
	@RequestMapping("deleteManageActivityProduct")
	public String deleteManageActivityProduct(Model model, String code, String activityCode, Integer actProductPage, Integer enrollFormPage){
		activityProductService.deleteLogicalByCode(code);
		model.addAttribute("actProductPage", (actProductPage==null)?1:actProductPage);
		model.addAttribute("enrollFormPage", (enrollFormPage==null)?1:enrollFormPage);
		model.addAttribute("activityCode", activityCode);
		return redirect("/web/activityController/forActivityManage");
	}
	
	/**
	 * 修改伪赞
	 * web/activityProductController/updateFakeLikeNum
	 * @return
	 */
	@RequestMapping(value="updateFakeLikeNum", produces = "text/html; charset=utf-8")
	@ResponseBody
	public String updateFakeLikeNum(String code, int fakeLikeNum){
		try {
			activityProductService.updateFakeLikeNum(code, fakeLikeNum);
			return "success";
		} catch (Exception e) {
			e.printStackTrace();
			return "error";
		}
	}
	
}
