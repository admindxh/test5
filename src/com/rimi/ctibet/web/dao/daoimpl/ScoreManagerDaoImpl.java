package com.rimi.ctibet.web.dao.daoimpl;

import org.springframework.stereotype.Repository;

import com.rimi.ctibet.common.dao.impl.BaseDaoImpl;
import com.rimi.ctibet.common.util.ListUtil;
import com.rimi.ctibet.domain.ScoreManager;
import com.rimi.ctibet.web.dao.IScoreManagerDao;

/**
 * 积分管理
 * @author dengxh
 *
 */
@Repository("scoreManagerDao")
public class ScoreManagerDaoImpl  extends BaseDaoImpl<ScoreManager> implements IScoreManagerDao{

    @Override
    public ScoreManager getScoreManagerByType(String type) {
        String sql = " SELECT * FROM scoremanager WHERE scoretype='" + type + "' ";
        return ListUtil.returnSingle(findListBySql(ScoreManager.class, sql));
    }
	
}
