package com.rimi.ctibet.web.dao;

import java.util.List;

import com.rimi.ctibet.common.dao.BaseDao;
import com.rimi.ctibet.common.util.Pager;
import com.rimi.ctibet.domain.Order;

public interface IOrderDao extends BaseDao<Order> {

    /**
     * 通过活动name 支付状态 搜索 支付内容管理列表
     * @param pager
     * @param name
     * @param payState
     * @return
     */
    public Pager searchOrderManage(Pager pager, String name, int payState);
    
    /**
     * 通过活动code 会员code 支付状态查询会员是否登录
     * @param acticityCode
     * @param memberCode
     * @param payState
     * @return
     */
    public Order getOrderByActCodeMemberCode(String activityCode, String memberCode);
    
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
    
}
