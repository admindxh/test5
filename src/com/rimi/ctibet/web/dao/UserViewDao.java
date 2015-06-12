package com.rimi.ctibet.web.dao;

import com.rimi.ctibet.common.dao.BaseDao;
import com.rimi.ctibet.domain.UserView;

public interface UserViewDao extends BaseDao<UserView>{

    /**
     * 通过 会员code 和 景点code获取数据
     */
    public UserView getUserView(String memberCode, String viewCode, String type);
    
    /**
     * 通过 会员code 和 景点code获取数据
     */
    public UserView getUserDes(String memberCode, String desCode, String type);
    
}
