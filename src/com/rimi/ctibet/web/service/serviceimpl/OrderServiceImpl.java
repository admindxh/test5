package com.rimi.ctibet.web.service.serviceimpl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.rimi.ctibet.common.service.impl.BaseServiceImpl;
import com.rimi.ctibet.common.util.DateUtil;
import com.rimi.ctibet.common.util.Pager;
import com.rimi.ctibet.domain.Activity;
import com.rimi.ctibet.domain.Order;
import com.rimi.ctibet.web.controller.vo.MemberEnrollDetailVO;
import com.rimi.ctibet.web.dao.IActivityDao;
import com.rimi.ctibet.web.dao.IMemberEnrollDetailDao;
import com.rimi.ctibet.web.dao.IOrderDao;
import com.rimi.ctibet.web.service.IOrderService;

@Service("orderService")
public class OrderServiceImpl extends BaseServiceImpl<Order> implements IOrderService {
    
    @Resource IOrderDao orderDao;
    @Resource IActivityDao activityDao;
    @Resource IMemberEnrollDetailDao memberEnrollDetailDao;
    
    @Override
    public Order getOrderByActCodeMemberCode(String activityCode, String memberCode) {
        return orderDao.getOrderByActCodeMemberCode(activityCode, memberCode);
    }
    
    @Override
    public Order saveOrderByPayLink(String activityCode, String orderChannelCode) {
        try {
            
            Activity activity = activityDao.findByCode(activityCode);
            
            Order order = new Order();
            order.setCode(Order.createCode());
            order.setActivityCode(activityCode);
            order.setOrderChannelCode(orderChannelCode);
            order.setMoney(activity.getMoney());
            order.setCreateTime(DateUtil.getCurrentDate());
            orderDao.save(order);
            return order;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    
    @Override
    public Pager searchOrderManage(Pager pager, String name, int payState) {
        return orderDao.searchOrderManage(pager, name, payState);
    }

    @Override
    public Pager searchOrder(Pager pager, String orderCode, String mobileEmail, String activityName, String channelCode, String orderChannelSourceCode, String activityCode, int payState) {
        return orderDao.searchOrder(pager, orderCode, mobileEmail, activityName, channelCode, orderChannelSourceCode, activityCode, payState);
    }

    @Override
    public String checkPay(String activityCode, String memberCode, String OCS) {
        String returnValue = "";
        //
        List<MemberEnrollDetailVO> list = memberEnrollDetailDao.getMemberEnrollDetailByActCodeMemberCode(activityCode, memberCode);
        if(list!=null && list.size()>0){
            //
            Order order = this.getOrderByActCodeMemberCode(activityCode, memberCode);
            //Order order = orderDao.findByCode(list.get(0).getOrderCode());
            if(order!=null){
                if(order.getPayState()==1){
                    returnValue = "already_pay";
                }else{
                    returnValue = "to_pay";
                }
            }else{
                try {
                    Activity activity = activityDao.findByCode(activityCode);
                    Order new_order = new Order();
                    new_order.setCode(Order.createCode());
                    new_order.setActivityCode(activityCode);
                    new_order.setMoney(activity.getMoney());
                    new_order.setMemberCode(memberCode);
                    new_order.setOrderChannelCode("-1");
                    new_order.setOrderChannelSourceCode(OCS);
                    new_order.setCreateTime(DateUtil.getCurrentDate());
                    orderDao.save(new_order);
                    returnValue = "to_pay";
                } catch (Exception e) {
                    e.printStackTrace();
                    returnValue = "error";
                }
            }
            //
        }else{
            returnValue = "need_enroll";
        }
        //
        return returnValue;
    }
    
    @Override
    public void deleteOrderAndEnroll(String orderCode) {
        Order order = orderDao.findByCode(orderCode);
        if(order!=null){
            String activityCode = order.getActivityCode();
            String memberCode = order.getMemberCode();
            //删除报名信息
            memberEnrollDetailDao.deleteMemberEnrollDetailByActCodeMemberCode(activityCode, memberCode);
            //删除订单
            orderDao.deleteLogicalByCode(orderCode);
        }
    }
    
    /********************************************
     * Setter Getter
     ********************************************/
    public IOrderDao getOrderDao() {
        return orderDao;
    }

    public void setOrderDao(IOrderDao orderDao) {
        this.orderDao = orderDao;
    }

}
