package com.rimi.ctibet.web.dao;

import com.rimi.ctibet.common.dao.BaseDao;
import com.rimi.ctibet.domain.ScoreManager;

/**
 * 积分管理
 * @author dengxh
 *
 */
public interface IScoreManagerDao  extends BaseDao<ScoreManager>{
		
    /**
     * 通过类型获取
     */
    public ScoreManager getScoreManagerByType(String type);
    
}
