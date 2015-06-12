package com.rimi.ctibet.web.controller;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.rimi.ctibet.common.controller.BaseController;
import com.rimi.ctibet.domain.ScoreManager;
import com.rimi.ctibet.web.service.IScoreManagerService;

@Controller
@RequestMapping("web/scoreManagerController")
public class ScoreManagerController  extends BaseController {
		
		@Resource
		private IScoreManagerService scoreManagerService;
		
		
		
		/**
		 * 查询
		 * @param request
		 * @param response
		 * @return
		 */
		@RequestMapping("getListScoreManager")
		public  String  getListScoreManager(HttpServletRequest request,HttpServletResponse response,ModelMap modelMap){
			List<ScoreManager>  list  = scoreManagerService.findAll();
			
			modelMap.addAttribute("list",list);
			
			return "/manage/html/settings/score";
		}
		
		/**
		 * 查询
		 * @param request
		 * @param response
		 * @return
		 */
		@RequestMapping("updateScore")
		public  String  updateScore(String[] ids,String scorecount[],HttpServletRequest request,HttpServletResponse response,ModelMap modelMap){
			for (int i = 0; i < ids.length; i++) {
				ScoreManager scoreManager =  scoreManagerService.findById(Integer.valueOf(ids[i]));
				scoreManager.setScorecount(Integer.valueOf(scorecount[i]));
				scoreManagerService.update(scoreManager);
			}
			return  this.getListScoreManager(request, response, modelMap);
		}
		
		

		public IScoreManagerService getScoreManagerService() {
			return scoreManagerService;
		}

		public void setScoreManagerService(IScoreManagerService scoreManagerService) {
			this.scoreManagerService = scoreManagerService;
		}
		
}
