package com.rimi.ctibet.web.controller;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.rimi.ctibet.common.controller.BaseController;
import com.rimi.ctibet.web.service.IEnrollFormService;

@Controller
@RequestMapping("web/enrollFormController")
public class EnrollFormController extends BaseController {

	@Resource IEnrollFormService enrollFormService;
	
}
