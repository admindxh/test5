package com.rimi.ctibet.web.controller;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.rimi.ctibet.common.controller.BaseController;
import com.rimi.ctibet.domain.Content;
import com.rimi.ctibet.domain.IndexManager;
import com.rimi.ctibet.web.service.IndexManagerService;
/**
 * 西藏文化传播
 * @author dengxh
 *
 */
@RequestMapping("web/indexWhMgController")
@Controller
public class IndexWhMgController extends BaseController {
	
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
		//{ "2010", "音乐" },
		//{ "2020", "小说" }, { "2030", "游戏" } 
		//saveType 保存类型 ，   save 保存，  preview 预览  设置  avaliable =1 保存 , 0 预览
		modelMap.addAttribute("proCode", proCode);//前台页面保存一个proCode栏目code
		List<IndexManager> list1   =  indexManagerService.getListIndexManager("09c7cb0d-7e8c-11e4-b6ce-005056a05bc9","2010");
		if (list1==null||list1.size()<=0) {
			//ContentType 
			//西藏文化传播列表  音乐
			indexManagerService.initIndexManagerbyScore("09c7cb0d-7e8c-11e4-b6ce-005056a05bc9","2010", 6);
			list1   =  indexManagerService.getListIndexManager("09c7cb0d-7e8c-11e4-b6ce-005056a05bc9","2010");
		}
		modelMap.addAttribute("list1",list1);
		
		List<IndexManager> list2   =  indexManagerService.getListIndexManager("45e0fd29-7e8c-11e4-b6ce-005056a05bc9","2020");
		if (list2==null||list2.size()<=0) {
			//ContentType 
			//西藏文化传播列表  小说
			indexManagerService.initIndexManagerbyScore("45e0fd29-7e8c-11e4-b6ce-005056a05bc9", "2020", 6);
			list2  = indexManagerService.getListIndexManager("45e0fd29-7e8c-11e4-b6ce-005056a05bc9","2020");
		}
		modelMap.addAttribute("list2",list2);
		List<IndexManager> list3   =  indexManagerService.getListIndexManager("61d19785-7e8c-11e4-b6ce-005056a05bc9","2030");
		if (list3==null||list3.size()<=0) {
			//ContentType 
			//西藏文化传播列表  游戏
			indexManagerService.initIndexManagerbyScore("61d19785-7e8c-11e4-b6ce-005056a05bc9", "2030", 6);
			list3  = indexManagerService.getListIndexManager("61d19785-7e8c-11e4-b6ce-005056a05bc9","2030");
		}
		modelMap.addAttribute("list3",list3);
		
		
		List<IndexManager> list4   =  indexManagerService.getListIndexManager("611bs12785-7w2e8c-11e4-b6ce-005056a05bc9","2040");
		if (list4==null||list4.size()<=0) {
			//ContentType 
			//西藏文化传播列表  游戏
			indexManagerService.initIndexManagerbyScore("611bs12785-7w2e8c-11e4-b6ce-005056a05bc9", "2040", 6);
			list4  = indexManagerService.getListIndexManager("611bs12785-7w2e8c-11e4-b6ce-005056a05bc9","2040");
		}
		modelMap.addAttribute("list4",list4);
		
		
		return "manage/html/home/culturalDiffuseUI";
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
		String program  = request.getParameter("program");
		String contentType  = request.getParameter("contentType");
		modelMap.addAttribute("proCode", proCode);//前台页面保存一个proCode栏目code
		//西藏文化传播列表  
		//indexManagerService.initIndexManager("09c7cb0d-7e8c-11e4-b6ce-005056a05bc9", "2010", 6);
		//indexManagerService.initIndexManager("45e0fd29-7e8c-11e4-b6ce-005056a05bc9", "2020", 6);
		indexManagerService.initIndexManagerbyScore(program,contentType, 6);
		this.dynamicDataAddUI(request, response, modelMap);
		return "manage/html/home/culturalDiffuseUI";
	}
	
	
	
	
	
	
	
	
	
	
	/**
	 * 首页西藏文化传播页面管理列表
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 */
	@RequestMapping("save")
	public String   dynamicDataSave(String program,String saveType,Integer  avaliable ,String codes[],String[]urls,String[]numbers,HttpServletRequest request,HttpServletResponse response,ModelMap modelMap){
		String proCode  = request.getParameter("proCode");
		modelMap.addAttribute("proCode", proCode);//前台页面保存一个proCode栏目code
		if (avaliable!=null&&avaliable==0) {
			 this.indexManagerService.deleteProgram(program,avaliable);
		}
		if (codes!=null) {
			for (int i = 0; i < codes.length; i++) {
				String code = codes[i];
				String url  = urls[i];
				String number  = numbers[i];
				IndexManager indexManager  = new IndexManager();
				indexManager.setCode(code);
				indexManager.setAvaliable(avaliable);
				indexManager.setUrl(url);
				indexManager.setNumber(number);
				indexManager.setPramaCode(program);
				this.indexManagerService.updateIndexManager(indexManager);
			}
		}
		if (saveType!=null&&saveType.equals("preview")) {
			//跳入到首页
			String springurl  = request.getParameter("springurl");
			return "redirect:/web/frontIndexController/"+springurl;
			
		}
		return  this.dynamicDataAddUI(request, response, modelMap);
	}

	public IndexManagerService getIndexManagerService() {
		return indexManagerService;
	}

	public void setIndexManagerService(IndexManagerService indexManagerService) {
		this.indexManagerService = indexManagerService;
	}
	
	@ResponseBody
	@RequestMapping("deleteIndexManage")
	/**
	 * 删除推荐信息
	 */
	public  String deleteIndexManage(HttpServletRequest req,HttpServletResponse resonse,ModelMap modelMap){
		
		//indexManagerService.deleteBySql("delete from indexmanager  where  programacode='"++"'");
		
		return "";
	}
	
}
