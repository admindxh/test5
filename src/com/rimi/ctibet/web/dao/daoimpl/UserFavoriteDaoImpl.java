package com.rimi.ctibet.web.dao.daoimpl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.rimi.ctibet.common.dao.impl.BaseDaoImpl;
import com.rimi.ctibet.common.util.ListUtil;
import com.rimi.ctibet.domain.UserFavorite;
import com.rimi.ctibet.web.dao.IUserFavoriteDao;
@Repository("userFavoriteDao")
public class UserFavoriteDaoImpl extends BaseDaoImpl<UserFavorite> implements IUserFavoriteDao{

    @Override
    public UserFavorite getByCode(String code, String memberCode) {
        List<UserFavorite> list = getHibernateTemplate().find(" from UserFavorite where code='" + code + "' and memberCode='" + memberCode + "' ");
        return ListUtil.returnSingle(list);
    }

}
