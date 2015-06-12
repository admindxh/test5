package com.rimi.ctibet.web.controller;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.ServletContext;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.ServletContextAware;
import org.springframework.web.multipart.MultipartFile;

import com.google.gson.Gson;
import com.rimi.ctibet.common.controller.BaseController;
import com.rimi.ctibet.common.util.CodeFactory;
import com.rimi.ctibet.common.util.DateUtil;
import com.rimi.ctibet.common.util.FileOperate;
import com.rimi.ctibet.common.util.FileUtil;
import com.rimi.ctibet.common.util.ListUtil;
import com.rimi.ctibet.common.util.LuceneUtil2;
import com.rimi.ctibet.common.util.Pager;
import com.rimi.ctibet.common.util.ReadSettingProperties;
import com.rimi.ctibet.common.util.StringUtil;
import com.rimi.ctibet.common.util.WebSearch;
import com.rimi.ctibet.domain.Activity;
import com.rimi.ctibet.domain.EnrollForm;
import com.rimi.ctibet.domain.EnrollNotice;
import com.rimi.ctibet.domain.Image;
import com.rimi.ctibet.domain.MemberEnrollDetail;
import com.rimi.ctibet.domain.OrderChannelSource;
import com.rimi.ctibet.domain.VoteOption;
import com.rimi.ctibet.web.controller.vo.ActivityVO;
import com.rimi.ctibet.web.controller.vo.MemberEnrollDetailVO;
import com.rimi.ctibet.web.service.IActivityProductService;
import com.rimi.ctibet.web.service.IActivityService;
import com.rimi.ctibet.web.service.IEnrollFormService;
import com.rimi.ctibet.web.service.IEnrollNoticeService;
import com.rimi.ctibet.web.service.IMemberEnrollDetailService;
import com.rimi.ctibet.web.service.IOrderChannelSourceService;
import com.rimi.ctibet.web.service.IVoteOptionService;
import com.rimi.ctibet.web.service.ImageService;

@Controller
@RequestMapping("web/activityController")
public class ActivityController extends BaseController implements ServletContextAware {
	
	 public static Logger LOGGER = Logger.getLogger(ActivityController.class);
	
	ServletContext application;
	
	@Resource IActivityService activityService;
	@Resource IVoteOptionService voteOptionService;
	@Resource IActivityProductService activityProductService;
	@Resource IMemberEnrollDetailService memberEnrollDetailService;
	@Resource IEnrollFormService enrollFormService;
	@Resource IEnrollNoticeService enrollNoticeService;
	@Resource ImageService imageService;
	@Resource IOrderChannelSourceService orderChannelSourceService;
	
	final String tempPath = "/temp/";
	
	/**
	 * 显示活动列表页
	 * @return
	 */
	@RequestMapping("showList")
	public String showList(Pager pager, Model model){
		pager = activityService.searchActivityByName(pager, null, 0, 0, 0);
		model.addAttribute("block", 0);
		model.addAttribute("isEnd", 0);
		model.addAttribute("orderby", 0);
		model.addAttribute("pager", pager);
		return "manage/html/activity/activ-info";
	}
	/**
	 * 搜索活动列表页
	 * @return
	 */
	@RequestMapping("searchList")
	public String searchList(Pager pager, Model model, String name, int block, int isEnd, int orderby ){
		pager = activityService.searchActivityByName(pager, name, block, isEnd, orderby);
		model.addAttribute("name", name);
		model.addAttribute("block", block);
		model.addAttribute("isEnd", isEnd);
		model.addAttribute("orderby", orderby);
		model.addAttribute("pager", pager);
		return "manage/html/activity/activ-info";
	}
	
	/**
	 * 跳转到活动添加页面
	 * @return
	 */
	@RequestMapping("forAddActivity")
	public String forAddActivity(Model model){
		//String activityCode = Uuid.uuid();
		model.addAttribute("activityCode", CodeFactory.createCode("ACT"));
		model.addAttribute("MaxNum", activityService.getMaxNum()+1);
		return "manage/html/activity/activity-creat";
	}
	/**
	 * 添加活动
	 * @param activityVO (接收listEnrollForm、listVoteOption)
	 * @param options 投票选项
	 * @return
	 */
	@RequestMapping("addActivity")
	public String addActivity(
			String activityCode, String startDate1, String endDate1,
			Activity activity, EnrollNotice enrollNotice, ActivityVO activityVO,
			String[] options
	){
	    String basePath = application.getRealPath("/");
	    if(StringUtil.isNotNull(activity.getBannerImg())){
	        String newBannerPath = activity.getBannerImg().replace(tempPath, "");
	        FileUtil.moveFile(basePath + activity.getBannerImg(), basePath+newBannerPath);
	        activity.setBannerImg(newBannerPath);
	    }
	    if(StringUtil.isNotNull(activity.getImg())){
	        String newPath = activity.getImg().replace(tempPath, "");
            FileUtil.moveFile(basePath + activity.getImg(), basePath+newPath);
            activity.setImg(newPath);
        }
		
		activity.setCode(activityCode);
		activity.setStartDate(DateUtil.strToDate(startDate1, DateUtil.DATE_PATTERN));
		activity.setEndDate(DateUtil.strToDate(endDate1, DateUtil.DATE_PATTERN));
		//activity.setLinkUrl(getUrl(Activity.LINK_URL_ACTIVITY_DETAIL, activityCode));
		activity.setLinkUrl(getUrlHtml("activity/detail", activityCode));
		
		List<EnrollForm> listEnrollForm = activityVO.getListEnrollForm();
		List<Image> listImage = activityVO.getListImage();
		if(ListUtil.isNotNull(listImage)){
    		for (Image image : listImage) {
    			String newUrl = image.getUrl().replace(tempPath, "");
    			FileUtil.moveFile(basePath + image.getUrl(), basePath+newUrl);
    			image.setUrl(newUrl);
    		}
		}
		
		//保存活动相关内容
		activityService.saveActivity(activity, enrollNotice, options, listEnrollForm, listImage);
		
		WebSearch webSearch = new WebSearch();
        webSearch.setCode(activity.getCode());
        webSearch.setUrl(activity.getLinkUrl());
        webSearch.setImageUrl(activity.getImg());
        webSearch.setTitle(activity.getName());
        webSearch.setContent(StringUtil.cleanHTML(activity.getSummary()));
        LuceneUtil2.add(webSearch);
		
		return redirect("showList");
	}
	
	/**
	 * 跳转到活动修改页面
	 */
	@RequestMapping("forEditActivity")
	public String forEditActivity(Model model, String code){
		//活动基础信息
		Activity activity = activityService.findByCode(code);
		//对应活动投票的选项
		List<VoteOption> listVoteOption = voteOptionService.getVoteOptionsByActivityCode(activity.getCode());
		//对应活动的报名表单
		List<EnrollForm> listEnrollForm = enrollFormService.getEnrollFormByActivityCode(activity.getCode());
		//对应活动的报名须知
		EnrollNotice enrollNotice = enrollNoticeService.getEnrollNoticesByActivityCode(activity.getCode());
	
		List<Image> listImage = imageService.getActivityOtherBlockByActivityCode(activity.getCode());
		
		model.addAttribute("activityCode", code);
		model.addAttribute("activity", activity);
		model.addAttribute("listVoteOption", listVoteOption);
		model.addAttribute("listEnrollForm", listEnrollForm);
		//model.addAttribute("listEnrollFormJson", new Gson().toJson(listEnrollForm));
		model.addAttribute("enrollNotice", enrollNotice);
		model.addAttribute("listImage", listImage);
		model.addAttribute("listImageJson", new Gson().toJson(listImage));
		
		return "manage/html/activity/activity-edit";
	}
	/**
	 * 修改活动
	 * @throws Exception 
	 */
	@RequestMapping("editActivity")
	public String editActivity(
			String activityCode, String enrollNoticeCode, String startDate1, String endDate1,
			Activity activity, EnrollNotice enrollNotice, ActivityVO activityVO
	) throws Exception{
		String basePath = application.getRealPath("/");
		if(StringUtil.isNotNull(activity.getBannerImg())){
		    String newBannerPath = activity.getBannerImg().replace(tempPath, "");
		    FileUtil.moveFile(basePath + activity.getBannerImg(), basePath+newBannerPath);
		    activity.setBannerImg(newBannerPath);
		}
		if(StringUtil.isNotNull(activity.getImg())){
		    String newPath = activity.getImg().replace(tempPath, "");
            FileUtil.moveFile(basePath + activity.getImg(), basePath+newPath);
            activity.setImg(newPath);
		}
		//activity.setLinkUrl(getUrl(Activity.LINK_URL_ACTIVITY_DETAIL, activityCode));
		activity.setLinkUrl(getUrlHtml("activity/detail", activityCode));
		
		activity.setCode(activityCode);
		activity.setStartDate(DateUtil.strToDate(startDate1, DateUtil.DATE_PATTERN));
		activity.setEndDate(DateUtil.strToDate(endDate1, DateUtil.DATE_PATTERN));
		
		enrollNotice.setCode(enrollNoticeCode);
		List<EnrollForm> listEnrollForm = activityVO.getListEnrollForm();
		List<VoteOption> listVoteOption = activityVO.getListVoteOption();
		List<Image> listImage = activityVO.getListImage();
		if(ListUtil.isNotNull(listImage)){
		    for (Image image : listImage) {
		        String newUrl = image.getUrl().replace(tempPath, "");
		        FileUtil.moveFile(basePath + image.getUrl(), basePath+newUrl);
		        image.setUrl(newUrl);
		    }
		}
		//
		activityService.updateActivity(activity, enrollNotice, listVoteOption, listEnrollForm, listImage);
		
		return redirect("showList");
	}
	
	/**
	 * 删除活动
	 * @return
	 */
	@RequestMapping("deleteActivity")
	public String deleteActivity(String[] codes){
		for (String code : codes) {
			LOGGER.debug(code);
		}
		activityService.deleteActivity(codes);
		return redirect("showList");
	}
	
	/**
	 * 预览活动
	 */
	@RequestMapping("previewActivity")
	public String previewActivity(
		Model model,
		String activityCode, String startDate1, String endDate1,
		Activity activity, EnrollNotice enrollNotice, ActivityVO activityVO,
		String[] options
	){
		
		activity.setCode(activityCode);
		activity.setStartDate(DateUtil.strToDate(startDate1, DateUtil.DATE_PATTERN));
		activity.setEndDate(DateUtil.strToDate(endDate1, DateUtil.DATE_PATTERN));
		List<EnrollForm> listEnrollForm = activityVO.getListEnrollForm();
		List<Image> listImage = activityVO.getListImage();
		
		List<VoteOption> listVoteOption = activityVO.getListVoteOption();
		if(!ListUtil.isNotNull(listVoteOption)){
			listVoteOption = new ArrayList<VoteOption>();
			if(options!=null && options.length>0){
				for (String op : options) {
					VoteOption option = new VoteOption();
					option.setOptions(op);
					listVoteOption.add(option);
				}
			}
		}
		List<MemberEnrollDetailVO> listMemberEnrollDetailVO = memberEnrollDetailService.getNewEnrollMemberByActivityCodeRow(activity.getCode(), 6, true);
		model.addAttribute("activity", activity);
		model.addAttribute("listVoteOption", listVoteOption);
		model.addAttribute("listEnrollForm", listEnrollForm);
		model.addAttribute("enrollNotice", enrollNotice);
		model.addAttribute("listMemberEnrollDetailVO", listMemberEnrollDetailVO);
		model.addAttribute("listImage", listImage);
		model.addAttribute("listImageJson", obj2json(listImage));
		
		model.addAttribute("PROPERTY_TYPE_TEXT", EnrollForm.PROPERTY_TYPE_TEXT);
		return "portal/app/activity/detail-pre";
	}
	
	/**
	 * 活动管理
	 */
	@RequestMapping("forActivityManage")
	public String forActivityManage(Model model, String activityCode, String orderChannelSourceCode, Integer actProductPage, Integer enrollFormPage){
		//活动基本信息
		Activity activity = activityService.findByCode(activityCode);
		//对应活动投票的选项
		List<VoteOption> listVoteOption = voteOptionService.getVoteOptionsByActivityCode(activity.getCode());
		List<OrderChannelSource> listOrderChannelSource = orderChannelSourceService.getOrderChannelSourceByActivityCode(activityCode);
		//--
		model.addAttribute("actProductPage", (actProductPage==null)?1:actProductPage);
		model.addAttribute("enrollFormPage", (enrollFormPage==null)?1:enrollFormPage);
		model.addAttribute("activity", activity);
		model.addAttribute("orderChannelSourceCode", orderChannelSourceCode);
		model.addAttribute("listVoteOption", listVoteOption);
		model.addAttribute("listOrderChannelSource", listOrderChannelSource);

		model.addAttribute("PROPERTY_TYPE_TEXT", EnrollForm.PROPERTY_TYPE_TEXT);
        model.addAttribute("PROPERTY_TYPE_NUMBER", EnrollForm.PROPERTY_TYPE_NUMBER);
        model.addAttribute("PROPERTY_TYPE_SELECT", EnrollForm.PROPERTY_TYPE_SELECT);
		return "manage/html/activity/activity-manage";
	}
	
	/**
	 * 更新活动伪查看数和伪投票数
	 * @return
	 */
	@RequestMapping("updateActivityCheckVoteNum")
	public String updateActivityCheckVoteNum(String activityCode, int fakeCheckNum, ActivityVO activityVO){
		Activity activity = activityService.findByCode(activityCode);
		activity.setFakeCheckNum(fakeCheckNum);
		List<VoteOption> listVoteOption = activityVO.getListVoteOption();
		//update
		activityService.updateActivityCheckNumVoteNum(activity, listVoteOption);
		return redirect("showList");
	}
	
	/**
     * 上传文件
     */
	@RequestMapping(value="fileUpload", produces = "text/html; charset=utf-8")
	@ResponseBody
	public String uploadFile(String activityCode, String floderName, @RequestParam MultipartFile[] files){
		Map<String, String> msgMap = new HashMap<String, String>();
		if(floderName==null || floderName.equals("")){
			LOGGER.info("需要传一个文件夹名字。");
		}
		if(files!=null && files.length>0){
		    Date currentDate = new Date(System.currentTimeMillis());
		    String month = new SimpleDateFormat("yyyyMM").format(currentDate);
			String basePath = application.getRealPath("/");
			String path = ReadSettingProperties.getValue("upload") + "/img/activity/" + tempPath + month + "/" + floderName + "/";
			for (int i = 0; i<files.length; i++) {
				MultipartFile multipartFile = files[i];
				String time = new SimpleDateFormat("yyyyMMddHHmmssSSS").format(currentDate) + i;
				String ext = FileOperate.getFileExt(multipartFile.getOriginalFilename(), true);
				String fileName = activityCode + "-" + time + ext;
				try {
					FileUtil.uploadFile(multipartFile, basePath + path, fileName);
					msgMap.put("msg", multipartFile.getOriginalFilename() + " 上传成功！");
					msgMap.put("filePath", path + fileName);
				} catch (Exception e) {
					e.printStackTrace();
					msgMap.put("msg", multipartFile.getOriginalFilename() + "上传失败！");
				}
			}
		}
		Gson gson = new Gson();
		String json = gson.toJson(msgMap);
		return json;
	}
	
	/**
	 * 下载
	 * web/activityController/download
	 */
	@RequestMapping("download")
	public void download(String code){
	    String url = null;
	    String fileName = null;
	    
	    MemberEnrollDetail memberEnrollDetail = memberEnrollDetailService.findByCode(code);
	    if(memberEnrollDetail!=null){
	        String filePath = application.getRealPath("/") + memberEnrollDetail.getPropertyValue();
	        downloadFile(new File(filePath), memberEnrollDetail.getFileName());
	    }
	    
	}
	
	/********************************************
	 * Setter Getter
	 ********************************************/
	@Override
	public void setServletContext(ServletContext arg0) {
		this.application = arg0;
	}
}
