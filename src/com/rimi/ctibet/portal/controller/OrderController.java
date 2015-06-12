package com.rimi.ctibet.portal.controller;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.rimi.ctibet.common.controller.BaseController;
import com.rimi.ctibet.domain.LogUser;
import com.rimi.ctibet.domain.Order;
import com.rimi.ctibet.web.service.IMemberEnrollDetailService;
import com.rimi.ctibet.web.service.IOrderService;

@Controller("portal_order")
@RequestMapping("portal/order")
public class OrderController extends BaseController {

    @Resource IOrderService orderService;
    @Resource IMemberEnrollDetailService memberEnrollDetailService;
    
    /**
     * 检查支付
     */
    @RequestMapping(value="checkPay", produces = "text/html; charset=utf-8")
    @ResponseBody
    public String checkPay(String activityCode, String OCS){
        String returnValue = "";
        LogUser user = getFrontUser();
        // 判断有木有登录
        if(user!=null){
            String memberCode = user.getCode();
            return orderService.checkPay(activityCode, memberCode, OCS);
        } else {
            returnValue = "need_login";
        }
        return returnValue;
    }
    
}
