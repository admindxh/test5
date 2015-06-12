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
import com.rimi.ctibet.web.service.IProgramaContentService;

/**
 * 首页游西藏攻略管理列表 controller
 * @author dengxh
 *
 */
@Controller
@RequestMapping("web/indexstrategydatamgController")
public class IndexstrategyataMgController extends BaseController{
	

	@Resource
	private IContentService contentServiceImpl;
	
	@Resource
	private  IProgramaContentService programaContentServiceIml;
	
	
	
	
	/**
	 * 通过栏目code 查询游西藏攻略管理列表栏目数据
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
		return   "manage/html/other/首页游西藏攻略管理列表管理list";
	}
	/**
	 * 游西藏攻略管理列表添加页面
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 */
	@RequestMapping("saveUI")
	public String   dynamicDataAddUI(HttpServletRequest request,HttpServletResponse response,ModelMap modelMap){
		String proCode  = request.getParameter("proCode");
		modelMap.addAttribute("proCode", proCode);//前台页面保存一个proCode栏目code
		return "manage/html/other/首页游西藏攻略管理列表管理addUI";
	}
	
	/**
	 * 游西藏攻略管理列表保存
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 */
	@RequestMapping("save")
	public String   dynamicDataSave(Pager pager,Content content,String []vies,String[]des,HttpServletRequest request,HttpServletResponse response,ModelMap modelMap){
		String proCode  = request.getParameter("proCode");
		content.setContentType(Content.CONTENTTYPE_STRATEGY);//内容内型
		contentServiceImpl.saveStrategy(content, proCode, vies, des);
		
		//保存成功后调用添加lucene 等待xiaozhen提供
		LuceneUtil.addDocuemnt(content, "");//url 待提供路径
		
		request.setAttribute("proCode", proCode);
		return   this.dynamicDataListUI(pager, request,response, modelMap);
	}
	
	
	
	/**
	 * 游西藏攻略管理列表修改页面
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 */
	@RequestMapping("updateUI")
	public String   dynamicDataUpdateUI(String contentCode,HttpServletRequest request,HttpServletResponse response,ModelMap modelMap){
		Content content   = contentServiceImpl.findByCode(contentCode);
		request.setAttribute("content", content);
		return "manage/html/other/首页游西藏攻略管理列表管理updateUI";
	}
	
	/**
	 * 游西藏攻略管理列表修改
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 */
	@RequestMapping("update")
	public String   dynamicDataUpdate(Pager pager,Content content,String []vies,String[]des,HttpServletRequest request,HttpServletResponse response,ModelMap modelMap){
		String proCode  = request.getParameter("proCode");
		contentServiceImpl.updateStrategy(content, vies, des, request.getContextPath());
		LuceneUtil.updateDocument(content, "");//待提供路径url内容
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
		contentServiceImpl.deleteStrategyByCode(contentcodes);
		//批量删除内容 和 中间表
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
		String proCode  = request.getParameter("proCode");
		//String 
		programaContentServiceIml.deleteProgramaContentByProCodeConCode(proCode, contentCode);
		request.setAttribute("proCode", proCode);
		return   this.dynamicDataListUI(pager, request, response, modelMap);
	}
	
	
	
	
	
	public IProgramaContentService getProgramaContentServiceIml() {
		return programaContentServiceIml;
	}
	public void setProgramaContentServiceIml(
			IProgramaContentService programaContentServiceIml) {
		this.programaContentServiceIml = programaContentServiceIml;
	}
	public IContentService getContentServiceImpl() {
		return contentServiceImpl;
	}

	public void setContentServiceImpl(IContentService contentServiceImpl) {
		this.contentServiceImpl = contentServiceImpl;
	}

}
