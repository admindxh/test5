package com.rimi.ctibet.portal.controller;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.ServletContext;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.ServletContextAware;

import com.rimi.ctibet.common.controller.BaseController;
import com.rimi.ctibet.common.util.FileUtil;
import com.rimi.ctibet.domain.LogUser;
import com.rimi.ctibet.domain.MemberEnrollDetail;
import com.rimi.ctibet.web.controller.vo.ActivityVO;
import com.rimi.ctibet.web.controller.vo.MemberEnrollDetailVO;
import com.rimi.ctibet.web.service.IMemberEnrollDetailService;

@Controller("portal_memberEnrollDetailController")
@RequestMapping("portal/memberEnrollDetailController")
public class MemberEnrollDetailController extends BaseController implements ServletContextAware {
	public static final	Logger LOGGER = Logger.getLogger(MemberEnrollDetailController.class);
	
	ServletContext application;
	
	@Resource IMemberEnrollDetailService memberEnrollDetailService;
	
	final String tempPath = "/temp/";
	
	/**
	 * 保存报名表单信息
	 * @param activityCode
	 * @param activityVO
	 * @return
	 */
	@RequestMapping(value="addMemberEnrollDetail", produces = "text/html; charset=utf-8")
	@ResponseBody
	public String addMemberEnrollDetail(String activityCode, String OCS, ActivityVO activityVO){
	    if(getFrontUser()!=null){
    		try {
    			String basePath = application.getRealPath("/");
    			List<MemberEnrollDetail> listMemberEnrollDetail = activityVO.getListMemberEnrollDetail();
    			for (MemberEnrollDetail memberEnrollDetail : listMemberEnrollDetail) {
    			    if(memberEnrollDetail.getPropertyValue()!=null){
    			        String newPath = memberEnrollDetail.getPropertyValue().replace(tempPath, "");
    			        FileUtil.moveFile(basePath + memberEnrollDetail.getPropertyValue(), basePath+newPath);
    			        memberEnrollDetail.setPropertyValue(newPath);
    			    }
    				memberEnrollDetail.setActivityCode(activityCode);
    				LogUser frontUser = getFrontUser();
    				memberEnrollDetail.setMemberCode(frontUser.getCode());
    			}
    			String memberCode = getFrontUser().getCode();
    			memberEnrollDetailService.saveMemberEnrollDetail(activityCode, OCS, memberCode, listMemberEnrollDetail);
    			return "success";
    		} catch (Exception e) {
    			e.printStackTrace();
    			return "error";
    		}
	    }else{
	        return "need_login";
	    }
	}
	
	/**
	 * 检查投票权限
	 * portal/memberEnrollDetailController/checkEnroll
	 */
	@RequestMapping(value="checkEnroll", produces = "text/html; charset=utf-8")
	@ResponseBody
	public String checkEnroll(String activityCode){
		LogUser user = getFrontUser();
		if(user!=null){
			String memberCode = user.getCode();
			List<MemberEnrollDetailVO> list = memberEnrollDetailService.getMemberEnrollDetailByActCodeMemberCode(activityCode, memberCode);
			if(list!=null && list.size()>0){
				return "你已经报名";
			}else{
				return "";
			}
		}else{
			return "请先登录";
		}
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
	
	/********************************************
	 * Setter Getter
	 ********************************************/
	@Override
	public void setServletContext(ServletContext arg0) {
		this.application = arg0;
	}
}
