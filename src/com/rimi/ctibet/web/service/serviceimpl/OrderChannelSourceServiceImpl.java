package com.rimi.ctibet.web.service.serviceimpl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.rimi.ctibet.common.service.impl.BaseServiceImpl;
import com.rimi.ctibet.common.util.CodeFactory;
import com.rimi.ctibet.common.util.DateUtil;
import com.rimi.ctibet.domain.Activity;
import com.rimi.ctibet.domain.OrderChannelSource;
import com.rimi.ctibet.web.dao.IOrderChannelSourceDao;
import com.rimi.ctibet.web.service.IOrderChannelSourceService;

@Service("OrderChannelSourceServce")
public class OrderChannelSourceServiceImpl extends BaseServiceImpl<OrderChannelSource> implements IOrderChannelSourceService {

    @Resource IOrderChannelSourceDao orderChannelSourceDao;

    @Override
    public List<OrderChannelSource> getOrderChannelSourceByActivityCode(String activityCode) {
        return orderChannelSourceDao.getOrderChannelSourceByActivityCode(activityCode);
    }
    
    @Override
    public boolean saveOrderChannelSource(OrderChannelSource orderChannelSource){
        try {
            String code = CodeFactory.createCode("OCS");
            //String activityLink = Activity.LINK_URL_ACTIVITY_DETAIL + "?code=" + orderChannelSource.getActivityCode();
            String activityLink = "activity/detail/" + orderChannelSource.getActivityCode()+".html";
            
            String orderChannelSourceLink = activityLink + "&OCS=" + code;
            orderChannelSource.setCode(code);
            orderChannelSource.setPayLink(orderChannelSourceLink);
            orderChannelSource.setCreateTime(DateUtil.getCurrentDate());
            orderChannelSourceDao.save(orderChannelSource);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    
    /********************************************
     * Setter Getter
     ********************************************/
    public IOrderChannelSourceDao getOrderChannelSourceDao() {
        return orderChannelSourceDao;
    }

    public void setOrderChannelSourceDao(IOrderChannelSourceDao orderChannelSourceDao) {
        this.orderChannelSourceDao = orderChannelSourceDao;
    }
    
}
