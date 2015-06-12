package com.rimi.ctibet.portal.controller;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.rimi.ctibet.common.controller.BaseController;
import com.rimi.ctibet.common.util.Pager;
import com.rimi.ctibet.domain.RideLine;
import com.rimi.ctibet.web.service.IRideLineService;

@Controller("StarService")
@RequestMapping("star-service")
public class StarServiceController extends BaseController {
    
    @Resource IRideLineService rideLineService;
    
    /**
     * 跳转到星级服务页面
     */
    @RequestMapping("list")
    public String list(Model model){
        List<RideLine> listRideLine = rideLineService.searchRideLineByName(new Pager(3), null).getDataList();
        model.addAttribute("listRideLine", listRideLine);
        return "";
    }
    
    /**
     * 加载路线信息
     */
    @RequestMapping(value="loadRideLine", produces = "text/html; charset=utf-8")
    @ResponseBody
    public String loadRideLine(String code){
        RideLine rideLine = rideLineService.findByCode(code);
        return obj2json(rideLine);
    }
    
}
