package com.rimi.ctibet.web.dao.daoimpl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Repository;

import com.rimi.ctibet.common.dao.impl.BaseDaoImpl;
import com.rimi.ctibet.common.util.Pager;
import com.rimi.ctibet.domain.CommonOrder;
import com.rimi.ctibet.web.dao.ICommonOrderDao;

/**
 * 骑行专区 / 订单管理 / DAO接口实现
 *
 * @author tangHQ
 * @date 2015-4-9
 */
@Repository("commonOrderDao")
public class CommonOrderDaoImpl extends BaseDaoImpl<CommonOrder> implements ICommonOrderDao {

	@Override
	public Pager queryCommonOrderByPager(Pager pager, CommonOrder order, String orderTime) {
		
		StringBuffer sql = new StringBuffer();
		
		List<Object> params = new ArrayList<Object>();
		
		/* ==> 基础SQL. */
		sql.append(" select ");
		sql.append(" 	c.id, c.avaliable, c.code, c.orderCode, c.orderName, c.note, c.payState, c.money, c.createTime, c.lastEditTime, c.dealTime, c.alipayEmail, c.type, c.source, c.successUrl, c.failureUrl, c.num, c.goodsCode, c.mobile, c.address, c.zipcode, c.memberCode, m.name as memberName ");
		sql.append(" from ");
		sql.append(" 	common_order c ");
		sql.append(" left join v_member_base m ");
		sql.append(" on c.memberCode = m.code ");
		sql.append(" where c.avaliable = 1 ");
		sql.append(" and m.avaliable = 1 ");
		
		/* ==> 查询条件. */
		
		// 订单名称
		condition(sql, params, "c.orderName", "like", order.getOrderName());
		
		// 支付状态
		if (order.getPayState() == 0 || order.getPayState() == 1) {
			condition(sql, params, "c.payState", "=", "" + order.getPayState());
		}
		
		// 会员编码
		condition(sql, params, "c.memberCode", "=", order.getMemberCode());
		
		// 创建时间
		condition(sql, params, "date_format(c.createTime,'%Y-%m-%d')", "=", orderTime);
		
		return findListPagerBySql(CommonOrder.class, pager, sql.toString(), params);
	}

	/**
	 * 合并SQL查询条件
	 * 
	 * @param sql 目标SQL语句
	 * @param params 参数
	 * @param name 条件字段
	 * @param str 比较符
	 * @param value 目标值
	 */
	public static final void condition (StringBuffer sql, List<Object> params, String name, String str, String value) {
		if (StringUtils.isNotBlank(value)) {
			sql.append(" and " + name + " " + str + " ? ");
			params.add(str.equals("like") ? "%" + value + "%" : value);
		}
	}
}
