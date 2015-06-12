package com.rimi.ctibet.web.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.ServletContext;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.ServletContextAware;
import org.springframework.web.multipart.MultipartFile;

import com.rimi.ctibet.common.controller.BaseController;
import com.rimi.ctibet.common.util.CodeFactory;
import com.rimi.ctibet.common.util.FileOperate;
import com.rimi.ctibet.common.util.FileUtil;
import com.rimi.ctibet.common.util.LuceneUtil2;
import com.rimi.ctibet.common.util.Pager;
import com.rimi.ctibet.common.util.ReadSettingProperties;
import com.rimi.ctibet.common.util.StringUtil;
import com.rimi.ctibet.common.util.WebSearch;
import com.rimi.ctibet.domain.Activity;
import com.rimi.ctibet.domain.Content;
import com.rimi.ctibet.domain.Praise;
import com.rimi.ctibet.web.service.IContentService;
import com.rimi.ctibet.web.service.IPraiseService;

/**
 * 专题
 */
@Controller
@RequestMapping("web/specialController")
public class SpecialController extends BaseController implements ServletContextAware {
	
	final String tempPath = "/temp/";
	
	ServletContext application;
	
	@Resource IContentService contentService;
	@Resource IPraiseService praiseService;
	
	/**
	 * 显示后台专题信息管理列表
	 */
	@RequestMapping("showSpecialList")
	public String showSpecialList(Pager pager, Model model){
		pager = contentService.searchSpecailListByNameTime(pager, null, null, 0);
		model.addAttribute("ORDERBY_FAVORITE", Activity.ORDERBY_FAVORITE);
		model.addAttribute("ORDERBY_REPLY", Activity.ORDERBY_REPLY);
		model.addAttribute("orderBy", 0);
		model.addAttribute("pager", pager);
		return "manage/html/activity/special-info";
	}
	/**
	 * 搜索后台专题信息管理列表
	 */
	@RequestMapping("searchSpecialList")
	public String searchSpecialList(Pager pager, Model model, String name, String time, int orderBy){
		pager = contentService.searchSpecailListByNameTime(pager, name, time, orderBy);
		model.addAttribute("ORDERBY_FAVORITE", Activity.ORDERBY_FAVORITE);
		model.addAttribute("ORDERBY_REPLY", Activity.ORDERBY_REPLY);
		model.addAttribute("name", name);
		model.addAttribute("time", time);
		model.addAttribute("orderBy", orderBy);
		model.addAttribute("pager", pager);
		return "manage/html/activity/special-info";
	}
	
	/**
	 * 跳转到添加专题页
	 */
	@RequestMapping("forAddSpecial")
	public String forAddSpecial(Model model){
		String code = CodeFactory.createCode("SPE");
		model.addAttribute("code", code);
		model.addAttribute("maxSortNum", contentService.getSpecialMaxSortNum()+1);
		return "manage/html/activity/special-creat";
	}
	/**
	 * 添加专题
	 */
	@RequestMapping("addSpecial")
	public String addSpecial(Content content){
		String basePath = application.getRealPath("/");
		if(StringUtil.isNotNull(content.getTitle())){
    		String newPath = content.getTitle().replace(tempPath, "");
    		FileUtil.moveFile(basePath + content.getTitle(), basePath+newPath);
    		content.setTitle(newPath);
		}
		if(StringUtil.isNotNull(content.getTag())){
		    String newPath = content.getTag().replace(tempPath, "");
		    FileUtil.moveFile(basePath + content.getTag(), basePath+newPath);
		    content.setTag(newPath);
		}
		content.setUrl(getUrlHtml(Activity.LINK_URL_SPECIAL_DETAIL, content.getCode()));
		
		contentService.saveSpecial(content);
		
		WebSearch webSearch = new WebSearch();
        webSearch.setCode(content.getCode());
        webSearch.setUrl(content.getUrl());
        webSearch.setImageUrl(content.getTag());
        webSearch.setTitle(content.getContentTitle());
        webSearch.setContent(StringUtil.cleanHTML(content.getSummary()));
        LuceneUtil2.add(webSearch);
		
		return redirect("showSpecialList");
	}
	/**
	 * 跳转到修改专题页面
	 */
	@RequestMapping("forEditSpecial")
	public String forEditSpecial(Model model,String code){
		Content content = contentService.findByCode(code);
		Praise praise = praiseService.getPraiseContentCode(code);
		if(praise==null){
		    praise = new Praise();
		    praise.setViewCount(0);
		}
		model.addAttribute("content", content);
		model.addAttribute("praise", praise);
		return "manage/html/activity/special-edit";
	}
	/**
	 * 修改专题
	 */
	@RequestMapping("editSpecial")
	public String editSpecial(Content content, String praiseCode, Integer falseViewcount){
		String basePath = application.getRealPath("/");
		if(StringUtil.isNotNull(content.getTitle())){
		    String newPath = content.getTitle().replace(tempPath, "");
	        FileUtil.moveFile(basePath + content.getTitle(), basePath+newPath);
	        content.setTitle(newPath);
        }
		if(StringUtil.isNotNull(content.getTag())){
            String newPath = content.getTag().replace(tempPath, "");
            FileUtil.moveFile(basePath + content.getTag(), basePath+newPath);
            content.setTag(newPath);
        }
		content.setUrl(getUrlHtml(Activity.LINK_URL_SPECIAL_DETAIL, content.getCode()));
		
		Praise praise = new Praise();
		praise.setCode(praiseCode);
		praise.setFalseViewcount((falseViewcount==null)?(0):(falseViewcount));
		contentService.updateSpecial(content, praise);
		
		WebSearch webSearch = new WebSearch();
        webSearch.setCode(content.getCode());
        webSearch.setUrl(content.getUrl());
        webSearch.setImageUrl(content.getTag());
        webSearch.setTitle(content.getContentTitle());
        webSearch.setContent(StringUtil.cleanHTML(content.getSummary()));
        LuceneUtil2.update(webSearch);
		
		return redirect("showSpecialList");
	}
	
	/**
	 * 删除
	 * @param codes
	 * @return
	 */
	@RequestMapping("deleteSpecial")
	public String deleteSpecial(String[] codes){
		contentService.deleteByCodes(codes);
		return redirect("showSpecialList");
	}
	
	/**
     * 预览专题
     * web/specialController/previewSpecial
     */
    @RequestMapping("previewSpecial")
    public String previewSpecial(Model model, Content content){
        model.addAttribute("content", content);
        return "portal/app/activity/special-detail";
    }
	
	
	
	/********************************************
     * other method
     ********************************************/
	/**
	 * 上传图片
	 * @return
	 */
	@RequestMapping(value="fileUpload", produces = "text/html; charset=utf-8")
	@ResponseBody
	public String uploadFile(String code, String floderName, @RequestParam MultipartFile[] files){
		Map<String, String> msgMap = new HashMap<String, String>();
		if(files!=null && files.length>0){
		    Date currentDate = new Date(System.currentTimeMillis());
            String month = new SimpleDateFormat("yyyyMM").format(currentDate);
			String basePath = application.getRealPath("/");
			String path = ReadSettingProperties.getValue("upload") + "/img/activity/" + tempPath + month + "/" + floderName + "/";
			for (int i = 0; i<files.length; i++) {
				MultipartFile multipartFile = files[i];
				String time = new SimpleDateFormat("yyyyMMddHHmmssSSS").format(currentDate) + i;
				String ext = FileOperate.getFileExt(multipartFile.getOriginalFilename(), true);
				String fileName = code + "-" + time + ext;
				try {
					FileUtil.uploadFile(multipartFile, basePath + path, fileName);
					msgMap.put("msg", multipartFile.getOriginalFilename() + " 上传成功！");
					msgMap.put("filePath", path + fileName);
					//msgMap.put("fileName", multipartFile.getOriginalFilename());
				} catch (Exception e) {
					e.printStackTrace();
					msgMap.put("msg", multipartFile.getOriginalFilename() + "上传失败！");
				}
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
