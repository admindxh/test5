package com.rimi.ctibet.web.controller;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.rimi.ctibet.common.controller.BaseController;
import com.rimi.ctibet.domain.Activity;
import com.rimi.ctibet.domain.OrderChannelSource;
import com.rimi.ctibet.web.service.IActivityService;
import com.rimi.ctibet.web.service.IOrderChannelService;
import com.rimi.ctibet.web.service.IOrderChannelSourceService;

@Controller
@RequestMapping("web/OrderChannelSource")
public class OrderChannelSourceController extends BaseController {

    @Resource IOrderChannelSourceService orderChannelSourceService;
    @Resource IOrderChannelService orderChannelService;
    @Resource IActivityService activityService;
    
    /**
     * 跳转到渠道管理页面
     */
    @RequestMapping("forOrderChannelSourceManage")
    public String forOrderChannelSourceManage(Model model, String activityCode){
        Activity activity = activityService.findByCode(activityCode);
        model.addAttribute("activity", activity);
        return "manage/html/activity/bill-enrollpay-source";
    }
    
    
    /**
     * 通过活动code获取渠道
     */
    @RequestMapping(value="loadOrderChannelSourcelList", produces = "text/html; charset=utf-8")
    @ResponseBody
    public String loadOrderChannelSourcelList(String activityCode){
        List<OrderChannelSource> listOrderChannelSourcel = orderChannelSourceService.getOrderChannelSourceByActivityCode(activityCode);
        return obj2json(listOrderChannelSourcel);
    }
    
    /**
     * 新增渠道
     */
    @RequestMapping(value="addOrderChannelSource", produces = "text/html; charset=utf-8")
    @ResponseBody
    public String addOrderChannelSource(OrderChannelSource orderChannelSource){
        boolean flag = orderChannelSourceService.saveOrderChannelSource(orderChannelSource);
        if (flag) {
            return "success";
        } else {
            return "error";
        }
    }
    
    /**
     * 编辑渠道
     */
    @RequestMapping(value="editOrderChannelSource", produces = "text/html; charset=utf-8")
    @ResponseBody
    public String editOrderChannelSource(OrderChannelSource orderChannelSource){
        try {
            OrderChannelSource newOrderChannelSource = orderChannelSourceService.findByCode(orderChannelSource.getCode());
            newOrderChannelSource.setName(orderChannelSource.getName());
            orderChannelSourceService.update(newOrderChannelSource);
            return "success";
        } catch (Exception e) {
            e.printStackTrace();
            return "error";
        }
    }

    /**
     * 删除渠道
     */
    @RequestMapping(value="deleteOrderChannelSource", produces = "text/html; charset=utf-8")
    @ResponseBody
    public String deleteOrderChannelSource(String code){
        try {
            orderChannelSourceService.deleteLogicalByCode(code);
            return "success";
        } catch (Exception e) {
            e.printStackTrace();
            return "error";
        }
    }
    
    
}
