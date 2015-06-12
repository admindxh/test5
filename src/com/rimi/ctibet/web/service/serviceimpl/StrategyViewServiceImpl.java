package com.rimi.ctibet.web.service.serviceimpl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.rimi.ctibet.common.service.impl.BaseServiceImpl;
import com.rimi.ctibet.domain.StrategyView;
import com.rimi.ctibet.web.dao.IStrategyViewDao;
import com.rimi.ctibet.web.service.IStrategyViewService;

@Service("strategyViewService")
@Transactional
public class StrategyViewServiceImpl extends BaseServiceImpl<StrategyView> implements IStrategyViewService {
	
	@Resource
	IStrategyViewDao strategyViewDao;
	
	/**
	 * 通过内容code查询攻略目的地code（景点为null的数据就是目的地code）
	 * @param contentCode
	 * @return
	 */
	public List<StrategyView> findDestinationCodeByContentCode(String contentCode){
		return strategyViewDao.findDestinationCodeByContentCode(contentCode);
	}
	
	/**
	 * 通过内容code查询攻略景点code（目的地和景点不为null的数据就是景点code）
	 * @param contentCode
	 * @return
	 */
	public List<StrategyView> findViewCodeByContentCode(String contentCode){
		return strategyViewDao.findViewCodeByContentCode(contentCode);
	}

	/********************************************
	 * Setter Getter
	 ********************************************/
	public IStrategyViewDao getStrategyViewDao() {
		return strategyViewDao;
	}

	public void setStrategyViewDao(IStrategyViewDao strategyViewDao) {
		this.strategyViewDao = strategyViewDao;
	}
	
}
