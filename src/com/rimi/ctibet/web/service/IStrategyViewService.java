package com.rimi.ctibet.web.service;

import java.util.List;

import com.rimi.ctibet.common.service.BaseService;
import com.rimi.ctibet.domain.StrategyView;

public interface IStrategyViewService extends BaseService<StrategyView> {
	
	/**
	 * 通过内容code查询攻略目的地code（景点为null的数据就是目的地code）
	 * @param contentCode
	 * @return
	 */
	public List<StrategyView> findDestinationCodeByContentCode(String contentCode);
	
	/**
	 * 通过内容code查询攻略景点code（目的地和景点不为null的数据就是景点code）
	 * @param contentCode
	 * @return
	 */
	public List<StrategyView> findViewCodeByContentCode(String contentCode);
	
}
