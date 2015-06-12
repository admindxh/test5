package com.rimi.ctibet.web.service.serviceimpl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.rimi.ctibet.common.service.impl.BaseServiceImpl;
import com.rimi.ctibet.common.util.CodeFactory;
import com.rimi.ctibet.common.util.DesUtil;
import com.rimi.ctibet.domain.OrderChannel;
import com.rimi.ctibet.web.dao.IOrderChannelDao;
import com.rimi.ctibet.web.service.IOrderChannelService;

@Service("orderChannelService")
public class OrderChannelServiceImpl extends BaseServiceImpl<OrderChannel> implements IOrderChannelService {

    @Resource IOrderChannelDao orderChannelDao;
    

    @Override
    public boolean saveOrderChannel(OrderChannel orderChannel) {
        //加密后的code
        String encode = null;
        try {
            orderChannel.setCode(CodeFactory.createCode("ORCHNL"));
            
            String value = orderChannel.getActivityCode() + "," + orderChannel.getCode();
            encode = new DesUtil().encrypt(value);
            
            orderChannel.setPayLink(OrderChannel.PAY_LINK + encode);
            orderChannelDao.save(orderChannel);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public List<OrderChannel> getOrderChannelByActivityCode(String activityCode){
        return orderChannelDao.getOrderChannelByActivityCode(activityCode);
    }


    /********************************************
     * Setter Getter
     ********************************************/
    public IOrderChannelDao getOrderChannelDao() {
        return orderChannelDao;
    }
    public void setOrderChannelDao(IOrderChannelDao orderChannelDao) {
        this.orderChannelDao = orderChannelDao;
    }
    
}
