package com.rimi.ctibet.portal.controller;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.rimi.ctibet.common.controller.BaseController;
import com.rimi.ctibet.web.service.IOrderChannelService;

@Controller("portal_orderChannel")
@RequestMapping("portal/orderChannel")
public class OrderChannelController extends BaseController {

    @Resource IOrderChannelService orderChannelService;
    
}
