package com.rimi.ctibet.web.controller;

import java.io.File;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.rimi.ctibet.common.util.FileHelper;
import com.rimi.ctibet.common.util.Pager;
import com.rimi.ctibet.common.util.ReadSettingProperties;
import com.rimi.ctibet.common.util.Uuid;
import com.rimi.ctibet.domain.Programa;
import com.rimi.ctibet.web.service.IPlateService;

/**板块管理controller
 * @author xiaozhen
 */
@Controller
@RequestMapping("web/plate")
public class PlateController {

	@Resource
	private IPlateService plateService;
	
	
	
	//后台修改板块列表
	@RequestMapping("plateList")
	public ModelAndView plateList(Pager pager,HttpServletRequest request,HttpServletResponse response){
		ModelAndView view = new ModelAndView("");
	    Pager pager1 = plateService.plateList(pager);
	    String topNum = plateService.postCountByIsTop();
	    view.addObject("pager", pager1).addObject("topNum", topNum);
	    return view;
	}
	
	//删除版块
	@RequestMapping("deletePlate")
	public @ResponseBody String deletePlate(HttpServletRequest request,HttpServletResponse response) {
		String code = request.getParameter("code");
		if(StringUtils.isBlank(code))
			return "缺少必要参数";
		Programa programa = plateService.findByCode(code);
		plateService.deletePlate(programa);
		return "删除成功";
	}
    
	@RequestMapping("findByCode")
	public ModelAndView findByCode(HttpServletRequest request,HttpServletResponse response) {
		//路径暂缺
		ModelAndView view = new ModelAndView("");
		String code = request.getParameter("code");
		if(StringUtils.isBlank(code))
			return view.addObject("result", "缺少必要参数");
		Programa programa = plateService.findByCode(code);
		return view.addObject("result",programa);
	}
     
	@RequestMapping("getContentByProgramaCode")
	public ModelAndView getContentByProgramaCode(HttpServletRequest request,HttpServletResponse response) {
		//路径暂缺
		ModelAndView view = new ModelAndView("");
		String code = request.getParameter("code");
		if(StringUtils.isBlank(code))
			return view.addObject("result", "缺少必要参数");
		return view.addObject("result",plateService.getContentByProgramaCode(code));
	}

	@RequestMapping("getSendPrograma")
	public ModelAndView getSendPrograma(HttpServletRequest request,HttpServletResponse response) {
		//路径暂缺
		ModelAndView view = new ModelAndView("");
		String code = request.getParameter("code");
		if(StringUtils.isBlank(code))
			return view.addObject("result", "缺少必要参数");
		return view.addObject("result",plateService.getSendPrograma(code));
	}

	@RequestMapping("getTopPlate")
	public @ResponseBody Pager getTopPlate(Pager pager,HttpServletRequest request,HttpServletResponse response) {
		pager.setDataList(plateService.getTopPlate());
		return pager;
	}
    @RequestMapping("savePlate")
	public @ResponseBody String saveOrUpdatPlate(HttpServletRequest request,HttpServletResponse response,@ModelAttribute Programa plate) throws Exception {
		// 获取上传图片集合
		List<MultipartFile> plateImgs = FileHelper.getFileSet(request,
				1024 * 1024 * 20, null, "plateImgs");
		String message = "";
		String path = request.getSession().getServletContext().getRealPath("/")
				+ "/WebRoot" + ReadSettingProperties.getValue("upload")
				+ "/img" + "/plateImg" + "/";
		if (StringUtils.isBlank(plate.getProgramaName())) {
			message = "必要参数为空";
			return message;
		}
    	//=============================修改板块=============================
    	if(StringUtils.isNotBlank(plate.getCode())){
    		Programa plate2 = plateService.findByCode(plate
					.getCode());
    		plate.setId(plate2.getId());
			// 将老图片全部删除
			if (StringUtils.isNotBlank(plate2.getImageUrl())) {
				String[] oldImgs = plate2.getImageUrl().split(",");
				FileHelper.deleteFile(oldImgs, path);
			}
			// 将新图片全部保存
			plate.setImageUrl("");
			for (MultipartFile plateImg : plateImgs) {
				String imgPath = path + plate.getCode();
				FileHelper.uploadFile(plateImg, imgPath);
				plate.setImageUrl(plate.getImageUrl()
						+ plate.getCode() + "/"
						+ plateImg.getOriginalFilename() + ",");
			}
			plate.setLastEditTime(new Date());
			plateService.updatePlate(plate);
			message = "修改成功";
			return message;
        }
    	//=================================保存板块=======================================
    	String plateCode = Uuid.uuid();
    	plate.setImageUrl("");
		// 判断是否存在文件夹
		String imgPath = path + plateCode;
		File file = new File(imgPath);
		if (!file.exists()) {
			file.mkdirs();
		}
		for (MultipartFile plateImg : plateImgs) {
			FileHelper.uploadFile(plateImg, imgPath);
			plate.setImageUrl(plate.getImageUrl() + plateCode + "/"
					+ plateImg.getOriginalFilename() + ",");
		}
		plate.setCode(plateCode);
		if (StringUtils.isNotBlank(plate.getImageUrl()))
			plate.setCreateTime(new Date());
		plateService.savePlate(plate);
		message = "保存成功";
		return message;
    }
}
