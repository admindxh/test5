package com.rimi.ctibet.portal.controller;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.rimi.ctibet.common.controller.BaseController;
import com.rimi.ctibet.common.util.Uuid;
import com.rimi.ctibet.domain.EnrollNotice;
import com.rimi.ctibet.web.service.IEnrollNoticeService;

@Controller("portal_enrollNoticeController")
@RequestMapping("portal/enrollNoticeController")
public class EnrollNoticeController extends BaseController {

	@Resource IEnrollNoticeService enrollNoticeService;
	
	@RequestMapping("test")
	public void test(){
		EnrollNotice enrollNotice = new EnrollNotice();
		enrollNotice.setCode(Uuid.uuid());
		enrollNotice.setActivityCode("123");
		enrollNotice.setName1("1");
		enrollNotice.setName2("2");
		enrollNotice.setName3("3");
		enrollNotice.setSummary1("summary1");
		enrollNotice.setSummary2("summary2");
		enrollNotice.setSummary3("summary3");
		enrollNoticeService.save(enrollNotice);
	}
	
}
