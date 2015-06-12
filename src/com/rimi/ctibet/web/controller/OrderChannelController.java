package com.rimi.ctibet.web.controller;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.rimi.ctibet.common.controller.BaseController;
import com.rimi.ctibet.domain.Activity;
import com.rimi.ctibet.domain.OrderChannel;
import com.rimi.ctibet.web.service.IActivityService;
import com.rimi.ctibet.web.service.IOrderChannelService;

@Controller
@RequestMapping("web/orderChannel")
public class OrderChannelController extends BaseController {

    @Resource IOrderChannelService orderChannelService;
    @Resource IActivityService activityService;
    
    /**
     * 跳转到渠道管理页面
     */
    @RequestMapping("forOrderChannelManage")
    public String forOrderChannelManage(Model model, String activityCode){
        Activity activity = activityService.findByCode(activityCode);
        model.addAttribute("activity", activity);
        return "manage/html/settings/order/bill-pay-source";
    }
    
    
    /**
     * 通过活动code获取渠道
     */
    @RequestMapping(value="loadOrderChannelList", produces = "text/html; charset=utf-8")
    @ResponseBody
    public String loadOrderChannelList(String activityCode){
        List<OrderChannel> listOrderChannel = orderChannelService.getOrderChannelByActivityCode(activityCode);
        return obj2json(listOrderChannel);
    }
    
    /**
     * 新增渠道
     */
    @RequestMapping(value="addOrderChannel", produces = "text/html; charset=utf-8")
    @ResponseBody
    public String addOrderChannel(OrderChannel orderChannel){
        boolean flag = orderChannelService.saveOrderChannel(orderChannel);
        if (flag) {
            return "success";
        } else {
            return "error";
        }
    }
    
    /**
     * 编辑渠道
     */
    @RequestMapping(value="editOrderChannel", produces = "text/html; charset=utf-8")
    @ResponseBody
    public String editOrderChannel(OrderChannel orderChannel){
        try {
            OrderChannel newOrderChannel = orderChannelService.findByCode(orderChannel.getCode());
            newOrderChannel.setName(orderChannel.getName());
            orderChannelService.update(newOrderChannel);
            return "success";
        } catch (Exception e) {
            e.printStackTrace();
            return "error";
        }
    }

    /**
     * 删除渠道
     */
    @RequestMapping(value="deleteOrderChannel", produces = "text/html; charset=utf-8")
    @ResponseBody
    public String deleteOrderChannel(String code){
        try {
            orderChannelService.deleteLogicalByCode(code);
            return "success";
        } catch (Exception e) {
            e.printStackTrace();
            return "error";
        }
    }
    
    
}
