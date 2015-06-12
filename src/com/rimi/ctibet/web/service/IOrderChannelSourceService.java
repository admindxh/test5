package com.rimi.ctibet.web.service;

import java.util.List;

import com.rimi.ctibet.common.service.BaseService;
import com.rimi.ctibet.domain.OrderChannelSource;

public interface IOrderChannelSourceService extends BaseService<OrderChannelSource> {

    /**
     * 通过活动code获取到订单来源渠道
     * @param activityCode
     * @return
     */
    public List<OrderChannelSource> getOrderChannelSourceByActivityCode(String activityCode);
    
    /**
     * 保存渠道
     * @param orderChannelSource
     * @return
     */
    public boolean saveOrderChannelSource(OrderChannelSource orderChannelSource);
    
}
