package com.rimi.ctibet.web.controller;

import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.annotation.Resource;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.ServletContextAware;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.rimi.ctibet.common.controller.BaseController;
import com.rimi.ctibet.common.exception.JsonParseException;
import com.rimi.ctibet.common.util.CodeFactory;
import com.rimi.ctibet.common.util.DateUtil;
import com.rimi.ctibet.common.util.FileOperate;
import com.rimi.ctibet.common.util.FileUtil;
import com.rimi.ctibet.common.util.JsonUtil;
import com.rimi.ctibet.common.util.ListUtil;
import com.rimi.ctibet.common.util.LuceneUtil2;
import com.rimi.ctibet.common.util.Pager;
import com.rimi.ctibet.common.util.ReadSettingProperties;
import com.rimi.ctibet.common.util.StringUtil;
import com.rimi.ctibet.common.util.Uuid;
import com.rimi.ctibet.common.util.WebSearch;
import com.rimi.ctibet.domain.Destination;
import com.rimi.ctibet.domain.Image;
import com.rimi.ctibet.domain.View;
import com.rimi.ctibet.web.service.DestinationService;
import com.rimi.ctibet.web.service.IViewService;
import com.rimi.ctibet.web.service.ImageService;
import com.rimi.ctibet.web.service.MutiImageService;

@Controller
@RequestMapping("web/viewController")
public class ViewController extends BaseController implements ServletContextAware {
    
    public static	Logger LOGGER = Logger.getLogger(ViewController.class);
    
    final String tempPath = "/temp/";
	
	ServletContext application;
	@Resource IViewService viewService;
	@Resource DestinationService destinationService;
	@Resource ImageService imageService;
	@Resource MutiImageService mutiImageService;
	
	
	/**
	 * 获取全部景点
	 */
	@RequestMapping("listView")
	public String listView(Model model, Pager pager){
	    List<Destination> listDestination = destinationService.getAllDestination();
        model.addAttribute("listDestination", listDestination);
        
		//pager = viewService.searchViewByKeyWords(pager, null, null, null, null, View.ORDER_REAL_GONECOUNT);
        pager = viewService.searchViewByKeyWords(pager, null, null, null, null, View.ORDER_EDITTIME);
		model.addAttribute("pager", pager);
		model.addAttribute("orderBy", View.ORDER_REAL_GONECOUNT);
		return "manage/html/travel/scenic-info-manage";
	}
	/**
	 * 通过'目的地、景点名称'搜索景点 去过数
	 */
	@RequestMapping("searchView")
	public String searchView(Model model, Pager pager, String viewName, String destinationCode, int orderBy){
	    List<Destination> listDestination = destinationService.getAllDestination();
        model.addAttribute("listDestination", listDestination);
        
	    pager = viewService.searchViewByKeyWords(pager, destinationCode, null, viewName, null, orderBy);
        model.addAttribute("pager", pager);
        model.addAttribute("viewName", viewName);
        model.addAttribute("destinationCode", destinationCode);
        model.addAttribute("orderBy", orderBy);
		return "manage/html/travel/scenic-info-manage";
	}
	
	/**
	 * 跳转到 添加View页面
	 */
	@RequestMapping("forAddView")
	public String forAddView(Model model){
	    String code = CodeFactory.createCode("VIEW");
	    model.addAttribute("code", code);
	    
	    List<Destination> listDestination = destinationService.getAllDestination();
        model.addAttribute("listDestination", listDestination);
	    
		return "manage/html/travel/scen-info-add";
	}
	/**
	 * 保存景点
	 */
	@RequestMapping("addView")
	public String addView(View view){
	    String basePath = application.getRealPath("/");
	    if(StringUtil.isNotNull(view.getImg())){
	        String img = view.getImg();
	        String newPath = img.replace(tempPath, "");
            FileUtil.moveFile(basePath + img, basePath+newPath);
            view.setImg(newPath);
	    }
	    if(StringUtil.isNotNull(view.getViewImage())){
	        String[] viewImages = view.getViewImage().split(",");
	        if(viewImages!=null && viewImages.length > 0){
	            List<String> newViewImages = new ArrayList<String>();
    	        for (String img : viewImages) {
    	            String newBannerPath = img.replace(tempPath, "");
    	            FileUtil.moveFile(basePath + img, basePath+newBannerPath);
    	            newViewImages.add(newBannerPath);
                }
    	        view.setViewImage(ListUtil.join(newViewImages, ","));
	        }
	    }
	    view.setAvaliable(1);
		view.setCreateTime(new Date());
		//view.setLinkUrl(View.LINK_URL_PORTAL_VIEW_DETAIL + view.getCode());
		view.setLinkUrl("tourism/view/detail/"+ view.getCode()+".html");
		view.setLastEditTime(DateUtil.getCurrentDate());
		viewService.save(view);
		
		WebSearch webSearch = new WebSearch();
		webSearch.setCode(view.getCode());
	    webSearch.setUrl(view.getLinkUrl());
	    webSearch.setImageUrl(view.getImg());
	    webSearch.setTitle(view.getViewName());
	    webSearch.setContent(view.getViewIntroduce());
		LuceneUtil2.add(webSearch);
		
		return redirect("listView");
	}
	
	/**
	 * 跳转到 修改View页面
	 */
	@RequestMapping("forEditView")
	public String forEditView(Model model, String code, Integer wantCount, Integer goneCount){
		View view = viewService.findByCode(code);
		view.setWantCount((wantCount==null?0:wantCount));
		view.setGoneCount((goneCount==null?0:goneCount));
		model.addAttribute("view", view);
		
		List<Destination> listDestination = destinationService.getAllDestination();
        model.addAttribute("listDestination", listDestination);
        
		return "manage/html/travel/scen-info-edit";
	}
	/**
	 * 修改View
	 */
	@RequestMapping("editView")
	public String editView(View view){
		String code = view.getCode();
		View newView = viewService.findByCode(code);
		String basePath = application.getRealPath("/");
        if(StringUtil.isNotNull(view.getImg())){
            String img = view.getImg();
            String newPath = img.replace(tempPath, "");
            FileUtil.moveFile(basePath + img, basePath+newPath);
            newView.setImg(newPath);
        }
		if(StringUtil.isNotNull(view.getViewImage())){
            String[] viewImages = view.getViewImage().split(",");
            if(viewImages!=null && viewImages.length > 0){
                List<String> newViewImages = new ArrayList<String>();
                for (String img : viewImages) {
                    String newBannerPath = img.replace(tempPath, "");
                    FileUtil.moveFile(basePath + img, basePath+newBannerPath);
                    newViewImages.add(newBannerPath);
                }
                newView.setViewImage(ListUtil.join(newViewImages, ","));
            }
        }
		
		newView.setLinkUrl("tourism/view/detail/"+ view.getCode()+".html");
		//newView.setLinkUrl(View.LINK_URL_PORTAL_VIEW_DETAIL + view.getCode());
		newView.setDestinationCode(view.getDestinationCode());
		newView.setViewName(view.getViewName());
		newView.setViewIntroduce(view.getViewIntroduce());
		newView.setView_360full(view.getView_360full());
		newView.setHdFullUrl(view.getHdFullUrl());
		newView.setRealSceneVideoUrl(view.getRealSceneVideoUrl());
		newView.setAddress(view.getAddress());
		newView.setGuide(view.getGuide());
		newView.setTraffic(view.getTraffic());
		newView.setNotice(view.getNotice());
		newView.setShortGuide(view.getShortGuide());
		newView.setShortTraffic(view.getShortTraffic());
		newView.setShortNotice(view.getShortNotice());
		newView.setKeyword(view.getKeyword());
		newView.setFakeWantCount(view.getFakeWantCount());
		newView.setFakeGoneCount(view.getFakeGoneCount());
		newView.setXy(view.getXy());
		newView.setLastEditTime(DateUtil.getCurrentDate());
		newView.setTdkTitle(view.getTdkTitle());
		newView.setTdkDescription(view.getTdkDescription());
		newView.setTdkKeywords(view.getTdkKeywords());
		viewService.updateLogical(newView);
		
		WebSearch webSearch = new WebSearch();
        webSearch.setCode(code);
        webSearch.setUrl(newView.getLinkUrl());
        webSearch.setImageUrl(view.getImg());
        webSearch.setTitle(view.getViewName());
        webSearch.setContent(view.getViewIntroduce());
        LuceneUtil2.update(webSearch);
		
		return redirect("listView");
	}
	
	/**
	 * 删除景点
	 */
	@RequestMapping("deleteViews")
	public String deleteViews(String[] codes){
	    viewService.deleteView(codes);
	    return redirect("listView");
	}
	
	/**
     * 跳转到景点图集管理页面
     */
    @RequestMapping("forViewPictureManage")
    public String forViewPictureManage(Model model, String viewCode, String destinationCode){
        model.addAttribute("viewCode", viewCode);
        model.addAttribute("destinationCode", destinationCode);
        return "manage/html/travel/scen-atlas-management";
    }
    /**
     * 获取景点图集
     */
    @RequestMapping(value="loadViewAtlas", produces = "text/html; charset=utf-8")
    @ResponseBody
    public String loadViewAtlas(Pager pager, String viewCode, String type){
        pager = imageService.getViewAtlasByCodeType(pager, viewCode, type);
        return obj2json(pager);
    }
    
    /**
     * 删除图集图片
     */
    @RequestMapping(value="deleteImage", produces = "text/html; charset=utf-8")
    @ResponseBody
    public String deleteImage(String[] codes){
        try {
            imageService.deleteImages(application.getRealPath("/"), codes);
            return "success";
        } catch (Exception e) {
            e.printStackTrace();
            return "error";
        }
    }

	
	
	@RequestMapping(value="findAll")
	public String findAll(HttpServletRequest request, Pager pager){
		pager.setPageSize(5);
		pager = viewService.getAll(pager);
		request.setAttribute("pager", pager);
		return "res";
	}

	@RequestMapping(value="search")
	public String search(View view){
		//List<View> list = viewService.search(view);
		//for(View v : list)//System.out.println(v);;
		return "";
	}
	
	@RequestMapping(value="testPager")
	public String testPager(Model model, Pager pager){
		pager.setPageSize(3);
		model.addAttribute("pager", viewService.testPager(pager));
		return "res";
	}
	
	/**
	 * 景点信息管理的分页列表
	 * @return
	 */
	@RequestMapping("getViewPager")
	public String getViewPager(ModelMap model,String destinationCode,String viewName,Pager pager){
		Pager resultPager = viewService.getViewPager(destinationCode,viewName,pager);
		List<Destination> destinationList = destinationService.getAllDestination();
		model.put("pager", resultPager);
		model.put("destinationList", destinationList);
		return "/manage/html/travel/scenic-info-manage";
	}
	/**
	 * 景点修改和添加
	 * @param viewCode
	 * @param model
	 * @return
	 */
	@RequestMapping("viewEdit")
	public String viewEdti(String viewCode,ModelMap model){
		List<Destination> destinationList = destinationService.getAllDestination();
		model.put("destinationList", destinationList);
		if(StringUtils.isNotBlank(viewCode)){ //传入的viewcode不为空 说明是修改
			View view = viewService.findByCode(viewCode);
			Destination viewDes = destinationService.getDestinationByCode(view.getDestinationCode());
			model.put("view", view);
			model.put("des",viewDes);
			return "/manage/html/travel/scen-info-edit";
			
		}else{  //新增
			return "/manage/html/travel/scen-info-add";
		}
	}
	/**
	 * 获取景点的详细信息
	 * @param viewCode
	 * @param model
	 * @return
	 */
	@RequestMapping("getviewDetail")
	public String getviewDetail(String viewCode,ModelMap model){
		return null;
	}
	
	/**
	 * 删除View
	 */
	@RequestMapping("deleteView")
	public ModelAndView deleteView(HttpServletRequest request,HttpServletResponse response,String paramJson){
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
			viewService.deleteSelect(codes);
			resultMap.put("msg", "1");
		}catch (Exception e) {
			e.printStackTrace();
			e.getMessage();
			resultMap.put("data", e.getMessage());
		}
		return jsonView(resultMap);
	}
	/**
	 * 保存景点 
	 * @param request
	 * @param response
	 * @param view
	 * @return
	 */
	@RequestMapping("saveView")
	public String saveView(HttpServletRequest request,HttpServletResponse response,View view){
		//System.out.println(view.toString());
		viewService.saveView(view);
		return "redirect:web/viewController/getViewPager";
	}
	/**
	 * 获取景点真实想去数和去过数
	 * @param option
	 * @param destinationCode
	 * @return
	 */
	@RequestMapping("getrealcount")
    public @ResponseBody String getrealcount(String option,String viewCode){
		View view = viewService.findByCode(viewCode);
		if("1".equals(option)){
			return String.valueOf(view.getWantCount());
		}else{
			return String.valueOf(view.getGoneCount());
		}
	}
	
	/**
	 * 获取和景点关联的 团游 商户 攻略数量
	 * web/viewController/checkViewDelete
	 */
    @RequestMapping(value="checkViewDelete", produces = "text/html; charset=utf-8")
    @ResponseBody
    public String checkViewDelete(String viewCode){
        int viewRelationContent = viewService.getViewRelationContent(viewCode);
        return viewRelationContent+"";
    }

    /**
     * 通过景点名字来查询同名景点的数量
     * web/viewController/getSameViewNum
     */
    @RequestMapping(value="getSameViewNum", produces = "text/html; charset=utf-8")
    @ResponseBody
    public String getSameViewNum(String name){
        return viewService.getSameViewNum(name)+"";
    }
    
    
	/********************************************
     * other method
     ********************************************/
	/**
     * 保存图片
     */
    @RequestMapping(value="fileUpload", produces = "text/html; charset=utf-8")
    @ResponseBody
    public String uploadFile(String code, String floderName, @RequestParam MultipartFile[] files){
        Map<String, String> msgMap = new HashMap<String, String>();
        if(floderName==null || floderName.equals("")){
            LOGGER.info("需要传一个文件夹名字。");
        }
        if(files!=null && files.length>0){
            String basePath = application.getRealPath("/");
            String path = ReadSettingProperties.getValue("upload") + "/img/view/" + tempPath + code + "/" + floderName + "/";
            List<String> filesPaths = new ArrayList<String>();
            List<String> filesNames = new ArrayList<String>();
            for (int i = 0; i<files.length; i++) {
                MultipartFile multipartFile = files[i];
                String time = new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date(System.currentTimeMillis()));
                time += new DecimalFormat("0000").format(new Random().nextInt(10000));
                String ext = FileOperate.getFileExt(multipartFile.getOriginalFilename(), true);
                String fileName = time + ext;
                try {
                    FileUtil.uploadFile(multipartFile, basePath + path, fileName);
                    filesPaths.add(path + fileName);
                    filesNames.add(multipartFile.getOriginalFilename());
                } catch (Exception e) {
                    e.printStackTrace();
                    msgMap.put("msg", multipartFile.getOriginalFilename() + "上传失败！");
                }
            }
            if(ListUtil.isNotNull(filesPaths))msgMap.put("filePath", ListUtil.join(filesPaths, ","));
            if(ListUtil.isNotNull(filesNames))msgMap.put("fileName", ListUtil.join(filesNames, ","));
        }
        return obj2json(msgMap);
    }
    
    /**
     * 保存图集
     */
    @RequestMapping(value="uploadImages", produces = "text/html; charset=utf-8")
    @ResponseBody
    public String uploadImages(String destinationCode, String viewCode, String floderName, @RequestParam MultipartFile file){
        Map<String, String> msgMap = new HashMap<String, String>();
        if(floderName==null || floderName.equals("")){
            LOGGER.info("需要传一个文件夹名字。");
        }
        if(file!=null){
            String basePath = application.getRealPath("/");
            String path = ReadSettingProperties.getValue("upload") + "/img/view/" + viewCode + "/" + floderName + "/";
            String time = new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date(System.currentTimeMillis()));
            time += new DecimalFormat("0000").format(new Random().nextInt(10000));
            String ext = FileOperate.getFileExt(file.getOriginalFilename(), true);
            String fileName = time + ext;
            try {
                FileUtil.uploadFile(file, basePath + path, fileName);
                msgMap.put("filePath", path + fileName);
                msgMap.put("fileName", file.getOriginalFilename());
                
                Image image = new Image();
                image.setAvaliable(1);
                image.setCode(Uuid.uuid());
                image.setUrl(path + fileName);
                image.setMutiCode(viewCode);
                image.setViewCode(viewCode);
                image.setDestinationCode(destinationCode);
                image.setCreateTime(new Timestamp(System.currentTimeMillis()));
                image.setSummary(Image.ATLAS_OFFICAL);
                imageService.save(image);
                
            } catch (Exception e) {
                e.printStackTrace();
                msgMap.put("msg", file.getOriginalFilename() + "上传失败！");
                msgMap.put("errorImage", file.getOriginalFilename());
            }
        }
        return obj2json(msgMap);
    }
	
	/********************************************
	 *  Setter Getter
	 ********************************************/
	public IViewService getViewService() {
		return viewService;
	}
	public void setViewService(IViewService viewService) {
		this.viewService = viewService;
	}

	@Override
	public void setServletContext(ServletContext application) {
		this.application = application;
	}
	
	public static void main(String[] args) {
	    String num = "2.0";
	    //System.out.println(new Float(num).intValue());
    }
	
}
