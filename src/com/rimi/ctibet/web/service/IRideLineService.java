package com.rimi.ctibet.web.service;

import com.rimi.ctibet.common.service.BaseService;
import com.rimi.ctibet.common.util.Pager;
import com.rimi.ctibet.domain.RideLine;

public interface IRideLineService extends BaseService<RideLine> {

    /**
     * 删除RideLine
     */
    public void deleteRideLines(String[] codes);
    
    /**
     * 通过name搜索
     */
    public Pager searchRideLineByName(Pager pager, String name); 
}
