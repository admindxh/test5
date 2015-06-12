package com.rimi.ctibet.web.dao;

import java.util.List;

import com.rimi.ctibet.common.dao.BaseDao;
import com.rimi.ctibet.domain.OrderChannel;

public interface IOrderChannelDao extends BaseDao<OrderChannel> {

    /**
     * 通过 活动code 获取渠道
     * @param activityCode
     * @return
     */
    public List<OrderChannel> getOrderChannelByActivityCode(String activityCode);
    
}
