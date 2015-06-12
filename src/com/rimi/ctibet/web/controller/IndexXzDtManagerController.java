package com.rimi.ctibet.web.controller;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.rimi.ctibet.common.controller.BaseController;
import com.rimi.ctibet.domain.Content;
import com.rimi.ctibet.domain.IndexManager;
import com.rimi.ctibet.web.service.IndexManagerService;
/**
 * 西藏动态
 * @author dengxh
 *
 */
@RequestMapping("web/indexXzDtManagerController")
@Controller
public class IndexXzDtManagerController extends BaseController {
	
	@Resource
	private IndexManagerService indexManagerService;
	
	
	
	/**
	 * 首页动态页面管理列表
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 */
	@RequestMapping("saveUI")
	public String   dynamicDataAddUI(HttpServletRequest request,HttpServletResponse response,ModelMap modelMap){
		String proCode  = request.getParameter("proCode");
		modelMap.addAttribute("proCode", proCode);//前台页面保存一个proCode栏目code
		List<IndexManager> list   =  indexManagerService.getListIndexManager("13198b8d-75da-11e4-b6ce-005056a05bc9", "1120");
		if (list==null||list.size()<=0) {
			//ContentType 
			//西藏动态列表
			indexManagerService.initIndexManager("13198b8d-75da-11e4-b6ce-005056a05bc9", "1120", 4);
			list=  indexManagerService.getListIndexManager("13198b8d-75da-11e4-b6ce-005056a05bc9", "1120");
		}
		modelMap.addAttribute("list",list);
		return "manage/html/home/dynamicUI";
	}
	
	/**
	 * 显示最新
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 */
	@RequestMapping("showNew")
	public String   showNew(HttpServletRequest request,HttpServletResponse response,ModelMap modelMap){
		String proCode  = request.getParameter("proCode");
		modelMap.addAttribute("proCode", proCode);//前台页面保存一个proCode栏目code
		//西藏动态列表
		indexManagerService.initIndexManager("13198b8d-75da-11e4-b6ce-005056a05bc9", "1120", 4);
		this.dynamicDataAddUI(request, response, modelMap);
		return "manage/html/home/dynamicUI";
	}
	
	
	
	
	/**
	 * 首页动态页面管理列表
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 */
	@RequestMapping("save")
	public String   dynamicDataSave(String saveType,Integer  avaliable , String codes[],String[]urls,String numbers[],HttpServletRequest request,HttpServletResponse response,ModelMap modelMap){
		String proCode  = request.getParameter("proCode");
		modelMap.addAttribute("proCode", proCode);//前台页面保存一个proCode栏目code
		if (avaliable!=null&&avaliable==0) {
			 this.indexManagerService.deleteProgram("13198b8d-75da-11e4-b6ce-005056a05bc9",avaliable);
		}
		for (int i = 0; i < codes.length; i++) {
			String code = codes[i];
			String url  = urls[i];
			String number  = numbers[i];
			IndexManager indexManager  = new IndexManager();
			indexManager.setCode(code);
			indexManager.setAvaliable(avaliable);
			indexManager.setUrl(url);
			indexManager.setNumber(number);
			indexManager.setPramaCode("13198b8d-75da-11e4-b6ce-005056a05bc9");
			this.indexManagerService.updateIndexManager(indexManager);
		}
		if (saveType!=null&&saveType.equals("preview")) {
			return "redirect:/web/frontIndexController/indexXzDtManagerController";
		}
		return  this.dynamicDataAddUI(request, response, modelMap);
	}

	public IndexManagerService getIndexManagerService() {
		return indexManagerService;
	}

	public void setIndexManagerService(IndexManagerService indexManagerService) {
		this.indexManagerService = indexManagerService;
	}
	
	
	
	
	
}
