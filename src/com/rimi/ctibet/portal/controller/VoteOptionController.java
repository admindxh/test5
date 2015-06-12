package com.rimi.ctibet.portal.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.rimi.ctibet.common.controller.BaseController;
import com.rimi.ctibet.web.service.IVoteOptionService;

@Controller("portal_voteOptionController")
@RequestMapping("portal/voteOptionController")
public class VoteOptionController extends BaseController {

	@Resource IVoteOptionService voteOptionService;
	
	/**
	 * 投票
	 * @param code
	 * @return
	 */
	@RequestMapping(value="vote", produces = "text/html; charset=utf-8")
	@ResponseBody
	public String vote(HttpServletRequest request, String activityCode, String code){
		boolean isOperate = checkSessionOperate(request.getSession(), activityCode+"activity_vote");
		String msg = "error";
		if(isOperate){
			msg = "already";
		}else{
			voteOptionService.updateVoteOptionCountByCode(code);
			msg = "success";
		}
		return msg;
	}
	
}
