package com.rimi.ctibet.web.controller;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.rimi.ctibet.common.controller.BaseController;
import com.rimi.ctibet.common.exception.JsonParseException;
import com.rimi.ctibet.common.util.FileHelper;
import com.rimi.ctibet.common.util.JsonUtil;
import com.rimi.ctibet.domain.Image;
import com.rimi.ctibet.domain.MutiImage;
import com.rimi.ctibet.web.service.ImageService;
import com.rimi.ctibet.web.service.MutiImageService;
@Controller
@RequestMapping("web/imageController")
public class ImageController extends BaseController {
	
	@Resource
	private ImageService imageService;
	@Resource
	private MutiImageService mutiService;
	
	private static List<String> UrlList = new ArrayList<String>();
	
	
	/**
	 * 上传图片
	 */
	@RequestMapping("uploadImage")
	@ResponseBody
	public String  uploadImage(HttpServletRequest request,
			HttpServletResponse response, ModelMap model) {
		// ***********************获取上传图片的本地路径**********************
		List<MultipartFile> images = FileHelper.getFileSet(request,
				4000 * 2000, null, "file");
		Timestamp tsp = new Timestamp(new Date().getTime());
		String path = request.getSession().getServletContext().getRealPath("/");
		//System.out.println("path----------------------->"+path);
		List<String> listPath = new ArrayList<String>();
		// ***********************返回上传至服务器的路径 **********************
		listPath = imageService.uploadImg(images,path,tsp); //上传图片获取路径（返回本地路径）
		if (listPath.size() > 0) {
			UrlList.clear();
			UrlList =  new ArrayList<String>();
			for (String url : listPath) {
				//System.out.println("图集：---------------------->" + url);
				UrlList.add(url);
			}
		}
		return listPath.get(0);
	}
	@RequestMapping("getUrl")
	@ResponseBody
	public void getUrl(HttpServletResponse res) throws IOException {
		for(String url : UrlList){
			//System.out.println("geturl------------------------------>"+url);
			res.getWriter().print(UrlList.get(UrlList.size()-1));	
			break;
		}
	}
	/**
	 * 上传图片并且储存
	 * @param request
	 * @param response
	 * @param model
	 */
	@RequestMapping(value="uploadandsave",method=RequestMethod.POST)
	public void uploadandsave(HttpServletRequest request,
			HttpServletResponse response, ModelMap model){
		// ***********************获取上传图片的本地路径**********************
		List<MultipartFile> images = FileHelper.getFileSet(request,
				10000 * 10000, null, "file");
		Timestamp tsp = new Timestamp(new Date().getTime());
		String path = request.getSession().getServletContext().getRealPath("/");
		//System.out.println("path----------------------->"+path);
		//System.out.println("上传的图片数量images的size----------------->"+images.size());
		List<String> listPath = new ArrayList<String>();
		// ***********************返回上传至服务器的路径**********************
		listPath = imageService.uploadImg(images,path,tsp); //上传图片获取路径（返回本地路径）
		//************************获取返回的路径存入数据库********************
		String mcode = request.getParameter("mcode");	    
		//System.out.println("uploadandsave--------------------------->"+mcode);
		
		if (listPath.size() > 0) {
			for (String url : listPath) {
				//System.out.println("图集：---------------------->" + url);
				Image image = new Image();
                image.setMutiCode(mcode);
                image.setUrl(url);
                imageService.saveImage(image);
			}
		}
	}

	
	/**
	 * 删除图片
	 * @param request
	 * @param response
	 * @param paramJson
	 * @return
	 */
	@RequestMapping(value="deleteImage",method=RequestMethod.POST)
	public ModelAndView deleteImage(HttpServletRequest request,HttpServletResponse response,String paramJson){
		// 参数MAP
		Map<String, String> maplist;
		// 返回接口MAP 初始化code 为0失败 1成功
		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put("msg", "0");

		if (paramJson == null) {
			resultMap.put("data", "参数错误！");
			return jsonView(resultMap);
		}
		try {
			maplist = JsonUtil.json2Map(paramJson);
		} catch (JsonParseException e) {
			e.printStackTrace();
			resultMap.put("data", "参数错误！");
			return jsonView(resultMap);
		}
		//从页面获取的要删除的id
		String code = maplist.get("code");
		if (StringUtils.isBlank(code)) {
			resultMap.put("data", "参数错误！");
			return jsonView(resultMap);
		}
		try{
			String [] codes=code.split(",");
			imageService.deleteSelect(codes);
			resultMap.put("msg", "1");
		}catch (Exception e) {
			e.printStackTrace();
			e.getMessage();
			resultMap.put("data", e.getMessage());
		}
		return jsonView(resultMap);
	}
	
	@ResponseBody
	@RequestMapping("deleteImageBymutiCode")
	public  String  deleteImage(String muticode){
		
		imageService.deleteBySql("delete  from  image where mutiCode='"+muticode+"'");
		
		return "";
		
	}

}
