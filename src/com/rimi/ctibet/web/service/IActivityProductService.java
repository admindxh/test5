package com.rimi.ctibet.web.service;

import com.rimi.ctibet.common.service.BaseService;
import com.rimi.ctibet.common.util.Pager;
import com.rimi.ctibet.domain.ActivityProduct;

public interface IActivityProductService extends BaseService<ActivityProduct> {

	/**
	 * 获取参赛作品列表
	 * @param activityCode 活动code
	 * @param isTop	是否按置顶排序
	 * @param state	审核状态
	 * @return
	 */
	public Pager getActivityProductsByActCodeIsTop(Pager pager, String activityCode, boolean isTop, int state);
	
	/**
	 * 保存作品信息
	 * @param activityCode
	 * @param activityProduct
	 */
	public void saveActivityProduct(ActivityProduct activityProduct);
	
	/**
	 * 修改审核状态
	 * @param code
	 */
	public void updateState(String code, int state);
	/**
	 * 修改置顶字段
	 * @param code
	 */
	public void updateIsTop(String code, int isTop);
	/**
	 * 修改伪赞字段
	 * @param code
	 */
	public void updateFakeLikeNum(String code, int fakeLikeNum);
	/**
	 * 点赞 +1
	 * @param code
	 */
	public void updateLikeNum(String code);
	
	
}
