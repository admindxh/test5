package com.rimi.ctibet.web.service;

import java.util.List;

import com.rimi.ctibet.common.service.BaseService;
import com.rimi.ctibet.domain.OrderChannel;

public interface IOrderChannelService extends BaseService<OrderChannel> {

    /**
     * 新增渠道并返回加密后的（activityCode,orderChannelCode）
     * @throws Exception 
     */
    public boolean saveOrderChannel(OrderChannel orderChannel);
    
    /**
     * 通过 活动code 获取渠道
     * @param activityCode
     * @return
     */
    public List<OrderChannel> getOrderChannelByActivityCode(String activityCode);
    
}
