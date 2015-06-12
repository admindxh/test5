package com.rimi.ctibet.web.dao;

import java.util.List;

import com.rimi.ctibet.common.dao.BaseDao;
import com.rimi.ctibet.domain.StrategyView;

public interface IStrategyViewDao extends BaseDao<StrategyView> {
	
	/**
	 * 通过内容code删除
	 * @param contentode
	 */
	public int deleteByContentCode(String contentCode);
	
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
