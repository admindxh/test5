package com.rimi.ctibet.web.dao.daoimpl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.rimi.ctibet.common.dao.impl.BaseDaoImpl;
import com.rimi.ctibet.common.util.Pager;
import com.rimi.ctibet.common.util.StringUtil;
import com.rimi.ctibet.domain.RideLine;
import com.rimi.ctibet.web.dao.IRideLineDao;

@Repository("RideLineDao")
public class RideLineDaoImpl extends BaseDaoImpl<RideLine> implements IRideLineDao {

    @Override
    public Pager searchRideLineByName(Pager pager, String name) {
        List<Object> params = new ArrayList<Object>();
        StringBuffer sql = new StringBuffer();
        sql.append(" SELECT * FROM ride_line WHERE avaliable = 1 ");
        if(StringUtil.isNotNull(name)){
            sql.append(" AND name LIKE ? ");
            params.add("%" + name + "%");
        }
        sql.append(" ORDER BY createTime DESC ");
                
        return findListPagerBySql(RideLine.class, pager, sql.toString(), params);
    }

}
