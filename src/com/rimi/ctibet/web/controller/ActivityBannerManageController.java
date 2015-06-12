package com.rimi.ctibet.web.controller;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.annotation.Resource;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.ServletContextAware;
import org.springframework.web.multipart.MultipartFile;

import com.rimi.ctibet.common.controller.BaseController;
import com.rimi.ctibet.common.util.FileOperate;
import com.rimi.ctibet.common.util.FileUtil;
import com.rimi.ctibet.common.util.ReadSettingProperties;
import com.rimi.ctibet.domain.Activity;
import com.rimi.ctibet.domain.Image;
import com.rimi.ctibet.web.controller.vo.MemberEnrollDetailVO;
import com.rimi.ctibet.web.controller.vo.MutiImageVO;
import com.rimi.ctibet.web.service.IActivityService;
import com.rimi.ctibet.web.service.IMemberEnrollDetailService;
import com.rimi.ctibet.web.service.ImageService;

@Controller
@RequestMapping("web/activityBannerManageController")
public class ActivityBannerManageController extends BaseController implements ServletContextAware  {
	
	public static  Logger LOGGER = Logger.getLogger(ActivityBannerManageController.class);
	
	ServletContext application;
	
	@Resource IActivityService activityService;
	@Resource ImageService imageService;
	@Resource IMemberEnrollDetailService memberEnrollDetailService;
	
	/** 预览类型 活动banner预览 */
	public static final String PREVIEW_TYPE_BANNER = "banner";
	/** 预览类型 专题显示预览  */
	public static final String PREVIEW_TYPE_SPECAIL = "specail";
	
	final String tempPath = "/temp/";
	
	/**
	 * 活动&专题首页显示
	 * web/activityBannerManageController/forActivityManage
	 */
	@RequestMapping("forActivityManage")
	public String forActivityManage(){
		return "manage/html/activity/activity";
	}
	
	/**
	 * 跳转到活动banner管理页面
	 * web/activityBannerManageController/forActivityBannerManage
	 */
	@RequestMapping("forActivityBannerManage")
	public String forActivityBannerManage(Model model){
		String mutiCode = Activity.MUTI_ACTIVITY_BANNER_CODE;
		List<Image> listImage = imageService.getImageByMutiCodeImageNum(mutiCode, 6, "图片", "活动banner图");
		model.addAttribute("listImage", listImage);
		model.addAttribute("previewType", PREVIEW_TYPE_BANNER);
		return "manage/html/activity/banner";
	}
	/**
	 * 修改活动banner
	 * web/activityBannerManageController/editActivityBanner
	 */
	@RequestMapping("editActivityBanner")
	public String editActivityBanner(MutiImageVO mutiImageVO){
		String basePath = application.getRealPath("/");
		for (Image img : mutiImageVO.getListImage()) {
			String newPath = img.getUrl().replace(tempPath, "");
			FileUtil.moveFile(basePath + img.getUrl(), basePath+newPath);
			img.setUrl(newPath);
		}
		imageService.updateImageList(mutiImageVO.getListImage());
		return redirect("forActivityBannerManage");
	}
	
	/**
	 * 跳转到专题显示管理页面
	 * web/activityBannerManageController/forSpecailShowManage
	 */
	@RequestMapping("forSpecailShowManage")
	public String forSpecailShowManage(Model model){
		String mutiCode = Activity.MUTI_SPECAIL_SHOW_CODE;
		List<Image> listImage = imageService.getImageByMutiCodeImageNum(mutiCode, 5, "图片", "专题显示图");
		model.addAttribute("listImage", listImage);
		model.addAttribute("previewType", PREVIEW_TYPE_SPECAIL);
		return "manage/html/activity/specialShow";
	}
	/**
	 * 修改专题显示
	 * web/activityBannerManageController/editSpecailShow
	 */
	@RequestMapping("editSpecailShow")
	public String editSpecailShow(MutiImageVO mutiImageVO){
		String basePath = application.getRealPath("/");
		for (Image img : mutiImageVO.getListImage()) {
			String newPath = img.getUrl().replace(tempPath, "");
			FileUtil.moveFile(basePath + img.getUrl(), basePath+newPath);
			img.setUrl(newPath);
		}
		imageService.updateImageList(mutiImageVO.getListImage());
		return redirect("forSpecailShowManage");
	}
	
	
	/**
	 * 预览 活动首页
	 * web/activityBannerManageController/forPreView
	 */
	@RequestMapping("forPreView")
	public String forPreView(Model model, MutiImageVO mutiImageVO, String previewType){
		List<Image> listBannerImage = null;
		List<Image> listSpecailShowImage = null;
		//最近参与
		//List<MemberEnrollDetailVO> listMemberEnrollDetailVO = memberEnrollDetailService.getNewEnrollMemberByActivityCodeRow(null, 7, true);
		List<MemberEnrollDetailVO> listMemberEnrollDetailVO = activityService.getJoinMemberList(7);
		
		if(previewType.equals(PREVIEW_TYPE_BANNER)){
			listBannerImage = mutiImageVO.getListImage();
			listSpecailShowImage = imageService.getImageByMutiCode(Activity.MUTI_SPECAIL_SHOW_CODE);
		}else if(previewType.equals(PREVIEW_TYPE_SPECAIL)){
			listBannerImage = imageService.getImageByMutiCode(Activity.MUTI_ACTIVITY_BANNER_CODE);
			listSpecailShowImage = mutiImageVO.getListImage();
		}
		
		model.addAttribute("listBannerImage", listBannerImage);
		model.addAttribute("listSpecailShowImage", listSpecailShowImage);
		model.addAttribute("listMemberEnrollDetailVO", listMemberEnrollDetailVO);
		model.addAttribute("programNam", 5);
		return "portal/app/activity/index";
	}
	
	/********************************************
	 * other method
	 ********************************************/
	
	/**
	 * banner管理 专题显示管理 上传图片
	 */
	@RequestMapping(value="fileUpload", produces = "text/html; charset=utf-8")
	@ResponseBody
	public String uploadFile(String activityCode, String floderName, @RequestParam MultipartFile file, HttpSession session){
	    ////System.out.println(session.getId());
		Map<String, String> msgMap = new HashMap<String, String>();
		if(floderName==null || floderName.equals("")){
			LOGGER.info("需要传一个文件夹名字。");
		}
		if(file!=null){
			String basePath = application.getRealPath("/");
			String path = ReadSettingProperties.getValue("upload") + "/img/activity/" + tempPath + floderName + "/";
				MultipartFile multipartFile = file;
				String time = new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date(System.currentTimeMillis()));
				time  += new DecimalFormat("0000").format(new Random().nextInt(10000));
				String ext = FileOperate.getFileExt(multipartFile.getOriginalFilename(), true);
				String fileName = time + ext;
				try {
					FileUtil.uploadFile(multipartFile, basePath + path, fileName);
					msgMap.put("filePath", path + fileName);
	                msgMap.put("fileName", file.getOriginalFilename());
				} catch (Exception e) {
					e.printStackTrace();
					msgMap.put("msg", multipartFile.getOriginalFilename() + "上传失败！");
				}
		}
		return obj2json(msgMap);
	}
	
	/********************************************
	 * Setter Getter
	 ********************************************/
	@Override
	public void setServletContext(ServletContext arg0) {
		this.application = arg0;
	}
	
}
