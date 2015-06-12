package com.rimi.ctibet.portal.controller;

import java.io.IOException;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.ServletContextAware;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.google.gson.Gson;
import com.rimi.ctibet.common.constants.Constants;
import com.rimi.ctibet.common.controller.BaseController;
import com.rimi.ctibet.common.util.CheckMobile;
import com.rimi.ctibet.common.util.FileOperate;
import com.rimi.ctibet.common.util.FileUtil;
import com.rimi.ctibet.common.util.ListUtil;
import com.rimi.ctibet.common.util.Pager;
import com.rimi.ctibet.common.util.ReadSettingProperties;
import com.rimi.ctibet.common.util.StringUtil;
import com.rimi.ctibet.common.util.cache.CacheOperation;
import com.rimi.ctibet.domain.Activity;
import com.rimi.ctibet.domain.EnrollForm;
import com.rimi.ctibet.domain.EnrollNotice;
import com.rimi.ctibet.domain.Image;
import com.rimi.ctibet.domain.LogUser;
import com.rimi.ctibet.domain.OrderChannelSource;
import com.rimi.ctibet.domain.VoteOption;
import com.rimi.ctibet.portal.controller.vo.VoteOptionVO;
import com.rimi.ctibet.web.controller.vo.MemberEnrollDetailVO;
import com.rimi.ctibet.web.service.IActivityProductService;
import com.rimi.ctibet.web.service.IActivityService;
import com.rimi.ctibet.web.service.IEnrollFormService;
import com.rimi.ctibet.web.service.IEnrollNoticeService;
import com.rimi.ctibet.web.service.IMemberEnrollDetailService;
import com.rimi.ctibet.web.service.IOrderChannelSourceService;
import com.rimi.ctibet.web.service.IVoteOptionService;
import com.rimi.ctibet.web.service.ImageService;

@Controller("portal_activityController")
@RequestMapping({"portal/activityController","activity"})
public class ActivityController extends BaseController implements ServletContextAware {
	
	public static final Logger LOGGER = Logger.getLogger(ActivityController.class);
	
	ServletContext application; 
	
	final String tempPath = "/temp/";
	
	@Resource IActivityService activityService;
	@Resource IVoteOptionService voteOptionService;
	@Resource IEnrollFormService enrollFormService;
	@Resource IEnrollNoticeService enrollNoticeService;
	@Resource ImageService imageService;
	@Resource IActivityProductService activityProductService;
	@Resource IMemberEnrollDetailService memberEnrollDetailService;
	@Resource IOrderChannelSourceService orderChannelSourceService;

	@ModelAttribute()
	public void initMethod(Model model){
	    //model.addAttribute("programNam","5");
	}
	
	/**
	 * 自驾米林
	 */
	@RequestMapping("drive-milin")
	public String driveMilin(){
	    return "portal/app/activity/other/drive_milin";
	}
	
	/**
	 * 活动&专题首页
	 */
	@RequestMapping("activityIndex")
	public ModelAndView activityIndex(Model model){
	    model.addAttribute("programNam","5");
		ModelAndView view = (ModelAndView) CacheOperation.getInstance().getCacheData(this, "activityIndexHandler", null, Constants.intervalTime, Constants.maxVisitCount);
		return view;
	}
	
	public ModelAndView activityIndexHandler(){
		ModelAndView view = new ModelAndView("portal/app/activity/index");
		List<Image> listBannerImage = imageService.getImageByMutiCode(Activity.MUTI_ACTIVITY_BANNER_CODE);
		List<Image> listSpecailShowImage = imageService.getImageByMutiCode(Activity.MUTI_SPECAIL_SHOW_CODE);
		//最近参与
		//List<MemberEnrollDetailVO> listMemberEnrollDetailVO = memberEnrollDetailService.getNewEnrollMemberByActivityCodeRow(null, 7, true);
		List<MemberEnrollDetailVO> listMemberEnrollDetailVO = activityService.getJoinMemberList(5);
		
		view.addObject("listBannerImage", listBannerImage);
		view.addObject("listSpecailShowImage", listSpecailShowImage);
		view.addObject("listMemberEnrollDetailVO", listMemberEnrollDetailVO);
		return view;
	}
	/**
	 * 活动&专题首页 全部活动列表
	 * portal/activityController/getActivityIndexActivityList
	 */
	@RequestMapping(value="getActivityIndexActivityList", produces = "text/html; charset=utf-8")
	@ResponseBody
	public String getActivityIndexActivityList(Pager pager){
		pager = activityService.getAllActivityOrder(pager, 0, false, false);
		pager.setTotalPage(pager.getTotalPage());
		return new Gson().toJson(pager);
	}
	
	/**
	 * 前往专题活动列表页
	 * @return
	 */
	@RequestMapping({"forActivities", "more"})
	public String forActivities(Model model){
	    model.addAttribute("programNam","5");
		model.addAttribute("ORDERBY_DATE", Activity.ORDERBY_DATE);
		model.addAttribute("ORDERBY_CHECKNUM", Activity.ORDERBY_CHECKNUM);
		return "portal/app/activity/activities";
	}
	
	/**
	 * 专题活动列表页 按时间和热度获取 活动列表
	 * portal/activityController/getActivitiesPageList
	 */
	@RequestMapping(value="getActivitiesPageList", produces = "text/html; charset=utf-8")
	@ResponseBody
	public String getActivitiesPageList(Pager pager, int orderBy){
		pager = activityService.getAllActivityOrder(pager, orderBy, false, false);
		pager.setTotalPage(pager.getTotalPage());
		return obj2json(pager);
	}
	
	/**
	 * 跳转到 手机活动详情页
	 */
	@RequestMapping({"mobileDetail"})
	public String forMobileActivityDetail(HttpServletRequest request, HttpServletResponse response, Model model, String code, String OCS) throws IOException{
	    activityService.activityCheckNumPlusOne(code);
	    //报名来源
        if(StringUtil.isNotNull(OCS)){
            OrderChannelSource orderChannelSource = orderChannelSourceService.findByCode(OCS);
            if(orderChannelSource==null){
                response.sendError(HttpServletResponse.SC_NOT_FOUND, "没有找到订单报名渠道");
                return "";
            }
        }else{
            OCS = "-1";//表示官方渠道
        }
        //活动基础信息
        Activity activity = activityService.findByCode(code);
        if(activity != null){
            try {
                String now_str = new SimpleDateFormat("yyyy-MM-dd").format(new Date(System.currentTimeMillis()));
                Date now = new SimpleDateFormat("yyyy-MM-dd").parse(now_str);
                
                if(activity.getEndDate()==null){
                    model.addAttribute("isEnd", "yes");
                }else if(now.after(activity.getEndDate())){
                    model.addAttribute("isEnd", "yes");
                }else{
                    model.addAttribute("isEnd", "no");
                }
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        
        model.addAttribute("activity", activity);
        model.addAttribute("OCS", OCS);
        
	    return ReadSettingProperties.getValue("activityMapping.properties", code + "_mobile");
	}
	/**
	 * 跳转到 手机活动报名
	 * @throws IOException 
	 */
	@RequestMapping("mobileEnroll")
	public String forMobileEnroll(HttpServletRequest request, HttpServletResponse response, Model model, String code, String OCS) throws IOException{
	    Activity activity = activityService.findByCode(code);
	    //对应活动的报名表单
        List<EnrollForm> listEnrollForm = enrollFormService.getEnrollFormByActivityCode(activity.getCode());
        
        model.addAttribute("activity", activity);
        model.addAttribute("listEnrollForm", listEnrollForm);
        model.addAttribute("OCS", OCS);
        model.addAttribute("PROPERTY_TYPE_TEXT", EnrollForm.PROPERTY_TYPE_TEXT);
        model.addAttribute("PROPERTY_TYPE_NUMBER", EnrollForm.PROPERTY_TYPE_NUMBER);
        model.addAttribute("PROPERTY_TYPE_SELECT", EnrollForm.PROPERTY_TYPE_SELECT);
        
	    return "portal/app/activity/other/qx-signUp";
	}
	
	
	/**
	 * 前往活动详情页
	 * @param OCS(OrderChannelSource code)
	 * @throws IOException 
	 */
	@RequestMapping({"forActivityDetail","detail"})
	public String forActivityDetail(HttpServletRequest request, HttpServletResponse response, Model model, String code, String OCS) throws IOException{
	    
		//查看数+1
		activityService.activityCheckNumPlusOne(code);
		
		//报名来源
		if(StringUtil.isNotNull(OCS)){
    		OrderChannelSource orderChannelSource = orderChannelSourceService.findByCode(OCS);
    		if(orderChannelSource==null){
    		    response.sendError(HttpServletResponse.SC_NOT_FOUND, "没有找到订单报名渠道");
    		    return "";
    		}
		}else{
		    OCS = "-1";//表示官方渠道
		}
		//活动基础信息
		Activity activity = activityService.findByCode(code);
		if(activity != null){
            try {
                String now_str = new SimpleDateFormat("yyyy-MM-dd").format(new Date(System.currentTimeMillis()));
                Date now = new SimpleDateFormat("yyyy-MM-dd").parse(now_str);
                
                if(activity.getEndDate()==null){
                    model.addAttribute("isEnd", "yes");
                }else if(now.after(activity.getEndDate())){
                    model.addAttribute("isEnd", "yes");
                }else{
                    model.addAttribute("isEnd", "no");
                }
            } catch (ParseException e) {
                e.printStackTrace();
            }
    		//
    		//对应活动投票的选项
    		List<VoteOption> listVoteOption = voteOptionService.getVoteOptionsByActivityCode(activity.getCode());
    		List<VoteOptionVO> listVoteOptionVO = computVotePercent(listVoteOption);
    		//对应活动的报名表单
    		List<EnrollForm> listEnrollForm = enrollFormService.getEnrollFormByActivityCode(activity.getCode());
    		//对应活动的报名须知
    		EnrollNotice enrollNotice = enrollNoticeService.getEnrollNoticesByActivityCode(activity.getCode());
    		//最新报名
    		List<MemberEnrollDetailVO> listMemberEnrollDetailVO = memberEnrollDetailService.getNewEnrollMemberByActivityCodeRow(activity.getCode(), 5, true);
    		
            model.addAttribute("activity", activity);
            model.addAttribute("OCS", OCS);
            
            //判断是否为手机
            String userAgent = request.getHeader("user-agent");
            boolean check = CheckMobile.check(userAgent);
            if(check && StringUtil.isNotNull(ReadSettingProperties.getValue("activityMapping.properties", code + "_mobile"))){
                model.addAttribute("code", code);
                return ReadSettingProperties.getValue("activityMapping.properties", code + "_mobile");
            }
            
            model.addAttribute("lgUser", getFrontUser());
            model.addAttribute("listVoteOption", listVoteOption);
            model.addAttribute("listVoteOptionVO", listVoteOptionVO);
            model.addAttribute("allVoteNum", (listVoteOptionVO!=null&&listVoteOptionVO.size()>0)?(listVoteOptionVO.get(0).getAllVoteNum()):(0));
            model.addAttribute("allFakeVoteNum", (listVoteOptionVO!=null&&listVoteOptionVO.size()>0)?(listVoteOptionVO.get(0).getAllFakeVoteNum()):(0));
            model.addAttribute("listEnrollForm", listEnrollForm);
            model.addAttribute("listEnrollFormJson", new Gson().toJson(listEnrollForm));
            model.addAttribute("enrollNotice", enrollNotice);
            //model.addAttribute("listImage", listImage);
            model.addAttribute("listMemberEnrollDetailVO", listMemberEnrollDetailVO);
            
		}else{
		    response.sendError(HttpServletResponse.SC_NOT_FOUND, "通过活动code没有查询到活动信息");
            return "";
		}
		
		model.addAttribute("PROPERTY_TYPE_TEXT", EnrollForm.PROPERTY_TYPE_TEXT);
		model.addAttribute("PROPERTY_TYPE_NUMBER", EnrollForm.PROPERTY_TYPE_NUMBER);
		model.addAttribute("PROPERTY_TYPE_SELECT", EnrollForm.PROPERTY_TYPE_SELECT);

		String url = ReadSettingProperties.getValue("activityMapping.properties", code);
		if(StringUtil.isNotNull(url)){
		    return url;
		}else{
		    return "portal/app/activity/detail";
		}
        /*if(code.equals("ACT4222013853974417")||code.equals("ACT4238208750543147")){
            return "portal/app/activity/other/qx_detail";
        }else{
            return "portal/app/activity/detail";
        }*/
	}
	
	/**
	 * 获取活动详情页 其他模块信息
	 * portal/activityController/showOtherBlockList
	 */
	@RequestMapping(value="showOtherBlockList", produces = "text/html; charset=utf-8")
	@ResponseBody
	public String showOtherBlockList(Pager pager, String activityCode){
		//Pager pager = new Pager(pageSize, currentPage);
		pager = imageService.getActivityOtherBlockByActivityCode(pager, activityCode);
		pager.setTotalPage(pager.getTotalPage());
		return new Gson().toJson(pager);
	}
	
	/**
     * 报名、作品 上传文件
     */
	@RequestMapping(value="fileUpload", produces = "text/html; charset=utf-8")
	@ResponseBody
	public String uploadFile(String activityCode, String floderName, @RequestParam MultipartFile[] files){
		/**TODO 需要换成获取当前用户 */
		String currentUserCode = getCurrentMemberCode();
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
				String fileName = activityCode + "-" + currentUserCode + "-" + time + ext;
				try {
					FileUtil.uploadFile(multipartFile, basePath + path, fileName);
					msgMap.put("msg", multipartFile.getOriginalFilename() + " 上传成功！");
					msgMap.put("filePath", path + fileName);
					msgMap.put("fileName", multipartFile.getOriginalFilename());
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
	/********************************************
	 * other method
	 ********************************************/
	
	/**
	 * 获取当前会员code
	 * @return
	 */
	public String getCurrentMemberCode(){
		LogUser user = getFrontUser();
		if(user!=null){
			return user.getCode();
		}else{
			return null;
		}
	}
	
	/**
	 * 计算投票百分比
	 */
	public List<VoteOptionVO> computVotePercent(List<VoteOption> listVoteOption){
		if(ListUtil.isNotNull(listVoteOption)){
		    List<VoteOptionVO> list = new ArrayList<VoteOptionVO>();
			int allVoteNum = 0;
			int allFakeVoteNum = 0;
			for (VoteOption v : listVoteOption) {
				allVoteNum+=v.getCounts();
				allFakeVoteNum+=v.getFakeCounts();
			}
			if(allFakeVoteNum>0){
				for (VoteOption v : listVoteOption) {
					BigDecimal setScale = new BigDecimal(((float)v.getFakeCounts()/(float)allFakeVoteNum) * 100).setScale(0, BigDecimal.ROUND_HALF_UP);
					int percent = setScale.toBigInteger().intValue();
					
					VoteOptionVO voteOptionVO = new VoteOptionVO();
					voteOptionVO.setAllVoteNum(allVoteNum);
					voteOptionVO.setAllFakeVoteNum(allFakeVoteNum);
					voteOptionVO.setPercent(percent);
					voteOptionVO.setOptions(v.getOptions());
					voteOptionVO.setCounts(v.getCounts());
					voteOptionVO.setFakeCounts(v.getFakeCounts());
					list.add(voteOptionVO);
				}
			} else {
			    for (VoteOption v : listVoteOption) {
	                VoteOptionVO voteOptionVO = new VoteOptionVO();
	                voteOptionVO.setPercent(0);
	                voteOptionVO.setOptions(v.getOptions());
	                voteOptionVO.setCounts(0);
	                voteOptionVO.setFakeCounts(0);
	                list.add(voteOptionVO);
	            }
			}
			return list;
		} else {
		    return null;
		}
	}
	
	/********************************************
	 * Setter Getter
	 ********************************************/
	@Override
	public void setServletContext(ServletContext arg0) {
		this.application = arg0;
	}
	
	public static void main(String[] args) {
	    try {
	        String now_str = new SimpleDateFormat("yyyy-MM-dd").format(new Date(System.currentTimeMillis()));
	        Date now = new SimpleDateFormat("yyyy-MM-dd").parse(now_str);
	        String d1 = "2015-01-08";
	        Date date = new SimpleDateFormat("yyyy-MM-dd").parse(d1);
            //System.out.println(new Date(System.currentTimeMillis()).after(date));
            //System.out.println(now.after(date));
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }
	
}
