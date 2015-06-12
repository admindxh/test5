package com.rimi.ctibet.web.dao.daoimpl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.rimi.ctibet.common.dao.impl.BaseDaoImpl;
import com.rimi.ctibet.domain.OrderChannelSource;
import com.rimi.ctibet.web.dao.IOrderChannelSourceDao;

@Repository("orderChannelSourceDao")
public class OrderChannelSourceDaoImpl extends BaseDaoImpl<OrderChannelSource> implements IOrderChannelSourceDao {
    @Override
    public List<OrderChannelSource> getOrderChannelSourceByActivityCode(String activityCode) {
        List<Object> params = new ArrayList<Object>();
        String sql = " SELECT * FROM order_channel_source WHERE avaliable=1 AND activityCode=? ";
        params.add(activityCode);
        return findListBySql(OrderChannelSource.class, sql, params);
    }
}
