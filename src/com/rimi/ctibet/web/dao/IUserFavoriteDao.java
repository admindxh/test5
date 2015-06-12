package com.rimi.ctibet.web.dao;

import com.rimi.ctibet.common.dao.BaseDao;
import com.rimi.ctibet.domain.UserFavorite;

public interface IUserFavoriteDao extends BaseDao<UserFavorite>{

    /**
     * 通过code查询
     * @param code
     * @return
     */
    public UserFavorite getByCode(String code, String memberCode);
    
}
