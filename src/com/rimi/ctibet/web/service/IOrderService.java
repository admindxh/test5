package com.rimi.ctibet.web.service;

import com.rimi.ctibet.common.service.BaseService;
import com.rimi.ctibet.common.util.Pager;
import com.rimi.ctibet.domain.Order;

public interface IOrderService extends BaseService<Order> {

    /**
     * 通过活动code 会员code 支付状态查询会员是否登录
     * @param acticityCode
     * @param memberCode
     * @param payState
     * @return
     */
    public Order getOrderByActCodeMemberCode(String activityCode, String memberCode);
    
    /**
     * 支付链接中加密的 code
     * @param code
     * @return
     */
    public Order saveOrderByPayLink(String activityCode, String orderChannelCode);
    
    /**
     * 通过活动name 支付状态 搜索 支付内容管理列表
     * @param pager
     * @param name
     * @param payState
     * @return
     */
    public Pager searchOrderManage(Pager pager, String name, int payState);
    
    /**
     * 通过 订单号 手机邮箱 活动名称 渠道 支付状态 搜索订单
     * @param orderCode
     * @param mobileEmail
     * @param activityName
     * @param channelCode
     * @param payState
     * @return
     */
    public Pager searchOrder(Pager pager, String orderCode, String mobileEmail, String activityName, String channelCode, String orderChannelSourceCode, String activityCode, int payState);
    
    /**
     * 检查是否支付是否报名
     * @param activityCode
     * @param memberCode
     * @return
     */
    public String checkPay(String activityCode, String memberCode, String OCS);
    
    /**
     * 删除订单和报名信息
     * @param orderCode
     */
    public void deleteOrderAndEnroll(String orderCode);
}
