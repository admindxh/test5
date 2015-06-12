package com.rimi.ctibet.web.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.rimi.ctibet.common.util.cache.CacheOperation;

@Controller
@RequestMapping("web/cacheManagerController")
public class CacheManagerController {

	
	/**
	 * 缓存页面list
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 */
	@RequestMapping("intoCacheList")
	public  String  intoCacheList(HttpServletRequest request,HttpServletResponse response,ModelMap modelMap){
		return  "/manage/html/settings/cache";
	}
	
	/**
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 */
	@RequestMapping("removeCacheData")
	@ResponseBody
	public  String  removeCacheData(HttpServletRequest request,HttpServletResponse response,ModelMap modelMap){
		
		//清除全部缓存
		
		try {
			CacheOperation.getInstance().removeAllCacheData();
			//CacheOperation.getInstance().removeAllRemoteCacheData();
		} catch (Exception e) {
			return "更新失败";
		}
		return "更新成功";
	}
	
	
	

	
	
}
