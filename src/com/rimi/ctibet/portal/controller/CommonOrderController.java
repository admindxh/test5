package com.rimi.ctibet.portal.controller;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.rimi.ctibet.common.controller.BaseController;
import com.rimi.ctibet.web.service.ICommonOrderService;

@Controller("portal_CommonOrderController")
@RequestMapping({"portal/commonorder","common-pay"})
public class CommonOrderController extends BaseController {

    @Resource ICommonOrderService commonOrderService;
    
    @RequestMapping("success")
    public String paySuccess(Model model, String activityCode){
        model.addAttribute("type", "success");
        model.addAttribute("programNam","4");
        return "portal/app/ridezone/pay_for_success";
    }
    @RequestMapping("failure")
    public String payFailure(Model model, String activityCode){
        model.addAttribute("type", "failure");
        model.addAttribute("programNam","4");
        return "portal/app/ridezone/pay_for_success";
    }
    
}
