package com.rimi.ctibet.web.dao.daoimpl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.rimi.ctibet.common.dao.impl.BaseDaoImpl;
import com.rimi.ctibet.domain.OrderChannel;
import com.rimi.ctibet.web.dao.IOrderChannelDao;

@Repository("orderChannelDao")
public class OrderChannelDaoImpl extends BaseDaoImpl<OrderChannel> implements IOrderChannelDao {

    @Override
    public List<OrderChannel> getOrderChannelByActivityCode(String activityCode) {
        List<Object> params = new ArrayList<Object>();
        String sql = " SELECT * FROM order_channel WHERE avaliable=1 AND activityCode=? ";
        params.add(activityCode);
        return findListBySql(OrderChannel.class, sql, params);
    }

}
