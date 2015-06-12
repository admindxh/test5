package com.rimi.ctibet.web.controller;

import java.text.SimpleDateFormat;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.rimi.ctibet.common.controller.BaseController;
import com.rimi.ctibet.common.util.DateUtil;
import com.rimi.ctibet.common.util.Pager;
import com.rimi.ctibet.common.util.StringUtil;
import com.rimi.ctibet.domain.Activity;
import com.rimi.ctibet.domain.EnrollForm;
import com.rimi.ctibet.domain.Order;
import com.rimi.ctibet.domain.OrderChannel;
import com.rimi.ctibet.domain.OrderChannelSource;
import com.rimi.ctibet.web.controller.vo.MemberEnrollDetailVO;
import com.rimi.ctibet.web.controller.vo.MemberVO;
import com.rimi.ctibet.web.controller.vo.OrderVO;
import com.rimi.ctibet.web.service.IActivityService;
import com.rimi.ctibet.web.service.IMemberEnrollDetailService;
import com.rimi.ctibet.web.service.IMemberService;
import com.rimi.ctibet.web.service.IOrderChannelService;
import com.rimi.ctibet.web.service.IOrderChannelSourceService;
import com.rimi.ctibet.web.service.IOrderService;

@Controller
@RequestMapping("web/order")
public class OrderController extends BaseController {

    @Resource IOrderService orderService;
    @Resource IOrderChannelSourceService orderChannelSourceService;
    @Resource IOrderChannelService orderChannelService;
    @Resource IActivityService activityService;
    @Resource IMemberService memberService;
    @Resource IMemberEnrollDetailService memberEnrollDetailService;
    
    /** 列表类型 */
    public final static String LIST_TYPE_ALL = "list_type_all"; //全部订单列表
    
    /**
     * 跳转到支付内容管理
     */
    @RequestMapping("forPayContentList")
    public String forPayContentList(Model model){
        return "manage/html/settings/order/bill-pay";
    }
    
    /**
     * 通过活动code获取渠道
     */
    @RequestMapping(value="loadPayContentList", produces = "text/html; charset=utf-8")
    @ResponseBody
    public String loadPayContentList(Pager pager, String name){
        pager = orderService.searchOrderManage(pager, name, 1);
        return obj2json(pager);
    }
    
    /**
     * 跳转到单个活动订单管理
     */
    @RequestMapping("forSingleOrderManageList")
    public String forSingleOrderManageList(Model model, String activityCode, String orderChannelSourceCode, String channelCode){
        List<OrderChannel> listOrderChannel = orderChannelService.getOrderChannelByActivityCode(activityCode);
        List<OrderChannelSource> listOrderChannelSource = orderChannelSourceService.getOrderChannelSourceByActivityCode(activityCode);
        model.addAttribute("listOrderChannel", listOrderChannel);
        model.addAttribute("listOrderChannelSource", listOrderChannelSource);
        model.addAttribute("orderChannelSourceCode", orderChannelSourceCode);
        model.addAttribute("channelCode", channelCode);
        model.addAttribute("activityCode", activityCode);
        return "manage/html/settings/order/singleOrder";
    }
    /**
     * 跳转到所有活动订单管理
     */
    @RequestMapping("forOrderManageList")
    public String forOrderManageList(){
        return "manage/html/settings/order/allOrder";
    }
    
    /**
     * 加载订单管理数据
     */
    @RequestMapping(value="loadOrderList", produces = "text/html; charset=utf-8")
    @ResponseBody
    public String loadOrderList(Pager pager, String orderCode, String mobileEmail, String activityName, String channelCode, String orderChannelSourceCode, String activityCode, int payState){
        pager = orderService.searchOrder(pager, orderCode, mobileEmail, activityName, channelCode, orderChannelSourceCode, activityCode, payState);
        return obj2json(pager);
    }
    
    /**
     * 删除订单
     */
    @RequestMapping(value="deleteOrder", produces = "text/html; charset=utf-8")
    @ResponseBody
    public String deleteOrder(String orderCode){
         try {
             //orderService.deleteLogicalByCode(orderCode);
             orderService.deleteOrderAndEnroll(orderCode);
             return "success";
         } catch (Exception e) {
             e.printStackTrace();
             return "error";
         }
    }
    
    /**
     * 跳转到订单详情
     */
    @RequestMapping("forOrderDetail")
    public String forOrderDetail(Model model, String orderCode, String listType){
        // 订单信息
        Order order = orderService.findByCode(orderCode);
        model.addAttribute("order", order);
        
        if(order!=null){
            String activityCode = order.getActivityCode();
            // 活动信息
            if(StringUtil.isNotNull(activityCode)){
                Activity activity = activityService.findByCode(activityCode);
                model.addAttribute("activity", activity);
            }
            // 会员信息
            String memberCode = order.getMemberCode();
            if(StringUtil.isNotNull(memberCode)){
                MemberVO memberVO = memberService.getMemberByMemberCode(memberCode);
                model.addAttribute("memberVO", memberVO);
            }
            // 报名信息
            if(StringUtil.isNotNull(activityCode, memberCode)){
                List<MemberEnrollDetailVO> listMemberEnrollDetailVO = memberEnrollDetailService.getMemberEnrollDetailByActCodeMemberCode(activityCode, memberCode);
                model.addAttribute("listMemberEnrollDetailVO", listMemberEnrollDetailVO);
            }
            // 支付渠道
            String orderChannelCode = order.getOrderChannelCode();
            if(StringUtil.isNotNull(orderChannelCode)){
                OrderChannel orderChannel = orderChannelService.findByCode(orderChannelCode);
                model.addAttribute("orderChannel", orderChannel);
            }
            // 报名来源渠道
            String orderChannelSourceCode = order.getOrderChannelSourceCode();
            if(StringUtil.isNotNull(orderChannelSourceCode)){
                OrderChannelSource orderChannelSource = orderChannelSourceService.findByCode(orderChannelSourceCode);
                model.addAttribute("orderChannelSource", orderChannelSource);
            }
            model.addAttribute("PROPERTY_TYPE_IMAGE", EnrollForm.PROPERTY_TYPE_IMAGE);
            model.addAttribute("PROPERTY_TYPE_DOC", EnrollForm.PROPERTY_TYPE_DOC);
            
            model.addAttribute("listType", listType);
            
        }
        return "manage/html/settings/order/order-lookup";
    }
    
    /**
     * 跳转到订单编辑
     */
    @RequestMapping("forEditOrder")
    public String forEditOrder(Model model, String orderCode, String listType){
        
        // 订单信息
        Order order = orderService.findByCode(orderCode);
        model.addAttribute("order", order);
        
        if(order!=null){
            String activityCode = order.getActivityCode();
            // 活动信息
            if(StringUtil.isNotNull(activityCode)){
                Activity activity = activityService.findByCode(activityCode);
                model.addAttribute("activity", activity);
            }
            // 会员信息
            String memberCode = order.getMemberCode();
            if(StringUtil.isNotNull(memberCode)){
                MemberVO memberVO = memberService.getMemberByMemberCode(memberCode);
                model.addAttribute("memberVO", memberVO);
            }
            // 报名信息
            if(StringUtil.isNotNull(activityCode, memberCode)){
                List<MemberEnrollDetailVO> listMemberEnrollDetailVO = memberEnrollDetailService.getMemberEnrollDetailByActCodeMemberCode(activityCode, memberCode);
                model.addAttribute("listMemberEnrollDetailVO", listMemberEnrollDetailVO);
            }
            // 支付渠道
            String orderChannelCode = order.getOrderChannelCode();
            if(StringUtil.isNotNull(orderChannelCode)){
                OrderChannel orderChannel = orderChannelService.findByCode(orderChannelCode);
                model.addAttribute("orderChannel", orderChannel);
            }
            // 报名来源渠道
            String orderChannelSourceCode = order.getOrderChannelSourceCode();
            if(StringUtil.isNotNull(orderChannelSourceCode)){
                OrderChannelSource orderChannelSource = orderChannelSourceService.findByCode(orderChannelSourceCode);
                model.addAttribute("orderChannelSource", orderChannelSource);
            }
            model.addAttribute("PROPERTY_TYPE_IMAGE", EnrollForm.PROPERTY_TYPE_IMAGE);
            model.addAttribute("PROPERTY_TYPE_DOC", EnrollForm.PROPERTY_TYPE_DOC);
            
            model.addAttribute("listType", listType);
        }
        return "manage/html/settings/order/order-edit";
    }
    
    /**
     * 编辑订单
     */
    @RequestMapping("editOrder")
    public String editOrder(Model model, Order order, String listType){
        Order newOrder = orderService.findByCode(order.getCode());
        newOrder.setMoney(order.getMoney());
        orderService.update(newOrder);
        
        model.addAttribute("activityCode", newOrder.getActivityCode());
        if(listType!=null && listType.equals(OrderController.LIST_TYPE_ALL)){
            return redirect("forOrderManageList");
        }
        return redirect("forSingleOrderManageList");
    }
    
    @RequestMapping("exportOrderExcel")
    public void exportOrderExcel(HttpServletResponse response, String mobileEmail, String channelCode, String orderChannelSourceCode, String activityCode, int payState){
        Activity activity = activityService.findByCode(activityCode);
        String activityName = "";
        if(activity!=null){
            activityName = activity.getName();
        }
        String[] titles = {"支付名称","订单编号","订单状态","订单金额","创建时间","渠道","用户手机","用户邮箱"};
        String[] properties = {"activityName","orderCode","payStateName","orderMoney","createTime","orderChannelSourceName","mobile","email"};
        List<OrderVO> dataList = orderService.searchOrder(new Pager(-1), null, mobileEmail, null, channelCode, orderChannelSourceCode, activityCode, payState).getDataList();
        String format = new SimpleDateFormat("yyyy-MM-dd").format(DateUtil.getCurrentDate());
        String fileName = format + "《" + activityName + "》订单信息.xls";
        exportExcel(response, fileName, titles, properties, dataList, "yyyy-MM-dd HH:mm:ss");
    }
    
}
