
package com.rimi.ctibet.web.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.rimi.ctibet.common.controller.BaseController;
import com.rimi.ctibet.common.util.StringUtil;
import com.rimi.ctibet.domain.Content;
import com.rimi.ctibet.domain.IndexManager;
import com.rimi.ctibet.domain.Praise;
import com.rimi.ctibet.web.service.IPraiseService;
import com.rimi.ctibet.web.service.IndexManagerService;
/**
 * 天上社区最赞回复管理
 * @author dengxh
 *
 */
@RequestMapping("web/bbsReplyControoler")
@Controller
public class BbsReplyControoler extends BaseController {
	
	@Resource
	private IndexManagerService indexManagerService;
	@Resource
	private IPraiseService praiseService;
	
	
	
	
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
		List<IndexManager> list   =  indexManagerService.getListIndexManager("e43cb122-72d6-11e4-b4ce-005056a05bc9",Content.CONTENTTYPE_REPLY);
		if (list==null||list.size()<=0) {
			//ContentType 
			 //帖子动态列表
			  indexManagerService.initIndexManagerbyPraise("e43cb122-72d6-11e4-b4ce-005056a05bc9", Content.CONTENTTYPE_REPLY, 5);
			  list   =  indexManagerService.getListIndexManager("e43cb122-72d6-11e4-b4ce-005056a05bc9",Content.CONTENTTYPE_REPLY);//indexManagerService.getListIndexManager("e43cb122-72d6-11e4-b4ce-005056a05bc9","0");
			  //初始化结果
			  List<Object[]> listObject  =  indexManagerService.getOrderManager("e43cb122-72d6-11e4-b4ce-005056a05bc9", Content.CONTENTTYPE_REPLY, 5);
			  List<Map<String,IndexManager>>  listAdd  = new ArrayList<Map<String,IndexManager>>();
			  for (Object[] objects : listObject) {
				String []codes  = (String[]) objects[3].toString().split(",");
				String []keys  = (String[]) objects[1].toString().split(",");
				
				for (int i = 0; i < keys.length; i++) {
					String string = keys[i]; 
					Map<String,IndexManager>  mp   = new HashMap<String, IndexManager>();
				    for (IndexManager im    : list) {
						if (im.getNumber()!=null&&im.getNumber().equals(codes[i])) {
							mp.put(string, im);
							break;
						}
					}
				    listAdd.add(mp);
				}
			 } 
			 for (Map<String, IndexManager> map : listAdd) {
				 Set<String> keySet = map.keySet();
				  String key  = "";
				   IndexManager indexManager =  new IndexManager();
				  for (String string : keySet) {
					key   =  string;
					indexManager =  map.get(key);
				  }
				  Integer key1   = Integer.valueOf(key);
				  list.set(key1, indexManager);
			 }
			  
			  
		}
		//判断是否与有相同的赞数，如果存在就把想同的进行时间排序
		
		
		
		modelMap.addAttribute("list",list);
		return "manage/html/bbs/bestReply";
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
		/*
            List<IndexManager> list   =   null;
			//ContentType 
			 //帖子动态列表
			  indexManagerService.initIndexManagerbyPraise("e43cb122-72d6-11e4-b4ce-005056a05bc9", Content.CONTENTTYPE_REPLY, 5);
			  list   =  indexManagerService.getListIndexManager("e43cb122-72d6-11e4-b4ce-005056a05bc9",Content.CONTENTTYPE_REPLY);//indexManagerService.getListIndexManager("e43cb122-72d6-11e4-b4ce-005056a05bc9","0");
			  
			  //初始化结果
			  List<Object[]> listObject  =  indexManagerService.getOrderManager("e43cb122-72d6-11e4-b4ce-005056a05bc9", Content.CONTENTTYPE_REPLY, 5);
			  List<Map<String,IndexManager>>  listAdd  = new ArrayList<Map<String,IndexManager>>();
			  for (Object[] objects : listObject) {
				String []codes  = (String[]) objects[3].toString().split(",");
				String []keys  = (String[]) objects[1].toString().split(",");
				
				for (int i = 0; i < keys.length; i++) {
					String string = keys[i]; 
					Map<String,IndexManager>  mp   = new HashMap<String, IndexManager>();
				    for (IndexManager im    : list) {
						if (im.getNumber()!=null&&im.getNumber().equals(codes[i])) {
							mp.put(string, im);
							break;
						}
					}
				    listAdd.add(mp);
				}
			 } 
			 for (Map<String, IndexManager> map : listAdd) {
				 Set<String> keySet = map.keySet();
				  String key  = "";
				  IndexManager indexManager =  new IndexManager();
				  for (String string : keySet) {
					key   =  string;
					indexManager =  map.get(key);
				  }
				  Integer key1   = Integer.valueOf(key);
				  list.set(key1, indexManager);
			 }
		//判断是否与有相同的赞数，如果存在就把想同的进行时间排序
        */		
		List<IndexManager> list = indexManagerService.getMostPraise(5);
		for (IndexManager i : list) {
		    //清除富文本标签
            i.setReplycontent(StringUtil.cleanHTML(i.getReplycontent()));
        }
		modelMap.addAttribute("list",list);
		return "manage/html/bbs/bestReply";
		
	}
	
	
	/**
	 * 首页帖子动态页面管理列表
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 */
	@RequestMapping("save")
	public String   dynamicDataSave(String saveType,Integer  avaliable ,String codes[],String[]urls
			,String[]replyname,String[]praise,String[]replycontent,HttpServletRequest request,HttpServletResponse response,ModelMap modelMap){
		String proCode  = request.getParameter("proCode");
		modelMap.addAttribute("proCode", proCode);//前台页面保存一个proCode栏目code
		if (saveType!=null&&saveType.equals("preview")) {
		}else{
			this.indexManagerService.deleteProgram("e43cb122-72d6-11e4-b4ce-005056a05bc9",avaliable);
		}
		for (int i = 0; i < codes.length; i++) {
			String code = codes[i];
			String url  = urls[i];
			IndexManager indexManager  = new IndexManager();
			indexManager.setReplycontent(replycontent[i]);
			indexManager.setReplyname(replyname[i]);
			if (!StringUtils.isNotBlank(praise[i])&&StringUtils.isNotBlank(replyname[i])) {
				praise[i]=  "0";
			}
			indexManager.setPraise(praise[i]);
			
			indexManager.setCode(code);
			indexManager.setAvaliable(avaliable);
			indexManager.setUrl(url);
			//关联赞表更新
			String[] ref = url.split("code=");
			if(ref!=null&&ref.length>1){
			    Praise p =praiseService.getPraiseContentCode(ref[1]);
			    p.setFalsePraise(Integer.valueOf(praise[i]));
			    praiseService.update(p);
			}else{
				if ( url != null
						&&  url.lastIndexOf(".html") != -1) {
					String code1 =  url.substring(
							url.lastIndexOf("/") + 1, url.lastIndexOf(".html"));
					 Praise p =praiseService.getPraiseContentCode(code1);
					    p.setFalsePraise(Integer.valueOf(praise[i]));
					    praiseService.update(p);
				}
			}
			indexManager.setPramaCode("e43cb122-72d6-11e4-b4ce-005056a05bc9");
			this.indexManagerService.updateIndexManager(indexManager);
		}
		if (saveType!=null&&saveType.equals("preview")) {
			  return "redirect:/community/frontIndex?prew=0";
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
