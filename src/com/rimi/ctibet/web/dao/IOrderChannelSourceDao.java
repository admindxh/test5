package com.rimi.ctibet.web.dao;

import java.util.List;

import com.rimi.ctibet.common.dao.BaseDao;
import com.rimi.ctibet.domain.OrderChannelSource;

public interface IOrderChannelSourceDao extends BaseDao<OrderChannelSource> {
    
    /**
     * 通过活动code获取到订单来源渠道
     * @param activityCode
     * @return
     */
    public List<OrderChannelSource> getOrderChannelSourceByActivityCode(String activityCode);
    
    
}
