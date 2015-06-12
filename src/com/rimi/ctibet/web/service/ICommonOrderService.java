package com.rimi.ctibet.web.service;


import java.util.List;

import com.rimi.ctibet.common.service.BaseService;
import com.rimi.ctibet.common.util.Pager;
import com.rimi.ctibet.domain.CommonOrder;

/**
 * 骑行专区 / 订单管理
 *
 * @author tangHQ
 * @date 2015-4-9
 */
public interface ICommonOrderService extends BaseService<CommonOrder> {
	
	/**
	 * 分页查询订单
	 * 
	 * @param pager
	 * @param order
	 * @return
	 */
	public Pager queryCommonOrderByPager (Pager pager, CommonOrder order, String orderTime);
	
	/**
	 * 查询购买了某个编号为goodsCode的订单
	 * 
	 * @param goodsCode
	 * @return
	 */
	public List<CommonOrder> queryOrdersByGoodsCode (String goodsCode);
	
}
