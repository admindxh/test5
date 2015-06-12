package com.rimi.ctibet.web.dao;

import com.rimi.ctibet.common.dao.BaseDao;
import com.rimi.ctibet.common.util.Pager;
import com.rimi.ctibet.domain.ActivityProduct;

public interface IActivityProductDao extends BaseDao<ActivityProduct> {

	/**
	 * 获取参赛作品列表
	 * @param activityCode 活动code
	 * @param isTop	是否按置顶排序
	 * @param state	审核状态
	 * @return
	 */
	public Pager getActivityProductsByActCodeIsTop(Pager pager, String activityCode, boolean isTop, int state);
	
}
