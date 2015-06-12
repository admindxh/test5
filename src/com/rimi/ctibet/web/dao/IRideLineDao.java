package com.rimi.ctibet.web.dao;

import com.rimi.ctibet.common.dao.BaseDao;
import com.rimi.ctibet.common.util.Pager;
import com.rimi.ctibet.domain.RideLine;

public interface IRideLineDao extends BaseDao<RideLine> {

    /**
     * 通过name搜索
     */
    public Pager searchRideLineByName(Pager pager, String name); 
    
}
