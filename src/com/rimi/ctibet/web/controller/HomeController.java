
package com.rimi.ctibet.web.controller;

import java.io.File;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.rimi.ctibet.common.controller.BaseController;
import com.rimi.ctibet.common.util.FileHelper;
import com.rimi.ctibet.common.util.ImageZipUtil;
import com.rimi.ctibet.domain.Destination;
import com.rimi.ctibet.domain.Image;
import com.rimi.ctibet.domain.MutiImage;
import com.rimi.ctibet.domain.Programa;
import com.rimi.ctibet.domain.View;
import com.rimi.ctibet.web.service.DestinationService;
import com.rimi.ctibet.web.service.IProgramaService;
import com.rimi.ctibet.web.service.IViewService;
import com.rimi.ctibet.web.service.ImageService;
import com.rimi.ctibet.web.service.MutiImageService;
import com.sun.mail.handlers.image_gif;

/**
 * @author xiangwq
 * @date 2014/11/26
 * 
 */
@Controller
@RequestMapping("web/homeController")
public class HomeController extends BaseController {


	@Resource
	private IProgramaService programaService;
	@Resource
	private MutiImageService mutiService;
	@Resource
	private ImageService imageService;
	@Resource
	private DestinationService destinationService;
	@Resource
	private IViewService viewService;

	private static List<String> urlList = new ArrayList<String>();
	
	public    static List<List<Image>> previewImageList  = new ArrayList<List<Image>>();
	public static   List<Image> listImage  = new ArrayList<Image>();
	/**
	 * 通过栏目code获取图片集
	 * 
	 * @param programaCode
	 * @param model
	 * @return 返回一个图集列表 和一个 图片列表的列表
	 */
	@RequestMapping("getManageImg")
	public String getManageImg(String programaCode, ModelMap model) {
		List<MutiImage> mutiImageList = mutiService.getMutiImageByProgramaCode(programaCode);
		Programa programa = programaService.getProgramaByCode(programaCode);
		List<Destination> destinationList = destinationService.getAllDestination();
		String str = ""; // 跳转的路径
		if (programa.getCode().equals("e43cb722-75d6-11e4-b6ce-005056a05bc9")) {
			str = "manage/html/home/banner";  //首页bannerg管理
		}
		if (programa.getCode().equals("13173f79-75da-11e4-b6ce-005056a05bc9")) {
            str= "manage/html/home/posid"; //首页推荐位管理
		}
		if (programa.getCode().equals("1320eb90-75da-11e4-b6ce-005056a05bc9")) {
			str = "manage/html/home/scenicSpots";  //首页景点管理
			model.put("destinationList", destinationList);
		}
		if (programa.getCode().equals("1323f0e2-75da-11e4-b6ce-005056a05bc9")) {
            str= "manage/html/home/imgIntro";  //首页图说西藏管理
		}
		if (programa.getCode().equals("er5gh3hl32-75e6-11e4-byce-005a56a05bc9")) {
            str= "manage/html/ride/ridehome";  //骑行装备bannner管理
		}
		if (programa.getCode().equals("er5tyq3h632-75e6-11e4-byce-005ajya05bc9")) {
			str= "manage/html/ride/ridepro";  //骑行首页bannner管理
		}
		if (programa.getCode().equals("132a2285-75da-11e4-b6ce-005056a05bc9")) {
			str = "manage/html/home/readCtibet";  //首页读西藏管理
		}
		if ("13334a3a-75da-11e4-b6ce-005056a05bc9".equals(programa.getCode())) {
			 str = "manage/html/home/floatLayer";   //首页浮窗管理
		}
		List<List<Image>> imageList = new ArrayList<List<Image>>();
		for (MutiImage muti : mutiImageList) {
			imageList.add(imageService.getImageByMutiImageCode(muti.getCode()));

		}
		if (programa.getCode().equals("1320eb90-75da-11e4-b6ce-005056a05bc9")) {
			
			
			
		}
		model.put("programaCode", programaCode);
		model.put("mutiList", mutiImageList);
		model.put("imageList", imageList);
		return str;
	}

	/**
	 * 把预览图片添加到全局变量
	 */
	@RequestMapping("previewManageImg")
	public @ResponseBody String  previewManageImg(Image image) {
		//System.out.println("imageName------------>"+image.toString());
		listImage.add(image);
		return "";
	}
	/**
	 * 跳转到指定的预览页面
	 */
	@RequestMapping("previewManageFront")
	public String previewManageFront(HttpServletRequest request,
			HttpServletResponse response,Image img) {
		if(listImage.size()==0){
			//return "false";
			listImage.add(img);
		} 
	
		String Path = request.getSession().getServletContext().getContextPath();
		//System.out.println("path----->"+Path);
		Image image = listImage.get(0);
		String mutiCode = image.getMutiCode();
		//System.out.println("programaCode----------------->"+mutiCode);
		String url = "";
		if("77ffa15c-0192-4ded-91f5-9ac29a651ee4".equals(mutiCode)){
			url = "redirect:/web/frontIndexController/indexbanner";  //跳转到banner预览页
		}
		if("d321de41-cd1e-4576-ba3a-4d1183ff714b".equals(mutiCode)){
			url = "redirect:/web/frontIndexController/indexRecommedone"; //跳转到推荐位一预览页面
		}
		if("4debe6ec-dbbf-45ee-86a4-fbeb3d1b28af".equals(mutiCode)){
			
			url = "redirect:/web/frontIndexController/indexRecommedtwo";  //跳转到推荐位二
		}
		if("32713644-32f0-4486-8b4b-048e43031035".equals(mutiCode)){
			url = "redirect:/web/frontIndexController/indexRecommedthree";  //跳转到推荐位三预览页面
		}
		if("fe33dccc-415f-424a-9553-775647c86443".equals(mutiCode)){
			//游西藏景点查询数据
			for (Image img1 : HomeController.listImage) {
				View view = viewService.findByCode(img1.getViewCode());
				if (view!=null) {
					 img1.setHyperlink(view.getLinkUrl());
					 img1.setName(view.getViewName());
					 img1.setGoCount(view.getFakeGoneCount());
					 img1.setWantCount(view.getFakeWantCount());
				}
			}
			url = "redirect:/web/frontIndexController/indexview";   //跳转到景点预览页面
		}
		if("789g65c5-99d8-4a37-9d13-414b80qe36g6".equals(mutiCode)){
			url = "redirect:/web/frontIndexController/indextsxz";   //跳转到图说西藏预览页面
		}
		if("648fw15c-0192-4ded-91f5-9ac29a651ee4".equals(mutiCode)){
			
			url = "redirect:/web/frontIndexController/indexwindow";//跳转到浮窗预览页面
		}
		if("0f533b63-8397-4847-a225-2d9e1352901e".equals(mutiCode)){
			url = "redirect:/web/frontIndexController/indexread"; //读西藏
		}
		if("64e186ad-2dae-4b43-94cb-27b872bc3ecd".equals(mutiCode)){
			url = "redirect:/web/frontIndexController/indexcustom";//风俗
		}
		if("d4b4d070-3c54-41f7-95bb-cd11f0c973ab".equals(mutiCode)){
			url = "redirect:/web/frontIndexController/indexculture"; // 文化
		}
		if("7eb1978f-07d7-4a3e-a74b-ae44a584cb85".equals(mutiCode)){
			url = "redirect:/web/frontIndexController/indexreligion"; //宗教
		}
		if("df543ag3-a397-a84w-aa25-gd9e2352901e".equals(mutiCode)){
			url = "redirect:/web/frontIndexController/indexother"; //其他
		}
		//response.reset();
		//response.resetBuffer();
		//response;
		return  url;
	}
	
	
	/**
	 * 上传图片
	 */
	@RequestMapping("uploadImage")
	public @ResponseBody String uploadImage(HttpServletRequest request,
			HttpServletResponse response, ModelMap model) {
		// ***********************获取上传图片的本地路径**********************
		List<MultipartFile> images = FileHelper.getFileSet(request,
				1024 * 1024, null, "file");
		Timestamp tsp = new Timestamp(new Date().getTime());
		String path = request.getSession().getServletContext().getRealPath("/");
		//System.out.println("path----------------------->"+path);
		List<String> listPath = new ArrayList<String>();
		// ***********************返回上传至服务器的路径**********************
		listPath = imageService.uploadImg(images,path,tsp); //上传图片获取路径（返回本地路径）
		if (listPath.size() > 0) {
			urlList.clear();
			for (String url : listPath) {
				//System.out.println("图集：---------------------->" + url);
				urlList.add(url);
			}
			return listPath.get(0);
		}
       return "false";
	}
	
	
	@RequestMapping("toHome")
	public String toHome(){
		return "manage/html/home/home";
	}

	/**
	 * 保存图片
	 * @throws IOException 
	 */
	@RequestMapping("saveManageImg")
	public @ResponseBody String saveManageImg(HttpServletRequest request,
			HttpServletResponse response,Image image) throws IOException {
		String name= request.getParameter("name");
		String summary = request.getParameter("summary");
		String hyperlink = request.getParameter("hyperlink");		
		String viewCode = request.getParameter("viewCode");
		String destinationCode = request.getParameter("destinationCode");
		if(StringUtils.isNotBlank(viewCode)){
			View view = viewService.findByCode(viewCode);
			if (view!=null) {
				int wantCount = view.getWantCount();
				int goCount = view.getGoneCount();
				image.setWantCount(wantCount);
				image.setGoCount(goCount);
				image.setName(view.getViewName());//景点名称
				image.setHyperlink(view.getLinkUrl()); //超链接内容待定
			}
			Destination destination =  destinationService.findByCode(destinationCode);
			if (destination!=null) {
				image.setSummary(destination.getDestinationName());//目的地名称
			}
			image.setDestinationCode(destinationCode);
			image.setViewCode(viewCode);
		}	
		image.setIsshow(image.getIsshow());
		String tsxz = request.getParameter("tsxzindex");
		//图说西藏 缩略图管理
		//获取  oldFile 图片
		if (StringUtils.isNotBlank(tsxz)&&tsxz.equals("tsxzindex")) {
			//System.out.println("imageName------------>"+image.getName());
			File oldFile  = new  File(getUrlFileImg(image.getUrl(),request));
			String fileName1 = oldFile.getName();
		    String  prexis = fileName1.substring(fileName1.lastIndexOf(".")+1);
			Timestamp tsp = new Timestamp(new Date().getTime());
			int x=(int)(Math.random()*100);
			String s =  "/upload" + "/img/home/" + tsp.getTime()+""+"/"+x+"."+prexis;//s 表示访问路劲
			String imgPath = request.getSession().getServletContext().getRealPath("")+ s;  //存入图片的路径
			File newFile  =  new File(imgPath);
			File filedir = new File(request.getSession().getServletContext().getRealPath("")+"/upload" + "/img/home/" + tsp.getTime());
			if (!filedir.exists()) {
				 filedir.mkdirs();
				if (!newFile.exists()) {
					newFile.createNewFile();
				}
			}
			//图片压缩
			ImageZipUtil.zipImageFile(oldFile, newFile, 103, 85, 1f);
			image.setSmimg(s);
		}
		imageService.saveImage(image);
		return "";
	}
	

	/**
	 * 获取url  图片路径
	 * @param url
	 * @param request
	 * @return
	 */
	public String getUrlFileImg(String url,HttpServletRequest request){
		String  tomcatPath =  request.getSession().getServletContext().getRealPath("");
		
		String imgPath =  "";
		if (StringUtils.isNotBlank(url)&&url.indexOf("upload")!=-1) {
			   String urls[]= url.split("upload");
			   imgPath = tomcatPath+"/upload"+urls[1];
		} 
		return  imgPath;
	}

	@RequestMapping("getUrl")
	@ResponseBody
	public void getUrl(HttpServletResponse res) throws IOException {
		//System.out.println("进入ge");
		if (urlList!=null) {
			res.getWriter().print(urlList.get(urlList.size()-1));		
			
		}
	}
	public static void main(String[] args) {
	      listImage.add(new Image());
	      List<Image>  list  =  listImage;
	}
	
	@RequestMapping("cycle")
	public String cycle(){
		return "manage/html/other/building";
	}
}
