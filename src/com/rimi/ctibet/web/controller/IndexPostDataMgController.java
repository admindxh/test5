package com.rimi.ctibet.web.controller;

import javax.annotation.Resource;
import javax.annotation.Resources;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.portlet.ModelAndView;

import com.rimi.ctibet.common.controller.BaseController;
import com.rimi.ctibet.common.util.LuceneUtil;
import com.rimi.ctibet.common.util.Pager;
import com.rimi.ctibet.domain.Content;
import com.rimi.ctibet.web.service.IContentService;

/**
 * 首页帖子列表管理列表 controller
 * @author dengxh
 *
 */
@Controller
@RequestMapping("web/indexpostdatamgController")
public class IndexPostDataMgController extends BaseController{
	

	@Resource
	private IContentService contentServiceImpl;
	

	
	
	
	
	/**
	 * 通过栏目code 查询帖子列表列表栏目数据
	 * @param programCode 栏目code 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 */
	@RequestMapping("list")
	public String   dynamicDataListUI(Pager pager,HttpServletRequest request,HttpServletResponse response,ModelMap modelMap){
		String viewCode = request.getParameter("viewCode"); 				
		//调用查询方法  获取分页对象
		pager.setPageSize(1);
		String proCode  = request.getParameter("proCode");
		pager = contentServiceImpl.findContentByProCode(pager, proCode);
		modelMap.addAttribute("pager", pager);
		return   "manage/html/other/首页帖子列表列表管理list";
	}
	/**
	 * 帖子列表列表添加页面
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 */
	@RequestMapping("saveUI")
	public String   dynamicDataAddUI(HttpServletRequest request,HttpServletResponse response,ModelMap modelMap){
		String proCode  = request.getParameter("proCode");
		modelMap.addAttribute("proCode", proCode);//前台页面保存一个proCode栏目code
		return "manage/html/other/首页帖子列表列表管理addUI";
	}
	
	/**
	 * 帖子列表列表保存
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 */
	@RequestMapping("save")
	public String   dynamicDataSave(Pager pager,Content content,HttpServletRequest request,HttpServletResponse response,ModelMap modelMap){
		String proCode  = request.getParameter("proCode");
		
		
		content.setContentType(Content.CONTENTTYPE_POST);//帖子内型
		contentServiceImpl.saveContentAndProgramaContent(content, proCode);
		//保存成功后调用添加lucene 等待xiaozhen提供
		LuceneUtil.addDocuemnt(content, "");//待提供路径内容
		
		
		
		
		
		
		
		
		
		
		
		
		request.setAttribute("proCode", proCode);
		return   this.dynamicDataListUI(pager, request,response, modelMap);
	}
	
	
	
	/**
	 * 帖子列表列表修改页面
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 */
	@RequestMapping("updateUI")
	public String   dynamicDataUpdateUI(String contentCode,HttpServletRequest request,HttpServletResponse response,ModelMap modelMap){
		Content content   = contentServiceImpl.findByCode(contentCode);
		
		
		
		request.setAttribute("content", content);
		
		
		return "manage/html/other/首页帖子列表列表管理updateUI";
	}
	
	/**
	 * 帖子列表列表修改
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 */
	@RequestMapping("update")
	public String   dynamicDataUpdate(Pager pager,Content content,HttpServletRequest request,HttpServletResponse response,ModelMap modelMap){
		contentServiceImpl.updateLogical(content);//保存新数据，删除旧数据，内容code 不变
		String proCode  = request.getParameter("proCode");
		LuceneUtil.updateDocument(content, "");//路径待定url,更新内容
		request.setAttribute("proCode", proCode);		
		return   this.dynamicDataListUI(pager, request,response, modelMap);
	}
	
	
	/**
	 * 删除内容
	 * @param pager
	 * @param contentCode  分割 ,
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 */
	@RequestMapping("delete")
	public String dynamicDataDelete(Pager pager,String contentCode,HttpServletRequest request,HttpServletResponse response,ModelMap modelMap){
		
		String [] contentcodes   =  null;
		
		if (contentCode!=null) {
			contentcodes  = contentCode.split(",");
		}
		//批量删除内容 和 中间表
		contentServiceImpl.deleteContentAndProgramaContent(contentcodes);
		
		String proCode  = request.getParameter("proCode");
		
		//删除lucene,定义成 ,分割 contentCode 
		LuceneUtil.deleteDocument(contentCode);
		
		request.setAttribute("proCode", proCode);
		return   this.dynamicDataListUI(pager, request, response, modelMap);
	}
	
	
	/**
	 * 取消首页显示  根据 内容code
	 * @param pager
	 * @param contentCode  内容code 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 */
	@RequestMapping("cancel")
	public String dynamicDataCancel(Pager pager,String contentCode,HttpServletRequest request,HttpServletResponse response,ModelMap modelMap){
		
		Content content  = new Content();
		content.setCode(contentCode);
		contentServiceImpl.deleteContentAndProgramaContent(content);
		String proCode  = request.getParameter("proCode");
		request.setAttribute("proCode", proCode);
		return   this.dynamicDataListUI(pager, request, response, modelMap);
	}
	
	
	
	
	
	public IContentService getContentServiceImpl() {
		return contentServiceImpl;
	}

	public void setContentServiceImpl(IContentService contentServiceImpl) {
		this.contentServiceImpl = contentServiceImpl;
	}

}
