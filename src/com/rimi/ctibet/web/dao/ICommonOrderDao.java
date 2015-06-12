package com.rimi.ctibet.web.dao;

import com.rimi.ctibet.common.dao.BaseDao;
import com.rimi.ctibet.common.util.Pager;
import com.rimi.ctibet.domain.CommonOrder;

/**
 * 骑行专区 / 订单管理 / DAO接口申明
 *
 * @author tangHQ
 * @date 2015-4-9
 */
public interface ICommonOrderDao extends BaseDao<CommonOrder> {

	/**
	 * 分页查询订单列表
	 * 
	 * @param pager 分页对象
	 * @param order 过滤条件
	 * @return 分页数据
	 */
	public Pager queryCommonOrderByPager (Pager pager, CommonOrder order, String orderTime);
	
}
