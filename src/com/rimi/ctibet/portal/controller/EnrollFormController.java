package com.rimi.ctibet.portal.controller;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.rimi.ctibet.common.controller.BaseController;
import com.rimi.ctibet.common.util.Uuid;
import com.rimi.ctibet.domain.EnrollForm;
import com.rimi.ctibet.web.service.IEnrollFormService;

@Controller("portal_enrollFormController")
@RequestMapping("portal/enrollFormController")
public class EnrollFormController extends BaseController {

	@Resource IEnrollFormService enrollFormService;
	
	@RequestMapping("test")
	public void test(){
		EnrollForm enrollForm = new EnrollForm();
		enrollForm.setCode(Uuid.uuid());
		enrollForm.setActivityCode("11");
		enrollForm.setProperty("pro");
		enrollForm.setPropertyType("proType");
		enrollFormService.save(enrollForm);
	}
	
}
