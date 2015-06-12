package com.rimi.ctibet.web.dao.daoimpl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.rimi.ctibet.common.dao.impl.BaseDaoImpl;
import com.rimi.ctibet.common.util.ListUtil;
import com.rimi.ctibet.common.util.Pager;
import com.rimi.ctibet.common.util.StringUtil;
import com.rimi.ctibet.domain.Order;
import com.rimi.ctibet.web.controller.vo.ActivityOrderVO;
import com.rimi.ctibet.web.controller.vo.OrderVO;
import com.rimi.ctibet.web.dao.IOrderDao;

@Repository("orderDao")
public class OrderDaoImpl extends BaseDaoImpl<Order> implements IOrderDao {

    @Override
    public Pager searchOrderManage(Pager pager, String name, int payState) {
        List<Object> params = new ArrayList<Object>();
        StringBuffer sql = new StringBuffer();
        sql.append("  ");
        sql.append(" SELECT ");
        sql.append("     a.code,a.name,a.money,a.createTime, ");
        sql.append("     (SELECT count(id) FROM t_order WHERE activityCode=a.code AND avaliable=1  ");
        if(payState==0 || payState==1 ){
            sql.append("         AND payState=? ");
            params.add(payState);
        }
        sql.append("     ) AS payNum, ");
        sql.append("     (SELECT ifnull(sum(money),0) FROM t_order WHERE activityCode=a.code AND avaliable=1 ");
        if(payState==0 || payState==1 ){
            sql.append("         AND payState=? ");
            params.add(payState);
        }
        sql.append("     ) AS totalMoney ");
        sql.append(" FROM activity a ");
        sql.append(" WHERE a.avaliable=1 AND a.isEnrollPay=1 ");
        if(StringUtil.isNotNull(name)){
            sql.append(" AND a.name like ? ");
            params.add("%" + name + "%");
        }
        sql.append(" ORDER BY a.createTime DESC ");
        return findListPagerBySql(ActivityOrderVO.class, pager, sql.toString(), params);
    }

    @Override
    public Order getOrderByActCodeMemberCode(String activityCode, String memberCode) {
        List<Object> params = new ArrayList<Object>();
        params.add(activityCode);
        params.add(memberCode);
        String sql = " SELECT * FROM t_order WHERE avaliable=1 and activityCode=? and memberCode=? ORDER BY createTime DESC ";
        return ListUtil.returnSingle(findListBySql(Order.class, sql, params));//倒序获取最新的订单
    }

    @Override
    public Pager searchOrder(Pager pager, String orderCode, String mobileEmail, String activityName, String channelCode, String orderChannelSourceCode, String activityCode, int payState) {
        List<Object> params = new ArrayList<Object>();
        StringBuffer sql = new StringBuffer();
        sql.append(" SELECT ");
        sql.append("     a.code AS activityCode, a.name AS activityName, a.money AS activityMoney, a.linkUrl AS activityLinkUrl, ");
        sql.append("     o.code AS orderCode, o.payState, o.money AS orderMoney, o.createTime, ");
        sql.append("     oc.code AS orderChannelCode, CASE WHEN o.orderChannelCode = '-1' THEN '官方网站' ELSE oc.name END  AS orderChannelName, ");
        sql.append("     ocs.code AS orderChannelSourceCode, CASE WHEN o.orderChannelSourceCode = '-1' THEN '官方网站' ELSE ocs.name END AS orderChannelSourceName, ");
        sql.append("     m.code AS memberCode, m.name AS memberName, m.mobile, m.email ");
        sql.append(" FROM activity a ");
        sql.append("     INNER JOIN t_order o ON a.code=o.activityCode ");
        sql.append("     LEFT JOIN order_channel oc ON o.orderChannelCode=oc.code AND oc.avaliable=1 ");
        sql.append("     LEFT JOIN order_channel_source ocs ON ocs.code = o.orderChannelSourceCode AND ocs.avaliable = 1 ");
        sql.append("     LEFT JOIN v_member_detail m ON o.memberCode=m.code AND m.avaliable=1 ");
        sql.append(" WHERE  ");
        sql.append("     a.avaliable=1 AND o.avaliable=1 ");
        if(StringUtil.isNotNull(orderCode)){
            sql.append("     AND o.code = ? ");
            params.add(orderCode);
        }
        if(StringUtil.isNotNull(mobileEmail)){
            sql.append("     AND concat(ifnull(m.mobile,''),ifnull(m.email,'')) LIKE ? ");
            params.add("%" + mobileEmail + "%");
        }
        if(StringUtil.isNotNull(activityName)){
            sql.append("     AND a.name LIKE ? ");
            params.add("%" + activityName + "%");
        }
        if(StringUtil.isNotNull(channelCode)){
            // -1 表示官方渠道
            /*if(channelCode.equals("-1")){
                sql.append("     AND (o.orderChannelCode is null OR o.orderChannelCode='') ");
            }else{
                sql.append("     AND o.code = ? ");
                params.add(channelCode);
            }*/
            sql.append("     AND o.code = ? ");
            params.add(channelCode);
        }
        if(StringUtil.isNotNull(orderChannelSourceCode)){
            // -1 表示官方渠道
            sql.append("     AND o.orderChannelSourceCode = ? ");
            params.add(orderChannelSourceCode);
        }
        if(StringUtil.isNotNull(activityCode)){
            sql.append("     AND a.code = ? ");
            params.add(activityCode);
        }
        if(payState==0 || payState==1){
            sql.append("     AND o.payState=? ");
            params.add(payState);
        }
        sql.append(" ORDER BY o.createTime DESC ");
        if(pager.getPageSize()==-1){
            pager.setDataList(findListBySql(OrderVO.class, sql.toString(), params));;
            return pager;
        }
        return findListPagerBySql(OrderVO.class, pager, sql.toString(), params);
    }

}
