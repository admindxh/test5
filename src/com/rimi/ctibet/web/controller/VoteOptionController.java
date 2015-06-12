package com.rimi.ctibet.web.controller;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.rimi.ctibet.common.controller.BaseController;
import com.rimi.ctibet.web.service.IVoteOptionService;

@Controller
@RequestMapping("web/voteOptionController")
public class VoteOptionController extends BaseController {

	@Resource IVoteOptionService voteOptionService;
	
}
