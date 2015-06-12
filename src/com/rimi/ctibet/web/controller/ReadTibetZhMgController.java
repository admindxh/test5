package com.rimi.ctibet.web.controller;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.rimi.ctibet.common.constants.Constants;
import com.rimi.ctibet.common.controller.BaseController;
import com.rimi.ctibet.common.util.LuceneUtil;
import com.rimi.ctibet.common.util.Pager;
import com.rimi.ctibet.domain.Content;
import com.rimi.ctibet.domain.Image;
import com.rimi.ctibet.domain.MutiImage;
import com.rimi.ctibet.web.service.DestinationService;
import com.rimi.ctibet.web.service.IContentService;
import com.rimi.ctibet.web.service.IProgramaContentService;
import com.rimi.ctibet.web.service.IProgramaService;
import com.rimi.ctibet.web.service.ImageService;
import com.rimi.ctibet.web.service.MutiImageService;

/**
 *  游西藏综合攻略管理列表 controller
 * @author dengxh
 *
 */
@Controller
@RequestMapping("web/readTibetZhMgController")
public class ReadTibetZhMgController extends BaseController{

	@Resource
	private IProgramaService programaService;
	@Resource
	private MutiImageService mutiService;
	@Resource
	private ImageService imageService;
	@Resource
	private DestinationService destinationService;

	@Resource
	private IContentService contentServiceImpl;
	
	@Resource
	private  IProgramaContentService programaContentServiceIml;
	
	private   static List<List<Image>> previewImageList  = new ArrayList<List<Image>>();
	
	private  static List<Image> listImage  = new ArrayList<Image>();
	
	
	/**
	 * 通过栏目code 查询游西藏综合攻略管理列表栏目数据
	 * @param programCode 栏目code 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 */
	@RequestMapping("list")
	public String   dynamicDataListUI(Pager pager,HttpServletRequest request,HttpServletResponse response,ModelMap modelMap){
		String viewCode = request.getParameter("viewCode"); 		
		listImage  =   new ArrayList<Image>();
		//调用查询方法  获取分页对象
		String programCode  = "1b808620-79bf-11e4-b6ce-005056a05bc9";
		List<MutiImage> mutiImageList = mutiService.getMutiImageByProgramaCode(programCode);
		List<List<Image>> imageList = new ArrayList<List<Image>>();
		for (MutiImage muti : mutiImageList) {
			imageList.add(imageService.getImageByMutiImageCode(muti.getCode()));
		}
		modelMap.put("mutiList", mutiImageList);
		modelMap.put("imageList", imageList);
		previewImageList  = new ArrayList<List<Image>>();
		listImage  = new ArrayList<Image>();
		return   "manage//html//travel//synthStrategy";
	}
	
	
	/**
	 * 保存图片
	 */
	@RequestMapping("saveManageImg")
	public @ResponseBody String saveManageImg(Image image) {
		//System.out.println("imageName------------>"+image.getName());
		imageService.saveImage(image);
		previewImageList  = new ArrayList<List<Image>>();
		listImage  = new ArrayList<Image>();
		return "";
	}
	
	/**
	 * 预览图片
	 */
	@RequestMapping("previewManageImg")
	public @ResponseBody String  previewManageImg(Image image) {
		//System.out.println("imageName------------>"+image.getName());
		//imageService.saveImage(image);
		listImage.add(image);
		return "redirect:/tourism/strage/fontIndexZhPage";
	}
	
	/**
	 * 预览跳转 前端
	 */
	@SuppressWarnings("static-access")
	@RequestMapping("previewManageFront")
	public String    previewManageFront(Image image) {
		//System.out.println("imageName------------>"+image.getName());
		//imageService.saveImage(image);
		if (previewImageList==null||previewImageList.size()<=1) {
			 previewImageList =  new ArrayList<List<Image>>();
		}
		this.previewImageList.add(listImage);
		return "redirect:/tourism/strage/fontIndexZhPage";
	}
	
	
	/**
	 * 游西藏综合攻略管理列表添加页面
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 */
	@RequestMapping("saveUI")
	public String   dynamicDataAddUI(HttpServletRequest request,HttpServletResponse response,ModelMap modelMap){
		String proCode  = request.getParameter("proCode");
		modelMap.addAttribute("proCode", proCode);//前台页面保存一个proCode栏目code
		return "manage/html/other/首页游西藏综合攻略管理列表管理addUI";
	}
	
	/**
	 * 游西藏综合攻略管理列表保存
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 */
	@RequestMapping("save")
	public String   dynamicDataSave(Pager pager,Content content,String []vies,String[]des,HttpServletRequest request,HttpServletResponse response,ModelMap modelMap){
		String proCode  = request.getParameter("proCode");
		content.setContentType(Content.CONTENTTYPE_STRATEGY);//内容内型
		content.setState(Constants.STATUS_CHECK);
		contentServiceImpl.saveStrategy(content, proCode, vies, des);
		
		//保存成功后调用添加lucene 等待xiaozhen提供
		LuceneUtil.addDocuemnt(content, "");//url 待提供路径
		
		request.setAttribute("proCode", proCode);
		return   this.dynamicDataListUI(pager, request,response, modelMap);
	}
	
	
	
	/**
	 * 游西藏综合攻略管理列表修改页面
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 */
	@RequestMapping("updateUI")
	public String   dynamicDataUpdateUI(String contentCode,HttpServletRequest request,HttpServletResponse response,ModelMap modelMap){
		Content content   = contentServiceImpl.findByCode(contentCode);
		request.setAttribute("content", content);
		return "manage/html/other/首页游西藏综合攻略管理列表管理updateUI";
	}
	
	/**
	 * 游西藏综合攻略管理列表修改
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
	
	
	
	public IProgramaService getProgramaService() {
		return programaService;
	}
	public void setProgramaService(IProgramaService programaService) {
		this.programaService = programaService;
	}
	public MutiImageService getMutiService() {
		return mutiService;
	}
	public void setMutiService(MutiImageService mutiService) {
		this.mutiService = mutiService;
	}
	public ImageService getImageService() {
		return imageService;
	}
	public void setImageService(ImageService imageService) {
		this.imageService = imageService;
	}
	public DestinationService getDestinationService() {
		return destinationService;
	}
	public void setDestinationService(DestinationService destinationService) {
		this.destinationService = destinationService;
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


	public static List<List<Image>> getPreviewImageList() {
		return previewImageList;
	}


	public static void setPreviewImageList(List<List<Image>> previewImageList) {
		ReadTibetZhMgController.previewImageList = previewImageList;
	}


	public static List<Image> getListImage() {
		return listImage;
	}


	public static void setListImage(List<Image> listImage) {
		ReadTibetZhMgController.listImage = listImage;
	}


	
	
	
	
}
